/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morgantest;

/**
 *
 * @author Himanshu
 */
public class MorganTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //Demo working of the convertor service
        CurrencyConvertor c= new CurrencyConvertor("DKKNZD");
        System.out.println(c.calculateConversionRate());
        CurrencyConvertor c1= new CurrencyConvertor("NZDDKK");
        System.out.println(c1.calculateConversionRate());
    }
    
}
