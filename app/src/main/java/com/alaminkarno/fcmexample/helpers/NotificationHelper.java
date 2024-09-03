package com.alaminkarno.fcmexample.helpers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.alaminkarno.fcmexample.MainActivity;
import com.alaminkarno.fcmexample.R;
import com.alaminkarno.fcmexample.utils.AppConstant;

public class NotificationHelper {

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static void displayNotification(Context context, String title, String body) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, AppConstant.CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(body))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);

        checkNotificationPermission(context);

        managerCompat.notify(1, builder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private static void checkNotificationPermission(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[] {android.Manifest.permission.POST_NOTIFICATIONS},1);
        }
    }
}
