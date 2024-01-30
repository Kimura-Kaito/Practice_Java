package com.example.licenseManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.licenseManager.entity.Level;

@Repository
public interface LevelRepository extends JpaRepository<Level, Integer> {
	//レベル順にソート(20231128)
	@Query(value = "select * from level order by id", nativeQuery = true)
	List<Level> queryAll();
}
