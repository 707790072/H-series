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
        for(int i = 1;i <= 5;i++){
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

    //每个套餐类型的额定功率
    public int getPackageRatedPower(String type){
        int packageRated;

        setpackageType(type);
        if(type.equals("H1")){
            packageRated = 3;
        }else{
            packageRated = getInverter() * 5;
        }
        return packageRated;
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
