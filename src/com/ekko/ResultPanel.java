package com.ekko;

import javax.swing.*;
import java.awt.*;

import static java.awt.Font.PLAIN;

public class ResultPanel extends JPanel{

    //字体设置
    Font TitlFont = new Font("Times New Roman",Font.BOLD,13);
    Font TitlFont2 = new Font("Times New Roman",PLAIN,12);
    Color titilColor = new Color(139,126,102);
    //背景颜色
    Color backGround = new Color(255,250,205);

    //根部Pannel 边界布局
    JPanel rootPanel = new JPanel(new BorderLayout());

    //标题数组
    ProductPackage.MianProduct columnName = new ProductPackage.MianProduct("H1");

    //JTable内容数组
    String[][] content = new String[][]{{"1","1","1","1"},{"2","2","2","1"},{"3","3","3","1"}};

    //显示页面Panel 布局为网格布局
    JPanel showPanel = new JPanel(new BorderLayout());
    //网格
    JTable showTable = new JTable();



    //底部按钮页面
    JPanel conformPanel = new JPanel();



    public ResultPanel() {
        super();

        //根部布局为边界布局
        //showPanel 为BorderLayout.SOUTH
        //conformPanel 为BorderLayout.CENTER
        rootPanel.add(showPanel,BorderLayout.CENTER);
        rootPanel.add(conformPanel,BorderLayout.SOUTH);

        //设置背景颜色
        showPanel.setBackground(Color.yellow);
        conformPanel.setBackground(Color.red);

        //showPanel添加Label为网格布局
        showPanel.add(showTable,BorderLayout.CENTER);
        showTable.setBackground(backGround);



    }



}
