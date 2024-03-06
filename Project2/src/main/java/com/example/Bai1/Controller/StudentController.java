package com.example.Bai1.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Bai1.Service.StudentService;
import com.example.Bai1.dto.ResponseDTO;
import com.example.Bai1.dto.SearchDTO;
import com.example.Bai1.dto.StudentDTO;

@RestController
@RequestMapping("/student")
public class StudentController {
	@Autowired
	StudentService studentService;
	
	@GetMapping("/list")
	public ResponseDTO<Page<StudentDTO>> getList(@ModelAttribute SearchDTO searchDTO){
		Page<StudentDTO> page = studentService.getAll(searchDTO);
		return ResponseDTO.<Page<StudentDTO>>builder()
					.status(200)
					.msg("done")
					.data(page)
					.build();
	}
	
	@GetMapping("/")
	public ResponseDTO<StudentDTO> getByID(@RequestParam("id") int id){
		StudentDTO studentDTO = studentService.getById(id);
		return ResponseDTO.<StudentDTO>builder()
				.status(200)
				.msg("done")
				.data(studentDTO)
				.build();
	}
	
	@PostMapping("/")
	public ResponseDTO<Void> add(@RequestBody StudentDTO studentDTO){
		studentService.add(studentDTO);
		return ResponseDTO.<Void>builder()
			.status(200)
			.msg("done")
			.build();
	}
	
	@PutMapping("/")
	public ResponseDTO<StudentDTO> update(@RequestBody StudentDTO studentDTO){
		studentService.update(studentDTO);
		
		return ResponseDTO.<StudentDTO>builder()
				.status(200)
				.msg("done")
				.data(studentDTO)
				.build();
	}
	
	@DeleteMapping("/")
	public ResponseDTO<Void> add(@RequestParam("id") int id){
		studentService.delete(id);
		return ResponseDTO.<Void>builder()
			.status(200)
			.msg("done")
			.build();
	}
	
}
