package com.main.spring.user.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.main.spring.config.auth.PrincipalDetails;
import com.main.spring.domain.User;
import com.main.spring.user.dto.UserRequestSaveDTO;
import com.main.spring.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j	// 로그를 찍기 위함
@RequiredArgsConstructor
@Controller	// 머스테치를 사용하기 위함(view 사용)
public class UserController {

	/** @ResponseBody 
	 * 자바 객체를 HTTP 응답 몸체로 전송함
	 * 자바 객체를 HTTP 요청의 body 내용으로 매핑하는 역할
	 * */
	
	private final UserService userService;
	
	@GetMapping({ "", "/" })
	public @ResponseBody String index() {
		return "메인 페이지";
	}
	
	@GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println("prin"+principalDetails.getUser());
		return "user";
	}
	
	@GetMapping("/user/myPage")
	public @ResponseBody String myPage() {
		return "myPage";
	}

	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}

	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}

	@GetMapping("/login")
	public @ResponseBody String login() {
		return "login";
	}

	 // user 회원가입
    @PostMapping("/join")
    public String userJoin (UserRequestSaveDTO userRequestDTO) {
        log.info("=====회원가입====="+userRequestDTO);
        User user = this.userService.userJoin(userRequestDTO);
        ResponseEntity.ok(user);
        return "redirect:/loginForm";
    }
}
