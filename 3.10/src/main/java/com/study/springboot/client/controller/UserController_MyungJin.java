package com.study.springboot.client.controller;

import com.study.springboot.admin.dto.*;
import com.study.springboot.admin.dto.ProductResponseDto;
import com.study.springboot.admin.service.OrderService;
import com.study.springboot.admin.service.QnaService;
import com.study.springboot.admin.service.ReviewService;
import com.study.springboot.client.dto.*;
import com.study.springboot.client.service.*;
import com.study.springboot.entity.*;
import com.study.springboot.admin.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
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
    private final EmailService emailService;
    private final QnaService qnaService;
    private final Cl_MemberService cl_MemberService;

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
        return "/client/login/userlogin";
    }


    @GetMapping("/logoutAction")
    @ResponseBody
    public String loguotAction(HttpServletRequest request) {
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
            String detail = bindingResult.getFieldError().getDefaultMessage();
            String bindResultCode = bindingResult.getFieldError().getCode();
            System.out.println(detail + ":" + bindResultCode);
            return "<script>alert('" + detail + "'); history.back();</script>";
        }
        int result = cl_MemberService.userSave(dto,bindingResult);
        if(result==2) {
            return "<script>alert('이미 등록된 사용자입니다.');history.back();</script>";
        } else if (result==3) {
            return "<script>alert('회원가입 실패했습니다.');history.back();</script>";
        }
        HttpStatus status = HttpStatus.OK;
        if (status == HttpStatus.OK) {
            return "<script>alert('회원가입 성공!'); location.href='/loginForm';</script>";
        } else {
            return "<script>alert('회원가입 실패'); history.back();</script>";
        }
    }



    @PostMapping("/checkPw")
    @ResponseBody
    public String checkPw(HttpServletRequest request)  {
        boolean checkPw = cl_MemberService.checkEncodePw((int) request.getSession().getAttribute("member_IDX"), request);
        Member entity = memberListRepository.findById((int) request.getSession().getAttribute("member_IDX")).get();
        request.getSession().setAttribute("memberEntity", entity);
        if (checkPw) {
            return "<script>location.href='/user/myinfo';</script>";
        } else {
            return "<script>alert('비밀번호가 일치하지 않습니다.'); history.back();</script>";
        }
    }

    @RequestMapping("/userModify")
    @ResponseBody
    public String modifyAction(MemberResponseDTO memberResponseDTO) {
        int modifyResult = cl_MemberService.modifyMemberInPage(memberResponseDTO);
        if(modifyResult == 1) {
            return "<script>alert('회원정보 수정 실패!'); history.back();</script>";
        }else {
            return "<script>alert('회원정보 수정 성공!!'); location.href='/myorder/list';</script>";
        }
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
        String phone = request.getParameter("phone1") + request.getParameter("phone2");
        NonmemberResponseDto nonmember = nonmemberService.findNonmember(name, phone);
        if(nonmember == null){
            return "회원정보 없음 페이지";
        }
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
    public String findID(@RequestParam("user_name") String name, @RequestParam("user_mobileNumber") String phone, Model model) {
        String memberID = memberService.findID(name, phone);
        System.out.println(memberID);
        if (!memberID.equals("없음")) {
            model.addAttribute("id", memberID);
        }
        return "/client/login/find-ID";
    }

    // 임시 비밀번호 발급
    

    @PostMapping("/findPW")
    public String sendPasswordMail(@RequestParam("email") String mail,
                                   @RequestParam("id")String id) {
        EmailMessage emailMessage = EmailMessage.builder()
                .to(mail)
                .subject("임시 비밀번호 발급")
                .build();
        String pw = cl_MemberService.createCode();
        MemberResponseDTO dto = memberService.findByMail(mail, id);
        if(dto == null){
            return "/client/login/noID";
        }else {
            emailService.sendMail(emailMessage, pw);
            dto.setMemberPw(passwordEncoder.encode(pw));
            Member member = dto.toUpdateEntity();
            memberListRepository.save(member);
            return "redirect:/main";
        }
    }

    @RequestMapping("/qna/myList/product")
    public String myQnAList(HttpSession session, Model model){
        List<QnaResponseDto> qnaResponseDtos = qnaService.findByMemberId((int)session.getAttribute("member_IDX"));
        List<ProductQnaDto> productQnaDtos = new ArrayList<>();
        for(QnaResponseDto dto : qnaResponseDtos){
            if(dto.getQna_CATE().equals("상품")){
                ProductQnaDto dto2 = new ProductQnaDto(qnaService.findProductConnected(dto.getItem_IDX()), dto);
                productQnaDtos.add(dto2);
            }
        }
        model.addAttribute("dto", productQnaDtos);
        return "/client/user/Member/myProductQnA";
    }

    @RequestMapping("/qna/myList")
    public String myQnAListProduct(HttpSession session, Model model){
        List<QnaResponseDto> qnaResponseDtos = qnaService.findByMemberId((int)session.getAttribute("member_IDX"));
        List<QnaResponseDto> allQna = new ArrayList<>();
        for(QnaResponseDto dto : qnaResponseDtos){
            if(dto.getQna_CATE().equals("게시판")){
                allQna.add(dto);
            }
        }
        model.addAttribute("allQNA", allQna);
        return "/client/user/Member/qna-user";
    }
}