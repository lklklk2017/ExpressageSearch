package com.cdxxgc.expressagesearchdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cdxxgc.expressagesearchdemo.HomePage.HomeView;
import com.cdxxgc.expressagesearchdemo.widget.CustomCountDown;

public class WelcomeActivty extends AppCompatActivity implements CustomCountDown.CountDownHandler, View.OnClickListener {


    private static final int JUMP = 1;
    private CustomCountDown mCountDown;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case JUMP:
                    Intent intent = new Intent(WelcomeActivty.this, HomeView.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                    break;

                default:
                    super.handleMessage(msg);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_activty);

        mCountDown = ((CustomCountDown) findViewById(R.id.count));
        mCountDown.setmCountDownHandler(this);
        mCountDown.setOnClickListener(this);
    }

    @Override
    public void end() {
        Message message = handler.obtainMessage(JUMP);
        handler.sendMessage(message);
    }

    @Override
    public void onClick(View v) {
        mCountDown.onStop();
        Message message = handler.obtainMessage(JUMP);
        handler.sendMessage(message);
    }
}
