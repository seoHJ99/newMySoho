package com.study.springboot.other.config;

import com.study.springboot.admin.service.SecurityService;
import com.study.springboot.entity.Member;
import com.study.springboot.entity.MemberListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity //웹보안 활성화를위한 annotation
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    final private SecurityService securityService;
    final private MemberListRepository memberListRepository;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //csrf 보안을 비활성화 한다.
        //http.csrf().disable();
        //csrf 보안을 쿠키  한다.
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        http
                .authorizeRequests()// 요청에 의한 인증 시작
                //permitAll()는 모든 사용자가 접근할 수 있다는 것을 의미합니다.
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/joinForm").permitAll()
                .antMatchers("/joinAction").permitAll()
                .antMatchers("/main").permitAll()
                .antMatchers("/templates/**").permitAll()
                .antMatchers("/admin/**").access("hasRole('ADMIN')")
                //.anyRequest().permitAll()
                .and()
                .formLogin() //인증은 formLogin방식으로 하겠다.
                .loginPage("/loginForm") //로그인 페이지를 /loginForm URL로 하겠다.
                .loginProcessingUrl("/loginAction") //로그인 즉 인증 처리를 하는 URL을 설정합니다.
                .defaultSuccessUrl("/main")//인증성공 했을 경우 이동하는 페이지를 설정합니다.
                .usernameParameter("memberID")
                .passwordParameter("memberPw")
                //인증성공 후 별도의 처리가 필요한경우 커스텀 핸들러를 생성하여 등록할 수 있습니다.
                .successHandler((request, response, authentication) -> {
                    response.sendRedirect("/main");
                    List<Member> list = memberListRepository.findByUserLoginId(request.getParameter("memberID"));
                    Member entity = list.get(0);
                    request.getSession().setAttribute("memberEntity", entity);
                    request.getSession().setAttribute("memberID", request.getParameter("memberID"));
                    request.getSession().setAttribute("memberPw", request.getParameter("memberPw"));
                    request.getSession().setAttribute("member_IDX", entity.getMember_IDX());
                    request.getSession().setAttribute("member_NAME", entity.getMember_NAME());
                })
                //인증이 실패 했을 경우 이동하는 페이지를 설정합니다.
                .failureUrl("/loginForm?error")
                .permitAll() //로그인 페이지를 모두에게 허용한다.
                //로그아웃
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logoutAction"))
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/main");
    }

    //BCrypt 암호화 엔코더 빈 생성
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //UserDetailsService 인터페이스 구현체를 설정한다.
    // - 내부의 loadUserByUsername 메소드를 통해, 로그인 인증결과를 가져온다.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityService).passwordEncoder(passwordEncoder());
    }

}