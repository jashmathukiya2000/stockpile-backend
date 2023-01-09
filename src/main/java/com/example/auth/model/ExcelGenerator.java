package com.example.auth.model;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


    public class ExcelGenerator {

        private List < PurchaseLogHistory > purchaseLogHistoryList;
        private XSSFWorkbook workbook;
        private XSSFSheet sheet;

        public ExcelGenerator(List < PurchaseLogHistory > purchaseLogHistoryList) {
            this.purchaseLogHistoryList = purchaseLogHistoryList;
            workbook = new XSSFWorkbook();
        }
        private void writeHeader() {
            sheet = workbook.createSheet("Invoice");
            Row row = sheet.createRow(0);
            CellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            font.setFontHeight(16);
            style.setFont(font);
            createCell(row, 0, "CustomerName", style);
            createCell(row, 1, "ItemName", style);
            createCell(row, 2, "Price", style);
            createCell(row, 3, "Quantity", style);
            createCell(row, 4, "DiscountInPercent", style);
            createCell(row, 5, "DiscountInRupee", style);
            createCell(row, 6, "TotalPrice", style);

        }
        private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
            sheet.autoSizeColumn(columnCount);
            Cell cell = row.createCell(columnCount);
            if (valueOfCell instanceof Integer) {
                cell.setCellValue((Integer) valueOfCell);
            } else if (valueOfCell instanceof Long) {
                cell.setCellValue((Long) valueOfCell);
            } else if (valueOfCell instanceof String) {
                cell.setCellValue((String) valueOfCell);

            } else {
                cell.setCellValue((Double) valueOfCell);
            }
            cell.setCellStyle(style);
        }
        private void write() {
            int rowCount = 1;
            CellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setFontHeight(14);
            style.setFont(font);
            for (PurchaseLogHistory record: purchaseLogHistoryList) {
                Row row = sheet.createRow(rowCount++);
                int columnCount = 0;
                createCell(row, columnCount++, record.getCustomerName(), style);
                createCell(row, columnCount++, record.getItemName(), style);
                createCell(row, columnCount++, record.getPrice(), style);
                createCell(row, columnCount++, record.getQuantity(), style);
                createCell(row, columnCount++, record.getDiscountInPercent(), style);
                createCell(row, columnCount++, record.getDiscountInRupee(), style);
                createCell(row, columnCount++, record.getTotalPrice(), style);
            }
        }
        public void generateExcelFile(HttpServletResponse response) throws IOException {
            writeHeader();
            write();
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        }
    }

