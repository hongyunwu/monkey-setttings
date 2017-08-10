package com.autoio.settings.holder;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.LinearLayout;

import com.autoio.core_sdk.base.BaseHolder;
import com.autoio.settings.R;

import butterknife.BindView;

/**
 * Created by wuhongyun on 17-8-8.
 */

public class BlueToothViewHolder extends BaseHolder {

    @BindView(R.id.bluetooth_list)
    public RecyclerView bluetooth_list;
    /**
     * 此处使用butterKnife进行了view绑定操作
     *
     * @param itemView
     */
    public BlueToothViewHolder(View itemView) {
        super(itemView);
        initBluetoothAutoRevHolder();
        initBluetoothSearchHolder();
        initBluetoothModifyHolder();
        initBluetoothVisibleHolder();
    }

    private void initBluetoothVisibleHolder() {
        bluetoothVisiBleHolder = new BluetoothVisiBleHolder(item_bluetooth_be_visible);
    }

    public BluetoothVisiBleHolder bluetoothVisiBleHolder;
    @BindView(R.id.item_bluetooth_be_visible)
    LinearLayout item_bluetooth_be_visible;
    public class BluetoothVisiBleHolder extends BaseHolder{

        @BindView(R.id.bluetooth_can_be_visible_switch)
        public SwitchCompat bluetooth_can_be_visible_switch;
        /**
         * 此处使用butterKnife进行了view绑定操作
         *
         * @param itemView
         */
        public BluetoothVisiBleHolder(View itemView) {
            super(itemView);
        }
    }

    private void initBluetoothModifyHolder() {
        bluetoothModifyHolder = new BluetoothModifyHolder(item_bluetooth_modify);
    }
    public BluetoothModifyHolder bluetoothModifyHolder;
    @BindView(R.id.item_bluetooth_modify)
    LinearLayout item_bluetooth_modify;
    public class BluetoothModifyHolder extends BaseHolder{

        /**
         * 此处使用butterKnife进行了view绑定操作
         *
         * @param itemView
         */
        public BluetoothModifyHolder(View itemView) {
            super(itemView);
        }
    }
    private void initBluetoothSearchHolder() {
        bluetoothSearchHolder = new BluetoothSearchHolder(item_bluetooth_search);
    }

    public BluetoothSearchHolder bluetoothSearchHolder;
    @BindView(R.id.item_bluetooth_search)
    LinearLayout item_bluetooth_search;
    public class BluetoothSearchHolder extends BaseHolder{

        /**
         * 此处使用butterKnife进行了view绑定操作
         *
         * @param itemView
         */
        public BluetoothSearchHolder(View itemView) {
            super(itemView);
        }
    }

    private void initBluetoothAutoRevHolder() {
        bluetoothAutoRevHolder = new BluetoothAutoRevHolder(item_bluetooth_auto_rev);
    }
    public BluetoothAutoRevHolder bluetoothAutoRevHolder;
    @BindView(R.id.item_bluetooth_auto_rev)
    LinearLayout item_bluetooth_auto_rev;
    public class BluetoothAutoRevHolder extends BaseHolder{
        @BindView(R.id.bluetooth_auto_rev_switch)
        public SwitchCompat bluetooth_auto_rev_switch;
        /**
         * 此处使用butterKnife进行了view绑定操作
         *
         * @param itemView
         */
        public BluetoothAutoRevHolder(View itemView) {
            super(itemView);
        }
    }
}
