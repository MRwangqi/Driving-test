package exam.e8net.com.exam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        textView = (TextView) findViewById(R.id.result_result);

        int rightAnswer = 0;
        for (Result r : Question.result
                ) {
            if (r.isChooseResult())
                rightAnswer++;
        }

        if (rightAnswer < 80) {
            textView.setText("马路杀手");
        } else if (rightAnswer >= 80 && rightAnswer < 90) {
            textView.setText("碾压一切");
        } else {
            textView.setText("秋名山上行人稀，\n常有车神较高低,\n如今车牌依旧在,\n不见当年老司机。\n    ----秋名山车神");
        }
    }
}
