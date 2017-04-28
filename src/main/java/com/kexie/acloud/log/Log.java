package com.kexie.acloud.log;

import java.util.logging.Logger;

/**
 * Author : wen
 * Create time : 17-3-11 上午9:34
 * Description : 能够输出行号的Log工具
 */

public class Log {

    public static void error(String message) {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        String s = ste.getFileName() + ": Line " + ste.getLineNumber();

        System.out.println(ste.getClassName() + "." + ste.getMethodName() + "  (" + ste.getFileName() + ":" + ste.getLineNumber() + ")");
        System.out.println(message);

    }

    public static void debug(String message) {
        StackTraceElement ste = new Throwable().getStackTrace()[1];

        System.out.println(ste.getClassName() + "." + ste.getMethodName() + "  (" + ste.getFileName() + ":" + ste.getLineNumber() + ")");
        System.out.println(message);
    }
}
