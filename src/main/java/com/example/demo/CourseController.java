package com.example.demo;

import com.example.demo.Course;
import com.example.demo.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepo;

    @GetMapping("/add")
    public String showCourseForm(Model model) {
        model.addAttribute("course", new Course());
        return "add_course";
    }

    @PostMapping("/add")
    public String saveCourse(@ModelAttribute Course course, Model model) {
        courseRepo.save(course);
        model.addAttribute("message", "Course added successfully!");
        return "redirect:/courses/view";
    }

    //  Add this for viewing courses
    @GetMapping("/view")
    public String viewCourses(Model model) {
        try {
            List<Course> courses = courseRepo.findAll();
            model.addAttribute("courses", courses);
        } catch (Exception e) {
            e.printStackTrace();  // This will show you the real cause
            model.addAttribute("message", "Error: " + e.getMessage());
        }
        return "view_courses";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseRepo.deleteById(id);
        return "redirect:/courses/view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Course course = courseRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + id));
        model.addAttribute("course", course);
        return "edit_course";
    }

    @PostMapping("/update")
    public String updateCourse(@ModelAttribute Course course) {
        courseRepo.save(course);
        return "redirect:/courses/view";
    }
    
    

}