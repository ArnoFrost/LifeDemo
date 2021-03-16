package com.arno.demo.life.utils;

import java.util.ArrayList;
import java.util.List;

public class DataUtil {

    public static List<String> getStringData() {
        int num = 15;
        List<String> dataList = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            dataList.add("text" + i);
        }
        return dataList;
    }
}
