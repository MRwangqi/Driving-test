package exam.e8net.com.exam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/4.
 */

public class Result {

    /**
     * {
     * "id": "9",
     * "question": "立体交叉处这个标志提示什么？",
     * "answer": "2",
     * "item1": "向右转弯",
     * "item2": "直行和左转弯",
     * "item3": "直行和右转弯",
     * "item4": "在桥下掉头",
     * "explains": "立交直行和左转弯行驶-表示车辆在立交处可以直行和按图示路线左转弯行驶。此标志设在立交左转弯出口处适当位置。",
     * "url": "http://api.avatardata.cn/Jztk/Img?file=b183701e051c472bb92f1a2bada295ce.jpg"
     * }
     */
    private String id;
    private String question;
    private String answer;
    private String item1;
    private String item2;
    private String item3;
    private String item4;
    private String explains;
    private String url;

    //单选
    private int rightAnswer;  //存储正确答案
    private int errorAnswer;  //存储错误答案
    private boolean chooseResult;//存储最最终用户选择是否正确
    private boolean finishAnswer;//是否完成了答题

    //多选
    private List<Integer> rightList = new ArrayList<>();//存储正确的答案
    private List<Integer> resultList = new ArrayList<>();//存储用户答题的答案


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem1() {
        return item1;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

    public String getItem3() {
        return item3;
    }

    public void setItem4(String item4) {
        this.item4 = item4;
    }

    public String getItem4() {
        return item4;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }

    public String getExplains() {
        return explains;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }


    public int getErrorAnswer() {
        return errorAnswer;
    }

    public void setErrorAnswer(int errorAnswer) {
        this.errorAnswer = errorAnswer;
    }

    public boolean isChooseResult() {
        return chooseResult;
    }

    public void setChooseResult(boolean chooseResult) {
        this.chooseResult = chooseResult;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public boolean isFinishAnswer() {
        return finishAnswer;
    }

    public void setFinishAnswer(boolean finishAnswer) {
        this.finishAnswer = finishAnswer;
    }


    public List<Integer> getRightList() {
        return rightList;
    }

    public void setRightList(List<Integer> rightList) {
        this.rightList = rightList;
    }

    public List<Integer> getResultList() {
        return resultList;
    }

    public void setResultList(List<Integer> resultList) {
        this.resultList = resultList;
    }
}
