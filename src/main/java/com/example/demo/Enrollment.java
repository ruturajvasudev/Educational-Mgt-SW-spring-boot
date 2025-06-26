package com.example.demo;

import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "enrollment")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "employee_id")  // just an integer now
    private Integer employeeId;

    @Column(name = "enrollment_date")
    private LocalDate enrollmentDate = LocalDate.now();

    private String status = "active";

    private String grade;
    
    @Column(name = "fees_paid")
    private boolean feesPaid=false;


    // Getters and Setters...

    public boolean isFeesPaid() {
		return feesPaid;
	}
	public void setFeesPaid(boolean feesPaid) {
		this.feesPaid = feesPaid;
	}
	public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public Integer getEmployeeId() { return employeeId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }

    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
}
