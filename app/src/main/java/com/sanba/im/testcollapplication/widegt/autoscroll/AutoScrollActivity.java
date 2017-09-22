package com.sanba.im.testcollapplication.widegt.autoscroll;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sanba.im.testcollapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者:Created by 简玉锋 on 2017/2/23 14:23
 * 邮箱: jianyufeng@38.hn
 */

public class AutoScrollActivity extends AppCompatActivity {
    private LimitScrollerView limitScroll;
    private ArrayList<String> strings ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_scroll);
        limitScroll = (LimitScrollerView) findViewById(R.id.scrollView);
        strings = new ArrayList<>();
        strings.add("简玉锋");
        strings.add("jianyufeng");
        strings.add("123456789");
        strings.add("fsfffdffsf");
        strings.add("123fdsfdsfd");
        strings.add("fsdf123469");
        MyLimitScrllAdapter adapter = new MyLimitScrllAdapter();
        adapter.setDatas(strings);
        limitScroll.setDataAdapter(adapter);
        limitScroll.setItemClickListener(new LimitScrollerView.OnItemClickListener() {
            @Override
            public void onItemClick(Object obj) {
                Toast.makeText(getApplicationContext(),obj.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        limitScroll.startScroll();
    }

    @Override
    protected void onPause() {
        super.onPause();
        limitScroll.cancel();
    }

    class MyLimitScrllAdapter implements LimitScrollerView.LimitScrollAdapter {
        private List<String> datas;

        public void setDatas(List<String> datas) {
            this.datas = datas;

        }
        @Override
        public int getCount() {
            return datas==null ?0:datas.size();
        }

        @Override
        public View getView(int index) {
            TextView view = (TextView) LayoutInflater.from(AutoScrollActivity.this).inflate(android.R.layout.simple_list_item_1,null,false);
            String s = datas.get(index);
            view.setText(s);
            view.setTag(s);
            return view;
        }
    }

}
