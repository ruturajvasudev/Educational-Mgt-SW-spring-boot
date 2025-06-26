package com.example.demo;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FeeReceiptService {

    public byte[] generateReceiptPdf(FeePayment payment, Student student, Course course) throws Exception {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, out);

        // Register the header-footer class
        writer.setPageEvent(new PdfHeaderFooter());

        document.open();
        document.add(new Paragraph("\n\n\n"));

        // Title
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Paragraph title = new Paragraph("Fee Payment Receipt", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1, 2});

        table.addCell("Receipt ID:");
        table.addCell(String.valueOf(payment.getId()));

        table.addCell("Student Name:");
        table.addCell(student.getName());

        table.addCell("Email:");
        table.addCell(student.getEmail());

        table.addCell("Phone:");
        table.addCell(student.getPhone());

        table.addCell("Course:");
        table.addCell(course.getName());

        table.addCell("Amount:");
        table.addCell(String.valueOf(payment.getAmount()));

        table.addCell("Mode:");
        table.addCell(payment.getPaymentMode());

        table.addCell("Date:");
        String formattedDate = new SimpleDateFormat("dd MMM yyyy HH:mm").format(new Date());
        table.addCell(formattedDate);

        table.addCell("Comments:");
        table.addCell(payment.getComment() != null ? payment.getComment() : "-");

        table.setSpacingBefore(10);
        document.add(table);

        document.close();
        return out.toByteArray();
    }
}
