package com.autoio.settings.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.autoio.core_sdk.bluetooth.LocalBluetoothAdapter;
import com.autoio.settings.R;
import com.autoio.settings.holder.BluetoothItemHolder;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by wuhongyun on 17-8-10.
 */

public class BluetoothListAdapter extends RecyclerView.Adapter<BluetoothItemHolder> {
    private Context context;
    private ArrayList<BluetoothDevice> mDevices;

    public BluetoothListAdapter(Context context, ArrayList<BluetoothDevice> mDevices) {
        this.context = context;
        this.mDevices = mDevices;
    }

    @Override
    public BluetoothItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        BluetoothItemHolder bluetoothItemHolder = new BluetoothItemHolder(View.inflate(context, R.layout.item_bluetooth_list_item, null));
        return bluetoothItemHolder;
    }

    @Override
    public void onBindViewHolder(BluetoothItemHolder holder, int position) {
        BluetoothDevice bluetoothDevice = mDevices.get(position);
        String name = null;
        if (TextUtils.isEmpty(bluetoothDevice.getName())){
            name = bluetoothDevice.getAddress();
        }else{
            name = bluetoothDevice.getName();
        }
        Set<BluetoothDevice> boundDevices = LocalBluetoothAdapter.getInstance().getBoundDevices();
        if (boundDevices!=null&&boundDevices.contains(bluetoothDevice)){
            holder.bluetooth_connected.setText(R.string.has_paired);
        }else{
            holder.bluetooth_connected.setText(R.string.has_not_paired);
        }
        if (bluetoothDevice.getBondState()==BluetoothDevice.BOND_BONDED){
            holder.bluetooth_connected.setText(R.string.bluetooth_connected);
        }else if (bluetoothDevice.getBondState()==BluetoothDevice.BOND_BONDING){
            holder.bluetooth_connected.setText(R.string.bluetooth_connecting);
        }else{
            //没有发起连接
        }

        holder.bluetooth_name.setText(name);

    }

    @Override
    public int getItemCount() {
        return mDevices.size();
    }
}
