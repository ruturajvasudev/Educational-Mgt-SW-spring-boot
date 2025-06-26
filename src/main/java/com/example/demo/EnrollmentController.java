package com.example.demo;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentRepository enrollmentRepo;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private CourseRepository courseRepo;

    @GetMapping("/view")
    public String listEnrollments(Model model) {
		List<Enrollment> enrollments = enrollmentRepo.findAll(); // make sure this does EAGER fetch or join fetch
        model.addAttribute("enrollments", enrollments);
        return "enrollment_list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("enrollment", new Enrollment());
        model.addAttribute("students", studentRepo.findAll());
        model.addAttribute("courses", courseRepo.findAll());
        return "enrollment_form";
    }

    @PostMapping("/save")
    public String saveEnrollment(@ModelAttribute Enrollment enrollment) {
        enrollmentRepo.save(enrollment);
        return "redirect:/enrollments/view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Enrollment e = enrollmentRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ID"));
        model.addAttribute("enrollment", e);
        model.addAttribute("students", studentRepo.findAll());
        model.addAttribute("courses", courseRepo.findAll());
        return "enrollment_form";
    }

    @PostMapping("/update")
    public String updateEnrollment(@ModelAttribute Enrollment enrollment) {
        enrollmentRepo.save(enrollment);
        return "redirect:/enrollments/view";
    }

    @PostMapping("/delete/{id}")
    public String deleteEnrollment(@PathVariable Long id) {
        enrollmentRepo.deleteById(id);
        return "redirect:/enrollments/view";
    }

    @GetMapping("/details/{id}")
    @ResponseBody
    public Enrollment getEnrollmentDetails(@PathVariable Long id) {
        return enrollmentRepo.findById(id).orElseThrow();
    }
}
