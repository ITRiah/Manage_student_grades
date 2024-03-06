package com.example.Bai1.dto;

import lombok.Data;

@Data
public class StudentDTO {
	private UserDTO user; // là userId (vì quan hệ one to one sẽ sinh ra cột user_id nên để studentId = userId để chỉ xuất hiện 1 cột userId)
	private String studentCode;
}
