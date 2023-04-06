package com.study.springboot.admin.service;

import com.study.springboot.entity.Member;
import com.study.springboot.entity.MemberListRepository;
import com.study.springboot.other.enumeration.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SecurityService implements UserDetailsService {
    private final MemberListRepository memberListRepository;

    //사용자 아이디를 통해, 사용자 정보와 권한을 스프링시큐리티에 전달한다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 실DB를 통해서 사용자 정보를 시큐리티에 전달한다.
        Optional<Member> _optMember = this.memberListRepository.findByUserId(username);
        if (_optMember.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        Member member = _optMember.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        return new User(member.getMemberID(), member.getMemberPw(), authorities);
    }
}
