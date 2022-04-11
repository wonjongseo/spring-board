package com.board.config.auth;


import com.board.config.CustomOAuth2UserService;
import com.board.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@RequiredArgsConstructor

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //must disable blow things to use h2-console screen
        http.csrf().disable()
                .headers().frameOptions().disable()
                //
                .and()
                // it is start point for admin per url
                // must declare authorizeRequests to use anyMatchers
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()

                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                // It is start point about OAuth2 login function
                .oauth2Login()
                // OAuth2 로그인 성공 이후 사용자 정보를 가져올 떄의 설정을 담당
                .userInfoEndpoint()
                // 소셜 로그인 성공 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록,
                // 리소스 서버 (소셜 서벼시) 에서 사용자 정보를 가져온 상태에서 추가로 진행하는 기능을 명시할 수 있음 .
                .userService(customOAuth2UserService);
    }
}
