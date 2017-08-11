package com.autoio.settings.holder;

import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autoio.core_sdk.base.BaseHolder;
import com.autoio.settings.R;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

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
        initTimeAutoHolder();
        initTimeHourHolder();
        initTimeYearHolder();
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

    private void initTimeAutoHolder() {
        timeAutoHolder = new TimeAutoHolder(item_time_auto);
    }
    public TimeAutoHolder timeAutoHolder;
    @BindView(R.id.item_time_auto)
    LinearLayout item_time_auto;
    public class TimeAutoHolder extends BaseHolder{
        @BindView(R.id.time_auto_switch)
        public SwitchCompat time_auto_switch;
        /**
         * 此处使用butterKnife进行了view绑定操作
         *
         * @param itemView
         */
        public TimeAutoHolder(View itemView) {
            super(itemView);
        }
    }


    private void initTimeHourHolder() {
        timeHourHolder = new TimeHourHolder(item_time_hour);
    }
    public TimeHourHolder timeHourHolder;
    @BindView(R.id.item_time_hour)
    LinearLayout item_time_hour;
    public class TimeHourHolder extends BaseHolder{
        @BindView(R.id.time_hour_header)
        public LinearLayout time_hour_header;
        @BindView(R.id.time_hour_arrow)
        public ImageButton time_hour_arrow;
        @BindView(R.id.time_hour_content)
        public ExpandableRelativeLayout time_hour_content;
        @BindView(R.id.time_hour)
        public EditText time_hour;
        @BindView(R.id.time_minute)
        public EditText time_minute;
        /**
         * 此处使用butterKnife进行了view绑定操作
         *
         * @param itemView
         */
        public TimeHourHolder(View itemView) {
            super(itemView);
        }
    }

    private void initTimeYearHolder() {
        timeYearHolder = new TimeYearHolder(item_time_year);
    }
    public TimeYearHolder timeYearHolder;
    @BindView(R.id.item_time_year)
    LinearLayout item_time_year;
    public class TimeYearHolder extends BaseHolder{
        @BindView(R.id.time_year_header)
        public LinearLayout time_year_header;

        @BindView(R.id.time_year_arrow)
        public ImageButton time_year_arrow;

        @BindView(R.id.time_year_content)
        public ExpandableRelativeLayout time_year_content;

        @BindView(R.id.time_year)
        public EditText time_year;
        @BindView(R.id.time_month)
        public EditText time_month;
        @BindView(R.id.time_day)
        public EditText time_day;
        /**
         * 此处使用butterKnife进行了view绑定操作
         *
         * @param itemView
         */
        public TimeYearHolder(View itemView) {
            super(itemView);
        }
    }
}
