package com.autoio.settings.ui.fragment;

import android.view.View;

import com.autoio.core_sdk.base.BaseFragment;
import com.autoio.core_sdk.utils.TimeUtils;
import com.autoio.settings.R;
import com.autoio.settings.holder.CalendarViewHolder;

/**
 * Created by wuhongyun on 17-8-8.
 */

public class CalendarFragment extends BaseFragment<CalendarViewHolder> {


    @Override
    public int getLayoutID() {
        return R.layout.fragment_calendar;
    }

    @Override
    public CalendarViewHolder getViewHolder(View contentView) {
        return new CalendarViewHolder(contentView);
    }

    @Override
    public void initData() {
        viewHolder.timeZoneHolder.time_zone_desc.setText(TimeUtils.getTimeZoneText());
    }

    private static CalendarFragment calendarFragment;

    public static CalendarFragment newInstance() {
        if (calendarFragment ==null){
            synchronized (CalendarFragment.class){
                if (calendarFragment ==null){
                    calendarFragment = new CalendarFragment();
                }
            }
        }
        return calendarFragment;

    }
}
