package com.example.demo.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

//시간을 관리하는 클래스
@MappedSuperclass //JPA가 엔티티를 스캔할 때 생략

//이벤트를 감지하는 리스너 설정
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
public class BaseEntity {

	@CreatedDate // INSERT 시 자동 저장
	LocalDateTime regDate;

    @LastModifiedDate // UPDATE 시 자동 저장
    LocalDateTime modDate;
}
