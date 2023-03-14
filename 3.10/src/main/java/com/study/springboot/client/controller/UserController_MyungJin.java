package com.study.springboot.client.controller;

import com.study.springboot.admin.dto.CouponResoponseDTO;
import com.study.springboot.admin.dto.MemberResponseDTO;
import com.study.springboot.admin.dto.OrderResponseDto;
import com.study.springboot.admin.service.OrderService;
import com.study.springboot.client.dto.*;
import com.study.springboot.client.service.ClientReviewService_JunTae;
import com.study.springboot.client.service.CouponService;
import com.study.springboot.client.service.NonmemberService;
import com.study.springboot.entity.Member;
import com.study.springboot.entity.MemberListRepository;
import com.study.springboot.admin.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Properties;
import java.util.Random;

@Controller
@RequiredArgsConstructor
public class UserController_MyungJin {
    private final MemberListRepository memberListRepository;
    private final MemberService memberService;
    private final OrderService orderService;
    private final PasswordEncoder passwordEncoder;
    private final CouponService couponService;
    private final NonmemberService nonmemberService;
    private final ClientReviewService_JunTae reviewService;
    @RequestMapping("/id-check")
    @ResponseBody
    public int checkId(@RequestParam("id") String id) {
        if (memberService.checkMemberId(id).equals("0")) {
            return 0;
        } else {
            return 1;
        }
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/client/login/userlogin";     // loginForm.html로 응답
    }


    @GetMapping("/logoutAction")
    @ResponseBody
    public String loguotAction(HttpServletRequest request) {
        // 세션 종료
        request.getSession().invalidate();
        return "<script>alert('로그아웃 되었습니다.'); location.href='/';</script>";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "/client/login/userjoin";
    }

    @PostMapping("/joinAction")
    @ResponseBody
    public String joinAction(@Valid MemberJoinDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // DTO에 설정한 message값을 가져온다.
            String detail = bindingResult.getFieldError().getDefaultMessage();
            // DTO에 유효성체크를 걸어놓은 어노테이션명을 가져온다.
            String bindResultCode = bindingResult.getFieldError().getCode();
            System.out.println(detail + ":" + bindResultCode);
            return "<script>alert('" + detail + "'); history.back();</script>";
        }

        System.out.println(dto.getMemberID());
        System.out.println(dto.getMemberPw());

        //암호화를 위해 시큐리티의 BCryptPasswordEncoder 클래스를 사용
        String encodedPassword = passwordEncoder.encode(dto.getMemberPw());
        System.out.println( "encodedPassword:" + encodedPassword );
        dto.setMemberPw( encodedPassword );
        dto.setMember_POINT(0);
        dto.setStatus("활동");

        //회원가입 DB 액션 수행
        try {
            Member entity = dto.toSaveEntity();
            memberListRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "<script>alert('이미 등록된 사용자입니다.');history.back();</script>";
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "<script>alert('회원가입 실패했습니다.');history.back();</script>";
        }

