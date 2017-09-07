package com.autoio.settings.ui.fragment;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CompoundButton;

import com.autoio.core_sdk.base.BaseFragment;
import com.autoio.core_sdk.bluetooth.LocalBluetoothAdapter;
import com.autoio.settings.R;
import com.autoio.settings.adapter.BluetoothListAdapter;
import com.autoio.settings.holder.BlueToothViewHolder;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

/**
 * Created by wuhongyun on 17-8-8.
 */

public class BlueToothFragment extends BaseFragment<BlueToothViewHolder> implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static BlueToothFragment blueToothFragment;
    private BlueToothReceiver mBluetoothReceiver;
    private BluetoothListAdapter bluetoothListAdapter;

    public static BlueToothFragment newInstance() {
        if (blueToothFragment ==null){
            synchronized (BlueToothFragment.class){
                if (blueToothFragment ==null){
                    blueToothFragment = new BlueToothFragment();
                }
            }
        }
        return blueToothFragment;

    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_bluetooth;
    }

    @Override
    public BlueToothViewHolder getViewHolder(View contentView) {
        return new BlueToothViewHolder(contentView);
    }

    @Override
    public void initData() {
        initBluetoothAutoRev();
        initBluetoothSearch();
        initBluetoothModify();
        initBluetoothList();
        initBluetoothVisible();
    }

    private void initBluetoothVisible() {

        Logger.i("scanMode:"+LocalBluetoothAdapter.getInstance().getScanMode());
        viewHolder.bluetoothVisiBleHolder.bluetooth_can_be_visible_switch.setChecked(LocalBluetoothAdapter.getInstance().getScanMode()==BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE);
        viewHolder.bluetoothVisiBleHolder.bluetooth_can_be_visible_switch.setOnCheckedChangeListener(this);
    }

    private void initBluetoothList() {
        viewHolder.bluetooth_list.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        bluetoothListAdapter = new BluetoothListAdapter(getContext(), mDevices);
        viewHolder.bluetooth_list.setAdapter(bluetoothListAdapter);
    }

    private void initBluetoothModify() {
        viewHolder.bluetoothModifyHolder.setListeners(this,R.id.bluetooth_modify,R.id.item_bluetooth_search);
    }

    private void initBluetoothSearch() {
        viewHolder.bluetoothSearchHolder.setListeners(this,R.id.item_bluetooth_search,R.id.bluetooth_search);
    }

    private void initBluetoothAutoRev() {
       // viewHolder.bluetoothAutoRevHolder.setListeners(this,R.id.item_bluetooth_auto_rev);
        viewHolder.bluetoothAutoRevHolder.bluetooth_auto_rev_switch.setOnCheckedChangeListener(this);
        viewHolder.bluetoothAutoRevHolder.bluetooth_auto_rev_switch.setChecked(LocalBluetoothAdapter.getInstance().getBluetoothState()==BluetoothAdapter.STATE_ON);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerListener();
    }

    private void registerListener() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        mBluetoothReceiver = new BlueToothReceiver();
        getContext().registerReceiver(mBluetoothReceiver,intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterListener();
    }

    private void unregisterListener() {
        getContext().unregisterReceiver(mBluetoothReceiver);
    }

    class BlueToothReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                //获取设备信息
                finishScan();

            }else if (BluetoothDevice.ACTION_FOUND.equals(action)){

                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (bluetoothDevice!=null){
                    if (!mDevices.contains(bluetoothDevice)){
                        mDevices.add(bluetoothDevice);
                        bluetoothListAdapter.notifyDataSetChanged();
                    }else{
                        mDevices.remove(bluetoothDevice);//
                        mDevices.add(bluetoothDevice);
                        bluetoothListAdapter.notifyDataSetChanged();
                    }
                }

                Logger.i("BluetoothDevice->"+bluetoothDevice.getName()+",address:"+bluetoothDevice.getAddress());

            }else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)){
                if (LocalBluetoothAdapter.getInstance().getState()==BluetoothAdapter.STATE_OFF){
                    viewHolder.bluetoothAutoRevHolder.bluetooth_auto_rev_switch.setChecked(false);
                    clearBluetoothList();
                }else if (LocalBluetoothAdapter.getInstance().getState()==BluetoothAdapter.STATE_ON){
                    viewHolder.bluetoothAutoRevHolder.bluetooth_auto_rev_switch.setChecked(true);
                }
                if (LocalBluetoothAdapter.getInstance().getScanMode()==BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE){
                    viewHolder.bluetoothVisiBleHolder.bluetooth_can_be_visible_switch.setChecked(true);
                } else {
                    viewHolder.bluetoothVisiBleHolder.bluetooth_can_be_visible_switch.setChecked(false);
                }

            }else if (BluetoothAdapter.ACTION_SCAN_MODE_CHANGED.equals(action)){
                if (LocalBluetoothAdapter.getInstance().getScanMode()==BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE){
                    viewHolder.bluetoothVisiBleHolder.bluetooth_can_be_visible_switch.setChecked(true);
                } else {
                    viewHolder.bluetoothVisiBleHolder.bluetooth_can_be_visible_switch.setChecked(false);
                }

            }else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)){//绑定状态发生改变,需要更新devices信息
                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (!mDevices.contains(bluetoothDevice)){
                    mDevices.add(bluetoothDevice);
                    bluetoothListAdapter.notifyDataSetChanged();
                }else{
                    mDevices.remove(bluetoothDevice);//
                    mDevices.add(bluetoothDevice);
                    bluetoothListAdapter.notifyDataSetChanged();
                }
                Logger.i("BluetoothDevice->name:"+BluetoothListAdapter.getBlueToothName(bluetoothDevice));

            }

            Logger.i("BluetoothReceiver->"+action+",scanMode:"+LocalBluetoothAdapter.getInstance().getScanMode());

        }
    }

    /**
     * 结束扫描
     */
    private void finishScan() {

        viewHolder.bluetooth_scanning.setVisibility(View.GONE);
        viewHolder.bluetooth_list.setVisibility(View.VISIBLE);

    }

    /**
     * 开始扫描
     */
    private void startScan() {
        //if (LocalBluetoothAdapter.getInstance().getBluetoothState()== BluetoothAdapter.STATE_ON){
        LocalBluetoothAdapter.getInstance().startScanning(true);
        viewHolder.bluetooth_scanning.setVisibility(View.VISIBLE);
        viewHolder.bluetooth_list.setVisibility(View.INVISIBLE);
        clearBluetoothList();
        Logger.i("LocalBluetoothAdapter->name:"+LocalBluetoothAdapter.getInstance().getName());
        //LocalBluetoothAdapter.getInstance().setName("wuhongyun");

        //}else{
        //   Logger.i("蓝牙没有打开...");
        //}

    }


    ArrayList<BluetoothDevice> mDevices = new ArrayList<>();
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bluetooth_search:
            case R.id.item_bluetooth_search:
                startScan();
                break;

        }
    }

    /**
     * 清理设备
     */
    private void clearBluetoothList() {
        mDevices.clear();
        bluetoothListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.bluetooth_auto_rev_switch:
                if (isChecked){
                    boolean enable = LocalBluetoothAdapter.getInstance().enable();
                    Logger.i("LocalBluetoothAdapter->enable:"+enable);
                }else{
                    boolean disable = LocalBluetoothAdapter.getInstance().disable();
                    Logger.i("LocalBluetoothAdapter->disable:"+disable);
                }

                break;
            case R.id.bluetooth_can_be_visible_switch:
                if (isChecked){
                    LocalBluetoothAdapter.getInstance().setScanMode(BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE);
                }else{
                    LocalBluetoothAdapter.getInstance().setScanMode(BluetoothAdapter.SCAN_MODE_CONNECTABLE);
                }

                break;
        }
    }
}
