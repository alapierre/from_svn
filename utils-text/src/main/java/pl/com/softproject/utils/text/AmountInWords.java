/*
 * Copyright 2012-01-23 the original author or authors.
 */
package pl.com.softproject.utils.text;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class AmountInWords {

    // Tblica opisów wartoœci jednostek.
    static String[] units = {
        "zero", "jeden", "dwa", "trzy", "cztery", "piêæ", "szeœæ",
        "siedem", "osiem", "dziewiêæ", "dziesiêæ", "jedenaœcie",
        "dwanaœcie", "trzynaœcie", "czternaœcie", "piêtnaœcie",
        "szesnaœcie", "siedemnaœcie", "osiemnaœcie", "dziewiêtnaœcie"
    };
    //Tablica opisów dziesi¹tek
    static String[] tens = {
        "dwadzieœcia", "trzydzieœci", "czterdzieœci", "piêædziesi¹t",
        "szeœædziesi¹t", "siedemdziesi¹t", "osiemdziesi¹t",
        "dziewiêædziesi¹t"
    };
    //Tablica obisów setek
    static String[] hundreds = {
        "", "sto", "dwieœcie", "trzysta", "czterysta", "piêæset",
        "szeœæset", "siedemset", "osiemset", "dziewiêæset"
    };
    //Dwuwymiarowa tablica tysiêcy,milionów,miliarów ...
    static String[][] otherUnits = {
        {"tysi¹c", "tysi¹ce", "tysiêcy"},
        {"milion", "miliony", "milionów"},
        {"miliard", "miliardy", "miliardów"}
    };

    //Konwersja ma³ych liczb ....
    private static String smallValueToWords(int n) {
        if (n == 0) {
            return null;
        }

        StringBuilder valueInWords = new StringBuilder();

        // Konwertuj setki.

        int temp = n / 100;
        if (temp > 0) {
            valueInWords.append(hundreds[temp]);
            n -= temp * 100;
        }

        // Konwertuj dziesi¹tki i jednoœci.

        if (n > 0) {
            if (valueInWords.length() > 0) {
                valueInWords.append(" ");
            }

            if (n < 20) {
                //  Liczby poni¿ej 20 przekonwertuj na podstawie
                //  tablicy jednoœci.

                valueInWords.append(units[n]);
            } else {
                //  Wiêksze liczby przekonwertuj ³¹cz¹c nazwy
                //  krotnoœci dziesi¹tek z nazwami jednoœci.
                valueInWords.append(tens[(n / 10) - 2]);
                int lastDigit = n % 10;

                if (lastDigit > 0) {
                    valueInWords.append(" ");
                    valueInWords.append(units[lastDigit]);
                }
            }
        }
        return valueInWords.toString();
    }

    //Obliczenia dla du¿ych liczb ... i odmiana prawid³owa ich wartoœci..
    private static int GetBigUnitIndex(long n) {
        int lastDigit = (int) n % 10;

        if ((n >= 10 && (n <= 20 || lastDigit == 0))
                || (lastDigit > 4)) {
            return 2;
        }
        return (lastDigit == 1) ? 0 : 1;
    }

    private static long processNumericValue(StringBuilder valueInWords, long n, int level) {
        int smallValue = 0;
        //long divisor = (long)Math.pow(10000, (long)level + 1);
        long divisor = (long) Math.pow(1000, (long) level + 1);

        if (divisor <= n) {
            //  Jeœli liczbê da siê podzieliæ przez najbli¿sz¹
            //  potêgê 1000, kontynuuj rekurencjê.

            n = processNumericValue(valueInWords, n, level + 1);
            smallValue = (int) (n / divisor);

            if (valueInWords.length() > 0) {
                valueInWords.append(" ");
            }

            if (smallValue > 1) {
                valueInWords.append(smallValueToWords(smallValue));
                valueInWords.append(" ");
            }
            valueInWords.append(otherUnits[level][GetBigUnitIndex(smallValue)]);
        }

        return n - smallValue * divisor;
    }

    private static String toWords(long value) {
        if (value == 0) {
            // Zero.
            return units[0];
        }
        StringBuilder valueInWords = new StringBuilder();
        long smallValue = processNumericValue(valueInWords, value, 0);
        if (smallValue > 0) {
            if (valueInWords.length() > 0) {
                valueInWords.append(" ");
            }
            valueInWords.append(smallValueToWords((int) smallValue));
        }
        return valueInWords.toString();
    }

    static long liczba_zlotych(double kwota) {
        Double result = Math.floor(kwota);
        return result.longValue();
    }

    static long liczba_groszy(double kwota) {
        Double groszy = (kwota * 100 - liczba_zlotych(kwota) * 100);
        return groszy.longValue();
    }

    public static String toWords(double kwota) {

        if (kwota < 0) {
            kwota = kwota * -1;
        }
        String strKwotaSl;
        strKwotaSl = toWords(liczba_zlotych(kwota)) + " z³ " + toWords(liczba_groszy(kwota)) + " gr";
        return strKwotaSl;
    }
    
    public static String toWords(double kwota, String currency) {

        if (kwota < 0) {
            kwota = kwota * -1;
        }
        String strKwotaSl;
        strKwotaSl = toWords(liczba_zlotych(kwota)) + " " + currency + " " + liczba_groszy(kwota) + "/100";
        return strKwotaSl;
    }
}