        HttpStatus status = HttpStatus.OK;
        if (status == HttpStatus.OK) {
            System.out.println("회원가입 성공!");
            return "<script>alert('회원가입 성공!'); location.href='/loginForm';</script>";
        } else {
            return "<script>alert('회원가입 실패'); history.back();</script>";
        }
    }



    @PostMapping("/checkPw")
    public String checkPw(HttpServletRequest request)  {
        Member entity = memberListRepository.findById((int) request.getSession().getAttribute("member_IDX")).get();
        request.getSession().setAttribute("memberEntity", entity);
        String realPw = entity.getMemberPw();
        String ppw = request.getParameter("memberPw");
        String encodedPassword = passwordEncoder.encode(ppw);
        boolean checkPw = passwordEncoder.matches(ppw, realPw);
        if (checkPw) {
            return "/client/user/Member/user-myinfo";
        }
        return "/client/user/Member/myorder-list-user";
    }

    @RequestMapping("/userModify")
    @ResponseBody
    public String modifyAction(MemberResponseDTO memberResponseDTO) {

        memberResponseDTO.setMember_SIGNUP(memberListRepository.findById(memberResponseDTO.getMember_IDX()).get().getJoinDate());
        try {
            String encodedPassword = passwordEncoder.encode(memberResponseDTO.getMemberPw());
            memberResponseDTO.setMemberPw( encodedPassword );
            Member entity = memberResponseDTO.toUpdateUserEntity();
            memberListRepository.save(entity);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "<script>alert('회원정보 수정 실패!'); history.back();</script>";
        }
        return "<script>alert('회원정보 수정 성공!!'); location.href='/myorder/list';</script>";
    }



    // 마이페이지 by 형민
    @GetMapping("/myorder/list")
    public String userMyOrderList(HttpSession session, Model model) {

        int memSession = (int) session.getAttribute("member_IDX");

        MemberResponseDTO mem = memberService.findByIDX(memSession);
        List<OrderResponseDto> dtoList = orderService.findOrderByMemberIDX(memSession); // 주문 정보. 해당 사용자가 주문한 모든 내역
        List<CouponResoponseDTO> couponList = couponService.findCouponByMemberIDX(memSession);
        List<OrderDetailTemp> orderTests = orderService.userMyOrderLogic(dtoList,couponList);
        OrdersStatus test = orderService.dtoListLogic(dtoList, orderTests);

        int status = dtoList.size();

        int refundCnt = 0;
        for(int i =0; i < orderTests.size(); i++) {
            refundCnt = refundCnt + orderTests.get(i).getRefundCnt();
        }

        model.addAttribute("status", status);
        model.addAttribute("order1", test.getNoPayCnt() );
        model.addAttribute("order2", test.getOrderReadyCnt() );
        model.addAttribute("order3", test.getOrderingCnt() );
        model.addAttribute("order4", test.getCompleteCnt() );
        model.addAttribute("order5", refundCnt);

        model.addAttribute("member", mem);
        model.addAttribute("point", mem.getMember_POINT());
        model.addAttribute("couponCnt", couponList.size());
        model.addAttribute("list", orderTests);

        return "/client/user/Member/myorder-list-user";     // loginForm.html로 응답
    }

    @GetMapping("/user/myinfoPswd")
    public String userMyinfoPswd() {
        return "/client/user/Member/user-myinfo-Pswd";     // loginForm.html로 응답
    }

    @GetMapping("/user/myinfo")
    public String userMyinfo() {
        return "/client/user/Member/user-myinfo";     // loginForm.html로 응답
    }


    // 비회원페이지 by 형민
    @PostMapping("/myorder-list")
    public String nonUserMyOrderList(Model model,HttpServletRequest request) {

        String name = request.getParameter("sender");
        String phone1 = request.getParameter("phone1");
        String phone2 = request.getParameter("phone2");
        String phone = phone1 + phone2;

        NonmemberResponseDto nonmember = nonmemberService.findNonmember(name, phone);
        List<OrderResponseDto> orderDto = nonmemberService.findOrderByNonMemberIDX(nonmember.getIdx());
        List<OrderDetailTemp> orderTests = nonmemberService.userMyOrderLogic(orderDto);
        OrdersStatus test = orderService.dtoListLogic(orderDto, orderTests);

        int status = orderDto.size();

        int refundCnt = 0;
        for(int i =0; i < orderTests.size(); i++) {
            refundCnt = refundCnt + orderTests.get(i).getRefundCnt();
        }

        model.addAttribute("member", name);
        model.addAttribute("status", status);
        model.addAttribute("order1", test.getNoPayCnt() );
        model.addAttribute("order2", test.getOrderReadyCnt() );
        model.addAttribute("order3", test.getOrderingCnt() );
        model.addAttribute("order4", test.getCompleteCnt() );
        model.addAttribute("order5", refundCnt);
        model.addAttribute("list", orderTests);
        return "/client/user/Nonmember/myorder-list";
    }

    @GetMapping("/myorder")
    public String nonUserMyorder() {
        return "/client/user/Nonmember/myorder";
    }

    @RequestMapping("/review/myList")
    public String myReviewList(Model model, HttpSession session){
        List<ReviewResponseDto> dtos = reviewService.findByMemId((String) session.getAttribute("memberID"));
        if(dtos.size()>0) {
            model.addAttribute("review", dtos);
        }
        return "/client/user/Member/review-mylist";
    }

    @RequestMapping("/findID")
    public String findID(@RequestParam("user_name") String name, @RequestParam("user_mobileNumber") String phone, Model model){
        String memberID = memberService.findID(name, phone);
        System.out.println(memberID);
        if(!memberID.equals("없음")){
            model.addAttribute("id",memberID);
        }
        return "/client/login/find-ID";
    }


    @RequestMapping("/findPW")
    @ResponseBody
    public String findPW(@RequestParam("email") String email, @RequestParam("id") String id){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        String generatedString = random.ints(leftLimit,rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        MemberResponseDTO dto = memberService.findByMail(email, id);
        String encodePW = passwordEncoder.encode(generatedString);
        dto.setMemberPw(encodePW);
        Member entity = dto.toUpdateUserEntity();
        memberListRepository.save(entity);
        sendMail(email, generatedString);
        return "alert('임시비밀번호가 발급되었습니다. 메일을 확인해주세요!'); location.href='/main';";
    }



    public void sendMail(String email, String PW) {
        String recipient = email;
        String code = PW;

        // 1. 발신자의 메일 계정과 비밀번호 설정
        final String user = "a9669579@gmail.com";
        final String password = "fhrmdls1541";

        // 2. Property에 SMTP 서버 정보 설정
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", 465);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        // 3. SMTP 서버정보와 사용자 정보를 기반으로 Session 클래스의 인스턴스 생성
        Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        // 4. Message 클래스의 객체를 사용하여 수신자와 내용, 제목의 메시지를 작성한다.
        // 5. Transport 클래스를 사용하여 작성한 메세지를 전달한다.

        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(user));

            // 수신자 메일 주소
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            // Subject
            message.setSubject("PLAYDDIT verification code");

            // Text
            message.setText("임시비밀번호를 발급합니다. 비밀번호는 ["+code+"]입니다.");

            Transport.send(message);    // send message

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}


