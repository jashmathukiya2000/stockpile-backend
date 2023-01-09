package com.example.auth.model;

import com.example.auth.commons.constant.MessageConstant;
import com.example.auth.commons.exception.NotFoundException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {"CustomerName", "ItemName", "price", "Quantity", "DiscountInPercent", "DiscountInRupee", "TotalPrice"};
    static String SHEET = "Invoice";

    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<PurchaseLogHistory> excelTopurchaseLogHistoryList(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<PurchaseLogHistory> purchaseLogHistoryList = new ArrayList<PurchaseLogHistory>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                PurchaseLogHistory purchaseLogHistory = new PurchaseLogHistory();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            purchaseLogHistory.setCustomerName(String.valueOf(currentCell.getStringCellValue()));
                            break;

                        case 1:
                            purchaseLogHistory.setItemName(currentCell.getStringCellValue());
                            break;

                        case 2:
                            purchaseLogHistory.setPrice(currentCell.getNumericCellValue());
                            break;

                        case 3:
                            purchaseLogHistory.setQuantity((int) currentCell.getNumericCellValue());
                            break;
                        case 4:
                            purchaseLogHistory.setDiscountInPercent((int) currentCell.getNumericCellValue());
                            break;
                        case 5:

                            purchaseLogHistory.setDiscountInRupee((int) currentCell.getNumericCellValue());
                            break;
                        case 6:
                            purchaseLogHistory.setTotalPrice((int) currentCell.getNumericCellValue());

                            break;
                        default:
                            break;
                    }

                    cellIdx++;
                }
                checkValidation(purchaseLogHistory);
                purchaseLogHistoryList.add(purchaseLogHistory);
            }

            workbook.close();
            return purchaseLogHistoryList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }

    }


    public static void checkValidation(PurchaseLogHistory purchaseLogHistory) {
//        purchaseLogHistory.setDiscountInRupee((purchaseLogHistory.getPrice() * purchaseLogHistory.getQuantity() * purchaseLogHistory.getDiscountInPercent()) / 100);
//        purchaseLogHistory.setTotalPrice(purchaseLogHistory.getPrice() * purchaseLogHistory.getQuantity() - purchaseLogHistory.getDiscountInRupee());
        if (purchaseLogHistory.getPrice() == 0) {
            throw new NotFoundException(MessageConstant.PRICE_MUST_NOT_BE_NULL);
        }

        if (purchaseLogHistory.getQuantity() < 0) {
            throw new NotFoundException(MessageConstant.MUST_NOT_BE_NULL);
        }

        if (purchaseLogHistory.getItemName().isEmpty()) {
            throw new NotFoundException(MessageConstant.ITEM_NAME_MUST_NOT_BE_NULL);

        }
        if (purchaseLogHistory.getCustomerName().isEmpty()) {
            throw new NotFoundException(MessageConstant.CUSTOMER_NAME_MUST_NOT_BE_NULL);
        }
        if (purchaseLogHistory.getDiscountInPercent() == 0) {
            throw new NotFoundException(MessageConstant.DISCOUNT_MUST_NOT_BE_NULL);

        }   if (purchaseLogHistory.getDiscountInRupee() == 0) {
            throw new NotFoundException(MessageConstant.DISCOUNT_MUST_NOT_BE_NULL);

        }   if (purchaseLogHistory.getTotalPrice() == 0) {
            throw new NotFoundException(MessageConstant.TOTAL_PRICE_MUST_NOT_BE_NULL);
        }

    }


}
