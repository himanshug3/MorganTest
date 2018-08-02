/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morgantest;

import java.util.Map;

/**
 *
 * @author Himanshu
 */
public class CurrencyConvertor {

    private static Configuration config;
    private String pair;

    CurrencyConvertor(String pair) {
        this.pair = pair;
        //provide only a single instance of the configuration for all convertor queries
        if (config == null) {
            config = new Configuration();
        }
    }

    public float calculateConversionRate() {
        Map<String, Integer> currencyMap = config.getCurrencyMap();
        String curr1 = pair.substring(0, 3);
        String curr2 = pair.substring(3, 6);
        return calculateRateRecursive(currencyMap.get(curr1), currencyMap.get(curr2));
    }

    //main work method which returns conversion recursively
    //row, column represent location in relation array
    //curr_Map is the lookup Map for currency index
    //arr is the actual relation map
    //arr_vis is the direct accessible map
    private float calculateRateRecursive(int row, int column) {
        Boolean arr_vis[][] = config.getArr_set();
        String arr[][] = config.getArr();
        Map<String, Integer> curr_Map = config.getCurrencyMap();
        //if direct calculatable return value
        if (arr_vis[row][column] == true) {
            return Float.parseFloat(arr[row][column]);
        }
        //find the indirect link currency
        int mid = curr_Map.get(arr[row][column]);
        float x = calculateRateRecursive(row, mid);
        float y = calculateRateRecursive(mid, column);
        //store the value to convert this cell as diret accesible
        config.setArr(row, column, x * y);
        //mark as direct accessible
        config.setArr_set(row, column, true);
        //also store the value of inverse relation
        config.setArr(column, row, (float) Math.pow(x * y, -1));
        //mark as direct accessible
        config.setArr_set(column, row, true);
        return x * y;
    }

    //place holder to add direct conversion pairs from application side
    public static void addDirectConversion(Map<String, Float> map) {
        //read and store all values from the map in directConfig.properties
        config.init();
    }

    //place holder to remove direct conversion pairs from application side
    public static void removeDirectConversion(Map<String, Float> map) {
        //remove all values in the map from directConfig.properties
        config.init();
    }

    //place holder to add indirect conversion pairs from application side
    public static void addIndirectConversion(Map<String, String> map) {
        //read and store all values from the map in IndirectConfig.properties
        config.init();
    }

    //place holder to remove indirect conversion pairs from application side
    public static void removeIndirectConversion(Map<String, String> map) {
        //remove all values in the map from IndirectConfig.properties
        config.init();
    }
}
