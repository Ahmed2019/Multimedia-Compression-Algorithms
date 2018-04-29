/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arithm;

import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Ahmed
 */
public class Arithm {

    HashMap<Character, Double> prob = new HashMap<>();
    HashMap<Character, Double> lowrang = new HashMap<>();
    HashMap<Character, Double> uprang = new HashMap<>();
    String cs = "abc";
    static Double code;
 /*  
    private void generateprobabilities(String s) {
        
        double arr[] = new double[129];
        for (int i = 0; i < s.length(); ++i) {
            ++arr[((int) s.charAt(i))];
        }
        for (int i = 0; i < 128; ++i) {
            if (arr[i] > 0.0) {
                // System.out.println(arr[i] / s.length() + " " + (char) i);
                prob.put((char) i, arr[i] / s.length());
                cs+=(char) i;
            }
        }
        Double l=0.0,u=0.0;
        for (int i = 0; i < 128; ++i) {
            if (arr[i] > 0.0) {
                u+=arr[i] / s.length();
                lowrang.put((char) i,l);
                uprang.put((char) i,u );
                l=arr[i] / s.length();
            }
        }

        //System.out.println(prob);
       // System.out.println(lowrang);
       // System.out.println(uprang);
    }
    */
    public String Compress(String s) {

        double lower = 0.0, upper = 0.0, rang = 1, lw = 0.0, up = 0.0;
        
        lowrang.put('a', 0.0);
        lowrang.put('b', 0.8);
        lowrang.put('c', 0.82);
        uprang.put('a', 0.8);
        uprang.put('b', 0.82);
        uprang.put('c', 1.0);
        
        for (int i = 0; i < s.length(); ++i) {
            lw = lowrang.get(s.charAt(i));
            up = uprang.get(s.charAt(i));
            //System.out.println(lw+" "+ up);
            //System.out.println(lower+" "+ upper+ " " + rang);
            upper = lower + rang * up;
            lower = lower + rang * lw;
            //System.out.println(lower+" "+ upper+ " " + rang);
            rang = upper - lower;
        }
        code = upper;
        String re = String.format("%.5g%n", upper);
       // System.out.println(re);
        return re;
    }

    public void DeCompress(Double n, int len) {
        Double lw = 0.0, up = 0.0, value = 0.0,lower=0.0,upper=0.0,rang=1.0;
        String s = "";
        --len;
        for (int i = 0; i < cs.length(); ++i) {
            lw = lowrang.get(cs.charAt(i));
            up = uprang.get(cs.charAt(i));
            if (n >= lw && n <= up) {
                s += cs.charAt(i);
                 break;
            }
        }
        //System.out.println(code + " " + s +" "+lw + " " + up);
        for (int i = 0; i < len; ++i) {
            upper = lower + rang * up;
            lower = lower + rang * lw;
            //System.out.println(lower + " " + upper+" "+rang);
            rang = upper - lower;
            value = (n - lower) / (upper - lower);
            for (int j = 0; j < cs.length(); ++j) {
                lw = lowrang.get(cs.charAt(j));
                up = uprang.get(cs.charAt(j));
                if (value >= lw && value <= up) {
                    s += cs.charAt(j);
                    break;
                }
            }       
        }
       // System.out.println(s);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        //String s = input.next();
       // Arithm ob = new Arithm();

        //ob.generateprobabilities(s);
        //ob.Compress(s);
        //ob.DeCompress(code,4);
        String text = "12.34"; // example String
        double value = Double.parseDouble(text);
        System.out.println(value);

    }
    
}
