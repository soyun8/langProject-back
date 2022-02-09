package com.main.spring.config.oauth;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.main.spring.config.auth.PrincipalDetails;
import com.main.spring.config.oauth.GoogleUserInfo;
import com.main.spring.config.oauth.NaverUserInfo;
import com.main.spring.config.oauth.OAuth2UserInfo;
import com.main.spring.domain.User;
import com.main.spring.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
		public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
			OAuth2User oAuth2User = super.loadUser(userRequest);
			log.info("userRequest : " + oAuth2User.getAttributes()); // 프로필 정보 가져오기
			log.info("name=="+oAuth2User.getAttribute("name"));
			
			OAuth2UserInfo oAuth2UserInfo = null;
			if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
				System.out.println("구글 로그인 요청~~");
				oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
			} else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")){
				System.out.println("네이버 로그인 요청~~");
				oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
			} else {
				System.out.println("이것만 지원한다");
			}

			String provider = oAuth2UserInfo.getProvider(); // google
			String name = oAuth2UserInfo.getName();
			String providerId = oAuth2UserInfo.getProviderId();
			String username = provider+"_"+providerId;
			String password = "noting";
			String providerEmail = oAuth2UserInfo.getEmail();
			String role = "ROLE_USER";

			// 구글로 이미 회원가입이 되어 있다면 패스 아니라면 회원가입

			User user = userRepository.findByusername(username);
			
			if(user == null) {
				user = User.builder()
						.username(username)
						.password(password)
						.email(providerEmail)
						.name(name)
						.age(0)
						.provider(provider)
						.providerId(providerId)
						.role(role)
						.createDate(LocalDateTime.now())
						.build();
				userRepository.save(user);
			}
			return new PrincipalDetails(user,oAuth2User.getAttributes());
	}
}

