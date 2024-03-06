package com.example.Bai1.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) //gen ngày tạo

public abstract class TimeAuditable {
	@CreatedDate
	@Column(updatable = false)
	private Date createAt;
	
	@LastModifiedDate
	private Date updateAt;
}
