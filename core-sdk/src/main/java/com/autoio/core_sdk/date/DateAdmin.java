package com.autoio.core_sdk.date;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by wuhongyun on 17-8-11.
 */

public class DateAdmin {

    private DateHandleIntent dateHandleIntent;
    private Context context;

    private DateAdmin(Context context, DateHandleIntent dateHandleIntent){
        this.context = context;
        this.dateHandleIntent = dateHandleIntent;
    }
    private static DateAdmin dateAdmin;
    public static DateAdmin getInstance(Context context,DateHandleIntent dateHandleIntent){
        if (dateAdmin==null){
            synchronized (DateAdmin.class){
                if (dateAdmin==null){
                    dateAdmin = new DateAdmin(context,dateHandleIntent);
                }
            }
        }
        return dateAdmin;
    }
    public static boolean getAutoState(Context context,String name) {
        try {
            return Settings.Global.getInt(context.getContentResolver(), name) > 0;

        } catch (Settings.SettingNotFoundException snfe) {
            return false;
        }
    }

    public static void setAutoState(Context context,boolean isChecked) {
        try {
            Settings.Global.putInt(context.getContentResolver(), Settings.Global.AUTO_TIME,
                    isChecked ? 1 : 0);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static String getTimeZoneText(TimeZone tz) {
        SimpleDateFormat sdf = new SimpleDateFormat("ZZZZ, zzzz");
        sdf.setTimeZone(tz);
        return sdf.format(new Date());
    }

    public static String getTimeZoneText() {
        SimpleDateFormat sdf = new SimpleDateFormat("ZZZZ, zzzz");
        sdf.setTimeZone(Calendar.getInstance().getTimeZone());
        return sdf.format(new Date());
    }

    public void registerListener(){

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        context.registerReceiver(mIntentReceiver, filter, null, null);
    }

    private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            dateHandleIntent.handleIntent(intent);
        }
    };

    public void unregisterListener(){
        context.unregisterReceiver(mIntentReceiver);
    }


    public void resume() {
        registerListener();
    }

    public void pause() {
        unregisterListener();
    }

    public static void setDate(Context context, int year, int month, int day) {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        long when = c.getTimeInMillis();

        if (when / 1000 < Integer.MAX_VALUE) {
            ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE)).setTime(when);
        }
    }

    public static void setTime(Context context, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long when = c.getTimeInMillis();

        if (when / 1000 < Integer.MAX_VALUE) {
            ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE)).setTime(when);
        }
    }
}
