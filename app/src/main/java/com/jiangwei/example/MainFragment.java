package com.jiangwei.example;

import com.butterknife.luffy.butterknife.R;
import com.jiangwei.annotation.bindviewf.BindClickf;
import com.jiangwei.annotation.bindviewf.BindIdf;
import com.jiangwei.annotation.bindviewf.BindViewf;
import com.jiangwei.api.BFProxy;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * author: jiangwei18 on 17/4/19 20:34 email: jiangwei18@baidu.com Hi: jwill金牛
 */
@BindViewf(R.layout.fragment_main)
public class MainFragment extends Fragment {
    @BindIdf(R.id.btn1)
    public TextView btn1;
    @BindIdf(R.id.btn2)
    public TextView btn2;
    @BindIdf(R.id.ll_container)
    public LinearLayout llContainerl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = BFProxy.injectFragment(this);
        BFProxy.injectFragmentId(this, view);
        BFProxy.injectFragmentClick(this, view);
        View v = View.inflate(getActivity(), R.layout.activity_main2, null);
        ViewHolder holder = new ViewHolder(v);
        llContainerl.addView(v);
        return view;
    }

    @BindClickf({ R.id.btn1, R.id.btn2 })
    public void send(View view) {
        if (view.getId() == R.id.btn1) {
            System.out.println("btn1");
        } else {
            System.out.println("btn2");
        }
    }

    public static class ViewHolder {
        @BindIdf(R.id.tv2)
        public TextView tv2;
        @BindIdf(R.id.tv3)
        public TextView tv3;

        public ViewHolder(View view) {
            BFProxy.injectViewF(this, view);
            tv2.setText("viewholder2");
            tv3.setText("viewholder3");
        }
    }
}
