package com.example.licenseManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.licenseManager.entity.License;

@Repository
public interface LicenseRepository  extends JpaRepository<License,Integer>{
	List<License> findByName(String name);
	List<License> findByNameLike(String name);
}