package com.example.demo;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/view")
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employee_list";
    }

    @GetMapping("/add")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee_add";
    }

    @PostMapping("/add")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/employees/view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "employee_edit";
    }

    @PostMapping("/update")
    public String updateEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/employees/view";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }

    @GetMapping("/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=employees.pdf");

        List<Employee> employees = employeeService.getAllEmployees();

        Document document = new Document(PageSize.A4, 36, 36, 100, 36);
          // 4 newlines = approx 80pt
 PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        writer.setPageEvent(new PdfHeaderFooter());
        document.open();
        document.add(new Paragraph("\n\n"));

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Employee Report", fontTitle);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" ")); // spacer

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);

        Stream.of("Emp Code", "Name", "Email", "Designation", "Joining Date", "Status", "CTC LPA")
                .forEach(header -> {
                    PdfPCell cell = new PdfPCell();
                    Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                    cell.setPhrase(new Phrase(header, font));
                    table.addCell(cell);
                });

        for (Employee emp : employees) {
            table.addCell(emp.getEmpCode());
            table.addCell(emp.getName());
            table.addCell(emp.getEmail());
            table.addCell(emp.getDesignation());
            table.addCell(emp.getJoiningDate() != null ? emp.getJoiningDate().toString() : "");
            table.addCell(emp.getStatus());
            table.addCell(emp.getCtcLpa() != null ? emp.getCtcLpa().toString() : "0.0");
        }

        document.add(table);
        document.close();
    }

    @GetMapping("/back_toDashboard")
    public String backDashboard() {
        return "redirect:/admin_dashboard";
    }
}
