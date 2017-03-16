package exam.e8net.com.exam;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/21.
 */
public class Question implements Serializable {
    public static List<Result> result;
    private int errorCode;
    private String reason;


    public void setResult(List<Result> result) {
        this.result = result;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

}
