package com.ekko;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ExcelExport{

    String path = "C:\\Users\\Public\\Desktop";

    public void ExcelWrite(String[][] arrDevice) throws Exception {
        //创建工作簿
        Workbook workbook = new HSSFWorkbook();
        //创建工作表
        Sheet sheet1 = workbook.createSheet("Device");
        Sheet sheet2 = workbook.createSheet("Result");

        //创建单元格
        for(int i = 0; i < 20;i++){
            for(int r = 0; r < 6; r++){
                sheet1.createRow(i).createCell(r).setCellValue(arrDevice[i][r]);
            }
        }

        //生成表(IO流)
        FileOutputStream fileOutputStream = new FileOutputStream(path + "Test.xls");
        workbook.write(fileOutputStream);
        //关闭流
        fileOutputStream.close();

    }

}