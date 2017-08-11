package com.autoio.settings.ui.fragment;

import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import com.autoio.core_sdk.base.BaseFragment;
import com.autoio.core_sdk.date.DateAdmin;
import com.autoio.core_sdk.date.DateHandleIntent;
import com.autoio.core_sdk.date.DateTextChangeListener;
import com.autoio.settings.R;
import com.autoio.settings.holder.CalendarViewHolder;
import com.autoio.settings.utils.AnimUtils;
import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.autoio.core_sdk.date.DateAdmin.getTimeZoneText;

/**
 * Created by wuhongyun on 17-8-8.
 */

public class CalendarFragment extends BaseFragment<CalendarViewHolder> implements CompoundButton.OnCheckedChangeListener,View.OnClickListener,DateHandleIntent,DateTextChangeListener.DateTextChangeHandler{


    private DateAdmin dateAdmin;

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
        dateAdmin = DateAdmin.getInstance(getContext(), this);
        initTimeZone();
        initTimeAuto();
        initTimeHour();
        initTimeYear();
        updateDateDisplay();


    }

    @Override
    public void onResume() {
        super.onResume();
        dateAdmin.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        dateAdmin.pause();
    }

    private void initTimeYear() {
        viewHolder.timeYearHolder.setListeners(this,R.id.time_year_arrow,R.id.time_year_header);
        viewHolder.timeYearHolder.time_year.addTextChangedListener(new DateTextChangeListener(this));
        viewHolder.timeYearHolder.time_month.addTextChangedListener(new DateTextChangeListener(this));
        viewHolder.timeYearHolder.time_day.addTextChangedListener(new DateTextChangeListener(this));
    }

    private void initTimeZone() {
        //viewHolder.timeZoneHolder.time_zone_desc.setText(DateAdmin.getTimeZoneText());
    }

    private void initTimeHour() {
        viewHolder.timeHourHolder.setListeners(this,R.id.time_hour_header,R.id.time_hour_arrow);
        viewHolder.timeHourHolder.time_hour.addTextChangedListener(new DateTextChangeListener(this));
        viewHolder.timeHourHolder.time_minute.addTextChangedListener(new DateTextChangeListener(this));
    }

    /**
     * 自动授时功能
     */
    private void initTimeAuto() {
        //viewHolder.timeAutoHolder.time_auto_switch.setChecked(DateAdmin.getAutoState(getContext(), Settings.Global.AUTO_TIME));
        viewHolder.timeAutoHolder.time_auto_switch.setOnCheckedChangeListener(this);
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.time_auto_switch:

                DateAdmin.setAutoState(getContext(),isChecked);

                if (isChecked){
                    if (viewHolder.timeYearHolder.time_year_content.isExpanded()){
                        AnimUtils.toggleContent(viewHolder.timeYearHolder.time_year_content,viewHolder.timeYearHolder.time_year_arrow);
                    }
                    if (viewHolder.timeHourHolder.time_hour_content.isExpanded()){
                        AnimUtils.toggleContent(viewHolder.timeHourHolder.time_hour_content,viewHolder.timeHourHolder.time_hour_arrow);
                    }

                }
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.time_hour_header:
            case R.id.time_hour_arrow:
                if (!viewHolder.timeAutoHolder.time_auto_switch.isChecked())
                    AnimUtils.toggleContent(viewHolder.timeHourHolder.time_hour_content,viewHolder.timeHourHolder.time_hour_arrow);
                break;
            case R.id.time_year_arrow:
            case R.id.time_year_header:
                if (!viewHolder.timeAutoHolder.time_auto_switch.isChecked())
                    AnimUtils.toggleContent(viewHolder.timeYearHolder.time_year_content,viewHolder.timeYearHolder.time_year_arrow);
                break;

        }
    }

    @Override
    public void handleIntent(Intent intent) {

        updateDateDisplay();

    }

    private void updateDateDisplay() {

        viewHolder.timeAutoHolder.time_auto_switch.setChecked(DateAdmin.getAutoState(getContext(), Settings.Global.AUTO_TIME));
        //时间和年月
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

        final Calendar now = Calendar.getInstance();
        viewHolder.timeZoneHolder.time_zone_desc.setText(getTimeZoneText(now.getTimeZone()));

        Logger.i("updateDateDisplay->"+now.get(Calendar.YEAR), 11, 31, 13, 0, 0);//年


        String formatTime = simpleDateFormat.format(now.getTime());
        Logger.i("updateDateDisplay->"+ formatTime);
        if (!TextUtils.isEmpty(formatTime)){
            String[] split = formatTime.split("-");
            try {
                viewHolder.timeYearHolder.time_year.setText(split[0]);
                viewHolder.timeYearHolder.time_month.setText(split[1]);
                viewHolder.timeYearHolder.time_day.setText(split[2]);
                viewHolder.timeHourHolder.time_hour.setText(split[3]);
                viewHolder.timeHourHolder.time_minute.setText(split[4]);

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void handleDateTextChange() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        try {
            DateAdmin.setDate(getContext()
                    ,Integer.parseInt(viewHolder.timeYearHolder.time_year.getText().toString())
                    ,Integer.parseInt(viewHolder.timeYearHolder.time_month.getText().toString())
                    ,Integer.parseInt(viewHolder.timeYearHolder.time_day.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            DateAdmin.setTime(getContext()
                    ,Integer.parseInt(viewHolder.timeHourHolder.time_hour.getText().toString())
                    ,Integer.parseInt(viewHolder.timeHourHolder.time_minute.getText().toString())
                    );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
