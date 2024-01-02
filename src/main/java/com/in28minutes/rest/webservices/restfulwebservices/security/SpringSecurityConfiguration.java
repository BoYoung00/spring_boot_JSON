package com.in28minutes.rest.webservices.restfulwebservices.security;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 하나 이상의 빈 생성될 클래스
public class SpringSecurityConfiguration {

    @Bean // 보안 필터 체인 설정정
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 필터체인 추가 로직

        // 들어오는 요청 모두 인증
        http.authorizeHttpRequests(
                auth -> auth.anyRequest().authenticated()
        );
        // http 기본 인증 설정
        http.httpBasic(withDefaults());
        // POST, PUT 관리
        http.csrf().disable(); // 보호 비활성화



        return http.build();
    }
}
