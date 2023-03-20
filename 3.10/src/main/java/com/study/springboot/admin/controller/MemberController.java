package com.study.springboot.admin.controller;




import com.study.springboot.admin.service.MemberService;
import com.study.springboot.entity.Coupon;
import com.study.springboot.entity.CouponRepository;
import com.study.springboot.entity.Member;
import com.study.springboot.entity.MemberListRepository;
import com.study.springboot.admin.dto.CouponResoponseDTO;
import com.study.springboot.admin.dto.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class MemberController {
    private final CouponRepository couponRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    @GetMapping("/member")
    public String memDetailForm(int idx, Model model) throws Exception {
        MemberResponseDTO dto = memberService.findByIDX(idx);
        List<Coupon> couponEntity = couponRepository.findByMemberIDX(idx);
        List<CouponResoponseDTO> coupons = new ArrayList<>();
        for(Coupon entity : couponEntity){
            CouponResoponseDTO couponDto = new CouponResoponseDTO(entity);
            coupons.add(couponDto);
        }
        if(coupons.size()>0){
            model.addAttribute("coupons",coupons.size());
        }

        model.addAttribute("member", dto);
        return "memDetailForm";
    }

    @RequestMapping("/modifyAction")
    @ResponseBody
    public String modifyAction(MemberResponseDTO memberResponseDTO) throws Exception {
        memberResponseDTO.setMember_SIGNUP(memberService.findByIDX(memberResponseDTO.getMember_IDX()).getMember_SIGNUP());
        try {
            String encodedPassword = passwordEncoder.encode(memberResponseDTO.getMemberPw());
            memberResponseDTO.setMemberPw( encodedPassword );
            if(memberResponseDTO.getStatus().equals("정지")){
                memberResponseDTO.setMember_ROLE("ROLE_DENIED") ;
            }
            memberService.changeMemberInfo(memberResponseDTO);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "<script>alert('회원정보 수정 실패!'); history.back();</script>";
        }

        return "<script>alert('회원정보 수정 성공!!'); location.href='/admin/list/member';</script>";
    }
}