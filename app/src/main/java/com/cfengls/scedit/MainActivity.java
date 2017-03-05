package com.cfengls.scedit;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ren.qinc.edit.PerformEdit;

import static java.lang.Byte.SIZE;


public class MainActivity extends AppCompatActivity {

    public static final int FILE_RESULT_CODE = 1;

    String filePaths = "";
    String [] yeka = new String[]{"","","","","","","","","",""};

    private PerformEdit mPerformEdit;
    int zhuti = 0,tabgeshu = Tabgeshu.getTabgeshu();

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        String flag = "";

        Intent intent1 = this.getIntent();
        String str = "";
        String pattern = "\n";
        String neirong = "";
        String name = "";

        String name2 = "";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(flag);


        filePaths = getIntent().getStringExtra("filePath");
        flag = intent1.getStringExtra("flag");
        str = intent1.getStringExtra("str");
        neirong = intent1.getStringExtra("neirong");
        tabgeshu = Tabgeshu.getTabgeshu();
        name = intent1.getStringExtra("name");

        TabLayout tabLayout= (TabLayout) findViewById(R.id.tabLayout);



        if (neirong!=name2&&tabgeshu>0) {

            name2 = name;


       for (int i=0;i<tabgeshu;i++) {

           tabLayout.addTab(tabLayout.newTab().setText(YeKa.getYeKa(tabgeshu)));

       }


        }else {
            tabLayout.addTab(tabLayout.newTab().setText(name));
        }



        EditText myEditText= ((EditText) findViewById(R.id.MT));

        mPerformEdit = new PerformEdit(myEditText){
            @Override
            protected void onTextChanged(Editable s) {
                //文本发生改变,可以是用户输入或者是EditText.setText触发.(setDefaultText的时候不会回调)
                super.onTextChanged(s);
            }
        };

/*
        SpannableString ss = new SpannableString("我的Android博客,高亮测试");

        ss.setSpan(new ForegroundColorSpan(Color.YELLOW),0,ss.length(),Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        myEditText.setText(ss);*/
        TextUtilTools textUtilTools = new TextUtilTools();

/*
    SpannableStringBuilder textString = TextUtilTools.highlight(neirong, "function");
    myEditText.setText(textString);
*/

        myEditText.setText(neirong);
// Title


        toolbar.setTitle("SC Edit");
// Sub Title
        toolbar.setSubtitle("By CFengls");


        setSupportActionBar(toolbar);



                  //tabLayout.addTab(tabLayout.newTab().setText(name));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            /**
             * 当tab第一次选择时候调用
             * @param tab
             */

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(MainActivity.this, "select tab = "+tab.getText(), Toast.LENGTH_SHORT).show();


            }

            /**
             * 当tab从 选择 ->未选择
             * @param tab
             */
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Toast.makeText(MainActivity.this, "unselected = "+tab.getText(), Toast.LENGTH_SHORT).show();
            }

            /**
             * 当tab已是选择状态时，继续点击调用此方法
             * @param tab
             */
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Toast.makeText(MainActivity.this, "reselected = "+tab.getText(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_openText) {//新建
openBrowser();
        }
        if (id==R.id.action_OK){
            EditText myEditText= ((EditText) findViewById(R.id.MT));
            FileUtil.writeString(filePaths,myEditText.getText().toString(),"utf-8");
           FileUtil.readString(myEditText.getText().toString(),"utf-8");
            Toast.makeText(this,myEditText.getText().toString(),Toast.LENGTH_SHORT).show();
        }
        if (id==R.id.action_qianjinText){
            mPerformEdit.redo();
        }
        if (id==R.id.action_houtuiText){

            mPerformEdit.undo();
        }
        if (id==R.id.action_zhuti){
            reload();

        }
        return super.onOptionsItemSelected(item);
    }
    private void openBrowser() {
        new AlertDialog.Builder(this).setTitle("选择存储区域").setIcon(
                R.drawable.open).setSingleChoiceItems(
                new String[]{"内置sd卡", "外部sd卡"}, 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, FileBrowserActivity.class);
                        if (which == 0)
                            intent.putExtra("area", false);
                        else
                            intent.putExtra("area", true);
                        startActivityForResult(intent, FILE_RESULT_CODE);
                        dialog.dismiss();
                    }
                }).setNegativeButton("取消", null).show();
    }





    public static void TemporaryFile(String pathString) {
        File file = new File(pathString);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {

                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        } else {
            System.out.println("文件已存在");
        }
    }
    /*
    public void readTxt(String filePath) {

        try {
            // read file content from file
            StringBuffer sb = new StringBuffer("");

            FileReader reader = new FileReader(filePath);
            BufferedReader br = new BufferedReader(reader);

            String str = "";
            String str2 = "";

            while ((str = br.readLine()) != null) {
                if (str2 != "") {
                    str = str2 + "\n" + str;
                }
                str2 = str;
                EditText myEditText = ((EditText) findViewById(R.id.MT));
                myEditText.setText(str);
            }

            br.close();
            reader.close();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();

        }
    }*/
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);//不设置进入退出动画
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
}
