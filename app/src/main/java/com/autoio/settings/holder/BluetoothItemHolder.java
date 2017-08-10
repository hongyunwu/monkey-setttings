package com.autoio.settings.holder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autoio.core_sdk.base.BaseHolder;
import com.autoio.settings.R;

import butterknife.BindView;

/**
 * Created by wuhongyun on 17-8-10.
 */

public class BluetoothItemHolder extends BaseHolder {
    @BindView(R.id.bluetooth_name)
    public TextView bluetooth_name;
    @BindView(R.id.bluetooth_connected)
    public Button bluetooth_connected;
    /**
     * 此处使用butterKnife进行了view绑定操作
     *
     * @param itemView
     */
    public BluetoothItemHolder(View itemView) {
        super(itemView);
    }
}
