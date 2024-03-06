package com.example.Bai1.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.Bai1.dto.CourseDTO;
import com.example.Bai1.dto.SearchDTO;
import com.example.Bai1.entity.Course;
import com.example.Bai1.repository.CourseRepo;

import jakarta.persistence.NoResultException;

@Service
public class CourseService {
	@Autowired
	CourseRepo courseRepo;

	public Page<CourseDTO> getAll(SearchDTO listUserDTO) {
		if (listUserDTO.getCurrentPage() == null)
			listUserDTO.setCurrentPage(0);
		if (listUserDTO.getSize() == null)
			listUserDTO.setSize(3);

		PageRequest pageRequest = PageRequest.of(listUserDTO.getCurrentPage(), listUserDTO.getSize());
		Page<Course> page = courseRepo.findAll(pageRequest);

		Page<CourseDTO> page2 = page.map(course -> new ModelMapper().map(course, CourseDTO.class));
		return page2;
	}

	public void create(CourseDTO courseDTO) {
		if (!courseRepo.findById(courseDTO.getId()).isPresent()) {
			Course course = new ModelMapper().map(courseDTO, Course.class);
			courseRepo.save(course);
		}
	}

	public void update(CourseDTO courseDTO) {
		Course current = courseRepo.findById(courseDTO.getId()).orElseThrow(NoResultException::new);
		current.setName(courseDTO.getName());
		courseRepo.save(current);
	}

	public void delete(int id) {
		courseRepo.deleteById(id);
	}

	public CourseDTO getById(int id) {
		Course course = courseRepo.getById(id);
		if (course != null) {
			CourseDTO courseDTO = new ModelMapper().map(course, CourseDTO.class);
			return courseDTO;
		}
		return null;
	}

	public List<CourseDTO> search(String name) {
		List<Course> courses = courseRepo.searchName("%" + name + "%");

		List<CourseDTO> courseDTOs = courses.stream()
				.map(course -> new ModelMapper().map(course, CourseDTO.class)).collect(Collectors.toList());
		
		return courseDTOs;
	}
}
