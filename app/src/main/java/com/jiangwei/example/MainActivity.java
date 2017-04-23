package com.jiangwei.example;

import com.butterknife.luffy.butterknife.R;
import com.jiangwei.annotation.bindviewa.BindClick;
import com.jiangwei.annotation.bindviewa.BindId;
import com.jiangwei.annotation.bindviewa.BindView;
import com.jiangwei.api.BFProxy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

@BindView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @BindId(R.id.tv)
    public TextView tv;
    @BindId(R.id.tv1)
    public TextView tv1;
    @BindId(R.id.tv2)
    public TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BFProxy.injectActivity(this);
        BFProxy.injectActivityId(this);
        BFProxy.injectActivityClick(this);
        tv.setText("tv");
        tv1.setText("tv1");
        tv2.setText("tv2");
    }

    @BindClick({ R.id.tv, R.id.tv1, R.id.tv2 })
    public void send(View view) {
        switch (view.getId()) {
            case R.id.tv:
                Toast.makeText(MainActivity.this, "tv被点击了", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv1:
                Toast.makeText(MainActivity.this, "tv1被点击了", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv2:
                Toast.makeText(MainActivity.this, "tv2被点击了", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
