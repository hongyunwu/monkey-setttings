package com.autoio.settings.holder;

import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.autoio.core_sdk.base.BaseHolder;
import com.autoio.settings.R;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import butterknife.BindView;

/**
 * Created by wuhongyun on 17-8-8.
 */

public class CarViewHolder extends BaseHolder {


    @BindView(R.id.right_recycler_view)
    LinearLayout right_recycler_view;



    /**
     * 此处使用butterKnife进行了view绑定操作
     *
     * @param itemView
     */
    public CarViewHolder(View itemView) {
        super(itemView);
        initBrightnessHolder();
        initSupportingSystemHolder();
        initCarLightHolder();
        initLockDeviceHolder();
        initAppUpdateHolder();
        initDownloadCenterHolder();
    }

    private void initDownloadCenterHolder() {
       downloadCenterHolder = new DownloadCenterHolder(item_download_center);
    }
    public DownloadCenterHolder downloadCenterHolder;
    @BindView(R.id.item_download_center)
    LinearLayout item_download_center;

    public class DownloadCenterHolder extends BaseHolder{

        /**
         * 此处使用butterKnife进行了view绑定操作
         *
         * @param itemView
         */
        public DownloadCenterHolder(View itemView) {
            super(itemView);
        }
    }
    private void initAppUpdateHolder() {
       appUpdateHolder = new AppUpdateHolder(item_app_update);
    }
    public AppUpdateHolder appUpdateHolder;
    @BindView(R.id.item_app_update)
    LinearLayout item_app_update;
    public class AppUpdateHolder extends BaseHolder{

        /**
         * 此处使用butterKnife进行了view绑定操作
         *
         * @param itemView
         */
        public AppUpdateHolder(View itemView) {
            super(itemView);
        }
    }
    private void initLockDeviceHolder() {
        lockDeviceHolder = new LockDeviceHolder(item_lock_device);
    }
    public LockDeviceHolder lockDeviceHolder;
    @BindView(R.id.item_lock_device)
    LinearLayout item_lock_device;
    public class LockDeviceHolder extends BaseHolder{


        /**
         * 此处使用butterKnife进行了view绑定操作
         *
         * @param itemView
         */
        public LockDeviceHolder(View itemView) {
            super(itemView);
        }
    }


    private void initBrightnessHolder() {
        brightnessHolder = new BrightnessHolder(item_driver_display);

    }
    public BrightnessHolder brightnessHolder;
    @BindView(R.id.item_driver_display)
    LinearLayout item_driver_display;
    public class BrightnessHolder extends BaseHolder{

        @BindView(R.id.brightness_progress)
        public ImageView brightness_progress;
        @BindView(R.id.brightness_arrow)
        public ImageButton brightness_arrow;
        @BindView(R.id.display_brightness_content)
        public ExpandableRelativeLayout display_brightness_content;
        @BindView(R.id.brightness_subtract)
        public ImageButton brightness_subtract;
        @BindView(R.id.brightness_add)
        public ImageButton brightness_add;
        /**
         * 此处使用butterKnife进行了view绑定操作
         *
         * @param itemView
         */
        public BrightnessHolder(View itemView) {
            super(itemView);
        }
    }


    private void initSupportingSystemHolder() {
        supportingSystemHolder = new SupportingSystemHolder(item_supporting_system);

    }

    public SupportingSystemHolder supportingSystemHolder;
    @BindView(R.id.item_supporting_system)
    LinearLayout item_supporting_system;
    public class SupportingSystemHolder extends BaseHolder{

        @BindView(R.id.supporting_system_arrow)
        public ImageButton supporting_system_arrow;
        @BindView(R.id.auto_open_camera_switch)
        public SwitchCompat auto_open_camera_switch;
        @BindView(R.id.open_allcast_display_switch)
        public SwitchCompat open_allcast_display_switch;
        @BindView(R.id.supporting_system_content)
        public ExpandableRelativeLayout supporting_system_content;
        /**
         * 此处使用butterKnife进行了view绑定操作
         *
         * @param itemView
         */
        public SupportingSystemHolder(View itemView) {
            super(itemView);
        }
    }


    private void initCarLightHolder(){
        carLightHolder = new CarLightHolder(item_car_light);
    }
    public CarLightHolder carLightHolder;
    @BindView(R.id.item_car_light)
    LinearLayout item_car_light;
    public class CarLightHolder extends BaseHolder{

        /**
         * 此处使用butterKnife进行了view绑定操作
         *
         * @param itemView
         */
        public CarLightHolder(View itemView) {
            super(itemView);
        }
    }
}
