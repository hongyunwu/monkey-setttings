package com.autoio.settings.ui.fragment;

import android.view.View;

import com.autoio.core_sdk.base.BaseFragment;
import com.autoio.settings.R;
import com.autoio.settings.holder.MediaViewHolder;

/**
 * Created by wuhongyun on 17-8-8.
 */

public class MediaFragment extends BaseFragment<MediaViewHolder> {


    @Override
    public int getLayoutID() {
        return R.layout.fragment_media;
    }

    @Override
    public MediaViewHolder getViewHolder(View contentView) {
        return new MediaViewHolder(contentView);
    }

    @Override
    public void initData() {

    }

    private static MediaFragment mediaFragment;

    public static MediaFragment newInstance() {
        if (mediaFragment ==null){
            synchronized (MediaFragment.class){
                if (mediaFragment ==null){
                    mediaFragment = new MediaFragment();
                }
            }
        }
        return mediaFragment;

    }
}
