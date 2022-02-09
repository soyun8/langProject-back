package com.main.spring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "user")
public class User extends AuditingEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

 	@Column(unique = true, nullable = false)		// 아이디
    private String username;
 	
    private String password;
	
	@Column(unique = true)						// 이메일
    private String email;
	
    private String name;
	
	private int age;
	
    private String role;

	@Column(name = "provider_id")					// 아이디 등록
    private String providerId;
	
    private String provider;

	@Builder
	public User(String username, String password, String email, String name, int age, String provider, String providerId, String role, LocalDateTime createDate) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
		this.age = age;
		this.provider = provider;
		this.providerId = providerId;
		this.role = role;
		this.createDate = createDate;
	}
}
