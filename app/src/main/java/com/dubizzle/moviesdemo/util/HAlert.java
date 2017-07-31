package com.dubizzle.moviesdemo.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Developed by Hasham.Tahir on 1/16/2017.
 */

public class HAlert {

    public static void showAlertDialog(Activity context, String title, String message) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        alert.show();
    }

    public static void showAlertDialog(Activity context, String title, String message, DialogInterface.OnClickListener listener) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton("OK", listener);
        alert.setCancelable(false);
        alert.show();
    }

    public static void showSnackBar(View view, String msg) {
//        findViewById(android.R.id.content) item_spinner_hint
        try {
            Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
            View snackBarView = snackbar.getView();
            TextView tv = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(Color.WHITE);
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showToast(Context context, String msg) {

        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String msg, int length) {

        Toast.makeText(context, msg, length).show();
    }
}
