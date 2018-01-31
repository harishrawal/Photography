package com.app.winklix.photography;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.app.winklix.photography.App.App_Api;
import com.app.winklix.photography.App.M_Shared_Pref;

public class Splash_Screen extends AppCompatActivity  {
    private LinearLayout ll_logo;
    private Animation animation;
    M_Shared_Pref m_shared_pref;
    private Context context;

    private static final String TAG = Splash_Screen.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        ll_logo = (LinearLayout) findViewById(R.id.ll_logo);
        context = Splash_Screen.this;
        m_shared_pref = new M_Shared_Pref(context);

        if (savedInstanceState == null) {
            flyIn();
        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                endSplash();
            }
        }, 3000);
    }

    private void flyIn() {
        animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        ll_logo.startAnimation(animation);

    }

    private void endSplash() {
        animation = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        ll_logo.startAnimation(animation);


        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {

                if (m_shared_pref.getPrefranceBooleanValue(App_Api.IsLoggedIn)) {
                    Intent intent = new Intent(getApplicationContext(), Category_Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), Category_Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                }

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationStart(Animation arg0) {
            }
        });

    }
}
