package com.ronny.rxjavaexample;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Ronny on 2020/11/11
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvLog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        tvLog = findViewById(R.id.tv_demo_info);
        tvLog.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    public void onClick(View view) {
        OnBtnClick();
    }

    public void displayLog(String... strings) {
        if (tvLog == null) return;

        String s = tvLog.getText().toString();

        StringBuilder sb = new StringBuilder();

        sb.append(s).append("\r\n");

        for (String string : strings) {
            sb.append(string);
            sb.append("\r\n");
        }
        tvLog.setText(sb.toString());
    }

    private String tmpLog = "";

    public void appendLogNoDisplay(String s) {
        tmpLog += s;
        tmpLog += "\r\n";
    }

    public void displayLog() {
        runOnUiThread(() -> tvLog.setText(tmpLog));
    }

    public abstract void OnBtnClick();
}
