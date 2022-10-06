package edu.whu.ioc.util;

public class StringUtil {

    /**
     * transform String failed
     */
    public static class TransformStringException extends RuntimeException {
    }

    public static final Class<?>[] simpleTypes = {boolean.class, char.class, long.class,
            int.class, short.class, byte.class, double.class, float.class, String.class};

    /**
     * turn the first char in String into upper case
     *
     * @param val String to transform
     * @return result
     * @throws TransformStringException string length is less than 1
     */
    public static String upperCaseFirst(String val) throws TransformStringException {
        if (val.length() < 1)
            throw new TransformStringException();
        char[] arr = val.toCharArray();
        arr[0] = Character.toUpperCase(arr[0]);
        return new String(arr);
    }

    /**
     * turn String into boolean, support "true"/"false", "yes"/"no", "on"/"off" and "1"/"0"
     *
     * @param val String
     * @return result
     * @throws TransformStringException cannot recognize the String
     */
    public static boolean toBoolean(String val) throws TransformStringException {
        if ("true".equals(val) || "yes".equals(val) || "on".equals(val) || "1".equals(val)) {
            return true;
        }
        if ("false".equals(val) || "no".equals(val) || "off".equals(val) || "0".equals(val)) {
            return false;
        }
        throw new TransformStringException();
    }

    /**
     * turn one char String into char
     * @param val String with only one char
     * @return char
     * @throws TransformStringException cannot recognize the String
     */
    public static char toChar(String val) throws TransformStringException {
        if (val.length() == 1) {
            return val.charAt(0);
        }
        throw new TransformStringException();
    }

    /**
     * turn String into long number
     * @param val String
     * @return long type number
     * @throws TransformStringException cannot recognize the String
     */
    public static long toLong(String val) throws TransformStringException {
        try {
            return Long.parseLong(val);
        } catch (NumberFormatException ignored) {

        }
        throw new TransformStringException();
    }

    /**
     * turn String into double number
     * @param val String
     * @return double type number
     * @throws TransformStringException cannot recognize the String
     */
    public static double toDouble(String val) throws TransformStringException {
        try {
            return Double.parseDouble(val);
        } catch (NumberFormatException ignored) {

        }
        throw new TransformStringException();
    }

}
