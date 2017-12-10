package com.wing.notification.notificationtest;


import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;


public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Spinner sp_priority;
    private Spinner sp_style;
    private Spinner sp_category;
    private Spinner sp_buttons;
    private Spinner sp_progress;
    private Spinner sp_time;
    private Spinner sp_alert;
    private Spinner sp_visibility;

    private Switch match_parent;
    private Switch switch_large_icon;
    private Switch switch_sub;
    private Switch switch_info;
    private Switch switch_ticker;
    private Switch switch_public;
    private Switch switch_auto_cancel;
    private Switch switch_ongoing;
    private Switch switch_only_alert_once;
    private Switch switch_insistent;
    private Switch switch_no_clear;
    private Switch switch_content_intent;
    private Switch switch_delete_intent;
    private Switch switch_full_screen_intent;


    private TextView tv_clear_all;
    private TextView tv_clear;
    private TextView tv_send;

    private static final int[] DEFAULTS = new int[]{
            0,
            Notification.DEFAULT_ALL,

            Notification.DEFAULT_LIGHTS,
            Notification.DEFAULT_SOUND,
            Notification.DEFAULT_VIBRATE,

            Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND,
            Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE,
            Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE
    };

    NotificationCompat.Builder mBuilder;
    int mNotifyId = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        initView();
        mBuilder = NotifyUtil.create(this, R.mipmap.ic_elder, "This is title", "This is content.");

        registerReceiver(new ActionReceiver(), new IntentFilter(ACTION));
    }

    private void initView(){
        sp_priority = (Spinner)findViewById(R.id.sp_priority);
        sp_style = (Spinner)findViewById(R.id.sp_style);
        sp_category = (Spinner)findViewById(R.id.sp_category);
        sp_buttons = (Spinner)findViewById(R.id.sp_buttons);
        sp_progress = (Spinner)findViewById(R.id.sp_progress);
        sp_time = (Spinner)findViewById(R.id.sp_time);
        sp_alert = (Spinner)findViewById(R.id.sp_alert);
        sp_visibility = (Spinner)findViewById(R.id.sp_visibility);

        sp_priority.setSelection(2);
        sp_visibility.setSelection(1);

        sp_priority.setOnItemSelectedListener(this);
        sp_style.setOnItemSelectedListener(this);
        sp_category.setOnItemSelectedListener(this);
        sp_buttons.setOnItemSelectedListener(this);
        sp_progress.setOnItemSelectedListener(this);
        sp_time.setOnItemSelectedListener(this);
        sp_alert.setOnItemSelectedListener(this);
        sp_visibility.setOnItemSelectedListener(this);

        switch_large_icon = (Switch)findViewById(R.id.switch_large_icon);
        switch_sub = (Switch)findViewById(R.id.switch_sub);
        switch_info = (Switch)findViewById(R.id.switch_info);
        switch_ticker = (Switch)findViewById(R.id.switch_ticker);
        switch_public = (Switch)findViewById(R.id.switch_public);
        switch_auto_cancel = (Switch)findViewById(R.id.switch_auto_cancel);
        switch_ongoing = (Switch)findViewById(R.id.switch_ongoing);
        switch_only_alert_once = (Switch)findViewById(R.id.switch_only_alert_once);
        switch_insistent = (Switch)findViewById(R.id.switch_insistent);
        switch_no_clear = (Switch)findViewById(R.id.switch_no_clear);
        switch_content_intent = (Switch)findViewById(R.id.switch_content_intent);
        switch_delete_intent = (Switch)findViewById(R.id.switch_delete_intent);
        switch_full_screen_intent = (Switch)findViewById(R.id.switch_full_screen_intent);

        tv_clear_all = (TextView)findViewById(R.id.clear_all);
        tv_clear = (TextView)findViewById(R.id.clear);
        tv_send = (TextView)findViewById(R.id.send);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
        case R.id.sp_priority:
            mBuilder.setPriority(position - 2);
            break;
        case R.id.sp_buttons:
            mBuilder.mActions.clear();
            if (position == 1) {
                mBuilder.addAction(android.R.drawable.ic_media_play, "Play", pi(1001));
            } else if (position == 2) {
                mBuilder.addAction(android.R.drawable.ic_media_previous, "Prev", pi(1001));
                mBuilder.addAction(android.R.drawable.ic_media_next, "Next", pi(1002));
            } else if (position == 3) {
                mBuilder.addAction(android.R.drawable.ic_media_previous, "Prev", pi(1001));
                mBuilder.addAction(android.R.drawable.ic_media_play, "Play", pi(1002));
                mBuilder.addAction(android.R.drawable.ic_media_next, "Next", pi(1003));
            }
            break;
        case R.id.sp_progress:
            if (position == 0) {
                mBuilder.setProgress(0, 0, false);
            } else {
                mBuilder.setProgress(100, 50, position == 2);
            }
            break;
        case R.id.sp_style:
            if (position == 0) {
                mBuilder.setStyle(null);
            } else if (position == 1) {
                mBuilder.setStyle(NotifyUtil.makeBigText("big text content\nbig text content\nbig text content\nbig text content\nbig text " +
                                                                 "content\nbig text content\n")
                                            .setBigContentTitle("big text title")
                                            .setSummaryText("big text summary"));
            } else if (position == 2) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.photo480);
                mBuilder.setStyle(NotifyUtil.makeBigPicture(bitmap).setBigContentTitle("big text title").setSummaryText("big text summary"));
            } else if (position == 3) {
                List<String> messages = Arrays.asList(new String[]{"line 1", "line 2", "line 3"});
                mBuilder.setStyle(NotifyUtil.makeInbox(messages).setBigContentTitle("big text title").setSummaryText("big text summary"));
            }
            break;
        case R.id.sp_time:
            if (position == 0) {
                mBuilder.setShowWhen(false);
                mBuilder.setUsesChronometer(false);
            } else if (position == 1) {
                mBuilder.setShowWhen(true);
                mBuilder.setUsesChronometer(false);
            } else if (position == 2) {
                mBuilder.setShowWhen(true);
                mBuilder.setUsesChronometer(true);
            }
            break;
        case R.id.sp_alert:
            mBuilder.setDefaults(DEFAULTS[position]);
            break;
        case R.id.sp_visibility:
            mBuilder.setVisibility(position - 1);
            break;
        case R.id.sp_category:
            mBuilder.setCategory(parent.getSelectedItem().toString());
            break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.switch_only_alert_once:
            mBuilder.setOnlyAlertOnce(switch_only_alert_once.isChecked());
            break;
        case R.id.switch_auto_cancel:
            mBuilder.setAutoCancel(switch_auto_cancel.isChecked());
            break;
        case R.id.switch_ongoing:
            mBuilder.setOngoing(switch_ongoing.isChecked());
            break;
        case R.id.switch_info:
            mBuilder.setContentInfo("info");
            break;
        case R.id.switch_public:
            if (switch_public.isChecked()) {
                mBuilder.setPublicVersion(NotifyUtil.create(this, R.mipmap.ic_wazhi, "public title", "public content.").build());
            } else {
                mBuilder.setPublicVersion(null);
            }

            break;
        case R.id.switch_large_icon:
            if (switch_large_icon.isChecked()) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.large);
                mBuilder.setLargeIcon(bitmap);
            } else {
                mBuilder.setLargeIcon(null);
            }
            break;
        case R.id.switch_ticker:
            if (switch_ticker.isChecked()) {
                mBuilder.setTicker("this is ticker");
            } else {
                mBuilder.setTicker(null);
            }
            break;
        case R.id.switch_sub:
            if (switch_sub.isChecked()) {
                mBuilder.setSubText("his is sub");
            } else {
                mBuilder.setSubText(null);
            }
            break;
        case R.id.switch_content_intent:
            if (switch_content_intent.isChecked()) {
                mBuilder.setContentIntent(pi(2000));
            } else {
                mBuilder.setContentIntent(null);
                mBuilder.mNotification.contentIntent = null;
            }
            break;
        case R.id.switch_delete_intent:
            if (switch_delete_intent.isChecked()) {
                mBuilder.setDeleteIntent(pi(2001));
            } else {
                mBuilder.setDeleteIntent(null);
                mBuilder.mNotification.deleteIntent = null;
            }
            break;
        case R.id.switch_full_screen_intent:
            if (switch_full_screen_intent.isChecked()) {
                mBuilder.setFullScreenIntent(pi(2002), true);
            } else {
                mBuilder.setFullScreenIntent(null, false);
                mBuilder.mNotification.fullScreenIntent = null;
            }
            break;
        case R.id.clear_all:

            NotifyUtil.cancelAll(this);
            break;
        case R.id.clear:
            NotifyUtil.cancel(this, mNotifyId);
            break;
        case R.id.send:
            mNotifyId++;
            final Notification notification = mBuilder.build();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (notification.publicVersion != null && notification.visibility == Notification.VISIBILITY_PUBLIC) {
                    tv_send.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            NotifyUtil.notify(mBuilder.mContext, mNotifyId, notification);
                        }
                    }, 5000);
                    break;
                }
            }
            if (switch_insistent.isChecked()) {
                notification.flags |= Notification.FLAG_INSISTENT;
            }
            if (switch_no_clear.isChecked()) {
                notification.flags |= Notification.FLAG_NO_CLEAR;
            }
            NotifyUtil.notify(this, mNotifyId, notification);
            break;
        }
    }

    PendingIntent pi(int op) {
        Intent intent = new Intent(ACTION);
        intent.putExtra("op", op);
        return PendingIntent.getBroadcast(this, 0, intent, 0);
    }

    public static final String ACTION = "action.ezy.demo.notification";

    public static class ActionReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (!ACTION.equals(action)) {
                return;
            }
            int op = intent.getIntExtra("op", 0);
            Log.e("ezy", "result ==> " + op);
            Toast.makeText(context, "result ==> " + op, Toast.LENGTH_LONG).show();
        }
    }
}
