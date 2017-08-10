package com.autoio.settings.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autoio.core_sdk.base.BaseHolder;
import com.autoio.settings.R;

import butterknife.BindView;

/**
 * Created by wuhongyun on 17-8-8.
 */

public class CalendarViewHolder extends BaseHolder {

    /**
     * 此处使用butterKnife进行了view绑定操作
     *
     * @param itemView
     */
    public CalendarViewHolder(View itemView) {
        super(itemView);
        initTimeZoneHolder();
    }

    private void initTimeZoneHolder() {
        timeZoneHolder = new TimeZoneHolder(item_time_zone);
    }
    public TimeZoneHolder timeZoneHolder;
    @BindView(R.id.item_time_zone)
    LinearLayout item_time_zone;
    public class TimeZoneHolder extends BaseHolder{
        @BindView(R.id.time_zone_desc)
        public TextView time_zone_desc;
        /**
         * 此处使用butterKnife进行了view绑定操作
         *
         * @param itemView
         */
        public TimeZoneHolder(View itemView) {
            super(itemView);
        }
    }
}
