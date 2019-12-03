package com.circleprogress;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.circleprogress.widget.CircleProgressBar;
import com.circleprogress.widget.NumberProgressBar;
import com.circleprogress.widget.PercentCircleView;

public class MainActivity extends Activity {

    private static final int MSG_CIRCLE = 0x1001;
    private Button mBtn;
    private CircleProgressBar mProgressBar;
    private NumberProgressBar mNumberProgressBar;
    private PercentCircleView mPercentCircleView;
    private int mProgress;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_CIRCLE) {
                mProgress++;
                if (mProgress <= 100) {
                    mProgressBar.setProgress(mProgress);
                    mNumberProgressBar.setProgress(mProgress);
                    mPercentCircleView.setProgress(mProgress);
                    mHandler.sendEmptyMessageDelayed(MSG_CIRCLE, 100);
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mProgressBar = (CircleProgressBar) findViewById(R.id.progress_bar);
        mNumberProgressBar = (NumberProgressBar) findViewById(R.id.progress_number);
        mPercentCircleView = (PercentCircleView) findViewById(R.id.circle_percent);
        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress = 0;
                mProgressBar.setProgress(mProgress);
                mNumberProgressBar.setProgress(mProgress);
                mHandler.removeMessages(MSG_CIRCLE);
                mHandler.sendEmptyMessage(MSG_CIRCLE);
            }
        });
    }

}
