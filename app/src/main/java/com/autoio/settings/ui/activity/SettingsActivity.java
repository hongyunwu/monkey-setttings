package com.autoio.settings.ui.activity;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewTreeObserver;

import com.autoio.core_sdk.base.BaseActivity;
import com.autoio.core_sdk.base.BaseFragment;
import com.autoio.settings.R;
import com.autoio.settings.adapter.LeftAdapter;
import com.autoio.settings.enums.Position;
import com.autoio.settings.holder.SettingsViewHolder;
import com.autoio.settings.ui.fragment.BlueToothFragment;
import com.autoio.settings.ui.fragment.CalendarFragment;
import com.autoio.settings.ui.fragment.CarFragment;
import com.autoio.settings.ui.fragment.MediaFragment;
import com.autoio.settings.ui.fragment.SoundFragment;
import com.autoio.settings.ui.fragment.WiFiFragment;
import com.orhanobut.logger.Logger;

public class SettingsActivity extends BaseActivity<SettingsViewHolder> implements LeftAdapter.OnItemClickListener {

    private int[] leftPics=  new int[]{
            R.drawable.settings_car_selector,
            R.drawable.settings_wifi_selector,
            R.drawable.settings_bluetooth_selector,
            R.drawable.settings_media_selector,
            R.drawable.settings_sound_selector,
            R.drawable.settings_calendar_selector
    };
    private int[] leftTexts = new int[]{
            R.string.settings_car,
            R.string.settings_wifi,
            R.string.settings_bluetooth,
            R.string.settings_media,
            R.string.settings_sound,
            R.string.settings_calendar
    };

    @Override
    public int getLayoutID() {
        return R.layout.activity_settings;
    }

    @Override
    public SettingsViewHolder getViewHolder(View contentView) {
        return new SettingsViewHolder(contentView);
    }

    @Override
    public void initData() {
        //
        viewHolder.leftTitle.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                viewHolder.leftTitle.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //刷新adapter
                viewHolder.leftTitle.setLayoutManager(new LinearLayoutManager(SettingsActivity.this,LinearLayoutManager.VERTICAL,false){


                });
                Logger.i("height:"+viewHolder.leftTitle.getHeight());
                LeftAdapter leftAdapter = new LeftAdapter(SettingsActivity.this, leftPics, leftTexts, viewHolder.leftTitle.getHeight() * 1f / 6);
                leftAdapter.setOnItemClickListener(SettingsActivity.this);

                viewHolder.leftTitle.setAdapter(leftAdapter);
                //默认添加汽车设置
                addFragment(CarFragment.newInstance());
            }
        });


    }

    @Override
    public void onItemClick(View view, int position) {
        Logger.i("onItemClick:"+position);
        BaseFragment fragment = null;
        //开始变换fragment;
        switch (position){
            case Position.POSITION_CAR:
                fragment = CarFragment.newInstance();
                break;
            case Position.POSITION_WIFI:
                fragment = WiFiFragment.newInstance();
                break;
            case Position.POSITION_BLUETOOTH:
                fragment = BlueToothFragment.newInstance();
                break;
            case Position.POSITION_MEDIA:
                fragment = MediaFragment.newInstance();
                break;
            case Position.POSITION_SOUND:
                fragment = SoundFragment.newInstance();
                break;
            case Position.POSITION_CALENDAR:
                fragment = CalendarFragment.newInstance();
                break;
        }
        addFragment(fragment);

    }

    private BaseFragment contentFragment;
    /**
     * 添加设置fragment
     * @param fragment
     */
    public void addFragment(@NonNull BaseFragment fragment){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (contentFragment==null){
            transaction.add(R.id.right_content,fragment).commit();
        }else{
            transaction.replace(R.id.right_content,fragment).commit();
        }
        contentFragment = fragment;

    }
}
