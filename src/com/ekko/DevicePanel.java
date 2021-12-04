package com.ekko;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.EventListener;

import static java.awt.Font.PLAIN;


public class DevicePanel extends JPanel
{

    //添加输入设备的Jpanel容器，布局输入设备页面
    JPanel inputPanel = new JPanel();
    //添加结果输入的Jpanel容器
    JPanel resultPanel = new JPanel();
    //底层Jpanel容器
    JPanel rootPanel = new JPanel();


    //横向坐标开始坐标
    int x = 50;
    //纵向坐标开始的坐标
    final int Y = 65;
    int y = Y;
    //每个控件的宽度
    int controlHeight = 25;
    //每个控件之间的X距离
    int distanceWidth = 15;
    //每个控件之间的Y的距离
    int distanceHeight = controlHeight + 5 ;
    //TextField的长度
    int textWidth = 60;
    //TimeText的长度
    int TimeTextWidth = 40;
    //Combobox的长度
    int comboboxWidth = 150;
    //设置for循环需要产生的数量
    int numb = 20;

    //设置字体
    Font TitlFont = new Font("Times New Roman",Font.BOLD,14);
    Font TitlFont2 = new Font("Times New Roman",PLAIN,12);
    Color titilColor = new Color(139,126,102);
    //背景颜色
    Color bcakGround = new Color(255,250,205);
    //设备超出额定功率高亮
    Color highPower = new Color(255,106,106);
    //设备超出额定功率低亮
    Color lowPower = new Color(255,218,185);

    //获取DeviceArray的数据
    DevicesArray devicesArray = new DevicesArray();


    //设备下拉列表框
    JComboBox<String>[] devicesCom = new JComboBox[numb];
    // 额定功率文本输入框
    JTextField[] textPower = new JTextField[numb];
    // 设备开启功率率文本输入框
    JTextField[] textStartPower = new JTextField[numb];
    // 设备使用开始时间文本输入框
    JTextField[] textTimeStart = new JTextField[numb];
    // 设备使用结束时间文本输入框
    JTextField[] textTimeEnd = new JTextField[numb];
    // 设备数量文本输入框
    JTextField[] textNmuber = new JTextField[numb];
    //显示开始和结束时间的横杠
    JLabel[] Timelabel = new JLabel[numb];


    /*
    结果面板内容
    1.1 市电情况            文本框
    1.2 发电机的功率         文本框
    1.3 屋顶高度             文本框
    1.4 屋顶面积             文本框
    1.5 房子朝向             下拉选择框
    1.6 是否做到不断电         复选框：选中后直接推荐最佳配置，侧重点在储能推荐电池最优解
     */
    //市电文本框
    JTextField textNEPA = new JTextField();
    //发电机功率文本框
    JTextField textGen = new JTextField();
    //屋顶高度文本框
    JTextField textRoofHeight = new JTextField();
    //屋顶面积文本框
    JTextField textRoofArea = new JTextField();
    //房子朝向下拉框
    JComboBox comOrientation = new JComboBox();
    //是否永不断电选项
    JCheckBox Checkpermanent = new JCheckBox("<html><body>" +"Ensure Uninterrupted" + "<br>" + " Power Supply" + "<html><body>");
    JButton resultButton = new JButton("GET PACKAGE PLAN");


