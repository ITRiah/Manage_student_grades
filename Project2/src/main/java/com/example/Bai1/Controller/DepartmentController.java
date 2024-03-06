package com.example.Bai1.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Bai1.Service.DepartmentService;
import com.example.Bai1.dto.DepartmentDTO;
import com.example.Bai1.dto.ResponseDTO;
import com.example.Bai1.dto.SearchDTO;

import jakarta.validation.Valid;

@RestController //chuẩn Rest
@RequestMapping("/department")
public class DepartmentController {
	@Autowired
	DepartmentService departmentService;
	
	@GetMapping("/list")
	public ResponseDTO<Page<DepartmentDTO>> getList(@ModelAttribute SearchDTO searchDTO) {
		Page<DepartmentDTO> list = departmentService.getAll(searchDTO);
		return ResponseDTO.<Page<DepartmentDTO>>builder()
					.status(200)
					.msg("done")
					.data(list)
					.build();
	}
	
	@GetMapping("/")
	public ResponseDTO<DepartmentDTO> getById(@RequestParam("id") int id) {
		DepartmentDTO departmentDTO = departmentService.getById(id);
		return ResponseDTO.<DepartmentDTO>builder()
				.status(200)
				.msg("done")
				.data(departmentDTO)
				.build();
	}
	

	@PostMapping("/new")
	public ResponseDTO<Void> add( //dùng responseEntity để tùy biến trạng thái trả về 
			@ModelAttribute("departmentDTO") @Valid DepartmentDTO departmentDTO) {
			departmentService.create(departmentDTO);
			return ResponseDTO.<Void>builder()
					.status(200)
					.msg("done")
					.build();
	}
	
	
	@PutMapping("/update")
	public ResponseDTO<DepartmentDTO> update(@ModelAttribute("departmentDTO")@Valid DepartmentDTO departmentDTO) {
		departmentService.update(departmentDTO);
		return  ResponseDTO.<DepartmentDTO>builder()
				.status(200)
				.msg("done")
				.data(departmentDTO)
				.build();
	}
	
	@DeleteMapping("/delete")
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		departmentService.delete(id);
		return ResponseDTO.<Void>builder()
			.status(200)
			.msg("done")
			.build();
	}
	
	@PostMapping("/search")
	public ResponseDTO<List<DepartmentDTO>> search(@RequestParam("name")String name) {
		List<DepartmentDTO> departmentDTO = departmentService.search(name);
		return ResponseDTO.<List<DepartmentDTO>>builder()
			.status(200)
			.msg("done")
			.data(departmentDTO)
			.build();
	}
}
