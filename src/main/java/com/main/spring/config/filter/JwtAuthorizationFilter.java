package com.main.spring.config.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.main.spring.config.auth.PrincipalDetails;
import com.main.spring.domain.User;
import com.main.spring.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter{

	private UserRepository userRepository;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
		super(authenticationManager);
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		log.info("인증/권한 필요");

		String jwtHeader = request.getHeader("Authorization");
		log.info("jwtHeader : "+jwtHeader);

		// header가 있는지 확인
		if(jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
			chain.doFilter(request, response);
			return;
		}

		// jwt 토큰을 검증해서 정상적인 사용자인지 확인

		// 토큰 검증 (이게 인증이기 때문에 AuthenticationManager도 필요 없음)
		// 내가 SecurityContext에 집적접근해서 세션을 만들때 자동으로 UserDetailsService에 있는 loadByUsername이 호출됨.
		String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
		String username = JWT.require(Algorithm.HMAC512("cos")).build().verify(jwtToken)
				.getClaim("username").asString();

		// 서명이 정상적으로 되었다면
		if(username != null) {	
			log.info("username 정상=============");
			User user = userRepository.findByusername(username);

			// 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해 
			// 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장!
			PrincipalDetails principalDetails = new PrincipalDetails(user);
			Authentication authentication =
					new UsernamePasswordAuthenticationToken(
							principalDetails, //나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
							null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
							principalDetails.getAuthorities());

			// 강제로 시큐리티의 세션에 접근하여 Authentication 객체 저장
			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(request, response);
		}
	}
}