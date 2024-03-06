package com.example.Bai1.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Bai1.Service.CourseService;
import com.example.Bai1.dto.CourseDTO;
import com.example.Bai1.dto.DepartmentDTO;
import com.example.Bai1.dto.ResponseDTO;
import com.example.Bai1.dto.SearchDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/course")
public class CourseController {
	@Autowired
	CourseService courseService;
	
	@GetMapping("/list")
	public ResponseDTO<Page<CourseDTO>> getList(@ModelAttribute SearchDTO searchDTO) {
		Page<CourseDTO> list = courseService.getAll(searchDTO);
		return ResponseDTO.<Page<CourseDTO>>builder()
					.status(200)
					.msg("done")
					.data(list)
					.build();
	}
	
	@GetMapping("/")
	public ResponseDTO<CourseDTO> getById(@RequestParam("id") int id) {
		CourseDTO departmentDTO = courseService.getById(id);
		return ResponseDTO.<CourseDTO>builder()
				.status(200)
				.msg("done")
				.data(departmentDTO)
				.build();
	}
	

	@PostMapping("/")
	public ResponseDTO<Void> add( //dùng responseEntity để tùy biến trạng thái trả về 
			@ModelAttribute @Valid CourseDTO departmentDTO) {
			courseService.create(departmentDTO);
			return ResponseDTO.<Void>builder()
					.status(200)
					.msg("done")
					.build();
	}
	
	
	@PutMapping("/")
	public ResponseDTO<CourseDTO> update(@ModelAttribute @Valid CourseDTO departmentDTO) {
		courseService.update(departmentDTO);
		return  ResponseDTO.<CourseDTO>builder()
				.status(200)
				.msg("done")
				.data(departmentDTO)
				.build();
	}
	
	@DeleteMapping("/delete")
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		courseService.delete(id);
		return ResponseDTO.<Void>builder()
			.status(200)
			.msg("done")
			.build();
	}
	
	@PostMapping("/search")
	public ResponseDTO<List<CourseDTO>> search( @RequestParam("name")String name) {
		List<CourseDTO> departmentDTO = courseService.search(name);
		return ResponseDTO.<List<CourseDTO>>builder()
			.status(200)
			.msg("done")
			.data(departmentDTO)
			.build();
	}
	
}
