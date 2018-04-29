/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import static java.util.Map.Entry.comparingByValue;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import static java.util.stream.Collectors.toMap;

/**
 *
 * @author Ahmed
 */
public class Huffman {

    HashMap<Character, Integer> prob = new HashMap<>();
    HashMap<Character, String> code = new HashMap<>();

    public void writeStringonFile(String s, String path) {
        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(path));
            fw.write(s);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getStringfromfile(String path) {
        String res = "";
        try {
            Scanner s = new Scanner(new File(path));
            while (s.hasNextLine()) {
                res += (s.nextLine());
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    public String Probability(String s) {
        String p = "";
        int[] pro = new int[128];
        for (int i = 0; i < s.length(); ++i) {
            ++pro[s.charAt(i)];
        }
        for (int i = 0; i < 128; ++i) {
            if (pro[i] > 0) {
                // System.out.print((char)i+"="+pro[i]+" ");
                prob.put((char) i, pro[i]);
            }
        }
        //System.out.println(prob);

        Map<Character, Integer> sorted = prob.entrySet().stream().sorted(comparingByValue()).collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));

        for (Map.Entry<Character, Integer> entry : sorted.entrySet()) {
            p += (entry.getKey());
        }
        //System.out.println(p);
        System.out.println(sorted);
        return p;

    }

    public String Compress(String source) {
        String s = "", CompressCode = "";
        //s=getStringfromfile("Source.txt");
        s = Probability(source);
        int sz = s.length();
        String cons = "0", change = "0";
        code.put(s.charAt(sz - 1), "1");
        sz -= 2;
        for (int i = sz; i >= 0; --i) {
            cons += '1';
            code.put(s.charAt(i), cons);
            change += '0';
            cons = change;
        }
        for (int i = 0; i < source.length(); ++i) {
            for (Map.Entry<Character, String> entry : code.entrySet()) {
                if (entry.getKey().equals(source.charAt(i))) {
                    CompressCode += entry.getValue();
                }
            }
        }
        System.out.println(code);
        //System.out.println(CompressCode);
        writeStringonFile(CompressCode, "Huffman.txt");
        return CompressCode;
    }

    public String Decompress(String ComCode) {
        //s=getStringfromfile("Huffman.txt");
        String re = "";
        String tem = "";
        for (int j = 0; j < ComCode.length(); ++j) {
            tem += ComCode.charAt(j);
            for (Map.Entry<Character, String> entry : code.entrySet()) {
                if (entry.getValue().equals(tem)) {
                    re += entry.getKey();
                    tem = "";
                }
            }
        }
        //System.out.println(re);
        writeStringonFile(re, "Huffman.txt");
        return re;
    }
    /*
    public static void main(String[] args) {

        String s = "aabbccccddabbb",r="";

        Huffman ob = new Huffman();
        System.out.println(s);
        r=ob.Compress(s);
       // System.out.println(s);
        ob.Decompress(r);

    }
     */
}
