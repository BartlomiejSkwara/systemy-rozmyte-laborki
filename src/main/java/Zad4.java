import fuzzlib.DefuzMethod;
import fuzzlib.FuzzySet;
import fuzzlib.norms.SNorm;
import fuzzlib.norms.TNorm;
import fuzzlib.reasoning.ReasoningSystem;
import fuzzlib.reasoning.SystemConfig;

import java.util.Scanner;

public class Zad4 {
    public static void main(String[] args) {

        FuzzySet zimno = new FuzzySet("zimno","").newTriangle(-5, 5, 5);
        FuzzySet chlodno = new FuzzySet("chlodno","").newTriangle(5, 7, 10);
        FuzzySet cieplo = new FuzzySet("cieplo","").newTriangle(20, 10, 10);
        FuzzySet goraco = new FuzzySet("goraco","").newTriangle(35, 10, 5);
        FuzzySet sucho = new FuzzySet("sucho","").newTriangle(20, 20, 30);
        FuzzySet normalnie = new FuzzySet("normalnie","").newTriangle(50, 30, 30);
        FuzzySet wilgotno = new FuzzySet("wilgotno","|").newTriangle(80, 30, 20);
        FuzzySet anyHumidity = new FuzzySet("any","");
        anyHumidity.addPoint(0, 1);
        anyHumidity.addPoint(100, 1);


        FuzzySet fanOff = new FuzzySet("fanOff","").newTriangle(0, 0, 20);
        FuzzySet fanLow = new FuzzySet("fanLow","").newTriangle(25, 25, 25);
        FuzzySet fanMed = new FuzzySet("fanMed","").newTriangle(50, 25, 25);
        FuzzySet fanHigh = new FuzzySet("fanHigh","").newTriangle(100, 25, 0);
        FuzzySet acOff = new FuzzySet("acOff","").newTriangle(0, 0, 20);
        FuzzySet acLow = new FuzzySet("acLow","").newTriangle(25, 25, 25);
        FuzzySet acMed = new FuzzySet("acMed","").newTriangle(50, 25, 25);
        FuzzySet acHigh = new FuzzySet("acHigh","").newTriangle(100, 25, 0);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj temperaturę (w stopniach celsjusza): ");
        double temp = scanner.nextDouble();
        System.out.print("Podaj wilgotność (0-100): ");
        double wilg = scanner.nextDouble();
        scanner.close();

        SystemConfig fanConfig = new SystemConfig();
        fanConfig.setInputWidth(2);
        fanConfig.setOutputWidth(1);
        fanConfig.setNumberOfPremiseSets(4);
        fanConfig.setNumberOfConclusionSets(4);
        fanConfig.setIsOperationType(TNorm.TN_PRODUCT);
        fanConfig.setAndOperationType(TNorm.TN_MINIMUM);
        fanConfig.setOrOperationType(SNorm.SN_PROBABSUM);
        fanConfig.setImplicationType(TNorm.TN_MINIMUM);
        fanConfig.setConclusionAgregationType(SNorm.SN_PROBABSUM);
        fanConfig.setTruthCompositionType(TNorm.TN_MINIMUM);
        fanConfig.setAutoDefuzzyfication(false);
        fanConfig.setDefuzzyfication(DefuzMethod.DF_COG);
        fanConfig.setAutoAlpha(true);
        fanConfig.setTruthPrecision(0.001, 0.0001);

        ReasoningSystem fanSys = new ReasoningSystem(fanConfig);
        fanSys.getInputVar(0).id = "temp";
        fanSys.getInputVar(1).id = "hum";
        fanSys.getOutputVar(0).id = "fan";

        fanSys.addPremiseSet(zimno);
        fanSys.addPremiseSet(chlodno);
        fanSys.addPremiseSet(cieplo);
        fanSys.addPremiseSet(goraco);
        fanSys.addPremiseSet(sucho);
        fanSys.addPremiseSet(normalnie);
        fanSys.addPremiseSet(wilgotno);
        fanSys.addPremiseSet(anyHumidity);

        fanSys.addConclusionSet(fanOff);
        fanSys.addConclusionSet(fanLow);
        fanSys.addConclusionSet(fanMed);
        fanSys.addConclusionSet(fanHigh);


        try {
            // Rule 1: zimno AND sucho -> fanOff
            fanSys.addRule(1, 1);
            fanSys.addRuleItem("temp", "zimno", "AND", "hum", "sucho");
            fanSys.addRuleConclusion("fan", "fanOff");

            // Rule 2: zimno AND normalnie -> fanOff
            fanSys.addRule(1, 1);
            fanSys.addRuleItem("temp", "zimno", "AND", "hum", "normalnie");
            fanSys.addRuleConclusion("fan", "fanOff");

            // Rule 3: zimno AND wilgotno -> fanOff
            fanSys.addRule(1, 1);
            fanSys.addRuleItem("temp", "zimno", "AND", "hum", "wilgotno");
            fanSys.addRuleConclusion("fan", "fanOff");

            // Rule 4: chlodno AND sucho -> fanLow
            fanSys.addRule(1, 1);
            fanSys.addRuleItem("temp", "chlodno", "AND", "hum", "sucho");
            fanSys.addRuleConclusion("fan", "fanLow");

            // Rule 5: chlodno AND normalnie -> fanLow
            fanSys.addRule(1, 1);
            fanSys.addRuleItem("temp", "chlodno", "AND", "hum", "normalnie");
            fanSys.addRuleConclusion("fan", "fanLow");

            // Rule 6: chlodno AND wilgotno -> fanMed
            fanSys.addRule(1, 1);
            fanSys.addRuleItem("temp", "chlodno", "AND", "hum", "wilgotno");
            fanSys.addRuleConclusion("fan", "fanMed");

            // Rule 7: cieplo AND sucho -> fanMed
            fanSys.addRule(1, 1);
            fanSys.addRuleItem("temp", "cieplo", "AND", "hum", "sucho");
            fanSys.addRuleConclusion("fan", "fanMed");

            // Rule 8: cieplo AND normalnie -> fanHigh
            fanSys.addRule(1, 1);
            fanSys.addRuleItem("temp", "cieplo", "AND", "hum", "normalnie");
            fanSys.addRuleConclusion("fan", "fanHigh");

            // Rule 9: cieplo AND wilgotno -> fanHigh
            fanSys.addRule(1, 1);
            fanSys.addRuleItem("temp", "cieplo", "AND", "hum", "wilgotno");
            fanSys.addRuleConclusion("fan", "fanHigh");

            // Rule 10: goraco AND any -> fanHigh
            fanSys.addRule(1, 1);
            fanSys.addRuleItem("temp", "goraco", "AND", "hum", "any");
            fanSys.addRuleConclusion("fan", "fanHigh");
        } catch (Exception e) {
            System.err.println("Błąd dodawania reguł dla wentylatora: " + e.getMessage());
        }

        fanSys.setInput(0, temp);
        fanSys.setInput(1, wilg);
        fanSys.Process();
        double fanOutput = fanSys.getOutputVar(0).outset.DeFuzzyfy();

        SystemConfig acConfig = new SystemConfig();
        acConfig.setInputWidth(2);
        acConfig.setOutputWidth(1);
        acConfig.setNumberOfPremiseSets(4);
        acConfig.setNumberOfConclusionSets(4);
        acConfig.setIsOperationType(TNorm.TN_PRODUCT);
        acConfig.setAndOperationType(TNorm.TN_MINIMUM);
        acConfig.setOrOperationType(SNorm.SN_PROBABSUM);
        acConfig.setImplicationType(TNorm.TN_MINIMUM);
        acConfig.setConclusionAgregationType(SNorm.SN_PROBABSUM);
        acConfig.setTruthCompositionType(TNorm.TN_MINIMUM);
        acConfig.setAutoDefuzzyfication(false);
        acConfig.setDefuzzyfication(DefuzMethod.DF_COG);
        acConfig.setAutoAlpha(true);
        acConfig.setTruthPrecision(0.001, 0.0001);

        ReasoningSystem acSys = new ReasoningSystem(acConfig);
        acSys.getInputVar(0).id = "temp";
        acSys.getInputVar(1).id = "hum";
        acSys.getOutputVar(0).id = "ac";

        acSys.addPremiseSet(zimno);
        acSys.addPremiseSet(chlodno);
        acSys.addPremiseSet(cieplo);
        acSys.addPremiseSet(goraco);
        acSys.addPremiseSet(sucho);
        acSys.addPremiseSet(normalnie);
        acSys.addPremiseSet(wilgotno);
        acSys.addPremiseSet(anyHumidity);

        acSys.addConclusionSet(acOff);
        acSys.addConclusionSet(acLow);
        acSys.addConclusionSet(acMed);
        acSys.addConclusionSet(acHigh);

        try {
            // Rule 1: zimno AND sucho -> acLow
            acSys.addRule(1, 1);
            acSys.addRuleItem("temp", "zimno", "AND", "hum", "sucho");
            acSys.addRuleConclusion("ac", "acLow");

            // Rule 2: zimno AND normalnie -> acMed
            acSys.addRule(1, 1);
            acSys.addRuleItem("temp", "zimno", "AND", "hum", "normalnie");
            acSys.addRuleConclusion("ac", "acMed");

            // Rule 3: zimno AND wilgotno -> acHigh
            acSys.addRule(1, 1);
            acSys.addRuleItem("temp", "zimno", "AND", "hum", "wilgotno");
            acSys.addRuleConclusion("ac", "acHigh");

            // Rule 4: chlodno AND sucho -> acLow
            acSys.addRule(1, 1);
            acSys.addRuleItem("temp", "chlodno", "AND", "hum", "sucho");
            acSys.addRuleConclusion("ac", "acLow");

            // Rule 5: chlodno AND normalnie -> acMed
            acSys.addRule(1, 1);
            acSys.addRuleItem("temp", "chlodno", "AND", "hum", "normalnie");
            acSys.addRuleConclusion("ac", "acMed");

            // Rule 6: chlodno AND wilgotno -> acHigh
            acSys.addRule(1, 1);
            acSys.addRuleItem("temp", "chlodno", "AND", "hum", "wilgotno");
            acSys.addRuleConclusion("ac", "acHigh");

            // Rule 7: cieplo AND sucho -> acLow
            acSys.addRule(1, 1);
            acSys.addRuleItem("temp", "cieplo", "AND", "hum", "sucho");
            acSys.addRuleConclusion("ac", "acLow");

            // Rule 8: cieplo AND normalnie -> acMed
            acSys.addRule(1, 1);
            acSys.addRuleItem("temp", "cieplo", "AND", "hum", "normalnie");
            acSys.addRuleConclusion("ac", "acMed");

            // Rule 9: cieplo AND wilgotno -> acHigh
            acSys.addRule(1, 1);
            acSys.addRuleItem("temp", "cieplo", "AND", "hum", "wilgotno");
            acSys.addRuleConclusion("ac", "acHigh");

            // Rule 10: goraco AND any -> acHigh
            acSys.addRule(1, 1);
            acSys.addRuleItem("temp", "goraco", "AND", "hum", "any");
            acSys.addRuleConclusion("ac", "acHigh");
        } catch (Exception e) {
            System.err.println("Błąd dodawania reguł dla AC: " + e.getMessage());
        }

        acSys.setInput(0, temp);
        acSys.setInput(1, wilg);
        acSys.Process();
        double acOutput = acSys.getOutputVar(0).outset.DeFuzzyfy();

        System.out.println("Wentylator: " + fanOutput);
        System.out.println("AC: " + acOutput);
    }
}
