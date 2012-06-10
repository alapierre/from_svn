/*
 * Copyright 2012-01-23 the original author or authors.
 */
package pl.com.softproject.utils.text;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class AmountInWords {

    // Tblica opis�w warto�ci jednostek.
    static String[] units = {
        "zero", "jeden", "dwa", "trzy", "cztery", "pi��", "sze��",
        "siedem", "osiem", "dziewi��", "dziesi��", "jedena�cie",
        "dwana�cie", "trzyna�cie", "czterna�cie", "pi�tna�cie",
        "szesna�cie", "siedemna�cie", "osiemna�cie", "dziewi�tna�cie"
    };
    //Tablica opis�w dziesi�tek
    static String[] tens = {
        "dwadzie�cia", "trzydzie�ci", "czterdzie�ci", "pi��dziesi�t",
        "sze��dziesi�t", "siedemdziesi�t", "osiemdziesi�t",
        "dziewi��dziesi�t"
    };
    //Tablica obis�w setek
    static String[] hundreds = {
        "", "sto", "dwie�cie", "trzysta", "czterysta", "pi��set",
        "sze��set", "siedemset", "osiemset", "dziewi��set"
    };
    //Dwuwymiarowa tablica tysi�cy,milion�w,miliar�w ...
    static String[][] otherUnits = {
        {"tysi�c", "tysi�ce", "tysi�cy"},
        {"milion", "miliony", "milion�w"},
        {"miliard", "miliardy", "miliard�w"}
    };

    //Konwersja ma�ych liczb ....
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

        // Konwertuj dziesi�tki i jedno�ci.

        if (n > 0) {
            if (valueInWords.length() > 0) {
                valueInWords.append(" ");
            }

            if (n < 20) {
                //  Liczby poni�ej 20 przekonwertuj na podstawie
                //  tablicy jedno�ci.

                valueInWords.append(units[n]);
            } else {
                //  Wi�ksze liczby przekonwertuj ��cz�c nazwy
                //  krotno�ci dziesi�tek z nazwami jedno�ci.
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

    //Obliczenia dla du�ych liczb ... i odmiana prawid�owa ich warto�ci..
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
            //  Je�li liczb� da si� podzieli� przez najbli�sz�
            //  pot�g� 1000, kontynuuj rekurencj�.

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
        strKwotaSl = toWords(liczba_zlotych(kwota)) + " z� " + toWords(liczba_groszy(kwota)) + " gr";
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
