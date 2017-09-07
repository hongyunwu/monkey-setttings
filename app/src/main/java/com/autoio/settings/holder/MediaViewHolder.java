package com.autoio.settings.holder;

import android.view.View;
import android.widget.TextView;

import com.autoio.core_sdk.base.BaseHolder;
import com.autoio.settings.R;

import butterknife.BindView;

/**
 * Created by wuhongyun on 17-8-8.
 */

public class MediaViewHolder extends BaseHolder {

    @BindView(R.id.media_storage_size)
    public TextView media_storage_size;
    /**
     * 此处使用butterKnife进行了view绑定操作
     *
     * @param itemView
     */
    public MediaViewHolder(View itemView) {
        super(itemView);

    }

}
