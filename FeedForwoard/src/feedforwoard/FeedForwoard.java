/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feedforwoard;

import com.sun.org.apache.xalan.internal.lib.ExsltDatetime;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Formatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import static java.util.Map.Entry.comparingByValue;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Vector;
import static java.util.stream.Collectors.toMap;
import javax.imageio.ImageIO;
import javax.print.DocFlavor;

/**
 *
 * @author Ahmed
 */
public class FeedForwoard {

    Vector<Integer> data = new Vector<>();
    Vector<Integer> diff = new Vector<>();
    Vector<Integer> Q = new Vector<>();
    Vector<Integer> Q_1 = new Vector<>();
    int[][] pixl = new int[400][600];
    int minvalue = 255, maxvalue = -255, rang = 0, Quantzationlevel = 0;

    public void writeonfile(int[][] out, String path) {
        try (PrintStream output = new PrintStream(new File(path));) {
            for (int i = 0; i < out[0].length; i++) {
                String sc = "";
                for (int j = 0; j < out.length; j++) {
                    sc += out[j][i] + " ";
                }
                output.println(sc);
            }
            output.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int[][] readImage(String path) {
        BufferedImage img;
        try {
            img = ImageIO.read(new File(path));
            int hieght = img.getHeight();
            int width = img.getWidth();
            //System.out.println(width + " " + hieght);
            int[][] imagePixels = new int[hieght][width];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < hieght; y++) {
                    int pixel = img.getRGB(x, y);
                    int red = (pixel & 0x00ff0000) >> 16;
                    int grean = (pixel & 0x0000ff00) >> 8;
                    int blue = pixel & 0x000000ff;
                    int alpha = (pixel & 0xff000000) >> 24;
                    imagePixels[y][x] = red;
                }
            }
            writeonfile(imagePixels, "Source.txt");
            return imagePixels;
        } catch (IOException e) {
            return null;
        }

    }

    public void writeImage(int[][] pixels, String outputFilePath, int width, int height) {
        File fileout = new File(outputFilePath);
        BufferedImage image2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image2.setRGB(x, y, (pixels[y][x] << 16) | (pixels[y][x] << 8) | (pixels[y][x]));
            }
        }
        try {
            ImageIO.write(image2, "jpg", fileout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[][] convert_Vec_to_2Darray(Vector<Integer> v) {
        int x = 0;
        int[][] a = new int[400][600];
        for (int i = 0; i < pixl[0].length; ++i) {
            for (int j = 0; j < pixl.length; ++j) {
                int y = v.elementAt(x);
                a[j][i] = y;
                ++x;
            }

        }
        return a;

    }

    public Vector Diff(Vector<Integer> v) {
        // System.out.println(v);
        Vector<Integer> vdiff = new Vector<>();
        int d = 0;
        vdiff.add(v.elementAt(0));
        int[][] Diffrence = new int[400][600];
        for (int i = 1; i < v.size(); ++i) {
            //d = Math.abs(v.elementAt(i) - v.elementAt(i - 1));
            d = v.elementAt(i) - v.elementAt(i - 1);
            vdiff.add(d);
            minvalue = Integer.min(minvalue, d);
            maxvalue = Integer.max(maxvalue, d);

        }

        rang = ((Math.abs(minvalue) + Math.abs(maxvalue)) / Quantzationlevel) + 1;
        Diffrence = convert_Vec_to_2Darray(vdiff);
        writeonfile(Diffrence, "Diffrence.txt");
        return vdiff;

    }

    public Vector<Vector> QuantizationTable() {
        Vector<Vector> QT = new Vector<>();
        int f = minvalue,
                l = minvalue + rang,
                Fq_1 = (f + l) / 2;
        Vector<Integer> temp = new Vector<>();
        for (int i = 0; i < Quantzationlevel; ++i) {
            temp.add(i);
            temp.add(f);
            temp.add(l);
            temp.add(Fq_1);
            QT.add(temp);
            temp = new Vector<>();
            f = l + 1;
            l += rang;
            Fq_1 += rang;
        }
        //System.out.println(QT);
        return QT;
    }

    public void QandQ_1(int n) {
        Quantzationlevel = n;
        int[][] arr = new int[400][600];
        pixl = readImage("cat.jpg");
        Vector<Integer> diff = new Vector<>();
        for (int i = 0; i < pixl[0].length; ++i) {
            for (int j = 0; j < pixl.length; ++j) {
                data.add(pixl[j][i]);
            }
        }
        diff = Diff(data);
        Vector<Vector> QTable = new Vector<>();
        QTable = QuantizationTable();
        Vector<Integer> temp = new Vector<>();
        Q.add(diff.elementAt(0));
        Q_1.add(diff.elementAt(0));
        temp = new Vector<>();

        for (int i = 1; i < diff.size(); ++i) {
            for (int j = 0; j < Quantzationlevel; ++j) {
                temp = QTable.elementAt(j);
                if (diff.elementAt(i) >= temp.elementAt(1)
                        && diff.elementAt(i) <= temp.elementAt(2)) {
                    Q.add(temp.elementAt(0));
                    Q_1.add(temp.elementAt(3));
                    break;
                }
            }
        }
        //arr=convert_Vec_to_2Darray(Q);
        // writeonfile(arr,"Q.txt");
        //arr=convert_Vec_to_2Darray(Q_1);
        // writeonfile(arr,"Q-1.txt");
    }

    public void Decode() {
        int[][] com = new int[400][600];
        Vector<Integer> VQ_1 = new Vector<>();
        Vector<Integer> Decode = new Vector<>();
        VQ_1 = Q_1;
        int sum = 0;
        sum = VQ_1.elementAt(0);
        Decode.add(sum);
        for (int i = 1; i < VQ_1.size(); ++i) {
            sum += VQ_1.elementAt(i);

            if (sum > 256) {
                sum %= 256;
            }

            sum = Math.abs(sum);
            Decode.add(sum);

        }
        com = convert_Vec_to_2Darray(Decode);
        writeonfile(com, "CompressFile.txt");
        writeImage(com, "Cat_out.jpg", com[0].length, com.length);
        System.exit(0);
    }
    /*
    public static void main(String[] args) {
        Vector<Integer> v = new Vector<>();
        v.add(15);
        v.add(16);
        v.add(24);
        v.add(33);
        v.add(44);
        v.add(68);

        FeedForwoard ob = new FeedForwoard();
        ob.Quantizer();
        //System.out.println("minvalue="+mn + "\nmaxvalue=" +mx+"\nrang="+r+"\nfisrt rang val="+(mn+r)+"\nfirst Q-1="+q_1);
        //System.out.println();

    }
     */
}