    public DevicePanel()
    {
        super();
        //将默认布局取消(重要)
        inputPanel.setLayout(null);
        inputPanel.setBackground(bcakGround);

        /*
                结果容器resultPanel
        1. 输入附加内容
            1.1 市电情况            文本框
            1.2 发电机的功率         文本框
            1.3 屋顶高度             文本框
            1.4 屋顶面积             文本框
            1.5 房子朝向             下拉选择框
            1.6 是否做到不断电         复选框：选中后直接推荐最佳配置，侧重点在储能推荐电池最优解

        2. 结果显示
            2.1 总功率
            2.2 最大启动功率
            2.3 日间使用总功率
            2.3 夜间使用总功率
            2.4 最大光伏板安装量

        3. 按钮
            3.1 检查是否有不合法的文本
                3.1.1 额定、启动功率是否等于0
                3.1.2 启动功率小于额定功率
                3.1.3 数量是否等于0
        */

        //将默认布局取消(重要)
        resultPanel.setLayout(null);
        //设置背景颜色
        resultPanel.setBackground(bcakGround);

        //循环遍历Label控件赋值
        int labelNumb = 11;
        String[] titilStr = new String[] {"<html><body>" +"NEPA(H)" + "<br>" + "Duration:" + "<html><body>",
                "<html><body>" +"Generator" + "<br>" + "Power(W):" + "<html><body>",
                "<html><body>" +"Roof" + "<br>" + "Height(M):" + "<html><body>",
                "<html><body>" +"Roof" + "<br>" + "Area(m*2):" + "<html><body>",
                "<html><body>" +"House" + "<br>" + "Orientation:" + "<html><body>",
                "",
                //显示结果
                "<html><body>" +"Total" + "<br>" + "Rated Power:" + "<html><body>",
                "<html><body>" +"Maximum" + "<br>" + "Starting Power:" + "<html><body>",
                "<html><body>" +"Total" + "<br>" + "Daytime Power:" + "<html><body>",
                "<html><body>" +"Total" + "<br>" + "Night Power:" + "<html><body>",
                "<html><body>" +"Maximum Panels" + "<br>" + "Can Be Installed:" + "<html><body>",
                };
        JLabel[] resultLabel = new JLabel[labelNumb];
        for(int i = 0; i < labelNumb; i++){
            resultLabel[i] = new JLabel(titilStr[i]);
            resultLabel[i].setFont(TitlFont2);
            if(i<5) resultLabel[i].setForeground(titilColor);
            else resultLabel[i].setForeground(Color.black);
            resultPanel.add(resultLabel[i]).setBounds(x-25,y-20,textWidth+30,controlHeight);
            y += distanceHeight + 10;
        }
        //还原赋值y
        y = Y;

        //市电情况
        resultPanel.add(textNEPA);
        textNEPA.setBackground(bcakGround);
        textNEPA.setHorizontalAlignment(JTextField.CENTER);
        textNEPA.setBounds(resultLabel[0].getX()+ resultLabel[0].getWidth()-25,resultLabel[0].getY(),textWidth+40,controlHeight);
        textNEPA.addKeyListener(new AdditionalTextKeyListener());
        //发电机的功率
        resultPanel.add(textGen);
        textGen.setBackground(bcakGround);
        textGen.setHorizontalAlignment(JTextField.CENTER);
        textGen.setBounds(resultLabel[1].getX()+ resultLabel[1].getWidth()-25,resultLabel[1].getY(),textWidth+40,controlHeight);
        textGen.addKeyListener(new AdditionalTextKeyListener());
        //屋顶高度
        resultPanel.add(textRoofHeight);
        textRoofHeight.setBackground(bcakGround);
        textRoofHeight.setHorizontalAlignment(JTextField.CENTER);
        textRoofHeight.setBounds(resultLabel[2].getX()+ resultLabel[2].getWidth()-25,resultLabel[2].getY(),textWidth+40,controlHeight);
        textRoofHeight.addKeyListener(new AdditionalTextKeyListener());
        //屋顶面积
        resultPanel.add(textRoofArea);
        textRoofArea.setBackground(bcakGround);
        textRoofArea.setHorizontalAlignment(JTextField.CENTER);
        textRoofArea.setBounds(resultLabel[3].getX()+ resultLabel[3].getWidth()-25,resultLabel[3].getY(),textWidth+40,controlHeight);
        textRoofArea.addKeyListener(new AdditionalTextKeyListener());
        //房子朝向
        resultPanel.add(comOrientation);
        comOrientation.setBackground(bcakGround);
        comOrientation.addItem("North-South");
        comOrientation.addItem("East-West");
        comOrientation.setSelectedIndex(-1);
        comOrientation.setBounds(resultLabel[4].getX()+ resultLabel[4].getWidth()-25,resultLabel[4].getY(),textWidth+40,controlHeight);
        //选择监听事件
        comOrientation.addKeyListener(new ComboKeyListener());

        //是否做到不断电
        resultPanel.add(Checkpermanent);
        Checkpermanent.setBackground(bcakGround);
        Checkpermanent.setFont(new Font("Times New Roman",Font.BOLD,13));
        Checkpermanent.setForeground(titilColor);
        Checkpermanent.setBounds(resultLabel[10].getX(),resultLabel[10].getY()+distanceHeight+10 ,comboboxWidth,controlHeight*2);
        //获取方案按钮
        resultPanel.add(resultButton);
        resultButton.setFont(TitlFont2);
        resultButton.setBackground(new Color(255,211,155));
        resultButton.setBounds(Checkpermanent.getX(),Checkpermanent.getY()+distanceHeight+60 ,textWidth+100,80);



        //设置文字框
        JLabel label1 = new JLabel("Devices(W)", JLabel.CENTER);
        JLabel label2 = new JLabel("<html><body>" +"Rated" + "<br>" + "Power(W)" + "<html><body>");
        JLabel label3 = new JLabel("<html><body>" +"Starting" + "<br>" + "Power(W)" + "<html><body>");
        JLabel label4 = new JLabel("Quantity");
        JLabel label5 = new JLabel("<html><body>" +"Start" + "<br>" + "Time" + "<html><body>");
        JLabel label6 = new JLabel("<html><body>" +"End" + "<br>" + "Time" + "<html><body>");

        label1.setFont(TitlFont);
        label2.setFont(TitlFont);
        label3.setFont(TitlFont);
        label4.setFont(TitlFont);
        label5.setFont(TitlFont);
        label6.setFont(TitlFont);
        label1.setForeground(titilColor);
        label2.setForeground(titilColor);
        label3.setForeground(titilColor);
        label4.setForeground(titilColor);
        label5.setForeground(titilColor);
        label6.setForeground(titilColor);


        inputPanel.add(label1).setBounds(x+30,y-35,100,controlHeight+10);
        inputPanel.add(label2).setBounds(x+165,y-40,100,controlHeight+10);
        inputPanel.add(label3).setBounds(x+240,y-40,100,controlHeight+10);
        inputPanel.add(label4).setBounds(x+315,y-35,100,controlHeight+10);
        inputPanel.add(label5).setBounds(x+390,y-40,100,controlHeight+10);
        inputPanel.add(label6).setBounds(x+450,y-40,100,controlHeight+10);


        /*
        1. 循环产生控件
        2. 每个控件按照 1~numb进行赋值
        3. devicesCom[i]  textPower[i]  textStartPower[i]  textUsedTime[i]  textNumber[i]
         */
        for (int i = 0; i < numb; i ++)
        {
            //设置下拉框样式，选择Devices
            devicesCom[i] = new JComboBox<>();
            inputPanel.add(devicesCom[i]);
            //循环下拉框数组
            for (int s = 0;s < devicesArray.getArray().size();s++)
            {
                devicesCom[i].addItem(devicesArray.getArray().get(s).getName());
            }
            //设置索引为-1
            devicesCom[i].setSelectedIndex(-1);
            devicesCom[i].setBounds(x,y,comboboxWidth,controlHeight);

            //设置设备平均功率文本框TextField
            textPower[i] = new JTextField();
            inputPanel.add(textPower[i]);
            textPower[i].setBounds(devicesCom[i].getX() + comboboxWidth + distanceWidth,y,textWidth,controlHeight);

            //设置启动功率的文本框
            textStartPower[i] = new JTextField();
            inputPanel.add(textStartPower[i]);
            textStartPower[i].setBounds(textPower[i].getX() + textWidth + distanceWidth ,y,textWidth,controlHeight);

            //设置数量文本框
            textNmuber[i] = new JTextField();
            inputPanel.add(textNmuber[i]);
            textNmuber[i].setBounds(textStartPower[i].getX() + textWidth + distanceWidth,y,textWidth,controlHeight);

            //设置设备开始时间的文本框
            textTimeStart[i] = new JTextField();
            inputPanel.add(textTimeStart[i]);
            textTimeStart[i].setBounds(textNmuber[i].getX() + textWidth + distanceWidth,y,TimeTextWidth,controlHeight);


            Timelabel[i] = new JLabel("--");
            inputPanel.add(Timelabel[i]);
            Timelabel[i].setBounds(textTimeStart[i].getX() + TimeTextWidth+3 ,y,15,controlHeight);

            //设置设备结束时间
            textTimeEnd[i] = new JTextField();
            inputPanel.add(textTimeEnd[i]);
            textTimeEnd[i].setBounds(textTimeStart[i].getX() + TimeTextWidth + distanceWidth,y,TimeTextWidth,controlHeight);

            //JTextField 字体居中显示
            textPower[i].setHorizontalAlignment(JTextField.CENTER);
            textStartPower[i].setHorizontalAlignment(JTextField.CENTER);
            textTimeStart[i].setHorizontalAlignment(JTextField.CENTER);
            textTimeEnd[i].setHorizontalAlignment(JTextField.CENTER);
            textNmuber[i].setHorizontalAlignment(JTextField.CENTER);

            //设置控件颜色
            devicesCom[i].setBackground(bcakGround);
            textPower[i].setBackground(bcakGround);
            textStartPower[i].setBackground(bcakGround);
            textTimeStart[i].setBackground(bcakGround);
            textTimeEnd[i].setBackground(bcakGround);
            textNmuber[i].setBackground(bcakGround);


            //Text框默认禁用状态
            textPower[i].setEnabled(false);
            textStartPower[i].setEnabled(false);
            textTimeStart[i].setEnabled(false);
            textTimeEnd[i].setEnabled(false);
            textNmuber[i].setEnabled(false);

            //设置每个控件之间的Y距离
            y+= distanceHeight;


            /*添加Combobox选中事件
            1、选中Combobox中某一个设备后，将设备的额定功率、启动功率、平均使用时长显示出来
            2、TextFiel 只可以填写数字
            3、可以自行修改设备的额定功率，如果设备的额定功率超过了默认值的50%填充黄色，如果填写的设备的额定功率超过默认值的100%填充红色并且提示填写值的正确性
            */

            //下拉框选中事件
            devicesCom[i].addActionListener(new comboActionListener());
            //下拉框键盘监听事件
            devicesCom[i].addKeyListener(new ComboKeyListener());
            //Text键盘监听事件
            textPower[i].addKeyListener(new DeviceTextKeyListener());
            textStartPower[i].addKeyListener(new DeviceTextKeyListener());
            textTimeStart[i].addKeyListener(new DeviceTextKeyListener());
            textTimeEnd[i].addKeyListener(new DeviceTextKeyListener());
            textNmuber[i].addKeyListener(new DeviceTextKeyListener());

        }
        //还原赋值y
        y = Y;

        //inputPanel生成标题框控件
        TitledBorder border = new TitledBorder("DEVICES INPUT");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        border.setTitleFont(new Font("Times New Roman",Font.BOLD,16));
        border.setTitleColor(new Color(105,105,105));
        inputPanel.setBorder(border);

        //resultPanel生成标题控件
        TitledBorder border2 = new TitledBorder("RESULT");
        border2.setTitleJustification(TitledBorder.CENTER);
        border2.setTitlePosition(TitledBorder.TOP);
        border2.setTitleFont(new Font("Times New Roman",Font.BOLD,16));
        border2.setTitleColor(new Color(105,105,105));
        resultPanel.setBorder(border2);


        //将设备输入面板和结果面板添加到跟面板下
        rootPanel.setLayout(null);
        rootPanel.setBackground(new Color(255,250,205));
        rootPanel.add(inputPanel);
        inputPanel.setBounds(0,0,590,685);
        rootPanel.add(resultPanel);
        resultPanel.setBounds(590,0,210,685);


    }






