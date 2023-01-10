package com.example.auth.controller;

import com.example.auth.decorator.ListResponse;
import com.example.auth.decorator.Response;
import com.example.auth.model.GeneratePdf;
import com.example.auth.model.GeneratePdfReport;
import com.example.auth.model.Invoice;
import com.example.auth.model.PurchaseLogHistory;
import com.example.auth.service.InvoiceService;
import com.itextpdf.text.DocumentException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("invoice")
public class CreateInvoiceController {
    private final InvoiceService invoiceService;

    public CreateInvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @RequestMapping(name = "createInvoice", value = "/Invoice", method = RequestMethod.POST)
    public ListResponse<Invoice> createInvoice(@RequestParam String  customerId){
        ListResponse<Invoice> listResponse = new ListResponse<>();
        listResponse.setData(invoiceService.createInvoice(customerId));
        listResponse.setStatus(Response.getOkResponse());
        return listResponse;
    }
    @RequestMapping(name = "generatePdfFile", value = "/export-to-pdf", method = RequestMethod.POST)
    public void generatePdfFile(HttpServletResponse response, @RequestParam String customerId) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
        String currentDateTime = dateFormat.format(new Date());
        String headerkey = "Content-Disposition";
        String headervalue = "attachment; filename=Invoice" + currentDateTime + ".pdf";
        response.setHeader(headerkey, headervalue);
        List<Invoice> purchaseLogHistoryList = invoiceService.createInvoice(customerId);
        GeneratePdf pdfReport = new GeneratePdf();
        pdfReport.generate1(purchaseLogHistoryList, response);
    }
}
