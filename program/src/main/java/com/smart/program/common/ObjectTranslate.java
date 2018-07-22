package com.smart.program.common;

public class ObjectTranslate {

    /**
     * 为空返回默认值def
     *
     * @param o
     * @param def
     * @return
     */
    public static String getString(Object o, String def) {
        if (o == null) {
            return def;
        }
        return String.valueOf(o);
    }

    /**
     * 转为String
     *
     * @param o
     * @return
     */
    public static String getString(Object o) {
        return String.valueOf(o);
    }

    /**
     * 转为double
     *
     * @param o
     * @return
     */
    public static double getDouble(Object o) {
        double aa = Double.parseDouble(o.toString());
        return aa;
    }

    /**
     * 转为Boolean
     *
     * @param o
     * @return
     */
    public static boolean getBoolean(Object o, boolean def) {
        try {
            if (o == null) {
                return def;
            }
            //def =true; 无论怎么返回都是token过期
            def = Boolean.parseBoolean(String.valueOf(o));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    /**
     * @param 转为INT
     * @param o
     * @return
     */
    public static int getInt(Object o, int def) {
        try {
            if (o == null) {
                return def;
            }
            def = Integer.parseInt(o.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    /**
     * 转为long
     *
     * @param o
     * @return
     */
    public static long getLong(Object o, long def) {
        try {
            if (o == null) {
                return def;
            }
            String str = o.toString();
            if (str.equals("") || str.equals("null")) {
                return def;
            }

            def = Long.parseLong(o.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }


}
