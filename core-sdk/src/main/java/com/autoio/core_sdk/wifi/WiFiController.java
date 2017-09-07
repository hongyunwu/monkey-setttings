package com.autoio.core_sdk.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.LinkProperties;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static com.autoio.core_sdk.wifi.WiFiSecurityType.SECURITY_EAP;
import static com.autoio.core_sdk.wifi.WiFiSecurityType.SECURITY_NONE;
import static com.autoio.core_sdk.wifi.WiFiSecurityType.SECURITY_PSK;
import static com.autoio.core_sdk.wifi.WiFiSecurityType.SECURITY_WEP;

/**
 * Created by wuhongyun on 17-8-11.
 */

public class WiFiController {

    Scanner scanner;
    private Context context;
    private WifiManager mWifiManager;
    private BroadcastReceiver mReceiver;
    private IntentFilter mFilter;

    public WiFiController(Context context, final WifiHandleEvent wifiHandleEvent){
        this.context = context;
        mFilter = new IntentFilter();
        mFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        mFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        mFilter.addAction(WifiManager.NETWORK_IDS_CHANGED_ACTION);
        mFilter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        mFilter.addAction(/*WifiManager.CONFIGURED_NETWORKS_CHANGED_ACTION*/"android.net.wifi.CONFIGURED_NETWORKS_CHANGE");
        mFilter.addAction("android.net.wifi.CONFIGURED_NETWORKS_CHANGE"/*WifiManager.LINK_CONFIGURATION_CHANGED_ACTION*/);
        mFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        mFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                wifiHandleEvent.handleEvent(context, intent);
            }
        };
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        scanner = new Scanner();

    }

    public void resume(){

        context.registerReceiver(mReceiver, mFilter);
    }
    public void pause(){
        context.unregisterReceiver(mReceiver);
        scanner.pause();
    }



    private static final int WIFI_RESCAN_INTERVAL_MS = 10 * 1000;

    public Scanner getScanner() {
        return scanner;
    }

    public List<ScanResult> getScanResult() {
        return mWifiManager.getScanResults();
    }

    public boolean isWifiEnabled() {
        return mWifiManager.isWifiEnabled();
    }

    public WifiInfo getConnectWifi() {

        return mWifiManager.getConnectionInfo();
    }

    public int getWifiState() {
        return mWifiManager.getWifiState();
    }

    public int getWifiType(ScanResult scanResult) {
        int wifiType = getSecurity(scanResult);
        Logger.i("getWifiType:"+wifiType);
        return wifiType;
    }

    public boolean reconnect() {
        if (!mWifiManager.isWifiEnabled()){
            return false;
        }

        return mWifiManager.reconnect();
    }



    public class Scanner extends Handler {
        private int mRetry = 0;

        public void resume() {
            if (!hasMessages(0)) {
                sendEmptyMessage(0);
            }
        }

        public void forceScan() {
            removeMessages(0);
            sendEmptyMessage(0);
        }

        public void pause() {
            mRetry = 0;
            removeMessages(0);
        }

        @Override
        public void handleMessage(Message message) {
            if (mWifiManager.startScan()) {
                mRetry = 0;
            } else if (++mRetry >= 3) {
                mRetry = 0;

                return;
            }
            //10秒扫描一次
            sendEmptyMessageDelayed(0, WIFI_RESCAN_INTERVAL_MS);
        }
    }

    /**
     * 创建wifiInfo
     * @param SSID  网络名称
     * @param password 密码
     * @param type
     * @return
     */
    public WifiConfiguration CreateWifiInfo(String SSID, String password, int type){
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";

        WifiConfiguration tempConfig = this.IsExsits(SSID);
        if(tempConfig != null) {
            mWifiManager.removeNetwork(tempConfig.networkId);
        }

        if(type == WiFiSecurityType.SECURITY_NONE) //WIFICIPHER_NOPASS
        {
            config.wepKeys[0] = "";
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if(type == WiFiSecurityType.SECURITY_WEP) //WIFICIPHER_WEP
        {
            config.hiddenSSID = true;
            config.wepKeys[0]= "\""+password+"\"";
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if(type == WiFiSecurityType.SECURITY_PSK) //WIFICIPHER_WPA
        {
            config.preSharedKey = "\""+password+"\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            //config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }
        return config;
    }

    private WifiConfiguration IsExsits(String SSID)
    {
        List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
        for (WifiConfiguration existingConfig : existingConfigs)
        {
            if (existingConfig.SSID.equals("\""+SSID+"\""))
            {
                return existingConfig;
            }
        }
        return null;
    }

    /**
     * 连接wifi
     * @param wifiConfiguration
     */
    public boolean enableNetWork(WifiConfiguration wifiConfiguration){
        return mWifiManager.enableNetwork(wifiConfiguration.networkId,true);
    }

    /**
     * 连接wifi
     * @param networkId
     */
    public boolean enableNetWork(int networkId){
        return mWifiManager.enableNetwork(networkId,true);
    }

    /**
     * 取消连接
     * @param wifiConfiguration
     */
    public void disableNetWork(WifiConfiguration wifiConfiguration) {
        WifiInfo connectionInfo = mWifiManager.getConnectionInfo();
        if (connectionInfo!=null){

            mWifiManager.disableNetwork(connectionInfo.getNetworkId());
            mWifiManager.disconnect();
        }
    }

    /**
     *
     * @param wifiConfiguration  wifi配置
     */
    public void connectNetWork(WifiConfiguration wifiConfiguration){

        try {


            Method connectNetWork = WifiManager.class.getMethod("connect",WifiConfiguration.class,Class.forName("android.net.wifi.WifiManager$ActionListener"));
            connectNetWork.invoke(mWifiManager,wifiConfiguration,null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static PskType getPskType(ScanResult result) {
        boolean wpa = result.capabilities.contains("WPA-PSK");
        boolean wpa2 = result.capabilities.contains("WPA2-PSK");
        if (wpa2 && wpa) {
            return PskType.WPA_WPA2;
        } else if (wpa2) {
            return PskType.WPA2;
        } else if (wpa) {
            return PskType.WPA;
        } else {
            return PskType.UNKNOWN;
        }
    }

    public int addNetWork(WifiConfiguration wifiConfiguration) {

        return mWifiManager.addNetwork(wifiConfiguration);

    }


    /**
     * 获取网络的类型
     * @param result
     * @return
     */
    static int getSecurity(ScanResult result) {
        if (result.capabilities.contains("WEP")) {
            return SECURITY_WEP;
        } else if (result.capabilities.contains("PSK")) {
            return SECURITY_PSK;
        } else if (result.capabilities.contains("EAP")) {
            return SECURITY_EAP;
        }


        return SECURITY_NONE;
    }






}
