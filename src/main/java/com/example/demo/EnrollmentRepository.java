package com.example.demo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

	List<Enrollment> findByStudentIdAndFeesPaidFalse(Long studentId);

}
