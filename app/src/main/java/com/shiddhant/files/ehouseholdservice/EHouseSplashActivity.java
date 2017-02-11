package com.shiddhant.files.ehouseholdservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

public class EHouseSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ehouse_splash);

        final TextView textView = (TextView)findViewById(R.id.tv_splash);
        final Animation ani = AnimationUtils.loadAnimation(getBaseContext(),R.anim.animation);
        textView.startAnimation(ani);
        ani.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                startActivity(new Intent(getBaseContext(),TabActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });




    }
}
