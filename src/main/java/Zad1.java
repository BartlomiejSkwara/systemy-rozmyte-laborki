import fuzzlib.FuzzySet;

import java.util.Scanner;
interface Fuzzy{
    public double getMembership(double x);
}


class FuzzyGauss implements Fuzzy{
    double m;
    double sigma;


    public FuzzyGauss(double m,double sigma){
        this.m = m;
        this.sigma = sigma;


    }
    public double getMembership(double x){
        return  Math.exp(-Math.pow((x - m), 2) / (2 * Math.pow(sigma, 2)));
    }
}


class FuzzyTrapezoidal implements Fuzzy{
    double a;
    double b;
    double c;
    double d;


    public FuzzyTrapezoidal(double a,double b,double c,double d){
        this.a =a;
        this.b =b;
        this.c =c;
        this.d =d;


    }
    public double getMembership(double x){
        if (x<=a || x>=d){
            return 0;
        }
        if ((x >= b) && (x <= c)){
            return 1;
        }


        if (x > a && x < b) {
            return  (x - a) / (b - a);
        } else {
            return  (d - x) / (d - c);
        }
    }
}


class FuzzyTriangular implements Fuzzy{
    double a;
    double b;
    double c;
    double d;


    public FuzzyTriangular(double left,double top,double right){
        this.a =left;
        this.b =top;
        this.c =top;
        this.d =right;


    }
    public double getMembership(double x){
        if (x<=a || x>=d){
            return 0;
        }
        if ((x >= b) && (x <= c)){
            return 1;
        }


        if (x > a && x < b) {
            return  (x - a) / (b - a);
        } else {
            return  (d - x) / (d - c);
        }
    }
}
public class Zad1 {
    public static void main(String[] args)  {
        Scanner scanner = new Scanner(System.in);



        Fuzzy[] c1 = {
                new FuzzyTriangular(5.006,      1.058490,     -1.1464900),
                new FuzzyTriangular(3.418,      1.499024,     -1.363024),
                new FuzzyTriangular(1.464,      0.637511,     -0.609511),
                new FuzzyTriangular(0.244,      0.251210,     -0.463210)
        };
        Fuzzy[] c2 = {
                new FuzzyTriangular(5.936,     1.552171,    -1.580171),
                new FuzzyTriangular(2.770,     1.083798,    -0.943798),
                new FuzzyTriangular(4.260,     1.729911,    -1.309911),
                new FuzzyTriangular(1.326,     0.523753,    -0.671753)
        };
        Fuzzy[] c3 = {
                new FuzzyTriangular(6.588,      2.323880,     -1.947880),
                new FuzzyTriangular(2.974,      1.096497,     -1.148497),
                new FuzzyTriangular(5.552,      1.603895,     -1.899895),
                new FuzzyTriangular(2.026,      0.900650,     -0.748650)
        };


        float[] c_p = {0.0f,0.0f,0.0f};


        float[] input = new float[4];
        for (int i = 0; i < 4; i++) {
            System.out.print("Value " + (i + 1) + ": ");
            input[i] = scanner.nextFloat();
        }
//        float[] input = {4.9f,3.0f,1.4f,0.2f};




        for(int i = 0; i<4; i++){
            float v = input[i];
            c_p[0]+=c1[i].getMembership(v);
            c_p[1]+=c2[i].getMembership(v);
            c_p[2]+=c3[i].getMembership(v);
        }


        float max = 0;
        float max_index = 0;
        for(int i = 0; i<3; i++){
            if (c_p[i]>max){
                max = c_p[i];
                max_index = i;
            }
        }


        System.out.println(max_index);
    }
}
