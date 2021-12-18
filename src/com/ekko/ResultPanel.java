package com.ekko;

import javax.swing.*;
import java.awt.*;
import static java.awt.Font.PLAIN;

public class ResultPanel extends JPanel{


    //设置字体
//    Font TitlFont = new Font("Times New Roman",Font.BOLD,15);
    Font TitlFont = new Font("Times New Roman",Font.BOLD,16);
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
    //continuous套餐
    JButton continuousButton = new JButton();


    public ResultPanel(String packageType,int totalRetedPower,int totalDayPower,int totalNightPower,
                       int NEPADayTime,int NEPANightTime,int genPower,int panleNumb,double solarFactor) {
        super();
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
        String baseResultStr = "<html><body>" +
                "Package Name: " + packageType + "<br><br>" +
                "Inverter: " + mianProduct.getInverter() + "<br><br>" +
                "2.5KWH Battery: " + mianProduct.getBattery_3KWH() + "<br><br>" +
                "5KWH Battery: " + mianProduct.getBattery_5KWH() + "<br><br>" +
                "Solar Panels: " + mianProduct.getSolarPanel() + "<br><br><br><br><br><br><br><br><br><br>" +
                "<html><body>";

        //advanced套餐的显示
        String advancedResultStr = "<html><body>" +
                "Package Name: " + packageType + "<br><br>" +
                "Inverter: " + mianProduct.getInverter() + "<br><br>" +
                "2.5KWH Battery: " + mianProduct.getBattery_3KWH() + "<br><br>" +
                "5KWH Battery: " + mianProduct.getBattery_5KWH() + "<br><br>" +
                "Solar Panels: " + mianProduct.getSolarPanel() + "<br><br><br><br>" +
                "<div style=\"color:#000000\t\">" +
                "ADDITIONAL :  <br><br>" +
                "5KWH Battery: " + addAdvancBattery + "<br><br>" +
                "Solar Panels: " + addAdvancPanle  + "<br><br>" +
                "<div style=\"color:#000000\">" +
                "<html><body>";

        //luxury套餐的显示
        String luxuryResultStr = "<html><body>" +
                "<div style=\"color:#000000\t\">" +
                 packageType + "<br><br><br><br>" +
                "Inverter: " + mianProduct.getInverter() + "<br><br>" +
                "2.5KWH Battery: " + mianProduct.getBattery_3KWH() + "<br><br>" +
                "5KWH Battery: " + mianProduct.getBattery_5KWH() + "<br><br>" +
                "Solar Panels: " + mianProduct.getSolarPanel() + "<br><br><br><br>" +
                "<div style=\"color:#000000\t\">" +
                "ADDITIONAL :  <br><br>" +
                "5KWH Battery: " + luxuryBattry + "<br><br>" +
                "Solar Panels: " + luxuryPanel  + "<br><br>" +
                "<div style=\"color:#000000\">" +
                "<html><body>";


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

    }

}
