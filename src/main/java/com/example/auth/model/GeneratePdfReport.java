package com.example.auth.model;

import com.lowagie.text.*;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GeneratePdfReport {
    public void generate(List<PurchaseLogHistory> purchaseLogHistoryList, HttpServletResponse response) throws DocumentException, IOException, IOException {
        Document document = new Document(PageSize.A3);
        // Getting instance of PdfWriter
        PdfWriter.getInstance(document, response.getOutputStream());
        // Opening the created document to change it
        document.open();
        // Creating font
        // Setting font style and size
        Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTiltle.setSize(20);
        // Creating paragraph
        Paragraph paragraph1 = new Paragraph("Invoice", fontTiltle);
        // Aligning the paragraph in the document
        paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph1);
        Font fontTiltle2 = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTiltle.setSize(15);
        Paragraph paragraph = new Paragraph("Date: ", fontTiltle);
        paragraph.setAlignment(Paragraph.ALIGN_LEFT);
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        String format = dateFormat.format(date);
        paragraph.add(String.valueOf(format));
        document.add(paragraph);

        // Adding the created paragraph in the document

        // Creating a table of the 4 columns
        PdfPTable table = new PdfPTable(6);
        // Setting width of the table, its columns and spacing
        table.setWidthPercentage(100f);
        table.setWidths(new int[]{10, 10, 10, 10, 10, 10});
        table.setSpacingBefore(30);
        // Create Table Cells for the table header
        PdfPCell cell = new PdfPCell();
        // Setting the background color and padding of the table cell
        cell.setBackgroundColor(CMYKColor.BLUE);
        cell.setPadding(5);
        // Creating font
        // Setting font style and size
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(CMYKColor.WHITE);
        // Adding headings in the created table cell or  header
        // Adding Cell to table
        cell.setPhrase(new Phrase(" customerName", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase(" ItemName", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("price", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("quantity", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("DiscountInPercent", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("DiscountInRupee", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("totalPrice", font));
        table.addCell(cell);
        // Iterating the list of customers
        for (PurchaseLogHistory purchaseLogHistoryResponse : purchaseLogHistoryList) {
            table.addCell(String.valueOf(purchaseLogHistoryResponse.getCustomerName()));
            table.addCell(purchaseLogHistoryResponse.getItemName());
            table.addCell(String.valueOf(purchaseLogHistoryResponse.getPrice()));
            table.addCell(String.valueOf(purchaseLogHistoryResponse.getQuantity()));
            table.addCell(String.valueOf(purchaseLogHistoryResponse.getDiscountInPercent()));
            table.addCell(String.valueOf(purchaseLogHistoryResponse.getDiscountInRupee()));
            table.addCell(String.valueOf(purchaseLogHistoryResponse.getTotalPrice()));

        }
        // Adding the created table to the document
        document.add(table);
        // Closing the document
        document.close();
    }
}

