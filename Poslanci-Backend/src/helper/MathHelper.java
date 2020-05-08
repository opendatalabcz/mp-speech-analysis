package helper;

public class MathHelper {
    public static Double countSentiment(Integer pocetPosSlov, Integer pocetNegSlov) {
        if(pocetPosSlov == 0 && pocetNegSlov == 0)
            return 0.0;
        else
            return ((pocetPosSlov * 1.0) + (pocetNegSlov * (-1.0))) / (pocetPosSlov + pocetNegSlov);
    }
}
