package com.flowerfat.volleyutil.utils;

/**
 * Created by bigflower on 2015/12/22.
 */
public class L {
    public static boolean debug = true;

    public static void e(String msg)
    {
        if (debug)
        {
            System.err.println(msg);
        }
    }
    public static void i(String msg)
    {
        if (debug)
        {
            System.out.println(msg);
        }
    }
}
