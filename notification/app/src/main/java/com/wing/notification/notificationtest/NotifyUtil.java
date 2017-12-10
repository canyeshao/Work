package com.wing.notification.notificationtest;



import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import java.util.List;

public class NotifyUtil {

    public static NotificationCompat.Builder create(Context context) {
        return new NotificationCompat.Builder(context);
    }

    public static NotificationCompat.Builder create(Context context, int smallIcon, CharSequence title, CharSequence content) {
        return new NotificationCompat.Builder(context).setSmallIcon(smallIcon).setContentTitle(title).setContentText(content);
    }

    public static NotificationCompat.Builder create(Context context, int largeIcon, int smallIcon, CharSequence title, CharSequence content) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), largeIcon);
        return new NotificationCompat.Builder(context).setShowWhen(true).setSmallIcon(smallIcon).setContentTitle(title).setContentText(content).setLargeIcon(bitmap);
    }

    public static NotificationCompat.Builder create(Context context, int largeIcon, int smallIcon, CharSequence title, CharSequence content, PendingIntent pi) {
        return create(context, largeIcon, smallIcon, title, content).setAutoCancel(true).setContentIntent(pi);
    }

    public static void notify(Context context, int notifyId, Notification notification) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notifyId, notification);
    }

    public static void cancel(Context context, int notifyId) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(notifyId);
    }

    public static void cancelAll(Context context) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
    }

    public static NotificationCompat.Action.Builder makeAction(int icon, String title, PendingIntent pi) {
        return new NotificationCompat.Action.Builder(icon, title, pi);
    }

    // 多行文本样式
    public static NotificationCompat.BigTextStyle makeBigText(CharSequence content) {
        return new NotificationCompat.BigTextStyle().bigText(content);
    }

    // 大图样式
    public static NotificationCompat.BigPictureStyle makeBigPicture(Bitmap bitmap) {
        return new NotificationCompat.BigPictureStyle().bigPicture(bitmap);
    }

    // 收件箱样式
    public static NotificationCompat.InboxStyle makeInbox(List<String> messages) {
        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        for (CharSequence msg : messages) {
            style.addLine(msg);
        }
        return style;
    }

}