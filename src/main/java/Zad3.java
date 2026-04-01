import fuzzlib.FuzzySet;
import fuzzlib.norms.SNMax;
import fuzzlib.DefuzMethod;

import java.util.Scanner;

public class Zad3 {
    public static void main(String[] args) {
        FuzzySet zimno = new FuzzySet().newTriangle(-5, 5, 5);
        FuzzySet chlodno = new FuzzySet().newTriangle(5, 7, 10);
        FuzzySet cieplo = new FuzzySet().newTriangle(20, 10, 10);
        FuzzySet goraco = new FuzzySet().newTriangle(35, 10, 5);

        FuzzySet sucho = new FuzzySet().newTriangle(20, 20, 30);
        FuzzySet normalnie = new FuzzySet().newTriangle(50, 30, 30);
        FuzzySet wilgotno = new FuzzySet().newTriangle(80, 30, 20);

        FuzzySet fanOff = new FuzzySet().newTriangle(0, 0, 20);
        FuzzySet fanLow = new FuzzySet().newTriangle(25, 25, 25);
        FuzzySet fanMed = new FuzzySet().newTriangle(50, 25, 25);
        FuzzySet fanHigh = new FuzzySet().newTriangle(100, 25, 0);

        FuzzySet acOff = new FuzzySet().newTriangle(0, 0, 20);
        FuzzySet acLow = new FuzzySet().newTriangle(25, 25, 25);
        FuzzySet acMed = new FuzzySet().newTriangle(50, 25, 25);
        FuzzySet acHigh = new FuzzySet().newTriangle(100, 25, 0);


        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj temperaturę (w stopniach celsjusza): ");
        double temp = scanner.nextDouble();
        System.out.print("Podaj wilgotność (0-100): ");
        double wilg = scanner.nextDouble();
        scanner.close();



        double muZimno   = zimno.getMembership(temp);
        double muChlodno = chlodno.getMembership(temp);
        double muCieplo  = cieplo.getMembership(temp);
        double muGoraco  = goraco.getMembership(temp);

        double muSucho    = sucho.getMembership(wilg);
        double muNormalne = normalnie.getMembership(wilg);
        double muWilgotno = wilgotno.getMembership(wilg);

        // Zbiory wynikowe dla agregacji
        FuzzySet fanAgg = new FuzzySet();
        fanAgg.addPoint(0.0, 0.0);
        fanAgg.addPoint(100.0, 0.0);
        FuzzySet acAgg = new FuzzySet();
        acAgg.addPoint(0.0, 0.0);
        acAgg.addPoint(100.0, 0.0);

        // Reguła 1: zimno i sucho -> fanOff , acLow
        double firing = Math.min(muZimno, muSucho);
        if (firing > 0) {
            FuzzySet clippedFan = new FuzzySet();
            clippedFan.assign(fanOff);
            clippedFan.cutMembership(firing);
            FuzzySet.processSetsWithNorm(fanAgg, fanAgg, clippedFan, new SNMax());

            FuzzySet clippedAc = new FuzzySet();
            clippedAc.assign(acLow);
            clippedAc.cutMembership(firing);
            FuzzySet.processSetsWithNorm(acAgg, acAgg, clippedAc, new SNMax());
        }

        // Reguła 2: zimno i normalne -> fanOff, acMed
        firing = Math.min(muZimno, muNormalne);
        if (firing > 0) {
            FuzzySet clippedFan = new FuzzySet();
            clippedFan.assign(fanOff);
            clippedFan.cutMembership(firing);
            FuzzySet.processSetsWithNorm(fanAgg, fanAgg, clippedFan, new SNMax());

            FuzzySet clippedAc = new FuzzySet();
            clippedAc.assign(acMed);
            clippedAc.cutMembership(firing);
            FuzzySet.processSetsWithNorm(acAgg, acAgg, clippedAc, new SNMax());
        }

        // Reguła 3: zimno i wilgotno -> fanOff, acHigh
        firing = Math.min(muZimno, muWilgotno);
        if (firing > 0) {
            FuzzySet clippedFan = new FuzzySet();
            clippedFan.assign(fanOff);
            clippedFan.cutMembership(firing);
            FuzzySet.processSetsWithNorm(fanAgg, fanAgg, clippedFan, new SNMax());

            FuzzySet clippedAc = new FuzzySet();
            clippedAc.assign(acHigh);
            clippedAc.cutMembership(firing);
            FuzzySet.processSetsWithNorm(acAgg, acAgg, clippedAc, new SNMax());
        }

        // Reguła 4: chłodno i sucho -> fanLow, acLow
        firing = Math.min(muChlodno, muSucho);
        if (firing > 0) {
            FuzzySet clippedFan = new FuzzySet();
            clippedFan.assign(fanLow);
            clippedFan.cutMembership(firing);
            FuzzySet.processSetsWithNorm(fanAgg, fanAgg, clippedFan, new SNMax());

            FuzzySet clippedAc = new FuzzySet();
            clippedAc.assign(acLow);
            clippedAc.cutMembership(firing);
            FuzzySet.processSetsWithNorm(acAgg, acAgg, clippedAc, new SNMax());
        }

        // Reguła 5: chłodno i normalne -> fanLow, acMed
        firing = Math.min(muChlodno, muNormalne);
        if (firing > 0) {
            FuzzySet clippedFan = new FuzzySet();
            clippedFan.assign(fanLow);
            clippedFan.cutMembership(firing);
            FuzzySet.processSetsWithNorm(fanAgg, fanAgg, clippedFan, new SNMax());

            FuzzySet clippedAc = new FuzzySet();
            clippedAc.assign(acMed);
            clippedAc.cutMembership(firing);
            FuzzySet.processSetsWithNorm(acAgg, acAgg, clippedAc, new SNMax());
        }

        // Reguła 6: chłodno i wilgotno -> fanMed, acHigh
        firing = Math.min(muChlodno, muWilgotno);
        if (firing > 0) {
            FuzzySet clippedFan = new FuzzySet();
            clippedFan.assign(fanMed);
            clippedFan.cutMembership(firing);
            FuzzySet.processSetsWithNorm(fanAgg, fanAgg, clippedFan, new SNMax());

            FuzzySet clippedAc = new FuzzySet();
            clippedAc.assign(acHigh);
            clippedAc.cutMembership(firing);
            FuzzySet.processSetsWithNorm(acAgg, acAgg, clippedAc, new SNMax());
        }

        // Reguła 7: ciepło i sucho -> fanMed, acLow
        firing = Math.min(muCieplo, muSucho);
        if (firing > 0) {
            FuzzySet clippedFan = new FuzzySet();
            clippedFan.assign(fanMed);
            clippedFan.cutMembership(firing);
            FuzzySet.processSetsWithNorm(fanAgg, fanAgg, clippedFan, new SNMax());

            FuzzySet clippedAc = new FuzzySet();
            clippedAc.assign(acLow);
            clippedAc.cutMembership(firing);
            FuzzySet.processSetsWithNorm(acAgg, acAgg, clippedAc, new SNMax());
        }

        // Reguła 8: ciepło i normalne -> fanHigh, acMed
        firing = Math.min(muCieplo, muNormalne);
        if (firing > 0) {
            FuzzySet clippedFan = new FuzzySet();
            clippedFan.assign(fanHigh);
            clippedFan.cutMembership(firing);
            FuzzySet.processSetsWithNorm(fanAgg, fanAgg, clippedFan, new SNMax());

            FuzzySet clippedAc = new FuzzySet();
            clippedAc.assign(acMed);
            clippedAc.cutMembership(firing);
            FuzzySet.processSetsWithNorm(acAgg, acAgg, clippedAc, new SNMax());
        }

        // Reguła 9: ciepło i wilgotno -> fanHigh, acHigh
        firing = Math.min(muCieplo, muWilgotno);
        if (firing > 0) {
            FuzzySet clippedFan = new FuzzySet();
            clippedFan.assign(fanHigh);
            clippedFan.cutMembership(firing);
            FuzzySet.processSetsWithNorm(fanAgg, fanAgg, clippedFan, new SNMax());

            FuzzySet clippedAc = new FuzzySet();
            clippedAc.assign(acHigh);
            clippedAc.cutMembership(firing);
            FuzzySet.processSetsWithNorm(acAgg, acAgg, clippedAc, new SNMax());
        }

        // Reguła 10: gorąco (niezależnie od wilgotności) -> fanHigh, acHigh
        firing = muGoraco;
        if (firing > 0) {
            FuzzySet clippedFan = new FuzzySet();
            clippedFan.assign(fanHigh);
            clippedFan.cutMembership(firing);
            FuzzySet.processSetsWithNorm(fanAgg, fanAgg, clippedFan, new SNMax());

            FuzzySet clippedAc = new FuzzySet();
            clippedAc.assign(acHigh);
            clippedAc.cutMembership(firing);
            FuzzySet.processSetsWithNorm(acAgg, acAgg, clippedAc, new SNMax());
        }


        double fanOutput = fanAgg.DeFuzzyfyEx(DefuzMethod.DF_COG, 0.0, 0.01);
        double acOutput = acAgg.DeFuzzyfyEx(DefuzMethod.DF_COG, 0.0, 0.01);

        System.out.println("Wentylator: " + fanOutput);
        System.out.println("AC: " + acOutput);





    }
}













