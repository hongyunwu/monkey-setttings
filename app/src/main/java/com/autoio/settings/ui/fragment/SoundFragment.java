package com.autoio.settings.ui.fragment;

import android.view.View;

import com.autoio.core_sdk.base.BaseFragment;
import com.autoio.settings.R;
import com.autoio.settings.holder.SoundViewHolder;

/**
 * Created by wuhongyun on 17-8-8.
 */

public class SoundFragment extends BaseFragment<SoundViewHolder> {


    @Override
    public int getLayoutID() {
        return R.layout.fragment_sound;
    }

    @Override
    public SoundViewHolder getViewHolder(View contentView) {
        return new SoundViewHolder(contentView);
    }

    @Override
    public void initData() {

    }

    private static SoundFragment soundFragment;

    public static SoundFragment newInstance() {
        if (soundFragment ==null){
            synchronized (SoundFragment.class){
                if (soundFragment ==null){
                    soundFragment = new SoundFragment();
                }
            }
        }
        return soundFragment;

    }
}
