package com.main.spring.config.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.spring.config.auth.PrincipalDetails;
import com.main.spring.user.dto.LoginRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private final AuthenticationManager authenticationManager;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("로그인 시도중");
		ObjectMapper om = new ObjectMapper();		// JSON 형식을 사용할 때, 응답들을 직렬화(Object -> String 문자열)하고 요청들을 역직렬화(String 문자열 -> Object) 할 때 사용하는 기술
		LoginRequestDto user = null;
		try {
			user = om.readValue(request.getInputStream(), LoginRequestDto.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);
		
		UsernamePasswordAuthenticationToken authenticationToken = 
				new UsernamePasswordAuthenticationToken(
						user.getUsername(), 
						user.getPassword());
		
		Authentication authentication = 
				authenticationManager.authenticate(authenticationToken);
		
		PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("로그인이 잘 되면 뜸 "+principalDetailis.getUser().getUsername());
		return authentication; // 로그인이 잘 되면 세션에 저장이 됨
		
		}
		
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("successfulAuthentication 실행됨 : 즉 인증완료됨");
		PrincipalDetails principalDetailis = (PrincipalDetails) authResult.getPrincipal();

		// build.gradle에 implementation 'com.auth0:java-jwt:3.18.3' 추가
		// RSA 방식은 아니고 Hash 암호방식이다. (이걸 많이 쓰더라)
		String jwtToken = JWT.create()
				.withSubject("cos토큰") // 의미없다. 아무거나 쓰자
				.withSubject(principalDetailis.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()+(60000*10)))	// 만료시간 (10분으로 해놓음)
				.withClaim("id", principalDetailis.getUser().getId())
				.withClaim("username", principalDetailis.getUser().getUsername())
				.sign(Algorithm.HMAC512("cos"));	// 내가 정한 고유값

		// 베리어 부분에 한칸 꼭 띄우기
		response.addHeader("Authorization", "Bearer "+jwtToken);
	}
}