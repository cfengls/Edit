package com.cfengls.scedit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/2/24.
 */

public class GoEdit extends AppCompatActivity{
    @Override
    public  void  onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取布局文件容器
        LinearLayout root = (LinearLayout) findViewById(R.id.root);
        final MyEditText myEditText = new MyEditText(this,null);
        root.addView(myEditText);
    }
}
