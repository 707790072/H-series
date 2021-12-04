package com.ekko;

import java.util.ArrayList;
import java.util.Collections;

public class DevicesArray
{

    public ArrayList<Devices> getArray()
    {
        //创建集合对象
        ArrayList<Devices> array = new ArrayList<Devices>();
        array.add(new Devices(0,"Other",100,1.1,0,24));
        array.add(new Devices(1,"Air Conditioner",1200,1.2,8,16));
        array.add(new Devices(2,"TV",150,1.1,17,23));
        array.add(new Devices(3,"Fridge",100,3,0,24));
        array.add(new Devices(4,"Bulb",20,1,17,24));
        array.add(new Devices(5,"Washing Machine",450,1.2,8,9));
        array.add(new Devices(6,"Computer",250,1,8,17));
        array.add(new Devices(7,"Other",0,1.1,0,24));
        return array;
    }

}
