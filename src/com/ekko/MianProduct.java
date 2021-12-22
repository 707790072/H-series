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
                this.solarPanel = (i - 1) * 9;
            }
        }

    }

    //套餐类型 获取逆变器功率
    public int getPackagePower(String type) {
        for(int i = 1;i <= 6;i++){
            if(type.equals("H1")){
                return 3000;
            }else if(type.equals("H" + i) && i > 1){
                return (i - 1) * 4800;
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
    public int getPanlePower(String type,int add) {
        int panleP = 275;
        for(int i = 1;i <= 6;i++){
            if(type.equals("H1")){
                return 6 * (panleP + add);
            }else if(type.equals("H" + i) && i > 1){
                return (((i - 1)) * 9 + add) * panleP;
            }
        }
        return 0;
    }

    //根据发电机功率判断最大充电电流
    public int getMaxRechargCurrent(String type,int genPower) {
        if (type != "false") {
            return genPower / (3 * 50 * (getPackagePower(type) / 5000));
        }
        return 0;
    }

    //判断夜晚使用时常
    public double getAvgTime(int totalRetedPower,int totalDayPower,int totalNightPower) {
        if (totalNightPower > 0) {
            return totalRetedPower / (totalNightPower + totalDayPower + getInverter() * 60 * 24);
        }
        return 0;
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
        double ratedFactor = 0.8;
        int startFactor = 1;

        if(ratedPower < 3000 * ratedFactor && startPower < 3000 * startFactor){
            return "H1";
        }else if(ratedPower < 5000 * ratedFactor && startPower < 5000 * startFactor){
            return "H2";
        }else if(ratedPower < 10000 * ratedFactor && startPower < 10000 * startFactor){
            return "H3";
        }else if(ratedPower < 15000 * ratedFactor && startPower < 15000 * startFactor){
            return "H4";
        }else if(ratedPower < 20000 * ratedFactor && startPower < 20000 * startFactor){
            return "H5";
        }else if(ratedPower < 25000 * ratedFactor && startPower < 25000 * startFactor ) {
            return "H6";
        }
        return "false";
    }

    // 2.升级套餐
    // 升级套餐 光伏板
    public int additionalPanel(String basePackage,int totalDayPower,int totalNightPower,
                               int NEPADayTime,int NEPANightTime,double solarFactor){
        for (int i = 0; i < 200; i++) {
            //到夜间电池充电到100%
            if ((totalDayPower + totalNightPower + getInverter() * 60 * 12 + totalDayPower) <
            (getPanlePower(basePackage,i)) * solarFactor + (NEPADayTime + NEPANightTime) * 1500 * getInverter()){
                //判断是否大于每台逆变器支持光伏板最大数 24
                if(i > getInverter() * 24 - getSolarPanel()){
                    return getInverter() * 24 - getSolarPanel();
                }else{
                    if(i % 3 == 0){
                        return i;
                    }else if(i % 3 == 1){
                        return i + 2;
                    }else if(i % 3 == 2){
                        return i + 1;
                    }
                }
            }
        }
        return getInverter() * 24 - getSolarPanel();
    }
    // 升级套餐 电池
    public int additionalBattery(String basePackage,int totalDayPower,int NEPADayTime,int totalNightPower,int NEPANightTime,int addPanel){
        int day = -1;
        int night = -1;
        //夜晚的功率
        for (int i = 0; i < 50; i++) {
            if (getBattryPower(basePackage) + 4800 * i  > totalDayPower * (1 - (double)NEPADayTime / 12) + getInverter() * 60 * 12
                    - getPanlePower(basePackage,addPanel)) {
                day = i;
                break;
            }
        }
        for (int i = 0; i < 50; i++) {
            if (getBattryPower(basePackage) + 4800 * i  > totalNightPower * (1 - (double)NEPANightTime / 12) + getInverter() * 60 * 12){
                night = i;
                break;
            }
        }
        if(day > night){return day;}else{return night;}
    }


    //豪华套餐
    //豪华光伏板
    public int luxuryPanel(String basePackage,int totalDayPower,int totalNightPower,double solarFactor){
        for (int i = 0; i < 200; i++) {
            //到夜间电池充电到100%
            if ((totalDayPower + totalNightPower + getInverter() * 60 * 12 + totalDayPower) <
                    (getPanlePower(basePackage,i)) * solarFactor ){
                //判断是否大于每台逆变器支持光伏板最大数 24
                if(i > getInverter() * 24 - getSolarPanel()){
                    return getInverter() * 24 - getSolarPanel();
                }else{
                    if(i % 3 == 0){
                        return i;
                    }else if(i % 3 == 1){
                        return i + 2;
                    }else if(i % 3 == 2){
                        return i + 1;
                    }
                }
            }
        }
        return getInverter() * 24 - getSolarPanel();
    }

    public int luxuryBattry(String basePackage,int totalDayPower,int totalNightPower,int addPanel){
        int day = -1;
        int night = -1;
        //夜晚的功率
        for (int i = 0; i < 50; i++) {
            if (getBattryPower(basePackage) + 4800 * i  > totalDayPower + getInverter() * 60 * 12
                    - getPanlePower(basePackage,addPanel)) {
                day = i;
                break;
            }
        }
        for (int i = 0; i < 50; i++) {
            if (getBattryPower(basePackage) + 4800 * i  > totalNightPower + getInverter() * 60 * 12){
                night = i;
                break;
            }
        }
        if(day > night){return day;}else{return night;}
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
