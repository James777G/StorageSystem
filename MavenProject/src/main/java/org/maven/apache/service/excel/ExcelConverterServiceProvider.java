package org.maven.apache.service.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.maven.apache.item.Item;
import org.maven.apache.service.item.ItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelConverterServiceProvider implements ExcelConverterService{

    @Resource
    private ItemService itemService;

    public ItemService getItemService() {
        return itemService;
    }

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public void convertToExcel(File file) throws IOException {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        convertItemToExcel(hssfWorkbook);

        hssfWorkbook.write(file);

    }

    private void convertItemToExcel(HSSFWorkbook hssfWorkbook){
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

        List<Item> itemList = itemService.selectAll();
        for(Item item : itemList){
            HSSFRow newRow = sheet.createRow(sheet.getLastRowNum() + 1);
            newRow.setRowStyle(cellStyle);
            newRow.createCell(0).setCellValue(item.getItemID());
            newRow.createCell(1).setCellValue(item.getItemName());
            newRow.createCell(2).setCellValue(item.getUnit());
            newRow.createCell(3).setCellValue(item.getDescription());
        }
    }
}
