package com.ekko;

public class MianProduct {

    private int id;
    //主要部件
    private String packageType;
    private int inverter;
    private int battery_3KWH;
    private int battery_5KWH;
    private int solarPanel;

    DevicePanel devicePanel = new DevicePanel();
    private int totalRatedPower = devicePanel.getTotalRatedPower();
    private int totalStartPower = devicePanel.getTotalStartPower();

    //产品类型设置 每个部件数量
    public void setpackageType(String type) {
        for(int i = 1;i <= 6;i++){
            if(type.equals("H1")){
                this.id = 1;
                this.inverter = 1;
                this.battery_3KWH = 1;
                this.battery_5KWH = 0;
                this.solarPanel = 6;
            }else if(type.equals("H" + i) && i > 1){
                this.id = i;
                this.inverter = i - 1;
                this.battery_3KWH = 0;
                this.battery_5KWH = i - 1;
                this.solarPanel = i * 9;
            }
        }

    }

    //套餐类型 获取逆变器功率
    public int getPackagePower(String type) {
        for(int i = 1;i <= 6;i++){
            if(type.equals("H1")){
                return 5000;
            }else if(type.equals("H" + i) && i > 1){
                return (i - 1) * 5000;
            }
        }
        return 0;
    }

    //套餐类型 获取电池功率
    public int getBattryPower(String type) {
        for(int i = 1;i <= 6;i++){
            if(type.equals("H1")){
                return 2500;
            }else if(type.equals("H" + i) && i > 1){
                return (i - 1) * 4800;
            }
        }
        return 0;
    }

    //套餐类型 获取光伏板功率
    public int getPanlePower(String type) {
        int panleP = 275;
        for(int i = 1;i <= 6;i++){
            if(type.equals("H1")){
                return 6 * panleP;
            }else if(type.equals("H" + i) && i > 1){
                return (i - 1) * 9 * panleP;
            }
        }
        return 0;
    }

    //根据发电机功率判断最大充电电流
    public int getMaxRechargCurrent(String type,int genPower) {
        return genPower / (3 * 50 * (getPackagePower(type)/5000) );
    }



/* 产品套餐生成逻辑
    * 1. 基础套餐判断条件
        1.1 开启功率必须小于套餐逆变器的80%
        1.2 额定功率必须小于套餐逆变器的60%
    * 2. 升级套餐判断条件
        2.1 开启功率必须小于套餐逆变器的80%
        2.2 额定功率必须小于套餐逆变器的60%
        2.3 判断白天和夜间的使用时长，白天的总功率计算增加的光伏板量大于6小时
        2.4 判断夜晚的使用时长，夜晚增加电池，保证保存4小时

    * 3. 豪华套餐判断条件
        3.1 开启功率必须小于套餐逆变器的60%
        3.2 额定功率必须小于套餐逆变器的40%
        3.3 判断白天和夜间的使用时长，白天的总功率计算增加的光伏板量大于12小时
        3.4 判断夜晚的使用时长，夜晚增加电池，保证保存6小时

    * 4. 不断电套餐条件
        3.1 开启功率必须小于套餐逆变器的60%
        3.2 额定功率必须小于套餐逆变器的40%
        3.3 判断白天和夜间的使用时长，白天的总功率计算增加的光伏板量大于12小时
        3.4 判断夜晚的使用时长，夜晚增加电池，保证保存12小时
    * */
    // 1.基础套餐判断
    public String getbasicPackage(int ratedPower,int startPower){

        if(ratedPower < 3000 * 0.6 && startPower < 3000 * 0.8){
            return "H1";
        }else if(ratedPower < 5000 * 0.6 && startPower < 5000 * 0.8){
            return "H2";
        }else if(ratedPower < 10000 * 0.6 && startPower < 10000 * 0.8){
            return "H3";
        }else if(ratedPower < 15000 * 0.6 && startPower < 15000 * 0.8){
            return "H4";
        }else if(ratedPower < 20000 * 0.6 && startPower < 20000 * 0.8){
            return "H5";
        }else if(ratedPower < 20000 && startPower < 20000 ) {
            return "H6";
        }
        return "false";
    }

    // 2.升级套餐
    public String advancedPackage(String basePackage,int dayPower,int nightPower,int NEPATime,int genPower,double solarFactor){

        //需要增加的电池数量
        int addBattry_6; //6小时
        int addBattry_12; //12小时


        //夜晚的功率
        for (int i = 1; i < 100; i++) {
            //夜间电池100% 到放完支持6个小时需要的电池量
            if (getBattryPower(basePackage) + 4800 * i - nightPower - getInverter() * 60 * 6 >= 6 ) {
                addBattry_6 = i;
            }
            if (getBattryPower(basePackage) + 4800 * i - nightPower - getInverter() * 60 * 6 >= 12 ) {
                addBattry_12 = i;
            }

            //白夜考虑市电和房屋朝向的情况电池从0%充满
            if ((getBattryPower(basePackage) + dayPower + getInverter() * 60) <
            getPanlePower(basePackage) * solarFactor + NEPATime * getMaxRechargCurrent(basePackage,genPower) ){

            }
        }
    }





    public int getId() {
        return id;
    }

    public String getPackageType() {
        return packageType;
    }

    public int getInverter() {
        return inverter;
    }

    public int getBattery_3KWH() {
        return battery_3KWH;
    }

    public int getBattery_5KWH() {
        return battery_5KWH;
    }

    public int getSolarPanel() {
        return solarPanel;

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
