package com.autoio.settings.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.ScanResult;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.autoio.settings.R;
import com.orhanobut.logger.Logger;


/**
 * Created by wuhongyun on 17-7-21.
 */

public class AutoioDialog extends Dialog {


    public AutoioDialog(@NonNull Context context) {
        super(context);
    }

    public AutoioDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    /**
     * 用于构建弹出框
     */
    public static class Builder{

        private Context context;

        /**
         * 确认按钮文字
         */
        private String positiveButtonText = "确定";
        /**
         * 取消按钮文字
         */
        private String negativeButtonText = "取消";

        public Builder setScanResult(ScanResult scanResult) {
            this.scanResult = scanResult;
            return this;
        }

        private ScanResult scanResult;
        /**
         * 弹窗布局
         */
        View contentView;

        public Builder(Context context){
            this.context = context;
            contentView = View.inflate(context, R.layout.autoio_dialog,null);
        }

        public Builder setPositiveText(String positiveText){
            this.positiveButtonText = positiveText;
            return this;
        }
        public Builder setPositiveText(@StringRes int positiveText){
            this.positiveButtonText = (String) context.getText(positiveText);
            return this;
        }

        public Builder setNegativeText(String negativeText){
            this.negativeButtonText = negativeText;
            return this;
        }
        public Builder setNegativeText(@StringRes int negativeText){
            this.negativeButtonText = (String) context.getText(negativeText);
            return this;
        }

        public Builder setContentView(View contentView){
            this.contentView = contentView;
            return this;
        }

        public Builder setContentView(@LayoutRes int contentView){
            this.contentView = View.inflate(context,contentView,null);
            return this;
        }


        public AutoioDialog create(){
            final AutoioDialog autoioDialog = new AutoioDialog(context,R.style.Dialog);
            Button cancelView = (Button) contentView.findViewById(R.id.cancel);
            final Button confirmView = (Button) contentView.findViewById(R.id.confirm);
            final EditText inputPwd = (EditText) contentView.findViewById(R.id.input_pwd);
            cancelView.setText(negativeButtonText);
            confirmView.setText(positiveButtonText);
            if (cancelView!=null){
                cancelView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onWiFiPwdModifyListener!=null){
                            onWiFiPwdModifyListener.onCancelClick(autoioDialog, scanResult);
                        }
                    }
                });
            }
            if (confirmView!=null){
                confirmView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (onWiFiPwdModifyListener!=null){
                            String pwd = inputPwd.getText().toString();

                            if (TextUtils.isEmpty(pwd)||pwd.length()<8){
                                Logger.i("密码长度不够...");
                                return;
                            }
                            onWiFiPwdModifyListener.onConfirmClick(autoioDialog, pwd,scanResult);
                        }
                    }
                });
            }
            inputPwd.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (TextUtils.isEmpty(s)||s.length()<8){
                        confirmView.setEnabled(false);
                        Logger.i("密码长度不够...");
                    }else{
                        confirmView.setEnabled(true);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            autoioDialog.setContentView(contentView);
            Window window = autoioDialog.getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = context.getResources().getDimensionPixelSize(R.dimen.dialog_width);
            attributes.height = context.getResources().getDimensionPixelSize(R.dimen.dialog_height);
            window.setAttributes(attributes);
            return autoioDialog;
        }

        public interface OnWiFiPwdModifyListener{
            void onCancelClick(DialogInterface dialogInterface,ScanResult scanResult);
            void onConfirmClick(DialogInterface dialogInterface,String inputPwd,ScanResult scanResult);
        }

        public Builder setOnWiFiPwdModifyListener(OnWiFiPwdModifyListener onWiFiPwdModifyListener) {
            this.onWiFiPwdModifyListener = onWiFiPwdModifyListener;
            return this;
        }

        private OnWiFiPwdModifyListener onWiFiPwdModifyListener;
    }




}
