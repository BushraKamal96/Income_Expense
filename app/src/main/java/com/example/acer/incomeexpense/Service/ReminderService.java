package com.example.acer.incomeexpense.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.acer.incomeexpense.DailyRecord;
import com.example.acer.incomeexpense.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReminderService extends Service {

    String toBeMatchedTime = "23:45:00";
    String currentTimeToBeMatched = "";

    SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");

    Handler repeatableHandler;
    Runnable reRunner = null;
    Context context;




    @Override
    public void onCreate() {
        super.onCreate();


        Log.e("Successfull", "Matched");

        repeatableHandler = new Handler();

        tracktimetoshownotification();
    }

    private void tracktimetoshownotification() {

        Log.e("Successfully", "Matched");


        repeatableHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                reRunner = this;

                Date currtentTime = Calendar.getInstance().getTime();
                currentTimeToBeMatched = timeFormatter.format(currtentTime);
                Log.e("CurrentTimeToBeMatched", currentTimeToBeMatched);

                if (currentTimeToBeMatched.equals(toBeMatchedTime)) {

                    Log.e("Success", "Matched");
                    showNotification();
                }
                repeatableHandler.postDelayed(reRunner, 1000);

            }
        }, 0);


    }

    private void showNotification() {

        NotificationManager notificationManager = (NotificationManager) getSystemService(
                Service.NOTIFICATION_SERVICE);


        Intent notificationIntent = new Intent(this, DailyRecord.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);



        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        String CHANNEL_ID = "my_channel_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,"XYZ", NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.iconscoin)
                .setContentTitle("Today's Expense")
                .setContentText("See Your Today's Expense")
                .setSound(alarmSound)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000});

        notificationManager.notify(1, builder.build());


    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
