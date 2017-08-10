package com.autoio.settings.ui.fragment;

import android.view.View;

import com.autoio.core_sdk.base.BaseFragment;
import com.autoio.settings.R;
import com.autoio.settings.holder.WiFiViewHolder;

/**
 * Created by wuhongyun on 17-8-8.
 */

public class WiFiFragment extends BaseFragment<WiFiViewHolder> {


    @Override
    public int getLayoutID() {
        return R.layout.fragment_wifi;
    }

    @Override
    public WiFiViewHolder getViewHolder(View contentView) {
        return new WiFiViewHolder(contentView);
    }

    @Override
    public void initData() {

    }

    private static WiFiFragment wiFiFragment;

    public static WiFiFragment newInstance() {
        if (wiFiFragment ==null){
            synchronized (WiFiFragment.class){
                if (wiFiFragment ==null){
                    wiFiFragment = new WiFiFragment();
                }
            }
        }
        return wiFiFragment;

    }
}
