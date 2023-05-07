package net.femtoparsec.tools.irc;

import lombok.NonNull;

/**
 * @author Bastien Aracil
 **/
public class TagValueUnescaper {

    public static final char ESCAPING_CHAR = '\\';

    public String unescape(String value) {
        if (value.indexOf(ESCAPING_CHAR)<0) {
            return value;
        }

        final StringBuilder sb = new StringBuilder();

        boolean previousIsEscapingChar = false;

        for (int i = 0; i < value.length(); i++) {

            final char currentChar = value.charAt(i);
            final boolean currentIsEscapingChar = currentChar == ESCAPING_CHAR;

            if (currentIsEscapingChar && !previousIsEscapingChar) {
                previousIsEscapingChar = true;
            } else {
                final char c = previousIsEscapingChar?unescaped(currentChar):currentChar;
                sb.append(c);
                previousIsEscapingChar = false;
            }
        }

        return sb.toString();
    }

    public char unescaped(char escaped) {
        return switch (escaped) {
            case ':' -> ';';
            case 's' -> ' ';
            case 'r' -> '\r';
            case 'n' -> '\n';
            default -> escaped;
        };
    }
}
