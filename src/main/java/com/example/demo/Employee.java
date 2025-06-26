package com.example.demo;



import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employee")
public class Employee {

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public LocalDate getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getCtcLpa() {
		return ctcLpa;
	}

	public void setCtcLpa(BigDecimal ctcLpa) {
		this.ctcLpa = ctcLpa;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emp_code", nullable = false, unique = true)
    private String empCode;

    @Column(nullable = false)
    private String name;

    private String email;
    private String designation;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    private String status;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "CTC_LPA")
    private BigDecimal ctcLpa;

    // Constructors, Getters and Setters

    public Employee() {
        this.status = "active";
        this.joiningDate = LocalDate.now();
    }
    
    

    // getters and setters below (or use Lombok for brevity)
}
