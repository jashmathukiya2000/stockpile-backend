package com.example.auth.service;

import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.exception.NotFoundException;
import com.example.auth.model.Customer;
import com.example.auth.model.Invoice;
import com.example.auth.model.PurchaseLogHistory;
import com.example.auth.repository.CustomerRepository;
import com.example.auth.repository.InvoiceRepository;
import com.example.auth.repository.PurchaseLogHistoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final PurchaseLogHistoryRepository purchaseLogHistoryRepository;
    private final ModelMapper modelMapper;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, CustomerRepository customerRepository, PurchaseLogHistoryRepository purchaseLogHistoryRepository, ModelMapper modelMapper) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
        this.purchaseLogHistoryRepository = purchaseLogHistoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Invoice> createInvoice(String customerId) {
        List<PurchaseLogHistory> purchaseLogHistoryList1=purchaseLogHistoryRepository.findByCustomerIdAndSoftDeleteFalse(customerId);
        List<Invoice> list= new ArrayList<>();
        for (PurchaseLogHistory purchaseLogHistory : purchaseLogHistoryList1) {
            Invoice invoice1=modelMapper.map(purchaseLogHistory,Invoice.class);
            invoice1.setPurchaseLogHistory(purchaseLogHistory);
            invoice1.setTotal(findTotal(customerId));
           list.add(invoice1);
             }
           return list;

      }

    Customer getcustomerById(String id) {
        return customerRepository.findByIdAndSoftDeleteIsFalse(id).orElseThrow(() -> new NotFoundException(MessageConstant.ID_NOT_FOUND));
    }


    public double findTotal(String customerId){
        double total = 0;
        List<PurchaseLogHistory> purchaseLogHistory = purchaseLogHistoryRepository.findByCustomerIdAndSoftDeleteFalse(customerId);
        for (PurchaseLogHistory logHistory : purchaseLogHistory) {
            total += logHistory.getTotalPrice();
        }


        return total;
    }

}
