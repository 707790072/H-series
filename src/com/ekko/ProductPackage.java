package com.ekko;

import java.util.ArrayList;
public class ProductPackage {

    public class MianProduct {

        private int id;
        //主要部件
        private String type;
        private int inverter;
        private int battery_3KWH;
        private int battery_5KWH;
        private int solarPanel;


    }

    public class solarAccessories {

        //光伏板安装配件
        private int PVbox;
        private int rail;
        private int solarCable;
        private int shape_L; //固定横杠
        private int shape_U; //固定两块光伏板
        private int shape_Z; //侧边固定光伏板
        private int corrugatedNail;


    }

    public class inverterAccessories {

        //逆变器安装
        private int acCable;
        private int networkCable;
        private int trunking;

    }

    public class batteryAccessories {

        //电池安装
        private int batteryCable_150;
        private int batteryCable_10;

    }



}

//    public ArrayList<Product> getProductPackageArray()
//    {
//        //创建集合对象
//        ArrayList<Product> array = new ArrayList<Product>();
//        array.add(new Product();
//        array.add(new Product();
//        array.add(new Product();
//        array.add(new Product();
//        array.add(new Product();
//        array.add(new Product();
//        array.add(new Product();
//        array.add(new Product();
//        return array;
//    }