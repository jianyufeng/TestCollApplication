package com.sanba.im.testcollapplication.drag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sanba.im.testcollapplication.R;

/**
 * 作者:Created by 简玉锋 on 2017/2/23 17:25
 * 邮箱: jianyufeng@38.hn
 */

public class LeftMenuFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.drag_view,container,false);
    }
}
