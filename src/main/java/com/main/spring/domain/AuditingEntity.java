package com.main.spring.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

/** 공통 파일 생성
 * 	id, 생성일, 수정일이 들어간다.
 * */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) 
public class AuditingEntity {

	
	 	@CreatedDate
	    @Column(updatable = false, name = "create_date")
		protected LocalDateTime createDate;

	    @LastModifiedDate
		@Column(name = "update_date")
		protected LocalDateTime updateDate;
}
