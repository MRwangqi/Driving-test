package exam.e8net.com.exam;


import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {


    public int position;
    View v;
    private ImageView titleImg;
    private TextView title;
    private RadioGroup radioGroup;
    private LinearLayout explainLayout;
    private TextView explainTxt;
    private LinearLayout checkLayout;

    List<AppCompatRadioButton> listRadio;
    List<AppCompatCheckBox> listCheck;
    int answer;
    Result result;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        position = getArguments().getInt("position");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listRadio = new ArrayList<>();
        listCheck = new ArrayList<>();
        v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_question, container, false);
        initView();
        initData();
        initListner();
        return v;
    }

    private void initView() {
        titleImg = (ImageView) v.findViewById(R.id.que_img);
        title = (TextView) v.findViewById(R.id.que_title);
        radioGroup = (RadioGroup) v.findViewById(R.id.que_group);
        explainLayout = (LinearLayout) v.findViewById(R.id.que_explain_layout);
        explainTxt = (TextView) v.findViewById(R.id.que_explain_txt);
        checkLayout = (LinearLayout) v.findViewById(R.id.que_check_layout);
    }

    private void initData() {
        result = Question.result.get(position);
        title.setText(result.getQuestion());
        explainTxt.setText(result.getExplains());

        answer = Integer.parseInt(result.getAnswer());
        if (answer <= 4) {
            initRadioButton();
        } else {
            initCheckBox();
        }

        if (!TextUtils.isEmpty(result.getUrl()))
            Glide.with(getContext()).load(result.getUrl()).placeholder(R.drawable.loading).error(R.drawable.load_fail).into(titleImg);
        else {
            titleImg.setVisibility(View.GONE);
        }

    }

    void initRadioButton() {
        if (!TextUtils.isEmpty(result.getItem1())) {
            addRadioButtonView(result.getItem1());
        }
        if (!TextUtils.isEmpty(result.getItem2())) {
            addRadioButtonView(result.getItem2());
        }
        if (!TextUtils.isEmpty(result.getItem3())) {
            addRadioButtonView(result.getItem3());
        }
        if (!TextUtils.isEmpty(result.getItem4())) {
            addRadioButtonView(result.getItem4());
        }

        if (result.isFinishAnswer()) {//判断当前题目是否已经答题答过了，如果是的话，就恢复答题数据，并且设置不可点击
            radioButtonClickEnable();
            if (!result.isChooseResult()) {//如果没有选择到正确答案的话，就要显示错误答案，否则不显示
                AppCompatRadioButton radio = listRadio.get(result.getErrorAnswer() - 1);
                radio.setTextColor(getContext().getResources().getColor(R.color.error));
                setErrorDrable(radio);
            }
            AppCompatRadioButton radio =
                    listRadio.get(result.getRightAnswer() - 1);
            radio.setTextColor(getContext().getResources().getColor(R.color.right));
            setRightDrable(radio);
        }
    }

    void initCheckBox() {
        if (!TextUtils.isEmpty(result.getItem1())) {
            addCheckBoxView(result.getItem1());
        }
        if (!TextUtils.isEmpty(result.getItem2())) {
            addCheckBoxView(result.getItem2());
        }
        if (!TextUtils.isEmpty(result.getItem3())) {
            addCheckBoxView(result.getItem3());
        }
        if (!TextUtils.isEmpty(result.getItem4())) {
            addCheckBoxView(result.getItem4());
        }

        if (result.isFinishAnswer()) {
            checkBoxClickEnable();
            //遍历用户的选择
            for (int i = 0; i < result.getResultList().size(); i++) {
                int a = result.getResultList().get(i);//拿到答题的标号
                listCheck.get(a - 1).setTextColor(getContext().getResources().getColor(R.color.error));
                listCheck.get(a - 1).setChecked(true);
            }

            for (int i = 0; i < result.getRightList().size(); i++) {
                int a = result.getRightList().get(i);//拿到答题的标号
                listCheck.get(a - 1).setTextColor(getContext().getResources().getColor(R.color.right));
            }

        } else {
            //添加一个确定按钮
            AppCompatButton appCompatButton = new AppCompatButton(getContext());
            appCompatButton.setText("确定");
            appCompatButton.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
            appCompatButton.setTextColor(Color.parseColor("#ffffff"));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(20, 30, 20, 20);
            appCompatButton.setLayoutParams(params);
            checkLayout.addView(appCompatButton);
            appCompatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doHandle();
                }
            });
        }
    }


    void radioButtonClickEnable() {
        for (AppCompatRadioButton radioButton :
                listRadio) {
            radioButton.setClickable(false);
        }
    }

    void checkBoxClickEnable() {
        for (AppCompatCheckBox checkbos :
                listCheck) {
            checkbos.setClickable(false);
        }
    }

    private void initListner() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup rd, int id) {
                for (int i = 0; i < listRadio.size(); i++) {
                    AppCompatRadioButton radioButton = listRadio.get(i);
                    //遍历查找找到当前点击的item
                    if (radioButton.getId() == id) {
                        if ((i + 1) == answer) {//判断选择是否是正确答案
                            radioButton.setTextColor(getContext().getResources().getColor(R.color.right));
                            result.setChooseResult(true);//存储用户选择的答案为正确的
                            setRightDrable(radioButton);//设置样式
                            QuestionActivity.nextQuestion();
                        } else {//选择的是错误答案
                            radioButton.setTextColor(getContext().getResources().getColor(R.color.error));
                            setErrorDrable(radioButton);//设置样式

                            listRadio.get(answer - 1).setTextColor(getContext().getResources().getColor(R.color.right));
                            setRightDrable(listRadio.get(answer - 1));

                            result.setErrorAnswer(i + 1);//设置选错题目的标识
                            result.setChooseResult(false);//存储用户选择的答案为错误的
                            setErrorDrable(radioButton);
                        }
                        result.setRightAnswer(answer);//设置选对题目的标识
                        result.setFinishAnswer(true);//设置完成了答题
                        radioButtonClickEnable();//设置不可点击

                        QuestionActivity.upDataRightAndError();//更新MainActivity
                        break;
                    }
                }
            }
        });

        explainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                explainTxt.setVisibility(View.VISIBLE);
            }
        });

    }


    public void addCheckBoxView(String question) {
        AppCompatCheckBox checkBox = new AppCompatCheckBox(getContext());
        checkBox.setText(question);
        checkBox.setTextSize(20);
        checkBox.setTextColor(getContext().getResources().getColor(R.color.black333));
        RadioGroup.LayoutParams param = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        param.setMargins(10, 10, 10, 10);
        checkBox.setLayoutParams(param);
        checkLayout.addView(checkBox);
        listCheck.add(checkBox);
    }


    public void addRadioButtonView(String question) {
        AppCompatRadioButton appCompatRadioButton = new AppCompatRadioButton(getContext());
        appCompatRadioButton.setText(question);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_default);
        appCompatRadioButton.setButtonDrawable(bitmapDrawable);
        appCompatRadioButton.setTextSize(20);
        appCompatRadioButton.setTextColor(getContext().getResources().getColor(R.color.black333));
        RadioGroup.LayoutParams param = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        param.setMargins(10, 10, 10, 10);
        appCompatRadioButton.setLayoutParams(param);
        radioGroup.addView(appCompatRadioButton);
        listRadio.add(appCompatRadioButton);
    }

    public void setErrorDrable(AppCompatRadioButton appCompatRadioButton) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_error);
        appCompatRadioButton.setButtonDrawable(bitmapDrawable);
    }


    public void setRightDrable(AppCompatRadioButton appCompatRadioButton) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_right);
        appCompatRadioButton.setButtonDrawable(bitmapDrawable);
    }


    //处理点击事件
    private void doHandle() {
        switch (answer) {
            case 7://AB 也就是1、2选项为正确
                //存储正确答案
                result.getRightList().add(1);
                listCheck.get(0).setTextColor(getContext().getResources().getColor(R.color.right));
                result.getRightList().add(2);
                listCheck.get(1).setTextColor(getContext().getResources().getColor(R.color.right));
                break;
            case 8://AC 也就是1、3
                //存储正确答案
                result.getRightList().add(1);
                listCheck.get(0).setTextColor(getContext().getResources().getColor(R.color.right));
                result.getRightList().add(3);
                listCheck.get(2).setTextColor(getContext().getResources().getColor(R.color.right));

                break;
            case 9://AD 也就是1、4
                //存储正确答案
                result.getRightList().add(1);
                listCheck.get(0).setTextColor(getContext().getResources().getColor(R.color.right));
                result.getRightList().add(4);
                listCheck.get(3).setTextColor(getContext().getResources().getColor(R.color.right));
                break;
            case 10://BC 也就是2、3
                //存储正确答案
                result.getRightList().add(2);
                listCheck.get(1).setTextColor(getContext().getResources().getColor(R.color.right));
                result.getRightList().add(3);
                listCheck.get(2).setTextColor(getContext().getResources().getColor(R.color.right));

                break;
            case 11://BD 也就是2、4
                //存储正确答案
                result.getRightList().add(2);
                listCheck.get(1).setTextColor(getContext().getResources().getColor(R.color.right));
                result.getRightList().add(4);
                listCheck.get(3).setTextColor(getContext().getResources().getColor(R.color.right));
                break;
            case 12://CD 也就是3、4
                //存储正确答案
                result.getRightList().add(3);
                listCheck.get(2).setTextColor(getContext().getResources().getColor(R.color.right));
                result.getRightList().add(4);
                listCheck.get(3).setTextColor(getContext().getResources().getColor(R.color.right));


                break;
            case 13://ABC 也就是1、2、3

                //存储正确答案
                result.getRightList().add(1);
                listCheck.get(0).setTextColor(getContext().getResources().getColor(R.color.right));
                result.getRightList().add(2);
                listCheck.get(1).setTextColor(getContext().getResources().getColor(R.color.right));
                result.getRightList().add(3);
                listCheck.get(2).setTextColor(getContext().getResources().getColor(R.color.right));

                break;
            case 14://ABD 也就是1、2、4
                result.getRightList().add(1);
                listCheck.get(0).setTextColor(getContext().getResources().getColor(R.color.right));
                result.getRightList().add(2);
                listCheck.get(1).setTextColor(getContext().getResources().getColor(R.color.right));
                result.getRightList().add(4);
                listCheck.get(3).setTextColor(getContext().getResources().getColor(R.color.right));

                break;
            case 15://ACD 也就是1、3、4
                result.getRightList().add(1);
                listCheck.get(0).setTextColor(getContext().getResources().getColor(R.color.right));
                result.getRightList().add(3);
                listCheck.get(2).setTextColor(getContext().getResources().getColor(R.color.right));
                result.getRightList().add(4);
                listCheck.get(3).setTextColor(getContext().getResources().getColor(R.color.right));

                break;
            case 16://BCD 也就是2、3、4
                result.getRightList().add(2);
                listCheck.get(1).setTextColor(getContext().getResources().getColor(R.color.right));
                result.getRightList().add(3);
                listCheck.get(2).setTextColor(getContext().getResources().getColor(R.color.right));
                result.getRightList().add(4);
                listCheck.get(3).setTextColor(getContext().getResources().getColor(R.color.right));

                break;
            case 17://ABCD 也就是1、2、3、4
                result.getRightList().add(1);
                listCheck.get(0).setTextColor(getContext().getResources().getColor(R.color.right));
                result.getRightList().add(2);
                listCheck.get(1).setTextColor(getContext().getResources().getColor(R.color.right));
                result.getRightList().add(3);
                listCheck.get(2).setTextColor(getContext().getResources().getColor(R.color.right));
                result.getRightList().add(4);
                listCheck.get(3).setTextColor(getContext().getResources().getColor(R.color.right));
                break;
        }
        //然后进行筛选
        commonJudge();

    }

    void commonJudge() {
        //存储用户选择的答案
        for (int i = 0; i < listCheck.size(); i++) {
            if (listCheck.get(i).isChecked()) {  //遍历查询当前是否选中
                result.getResultList().add(i + 1);
            }
        }

        //先判断用户输入的和答案的选择个数是否相同
        if (result.getResultList().size() == result.getRightList().size()) {
            for (int i = 0; i < result.getResultList().size(); i++) {
                if (result.getResultList().get(i) != result.getRightList().get(i)) {
                    result.setChooseResult(false);//遍历如果有答案不相同时，则选择答题失败，并跳出
                    //如果有错误的话就把错误答案显示出来
                    for (int j = 0; j < listCheck.size(); j++) {
                        if (listCheck.get(j).isChecked()) {  //遍历查询当前是否选中
                            listCheck.get(j).setTextColor(getContext().getResources().getColor(R.color.error));
                        }
                    }
                    break;
                } else {
                    result.setChooseResult(true);//这一步骤可以设置为true，因为只要有不同就直接跳出了，并且设置了false，如果不走的话，就是为true
                }
            }
            if (result.isChooseResult())
                QuestionActivity.nextQuestion();
        } else {
            //如果有错误的话就把错误答案显示出来
            for (int j = 0; j < listCheck.size(); j++) {
                if (listCheck.get(j).isChecked()) {  //遍历查询当前是否选中
                    listCheck.get(j).setTextColor(getContext().getResources().getColor(R.color.error));
                }
            }
            result.setChooseResult(false);
        }
        result.setFinishAnswer(true);
        checkBoxClickEnable();
        QuestionActivity.upDataRightAndError();//更新MainActivity

    }


}
