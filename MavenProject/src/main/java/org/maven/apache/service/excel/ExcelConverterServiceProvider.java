package org.maven.apache.service.excel;

import lombok.Data;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.maven.apache.item.Item;
import org.maven.apache.mapper.TransactionMapper;
import org.maven.apache.service.item.ItemService;
import org.maven.apache.service.staff.StaffDAOService;
import org.maven.apache.staff.Staff;
import org.maven.apache.transaction.Transaction;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service("excelConverterService")
@Data
public class ExcelConverterServiceProvider implements ExcelConverterService {

    @Resource
    private ItemService itemService;

    @Resource
    private StaffDAOService staffDAOService;

    @Resource
    private TransactionMapper transactionMapper;

    @Override
    public void convertToExcel(File file) throws IOException {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        convertItemToExcel(hssfWorkbook);
        convertStaffToExcel(hssfWorkbook);
        convertTransactionToExcel(hssfWorkbook);
        hssfWorkbook.write(file);

    }

    private void convertItemToExcel(HSSFWorkbook hssfWorkbook) {
        HSSFSheet sheet = hssfWorkbook.createSheet("Item Sheet");
        HSSFCellStyle cellStyle = hssfWorkbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        sheet.setColumnWidth(3, 10000);
        sheet.setColumnWidth(1, 5120);
        sheet.setColumnWidth(2, 3840);
        sheet.setColumnWidth(0, 1800);
        HSSFRow row = sheet.createRow(0);
        row.setRowStyle(cellStyle);
        row.createCell(0).setCellValue("Item ID");
        row.createCell(1).setCellValue("Item Name");
        row.createCell(2).setCellValue("Item Unit");
        row.createCell(3).setCellValue("Item Description");
        row.createCell(4).setCellValue("Price");
        row.createCell(5).setCellValue("Total Price");
        List<Item> itemList = itemService.selectAll();
        for (Item item : itemList) {
            HSSFRow newRow = sheet.createRow(sheet.getLastRowNum() + 1);
            newRow.setRowStyle(cellStyle);
            newRow.createCell(0).setCellValue(item.getItemID());
            newRow.createCell(1).setCellValue(item.getItemName());
            newRow.createCell(2).setCellValue(item.getUnit());
            newRow.createCell(3).setCellValue(item.getDescription());
            newRow.createCell(3).setCellValue(item.getDescription().split("%%")[0]);
            try{
                newRow.createCell(4).setCellValue(Integer.valueOf(item.getDescription().split("%%")[0]) * item.getUnit());
            }catch (Exception e){
                newRow.createCell(4).setCellValue("ERROR");
            }


        }
    }

    private void convertStaffToExcel(HSSFWorkbook hssfWorkbook) {
        HSSFSheet sheet = hssfWorkbook.createSheet("Staff Sheet");
        HSSFCellStyle cellStyle = hssfWorkbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        sheet.setColumnWidth(3, 10000);
        sheet.setColumnWidth(1, 5120);
        sheet.setColumnWidth(2, 3940);
        sheet.setColumnWidth(0, 1800);
        HSSFRow row = sheet.createRow(0);
        row.setRowStyle(cellStyle);
        row.createCell(0).setCellValue("Staff ID");
        row.createCell(1).setCellValue("Staff Name");
        row.createCell(2).setCellValue("Staff Status");
        row.createCell(3).setCellValue("Staff Info");

        List<Staff> staffList = staffDAOService.selectAll();
        for (Staff staff : staffList) {
            HSSFRow newRow = sheet.createRow(sheet.getLastRowNum() + 1);
            newRow.setRowStyle(cellStyle);
            newRow.createCell(0).setCellValue(staff.getStaffID());
            newRow.createCell(1).setCellValue(staff.getStaffName());
            newRow.createCell(2).setCellValue(staff.getStatus());
            newRow.createCell(3).setCellValue(staff.getOtherInfo());
        }
    }

    private void convertTransactionToExcel(HSSFWorkbook hssfWorkbook) {
        HSSFSheet sheet = hssfWorkbook.createSheet("Transaction Sheet");
        HSSFCellStyle cellStyle = hssfWorkbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        sheet.setColumnWidth(3, 10000);
        sheet.setColumnWidth(1, 5120);
        sheet.setColumnWidth(2, 3940);
        sheet.setColumnWidth(0, 1800);
        sheet.setColumnWidth(4, 5120);
        sheet.setColumnWidth(5, 3940);
        HSSFRow row = sheet.createRow(0);
        row.setRowStyle(cellStyle);
        row.createCell(0).setCellValue("Transaction ID");
        row.createCell(1).setCellValue("Staff Name");
        row.createCell(2).setCellValue("Transaction Status");
        row.createCell(4).setCellValue("Transaction Time");
        row.createCell(5).setCellValue("ItemName");
        row.createCell(3).setCellValue("Transaction Info");

        List<Transaction> transactionList = transactionMapper.selectAll();
        for (Transaction transaction : transactionList) {
            HSSFRow newRow = sheet.createRow(sheet.getLastRowNum() + 1);
            newRow.setRowStyle(cellStyle);
            newRow.createCell(0).setCellValue(transaction.getID());
            newRow.createCell(1).setCellValue(transaction.getStaffName());
            newRow.createCell(2).setCellValue(transaction.getStatus());
            newRow.createCell(3).setCellValue(transaction.getPurpose());
            newRow.createCell(4).setCellValue(transaction.getTransactionTime());
            newRow.createCell(5).setCellValue(transaction.getItemName());
        }
    }
}
