package com.autoio.settings.adapter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.autoio.core_sdk.wifi.WiFiController;
import com.autoio.settings.R;
import com.autoio.settings.holder.WiFiListItemHolder;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by wuhongyun on 17-8-11.
 */

public class WiFiListAdapter extends RecyclerView.Adapter<WiFiListItemHolder> {

    private WiFiController wifiController;
    private Context context;
    private List<ScanResult> scanResults;

    /**
     * wifi扫描出来的数据列表adapter
     * @param context  用于填充布局
     * @param scanResult 扫描出来的wifi结果
     * @param wiFiController 用于控制wifi的变化
     */
    public WiFiListAdapter(Context context, List<ScanResult> scanResult, WiFiController wiFiController) {
        connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        this.context =context;
        this.scanResults = scanResult;
        this.wifiController = wiFiController;
    }

    @Override
    public WiFiListItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final WiFiListItemHolder wiFiListItemHolder = new WiFiListItemHolder(View.inflate(context, R.layout.item_wifi_list_item, null));

        return wiFiListItemHolder;
    }

    @Override
    public void onBindViewHolder(final WiFiListItemHolder holder, final int position) {
        final ScanResult scanResult = this.scanResults.get(position);
        holder.wifi_name.setText(scanResult.SSID);
        holder.wifi_connneted.setText(scanResult.SSID);
        WifiInfo wifiInfo = wifiController.getConnectWifi();
        if (wifiInfo!=null&&wifiInfo.getBSSID()!=null && wifiInfo.getBSSID().equals(scanResult.BSSID)&& isWifiConnected(context)){
            holder.wifi_connneted.append("已连接");
        }else{
            holder.wifi_connneted.append("未连接");
        }

        holder.setListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //根据位置
                if (onWiFiItemClickListener!=null)
                    onWiFiItemClickListener.onWiFiItemClick(holder.itemView, scanResult, position);

            }
        },R.id.item_wifi_list_item);
        //5个级别,信号强度
        //level值为0～4
        int level = WifiManager.calculateSignalLevel(scanResult.level, 4);
        Logger.i("onBindViewHolder->calculateSignalLevel:"+level+",name:"+scanResult.SSID);
        switch (level){
            case 0:
                holder.wifi_level.setBackgroundResource(R.drawable.ic_wifi_signal_1_dark);

                break;
            case 1:
                holder.wifi_level.setBackgroundResource(R.drawable.ic_wifi_signal_2_dark);

                break;
            case 2:
                holder.wifi_level.setBackgroundResource(R.drawable.ic_wifi_signal_3_dark);

                break;
            case 3:
                holder.wifi_level.setBackgroundResource(R.drawable.ic_wifi_signal_4_dark);

                break;

        }
    }


    ConnectivityManager connectivityManager;
    public boolean isWifiConnected(Context context)
    {

        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(wifiNetworkInfo.isConnected())
        {
            return true ;
        }

        return false ;
    }
    @Override
    public int getItemCount() {
        if (scanResults !=null){
            return scanResults.size();
        }
        return 0;
    }

    public void setScanResults(List<ScanResult> scanResults) {
        this.scanResults = scanResults;
        notifyDataSetChanged();
    }


    public void setOnWiFiItemClickListener(OnWiFiItemClickListener onWiFiItemClickListener) {

        this.onWiFiItemClickListener = onWiFiItemClickListener;
    }

    private OnWiFiItemClickListener onWiFiItemClickListener;
    public interface OnWiFiItemClickListener{
        void onWiFiItemClick(View view,ScanResult scanResult,int position);
    }
}
