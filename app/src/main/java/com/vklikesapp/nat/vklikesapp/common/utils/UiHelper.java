package com.vklikesapp.nat.vklikesapp.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.vklikesapp.nat.vklikesapp.analitycs.Analitycs;


public class UiHelper {
    private static UiHelper ourInstance = new UiHelper();

    private Resources resources;
    private Context context;

    public static UiHelper getInstance() {
        return ourInstance;
    }



    public void setUpTextViewWithVisibility(TextView textView, String s) {
        textView.setText(s);

        if (s != null && s.length() != 0) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    public void setUpTextViewWithMessage(TextView textView, String s, String messageIfEmpty) {
        String s1;
        int color;
        Resources res = textView.getResources();

        if (s.length() != 0) {
            textView.setVisibility(View.VISIBLE);
            color = android.R.color.primary_text_light;

            s1 = s;

        } else {
            s1 = "Поделился";

//            color = R.color.colorIcon;
            color = android.R.color.primary_text_light;;
        }

        textView.setText(s1);
        textView.setTextColor(res.getColor(color));
    }

    public static void seeAlertDialogJoinVkGroup(final Activity activity, String title, String text, String positive, String negative) {
        Analitycs.onEventJoinGroup("see_dialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder
                .setTitle(title)
                .setMessage(text)
                .setCancelable(false)
                .setPositiveButton(positive,
                        (dialog, id) -> {
                            Analitycs.onEventJoinGroup("yes");
                            Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.bs.vkfriends"));
                            activity.startActivity(intent);
                        })
                .setNegativeButton(negative,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Analitycs.onEventJoinGroup("no");
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }


}
