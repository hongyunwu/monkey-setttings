package com.autoio.settings.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autoio.core_sdk.base.BaseHolder;
import com.autoio.settings.R;

import butterknife.BindView;

/**
 * Created by wuhongyun on 17-8-11.
 */

public class WiFiListItemHolder  extends BaseHolder{
    @BindView(R.id.wifi_name)
    public TextView wifi_name;
    @BindView(R.id.wifi_connected)
    public TextView wifi_connneted;
    @BindView(R.id.item_wifi_list_item)
    public LinearLayout item_wifi_list_item;
    /**
     * 此处使用butterKnife进行了view绑定操作
     *
     * @param itemView
     */
    public WiFiListItemHolder(View itemView) {
        super(itemView);
    }
}
