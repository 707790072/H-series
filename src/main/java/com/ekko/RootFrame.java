package com.ekko;

import javax.swing.*;
import javax.swing.plaf.metal.MetalTabbedPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RootFrame {

    //设置框架
    JFrame frame = new JFrame("123");
    //结果面板
    ResultPanel resultPanel = new ResultPanel("false",0,0,
            0, 0, 0,0,0,0,null);
    //Tab
    JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.LEFT,JTabbedPane.WRAP_TAB_LAYOUT);
    //设备输入面板
    DevicePanel deivcesPanel = new DevicePanel();

    public RootFrame(){


        //Tab背景颜色为背景颜色
        UIManager.put("TabbedPane.background",new DevicePanel().backGround);
        //选中后显示黄色
        UIManager.put("TabbedPane.selected", new Color(255,211,155) );


        //tabbedPane重写IU重新编写大小
        tabbedPane.setUI(new MetalTabbedPaneUI() {
            @Override
            //重新设置宽度
            protected int calculateTabWidth(int tabPlacement, int tabIndex,
                                            FontMetrics metrics) {
                int width = super.calculateTabWidth(tabPlacement, tabIndex, metrics);
                int extra = tabIndex + 35;
                return width + extra;
            }
            //重新设置高度
            protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight)
            {
                return super.calculateTabHeight(tabPlacement, tabIndex, fontHeight) + 20;
            }

        });

        //向选择框内添加Tab
        tabbedPane.addTab("device",deivcesPanel.rootPanel);

        deivcesPanel.resultButton.addActionListener(new ButtonActionListener());


        //最底层面板
        JPanel rootPanel = new JPanel();
        //使用Border布局
        rootPanel.setLayout(new BorderLayout());
        //向最底层面板添加选项面板
        rootPanel.add(tabbedPane,BorderLayout.CENTER);
        rootPanel.setBackground(new DevicePanel().backGround);

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

    //按钮出发事件
    class ButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            //Button点击触发事件
            if(deivcesPanel.resultButton.isFocusOwner()){

                //定义接收的二位数组
                String[][] arrDevice = new String[20][6];
                //获取Device和Resilt的页面信息
                for(int i = 0;i < 20; i++){
                    for(int r = 0;r < 6 ;r++){
                        if(deivcesPanel.devicesCom[i].getSelectedIndex() > -1) {
                            switch (r) {
                                case 0:
                                    arrDevice[i][r] = String.valueOf(deivcesPanel.devicesCom[i].getSelectedItem());
                                    break;

                                case 1:
                                    arrDevice[i][r] = deivcesPanel.textPower[i].getText();
                                    break;

                                case 2:
                                    arrDevice[i][r] = deivcesPanel.textStartPower[i].getText();
                                    break;

                                case 3:
                                    arrDevice[i][r] = deivcesPanel.textNmuber[i].getText();
                                    break;

                                case 4:
                                    arrDevice[i][r] = deivcesPanel.textDayTime[i].getText();
                                    break;

                                case 5:
                                    arrDevice[i][r] = deivcesPanel.textNightTime[i].getText();
                                    break;

                            }
                        }
                    }
                }


                /* 产品套餐生成逻辑
                * 1. 基础套餐判断条件
                    1.1 开启功率必须小于套餐逆变器的80%
                    1.2 额定功率必须小于套餐逆变器的60%
                */
                if(deivcesPanel.getTotalRatedPower() > 0 && deivcesPanel.getTotalStartPower() > 0 && deivcesPanel.textNightNEPA.getText().length() > 0
                        && deivcesPanel.textDayNEPA.getText().length() > 0 && deivcesPanel.textGen.getText().length() > 0
                        && deivcesPanel.textRoofArea.getText().length() > 0 && deivcesPanel.comOrientation.getSelectedIndex() > -1){

                    MianProduct mianProduct = new MianProduct();
                    String packageType = mianProduct.getbasicPackage(deivcesPanel.getTotalRatedPower(),deivcesPanel.getTotalStartPower());

                    //判断套餐类型是否是false
                    if(packageType.equals("false")){
                        JOptionPane.showMessageDialog(null, "Exceeds the standard package type (25KW)", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }else{
                        if(RootFrame.this.tabbedPane.getTabCount() > 1) {
                            //删除之前的结果
                            RootFrame.this.tabbedPane.remove(tabbedPane.getTabCount() - 1);
                            //给ResultPanel赋值
                            RootFrame.this.tabbedPane.addTab("结果", new ResultPanel(
                                    packageType,
                                    deivcesPanel.getTotalRatedPower(),
                                    deivcesPanel.getTotalDayPower(),
                                    deivcesPanel.getTotalNightPower(),
                                    Integer.valueOf(deivcesPanel.textDayNEPA.getText()),
                                    Integer.valueOf(deivcesPanel.textNightNEPA.getText()),
                                    Integer.valueOf(deivcesPanel.textGen.getText()),
                                    Integer.valueOf(deivcesPanel.getPanleNumb()),
                                    Double.valueOf(deivcesPanel.getSolarFactor()),
                                    arrDevice
                            ).rootPanel);

                            RootFrame.this.tabbedPane.setSelectedIndex(1);
                            JOptionPane.showMessageDialog(null, "Product package results have been updated", "SUCCESS", JOptionPane.CLOSED_OPTION);
                        }else{
                            //给ResultPanel赋值
                            RootFrame.this.tabbedPane.addTab("结果", new ResultPanel(
                                    packageType,
                                    deivcesPanel.getTotalRatedPower(),
                                    deivcesPanel.getTotalDayPower(),
                                    deivcesPanel.getTotalNightPower(),
                                    Integer.valueOf(deivcesPanel.textDayNEPA.getText()),
                                    Integer.valueOf(deivcesPanel.textNightNEPA.getText()),
                                    Integer.valueOf(deivcesPanel.textGen.getText()),
                                    Integer.valueOf(deivcesPanel.getPanleNumb()),
                                    Double.valueOf(deivcesPanel.getSolarFactor()),
                                    arrDevice
                            ).rootPanel);
                            RootFrame.this.tabbedPane.setSelectedIndex(1);
                        }
                    }
                }else {
                    JOptionPane.showMessageDialog(null, "Please fill in the complete information", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }


}
