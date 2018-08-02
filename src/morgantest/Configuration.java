/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morgantest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Himanshu
 */
public final class Configuration {

    //will store currency index as <CURR,index>
    private Map<String, Integer> currencyMap = new HashMap<String, Integer>();
    //String array to hold the relation map
    private String[][] arr;
    //Boolean array to mark the direct calculated cells
    private Boolean[][] arr_set;

    public Configuration() {
        init();
    }

    public void init() {
        getDirectCurrency();
        getIndirectCurrency();
    }

    private void getDirectCurrency() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("src/morgantest/directCurrencyConfig.properties");
            prop.load(input);
            //stores length
            int i = 0;
            //creating the map to store currency index 
            //Property index is like <AUSUSD,0.8371>
            for (Map.Entry<Object, Object> m : prop.entrySet()) {
                String pair = (String) m.getKey();
                String curr1 = pair.substring(0, 3);
                String curr2 = pair.substring(3, 6);
                if (!currencyMap.containsKey(curr1)) {
                    currencyMap.put(curr1, i);
                    i++;
                }
                if (!currencyMap.containsKey(curr2)) {
                    currencyMap.put(curr2, i);
                    i++;
                }
            }
            //initialize once we get the size
            this.arr = new String[i][i];
            this.arr_set = new Boolean[i][i];
            //Property index is like <AUSUSD,0.8371>
            for (Map.Entry<Object, Object> m : prop.entrySet()) {
                String pair = (String) m.getKey();
                String val = (String) m.getValue();
                String curr1 = pair.substring(0, 3);
                String curr2 = pair.substring(3, 6);
                //getting location for storing in the relation map 
                int index1 = currencyMap.get(curr1);
                int index2 = currencyMap.get(curr2);
                //value stored
                arr[index1][index2] = val;
                //direct calculatable marked
                arr_set[index1][index2] = true;
                //store the inverse value as 1/x the original value
                float inv_val = Float.parseFloat(val);
                arr[index2][index1] = Float.toString((float) Math.pow(inv_val, -1));
                //direct calculatable marked
                arr_set[index2][index1] = true;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void getIndirectCurrency() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("src/morgantest/indirectCurrencyConfig.properties");
            prop.load(input);
            //Property index is like <AUDCAD,USD>
            for (Map.Entry<Object, Object> m : prop.entrySet()) {
                String pair = (String) m.getKey();
                String val = (String) m.getValue();
                String curr1 = pair.substring(0, 3);
                String curr2 = pair.substring(3, 6);
                //lookup index in map
                int index1 = currencyMap.get(curr1);
                int index2 = currencyMap.get(curr2);
                //store the indirect access location
                arr[index1][index2] = val;
                //direct access not possible
                arr_set[index1][index2] = false;
            }

            //Mark all the self relations(USD<-->USD)as 1 and direct access possible
            for (int k = 0; k < arr.length; k++) {
                for (int j = 0; j < arr.length; j++) {
                    if (k == j) {
                        arr[k][j] = "1";
                        arr_set[k][j] = true;
                    }
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Map<String, Integer> getCurrencyMap() {
        return currencyMap;
    }

    public String[][] getArr() {
        return arr;
    }

    public Boolean[][] getArr_set() {
        return arr_set;
    }

    public void setArr(int row, int column, float value) {
        //store the value to convert this cell as diret accesible
        this.arr[row][column] = Float.toString(value);
    }

    public void setArr_set(int row, int column, boolean value) {
        //mark as direct accessible
        this.arr_set[row][column] = value;
    }
}
