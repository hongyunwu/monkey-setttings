package com.autoio.settings.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CompoundButton;

import com.autoio.core_sdk.base.BaseFragment;
import com.autoio.core_sdk.wifi.WiFiController;
import com.autoio.core_sdk.wifi.WifiApEnabler;
import com.autoio.core_sdk.wifi.WifiEnabler;
import com.autoio.core_sdk.wifi.WifiHandleEvent;
import com.autoio.settings.R;
import com.autoio.settings.adapter.WiFiListAdapter;
import com.autoio.settings.holder.WiFiViewHolder;
import com.autoio.settings.view.AutoioDialog;
import com.orhanobut.logger.Logger;

import java.util.List;

import static com.autoio.settings.utils.AnimUtils.toggleContent;

/**
 * Created by wuhongyun on 17-8-8.
 */

public class WiFiFragment extends BaseFragment<WiFiViewHolder> implements CompoundButton.OnCheckedChangeListener,WifiHandleEvent,View.OnClickListener {


    private WifiEnabler wifiEnabler;
    private WifiApEnabler wifiApEnabler;
    private WiFiController wiFiController;
    private WiFiListAdapter wiFiListAdapter;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_wifi;
    }

    @Override
    public WiFiViewHolder getViewHolder(View contentView) {
        return new WiFiViewHolder(contentView);
    }

    @Override
    public void initData() {
        wifiEnabler = new WifiEnabler(getContext(),viewHolder.wiFiOpenHolder.wifi_open_switch);
        wifiApEnabler = new WifiApEnabler(getContext(), viewHolder.wiFiApOpenHolder.wifi_ap_open_switch);
        viewHolder.wiFiApOpenHolder.wifi_ap_open_switch.setOnCheckedChangeListener(this);
        wiFiController = new WiFiController(getContext(), this);
        initWifiList();

    }

    private void initWifiList() {
        viewHolder.wiFiDevicesHolder.setListeners(this,R.id.wifi_devices_header,R.id.wifi_devices_arrow);
    }

    @Override
    public void onResume() {
        super.onResume();
        wifiEnabler.resume();
        wifiApEnabler.resume();
        wiFiController.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        wifiEnabler.pause();
        wifiApEnabler.pause();
        wiFiController.pause();
    }

    private static WiFiFragment wiFiFragment;

    public static WiFiFragment newInstance() {
        if (wiFiFragment ==null){
            synchronized (WiFiFragment.class){
                if (wiFiFragment ==null){
                    wiFiFragment = new WiFiFragment();
                }
            }
        }
        return wiFiFragment;

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.wifi_ap_open_switch:
                wifiApEnabler.setSoftapEnabled(isChecked);

                break;


        }
    }

    @Override
    public void handleEvent(Context context, Intent intent) {
        Logger.i("action->"+intent.getAction());
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            updateWifiState(intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN));
        } else if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            NetworkInfo info = (NetworkInfo) intent.getParcelableExtra(
                    WifiManager.EXTRA_NETWORK_INFO);
            //mConnected.set(info.isConnected());
            //changeNextButtonState(info.isConnected());
            //updateAccessPoints();
            //updateConnectionState(info.getDetailedState());
            updateWifiState(wiFiController.getWifiState());


        }else if ("android.net.wifi.SCAN_RESULTS".equals(intent.getAction())){
            List<ScanResult> scanResult = wiFiController.getScanResult();
            Logger.i("scanResult:"+scanResult);
            //刷新页面
            refreshScanResult(scanResult);
        }

    }
    private void updateWifiState(int state) {
        Logger.i("state->"+state);
        switch (state) {
            case WifiManager.WIFI_STATE_ENABLED:
                wiFiController.getScanner().resume();//扫描wfi
                List<ScanResult> scanResult = wiFiController.getScanResult();
                Logger.i("scanResult:"+scanResult);
                //刷新页面
                refreshScanResult(scanResult);

                return; // not break, to avoid the call to pause() below

            case WifiManager.WIFI_STATE_ENABLING:
                //正在打开wifi
                break;

            case WifiManager.WIFI_STATE_DISABLED:
                //setOffMessage();
                refreshScanResult(null);
                break;
        }


        wiFiController.getScanner().pause();
    }

    /**
     * 刷新wifi列表页面
     * @param scanResult wifi列表数据
     */
    private void refreshScanResult(List<ScanResult> scanResult) {
        if (wiFiListAdapter==null){
            viewHolder.wiFiDevicesHolder.wifi_list
                    .setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            wiFiListAdapter = new WiFiListAdapter(getContext(), scanResult,wiFiController);
            viewHolder.wiFiDevicesHolder.wifi_list.setAdapter(wiFiListAdapter);
            wiFiListAdapter.setOnWiFiItemClickListener(new WiFiListAdapter.OnWiFiItemClickListener() {
                @Override
                public void onWiFiItemClick(View view, ScanResult scanResult, int position) {

                    Logger.i("click-scanResult->"+scanResult+",position->"+position);
                    WifiInfo wifiInfo = wiFiController.getConnectWifi();
                    if (wifiInfo!=null&&wifiInfo.getBSSID()!=null && wifiInfo.getBSSID().equals(scanResult.BSSID)){
                        //已经连接上，不需要弹框

                    }else{
                        //未连接上，当点击时需要弹框
                        showInputPwdDialog(scanResult);
                    }
                }
            });
        }else{
            wiFiListAdapter.setScanResults(scanResult);
        }

    }


    /**
     *弹出对话框询问密码
     * @param scanResult
     */
    private void showInputPwdDialog(ScanResult scanResult) {
        //输入密码
        new AutoioDialog.Builder(getContext())
                .setScanResult(scanResult)
                .setOnWiFiPwdModifyListener(new AutoioDialog.Builder.OnWiFiPwdModifyListener() {
                    @Override
                    public void onCancelClick(DialogInterface dialogInterface, ScanResult scanResult) {
                        dialogInterface.dismiss();
                        Logger.i("scanResult->"+scanResult);
                    }

                    @Override
                    public void onConfirmClick(DialogInterface dialogInterface, String inputPwd, ScanResult scanResult) {
                        dialogInterface.dismiss();
                        Logger.i("scanResult->"+scanResult+", inputPwd->"+inputPwd);
                        WifiConfiguration wifiConfiguration = wiFiController.CreateWifiInfo(scanResult.SSID, inputPwd, wiFiController.getWifiType());

                        wiFiController.enableNetWork(wifiConfiguration);
                        //此处需要进行连接wifi的操作
                    }}
                ).create().show();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wifi_devices_header:
            case R.id.wifi_devices_arrow:
                if (wiFiController.isWifiEnabled()){
                    toggleContent(viewHolder.wiFiDevicesHolder.wifi_devices_content,viewHolder.wiFiDevicesHolder.wifi_devices_arrow);
                }
                break;
        }
    }

}
