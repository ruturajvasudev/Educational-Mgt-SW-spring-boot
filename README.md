

This Spring Boot-based web application is designed to manage the core administrative functions of an educational institute, 
including student registration, course management, enrollment tracking, and fee payment processing. Built with a modular approach, 
the system provides a clean and intuitive dashboard for administrators to efficiently handle operations.

The application enables admins to register students, define and manage courses, and track which students are enrolled in which courses. 
The enrollment module records critical details such as student ID, course ID, assigned employee, and enrollment date. It also supports 
status updates and grading.

A key feature is the fee management module. Admins can search for students by ID, view their enrolled courses, and record fee payments. 
Student details like ID, name, and phone are displayed as read-only, while email can be edited. Once a course is selected, payment amount, 
mode, and comments can be entered. Upon successful fee payment, a professionally styled PDF receipt is generated, complete with a header, 
footer, logo, and timestamp, and automatically emailed to the student.

The project uses Spring Boot, Thymeleaf, Bootstrap, and PostgreSQL, adhering to the MVC pattern. 
It demonstrates proficiency in full-stack Java development, database interaction using Spring Data JPA, 
PDF generation, and email integration.

✅ Technologies Used:
Java (JDK 17+)

Spring Boot

Spring MVC

Spring Data JPA

Thymeleaf (for HTML templating)

PostgreSQL (as the database)

Bootstrap 5 (for UI styling)

HTML5/CSS3

JavaScript (Vanilla JS) (for AJAX/modal actions)

iText (v5) (for generating PDF fee receipts)

JavaMailSender (Spring Boot Starter Mail) (for sending emails with receipts)

Maven (as the build tool)
