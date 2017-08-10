package com.autoio.settings.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autoio.core_sdk.base.BaseHolder;
import com.autoio.settings.R;

import butterknife.BindView;

/**
 * Created by wuhongyun on 17-8-8.
 */

public class LeftViewHolder extends BaseHolder {

    @BindView(R.id.left_recycler_item)
    public RelativeLayout left_recycler_item;
    @BindView(R.id.left_recycler_item_bg)
    public ImageView left_recycler_item_bg;

    @BindView(R.id.left_recycler_item_pic)
    public ImageView left_recycler_item_pic;

    @BindView(R.id.left_recycler_item_txt)
    public TextView left_recycler_item_txt;
    /**
     * 此处使用butterKnife进行了view绑定操作
     *
     * @param itemView
     */
    public LeftViewHolder(View itemView) {
        super(itemView);
    }

    public void setHeight(@NonNull float height) {
        ViewGroup.LayoutParams layoutParams = left_recycler_item_bg.getLayoutParams();
        layoutParams.height = (int) height;
        left_recycler_item_bg.setLayoutParams(layoutParams);
    }
}
