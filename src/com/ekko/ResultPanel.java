package com.ekko;

import javax.swing.*;
import java.awt.*;
import static java.awt.Font.PLAIN;

public class ResultPanel extends JPanel{

    //设置字体
    Font TitlFont = new Font("Times New Roman",Font.BOLD,13);
    Font TitlFont2 = new Font("Times New Roman",PLAIN,12);
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


    public ResultPanel(String packageType,int totalDayPower,int totalNightPower,int NEPADayTime,int NEPANightTime,int genPower,int panleNumb,double solarFactor) {
        super();

        rootPanel.add(basicButton);
        rootPanel.add(advancedButton);
        rootPanel.add(luxuryButton);
        rootPanel.add(continuousButton);
        //设置颜色
        basicButton.setBackground(bcakGround);
        advancedButton.setBackground(bcakGround);
        luxuryButton.setBackground(bcakGround);
        continuousButton.setBackground(bcakGround);

        //接受结果的字符串
        MianProduct mianProduct = new MianProduct();
        //设置产品类型得到基础套餐包
        mianProduct.setpackageType(packageType);
        //升级套餐电池数量
        int advancedAdditionalBattry = mianProduct.additionalBattery(packageType,totalNightPower,1.5);
        //豪华套餐电池数量
        int luxuryAdditionalBattry = mianProduct.additionalBattery(packageType,totalNightPower,2);

        int additionalPanel = mianProduct.additionalPanel(packageType,totalDayPower,NEPADayTime,solarFactor);

        //Base套餐的显示
        String baseResultStr = "<html><body>" +
                "Inverter: " + mianProduct.getInverter() + "<br><br>" +
                "2.5KWH Battery: " + mianProduct.getBattery_3KWH() + "<br><br>" +
                "5KWH Battery: " + mianProduct.getBattery_5KWH() + "<br><br>" +
                "Solar Panels: " + mianProduct.getSolarPanel() + "<br><br>" +
                "<html><body>";

        //advanced套餐的显示
        String advancedResultStr = "<html><body>" +
                "Inverter: " + mianProduct.getInverter() + "<br><br>" +
                "2.5KWH Battery: " + mianProduct.getBattery_3KWH() + "<br><br>" +
                "5KWH Battery: " + mianProduct.getBattery_5KWH() + "<br><br>" +
                "Additional 5KWH Battery: " + advancedAdditionalBattry + "<br><br>" +
                "Solar Panels: " + mianProduct.getSolarPanel() + "<br><br>" +
                "Additional Solar Panels: " + additionalPanel + "<br><br>" +
                "<html><body>";

        //advanced套餐的显示
        String luxuryResultStr = "<html><body>" +
                "Inverter: " + mianProduct.getInverter() + "<br><br>" +
                "2.5KWH Battery: " + mianProduct.getBattery_3KWH() + "<br><br>" +
                "5KWH Battery: " + mianProduct.getBattery_5KWH() + "<br><br>" +
                "Additional 5KWH Battery: " + luxuryAdditionalBattry + "<br><br>" +
                "Solar Panels: " + mianProduct.getSolarPanel() + "<br><br>" +
                "Additional Solar Panels: " + additionalPanel + "<br><br>" +
                "<html><body>";


        //结果显示在Button的Text内部
        basicButton.setText(baseResultStr);
        advancedButton.setText(advancedResultStr);
        luxuryButton.setText(luxuryResultStr);
    }

}
