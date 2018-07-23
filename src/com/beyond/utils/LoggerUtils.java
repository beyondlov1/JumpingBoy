package com.beyond.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerUtils {
    private static Logger logger=Logger.getLogger("myLogger");

    public static void log(String msg){
        logger.log(Level.FINE,msg);
    }
}
