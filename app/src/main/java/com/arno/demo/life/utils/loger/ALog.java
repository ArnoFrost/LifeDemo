package com.arno.demo.life.utils.loger;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.arno.demo.life.BuildConfig;

public class ALog {
    private static final String TAG = "ALog";
    public static String LOG_NAME = "ALog.txt";

    private static Application mContext;
    private static final boolean DEBUG_CLOSE_LOG = false && BuildConfig.DEBUG;


    /**
     * 自定义日志tag
     */
    private static String CUSTOM_TAG;
    private static String LOG_PREFIX;


    public static void init(Application mContext) {
        Log.d(TAG, "init: ");
        init(mContext, new ALogConfig.Builder().build());
    }

    public static void init(Application mContext, @NonNull ALogConfig config) {
        ALog.mContext = mContext;
        LOG_NAME = config.logName;
        CUSTOM_TAG = config.customTag;
        LOG_PREFIX = config.logPrefix;
    }

    private static class ALogConfig {
        private String logName;
        private String customTag;
        private String logPrefix;

        public ALogConfig() {
        }

        private static final class Builder {
            private Builder() {

            }

            public static Builder create() {
                return new Builder();
            }

            private String logName = "ALog.txt";
            private String customTag = "##Main";
            private String logPrefix = "##ALog_";


            public Builder withLogName(String logName) {
                this.logName = logName;
                return this;
            }

            public Builder withCustomTag(String tag) {
                this.customTag = tag;
                return this;
            }

            public Builder withLogPrefix(String logPrefix) {
                this.logPrefix = logPrefix;
                return this;
            }


            private ALogConfig build() {
                ALogConfig config = new ALogConfig();
                config.customTag = customTag;
                config.logName = logName;
                config.logPrefix = logPrefix;
                return config;
            }
        }

    }

    public static void v(String tag, String msg) {
        if (DEBUG_CLOSE_LOG) {
            return;
        }
        if (msg != null) {
            Log.v(tag, msg);
        }
    }
}
