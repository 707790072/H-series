package com.ekko;

public class MyDemo {
    public static void main(String[] args) throws Exception {
        new RootFrame();

        String[][] arr = {{"1","2","3","4","5","6"}, {"1","2","3","4","5","6"}};
        ExcelExport excelExport = new ExcelExport();
        excelExport.ExcelWrite(arr);

    }
}
