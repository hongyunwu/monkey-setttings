package com.autoio.settings.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.autoio.core_sdk.base.BaseHolder;
import com.autoio.settings.R;

import butterknife.BindView;

/**
 * Created by wuhongyun on 17-8-8.
 */

public class SettingsViewHolder extends BaseHolder {

    @BindView(R.id.left_title)
    public RecyclerView leftTitle;
    @BindView(R.id.right_content)
    public FrameLayout rightContent;
    /**
     * 此处使用butterKnife进行了view绑定操作
     *
     * @param itemView
     */
    public SettingsViewHolder(View itemView) {
        super(itemView);
    }
}
