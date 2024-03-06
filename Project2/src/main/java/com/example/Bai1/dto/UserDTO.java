package com.example.Bai1.dto;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {
	private int id;
	@NotBlank(message = "Không được để trống")
	private String name;
	private int age;
	private String avatarURL;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
	private Date birthdate;
	private String email;
	
	private String username;
	private String password;
	
	@JsonIgnore // nếu không có dữ liệu không hiển thị
	private MultipartFile file;
	
	private DepartmentDTO department;
	private List<String> roles;

	
	public UserDTO(int id) {
		this.id = id;
	}
	
	public UserDTO() {
		
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		return id == other.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
