package com.example.Bai1.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;

@Entity
@Data
public class Student {
	@Id
	private int userId; // là userId (vì quan hệ one to one sẽ sinh ra cột user_id nên để studentId = userId để chỉ xuất hiện 1 cột userId)
	private String studentCode;
	
	@OneToOne(cascade = CascadeType.ALL)// nếu không có cascade thì phải tạo user -> tạo student
    @PrimaryKeyJoinColumn
    @MapsId //copy id của user set cho student
	private User user;
}
