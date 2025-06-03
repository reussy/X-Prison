package dev.drawethree.xprison.utils;

import java.text.DecimalFormat;

public class NumberUtils {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###.#");

    private static String formatLarge(double n, int iteration) {
        double f = n / 1000.0D;
        return f < 1000 || iteration >= getNumberFormat().length - 1 ?
                DECIMAL_FORMAT.format(f) + getNumberFormat()[iteration] : formatLarge(f, iteration + 1);
    }

    public static String format(double value) {
        return value < 1000 ? DECIMAL_FORMAT.format(value) : formatLarge(value, 0);
    }

    private static String[] getNumberFormat() {
        return String.valueOf("K;M;B;T;Q;QQ;S;SS;OC;N;D;UN;DD;TR;QT;QN;SD;SPD;OD;ND;VG;UVG;DVG;TVG;QTV;QNV;SEV;SPV;OVG;NVG;TG").split(";");
    }

    public static boolean isValidInt(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidDouble(String number) {
        try {
            Double.parseDouble(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }

}
