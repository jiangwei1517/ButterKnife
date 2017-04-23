package com.jiangwei.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.butterknife.luffy.butterknife.R;

/**
 * author:  jiangwei18 on 17/4/19 22:36
 * email:  jiangwei18@baidu.com
 * Hi:   jwill金牛
 */

public class FragmentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);
        MainFragment mf = new MainFragment();
        getFragmentManager().beginTransaction().replace(R.id.fl, mf).commit();
    }
}
