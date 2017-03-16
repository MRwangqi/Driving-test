package exam.e8net.com.exam;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionActivity extends AppCompatActivity {
    public static final String IS_EXAM = "is_exam";
    private static ViewPager viewPager;
    private LinearLayout mainBar;
    private static TextView rightTxt, errorTxt, totalTxt;
    private TextView timer;
    boolean isExam;
    private DefineTimer countDownTimer;
    private LinearLayout timerLayout;
    private ImageView back;
    private TextView submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_main);
        viewPager = (ViewPager) findViewById(R.id.main_viewpager);
        mainBar = (LinearLayout) findViewById(R.id.main_bar);
        rightTxt = (TextView) findViewById(R.id.main_right_tx);
        errorTxt = (TextView) findViewById(R.id.main_error_tx);
        totalTxt = (TextView) findViewById(R.id.main_total_tx);
        timer = (TextView) findViewById(R.id.question_countdown);
        timerLayout = (LinearLayout) findViewById(R.id.question_timer);
        back = (ImageView) findViewById(R.id.question_back);
        submit = (TextView) findViewById(R.id.question_submit);

        mainBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBottomSheetDialog();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExam)
                    backHandler();
                else
                    QuestionActivity.this.finish();
            }
        });
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(new MyPagerChangeListner());

        isExam = getIntent().getBooleanExtra(IS_EXAM, false);

        if (!isExam) {//如果不是考试,就隐藏
            timerLayout.setVisibility(View.GONE);
            submit.setVisibility(View.GONE);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backHandler();
            }
        });
    }

    private long currentTime = 120000L;

    public void startTimer() {
        countDownTimer = new DefineTimer(currentTime, 1000) {//2700 45分钟
            @Override
            public void onTick(long l) {
                currentTime = l;
                int allSecond = (int) l / 1000;//秒
                int minute = allSecond / 60;
                int second = allSecond - minute * 60;
                timer.setText("倒计时 " + minute + ":" + second);
            }

            @Override
            public void onFinish() {
                submitAnswer();
            }
        };
        countDownTimer.start();
    }


    @Override
    protected void onPause() {
        if (isExam) {
            countDownTimer.cancel();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (isExam) {
            startTimer();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (isExam) {
            countDownTimer.cancel();
        }
        super.onDestroy();
    }

    public static void nextQuestion() {
        if (viewPager.getCurrentItem() <= Question.result.size()) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    }

    BottomSheetDialog bottomSheetDialog;

    public void openBottomSheetDialog() {
        bottomSheetDialog = new BottomSheetDialog(this);
        GridView gridView = new GridView(this);
        gridView.setNumColumns(6);
        gridView.setVerticalSpacing(10);
        gridView.setHorizontalSpacing(10);
        gridView.setBackgroundColor(0xffffffff);
        gridView.setAdapter(new AnswerAdapter());
        gridView.setScrollBarStyle(GridView.SCROLLBARS_OUTSIDE_INSET);
        gridView.setPadding(20, 20, 20, 20);
        bottomSheetDialog.setContentView(gridView);
        bottomSheetDialog.show();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewPager.setCurrentItem(position, false);
                bottomSheetDialog.dismiss();
            }
        });
    }


    class MyPagerChangeListner implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            totalTxt.setText((position + 1) + "/" + Question.result.size());
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            QuestionFragment questionFragment = new QuestionFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            questionFragment.setArguments(bundle);
            return questionFragment;
        }

        @Override
        public int getCount() {
            return Question.result.size();
        }
    }


    static int rightCount;
    static int errorCount;

    public static void upDataRightAndError() {
        rightCount = 0;
        errorCount = 0;
        for (Result result : Question.result
                ) {
            if (result.isFinishAnswer()) {
                if (result.isChooseResult()) {
                    rightCount++;
                } else {
                    errorCount++;
                }
            }
        }
        rightTxt.setText(rightCount + "");
        errorTxt.setText(errorCount + "");
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isExam) {
            backHandler();
            return false;
        }
        return super.onKeyUp(keyCode, event);
    }

    public void backHandler() {
        int finishAnswer = 0;
        int rightAnswer = 0;
        for (Result r : Question.result) {
            if (r.isChooseResult())
                rightAnswer++;
            if (r.isFinishAnswer())
                finishAnswer++;
        }

        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("您已回答了" + finishAnswer + "题(共100)题,考试得分" + rightAnswer + ",确定交卷？").setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                submitAnswer();
            }
        }).setPositiveButton("退出答题", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                QuestionActivity.this.finish();
            }
        }).create();
        dialog.show();
    }


    //提交答案
    public void submitAnswer() {
        startActivity(new Intent(QuestionActivity.this, ResultActivity.class));
        finish();
    }

}
