/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.com.softproject.utils.text;

/**
 *
 * @author Adrian Lapierre
 */
public class StringUtil {

    private static char[] delimiters = {' ','.'};

    public static String swapCase(String oryginal) {

        if(isOnlyFirstLetterUpperCase(oryginal))
            return oryginal.toLowerCase();
        else if(isUpperCase(oryginal))
            return oryginal.toLowerCase();
        else if(isCapitalaze(oryginal)) {
            return oryginal.toUpperCase();
        } else if(isLowerCase(oryginal))
            return capitalize(oryginal, delimiters);

            return oryginal.toUpperCase();
    }

    public static boolean isOnlyFirstLetterUpperCase(String str) {

        if(str == null || str.length() == 0)
            return false;

        if(Character.isUpperCase(str.charAt(0))) {
            int strLen = str.length();
            for (int i = 1; i < strLen; i++) {
                char ch = str.charAt(i);
                if(Character.isUpperCase(ch))
                    return false;
            }
            return true;
        }
        return false;
    }

    public static boolean isCapitalaze(String str) {

        int strLen = str.length();

        boolean checkNextCharacter = true;

        for (int i = 0; i < strLen; i++) {
            char ch = str.charAt(i);

            if(isDelimiter(ch, delimiters)) 
                checkNextCharacter = true;
            else if(checkNextCharacter) {
                if(Character.isLowerCase(ch))
                    return false;
                checkNextCharacter = false;
            }

        }

        return true;
    }

    public static boolean isLowerCase(String str) {

        int strLen = str.length();

        for (int i = 0; i < strLen; i++) {
            char ch = str.charAt(i);

            if(Character.isLetter(ch))  
                if(Character.isUpperCase(ch)) return false;            
        }

        return true;
    }

    public static boolean isUpperCase(String str) {

        int strLen = str.length();

        for (int i = 0; i < strLen; i++) {
            char ch = str.charAt(i);

            if(Character.isLetter(ch))
                if(Character.isLowerCase(ch)) return false;
        }

        return true;
    }

    /**
     * <p>Uncapitalizes all the whitespace separated words in a String.
     * Only the first letter of each word is changed.</p>
     * Based on Apache Commons Lang
     *
     * <p>The delimiters represent a set of characters understood to separate words.
     * The first string character and the first non-delimiter character after a
     * delimiter will be uncapitalized. </p>
     *
     * A <code>null</code> input String returns <code>null</code>.</p>
     *
     * @param str  the String to uncapitalize, may be null
     * @param delimiters  set of characters to determine uncapitalization, null means whitespace
     * @return uncapitalized String, <code>null</code> if null String input
     *
     */
    public static String uncapitalize(String str, char[] delimiters) {
        int delimLen = (delimiters == null ? -1 : delimiters.length);
        if (str == null || str.length() == 0 || delimLen == 0) {
            return str;
        }
        int strLen = str.length();
        StringBuffer buffer = new StringBuffer(strLen);
        boolean uncapitalizeNext = true;
        for (int i = 0; i < strLen; i++) {
            char ch = str.charAt(i);

            if (isDelimiter(ch, delimiters)) {
                buffer.append(ch);
                uncapitalizeNext = true;
            } else if (uncapitalizeNext) {
                buffer.append(Character.toLowerCase(ch));
                uncapitalizeNext = false;
            } else {
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }

    /**
     * <p>Capitalizes all the delimiter separated words in a String.
     * Only the first letter of each word is changed. To convert the
     * rest of each word to lowercase at the same time,
     *
     * <p>The delimiters represent a set of characters understood to separate words.
     * The first string character and the first non-delimiter character after a
     * delimiter will be capitalized. </p>
     *
     * <p>A <code>null</code> input String returns <code>null</code>.
     * Capitalization uses the unicode title case, normally equivalent to
     * upper case.</p>
     *
     * @param str  the String to capitalize, may be null
     * @param delimiters  set of characters to determine capitalization, null means whitespace
     * @return capitalized String, <code>null</code> if null String input
     */
    public static String capitalize(String str, char[] delimiters) {
        int delimLen = (delimiters == null ? -1 : delimiters.length);
        if (str == null || str.length() == 0 || delimLen == 0) {
            return str;
        }
        int strLen = str.length();
        StringBuffer buffer = new StringBuffer(strLen);
        boolean capitalizeNext = true;
        for (int i = 0; i < strLen; i++) {
            char ch = str.charAt(i);

            if (isDelimiter(ch, delimiters)) {
                buffer.append(ch);
                capitalizeNext = true;
            } else if (capitalizeNext) {
                buffer.append(Character.toTitleCase(ch));
                capitalizeNext = false;
            } else {
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }

    //-----------------------------------------------------------------------
    /**
     * Is the character a delimiter.
     *
     * @param ch  the character to check
     * @param delimiters  the delimiters
     * @return true if it is a delimiter
     */
    private static boolean isDelimiter(char ch, char[] delimiters) {
        if (delimiters == null) {
            return Character.isWhitespace(ch);
        }
        for (int i = 0, isize = delimiters.length; i < isize; i++) {
            if (ch == delimiters[i]) {
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {

        System.out.println(isCapitalaze("ala Ma Kota"));

    }

}
