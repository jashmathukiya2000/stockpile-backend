package com.example.auth.service;

import com.example.auth.model.Invoice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InvoiceService {

    List<Invoice> createInvoice(String customerId );

}
