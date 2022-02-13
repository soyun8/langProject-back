package com.main.spring.config.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MyFilter1 implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		// 내가만들어준 토큰을 고객이 가지고 있는지 없는지 이렇게 필터 걸어서 체크함
		// 토큰이 맞으면 필터에서 메인으로 넘기고, 아니면 다시 돌아가
		// 이 필터는 시큐리티 필터가 돌기전에 실행되야 한다. 무조건 

		/**
		 * 토큰 : cos 이걸 만들어줘야 한다. id와 pw가 들어와 로그인이 완료되면 토큰 생성 --> 응답
		 * 요청할때마다 header에 Authorization에 value값으로 토큰을 가지고 옴
		 * 이 토큰이 넘어올때마다 그냥 나는 검증만 하면 됨 (RSA, HS256)
		 * */

		if(req.getMethod().equals("POST")) {
			System.out.println("포스트 요청");
			String headerAuth = req.getHeader("Authorization");  // 받아오기
			System.out.println(headerAuth);

			if(headerAuth.equals("cos")) {
				chain.doFilter(req, res);
			}else {
				PrintWriter outPrintWriter = res.getWriter();
				outPrintWriter.println("인증xxx");
			}
		}
	}
}
