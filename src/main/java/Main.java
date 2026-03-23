import fuzzlib.FuzzySet;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FuzzySet[] c1 = new FuzzySet[4];
        c1[0] = new FuzzySet();
        c1[0].newTriangle(5.006,      1.058490,     -1.1464900);
        c1[1] = new FuzzySet();
        c1[1].newTriangle(3.418,      1.499024,     -1.363024);
        c1[2] = new FuzzySet();
        c1[2].newTriangle(1.464,      0.637511,     -0.609511);
        c1[3] = new FuzzySet();
        c1[3].newTriangle(0.244,      0.251210,     -0.463210);

        FuzzySet[] c2 = new FuzzySet[4];
        c2[0] = new FuzzySet();
        c2[0].newTriangle(5.936,     1.552171,    -1.580171);
        c2[1] = new FuzzySet();
        c2[1].newTriangle(2.770,     1.083798,    -0.943798);
        c2[2] = new FuzzySet();
        c2[2].newTriangle( 4.260,     1.729911,    -1.309911);
        c2[3] = new FuzzySet();
        c2[3].newTriangle( 1.326,     0.523753,    -0.671753);

        FuzzySet[] c3 = new FuzzySet[4];
        c3[0] = new FuzzySet();
        c3[0].newTriangle(6.588,      2.323880,     -1.947880);
        c3[1] = new FuzzySet();
        c3[1].newTriangle( 2.974,      1.096497,     -1.148497);
        c3[2] = new FuzzySet();
        c3[2].newTriangle(5.552,      1.603895,     -1.899895);
        c3[3] = new FuzzySet();
        c3[3].newTriangle( 2.026,      0.900650,     -0.748650);

        double[] c_p = {0.0f, 0.0f, 0.0f};

        float[] input = new float[4];
        String[] col = {"SepalLengthCm","SepalWidthCm","PetalLengthCm","PetalWidthCm"};

        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 4; i++) {
            System.out.print(col[i]+": ");
            input[i] = scanner.nextFloat();
        }

        for (int i = 0; i < 4; i++) {
            float v = input[i];
            c_p[0] += c1[i].getMembership(v);
            c_p[1] += c2[i].getMembership(v);
            c_p[2] += c3[i].getMembership(v);
        }

        double max = 0;
        int max_index = 0;
        for (int i = 0; i < 3; i++) {
            if (c_p[i] > max) {
                max = c_p[i];
                max_index = i;
            }
        }

        String[] labels = {"Iris-setosa","Iris-versicolor","Iris-virginica"};
        System.out.println(labels[max_index]);
        scanner.close();
    }
}