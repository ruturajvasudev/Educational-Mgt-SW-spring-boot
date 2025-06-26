package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class helloController {

    @Autowired
    private Repository studentRepo;

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private StudentService studentService;

    @Autowired
    private EnrollmentRepository enrollmentRepo;

    @GetMapping("/")
    public String home() {
        return "welcome";
    }
    @GetMapping("/back_toDashboard")
    public String backDashboard(Model model) {
    	return "redirect:/admin_dashboard";
    }
    @GetMapping("/register")
    public String showForm(Model model) {
        model.addAttribute("student", new Student());
        return "register";
    }

    @PostMapping("/register")
    public String processForm(@ModelAttribute Student student, RedirectAttributes redirectAttributes) {
        studentRepo.save(student);
        emailService.sendWelcomeEmail(student.getEmail(), student.getName());
        redirectAttributes.addFlashAttribute("success", "Student registered successfully!");
        return "redirect:/register";
    }

    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", studentRepo.findAll());
        return "student_list";
    }

    @GetMapping("/students/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Student student = studentRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + id));
        model.addAttribute("student", student);
        return "edit";
    }

    @PostMapping("/students/update")
    public String updateStudent(@ModelAttribute Student student) {
        studentRepo.save(student);
        return "redirect:/students";
    }

    @PostMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentRepo.deleteById(id);
        return "redirect:/students";
    }

    @GetMapping("/students/search**")
    @ResponseBody
    public List<Student> searchStudents(@RequestParam("name") String name) {
        return studentService.searchByName(name);
    }

   

  
}
