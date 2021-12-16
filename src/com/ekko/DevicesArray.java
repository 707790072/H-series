package com.ekko;

import java.util.ArrayList;
import java.util.Collections;

public class DevicesArray
{

    public ArrayList<Devices> getArray()
    {
        //创建集合对象
        ArrayList<Devices> array = new ArrayList<Devices>();
        array.add(new Devices(0,"Other",100,1.1,0,0));
        array.add(new Devices(1,"Air Conditioner",1200,1.2,2,6));
        array.add(new Devices(2,"TV",150,1.1,2,6));
        array.add(new Devices(3,"Fridge",100,3,12,12));
        array.add(new Devices(4,"Bulb",20,1,4,12));
        array.add(new Devices(5,"Washing Machine",450,1.2,1,1));
        array.add(new Devices(6,"Computer",250,1,9,2));

        return array;
    }

}
