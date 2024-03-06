package com.example.Bai1.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Bai1.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	@Query("SELECT u FROM User u Where u.name LIKE :x")
	List<User> searchByName(@Param("x") String s);

	@Query("SELECT u FROM User u WHERE MONTH(u.birthdate) = :month AND DAY(u.birthdate) = :date")
	List<User> searchByBirthDay(@Param("date") int date, @Param("month") int month);

	Page<User> findAll(Pageable pageable);
	
	User findByUsername(String username);
}
