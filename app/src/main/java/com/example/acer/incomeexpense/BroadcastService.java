package com.example.acer.incomeexpense;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.acer.incomeexpense.Database.DatabaseHelper;

public class BroadcastService extends BroadcastReceiver {

    String loanid;
    DatabaseHelper db;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("ACTION ", intent.getAction());
        int notificationid = intent.getIntExtra("noti_id", 0);
        Log.e("NOTIID", notificationid+"");
        if (notificationid != 0) {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancel(notificationid);

        } else
            Log.e("ERROR ", "NO ID");
        Toast.makeText(context, "working", Toast.LENGTH_SHORT).show();

      loanid =   intent.getStringExtra("Loan");
        db = new DatabaseHelper(context);
        db.removeLoan(loanid);


    }
}
