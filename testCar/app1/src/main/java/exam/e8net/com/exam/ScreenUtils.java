package exam.e8net.com.exam;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/10/20.
 */
public class ScreenUtils {

    public static int width(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getWidth();
    }
}
