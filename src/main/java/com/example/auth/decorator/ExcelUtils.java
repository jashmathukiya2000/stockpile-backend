package com.example.auth.decorator;

import com.example.auth.decorator.user.UserSpiDataInExcel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static org.apache.poi.xssf.usermodel.XSSFWorkbookFactory.createWorkbook;

@Data
@Component
@Slf4j
public class ExcelUtils {


    private static HSSFWorkbook workbook;

    public static <T> Workbook createWorkbookFromData(List<T> data, String title) {
        //create new workbook
        Workbook workbook = new XSSFWorkbook();
        workbook = createWorkbook();
        //create a sheet
        Sheet sheet = workbook.createSheet("SHEET NAME");


        //if no data then return no work
        if (data.size() == 0) {
            return workbook;
        }

        Row topRow = sheet.createRow(0);
        Row headerRow = sheet.createRow(1);


        //set title in excel file
        topRow.createCell(0).setCellValue(title);

        //set method
        List<Method> methods = setHeaders(headerRow, data.get(0).getClass(), sheet);

        //This is start with one because we need to skip first element ...
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, methods.size()));
        topRow.getCell(0).getCellStyle().setAlignment(HorizontalAlignment.CENTER);

        int i = 2;
        for (T record : data) {
            Row row = sheet.createRow(i++);
            setData(row, methods, record, i , sheet);


        }

        return workbook;
    }


    private static void setData(Row row, List<Method> methods, Object o, int position, Sheet sheet) {

        int index = 0;
        Cell cell = row.createCell(index++);
        for (Method method : methods) {
            cell = row.createCell(index++);
            try {
                Object cellValue = method.invoke(o);
                if (cellValue == null) {
                    cellValue = "";
                }
                cell.setCellValue(cellValue.toString().replaceAll("null", ""));
                row.getSheet().autoSizeColumn(index-1);
                CellUtil.setAlignment(cell, HorizontalAlignment.CENTER);
            } catch (Exception ignored) {
            }
        }

    }

    private static List<Method> setHeaders(Row headerRow, Class c, Sheet sheet) {
        List<Method> methods = new ArrayList<>();
        Method[] fields = c.getMethods();
        for (Method field : fields) {
            ExcelField excelField = field.getAnnotation(ExcelField.class);
            if (excelField != null) {
                methods.add(field);

            }
        }
        methods.sort(Comparator.comparingInt(o -> o.getAnnotation(ExcelField.class).position()));
        int i = 0;
        Cell cell = headerRow.createCell(i++);
        for (Method method : methods) {
            ExcelField excelField = method.getAnnotation(ExcelField.class);
            cell = headerRow.createCell(i++);
            cell.setCellValue(excelField.excelHeader());
            headerRow.createCell(excelField.position());
               headerRow.getSheet().autoSizeColumn(i);

            CellUtil.setAlignment(cell, HorizontalAlignment.CENTER);
        }
        return methods;
    }

    public static ByteArrayResource getBiteResourceFromWorkbook(Workbook workbook) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return new ByteArrayResource(outputStream.toByteArray());
    }


    public static <T> Workbook createWorkbookOnResultSpi(HashMap<String, List<UserSpiDataInExcel>> hashMap, String title) {
        Workbook workbook = createWorkbook();

        // Create A Sheet
        Sheet sheet = workbook.createSheet("Sheet1");

        // If no data then return no work needed
        if (hashMap.size() == 0) {
            return workbook;
        }
        //set title in excel sheet
        Row topRow = sheet.createRow(0);
        topRow.createCell(0).setCellValue(title);

        //add space 2 after than  print studentName
        int i = topRow.getRowNum();

        for (HashMap.Entry<String, List<UserSpiDataInExcel>> entry : hashMap.entrySet()) {
            i = i + 1;
            Row CustomerName = sheet.createRow(i);
            //add student Name
            CustomerName.createCell(0).setCellValue(entry.getKey());


            i = i + 1;
            Row header = sheet.createRow(i);
            List<Method> methods = setHeaders(header, UserSpiDataInExcel.class, sheet);
            int k = 1;
            //set data
            for (UserSpiDataInExcel userSpiDataInExcel : entry.getValue()) {
                i = i + 1;
                Row row = sheet.createRow(i);


                setData(row, methods, userSpiDataInExcel, k++, sheet);
            }
            i = i + 1;
        }
        return workbook;
    }

    public static <T> Workbook createWorkbookOnBookDetailsData(HashMap<String, List<PurchaseLogExcelGenerator>> hashMap, String title) {
        Workbook workbook = createWorkbook();

        // Create A Sheet
        Sheet sheet = workbook.createSheet("Sheet1");

        // If no data then return no work needed
        if (hashMap.size() == 0) {
            return workbook;
        }
        //set title in excel sheet
        Row topRow = sheet.createRow(0);
        topRow.createCell(0).setCellValue(title);

        //add space 2 after than  print CustomerName
        int i = topRow.getRowNum();

        for (HashMap.Entry<String, List<PurchaseLogExcelGenerator>> entry : hashMap.entrySet()) {
            i = i + 1;
            Row CustomerName = sheet.createRow(i);
            //add Customer Name
            CustomerName.createCell(0).setCellValue(entry.getKey());

            i = i + 1;
            Row header = sheet.createRow(i);
            List<Method> methods = setHeaders(header, PurchaseLogExcelGenerator.class, sheet);
            int k = 1;
            //set data
            for (PurchaseLogExcelGenerator purchaseLogExcelGenerator : entry.getValue()) {
                i = i + 1;
                Row row = sheet.createRow(i);
                setData(row, methods, purchaseLogExcelGenerator, k++, sheet);
            }
            i = i + 1;
        }
        return workbook;
    }



}
