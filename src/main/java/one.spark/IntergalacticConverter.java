package one.spark;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class IntergalacticConverter {

    private Map<String, String> intergalacticToRoman;
    private Map<String, Double> unitToCredit;

    Map<Character, Integer> romanToArabic = new HashMap<>() {{
        put('I', 1);
        put('V', 5);
        put('X', 10);
        put('L', 50);
        put('C', 100);
        put('D', 500);
        put('M', 1000);
    }};

    public IntergalacticConverter() {
        this.intergalacticToRoman = new HashMap<>();
        this.unitToCredit = new HashMap<>();
    }

    public String processLine(String line) {
        String[] tokens = line.split(" ");

        if (tokens.length == 3 && tokens[1].equals("is")) {
            String intergalactic = tokens[0];
            String roman = tokens[2];
            getIntergalacticToRoman().put(intergalactic, roman);
        } else if (tokens[tokens.length - 1].equalsIgnoreCase("Credits")) {
            String unit = tokens[tokens.length - 2];
            double credits = Double.parseDouble(unit);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < tokens.length - 4; i++) {
                sb.append(getIntergalacticToRoman().get(tokens[i]));
            }
            String roman = sb.toString();
            int arabic = romanToArabic(roman);
            double unitValue = credits / arabic;
            String res = tokens[tokens.length - 4];
            getUnitToCredit().put(res, unitValue);
        } else if (contains(tokens, "how") && contains(tokens, "is")) {
            return processQuery(line.substring(0, line.length() - 1));
        } else {
            return "I have no idea what you are talking about";
        }
        return "";
    }

    public boolean contains(final String[] tokens, final String value) {
        return Arrays.stream(tokens)
                .anyMatch(t -> t.equalsIgnoreCase(value));
    }

    public int indexOf(final String[] tokens, final String search) {

        for (int i = tokens.length - 1; i > -1; i--) {
            if (search.equalsIgnoreCase(tokens[i])) {
                return i;
            }
        }
        return -1;
    }

    private int romanToArabic(String roman) {

        int arabic = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            int currentValue = romanToArabic.get(roman.charAt(i));
            if (currentValue < prevValue) {
                arabic -= currentValue;
            } else {
                arabic += currentValue;
                prevValue = currentValue;
            }
        }

        return arabic;
    }

    public String processQuery(String query) {
        String[] tokens = query.split(" ");

        int isIndex = indexOf(tokens, "is");
        if (isIndex == -1) {
            return "I have no idea what you are talking about";
        }
        String inQuestion = query.substring(query.indexOf("is") + 2).trim();
        if (contains(tokens, "Credits")) {
            StringBuilder sb = new StringBuilder();
            for (int i = isIndex + 1; i < tokens.length - 1; i++) {
                sb.append(getIntergalacticToRoman().get(tokens[i]));
            }
            String roman = sb.toString();
            int arabic = romanToArabic(roman);
            String unit = tokens[tokens.length - 1];
            double unitValue = getUnitToCredit().get(unit);

            return String.format("%s is %.0f Credits", inQuestion, (unitValue * arabic));
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = isIndex + 1; i < tokens.length; i++) {
                sb.append(getIntergalacticToRoman().get(tokens[i]));
            }
            String roman = sb.toString();
            int arabic = romanToArabic(roman);
            return String.format("%s is %d", inQuestion, arabic);
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        IntergalacticConverter converter = new IntergalacticConverter();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            converter.processLine(line);
        }

        scanner.close();
    }

    public Map<String, String> getIntergalacticToRoman() {
        return intergalacticToRoman;
    }

    public Map<String, Double> getUnitToCredit() {
        return unitToCredit;
    }
}