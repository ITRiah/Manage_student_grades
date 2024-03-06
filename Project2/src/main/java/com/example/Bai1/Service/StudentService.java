package com.example.Bai1.Service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.Bai1.dto.SearchDTO;
import com.example.Bai1.dto.StudentDTO;
import com.example.Bai1.entity.Student;
import com.example.Bai1.repository.StudentRepo;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

@Service
public class StudentService {
	@Autowired
	StudentRepo studentRepo;

	public Page<StudentDTO> getAll(SearchDTO searchDTO) {
		if (searchDTO.getCurrentPage() == null)
			searchDTO.setCurrentPage(0);
		if (searchDTO.getSize() == null)
			searchDTO.setSize(3);

		Sort sort = Sort.by("userId").ascending();

		PageRequest pageRequest = PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize());
		Page<Student> page = studentRepo.findAll(pageRequest);
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		Page<StudentDTO> page2 = page.map(student -> mapper.map(student, StudentDTO.class));		
		
		return page2;
	}
	
	@Transactional
	public void add(StudentDTO studentDTO) {
		Student student = new ModelMapper().map(studentDTO, Student.class);
		studentRepo.save(student);
	}
	
	public StudentDTO getById(int id) {
		
		Student student = studentRepo.getById(id);
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		if(student != null) {
			StudentDTO studentDTO = mapper.map(student, StudentDTO.class);
			return studentDTO;
		}
		return null;
	}
	
	public void update(StudentDTO studentDTO) {
		Student student = studentRepo.findById(studentDTO.getUser().getId()).orElseThrow(NoResultException::new);
		
		if(studentDTO.getStudentCode() != null) {
			student.setStudentCode(studentDTO.getStudentCode());
			studentRepo.save(student);
		}
	}
	
	public void delete(int id) {
			studentRepo.deleteById(id);
	}
}
