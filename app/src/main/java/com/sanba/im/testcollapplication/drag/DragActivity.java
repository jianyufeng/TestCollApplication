package com.sanba.im.testcollapplication.drag;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.sanba.im.testcollapplication.R;

/**
 * 作者:Created by 简玉锋 on 2017/2/22 10:04
 * 邮箱: jianyufeng@38.hn
 */

public class DragActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drag_view);
        findViewById(R.id.drag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(),"fdsfs",Toast.LENGTH_LONG).show();
            }
        });
    }
}
