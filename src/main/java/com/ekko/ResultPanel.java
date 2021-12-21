package com.ekko;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static java.awt.Font.PLAIN;
import static javax.swing.JFileChooser.DIRECTORIES_ONLY;

public class ResultPanel extends JPanel{

    String[][] arrDevice;

    //设置字体
    Font TitlFont = new Font("Times New Roman",Font.BOLD,17);
    Color titilColor = new Color(139,126,102);
    //背景颜色
    Color bcakGround = new Color(255,250,205);


    //添加显示面板 网格布局
    GridLayout gridLayout = new GridLayout(1,4);
    //底部Panel
    JPanel rootPanel = new JPanel(gridLayout);

    //显示结果按钮
    //basic套餐
    JButton basicButton = new JButton();
    //advanced套餐
    JButton advancedButton = new JButton();
    //luxury套餐
    JButton luxuryButton = new JButton();



    public ResultPanel(String packageType,int totalRetedPower,int totalDayPower,int totalNightPower,
                       int NEPADayTime,int NEPANightTime,int genPower,int panleNumb,double solarFactor,String[][] arrDevice) {
        super();
        ResultPanel.this.arrDevice = arrDevice;

        rootPanel.add(basicButton);
        rootPanel.add(advancedButton);
        rootPanel.add(luxuryButton);
        //设置颜色
        basicButton.setBackground(bcakGround);
        advancedButton.setBackground(bcakGround);
        luxuryButton.setBackground(bcakGround);


        //接受结果的字符串
        MianProduct mianProduct = new MianProduct();
        //设置产品类型得到基础套餐包
        mianProduct.setpackageType(packageType);


        //升级套餐增加光伏板数量
        int addAdvancPanle = mianProduct.additionalPanel(packageType,totalDayPower,totalNightPower,NEPADayTime,NEPANightTime,solarFactor);
        //升级套餐增加电池数量
        int addAdvancBattery = mianProduct.additionalBattery(packageType,totalDayPower,NEPADayTime,totalNightPower,NEPANightTime,addAdvancPanle);


        //豪华套餐电池数量
        int luxuryPanel = mianProduct.luxuryPanel(packageType,totalDayPower,totalNightPower,solarFactor);
        int luxuryBattry = mianProduct.luxuryBattry(packageType,totalDayPower,totalNightPower,luxuryPanel);

        //Base套餐的显示
        String baseResultStr =
                "<html><body>" +
                        "<div style=\"font-size:15px;color:#000000\">-----------&nbsp;&nbsp;" + packageType + "&nbsp;&nbsp;-----------</div><br><br>" +

                        "<div style=\"width:50px;height:25px;float:left;display:inline;border:1px solid black;\">" +
                        "Inverter&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                        mianProduct.getInverter() + "</div><br>" +

                        "<div style=\"width:50px;height:25px;float:left;display:inline;border:1px solid black;\">" +
                        "2.5kwh Battery&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                        mianProduct.getBattery_3KWH() + "</div><br>" +

                        "<div style=\"width:50px;height:25px;float:left;display:inline;border:1px solid black;\">" +
                        "5kwh Battery&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                        mianProduct.getBattery_5KWH() + "</div><br>" +

                        "<div style=\"width:50px;height:25px;float:left;display:inline;border:1px solid black;\">" +
                        "Solar Panels&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                        mianProduct.getSolarPanel() + "</div><br><br><br><br><br><br><br><br>" +
                "<html><body>";

        //advanced套餐的显示
        String advancedResultStr =
                "<html><body>" +
                "<div style=\"font-size:15px;color:#000000\">-----------&nbsp;&nbsp;" + packageType + "&nbsp;&nbsp;-----------</div><br><br>" +

                "<div style=\"width:50px;height:25px;float:left;display:inline;border:1px solid black;\">" +
                "Inverter&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                mianProduct.getInverter() + "</div><br>" +

                "<div style=\"width:50px;height:25px;float:left;display:inline;border:1px solid black;\">" +
                "2.5kwh Battery&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                mianProduct.getBattery_3KWH() + "</div><br>" +

                "<div style=\"width:50px;height:25px;float:left;display:inline;border:1px solid black;\">" +
                "5kwh Battery&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                mianProduct.getBattery_5KWH() + "</div><br>" +

                "<div style=\"width:50px;height:25px;float:left;display:inline;border:1px solid black;\">" +
                "Solar Panels&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                mianProduct.getSolarPanel() + "</div><br>" +


                "<div style=\"font-size:15px;color:#000000\">----&nbsp;ADDITIONAL&nbsp;----</div><br>" +

                "<div style=\"width:50px;height:25px;float:left;display:inline;border:1px solid black;\">" +
                "5kwh Battery&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                addAdvancBattery + "</div><br>" +

                "<div style=\"width:50px;height:25px;float:left;display:inline;border:1px solid black;\">" +
                "Solar Panels&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                addAdvancPanle + "</div><br>" +

                "<body><html>";

        //luxury套餐的显示
        String luxuryResultStr =
                "<html><body>" +
                "<div style=\"font-size:15px;color:#000000\">-----------&nbsp;&nbsp;" + packageType + "&nbsp;&nbsp;-----------</div><br><br>" +

                "<div style=\"width:50px;height:25px;float:left;display:inline;border:1px solid black;\">" +
                "Inverter&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                mianProduct.getInverter() + "</div><br>" +

                "<div style=\"width:50px;height:25px;float:left;display:inline;border:1px solid black;\">" +
                "2.5kwh Battery&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                mianProduct.getBattery_3KWH() + "</div><br>" +

                "<div style=\"width:50px;height:25px;float:left;display:inline;border:1px solid black;\">" +
                "5kwh Battery&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                mianProduct.getBattery_5KWH() + "</div><br>" +

                "<div style=\"width:50px;height:25px;float:left;display:inline;border:1px solid black;\">" +
                "Solar Panels&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                mianProduct.getSolarPanel() + "</div><br>" +


                "<div style=\"font-size:15px;color:#000000\">----&nbsp;ADDITIONAL&nbsp;----</div><br>" +

                "<div style=\"width:50px;height:25px;float:left;display:inline;border:1px solid black;\">" +
                "5kwh Battery&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                luxuryBattry + "</div><br>" +

                "<div style=\"width:50px;height:25px;float:left;display:inline;border:1px solid black;\">" +
                "Solar Panels&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                luxuryPanel + "</div><br>" +

                "<body><html>";


        //结果显示在Button的Text内部
        basicButton.setText(baseResultStr);
        advancedButton.setText(advancedResultStr);
        luxuryButton.setText(luxuryResultStr);

        basicButton.setFont(TitlFont);
        advancedButton.setFont(TitlFont);
        luxuryButton.setFont(TitlFont);

        basicButton.setForeground(titilColor);
        advancedButton.setForeground(titilColor);
        luxuryButton.setForeground(titilColor);

        basicButton.addActionListener(new ButtonActionListener());
        advancedButton.addActionListener(new ButtonActionListener());
        luxuryButton.addActionListener(new ButtonActionListener());

    }


