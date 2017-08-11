/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.autoio.core_sdk.wifi;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v7.widget.SwitchCompat;

import com.autoio.core_sdk.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;


public class WifiApEnabler {
    private final Context mContext;
    private final SwitchCompat mCheckBox;

    private WifiManager mWifiManager;
    private final IntentFilter mIntentFilter;

    ConnectivityManager mCm;
    private String[] mWifiRegexs;
    boolean withStaEnabled;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (/*WifiManager.WIFI_AP_STATE_CHANGED_ACTION*/"android.net.wifi.WIFI_AP_STATE_CHANGED".equals(action)) {
                handleWifiApStateChanged(intent.getIntExtra(
                        "wifi_state"/*WifiManager.EXTRA_WIFI_AP_STATE*/, 14/*WifiManager.WIFI_AP_STATE_FAILED*/));
            } else if (/*ConnectivityManager.ACTION_TETHER_STATE_CHANGED*/"android.net.conn.TETHER_STATE_CHANGED".equals(action)) {
                ArrayList<String> available = intent.getStringArrayListExtra(
                        "availableArray"/*ConnectivityManager.EXTRA_AVAILABLE_TETHER*/);
                ArrayList<String> active = intent.getStringArrayListExtra(
                        /*ConnectivityManager.EXTRA_ACTIVE_TETHER*/"activeArray");
                ArrayList<String> errored = intent.getStringArrayListExtra(
                        /*ConnectivityManager.EXTRA_ERRORED_TETHER*/"erroredArray");
                updateTetherState(available.toArray(), active.toArray(), errored.toArray());
            } else if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(action)) {
                enableWifiCheckBox();
            } else if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(action)) {
                handleWifiStateChanged(intent.getIntExtra(
                        WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN));
            }

        }
    };

    public WifiApEnabler(Context context, SwitchCompat checkBox) {
        mContext = context;
        mCheckBox = checkBox;


        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mCm = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        mWifiRegexs = getTetherableWifiRegexs();

        mIntentFilter = new IntentFilter("android.net.wifi.WIFI_AP_STATE_CHANGED"/*WifiManager.WIFI_AP_STATE_CHANGED_ACTION*/);
        mIntentFilter.addAction("android.net.conn.TETHER_STATE_CHANGED"/*ConnectivityManager.ACTION_TETHER_STATE_CHANGED*/);
        mIntentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        mIntentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
    }

    private String[] getTetherableWifiRegexs() {

        try {
            Method getTetherableWifiRegexs = ConnectivityManager.class.getMethod("getTetherableWifiRegexs");

            return (String[]) getTetherableWifiRegexs.invoke(mCm);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    public void resume() {
        mContext.registerReceiver(mReceiver, mIntentFilter);
        enableWifiCheckBox();
    }

    public void pause() {
        mContext.unregisterReceiver(mReceiver);
    }

    private void enableWifiCheckBox() {
        boolean isAirplaneMode = Settings.Global.getInt(mContext.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        if(!isAirplaneMode) {
            mCheckBox.setEnabled(true);
        } else {
            //mCheckBox.setSummary(mOriginalSummary);
            mCheckBox.setEnabled(false);
        }
    }

    public void setSoftapEnabled(boolean enable) {
        final ContentResolver cr = mContext.getContentResolver();
        /**
         * Disable Wifi if enabling tethering
         */
        int wifiState = mWifiManager.getWifiState();
        if (enable && ((wifiState == WifiManager.WIFI_STATE_ENABLING) ||
                    (wifiState == WifiManager.WIFI_STATE_ENABLED))) {
            mWifiManager.setWifiEnabled(false);
            try {
                Settings.Global.putInt(cr, "wifi_saved_state"/*Settings.Global.WIFI_SAVED_STATE*/, 1);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        if (setWifiApEnabled(null, enable)) {
            /* Disable here, enabled on receiving success broadcast */
            mCheckBox.setEnabled(false);
        } else {
            //mCheckBox.setSummary(R.string.wifi_error);
        }

        /**
         *  If needed, restore Wifi on tether disable
         */
        if (!enable) {
            int wifiSavedState = 0;
            withStaEnabled = false;
            try {
                wifiSavedState = Settings.Global.getInt(cr,"wifi_saved_state" /*Settings.Global.WIFI_SAVED_STATE*/);
                withStaEnabled = wifiSavedState == 1? true : false;
            } catch (Settings.SettingNotFoundException e) {
                ;
            }
            if (wifiSavedState == 1) {
                mWifiManager.setWifiEnabled(true);
                /* Disable here, enabled when sta state change to  WIFI_STATE_ENABLING*/
                mCheckBox.setEnabled(false);
                Settings.Global.putInt(cr, /*Settings.Global.WIFI_SAVED_STATE*/"wifi_saved_state", 0);
            }
        }
    }

    private boolean setWifiApEnabled(WifiConfiguration wifiConfiguration, boolean enabled) {

        try {

            Method setWifiApEnabled = WifiManager.class.getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            return (boolean) setWifiApEnabled.invoke(mWifiManager,wifiConfiguration,enabled);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateConfigSummary(WifiConfiguration wifiConfig) {

        /*mCheckBox.setSummary(String.format(
                    mContext.getString(R.string.wifi_tether_enabled_subtext),
                    (wifiConfig == null) ? s : wifiConfig.SSID));*/
    }

    private void updateTetherState(Object[] available, Object[] tethered, Object[] errored) {
        boolean wifiTethered = false;
        boolean wifiErrored = false;

        for (Object o : tethered) {
            String s = (String)o;
            for (String regex : mWifiRegexs) {
                if (s.matches(regex)) wifiTethered = true;
            }
        }
        for (Object o: errored) {
            String s = (String)o;
            for (String regex : mWifiRegexs) {
                if (s.matches(regex)) wifiErrored = true;
            }
        }

        if (wifiTethered) {
            WifiConfiguration wifiConfig = getWifiApConfiguration();
            updateConfigSummary(wifiConfig);
        } else if (wifiErrored) {
            //mCheckBox.setSummary(R.string.wifi_error);
        }
    }

    private WifiConfiguration getWifiApConfiguration() {
        try {

            Method getWifiApConfiguration = WifiManager.class.getMethod("getWifiApConfiguration");
            return (WifiConfiguration) getWifiApConfiguration.invoke(mWifiManager);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void handleWifiApStateChanged(int state) {
        switch (state) {
            case 12/*WifiManager.WIFI_AP_STATE_ENABLING*/:
                //mCheckBox.setSummary(R.string.wifi_tether_starting);
                mCheckBox.setEnabled(false);
                break;
            case 13/*WifiManager.WIFI_AP_STATE_ENABLED*/:
                /**
                 * Summary on enable is handled by tether
                 * broadcast notice
                 */
                mCheckBox.setChecked(true);
                /* Doesnt need the airplane check */
                mCheckBox.setEnabled(true);
                break;
            case /*WifiManager.WIFI_AP_STATE_DISABLING*/10:
                //mCheckBox.setSummary(R.string.wifi_tether_stopping);
                mCheckBox.setEnabled(false);
                break;
            case 11/*WifiManager.WIFI_AP_STATE_DISABLED*/:
                mCheckBox.setChecked(false);
                //mCheckBox.setSummary(mOriginalSummary);
                if(!withStaEnabled)
                    enableWifiCheckBox();
                break;
            default:
                mCheckBox.setChecked(false);
                //mCheckBox.setSummary(R.string.wifi_error);
                enableWifiCheckBox();
        }
    }

    private void handleWifiStateChanged(int state) {
        switch (state) {
            case WifiManager.WIFI_STATE_ENABLING:
                if(!mCheckBox.isEnabled()) {
                    enableWifiCheckBox();
                }
                break;
            default:
                break;
        }
    }
}
