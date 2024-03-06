package com.example.Bai1.Controller;

import java.util.List;

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

import com.example.Bai1.Service.ScoreService;
import com.example.Bai1.dto.ResponseDTO;
import com.example.Bai1.dto.ScoreDTO;
import com.example.Bai1.dto.SearchDTO;
import com.example.Bai1.dto.SearchScoreDTO;
import com.example.Bai1.dto.avgScore;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/score")
public class ScoreController {
	@Autowired
	ScoreService scoreService;
	
	@GetMapping("/list")
	public ResponseDTO<Page<ScoreDTO>> getList(@ModelAttribute SearchDTO searchDTO) {
		Page<ScoreDTO> list = scoreService.getAll(searchDTO);
		return ResponseDTO.<Page<ScoreDTO>>builder()
					.status(200)
					.msg("done")
					.data(list)
					.build();
	}
	
	@GetMapping("/")
	public ResponseDTO<ScoreDTO> getById(@RequestParam("id") int id) {
		ScoreDTO departmentDTO = scoreService.getById(id);
		return ResponseDTO.<ScoreDTO>builder()
				.status(200)
				.msg("done")
				.data(departmentDTO)
				.build();
	}
	

	@PostMapping("/")
	public ResponseDTO<Void> add( //dùng responseEntity để tùy biến trạng thái trả về 
			@RequestBody @Valid ScoreDTO departmentDTO) {
			scoreService.create(departmentDTO);
			return ResponseDTO.<Void>builder()
					.status(200)
					.msg("done")
					.build();
	}
	
	
	@PutMapping("/")
	public ResponseDTO<ScoreDTO> update(@ModelAttribute @Valid ScoreDTO departmentDTO) {
		scoreService.update(departmentDTO);
		return  ResponseDTO.<ScoreDTO>builder()
				.status(200)
				.msg("done")
				.data(departmentDTO)
				.build();
	}
	
	@DeleteMapping("/delete")
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		scoreService.delete(id);
		return ResponseDTO.<Void>builder()
			.status(200)
			.msg("done")
			.build();
	}
	
	@PostMapping("/search")
	public ResponseDTO<List<ScoreDTO>> search(@ModelAttribute SearchScoreDTO searchScoreDTO) {
		List<ScoreDTO> departmentDTO = scoreService.search(searchScoreDTO);
		return ResponseDTO.<List<ScoreDTO>>builder()
			.status(200)
			.msg("done")
			.data(departmentDTO)
			.build();
	}
	
	@GetMapping("/agv-score-by-course")
	public ResponseDTO<List<avgScore>> searchByCourse() {
		List<avgScore> departmentDTO = null;
		departmentDTO = scoreService.avgScoresByCourse();
		
		return ResponseDTO.<List<avgScore>>builder()
			.status(200)
			.msg("done")
			.data(departmentDTO)
			.build();
	}
	
}
