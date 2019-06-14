package com.example.geoquiz;

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mAnswered;

    public Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mAnswered =false;

    }

    public void setAnswered(boolean isAnswered){
        this.mAnswered =isAnswered;
    }

    public  boolean getAnswered(){
        return mAnswered;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }







}
