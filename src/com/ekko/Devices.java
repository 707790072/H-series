package com.ekko;

public class Devices
{
    public int id;
    public String name;
    public int avgPower;
    public double PowerFactor;
    public int timeStart;
    public int timeEnd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvgPower() {
        return avgPower;
    }

    public void setAvgPower(int avgPower) {
        this.avgPower = avgPower;
    }

    public double getPowerFactor() {
        return PowerFactor;
    }

    public void setPowerFactor(double powerFactor) {
        PowerFactor = powerFactor;
    }

    public int getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(int timeStart) {
        this.timeStart = timeStart;
    }

    public int getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(int timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Devices(int id, String name, int avgPower, double PowerFactor, int timeStart ,int timeEnd)
    {

        this.id = id;
        this.name = name;
        this.avgPower = avgPower;
        this.PowerFactor = PowerFactor;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;

    }

    public double getStartPower()
    {
        double StartPower = getAvgPower() * getPowerFactor();
        return StartPower;
    }

}
