package com.example.imgurapiapp.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.location.LocationManagerCompat;

import com.example.imgurapiapp.StringsConstant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class HelperUtility {

    public static Boolean InternetCheck(Context _context) {
        if (_context != null) {
            ConnectivityManager cn = (ConnectivityManager) _context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cn != null) {
                NetworkInfo nf = cn.getActiveNetworkInfo();
                return nf != null && nf.isConnected();
            }
        }
        return false;
    }

    public static String getStringByLocale(Context context, int id) {
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(getCurrentLocale(context));
        return context.createConfigurationContext(configuration).getResources().getString(id);
    }

    public static Locale getCurrentLocale(Context context) {
        switch (LocaleManager.getLanguagePref(context)) {
            case LocaleManager.ENGLISH: {
                return new Locale(LocaleManager.getLanguagePref(context), "");
            }
            default: {
                return new Locale(LocaleManager.ENGLISH, "");
            }
        }
    }

    public static void disableClicks(View view) {
        view.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);

            }
        }, 1000);
    }

    public static String getDate(Long timestamp) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(timestamp * 1000L);
        String date = DateFormat.format("DD/MM/YY", calendar).toString();
        return date;
    }

    public static String getTime(Long timestamp) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(timestamp * 1000L);
        String date = DateFormat.format("hh:mm a", calendar).toString();
        return date;
    }

    public static String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat(StringsConstant.Companion.getCUSTOM_DATE_FORMAT());
        return format.format(date);
    }


    public static String getDateDiff(Date date1, Date date2) {
        long diff = date1.getTime() - date2.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        if (days > 0) {
            if (days > 6 && days < 32) {
                int week = Integer.parseInt(days + "") / 7;
                week = Math.round(week);
                return week + " w ago";
            } else if (days > 31 && days < 366) {
                int month = Integer.parseInt(days + "") / 30;
                month = Math.round(month);
                if (month == 1) {
                    return month + " month ago";
                } else {
                    return month + " months ago";
                }
            } else if (days > 365) {
                int years = Integer.parseInt(days + "") / 365;
                years = Math.round(years);
                if (years == 1) {
                    return years + " yea ago";
                } else {
                    return years + " years ago";
                }

            } else
                return days + " days ago";
        } else if (hours > 0) {
            return hours + " hours ago";
        } else if (minutes > 0) {
            return minutes + " mins ago";
        } else {
            return seconds + " secs ago";
        }
    }

    public static String getUsernameLastUpdated(Date date1, Date date2) {
        long diff = date1.getTime() - date2.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (days <= 1) {
            return " Today";
        } else {
            return days + " days ago";
        }



    }


    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    private static void showAlert(Context ctx, String msg,String title, String positive, String negative, final OnOkClicked onOkClicked) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(ctx);
        builder1.setMessage(msg);
        builder1.setCancelable(true);
        builder1.setIcon(android.R.drawable.ic_dialog_alert);
        builder1.setTitle(title);
        builder1.setPositiveButton(
                positive,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onOkClicked.onOkClicked();
                    }
                });

        builder1.setNegativeButton(
                negative,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void disableClick(final View view){
        view.setEnabled(false);
        view.setAlpha(0.5f);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);
                view.setAlpha(1f);
            }
        },1000);
    }

    public interface OnOkClicked{
        void onOkClicked();
    }

}
