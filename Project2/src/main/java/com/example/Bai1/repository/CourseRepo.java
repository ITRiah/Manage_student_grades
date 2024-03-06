package com.example.Bai1.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.Bai1.entity.Course;

public interface CourseRepo extends JpaRepository<Course, Integer> {
	@Query("SELECT d FROM Course d WHERE d.name LIKE :name")
	List<Course> searchName(String name);
	
	Page<Course> findAll(Pageable pageable);
}
