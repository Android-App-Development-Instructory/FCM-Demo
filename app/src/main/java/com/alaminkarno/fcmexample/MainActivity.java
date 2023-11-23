package com.alaminkarno.fcmexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alaminkarno.fcmexample.utils.AppConstant;

public class MainActivity extends AppCompatActivity {

    private Button notificationBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        notificationBTN = findViewById(R.id.sendNotificationBTN);

        createNotificationChannel();


        notificationBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNotification();
            }
        });


    }

    void showNotification(){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,AppConstant.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Notification")
                .setContentText("This is our test notification")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1,builder.build());

    }

    void createNotificationChannel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel = new NotificationChannel(AppConstant.CHANNEL_ID,AppConstant.CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(AppConstant.CHANNEL_DESCRIPTION);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }

    }
}