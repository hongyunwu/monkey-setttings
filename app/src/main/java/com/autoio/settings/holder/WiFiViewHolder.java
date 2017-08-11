package com.autoio.settings.holder;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.autoio.core_sdk.base.BaseHolder;
import com.autoio.settings.R;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import butterknife.BindView;

/**
 * Created by wuhongyun on 17-8-8.
 */

public class WiFiViewHolder extends BaseHolder {
    /**
     * 此处使用butterKnife进行了view绑定操作
     *
     * @param itemView
     */
    public WiFiViewHolder(View itemView) {
        super(itemView);
        initWiFiOpenHolder();
        initWiFiApOpenHolder();
        initWiFiDevicesHolder();
    }

    private void initWiFiOpenHolder() {
        wiFiOpenHolder = new WiFiOpenHolder(item_wifi_open);
    }

    public WiFiOpenHolder wiFiOpenHolder;
    @BindView(R.id.item_wifi_open)
    LinearLayout item_wifi_open;

    public class WiFiOpenHolder extends BaseHolder{

        @BindView(R.id.wifi_open_switch)
        public SwitchCompat wifi_open_switch;
        /**
         * 此处使用butterKnife进行了view绑定操作
         *
         * @param itemView
         */
        public WiFiOpenHolder(View itemView) {
            super(itemView);
        }
    }


    private void initWiFiApOpenHolder() {
        wiFiApOpenHolder = new WiFiApOpenHolder(item_wifi_ap_open);
    }

    public WiFiApOpenHolder wiFiApOpenHolder;
    @BindView(R.id.item_wifi_ap_open)
    LinearLayout item_wifi_ap_open;

    public class WiFiApOpenHolder extends BaseHolder{

        @BindView(R.id.wifi_ap_open_switch)
        public SwitchCompat wifi_ap_open_switch;
        /**
         * 此处使用butterKnife进行了view绑定操作
         *
         * @param itemView
         */
        public WiFiApOpenHolder(View itemView) {
            super(itemView);
        }
    }

    private void initWiFiDevicesHolder() {
        wiFiDevicesHolder = new WiFiDevicesHolder(item_wifi_devices);
    }

    public WiFiDevicesHolder wiFiDevicesHolder;
    @BindView(R.id.item_wifi_devices)
    LinearLayout item_wifi_devices;

    public class WiFiDevicesHolder extends BaseHolder{
        @BindView(R.id.wifi_devices_header)
        public LinearLayout wifi_devices_header;
        @BindView(R.id.wifi_devices_arrow)
        public ImageButton wifi_devices_arrow;
        @BindView(R.id.wifi_list)
        public RecyclerView wifi_list;
        @BindView(R.id.wifi_devices_content)
        public ExpandableRelativeLayout wifi_devices_content;
        /**
         * 此处使用butterKnife进行了view绑定操作
         *
         * @param itemView
         */
        public WiFiDevicesHolder(View itemView) {
            super(itemView);
        }
    }
}
