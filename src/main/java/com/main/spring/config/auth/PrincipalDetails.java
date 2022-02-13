package com.main.spring.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.main.spring.domain.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user;
    private Map<String, Object> attributes;

    // 생성자 만들기
    public PrincipalDetails(User user) {
        this.user = user;
    }
    
    // oauth 로그인 시 사용
 	public PrincipalDetails(User user, Map<String,Object> attributes) {
 		this.user = user;
 		this.attributes = attributes;
 	}

    /**
	 * 시큐리티가 /login 주소 요청 시 낚아채서 로그인을 진행시킨다.
	 * 
	 * UserDetails : 사용자의 정보를 담는 인터페이스 시큐리티엔 시큐리티 session이 따로 존재함 (Security ContextHolder)
	 * 로그인 요청이 오면 시큐리티가 가져가서 실행시킴 흐름 완료되면 security session 생성 --> Authentication 타입의 객체 --> 
	 * 이 안에는 user 정보가 있다. --> 이 user의 타입은 UserDetails 타입 객체임
	 * 
	 * */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {		// getAuthorities : 계정의 권한 목록 리턴
        ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        user.getRoleList().forEach(r->{
			auth.add(()->r);
		});
		return auth;
	}

    @Override
    public String getPassword() {			// getPassword 계정의 비밀번호를 리턴
        return user.getPassword();
    }

    @Override
    public String getUsername() {			// getUsername : 계정의 고유한 값을 리턴
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {		// isAccountNonExpired : 계정의 만료 여부 리턴 ( true일시 만료x )
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {		// isAccountNonLocked : 계정의 잠김 여부 리턴 ( true일시 잠김x )
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {		// isCredentialsNonExpired : 비밀번호 만료 여부 리턴 ( true일시 만료x )
        return true;
    }

    @Override
    public boolean isEnabled() {			// isEnabled : 계정의 활성화 여부 ( ture면 활성화 o )
        return true;
    }

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getName() {
		return null;
	}
}
