package com.example.Bai1.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.Bai1.dto.ScoreDTO;
import com.example.Bai1.dto.SearchDTO;
import com.example.Bai1.dto.SearchScoreDTO;
import com.example.Bai1.dto.avgScore;
import com.example.Bai1.entity.Course;
import com.example.Bai1.entity.Score;
import com.example.Bai1.entity.Student;
import com.example.Bai1.repository.CourseRepo;
import com.example.Bai1.repository.ScoreRepo;
import com.example.Bai1.repository.StudentRepo;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

@Service
public class ScoreService {
	@Autowired
	ScoreRepo scoreRepo;
	
	@Autowired
	StudentRepo studentRepo;
	
	@Autowired
	CourseRepo courseRepo;

	public Page<ScoreDTO> getAll(SearchDTO listUserDTO) {
		if (listUserDTO.getCurrentPage() == null)
			listUserDTO.setCurrentPage(0);
		if (listUserDTO.getSize() == null)
			listUserDTO.setSize(3);

		PageRequest pageRequest = PageRequest.of(listUserDTO.getCurrentPage(), listUserDTO.getSize());
		Page<Score> page = scoreRepo.findAll(pageRequest);

		Page<ScoreDTO> page2 = page.map(Score -> new ModelMapper().map(Score, ScoreDTO.class));
		return page2;
	}
	
	@Transactional
	public void create(ScoreDTO ScoreDTO) {
		if (!scoreRepo.findById(ScoreDTO.getId()).isPresent()) {
			Score Score = new ModelMapper().map(ScoreDTO, Score.class);
			scoreRepo.save(Score);
		}
	}

	@Transactional
	public void update(ScoreDTO scoreDTO) {
		Score current = scoreRepo.findById(scoreDTO.getId()).orElseThrow(NoResultException::new);
		current.setScore(scoreDTO.getScore());
		
		Student student = studentRepo.findById(scoreDTO.getStudent().getUser().getId()).orElseThrow(NoResultException::new);
		current.setStudent(student);
		
		Course course = courseRepo.findById(scoreDTO.getCourse().getId()).orElseThrow(NoResultException::new);
		current.setCourse(course);		
		
		scoreRepo.save(current);
	}

	public void delete(int id) {
		scoreRepo.deleteById(id);
	}

	public ScoreDTO getById(int id) {
		Score Score = scoreRepo.getById(id);
		if (Score != null) {
			ScoreDTO ScoreDTO = new ModelMapper().map(Score, ScoreDTO.class);
			return ScoreDTO;
		}
		return null;
	}

	public List<ScoreDTO> search(SearchScoreDTO searchScoreDTO) {
		Sort sortBy = Sort.by("id").ascending();
		
		List<Score> scores = scoreRepo.findAll();
		
		if(searchScoreDTO.getCourseId() != null)
			scores = scoreRepo.searchByCourse(searchScoreDTO.getCourseId());
		if(searchScoreDTO.getStudentId() != null)
			 scores = scoreRepo.searchByStudent(searchScoreDTO.getStudentId());

		List<ScoreDTO> scoreDTOs = scores.stream()
				.map(Score -> new ModelMapper().map(Score, ScoreDTO.class)).collect(Collectors.toList());
		
		return scoreDTOs;
	}
	
	//Thống kê
	public List<avgScore> avgScoresByCourse(){
		return scoreRepo.avgScoreByCourse();
	}
}
