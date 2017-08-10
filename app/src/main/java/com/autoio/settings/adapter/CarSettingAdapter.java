package com.autoio.settings.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.autoio.settings.holder.CarSettingViewHolder;

/**
 * Created by wuhongyun on 17-8-9.
 */

public class CarSettingAdapter extends RecyclerView.Adapter<CarSettingViewHolder> {

    private Context context;

    public CarSettingAdapter(Context context) {

        this.context = context;
    }

    @Override
    public CarSettingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(CarSettingViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