    class ButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            int result = 0;
            String path = null;
            JFileChooser fileChooser = new JFileChooser();

            //得到桌面路径
            FileSystemView fsv = FileSystemView.getFileSystemView();
            System.out.println(fsv.getHomeDirectory());

            fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
            fileChooser.setApproveButtonText("Confirm");
            fileChooser.setFileSelectionMode(DIRECTORIES_ONLY);
            result = fileChooser.showOpenDialog(null);

            if (JFileChooser.APPROVE_OPTION == result) {
                path = fileChooser.getSelectedFile().getPath() + "test.xls";
            }


            //创建工作簿
            Workbook workbook = new HSSFWorkbook();
            //创建工作表
            Sheet sheet1 = workbook.createSheet("Device");
            Sheet sheet2 = workbook.createSheet("Result");


            if (basicButton.isFocusable()) {
                //设置标题
                sheet1.createRow(1).createCell(0).setCellValue("Device Name");
                sheet1.createRow(1).createCell(1).setCellValue("Rated Power");
                sheet1.createRow(1).createCell(2).setCellValue("Starting Power");
                sheet1.createRow(1).createCell(3).setCellValue("Quantity");
                sheet1.createRow(1).createCell(4).setCellValue("Dayting Time");
                sheet1.createRow(1).createCell(5).setCellValue("Night Time");

                sheet2.createRow(1).createCell(0).setCellValue("Product package name");
                sheet2.createRow(2).createCell(0).setCellValue("Inverter");
                sheet2.createRow(3).createCell(0).setCellValue("2.5kwh Battery");
                sheet2.createRow(4).createCell(0).setCellValue("5kwh Battery");
                sheet2.createRow(5).createCell(0).setCellValue("Solar Panels");
                sheet2.createRow(6).createCell(0).setCellValue("ADD_5kwh Battery");
                sheet2.createRow(7).createCell(0).setCellValue("ADD_Solar Panels");

                //设置内容
                for(int i = 0; i < 20;i++){
                    for(int r = 0; r < 6; r++){
                        sheet1.createRow(i + 2).createCell(r).setCellValue(arrDevice[i][r]);
                    }
                }
            } else if (advancedButton.isFocusable()) {

            } else if (luxuryButton.isFocusable()) {

            }

            File file = new File(path);
            if(!file.exists()){
                try {
                    file.createNewFile();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
            //保存文件

            FileOutputStream fileOut = null;
//            try {
//                fileOut = new FileOutputStream(file);
//
//            } catch (FileNotFoundException fileNotFoundException) {
//                fileNotFoundException.printStackTrace();
//            }
//            try {
//                workbook.write(fileOut);
//                fileOut.close();
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }

        }
    }
}
