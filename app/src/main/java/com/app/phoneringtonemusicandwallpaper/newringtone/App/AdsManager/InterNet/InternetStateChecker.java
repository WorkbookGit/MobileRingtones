package com.app.phoneringtonemusicandwallpaper.newringtone.App.AdsManager.InterNet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;


import com.app.phoneringtonemusicandwallpaper.newringtone.R;

import java.util.Objects;


public class InternetStateChecker implements ConnectivityReceiverListener {

    private Context context;
    private ConnectivityReceiver receiver;

    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    //For Alert Dialog

    private boolean isAlreadyVisible;

    private InternetStateChecker(Context context, String title, String message, int color, int textColor, int icon, boolean cancelable) {
        this.context = context;
        initReciever(context);

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        try {
            if (isConnected) {
                hideAlertDialog();
                isAlreadyVisible = false;
            } else {
                if (!isAlreadyVisible) {
                    showAlertDialog();
                    isAlreadyVisible = true;
                }
            }
        } catch (Exception e) {
            Log.e("Negtwork error :: ", Objects.requireNonNull(e.getMessage()));
        }

    }


    private void initReciever(Context context) {
        try {
            receiver = new ConnectivityReceiver();
            context.registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            receiver.setConnectionCallback(this);
        } catch (Exception e) {

        }

    }

    private void showAlertDialog() {
        try {

            dialogBuilder = new AlertDialog.Builder(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layoutView = inflater.inflate(R.layout.dialog_internet, null);
            dialogBuilder.setView(layoutView);
            alertDialog = dialogBuilder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        } catch (Exception e) {


        }
    }

    private void hideAlertDialog() {
        try {
            if (isAlreadyVisible) {
                alertDialog.dismiss();
            }
        } catch (Exception e) {


        }
    }


    public static class Builder {
        private Context context;
        private boolean cancelable;
        private String title = "";
        private String message = "";
        private int color;
        private int textColor;
        private int icon;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setDialogTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDialogMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setDialogBgColor(int color) {
            this.color = color;
            return this;
        }

        public Builder setDialogIcon(int icon) {
            this.icon = icon;
            return this;
        }

        public Builder setDialogTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public InternetStateChecker build() {
            InternetStateChecker checker = new InternetStateChecker(context, title, message, color, textColor, icon, cancelable);

            return checker;
        }
    }
}
