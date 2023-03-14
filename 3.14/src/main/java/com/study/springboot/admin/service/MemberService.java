package com.study.springboot.admin.service;


import com.study.springboot.admin.dto.MemberResponseDTO;
import com.study.springboot.entity.Member;
import com.study.springboot.entity.MemberListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberListRepository memberRepository;

    public MemberResponseDTO findByIDX(int idx){
        Member entity = memberRepository.findById(idx).get();
        MemberResponseDTO dto = new MemberResponseDTO(entity);
        return dto;
    }

    public void changeMemberInfo(MemberResponseDTO dto){
        memberRepository.save( dto.toUpdateEntity());
    }

    public String checkMemberId(String id){
        if(memberRepository.findByMemberID(id).isPresent()){
            return "0";
        }else {
            return "1";
        }
    }
}