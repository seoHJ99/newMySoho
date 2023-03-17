package com.study.springboot.client.service;

import com.study.springboot.admin.dto.MemberResponseDTO;
import com.study.springboot.client.dto.MemberJoinDto;
import com.study.springboot.entity.Member;
import com.study.springboot.entity.MemberListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class Cl_MemberService {
    private final PasswordEncoder passwordEncoder;
    private MemberListRepository memberListRepository;

    @Transactional
    public int userSave(MemberJoinDto dto, BindingResult bindingResult){
        String encodedPassword = passwordEncoder.encode(dto.getMemberPw());
        dto.setMemberPw( encodedPassword );
        dto.setMember_POINT(0);
        dto.setStatus("활동");
        try {
            Member entity = dto.toSaveEntity();
            memberListRepository.save(entity);
            return 1;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return 2;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return 3;
        }
    }

    @Transactional(readOnly = true)
    public boolean checkEncodePw(int memberIDX, HttpServletRequest request){
        Member entity = memberListRepository.findById(memberIDX).get();
        String inputPw = request.getParameter("memberPw");
        String dbPw = entity.getMemberPw();
        boolean checkPw = passwordEncoder.matches(inputPw, dbPw);
        return checkPw;
    }

    @Transactional
    public int modifyMemberInPage(MemberResponseDTO memberResponseDTO){
        memberResponseDTO.setMember_SIGNUP(memberListRepository.findById(memberResponseDTO.getMember_IDX()).get().getJoinDate());
        try {
            String encodedPassword = passwordEncoder.encode(memberResponseDTO.getMemberPw());
            memberResponseDTO.setMemberPw( encodedPassword );
            Member entity = memberResponseDTO.toUpdateUserEntity();
            memberListRepository.save(entity);
            return 1;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return 2;
        }
    }

    public String createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(4);
            switch (index) {
                case 0: key.append((char) ((int) random.nextInt(26) + 97)); break;
                case 1: key.append((char) ((int) random.nextInt(26) + 65)); break;
                default: key.append(random.nextInt(9));
            }
        }
        return key.toString();
    }
}
