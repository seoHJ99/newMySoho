package com.study.springboot;

import com.study.springboot.admin.dto.MemberResponseDTO;
import com.study.springboot.admin.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class Test {
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    public String sss(){
        MemberResponseDTO dto= memberService.findByIDX(9);
        System.out.println(passwordEncoder.matches("test2222@", dto.getMemberPw()));
        String en = passwordEncoder.encode(dto.getMemberPw());
        System.out.println(passwordEncoder.matches("test2222@", en));
        return "";
    }
}
