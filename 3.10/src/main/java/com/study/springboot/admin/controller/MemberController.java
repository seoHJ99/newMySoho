package com.study.springboot.admin.controller;




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
    private final MemberListRepository memberListRepository;
    private final CouponRepository couponRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/member")
    public String memDetailForm(int idx, Model model) throws Exception {
        Optional<Member> optional = memberListRepository.findById(idx);
        if (!optional.isPresent()) {
            throw new Exception("member id is wrong!");
        }
        Member list = optional.get();

        List<Coupon> couponEntity = couponRepository.findByMemberIDX(idx);
        List<CouponResoponseDTO> coupons = new ArrayList<>();
        for(Coupon entity : couponEntity){
            CouponResoponseDTO dto = new CouponResoponseDTO(entity);
            coupons.add(dto);
        }
        if(coupons.size()>0){
            model.addAttribute("coupons",coupons.size());
        }

        model.addAttribute("member", list);
        return "memDetailForm";
    }

    @RequestMapping("/modifyAction")
    @ResponseBody
    public String modifyAction(MemberResponseDTO memberResponseDTO) throws Exception {

        memberResponseDTO.setMember_SIGNUP(memberListRepository.findById(memberResponseDTO.getMember_IDX()).get().getJoinDate());
        try {
            String encodedPassword = passwordEncoder.encode(memberResponseDTO.getMemberPw());
            memberResponseDTO.setMemberPw( encodedPassword );
            if(memberResponseDTO.getStatus().equals("정지")){
                memberResponseDTO.setMember_ROLE("ROLE_DENIED") ;
            }
            Member entity = memberResponseDTO.toUpdateEntity();
            memberListRepository.save(entity);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "<script>alert('회원정보 수정 실패!'); history.back();</script>";
        }
        return "<script>alert('회원정보 수정 성공!!'); location.href='/admin/list/member';</script>";
    }
}