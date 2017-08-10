package com.autoio.core_sdk.manager;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.IntRange;

/**
 * Created by wuhongyun on 17-8-9.
 */

public class BrightnessManager {
    public static final int MAX_PROGRESS = 10000;
    private int current_level;
    private Context context;

    private BrightnessManager(Context context) {
        this.context = context;
    }

    private static BrightnessManager brightManager;

    public static BrightnessManager getInstance(Context context) {
        if (brightManager == null) {
            synchronized (BrightnessManager.class) {
                if (brightManager == null) {
                    brightManager = new BrightnessManager(context);
                }
            }
        }
        return brightManager;
    }

    /**
     * 获取当前的屏幕亮度
     *
     * @param context
     * @return 返回获取到的亮度值，取不到默认返回255
     */
    public int getCurrentBright(Context context) {

        //closeAutoMatic(context);
        if (isAutoBrightness(context)){
            closeAutoMatic(context);
            //setBrightnessMode(context,Settings.System.sc);
        }
        //获取当前的屏幕亮度
        int currentBright = (Settings.System.getInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS,
                MAX_PROGRESS));
        if (currentBright<= BrightnessLevel.LEVEL_FULL&&currentBright>BrightnessLevel.LEVEL_FIVE){
            current_level = BrightnessLevel.LEVEL_FULL;
        }else if (currentBright<= BrightnessLevel.LEVEL_FIVE&&currentBright>BrightnessLevel.LEVEL_FOUR){
            current_level = BrightnessLevel.LEVEL_FIVE;
        }else if (currentBright<= BrightnessLevel.LEVEL_FOUR&&currentBright>BrightnessLevel.LEVEL_THREE){
            current_level = BrightnessLevel.LEVEL_FOUR;
        }else if (currentBright<= BrightnessLevel.LEVEL_THREE&&currentBright>BrightnessLevel.LEVEL_TWO){
            current_level = BrightnessLevel.LEVEL_THREE;
        }else if (currentBright<= BrightnessLevel.LEVEL_TWO&&currentBright>BrightnessLevel.LEVEL_ONE){
            current_level = BrightnessLevel.LEVEL_TWO;
        }else if (currentBright<= BrightnessLevel.LEVEL_ONE&&currentBright>1){
            current_level = BrightnessLevel.LEVEL_ONE;
        }else{
            current_level = BrightnessLevel.LEVEL_ZERO;
        }
        return currentBright;
    }

    /**
     * 关闭自动亮度调节
     *
     * @param context
     */
    private void closeAutoMatic(Context context) {
        //首先尝试关掉自动亮度
        try {
            if (Settings.System.getInt(context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE)
                    == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {

                Settings.System.putInt(context.getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            }
        } catch (Settings.SettingNotFoundException e) {

            e.printStackTrace();
        }
    }
    /**
     * 设置当前屏幕亮度的模式
     * SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
     * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
     */
    public static void setBrightnessMode(Context context, int brightMode) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, brightMode);
    }

    // 判断是否开启了自动亮度调节
    public static boolean isAutoBrightness(Context context) {
        boolean autoBrightness = false;
        ContentResolver contentResolver = context.getContentResolver();
        try {
            autoBrightness = Settings.System.getInt(contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return autoBrightness;
    }
    /**
     * 设置当前的亮度值
     *
     * @param progress
     */
    public void setCurrentBright(Context context, @IntRange(from = 0, to = MAX_PROGRESS) int progress) {

        if (progress<BrightnessLevel.LEVEL_ZERO){
            progress = BrightnessLevel.LEVEL_ZERO;
        }
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, progress);
        contentResolver.notifyChange(uri, null);

    }

    public void inCreaseBrightnessLevel() {
        switch (current_level){
            case BrightnessLevel.LEVEL_ZERO:
                setCurrentBright(context,BrightnessLevel.LEVEL_ONE);
                current_level = BrightnessLevel.LEVEL_ONE;
                break;
            case BrightnessLevel.LEVEL_ONE:
                setCurrentBright(context,BrightnessLevel.LEVEL_TWO);
                current_level = BrightnessLevel.LEVEL_TWO;
                break;
            case BrightnessLevel.LEVEL_TWO:
                setCurrentBright(context,BrightnessLevel.LEVEL_THREE);
                current_level = BrightnessLevel.LEVEL_THREE;
                break;
            case BrightnessLevel.LEVEL_THREE:
                setCurrentBright(context,BrightnessLevel.LEVEL_FOUR);
                current_level = BrightnessLevel.LEVEL_FOUR;
                break;
            case BrightnessLevel.LEVEL_FOUR:
                setCurrentBright(context,BrightnessLevel.LEVEL_FIVE);
                current_level = BrightnessLevel.LEVEL_FIVE;
                break;
            case BrightnessLevel.LEVEL_FIVE:
                setCurrentBright(context,BrightnessLevel.LEVEL_FULL);
                current_level = BrightnessLevel.LEVEL_FULL;
                break;
            case BrightnessLevel.LEVEL_FULL:
                //setCurrentBright(context,BrightnessLevel.LEVEL_TWO);
                current_level = BrightnessLevel.LEVEL_FULL;
                break;
            default:
                setCurrentBright(context,BrightnessLevel.LEVEL_FULL);
                current_level = BrightnessLevel.LEVEL_FULL;
                break;
        }
    }

    public void deCreaseBrightnessLevel() {
        switch (current_level){
            case BrightnessLevel.LEVEL_ZERO:
                setCurrentBright(context,BrightnessLevel.LEVEL_ZERO);
                current_level = BrightnessLevel.LEVEL_ZERO;
                break;
            case BrightnessLevel.LEVEL_ONE:
                setCurrentBright(context,BrightnessLevel.LEVEL_ZERO);
                current_level = BrightnessLevel.LEVEL_ZERO;
                break;
            case BrightnessLevel.LEVEL_TWO:
                setCurrentBright(context,BrightnessLevel.LEVEL_ONE);
                current_level = BrightnessLevel.LEVEL_ONE;
                break;
            case BrightnessLevel.LEVEL_THREE:
                setCurrentBright(context,BrightnessLevel.LEVEL_TWO);
                current_level = BrightnessLevel.LEVEL_TWO;
                break;
            case BrightnessLevel.LEVEL_FOUR:
                setCurrentBright(context,BrightnessLevel.LEVEL_THREE);
                current_level = BrightnessLevel.LEVEL_THREE;
                break;
            case BrightnessLevel.LEVEL_FIVE:
                setCurrentBright(context,BrightnessLevel.LEVEL_FOUR);
                current_level = BrightnessLevel.LEVEL_FOUR;
                break;
            case BrightnessLevel.LEVEL_FULL:
                setCurrentBright(context,BrightnessLevel.LEVEL_FIVE);
                current_level = BrightnessLevel.LEVEL_FIVE;
                break;
            default:
                setCurrentBright(context,BrightnessLevel.LEVEL_FULL);
                current_level = BrightnessLevel.LEVEL_FULL;
                break;
        }
    }
}
