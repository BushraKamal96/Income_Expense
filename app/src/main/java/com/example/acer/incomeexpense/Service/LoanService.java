package com.example.acer.incomeexpense.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.acer.incomeexpense.BroadcastService;
import com.example.acer.incomeexpense.DailyRecord;
import com.example.acer.incomeexpense.Database.DatabaseHelper;
import com.example.acer.incomeexpense.DueDate;
import com.example.acer.incomeexpense.R;
import com.example.acer.incomeexpense.models.Loan_model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class LoanService extends Service {

    Handler repeatableHandler;
    Runnable reRunner = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    DatabaseHelper dbHelper;
    ArrayList<Loan_model> arrayList = new ArrayList<>();
    String currentDateToBeMatched = "";

    String toBeMatchedTime = "12:00:00";
    String currentTimeToBeMatched = "";

    SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");


    @Override
    public void onCreate() {
        super.onCreate();

        dbHelper = new DatabaseHelper(this);

        repeatableHandler = new Handler();
        ReturnDateNorification();

    }

    private void ReturnDateNorification() {

        repeatableHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                reRunner = this;


                Date currentDate = Calendar.getInstance().getTime();
                currentDateToBeMatched = dateFormat.format(currentDate);
                arrayList.addAll(dbHelper.getreturnDate(currentDateToBeMatched));

                Log.e("dateToBeMatched", String.valueOf(arrayList));
                Log.e("currentDate", currentDateToBeMatched );


                Date currentTime = Calendar.getInstance().getTime();
                currentTimeToBeMatched = timeFormatter.format(currentTime);
                if (currentTimeToBeMatched.equals(toBeMatchedTime)){
                    for (Loan_model loan : arrayList) {
                        newNotification(loan);
                    }
                }



                repeatableHandler.postDelayed(reRunner, 1000);

            }


        }, 0);

    }

    private void newNotification(Loan_model loan) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(this, DueDate.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        String CHANNEL_ID = "my_channel_02";// The id of the channel.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }



        //yahan
        int notificationId = 123;
        Intent intent = new Intent(LoanService.this, BroadcastService.class);
        intent.setAction("com.myaction.com");
        intent.putExtra("Loan", loan.getId());
        intent.putExtra("noti_id", notificationId);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 123, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.iconscoin)
                .setContentTitle("Return Loan")
                .setContentText(loan.getPersonName() + " has "+ loan.getLoan_type() + " " + loan.getLoan()+ "rs ")
                .setSound(alarmSound)
                .setAutoCancel(true)
                .addAction(R.drawable.iconsdelete, "loan remove", pendingIntent)
                .setContentIntent(contentIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000});


        notificationManager.notify(notificationId, builder.build());

//        dbHelper.removeLoan(loan.getId());
//        arrayList.remove(loan);

    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
