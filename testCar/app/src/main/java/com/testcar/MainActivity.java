package com.testcar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

import exam.e8net.com.exam.Question;
import exam.e8net.com.exam.QuestionActivity;
import exam.e8net.com.exam.Result;

public class MainActivity extends AppCompatActivity {
    // public static String SQL = "select * from c1_4 order by random() limit 100";//科四题目
    public static String SQL = "select * from c1_1 order by random() limit 100";
    ArrayList<Result> results = new ArrayList<>();
    ProgressDialog dialog;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(MainActivity.this, QuestionActivity.class);
    }

    //随机练习
    public void intent(View view) {
        intent.putExtra(QuestionActivity.IS_EXAM, false);
        SQL = "select * from c1_1 order by random() limit 100";
        initData();
    }

    //顺序练习
    public void test(View v) {
        intent.putExtra(QuestionActivity.IS_EXAM, false);
        SQL = "select * from c1_1";
        initData();
    }

    //全真模拟
    public void exam(View v) {
        intent.putExtra(QuestionActivity.IS_EXAM, true);
        SQL = "select * from c1_1 order by random() limit 100";
        initData();
    }


    public void initData() {
        results.clear();
        dialog = new ProgressDialog(this);
        dialog.setTitle("loading");
        dialog.show();
        new Thread() {
            @Override
            public void run() {
                SQLiteDatabase database = SQLTools.opendatabase(MainActivity.this);
                Cursor cursor = database.rawQuery(SQL, null);
                int question = cursor.getColumnIndex("question");
                int answer = cursor.getColumnIndex("answer");
                int item1 = cursor.getColumnIndex("item1");
                int item2 = cursor.getColumnIndex("item2");
                int item3 = cursor.getColumnIndex("item3");
                int item4 = cursor.getColumnIndex("item4");
                int explains = cursor.getColumnIndex("explains");
                int url = cursor.getColumnIndex("url");
                while (cursor.moveToNext()) {
                    Result r = new Result();
                    r.setQuestion(cursor.getString(question));
                    r.setAnswer(cursor.getString(answer));
                    r.setItem1(cursor.getString(item1));
                    r.setItem2(cursor.getString(item2));
                    r.setItem3(cursor.getString(item3));
                    r.setItem4(cursor.getString(item4));
                    r.setExplains(cursor.getString(explains));
                    r.setUrl(cursor.getString(url));
                    results.add(r);
                }
                Question.result = results;
                handler.sendEmptyMessage(0);
                cursor.close();
                database.close();

            }
        }.start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            dialog.dismiss();
            startActivity(intent);
        }
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
