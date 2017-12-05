package com.pccb.newapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pccb.newapp.firstwelcome.FirstShowViewPagerActivity;
import com.pccb.newapp.util.CommonUtils;
import com.pccb.newapp.util.PreferencesUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // 做逻辑处理 判断应用是否第一次安装(当前版是否第一次运行)
        if (PreferencesUtils.isFirstLaunch(StartActivity.this, CommonUtils.getVersionCode(this))) {
            PreferencesUtils.setFirstLaunch(StartActivity.this, CommonUtils.getVersionCode(this));

            Intent intent = new Intent(this, FirstShowViewPagerActivity.class);
            startActivity(intent);
            finish();
            return;
        }


//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                //execute the task
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent intent = new Intent(StartActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                });
//            }
//        }, 2000);

//        new Handler().postDelayed(() -> {
//            //execute the task
//            runOnUiThread(() -> {
//                Intent intent = new Intent(StartActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            });
//        }, 1000);

        Observable.timer(2, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Long aLong) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        Observable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
//                .map(l->{
//                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                    return null;
//        }).subscribe();

    }
}
