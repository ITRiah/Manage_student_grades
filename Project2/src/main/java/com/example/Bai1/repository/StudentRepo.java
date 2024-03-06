package com.example.Bai1.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.Bai1.entity.Student;
import com.example.Bai1.entity.User;

public interface StudentRepo extends JpaRepository<Student,Integer > {

	@Query("SELECT u FROM Student u WHERE u.studentCode LIKE :name")
	List<Student> searchByName(String name);
	
	Page<Student> findAll(Pageable pageable); 
}
	