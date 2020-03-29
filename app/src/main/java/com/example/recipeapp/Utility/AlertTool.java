package com.example.recipeapp.Utility;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.example.recipeapp.R;

public class AlertTool {
    private alertListener mListener;

    public AlertTool(alertListener mListener){
        this.mListener = mListener;
    }


    public void alertSingleButton(Context context, final String dialogTag, int messageText , int positiveText){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(context.getResources().getString(messageText));
        builder1.setCancelable(false);

        builder1.setPositiveButton(context.getResources().getString(positiveText), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                mListener.onPositiveDialogClick(dialogTag);
                dialog.cancel();
            }
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();
        alert11.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
    }

    public void alertDuoButton(Context context, final String dialogTag, int messageText , int positiveText, int negativeText){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(context.getResources().getString(messageText));
        builder1.setCancelable(false);

        builder1.setPositiveButton(context.getResources().getString(positiveText), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                mListener.onPositiveDialogClick(dialogTag);
                dialog.cancel();
            }
        });

        builder1.setNegativeButton(context.getResources().getString(negativeText), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                mListener.onNegativeDialogClick(dialogTag);
                dialog.cancel();
            }
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();
        alert11.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
        alert11.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
    }

    public interface alertListener{
        void onPositiveDialogClick(String dialogTag);
        void onNegativeDialogClick(String dialogTag);
    }
}
