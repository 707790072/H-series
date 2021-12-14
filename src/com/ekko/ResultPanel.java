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

    public ResultPanel() {
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
        ProductPackage productPackage = new ProductPackage();
        String resultStr = new String();
        resultStr = "<html><body>" +"Inverter: " + " 2 " + "<br><br>" + "Duration:" + "<html><body>";
        //结果显示在Button的Text内部
        basicButton.setText(resultStr);



    }



}
