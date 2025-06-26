package com.example.demo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<Student, Long> {
	List<Student> findByNameContainingIgnoreCase(String name);

}
