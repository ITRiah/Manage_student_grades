package com.example.Bai1.dto;

import lombok.Data;

@Data
public class SearchDTO {
	private String keyword;
	private Integer currentPage;
	private Integer size;	
}
