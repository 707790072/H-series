package com.ekko;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.MetalTabbedPaneUI;
import java.awt.*;

public class MyDemo {
    public static void main(String[] args) {

        //设置框架
        JFrame frame = new JFrame("1");

        //设备输入面板
        JPanel deivcesPanel = new DevicePanel().rootPanel;

        //结果面板
        JPanel resultPanel = new ResultPanel().rootPanel;


        //Tab背景颜色为背景颜色
        UIManager.put("TabbedPane.background",new DevicePanel().bcakGround);
        //选中后显示黄色
        UIManager.put("TabbedPane.selected", new Color(255,211,155) );
        //设置选择框
        JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.LEFT,JTabbedPane.WRAP_TAB_LAYOUT);


        //tabbedPane重写IU重新编写大小
        tabbedPane.setUI(new MetalTabbedPaneUI() {
            @Override
            //重新设置宽度
            protected int calculateTabWidth(int tabPlacement, int tabIndex,
                                            FontMetrics metrics) {
                int width = super.calculateTabWidth(tabPlacement, tabIndex, metrics);
                int extra = tabIndex * 50;
                return width + extra;
            }
            //重新设置高度
            protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight)
            {
                return super.calculateTabHeight(tabPlacement, tabIndex, fontHeight)+20;
            }

        });

        //向选择框雷添加Tab
        tabbedPane.addTab("device",deivcesPanel);
        tabbedPane.addTab("result",resultPanel);


        //最底层面板
        JPanel rootPanel = new JPanel();
        //使用Border布局
        rootPanel.setLayout(new BorderLayout());
        //向最底层面板添加选项面板
        rootPanel.add(tabbedPane,BorderLayout.CENTER);
        rootPanel.setBackground(new DevicePanel().bcakGround);

        //向框架内添加底层面板
        frame.add(rootPanel);

        //退出时候退出整个程序
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //设置窗口的参数
        frame.setSize(920,730);

        //显示窗体
        frame.setVisible(true);
        frame.setResizable(false);


    }


}
