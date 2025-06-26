package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/fees")
public class FeeController {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private EnrollmentRepository enrollmentRepo;

    @Autowired
    private FeePaymentRepository feePaymentRepo;

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private FeeReceiptService feeReceiptService; // Inject the receipt service

    @Autowired
    private EmailService emailService;

    @GetMapping("/manage")
    public String feeHome() {
        return "manage_fees";
    }

    @GetMapping("/fetch/{studentId}")
    public String fetchStudentFeeInfo(@PathVariable Long studentId, Model model) {
        Optional<Student> studentOpt = studentRepo.findById(studentId);
        if (studentOpt.isEmpty()) {
            model.addAttribute("error", "Student not found");
            return "manage_fees";
        }

        Student student = studentOpt.get();
        List<Enrollment> enrollments = enrollmentRepo.findByStudentIdAndFeesPaidFalse(studentId);

        List<Course> enrolledCourses = enrollments.stream()
                .map(Enrollment::getCourse)
                .collect(Collectors.toList());

        model.addAttribute("student", student);
        model.addAttribute("enrolledCourses", enrolledCourses);
        model.addAttribute("feePayment", new FeePayment());

        return "manage_fees";
    }

    
    @PostMapping("/pay")
    public String payFees(@ModelAttribute FeePayment feePayment, Model model) {
        feePaymentRepo.save(feePayment);

        List<Enrollment> enrollments = enrollmentRepo.findByStudentIdAndFeesPaidFalse(feePayment.getStudentId());
        for (Enrollment e : enrollments) {
            if (Objects.equals(e.getCourse().getId(), feePayment.getCourseId())) {
                e.setFeesPaid(true);
                enrollmentRepo.save(e);
                break;
            }
        }

        Student student = studentRepo.findById(feePayment.getStudentId()).orElseThrow();
        Course course = courseRepo.findById(feePayment.getCourseId().longValue()).orElseThrow();

        try {
            byte[] pdfBytes = feeReceiptService.generateReceiptPdf(feePayment, student, course);

            emailService.sendFeeReceipt(student.getEmail(), student.getName(), pdfBytes);
            model.addAttribute("success", "Fee payment recorded and receipt emailed.");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Payment saved, but error sending receipt: " + e.getMessage());
        }

        return "redirect:/fees/manage";
    }

}
