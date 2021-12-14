package com.ekko;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
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


    //计算使用的数据
    //总平均功率
    private int totalRatedPower = 0;
    //启动功率
    private int totalStartPower = 0;
    //日间功率
    private int totalDayPower = 0;
    //夜间功率
    private int totalNightPower = 0;
    //最大铺设光伏板数量
    private int panleNumb = 0;

    public int getTotalRatedPower() {
        return totalRatedPower;
    }
    public int getTotalStartPower() {
        return totalStartPower;
    }
    public int getTotalDayPower() {
        return totalDayPower;
    }
    public int getTotalNightPower() {
        return totalNightPower;
    }
    public int getPanleNumb() {
        return panleNumb;
    }

    //设置字体
    Font TitlFont = new Font("Times New Roman",Font.BOLD,13);
    Font TitlFont2 = new Font("Times New Roman",PLAIN,12);
    Color titilColor = new Color(139,126,102);
    //背景颜色
    Color backGround = new Color(255,250,205);
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
    JTextField[] textDayTime = new JTextField[numb];
    // 设备使用结束时间文本输入框
    JTextField[] textNightTime = new JTextField[numb];
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

    int[] resletLabelArr= new int[] {totalRatedPower,totalStartPower,totalDayPower,totalNightPower,panleNumb};
    JLabel[] resultLabel = new JLabel[resletLabelArr.length];


    public DevicePanel()
    {
        super();
        //将默认布局取消(重要)
        inputPanel.setLayout(null);
        inputPanel.setBackground(backGround);

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
        resultPanel.setBackground(backGround);

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
        JLabel[] resultTitlLabel = new JLabel[labelNumb];
        for(int i = 0; i < labelNumb; i++){
            resultTitlLabel[i] = new JLabel(titilStr[i]);
            resultTitlLabel[i].setFont(TitlFont2);
            if(i<5) resultTitlLabel[i].setForeground(titilColor);
            else resultTitlLabel[i].setForeground(Color.black);
            resultPanel.add(resultTitlLabel[i]).setBounds(x-25,y-20,textWidth+30,controlHeight);
            y += distanceHeight + 10;
        }
        //还原赋值y
        y = Y;

        //结果显示
        y=0;
        for(int i = 0; i < resletLabelArr.length; i++) {
            resultLabel[i] = new JLabel(String.valueOf(resletLabelArr[i]));
            resultLabel[i].setFont(TitlFont);
            resultPanel.add(resultLabel[i]).setBounds(resultTitlLabel[6].getX()+textWidth+50,resultTitlLabel[6].getY()+y,textWidth+30,controlHeight);
            y += distanceHeight + 10;
        }
        //还原赋值y
        y = Y;


        //市电情况
        resultPanel.add(textNEPA);
        textNEPA.setBackground(backGround);
        textNEPA.setHorizontalAlignment(JTextField.CENTER);
        textNEPA.setBounds(resultTitlLabel[0].getX()+ resultTitlLabel[0].getWidth()-25,resultTitlLabel[0].getY(),textWidth+40,controlHeight);

        //发电机的功率
        resultPanel.add(textGen);
        textGen.setBackground(backGround);
        textGen.setHorizontalAlignment(JTextField.CENTER);
        textGen.setBounds(resultTitlLabel[1].getX()+ resultTitlLabel[1].getWidth()-25,resultTitlLabel[1].getY(),textWidth+40,controlHeight);

        //屋顶高度
        resultPanel.add(textRoofHeight);
        textRoofHeight.setBackground(backGround);
        textRoofHeight.setHorizontalAlignment(JTextField.CENTER);
        textRoofHeight.setBounds(resultTitlLabel[2].getX()+ resultTitlLabel[2].getWidth()-25,resultTitlLabel[2].getY(),textWidth+40,controlHeight);

        //屋顶面积
        resultPanel.add(textRoofArea);
        textRoofArea.setBackground(backGround);
        textRoofArea.setHorizontalAlignment(JTextField.CENTER);
        textRoofArea.setBounds(resultTitlLabel[3].getX()+ resultTitlLabel[3].getWidth()-25,resultTitlLabel[3].getY(),textWidth+40,controlHeight);

        //房子朝向
        resultPanel.add(comOrientation);
        comOrientation.setBackground(backGround);
        comOrientation.addItem("North-South");
        comOrientation.addItem("East-West");
        comOrientation.setSelectedIndex(-1);
        comOrientation.setBounds(resultTitlLabel[4].getX()+ resultTitlLabel[4].getWidth()-25,resultTitlLabel[4].getY(),textWidth+40,controlHeight);
        //选择监听事件
        comOrientation.addKeyListener(new ComboKeyListener());

        //是否做到不断电
        resultPanel.add(Checkpermanent);
        Checkpermanent.setBackground(backGround);
        Checkpermanent.setFont(new Font("Times New Roman",Font.BOLD,13));
        Checkpermanent.setForeground(titilColor);
        Checkpermanent.setBounds(resultTitlLabel[10].getX(),resultTitlLabel[10].getY()+distanceHeight+10 ,comboboxWidth,controlHeight*2);
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
        JLabel label5 = new JLabel("<html><body>" +"Day" + "<br>" + "Time(H)" + "<html><body>");
        JLabel label6 = new JLabel("<html><body>" +"Night" + "<br>" + "Time(H)" + "<html><body>");

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
            textDayTime[i] = new JTextField();
            inputPanel.add(textDayTime[i]);
            textDayTime[i].setBounds(textNmuber[i].getX() + textWidth + distanceWidth,y,TimeTextWidth,controlHeight);


            Timelabel[i] = new JLabel("+");
            inputPanel.add(Timelabel[i]);
            Timelabel[i].setBounds(textDayTime[i].getX() + TimeTextWidth+3 ,y,15,controlHeight);

            //设置设备结束时间
            textNightTime[i] = new JTextField();
            inputPanel.add(textNightTime[i]);
            textNightTime[i].setBounds(textDayTime[i].getX() + TimeTextWidth + distanceWidth,y,TimeTextWidth,controlHeight);

            //JTextField 字体居中显示
            textPower[i].setHorizontalAlignment(JTextField.CENTER);
            textStartPower[i].setHorizontalAlignment(JTextField.CENTER);
            textDayTime[i].setHorizontalAlignment(JTextField.CENTER);
            textNightTime[i].setHorizontalAlignment(JTextField.CENTER);
            textNmuber[i].setHorizontalAlignment(JTextField.CENTER);

            //设置控件颜色
            devicesCom[i].setBackground(backGround);
            textPower[i].setBackground(backGround);
            textStartPower[i].setBackground(backGround);
            textDayTime[i].setBackground(backGround);
            textNightTime[i].setBackground(backGround);
            textNmuber[i].setBackground(backGround);


            //Text框默认禁用状态
            textPower[i].setEnabled(false);
            textStartPower[i].setEnabled(false);
            textDayTime[i].setEnabled(false);
            textNightTime[i].setEnabled(false);
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
            textDayTime[i].addKeyListener(new DeviceTextKeyListener());
            textNightTime[i].addKeyListener(new DeviceTextKeyListener());
            textNmuber[i].addKeyListener(new DeviceTextKeyListener());


            textPower[i].getDocument().addDocumentListener(new TextDocumentListener());
            textStartPower[i].getDocument().addDocumentListener(new TextDocumentListener());
            textNmuber[i].getDocument().addDocumentListener(new TextDocumentListener());
            textDayTime[i].getDocument().addDocumentListener(new TextDocumentListener());
            textNightTime[i].getDocument().addDocumentListener(new TextDocumentListener());

        }
        //附加输入框事件
        textNEPA.addKeyListener(new DeviceTextKeyListener());
        textGen.addKeyListener(new DeviceTextKeyListener());
        textRoofHeight.addKeyListener(new DeviceTextKeyListener());
        textRoofArea.addKeyListener(new DeviceTextKeyListener());
        textRoofArea.getDocument().addDocumentListener(new TextPanleDocumentListener());
        //结果按钮事件
        resultButton.addActionListener(new comboActionListener());


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
                        textDayTime[i].setEnabled(true);
                        textNightTime[i].setEnabled(true);
                        textNmuber[i].setEnabled(true);
                        //TextFiles显示数据
                        textPower[i].setText(String.valueOf(Math.round(devicesArray.getArray().get(s).getAvgPower())));
                        textStartPower[i].setText(String.valueOf(Math.round(devicesArray.getArray().get(s).getStartPower())));
                        textDayTime[i].setText(String.valueOf(Math.round(devicesArray.getArray().get(s).getTimeStart())));
                        textNightTime[i].setText(String.valueOf(Math.round(devicesArray.getArray().get(s).getTimeEnd())));
                        textNmuber[i].setText("1");
                        //初始化Text的颜色
                        textPower[i].setBackground(backGround);
                        textStartPower[i].setBackground(backGround);
                        textDayTime[i].setBackground(backGround);
                        textNightTime[i].setBackground(backGround);
                        textNmuber[i].setBackground(backGround);
                    }
                }
            }

            //Button点击触发事件
            if(resultButton.isFocusOwner()){
                /* 产品套餐生成逻辑
                * 1. 基础套餐判断条件
                    1.1 开启功率必须小于套餐逆变器的80%
                    1.2 额定功率必须小于套餐逆变器的60%
                */
                if(totalRatedPower > 0 && totalStartPower > 0){
                    MianProduct mianProduct = new MianProduct();
                    String packageType = mianProduct.getbasicPackage(totalRatedPower,totalStartPower);

                    //判断套餐类型是否是false
                    if(packageType.equals("false")){
                        JOptionPane.showMessageDialog(null, "Exceeds the standard package type (20KW)", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }else{
                        new ResultPanel(packageType);
                    }
                }else {
                    JOptionPane.showMessageDialog(null, "The total power is 0", "ERROR", JOptionPane.ERROR_MESSAGE);
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
            //计算结果使用的变量
            int lastTime = 0;
            int ratedPower = 0;
            int startPower = 0;
            int dayPower = 0;
            int nightPower = 0;

            for (int i = 0; i < numb; i++) {
                //判断是否焦点在控件上
                if (keyCode == KeyEvent.VK_BACK_SPACE || keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_ESCAPE) {
                    if (comOrientation.isFocusOwner()) comOrientation.setSelectedIndex(-1);
                    //返回、BackSpace、空格 删除选择
                    if (devicesCom[i].isFocusOwner()) {
                        devicesCom[i].setSelectedIndex(-1);
                        textPower[i].setText("");
                        textStartPower[i].setText("");
                        textDayTime[i].setText("");
                        textNightTime[i].setText("");
                        textNmuber[i].setText("");
                        //禁用Text框
                        textPower[i].setEnabled(false);
                        textStartPower[i].setEnabled(false);
                        textDayTime[i].setEnabled(false);
                        textNightTime[i].setEnabled(false);
                        textNmuber[i].setEnabled(false);
                        //初始化Text的颜色
                        textPower[i].setBackground(backGround);
                        textStartPower[i].setBackground(backGround);
                        textDayTime[i].setBackground(backGround);
                        textNightTime[i].setBackground(backGround);
                        textNmuber[i].setBackground(backGround);
                    }


                    //取消Com的下拉选择后初始值 结果栏
                    if (textPower[i].getText().length() > 0 && textStartPower[i].getText().length() > 0 && !textNmuber[i].getText().equals("")
                            && textDayTime[i].getText().length() > 0 && textNightTime[i].getText().length() > 0){
                        //初始化 textPower和TextStartPower
                        if(!textNmuber[i].getText().equals("0")){
                            ratedPower += Integer.parseInt(textPower[i].getText()) * Integer.parseInt(textNmuber[i].getText());
                            startPower += Integer.parseInt(textStartPower[i].getText()) * Integer.parseInt(textNmuber[i].getText());
                            dayPower += Integer.parseInt(textDayTime[i].getText());
                            nightPower += Integer.parseInt(textNightTime[i].getText());
                            resultLabel[0].setText(String.valueOf(ratedPower) + " W");
                            resultLabel[1].setText(String.valueOf(startPower) + " W");
                            resultLabel[2].setText(String.valueOf(dayPower) + " H");
                            resultLabel[3].setText(String.valueOf(nightPower) + " H");
                            totalRatedPower = ratedPower;
                            totalStartPower = startPower;
                            totalDayPower = dayPower;
                            totalNightPower = nightPower;
                        }
                        lastTime ++;
                    }
                    if(lastTime == 0){
                        resultLabel[0].setText("0");
                        resultLabel[1].setText("0");
                        resultLabel[2].setText("0");
                        resultLabel[3].setText("0");
                        totalRatedPower = 0;
                        totalStartPower = 0;
                        totalDayPower = 0;
                        totalNightPower = 0;
                    }
                }
            }
            ratedPower = 0;
            startPower = 0;
            dayPower = 0;
            nightPower = 0;
            lastTime = 0;
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
                    if (textPower[i].isFocusOwner() && devicesCom[i].getSelectedIndex() > -1)
                    {
                        double arrayPower = devicesArray.getArray().get(devicesCom[i].getSelectedIndex()).getAvgPower();
                        double arrayPowerFactor = devicesArray.getArray().get(devicesCom[i].getSelectedIndex()).getPowerFactor();

                        // 额定功率Text颜色填充
                        if (textPower[i].isFocusOwner() && textPower[i].getText().length() > 0 && e.getKeyChar() != KeyEvent.VK_BACK_SPACE)
                        {
                            int power = Integer.parseInt(String.valueOf(textPower[i].getText()) + String.valueOf(e.getKeyChar()));
                            if(2 * arrayPower >= power && power >= 0.6 * arrayPower)
                            {
                                textPower[i].setBackground(backGround);
                            }else if(3 * arrayPower < power || 0.2 * arrayPower > power || power == 0)
                            {
                                textPower[i].setBackground(highPower);
                            }else{
                                textPower[i].setBackground(lowPower);
                            }
                            //设置启动功率跟随额定功率变化
                            if(String.valueOf(power).length() < 6) {
                                textStartPower[i].setText(String.valueOf(Math.round(power * arrayPowerFactor)));
                            }else{
                                textStartPower[i].setText(String.valueOf(Math.round(power * arrayPowerFactor)).substring(0,6));
                            }

                        }else if( textPower[i].getText().length() > 0 && e.getKeyChar() == KeyEvent.VK_BACK_SPACE){
                            int power = Integer.parseInt(textPower[i].getText());
                            if(2 * arrayPower >= power && power >= 0.6 * arrayPower)
                            {
                                textPower[i].setBackground(backGround);
                            }else if(3 * arrayPower < power || 0.2 * arrayPower > power || power == 0)
                            {
                                textPower[i].setBackground(highPower);
                            }else{
                                textPower[i].setBackground(lowPower);
                            }
                            if(String.valueOf(power).length() < 6) {
                                textStartPower[i].setText(String.valueOf(Math.round(power * arrayPowerFactor)));
                            }else{
                                textStartPower[i].setText(String.valueOf(Math.round(power * arrayPowerFactor)).substring(0,6));
                            }
                        }
                    }
                }

                //只允许输入数字
                if (e.getKeyChar() >= KeyEvent.VK_0 && e.getKeyChar() <= KeyEvent.VK_9 || e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {

                    //DeviceText
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

                    //日间时间
                    if (textDayTime[i].isFocusOwner() && textDayTime[i].getText().length() > 1) {
                        e.consume();
                    }else if(textDayTime[i].isFocusOwner() && textDayTime[i].getText().length() == 0){
                        textDayTime[i].setText("0");
                    }else if(textDayTime[i].isFocusOwner() && textDayTime[i].getText().length() > 0 && e.getKeyChar() != KeyEvent.VK_BACK_SPACE){
                        if(Integer.parseInt(String.valueOf(textDayTime[i].getText()) + String.valueOf(e.getKeyChar())) < 12) {
                            textDayTime[i].setText(textDayTime[i].getText().replaceFirst("^0*", ""));
                        }else{
                            textDayTime[i].setText("12");
                            e.consume();
                        }
                    }
                    //夜间时间
                    if (textNightTime[i].isFocusOwner() && textNightTime[i].getText().length() > 1) {
                        e.consume();
                    }else if(textNightTime[i].isFocusOwner() && textNightTime[i].getText().length() == 0){
                        textNightTime[i].setText("0");
                    }else if(textNightTime[i].isFocusOwner() && textNightTime[i].getText().length() > 0 && e.getKeyChar() != KeyEvent.VK_BACK_SPACE){
                        if(Integer.parseInt(String.valueOf(textNightTime[i].getText()) + String.valueOf(e.getKeyChar())) < 12) {
                            textNightTime[i].setText(textNightTime[i].getText().replaceFirst("^0*", ""));
                        }else{
                            textNightTime[i].setText("12");
                            e.consume();
                        }
                    }
                }else{e.consume();}
            }

            //循环外
            //只允许输入数字
            if (e.getKeyChar() >= KeyEvent.VK_0 && e.getKeyChar() <= KeyEvent.VK_9 || e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                //附加Text
                if (textNEPA.isFocusOwner() && textNEPA.getText().length() > 1) { e.consume();}
                if (textGen.isFocusOwner() && textGen.getText().length() > 4) { e.consume();}
                if (textRoofArea.isFocusOwner() && textRoofArea.getText().length() > 3) { e.consume();}
                if (textRoofHeight.isFocusOwner() && textRoofHeight.getText().length() > 2) { e.consume();}

                //NEPA持续时间
                if (textNEPA.isFocusOwner() && textNEPA.getText().length() > 1) {
                    e.consume();
                }else if(textNEPA.isFocusOwner() && textNEPA.getText().length() == 0){
                    textNEPA.setText("0");
                }else if(textNEPA.isFocusOwner() && textNEPA.getText().length() > 0 && e.getKeyChar() != KeyEvent.VK_BACK_SPACE){
                    if(Integer.parseInt(String.valueOf(textNEPA.getText()) + String.valueOf(e.getKeyChar())) < 12) {
                        textNEPA.setText(textNEPA.getText().replaceFirst("^0*", ""));
                    }else{
                        textNEPA.setText("24");
                        e.consume();
                    }
                }

            }else{e.consume();}
        }
    }


    //TextField 内容监听事件
    class TextDocumentListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            //计算结果使用的变量
            int ratedPower = 0;
            int startPower = 0;
            int dayPower = 0;
            int nightPower = 0;
            for(int i = 0; i < numb; i++){
                if (textPower[i].getText().length() > 0 && textStartPower[i].getText().length() > 0 && !textNmuber[i].getText().equals("")
                        && textDayTime[i].getText().length() > 0 && textNightTime[i].getText().length() > 0){
                    //初始化 textPower、TextStartPower、TextNumber
                    if(!textNmuber[i].getText().equals("0")){
                        ratedPower += Integer.parseInt(textPower[i].getText()) * Integer.parseInt(textNmuber[i].getText());
                        startPower += Integer.parseInt(textStartPower[i].getText()) * Integer.parseInt(textNmuber[i].getText());
                        dayPower += Integer.parseInt(textDayTime[i].getText());
                        nightPower += Integer.parseInt(textNightTime[i].getText());
                    }else{
                        ratedPower -= Integer.parseInt(textPower[i].getText()) * Integer.parseInt(textNmuber[i].getText());
                        startPower -= Integer.parseInt(textStartPower[i].getText()) * Integer.parseInt(textNmuber[i].getText());
                        dayPower -= Integer.parseInt(textDayTime[i].getText());
                        nightPower -= Integer.parseInt(textNightTime[i].getText());
                    }
                    resultLabel[0].setText(String.valueOf(ratedPower) + " W");
                    resultLabel[1].setText(String.valueOf(startPower) + " W");
                    resultLabel[2].setText(String.valueOf(dayPower) + " H");
                    resultLabel[3].setText(String.valueOf(nightPower) + " H");
                    totalRatedPower = ratedPower;
                    totalStartPower = startPower;
                    totalDayPower = dayPower;
                    totalNightPower = nightPower;
                }
            }
            ratedPower = 0;
            startPower = 0;
            dayPower = 0;
            nightPower = 0;
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
        }
        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    }

    //panleNumb 光伏板安装数内容监听事件
    class TextPanleDocumentListener implements DocumentListener{
    double groupSolarPanelLength = 1.65 + 0.3;
    double groupSolarPaneWidth = 1 * 3 + 0.1;
    double groupArea = groupSolarPanelLength * groupSolarPaneWidth;

        @Override
        public void insertUpdate(DocumentEvent e) {
            if(textRoofArea.getText().length()> 0 && isNumeric(textRoofArea.getText())){
                panleNumb = (int)Math.round(Double.valueOf(textRoofArea.getText())/groupArea) * 3;
                resultLabel[4].setText(String.valueOf(panleNumb));
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if(textRoofArea.getText().length()> 0 && isNumeric(textRoofArea.getText())){
                panleNumb = (int)Math.round(Double.valueOf(textRoofArea.getText())/groupArea) * 3;
                resultLabel[4].setText(String.valueOf(panleNumb));
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }

    }


    public static boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }





}
