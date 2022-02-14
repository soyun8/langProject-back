package com.main.spring.config.oauth;

import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo{

	private Map<String, Object> attribute;
	
	public GoogleUserInfo(Map<String, Object> attribute) {
		this.attribute = attribute;
	}
	
	@Override
	public String getProviderId() {
		return (String)attribute.get("googleId");
	}

	@Override
	public String getProvider() {
		return "google";
	}

	@Override
	public String getEmail() {
		return (String)attribute.get("email");
	}

	@Override
	public String getName() {
		return (String)attribute.get("name");
	}
	

}
