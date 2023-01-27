package com.example.auth.controller;

import com.example.auth.commons.Access;
import com.example.auth.commons.constant.ResponseConstant;
import com.example.auth.commons.decorator.GeneralHelper;
import com.example.auth.commons.enums.Role;
import com.example.auth.decorator.*;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.decorator.pagination.PageResponse;
import com.example.auth.decorator.pagination.PurchaseLogFilter;
import com.example.auth.decorator.pagination.PurchaseLogSortBy;
import com.example.auth.model.ExcelGenerator;
import com.example.auth.model.ExcelHelper;
import com.example.auth.model.GeneratePdfReport;
import com.example.auth.model.PurchaseLogHistory;
import com.example.auth.service.PurchaseLogHistoryService;
import com.itextpdf.text.DocumentException;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("purchaseLogHistory")

public class PurchaseLogHistoryController {
    private final PurchaseLogHistoryService purchaseLogHistoryService;
    private final GeneralHelper generalHelper;

    public PurchaseLogHistoryController(PurchaseLogHistoryService purchaseLogHistoryService, GeneralHelper generalHelper) {
        this.purchaseLogHistoryService = purchaseLogHistoryService;
        this.generalHelper = generalHelper;
    }

    @RequestMapping(name = "addPurchaseLog", value = "/add", method = RequestMethod.POST)
    @Access (levels = Role.ADMIN)
    public DataResponse<PurchaseLogHistoryResponse> addPurchaseLog(@RequestBody PurchaseLogHistoryAddRequest purchaseLogHistoryAddRequest, @RequestParam String customerId,@RequestParam String itemName) {
        DataResponse<PurchaseLogHistoryResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(purchaseLogHistoryService.addPurchaseLog(purchaseLogHistoryAddRequest, customerId,itemName));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.SAVED_SUCCESSFULLY));
        return dataResponse;
    }

    @RequestMapping(name = "updatePurchaseLogHistory", value = "/update/{id}", method = RequestMethod.POST)
    @Access (levels = Role.ADMIN)
    public DataResponse<Object> updatePurchaseLog(@RequestBody PurchaseLogHistoryAddRequest purchaseLogHistoryAddRequest, @RequestParam String id) throws InvocationTargetException, IllegalAccessException {
        DataResponse<Object> dataResponse = new DataResponse<>();
        dataResponse.setData(purchaseLogHistoryService.updatePurchaseLog(purchaseLogHistoryAddRequest, id));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.UPDATED_SUCCESSFULLY));
        return dataResponse;
    }

    @RequestMapping(name = "getPurchaseLogById", value = "/get/{id}", method = RequestMethod.GET)
    @Access (levels = Role.ADMIN)
    public DataResponse<PurchaseLogHistoryResponse> getPurchaseLogById(@RequestParam String id) {
        DataResponse<PurchaseLogHistoryResponse> dataResponse = new DataResponse<>();
        dataResponse.setData(purchaseLogHistoryService.getPurchaseLogById(id));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return dataResponse;
    }

    @RequestMapping(name = "getAllPurchaseLog", value = "/get/All", method = RequestMethod.GET)
    @Access (levels = Role.ADMIN)
    public ListResponse<PurchaseLogHistoryResponse> getAllPurchaseLog() {
        ListResponse<PurchaseLogHistoryResponse> listResponse = new ListResponse<>();
        listResponse.setData(purchaseLogHistoryService.getAllPurchaseLog());
        listResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return listResponse;
    }

    @RequestMapping(name = "deletePurchaseLogById", value = "/delete/{id}", method = RequestMethod.DELETE)
    @Access (levels = Role.ADMIN)
    public DataResponse<Object> deletePurchaseLogById(@RequestParam String id) {
        DataResponse<Object> dataResponse = new DataResponse<>();
        dataResponse.setData(purchaseLogHistoryService.deletePurchaseLogById(id));
        dataResponse.setStatus(Response.getOkResponse(ResponseConstant.DELETED_SUCCESSFULLY));

        return dataResponse;
    }

    @RequestMapping(name = "getAllPurchaseLogByPagination", value = "get/all/pagination", method = RequestMethod.POST)
    @Access (levels = Role.ADMIN)
    public PageResponse<PurchaseLogHistoryResponse> getAllPurchaseLogByPagination(@RequestBody FilterSortRequest<PurchaseLogFilter, PurchaseLogSortBy> filterSortRequest) {
        PageResponse<PurchaseLogHistoryResponse> pageResponse = new PageResponse<>();
        Page<PurchaseLogHistoryResponse> logHistoryResponse = purchaseLogHistoryService.getAllPurchaseLogByPagination(filterSortRequest.getFilter(), filterSortRequest.getSort(),
                generalHelper.getPagination(filterSortRequest.getPagination().getPage(), filterSortRequest.getPagination().getLimit()));
        pageResponse.setData(logHistoryResponse);
        pageResponse.setStatus(Response.getOkResponse());
        return pageResponse;
    }


    @RequestMapping(name = "generatePdfFile", value = "/export-to-pdf", method = RequestMethod.POST)
    @Access (levels = Role.ADMIN)
    public void generatePdfFile(HttpServletResponse response, @RequestParam String customerId) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
        String currentDateTime = dateFormat.format(new Date());
        String headerkey = "Content-Disposition";
        String headervalue = "attachment; filename=Invoice" + currentDateTime + ".pdf";
        response.setHeader(headerkey, headervalue);
        List<PurchaseLogHistory> purchaseLogHistoryList = purchaseLogHistoryService.findById(customerId);
        GeneratePdfReport pdfReport = new GeneratePdfReport();
        pdfReport.generate(purchaseLogHistoryList, response);
    }


    @RequestMapping(name = "generateExcelFile", value = "/export-to-excel", method = RequestMethod.POST)
    @Access (levels = Role.ADMIN)
    public void exportIntoExcelFile(HttpServletResponse response, @RequestParam String customerId) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Invoice" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<PurchaseLogHistory> customerLog = purchaseLogHistoryService.findById(customerId);
        ExcelGenerator generator = new ExcelGenerator(customerLog);
        generator.generateExcelFile(response);
    }

    @RequestMapping(name = "uploadExcelFile", value = "/upload/excelFile", method = RequestMethod.POST)
    @Access (levels = Role.ADMIN)
    public DataResponse<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        DataResponse<Object> dataResponse = new DataResponse<>();
        if (ExcelHelper.hasExcelFormat(file)) {
            purchaseLogHistoryService.save(file);
            dataResponse.setStatus(Response.getOkResponse(ResponseConstant.UPLOADED_FILE_SUCCESSFULLY));
        } else {
            dataResponse.setStatus(Response.getNotFoundResponse(ResponseConstant.PLEASE_UPLOAD_EXCEL_FILE));
        }
        return dataResponse;
    }

    @RequestMapping(name = "getPurchaseLogByMonth", value = "/month/year", method = RequestMethod.POST)
    @Access (levels = Role.ADMIN)
    public ListResponse<PurchaseLogHistoryResponse> getPurchaseLogByMonth(@RequestParam int month) {
        ListResponse<PurchaseLogHistoryResponse> listResponse = new ListResponse<>();
        listResponse.setData(purchaseLogHistoryService.getPurchaseLogByMonth(month));
        listResponse.setStatus(Response.getOkResponse(ResponseConstant.OK));
        return listResponse;
    }

    public void date(){
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
        String currentDateTime = dateFormat.format(new Date());
        String headerkey = "Content-Disposition";

    }
    @RequestMapping(name = "getItemPurchaseDetailsByMonthYear",value = "/month/Year", method = RequestMethod.POST)
    @Access (levels = Role.ANONYMOUS)
    public ListResponse<PurchaseAggregationResponse> findItemPurchaseDetailsByMonthYear(){
        ListResponse<PurchaseAggregationResponse> listResponse= new ListResponse<>();
        listResponse.setData(purchaseLogHistoryService.getItemPurchaseDetailsByMonthYear());
        listResponse.setStatus(Response.getOkResponse());
        return listResponse;
    }
    @RequestMapping(name = "getPurchaseDetailsByCustomerName",value = "/customerName",method = RequestMethod.POST)
    @Access (levels = Role.ANONYMOUS)
   public ListResponse<ItemPurchaseAggregationResponse> getPurchaseDetailsByCustomerName(){
        ListResponse<ItemPurchaseAggregationResponse> listResponse= new ListResponse<>();
        listResponse.setData(purchaseLogHistoryService.getPurchaseDetailsByCustomerName());
        listResponse.setStatus(Response.getOkResponse());
        return listResponse;
    }



}