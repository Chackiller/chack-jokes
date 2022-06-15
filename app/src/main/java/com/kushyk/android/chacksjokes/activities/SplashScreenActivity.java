package com.kushyk.android.chacksjokes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.kushyk.android.chacksjokes.R;
import com.onesignal.OneSignal;

import static com.kushyk.android.chacksjokes.activities.CategoriesListActivity.getActivity;
import static com.kushyk.android.chacksjokes.activities.CategoriesListActivity.makeFullScreen;

public class SplashScreenActivity extends AppCompatActivity {
    private static final String ONESIGNAL_APP_ID = "3724275f-f323-4528-a6f9-ee32e616545f";
    private static final String SAVED_WAS_NOTIFICATION_CLICKED = "was_notification_clicked";
    public static SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeFullScreen(getActivity(this));
        setContentView(R.layout.activity_splash_screen);
        sPref = getPreferences(MODE_PRIVATE);

        new Handler().postDelayed(() -> {
            if (sPref.getBoolean(SAVED_WAS_NOTIFICATION_CLICKED, false)) {
                startActivity(new Intent(SplashScreenActivity.this, WebViewActivity.class));
            } else {
                startActivity(new Intent(SplashScreenActivity.this, CategoriesListActivity.class));
            }
            finish();
        },2000);

        setOneSignal();
    }

    private void setOneSignal() {
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
        OneSignal.setNotificationOpenedHandler(result -> {
            SharedPreferences.Editor ed = sPref.edit();
            ed.putBoolean(SAVED_WAS_NOTIFICATION_CLICKED, true);
            ed.apply();
            new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashScreenActivity.this, WebViewActivity.class));
                finish();
            },2000);
        });
    }
}