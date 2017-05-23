package com.code4a.retrofitutil.uitl;

import android.util.Log;

/**
 * Log统一管理类
 */
public class LogUtil {

    private LogUtil() { 
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 默认的tag
     */
    public static final String defaultTag = "code4a";
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    /**
     * 下面这个变量定义日志级别
     */
    public static final int LEVEL = VERBOSE;


    public static void v(String tag, String msg) {
        if (LEVEL <= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (LEVEL <= DEBUG) {
            int len = 3000;
            int start = 0;
            for (int i = 1; i < 100; i++) {
                if (msg.length() > len * i) {
                    Log.d(tag, "[分割打印Log" + i + "]" + msg.substring(start, len * i));
                    start = len * i;
                } else {
                    if (i == 1) {
                        Log.d(tag, msg.substring(start, msg.length()));
                    } else {
                        Log.d(tag, "[分割打印Log" + i + "]" + msg.substring(start, msg.length()));
                    }
                    break;
                }
            }
        }
    }

    public static void i(String tag, String msg) {
        if (LEVEL <= INFO) {
//            Log.i(tag, msg);
            int len = 3000;
            int start = 0;
            for (int i = 1; i < 100; i++) {
                if (msg.length() > len * i) {
                    Log.i(tag, "[分割打印Log" + i + "]" + msg.substring(start, len * i));
                    start = len * i;
                } else {
                    if (i == 1) {
                        Log.i(tag, msg.substring(start, msg.length()));
                    } else {
                        Log.i(tag, "[分割打印Log" + i + "]" + msg.substring(start, msg.length()));
                    }
                    break;
                }
            }
        }
    }

    public static void w(String tag, String msg) {
        if (LEVEL <= WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LEVEL <= ERROR) {
//            Log.e(tag, msg);
            int len = 3000;
            int start = 0;
            for (int i = 1; i < 100; i++) {
                if (msg.length() > len * i) {
                    Log.e(tag, "[分割打印Log" + i + "]" + msg.substring(start, len * i));
                    start = len * i;
                } else {
                    if (i == 1) {
                        Log.e(tag, msg.substring(start, msg.length()));
                    } else {
                        Log.e(tag, "[分割打印Log" + i + "]" + msg.substring(start, msg.length()));
                    }
                    break;
                }
            }
        }
    }

    public static void v(String msg) {
        if (LEVEL <= VERBOSE) {
            Log.v(defaultTag, msg);
        }
    }

    public static void d(String msg) {
        if (LEVEL <= DEBUG) {
//            Log.d(defaultTag, msg);
            int len = 3000;
            int start = 0;
            for (int i = 1; i < 100; i++) {
                if (msg.length() > len * i) {
                    Log.d(defaultTag, "[分割打印Log" + i + "]" + msg.substring(start, len * i));
                    start = len * i;
                } else {
                    if (i == 1) {
                        Log.d(defaultTag, msg.substring(start, msg.length()));
                    } else {
                        Log.d(defaultTag, "[分割打印Log" + i + "]" + msg.substring(start, msg.length()));
                    }
                    break;
                }
            }
        }
    }

    public static void i(String msg) {
        if (LEVEL <= INFO) {
//            Log.i(defaultTag, msg);
            int len = 3000;
            int start = 0;
            for (int i = 1; i < 100; i++) {
                if (msg.length() > len * i) {
                    Log.i(defaultTag, "[分割打印Log" + i + "]" + msg.substring(start, len * i));
                    start = len * i;
                } else {
                    if (i == 1) {
                        Log.i(defaultTag, msg.substring(start, msg.length()));
                    } else {
                        Log.i(defaultTag, "[分割打印Log" + i + "]" + msg.substring(start, msg.length()));
                    }
                    break;
                }
            }
        }
    }

    public static void w(String msg) {
        if (LEVEL <= WARN) {
            Log.w(defaultTag, msg);
        }
    }

    public static void e(String msg) {
        if (LEVEL <= ERROR) {
//            Log.e(defaultTag, msg);
            int len = 3000;
            int start = 0;
            for (int i = 0; i < 100; i++) {
                if (msg.length() > len * i) {
                    Log.e(defaultTag, "[分割打印Log" + i + "]" + msg.substring(start, len * (i + 1)));
                    start = len * i;
                } else {
                    if (i == 0) {
                        Log.e(defaultTag, msg.substring(start, msg.length()));
                    } else {
                        Log.e(defaultTag, "[分割打印Log" + i + "]" + msg.substring(start, msg.length()));
                    }
                    break;
                }
            }
        }
    }

    public static void m(String msg) {
        String methodName = new Exception().getStackTrace()[1].getMethodName();
        Log.v(defaultTag, methodName + ":    " + msg);
    }

    public static void m(int msg) {
        String methodName = new Exception().getStackTrace()[1].getMethodName();
        Log.v(defaultTag, methodName + ":    " + msg + "");
    }

    public static void m() {
        String methodName = new Exception().getStackTrace()[1].getMethodName();
        Log.v(defaultTag, methodName);
    }

    public static void v(int msg) {
        LogUtil.v(msg + "");
    }

    public static void d(int msg) {
        LogUtil.d(msg + "");
    }

    public static void i(int msg) {
        LogUtil.i(msg + "");
    }

    public static void w(int msg) {
        LogUtil.w(msg + "");
    }

    public static void e(int msg) {
        LogUtil.e(msg + "");
    }
}