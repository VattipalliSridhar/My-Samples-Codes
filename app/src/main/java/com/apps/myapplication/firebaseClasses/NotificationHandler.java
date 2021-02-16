package com.apps.myapplication.firebaseClasses;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.apps.myapplication.CalenderActivity;

import java.util.List;
import java.util.Objects;

public class NotificationHandler {

    private Context mContext;

    public static int NOTIFICATION_ID = 1;
    public static int REQUEST_ID = 1;
    private Bitmap icon;

    public NotificationHandler(Context mContext) {
        this.mContext = mContext;
    }

    //This method will check whether the app is in background or not
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    public void showNotificationMessage(String messages) {

        Intent intent;



            intent = new Intent(mContext, CalenderActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            // intent.putExtra("title",title);
            intent.putExtra("message",messages);

        if (REQUEST_ID > 1073741824) {
            REQUEST_ID = 0;
        }

        int r=REQUEST_ID;
        REQUEST_ID = r + 1;

        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, r /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = "101000101111";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        if (icon == null)
            icon = getBitmapFromDrawable(mContext.getApplicationInfo().loadIcon(mContext.getPackageManager()));

        StringBuilder title_append=new StringBuilder();
        title_append.append("My App");
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(mContext, channelId)
                        // .setSmallIcon(R.drawable.ic_notifications_none_black_24dp)
                        // .setSmallIcon(R.drawable.ic_notifications_none_black_24dp).setTicker("").setWhen(0)
                        .setContentTitle(title_append)
                        .setContentText(messages)
                        // .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_notifications_none_black_24dp))
                        .setSmallIcon(mContext.getApplicationInfo().icon)
                        .setLargeIcon(icon)
                        .setAutoCancel(true)
                        //.setDefaults(Notification.DEFAULT_SOUND)
                        .setSound(defaultSoundUri)
                        .setWhen(System.currentTimeMillis())
                        .setPriority(Notification.PRIORITY_MAX)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (NOTIFICATION_ID > 1073741824) {
            NOTIFICATION_ID = 0;
        }
        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "edurate_messages", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("edurate notifications");
            channel.enableLights(true);
            channel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            channel.enableVibration(true);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
        }
        int i = NOTIFICATION_ID;
        NOTIFICATION_ID = i + 1;


        notificationManager.notify(i /* ID of notification */, notificationBuilder.build());

}

    @NonNull
    static private Bitmap getBitmapFromDrawable(@NonNull Drawable drawable) {
        final Bitmap bmp = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bmp);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bmp;
    }

}
