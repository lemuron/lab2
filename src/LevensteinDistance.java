import java.util.*;

public class LevensteinDistance {
    private static int minimum(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    public static double computeLevenshteinDistance(CharSequence lhs, CharSequence rhs) {

        Map<String, String> qwerty = new HashMap<>();

        qwerty.put("q", "wa");
        qwerty.put("w", "qasde");
        qwerty.put("e", "wsdfr");
        qwerty.put("r", "edfgt");
        qwerty.put("t", "rfghy");
        qwerty.put("y", "tghju");
        qwerty.put("u", "yhjki");
        qwerty.put("i", "ujklo");
        qwerty.put("o", "iklp");
        qwerty.put("p", "ol");
        qwerty.put("a", "qwsxz");
        qwerty.put("s", "qazxcdew");
        qwerty.put("d", "wsxcvfre");
        qwerty.put("f", "edcvbgtr");
        qwerty.put("g", "rfvbnhyt");
        qwerty.put("h", "tgbnmjuy");
        qwerty.put("j", "yhnmkiu");
        qwerty.put("k", "ujmloi");
        qwerty.put("l", "poik");
        qwerty.put("z", "asx");
        qwerty.put("x", "zasdc");
        qwerty.put("c", "xsdfv");
        qwerty.put("v", "cdfgb");
        qwerty.put("b", "vfghn");
        qwerty.put("n", "bghjm");
        qwerty.put("m", "nhjk");

        int[][] distance = new int[lhs.length() + 1][rhs.length() + 1];

        for (int i = 0; i <= lhs.length(); i++)
            distance[i][0] = i;
        for (int j = 1; j <= rhs.length(); j++)
            distance[0][j] = j;

        for (int i = 1; i <= lhs.length(); i++)
            for (int j = 1; j <= rhs.length(); j++)
                distance[i][j] = minimum(
                        distance[i - 1][j] + 1,
                        distance[i][j - 1] + 1,
                        distance[i - 1][j - 1] + ((lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1));

        return distance[lhs.length()][rhs.length()];
    }

    private static boolean isNear(String value, Character key){

        for (int i = 0; i<value.length();i++){
            if (value.charAt(i) == key) return true;
        }
        return false;
    }

}
