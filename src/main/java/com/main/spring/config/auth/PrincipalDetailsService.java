package com.main.spring.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.main.spring.domain.User;
import com.main.spring.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
	
	/**
	 * AuthenticationProvider는 UserDetailsService에게 조회할 username을 전달하여 인증을 위한 UserDetails(DB 사용자 정보) 객체를 요청한다.
	 * .loginProcessingUrl("/login") 이 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행
	 * method : loadUserByUsername : 유저 정보를 불러오는 메서드, CustomUserDetails 형으로 사용자의 정보를 가져옴
	 * */

	/**
	 * 1. AuthenticationProvider는 UserDetailsService에게 조회할 username을 전달하여 인증을 위한 UserDetails(DB 사용자 정보) 객체를 요청한다.
	 * 2. UserDetailsService는 AuthenticationProvider에게 전달 받은 username을 통해 UserDetails(DB 사용자 정보)를 찾는다.
	 * 3. UserDetailsService는 username을 통해 찾은 UserDetails(DB 사용자 정보)를 반환한다.
	 * 결론 : 시큐리티 세션(내부 Authentication(내부 UserDetails))
	 * */

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("=====username======"+username);
        User user = userRepository.findByusername(username);
        if (user != null) {
            return new PrincipalDetails(user);
        }
        return null;
    }
}
