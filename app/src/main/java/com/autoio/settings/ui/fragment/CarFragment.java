package com.autoio.settings.ui.fragment;

import android.graphics.drawable.ClipDrawable;
import android.view.View;
import android.widget.CompoundButton;

import com.autoio.core_sdk.base.BaseFragment;
import com.autoio.core_sdk.manager.BrightnessLevel;
import com.autoio.core_sdk.manager.BrightnessManager;
import com.autoio.settings.R;
import com.autoio.settings.holder.CarViewHolder;
import com.orhanobut.logger.Logger;

import static com.autoio.settings.utils.AnimUtils.toggleContent;

/**
 * Created by wuhongyun on 17-8-8.
 * 对我的汽车进行设置的页面
 */

public class CarFragment extends BaseFragment<CarViewHolder> implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    private static CarFragment carFragment;

    public static CarFragment newInstance() {
        if (carFragment==null){
            synchronized (CarFragment.class){
                if (carFragment==null){
                    carFragment = new CarFragment();
                }
            }
        }
        return carFragment;

    }


    @Override
    public int getLayoutID() {
        return R.layout.fragment_car;
    }

    @Override
    public CarViewHolder getViewHolder(View contentView) {
        return new CarViewHolder(contentView);

    }

    @Override
    public void initData() {
        initBrightness();
        initSupportingSystem();
        initCarLight();
        initLockDevice();
        initAppUpdate();
        initDownloadCenter();

    }

    /**
     * 对下载中心进行初始化
     */
    private void initDownloadCenter() {
        viewHolder.downloadCenterHolder.setListeners(this,R.id.item_download_center);
    }

    /**
     * 对软件更新进行初始化
     */
    private void initAppUpdate() {
        viewHolder.appUpdateHolder.setListeners(this,R.id.item_app_update);
    }

    /**
     * 对锁定装置设置进行初始化
     */
    private void initLockDevice() {
        viewHolder.lockDeviceHolder.setListeners(this,R.id.item_lock_device);
    }

    /**
     * 对车灯进行初始化
     */
    private void initCarLight() {
        viewHolder.carLightHolder.setListeners(this,R.id.item_car_light);
    }

    /**
     * 对驻车辅助系统急性初始化
     */
    private void initSupportingSystem() {
        viewHolder.supportingSystemHolder.setListeners(this,R.id.supporting_system_arrow,R.id.supporting_system_header);
        viewHolder.supportingSystemHolder.auto_open_camera_switch.setOnCheckedChangeListener(this);
        //viewHolder.supportingSystemHolder.auto_open_camera_switch.setOnCheckedChangeListener(this);
    }

    /**
     * 对显示屏进行初始化
     */
    private void initBrightness() {
        viewHolder.brightnessHolder.setListeners(this,R.id.brightness_arrow,R.id.brightness_add,R.id.brightness_subtract,R.id.display_brightness_header);
        BrightnessManager.getInstance(getContext()).setCurrentBright(getContext(), BrightnessLevel.LEVEL_FULL);
        ((ClipDrawable)viewHolder.brightnessHolder.brightness_progress.getDrawable())
                .setLevel(BrightnessManager.getInstance(getContext()).getCurrentBright(getContext()));

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.brightness_arrow:
            case R.id.display_brightness_header:
                toggleContent(viewHolder.brightnessHolder.display_brightness_content,viewHolder.brightnessHolder.brightness_arrow);
                break;
            case R.id.brightness_subtract:
                BrightnessManager.getInstance(getContext()).deCreaseBrightnessLevel();
                ((ClipDrawable)viewHolder.brightnessHolder.brightness_progress.getDrawable())
                        .setLevel(BrightnessManager.getInstance(getContext()).getCurrentBright(getContext()));
                break;
            case R.id.brightness_add:
                BrightnessManager.getInstance(getContext()).inCreaseBrightnessLevel();
                ((ClipDrawable)viewHolder.brightnessHolder.brightness_progress.getDrawable())
                        .setLevel(BrightnessManager.getInstance(getContext()).getCurrentBright(getContext()));
                break;
            case R.id.supporting_system_arrow:
            case R.id.supporting_system_header:
                toggleContent(viewHolder.supportingSystemHolder.supporting_system_content,viewHolder.supportingSystemHolder.supporting_system_arrow);
                break;

        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.auto_open_camera_switch:

                break;
            case R.id.open_allcast_display_switch:

                break;
        }
        Logger.i(buttonView+":checked = "+isChecked);
    }
}
