package com.example.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final String KEY_INDEX = "index";
    private static final String ANSWERED="answered";



    private Button mTrueButton;
    private Button mFalseButton;

    private ImageButton mNextButton;
    private ImageButton mPreviousButton;


    private TextView mQuestionTextView;

    private Question [] mQuestionBank;

    private   int mCurrentIndex;

    private boolean [] answered;

    private static final String TAG = "QuizActivity";
    private double score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(Bundle) called");
        setContentView(R.layout.activity_main);


        mQuestionBank = new Question[]{
                new Question(R.string.question_australia, true),
                new Question(R.string.question_oceans, true),
                new Question(R.string.question_mideast, false),
                new Question(R.string.question_africa, false),
                new Question(R.string.question_americas, true),
                new Question(R.string.question_asia, true),

        };

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateQuestion();



            }
        });





        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);



            answered=savedInstanceState.getBooleanArray(ANSWERED);


            /*
            for(int i=0;i<answered.length;i++){
                mQuestionBank[i].setAnswered(answered[i]);
                if(i==mCurrentIndex){
                    disableButtons(mQuestionBank[i].getAnswered());
                }

            }

            */


        }


















        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAnswer(true);
                mQuestionBank[mCurrentIndex].setAnswered(true);
                disableButtons(mQuestionBank[mCurrentIndex].getAnswered());

            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAnswer(false);
                mQuestionBank[mCurrentIndex].setAnswered(true);
                disableButtons(mQuestionBank[mCurrentIndex].getAnswered());


            }

          });



        mNextButton = (ImageButton) findViewById(R.id.next_button);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;

                disableButtons (mQuestionBank[mCurrentIndex].getAnswered());
                updateQuestion();





            }
        });


        mPreviousButton = (ImageButton) findViewById(R.id.prev_button);

        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mCurrentIndex = (mCurrentIndex -1) % (mQuestionBank.length);


                if(mCurrentIndex<0){
                    mCurrentIndex=mQuestionBank.length-1;


                }

                disableButtons (mQuestionBank[mCurrentIndex].getAnswered());

                updateQuestion();
            }
        });



        updateQuestion();


    }





    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }


    private  void checkAnswer(boolean userPressedTrue){

        boolean isAnswerTrue =  mQuestionBank[mCurrentIndex].isAnswerTrue();

        if(isAnswerTrue==userPressedTrue){
            Toast toast= Toast.makeText(MainActivity.this,R.string.correct_toast,Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP,0,0);
            toast.show();
            score+=16.66667;



        }else {

            Toast toast= Toast.makeText(MainActivity.this,R.string.incorrect_toast,Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP,0,0);
            toast.show();

        }


        String scoreDescription = getString(R.string.score_message);
        StringBuilder stringBuilder= new StringBuilder(scoreDescription);
        String message = stringBuilder.append(score).toString();

        Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();






    }


    public void disableButtons(boolean answered){

        if(answered){
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        } else {

            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        Log.i(TAG, "onSaveInstanceState");

        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);

        boolean [] areAnswered= new boolean[mQuestionBank.length];

        for(int i=0;i<mQuestionBank.length;i++){
            areAnswered[i]=mQuestionBank[i].getAnswered();
            System.out.println(areAnswered[i]);

        }


        savedInstanceState.putBooleanArray(ANSWERED,areAnswered);



    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }





}
