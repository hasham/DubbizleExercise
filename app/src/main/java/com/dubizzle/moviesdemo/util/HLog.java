package com.dubizzle.moviesdemo.util;

import android.util.Log;

import com.dubizzle.moviesdemo.BuildConfig;


/**
 * Developed by Hasham.Tahir on 1/16/2017.
 */

public class HLog {

    private static final String TAG = HLog.class.getSimpleName();
    private static final String EMPTY = "";

    private static int e(String tag, String format, Object... args) {
        return Log.e(tag, format(format, args));
    }

    public static void e(String tag, String msg) {
        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            int maxLogSize = 1000;
            for (int i = 0; i <= msg.length() / maxLogSize; i++) {
                int start = i * maxLogSize;
                int end = (i + 1) * maxLogSize;
                end = end > msg.length() ? msg.length() : end;
                Log.e(tag, msg.substring(start, end));
            }
        }
    }

    public static int e(String tag, String msg, Throwable e) {
        return Log.e(tag, msg, e);
    }

    public static int e(String tag, String format, Throwable e, Object... args) {
        return Log.e(tag, format(format, args), e);
    }

    private static String format(String format, Object... args) {
        try {
            return String.format(format == null ? EMPTY : format, args);
        } catch (RuntimeException e) {
            HLog.e(TAG, "format error. reason=%s, format=%s", e.getMessage(), format);
            return String.format(EMPTY, format);
        }

    }
}
