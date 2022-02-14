package com.main.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;

import com.main.spring.config.filter.JwtAuthenticationFilter;
import com.main.spring.config.filter.JwtAuthorizationFilter;
import com.main.spring.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/** 시큐리티 설정을 위한 클래스
 *  애플리케이션의 보안(인증과 권한, 인가 등)을 담당하는 스프링 하위 프레임워크
 *  인증 : 본인이 맞는지 확인
 *  인가 : 인증된 자가 요청한 이곳에 접근 가능한지
 */
@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity  // 스프링 시큐리티 필터가 필터체인에 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final UserRepository userRepository;
	
	private final CorsFilter corsFilter;
    @Bean
    // class : BCryptPasswordEncoder 비밀번호를 암호화 하는 메서드
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	http.csrf().disable();			// csrf를 활성화 시키면 post를 사용할 수 없다.
    	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 더이상 세션을 안 쓰겠다.
			.and()
			.formLogin().disable() // 폼로그인 안 쓰겠다.
			.httpBasic().disable()
			.addFilter(new JwtAuthenticationFilter(authenticationManager()))
			.addFilter(new JwtAuthorizationFilter(authenticationManager(),userRepository))
			.addFilter(corsFilter); // cors 방지
		http.authorizeRequests()		// 권한 부여
			.antMatchers("/user/**").authenticated()	// user 루트 밑으로 오는 요청은 인증이 필요하다.
			// admin에 대한 요청은 전부, role이 ADMIN인 유저만이 사용가능하다.
			// 인증이 안될경우 403 에러가 뜸
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll();		// 나머지 경로는 다른 요청 전부 허용
			
		/*	// 로그인 페이지 꾸며서 사용할 수 있게 해줌
			.and()
			.formLogin()
			.loginPage("/loginForm")
			// login 주소 호출 시 시큐리티가 낚아채서 대신 로그인해줌( 컨트롤러로 /login 안만들어줘도 됨)
			.loginProcessingUrl("/login")
			// 로그인 성공시 이동할 페이지, 너가 /admin 으로 이동을 했는데 로그인 페이지가 떴어? 그리고나서 로그인을 하고나면 /로 가는게 아니라 자동으로 너가 가려했던 /admin으로 들어가짐 
			.defaultSuccessUrl("/")
		
			//oauth 로 구글 로그인 실행
			.and()
			.oauth2Login()
			.userInfoEndpoint()
			.userService(pOauth2UserService);
		*/
    }
}
