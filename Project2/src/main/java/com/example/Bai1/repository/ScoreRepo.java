package com.example.Bai1.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.Bai1.dto.avgScore;
import com.example.Bai1.entity.Score;

public interface ScoreRepo 
				extends JpaRepository<Score, Integer> {
	@Query("SELECT s FROM Score s WHERE s.student.userId = :sid")
	List<Score> searchByStudent(int sid);

	@Query("SELECT s FROM Score s WHERE s.course.id = :cid")
	List<Score> searchByCourse(int cid);

	@Query("SELECT new com.example.Bai1.dto.avgScore(c.id, c.name, AVG(s.score)) FROM Score s INNER JOIN s.course c GROUP BY c.id, c.name")
	List<avgScore> avgScoreByCourse();

	
	Page<Score> findAll(Pageable pageable);
}
