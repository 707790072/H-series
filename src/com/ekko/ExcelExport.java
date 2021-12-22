package com.ekko;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ExcelExport {

    String path = null;

    public void ExcelWrite(String[][] deviceArr) throws Exception {

        //创建工作簿
        Workbook workbook = new HSSFWorkbook();
        //创建工作表
        Sheet sheet1 = workbook.createSheet("Device");
        Sheet sheet2 = workbook.createSheet("Result");

        //路径选择框获取路径
        JFileChooser fileChooser = new JFileChooser();
        //获取桌面路径
        FileSystemView fsv = FileSystemView.getFileSystemView();
        fileChooser.setCurrentDirectory(fsv.getHomeDirectory());

        fileChooser.setDialogTitle("Please choose a save path");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(null);
        //选中确认键
        if (JFileChooser.APPROVE_OPTION == result) {
            path = fileChooser.getSelectedFile().getPath();

            if(path != null && !path.equals("")) {}else{
                path = String.valueOf(fsv.getHomeDirectory());
            }

            //创建单元格
            for (int i = 0; i < deviceArr.length; i++) {
                Row row = sheet1.createRow(i);
                for (int r = 0; r < 6; r++) {
                    row.createCell(r).setCellValue(deviceArr[i][r]);
                }
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date date = new Date();
            String dateFormat = simpleDateFormat.format(date);
            //生成表(IO流)
            System.out.println("path: " + path + "\\H-Series Package_" + dateFormat + ".xls");
            FileOutputStream fileOutputStream = new FileOutputStream(path + "\\H-SeriesPackage_" + dateFormat + ".xls");
            workbook.write(fileOutputStream);
            //关闭流
            fileOutputStream.close();
            JOptionPane.showMessageDialog(null, "<html><body>" + "Created successfully<br>"
                            + "Path：" + path + "<html><body>", "SUCCESS", JOptionPane.CLOSED_OPTION);
        }
    }

}
