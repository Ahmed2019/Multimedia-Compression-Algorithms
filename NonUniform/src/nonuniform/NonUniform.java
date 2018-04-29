/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nonuniform;

import java.io.*;
import java.util.*;

/**
 *
 * @author Ahmed
 */
public class NonUniform {

    Vector<Integer> vrang = new Vector<>();
    Vector<Integer> vavarage = new Vector<>();
    Vector<Integer> vQ = new Vector<>();
    Vector<Integer> vQ_1 = new Vector<>();
    Vector<Integer> v = new Vector<>();
    String pt="";

    public int avarage(Vector<Integer> v) {
        int ava = 0;
        for (int i = 0; i < v.size(); ++i) {
            ava += v.elementAt(i);
        }
        ava /= v.size();
        return ava;
    }
/*
    public void Cheq(Vector<Vector<Integer>> vlev, Vector<Integer> v, int n) {
        Vector<Integer> vinner = new Vector<>();
        Vector<Integer> vavarage = new Vector<>();
        Vector<Integer> vt = new Vector<>();
        Vector<Vector<Integer>> vcheq = new Vector<Vector<Integer>>();
        int c = n / 2, a = 0, s = 0, ind = 0;
        for (int i = c, j = 1; i < vlev.size(); ++i, ++j) {
            vinner = vlev.elementAt(i);
            a = avarage(vinner);
            vavarage.addElement(a);
        }
        // System.out.println(vavarage);
        for (int i = 0; i < v.size(); ++i) {
            int m = 1074856743;
            for (int x = 0; x < vavarage.size(); ++x) {
                s = v.elementAt(i) - vavarage.elementAt(x);
                if (m > Math.abs(s)) {
                    //System.out.print(Math.abs(s) + " ");
                    m = s;
                    ind = x;
                }
            }
            // System.out.print(ind + " ");
          //  vcheq.elementAt(ind).addElement(v.elementAt(i));
        }
        // System.out.println(vcheq);
/*       
        while (true) {            
           for(int i=0;i<n;++i){
               if(vcheq.elementAt(i)==vlev.elementAt(i)){
                   
               }
           }    
        }
    
    }
*/
    
    //  path = "C:\\Users\\Ahmed\\Desktop\\Source.txt";   اعمل فايل علي الديسكتوب بتاع الاب بتاعك وسميه Source 
    //  وحط فيه الارقام الي موجودة في المحاضرة ولما ترن حط ال                                                   
    //                                                     path and number of levels 
    public String Compress(String path, int n) {
        pt=path;
        Vector<Vector<Integer>> vlev = new Vector<Vector<Integer>>();
        Vector<Integer> vinner = new Vector<>();
        Vector<Integer> vt1 = new Vector<>();
        Vector<Integer> vt2 = new Vector<>();
        Vector<Integer> vQ = new Vector<>();
        Vector<Integer> vQ_1 = new Vector<>();
        int a = 0, b = 0, lv = 0,y;
       String res="",sc="";
        try {
            Scanner s = new Scanner(new File(path));
            while (s.hasNextLine()) {
                res += (s.nextLine());
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for(int i=0;i<res.length();++i){
            if(res.charAt(i)==' '){
                y = Integer.parseInt(sc);
                v.addElement(y);
                ++i;
                sc="";
            }
            sc+=res.charAt(i);
        }
        y = Integer.parseInt(sc);
        v.addElement(y);
       // System.out.println(v);
        a = avarage(v) - 1;
        // System.out.println(a);
        for (int j = 0; j < v.size(); ++j) {
            if (v.elementAt(j) <= a) {
                vt1.addElement(v.elementAt(j));
            } else {
                vt2.addElement(v.elementAt(j));
            }
        }
        vlev.add(vt1);
        vlev.add(vt2);
        lv = n - 2;
        //System.out.println(ava + " " + a + " " + b + " " + lv);
        // System.out.println(vt1 + "\n" + vt2);
        for (int i = 0; i < lv; ++i) {
            vt1 = new Vector<>();
            vt2 = new Vector<>();
            vinner = vlev.elementAt(i);
            //System.out.println(vinner);
            a = avarage(vinner) - 1;
            // System.out.println(a);
            for (int x = 0; x < vinner.size(); ++x) {
                if (vinner.elementAt(x) <= a) {
                    vt1.add(vinner.elementAt(x));
                } else {
                    vt2.add(vinner.elementAt(x));
                }
            }
            //System.out.println(vt1 + "\n" + vt2);
            vlev.add(vt1);
            vlev.add(vt2);
        }

        //System.out.println(vavarage);
        //Cheq(vlev, v, n);
        int c = n - 2, s = 0, max = 0, min = 0;
        for (int i = c; i < vlev.size(); ++i) {
            vinner = vlev.elementAt(i);
            a = avarage(vinner);
            vavarage.addElement(a);
            Collections.sort(vinner);
            max = vinner.elementAt(vinner.size() - 1);
            vrang.add(s);
            vrang.add(max);
            s = max + 1;
            //System.out.println(max);
        }

        //System.out.println(vavarage);
        int z=0;
        String xy="";
        for (int i = 0; i < v.size(); ++i) {
            for (int j = 0; j < vrang.size(); j += 2) {
                if (v.elementAt(i) >= vrang.elementAt(j) && v.elementAt(i) <= vrang.elementAt(j + 1)) {
                    z=j/2;
                    vQ.add(j / 2);
                    xy+=String.format("%d ",z);
                }
            }
        }
         Collections.sort(vrang);
         Collections.sort(vavarage);
        try {
    	    BufferedWriter fw= new BufferedWriter(new FileWriter(path));
		fw.write(xy);
                fw.close();
	} catch (IOException e) {
		e.printStackTrace();
        }
        return xy; 
        //System.out.println(vQ + "\n" + vQ_1);
    }

    public String decom() {
        String de = "";
        int d=0;
        System.out.println(vrang+"\n"+vavarage);
        for (int i = 0; i < v.size(); ++i) {
            for (int j = 0; j < vrang.size(); j += 2) {
                if (v.elementAt(i) >= vrang.elementAt(j) && v.elementAt(i) <= vrang.elementAt(j + 1)) {
                   d=vavarage.elementAt(j / 2);
                    //System.err.println(d);
                   de+=String.format("%d ",d);
                   vQ_1.add(vavarage.elementAt(j / 2));
                }

            }
        }
        try {
    	    BufferedWriter fw= new BufferedWriter(new FileWriter(pt));
		fw.write(de);
                fw.close();
	} catch (IOException e) {
		e.printStackTrace();
        }
        //System.out.println(vQ_1);
        return de;
    }
/*
    public static void main(String[] args) {
        Vector<Integer> v = new Vector<>();
        int x, num;
        Scanner input = new Scanner(System.in);
        String p = input.next();
      //  p = "C:\\Users\\Ahmed\\Desktop\\Source.txt";
        num = input.nextInt();
        // System.out.println(v);
        NonUniform ob = new NonUniform();
        ob.Compress(p, num);

    }
*/
}
