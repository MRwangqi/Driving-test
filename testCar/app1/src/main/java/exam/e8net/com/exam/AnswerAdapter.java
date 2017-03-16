package exam.e8net.com.exam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/10/20.
 */
public class AnswerAdapter extends BaseAdapter {


    @Override
    public int getCount() {
        return Question.result.size();
    }

    @Override
    public Object getItem(int position) {
        return Question.result.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Result result = Question.result.get(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            int width = ScreenUtils.width(parent.getContext());
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.circle_img, parent, false);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(width / 9, width / 9);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.circle_img);
            viewHolder.tv.setLayoutParams(params);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv.setText((position + 1) + "");

        if (!result.isFinishAnswer()) {//判断该题是否已经作答
            viewHolder.tv.setBackgroundResource(R.drawable.circle_default);
        } else {
            if (result.isChooseResult()) {//判断选择的结果是否正确
                viewHolder.tv.setBackgroundResource(R.drawable.circle_right);
            } else {
                viewHolder.tv.setBackgroundResource(R.drawable.circle_error);
            }
        }


        return convertView;
    }

    class ViewHolder {
        TextView tv;
    }
}
