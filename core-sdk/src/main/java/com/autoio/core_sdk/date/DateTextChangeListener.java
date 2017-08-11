package com.autoio.core_sdk.date;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by wuhongyun on 17-8-11.
 */

public class DateTextChangeListener implements TextWatcher {

    public DateTextChangeListener(DateTextChangeHandler dateTextChangeHandler){
        this.dateTextChangeHandler = dateTextChangeHandler;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (dateTextChangeHandler!=null){
            dateTextChangeHandler.handleDateTextChange();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
    public interface DateTextChangeHandler{
        void handleDateTextChange();
    }
    public DateTextChangeHandler dateTextChangeHandler;
}