    /*Combobox
        选项选中事件
    1. 选中某一项后，索引和 DeviceArray的ID进行对照
    2. 显示在Text中对应的信息
    3. 启用Text框
    */
    class comboActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < numb; i++) {
                for (int s = 0; s < devicesArray.getArray().size(); s++) {
                    if (devicesCom[i].isFocusOwner() && devicesCom[i].getSelectedIndex() == devicesArray.getArray().get(s).getId()) {
                        //启用Text框
                        textPower[i].setEnabled(true);
                        textStartPower[i].setEnabled(true);
                        textTimeStart[i].setEnabled(true);
                        textTimeEnd[i].setEnabled(true);
                        textNmuber[i].setEnabled(true);
                        //TextFiles显示数据
                        textPower[i].setText(getDoubleString(devicesArray.getArray().get(s).getAvgPower()));
                        textStartPower[i].setText(getDoubleString(devicesArray.getArray().get(s).getStartPower()));
                        textTimeStart[i].setText(getDoubleString(devicesArray.getArray().get(s).getTimeStart()));
                        textTimeEnd[i].setText(getDoubleString(devicesArray.getArray().get(s).getTimeEnd()));
                        textNmuber[i].setText("1");
                        //初始化Text的颜色
                        textPower[i].setBackground(bcakGround);
                        textStartPower[i].setBackground(bcakGround);
                        textTimeStart[i].setBackground(bcakGround);
                        textTimeEnd[i].setBackground(bcakGround);
                        textNmuber[i].setBackground(bcakGround);
                    }
                }
            }
        }
    }

    /*
        Combobox的键盘监听事件
    1. BackSpace、空格 删除 键将Combobox的索引指向 -1
    2. 按键后将Text框禁用
    */
    class ComboKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            for (int i = 0; i < numb; i++) {
                //判断是否焦点在控件上
                if (keyCode == KeyEvent.VK_BACK_SPACE || keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_ESCAPE) {
                    //返回、BackSpace、空格 删除选择
                    if (devicesCom[i].isFocusOwner()) {
                        devicesCom[i].setSelectedIndex(-1);
                        textPower[i].setText("");
                        textStartPower[i].setText("");
                        textTimeStart[i].setText("");
                        textTimeEnd[i].setText("");
                        textNmuber[i].setText("");
                        //禁用Text框
                        textPower[i].setEnabled(false);
                        textStartPower[i].setEnabled(false);
                        textTimeStart[i].setEnabled(false);
                        textTimeEnd[i].setEnabled(false);
                        textNmuber[i].setEnabled(false);
                        //初始化Text的颜色
                        textPower[i].setBackground(bcakGround);
                        textStartPower[i].setBackground(bcakGround);
                        textTimeStart[i].setBackground(bcakGround);
                        textTimeEnd[i].setBackground(bcakGround);
                        textNmuber[i].setBackground(bcakGround);
                    }
                }
            }

                if (keyCode == KeyEvent.VK_BACK_SPACE || keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_ESCAPE) {
                    if (comOrientation.isFocusOwner()) comOrientation.setSelectedIndex(-1);
            }
        }
    }


    /*
           TextField 的键盘监听事件
    1. 只允许输入数字
    2. 如果为空值将“0”填数Text框中
    3. 可以自行修改设备的额定功率，如果设备的额定功率超过了默认值的60%填充黄色，如果填写的设备的额定功率超过默认值的300%填充红色并且提示填写值的正确性
    */
    class DeviceTextKeyListener extends KeyAdapter {
        @Override
        //键盘输入事件，执行程序，后Text内得到值
        public void keyTyped(KeyEvent e) {
            super.keyTyped(e);

            //DeviceInput
            for (int i = 0; i < numb; i++) {
                if ( e.getKeyChar() >= KeyEvent.VK_0 && e.getKeyChar() <= KeyEvent.VK_9 || e.getKeyChar() == KeyEvent.VK_BACK_SPACE){
                    //设备功率判断 黄色为特殊，红色为警告
                    if (textPower[i].isFocusOwner() && devicesCom[i].getSelectedIndex() != 0)
                    {
                        double arrayPower = devicesArray.getArray().get(devicesCom[i].getSelectedIndex()).getAvgPower();
                        double arrayPowerFactor = devicesArray.getArray().get(devicesCom[i].getSelectedIndex()).getPowerFactor();

                        // 额定功率Text颜色填充
                        if (textPower[i].isFocusOwner() && textPower[i].getText().length() > 0 && e.getKeyChar() != KeyEvent.VK_BACK_SPACE)
                        {
                            int power = Integer.parseInt(String.valueOf(textPower[i].getText()) + String.valueOf(e.getKeyChar()));
                            if(2 * arrayPower >= power && power >= 0.6 * arrayPower)
                            {
                                textPower[i].setBackground(bcakGround);
                            }else if(3 * arrayPower < power || 0.2 * arrayPower > power || power == 0)
                            {
                                textPower[i].setBackground(highPower);
                            }else{
                                textPower[i].setBackground(lowPower);
                            }
                            //设置启动功率跟随额定功率变化
                            textStartPower[i].setText(getDoubleString(power * arrayPowerFactor));

                        }else if( textPower[i].getText().length() > 0 && e.getKeyChar() == KeyEvent.VK_BACK_SPACE){
                            int power = Integer.parseInt(textPower[i].getText());
                            if(2 * arrayPower >= power && power >= 0.6 * arrayPower)
                            {
                                textPower[i].setBackground(bcakGround);
                            }else if(3 * arrayPower < power || 0.2 * arrayPower > power || power == 0)
                            {
                                textPower[i].setBackground(highPower);
                            }else{
                                textPower[i].setBackground(lowPower);
                            }
                            textStartPower[i].setText(getDoubleString(power * arrayPowerFactor));
                        }
                    }
                }

                //只允许输入数字
                if (e.getKeyChar() >= KeyEvent.VK_0 && e.getKeyChar() <= KeyEvent.VK_9 || e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                    if (textPower[i].isFocusOwner() && textPower[i].getText().length() > 4){
                        e.consume();
                    }else if(textPower[i].isFocusOwner() && textPower[i].getText().length() == 0){
                        textPower[i].setText("0");
                    }else if (textPower[i].isFocusOwner() && textPower[i].getText().length() > 0){
                        textPower[i].setText(textPower[i].getText().replaceFirst("^0*",""));
                    }

                    if (textStartPower[i].isFocusOwner() && textStartPower[i].getText().length() > 4) {
                        e.consume();
                    }else if(textStartPower[i].isFocusOwner() && textStartPower[i].getText().length() == 0){
                        textStartPower[i].setText("0");
                    }else if(textStartPower[i].isFocusOwner() && textStartPower[i].getText().length() > 0){
                        textStartPower[i].setText(textStartPower[i].getText().replaceFirst("^0*",""));
                    }

                    if (textNmuber[i].isFocusOwner() && textNmuber[i].getText().length() > 1) {
                        e.consume();
                    }else if(textNmuber[i].isFocusOwner() && textNmuber[i].getText().length() == 0){
                        textNmuber[i].setText("0");
                    }else if(textNmuber[i].isFocusOwner() && textNmuber[i].getText().length() > 0){
                        textNmuber[i].setText(textNmuber[i].getText().replaceFirst("^0*",""));
                    }



                }else{e.consume();}
            }
        }

        //键盘弹起事件，Text先得到值，然后执行
        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e);
            for (int i = 0; i < numb; i++) {
                //只允许输入数字
                if (e.getKeyChar() >= KeyEvent.VK_0 && e.getKeyChar() <= KeyEvent.VK_9 || e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                    if (textTimeStart[i].isFocusOwner() && textTimeStart[i].getText().length() > 0) {
                        textTimeStart[i].setText(textTimeStart[i].getText().replaceFirst("^0*", ""));
                        textTimeStart[i].setText(TimeInputText(textTimeStart[i].getText()));
                    }else if(textTimeStart[i].isFocusOwner() && textTimeStart[i].getText().length() == 0){
                        textTimeStart[i].setText("0");
                    }

                    if (textTimeEnd[i].isFocusOwner() && textTimeEnd[i].getText().length() > 0) {
                        textTimeEnd[i].setText(textTimeEnd[i].getText().replaceFirst("^0*", ""));
                        textTimeEnd[i].setText(TimeInputText(textTimeEnd[i].getText()));
                    }else if(textTimeEnd[i].isFocusOwner() && textTimeEnd[i].getText().length() == 0){
                        textTimeEnd[i].setText("0");
                    }
                }else{e.consume();}
            }
        }
    }
    //附加输入框
    class AdditionalTextKeyListener extends KeyAdapter {

        //键盘输入事件，执行程序，后Text内得到值
        @Override
        public void keyTyped(KeyEvent e){
            if (e.getKeyChar() >= KeyEvent.VK_0 && e.getKeyChar() <= KeyEvent.VK_9 || e.getKeyChar() == KeyEvent.VK_BACK_SPACE){
                if(textNEPA.isFocusOwner() && textNEPA.getText().length() > 1 ) { e.consume();}
                if(textGen.isFocusOwner() && textGen.getText().length() > 4 ){e.consume();}
                if(textRoofArea.isFocusOwner() && textRoofArea.getText().length() > 3 ){e.consume();}
                if(textRoofHeight.isFocusOwner() && textRoofHeight.getText().length() > 2 ){e.consume();}
            }
        }

        //键盘弹起事件，Text先得到值，然后执行
        @Override
        public void keyReleased(KeyEvent e){
            if (e.getKeyChar() >= KeyEvent.VK_0 && e.getKeyChar() <= KeyEvent.VK_9 || e.getKeyChar() == KeyEvent.VK_BACK_SPACE){
                //不可以大于24小时
                if(textNEPA.isFocusOwner() && textNEPA.getText().length() > 0){
                    textNEPA.setText(textNEPA.getText().replaceFirst("^0*", ""));
                    textNEPA.setText(TimeInputText(textNEPA.getText()));
                }else if(textNEPA.isFocusOwner() && textNEPA.getText().length() == 0){
                    textNEPA.setText("0");
                }



            }
        }

    }






    //将浮点类型转换为字符串类型方法
    public String getDoubleString(double number) {
        number = (double) Math.round(number * 100) / 100;
        DecimalFormat df = new DecimalFormat("#");
        String str = df.format(number);
        return str;
    }


    //text框的格式方法
    public String setText(String str) {
        if (str.length() == 0) {
            str = "0";
        }else if (str.length() > 0) {
            str = str.replaceFirst("^0*","");
        }
        return str;
    }

    public String TimeInputText(String str) {
        if (str.length() > 0 && Integer.parseInt(str) > 24) return "24";
        else if (str.length() > 2) return "24";
        return str;
    }


}
