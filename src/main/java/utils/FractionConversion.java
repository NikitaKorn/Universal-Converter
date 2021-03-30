package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FractionConversion {
    private final List<String> numerator = new ArrayList<>();
    private final List<String> denominator = new ArrayList<>();

    public static class Response{
        private final double value;
        private final int responseCode;

        public Response(double value, int responseCode){
            this.value = value;
            this.responseCode = responseCode;
        }

        public double getValue(){
            return value;
        }

        public int getResponseCode(){
            return responseCode;
        }
    }

    public Response conversion(String from, String to){
        from = from.replaceAll(" ", "");
        to = to.replaceAll(" ", "");
        double value = 0;
        conversion(from, true);
        conversion(to, false);
        value = fractionReduction();
        int code = correctReductionCheck();
        if(code == 400){
            return new Response(0, code);
        } else if(code == 404){
            return new Response(0, code);
        } else {
            return new Response(value, code);
        }
    }

    private int correctReductionCheck(){
        int value = 200;
        Map<Integer, Map<Integer, Double>> adj = SimpleAbbreviations.getAdj();
        for (String s : numerator) {
            if (!s.equals("") && !adj.containsKey(s.hashCode())) {
                return 400;
            }
            if (!s.equals("")) {
                value = 404;
            }
        }
        for (String s : denominator) {
            if(!s.equals("") && !adj.containsKey(s.hashCode())){
                return 400;
            }
            if(!s.equals("")){
                value = 404;
            }
        }

        return value;
    }

    private void conversion(String line, boolean isFrom){
        if(line == null || line.equals("")){
            return;
        }

        if(!isFrom){
            line = turnOverFraction(line);
        }

        if(line.contains("/")){
            String[] numeratorLine = line.split("/");
            String[] splitNumeratorOnValue = numeratorLine[0].split("\\*");
            String[] splitDenominatorOnValue = numeratorLine[1].split("\\*");
            for (String s : splitNumeratorOnValue) {
                numerator.add(s);
            }

            for (String s : splitDenominatorOnValue) {
                denominator.add(s);
            }
        } else {
            String[] splitLine = line.split("\\*");
            for (String s : splitLine) {
                numerator.add(s);
            }
        }
    }

    private String turnOverFraction(String line){
        StringBuilder turnedFraction = new StringBuilder();
        if(line.contains("/")){
            String[] numeratorLine = line.split("/");
            turnedFraction.append(numeratorLine[1]);
            turnedFraction.append("/");
            turnedFraction.append(numeratorLine[0]);
        } else {
            turnedFraction.append("");
            turnedFraction.append("/");
            turnedFraction.append(line);
        }
        return turnedFraction.toString();
    }

    private double fractionReduction(){
        double value = 1;
        for (int i = 0; i < numerator.size(); i++) {
            try{
                double parse = Double.parseDouble(numerator.get(i));
                value *= parse;
                numerator.set(i, "");
                continue;
            } catch (Exception e){

            }
            for (int j = 0; j < denominator.size(); j++) {
                try{
                    double parse = Double.parseDouble(denominator.get(j));
                    value *= 1/parse;
                    denominator.set(j, "");
                    continue;
                } catch (Exception e){

                }
                if (!numerator.get(i).equals("") && !denominator.get(j).equals("")) {
                    double condition = 0;
                    condition = SimpleAbbreviations.simpleConversation(numerator.get(i), denominator.get(j));
                    if (condition != -1 && condition != -2) {
                        value *= condition;
                        numerator.set(i, "");
                        denominator.set(j, "");
                    }
                }
            }
        }
        return value;
    }
}
