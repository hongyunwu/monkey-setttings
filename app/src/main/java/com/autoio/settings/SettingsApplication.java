package com.autoio.settings;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Created by wuhongyun on 17-8-8.
 */

public class SettingsApplication extends Application {

    private static final String TAG = "AutoIO_settings";


    @Override
    public void onCreate() {
        super.onCreate();
        initLogger();
    }
    /**
     * 日志类，管理负责管理日志以及日志显示样式
     */
    private void initLogger() {


        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(2)        // (Optional) Hides internal method calls up to offset. Default 5
                //.logStrategy(tag) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag(TAG)// (Optional) Global tag for every log. Default PRETTY_LOGGER
                //.logStrategy(new LogcatLogStrategy())
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy){

            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }

            @Override
            public void log(int priority, String tag, String message) {
                super.log(priority, tag, message);
            }
        });

        Logger.i("this is a simple log for test info...");

    }
}
