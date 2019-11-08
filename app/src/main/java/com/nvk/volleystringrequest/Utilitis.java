package com.nvk.volleystringrequest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Process;

public class Utilitis {
    public static final String BASE ="";

    public static Boolean checkNetWork(Context context, Bundle data){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null){
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static AlertDialog ShowDialogNotification(String message, final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("ERROR").setMessage(message).setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity mainActivity  = (MainActivity) context;
                mainActivity.moveTaskToBack(true);
                android.os.Process.killProcess(Process.myPid());
                dialog.dismiss();
            }
        });
        return builder.create();
    }

}
