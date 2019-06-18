package com.example.geoquiz;

import android.app.Activity;
import android.content.Intent;
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
    private  static  final int REQUEST_CODE_CHEAT=0;
    private static final String TAG = "QuizActivity";
    private static final String CHEATER="cheater";
    private static final String CHEAT_COUNT ="cheatcount";

    private boolean mIsCheater;



    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;

    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private int mCheatCount;


    private TextView mQuestionTextView;
    private TextView mApiTextView;

    private Question [] mQuestionBank;

    private   int mCurrentIndex;

    private boolean [] answered;


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
                mIsCheater = false;
                updateQuestion();

                if(mCheatCount>=3){
                    mCheatButton.setEnabled(false);
                }




            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);


        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(MainActivity.this,answerIsTrue);

                startActivityForResult(intent,REQUEST_CODE_CHEAT);





            }
        });


        if(mCheatCount>=3){
            mCheatButton.setEnabled(false);
        }


        mPreviousButton = (ImageButton) findViewById(R.id.prev_button);

        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mCheatCount>=3){
                    mCheatButton.setEnabled(false);
                }


                mCurrentIndex = (mCurrentIndex -1) % (mQuestionBank.length);


                if(mCurrentIndex<0){
                    mCurrentIndex=mQuestionBank.length-1;


                }

                disableButtons (mQuestionBank[mCurrentIndex].getAnswered());

                updateQuestion();
            }
        });

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater=savedInstanceState.getBoolean(CHEATER);
            mCheatCount=savedInstanceState.getInt(CHEAT_COUNT);


            answered=(boolean [])savedInstanceState.get(ANSWERED);





               for(int i=0;i<answered.length;i++){
                mQuestionBank[i].setAnswered(answered[i]);
                if(i==mCurrentIndex){
                    disableButtons(mQuestionBank[i].getAnswered());
                }

            }




        }




        updateQuestion();


    }





    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }


    private  void checkAnswer(boolean userPressedTrue){

        boolean isAnswerTrue =  mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId=0;

      if(mIsCheater){

          messageResId = R.string.judgment_toast;
          mCheatCount++;

          if(mCheatCount>=3){
              Toast.makeText(MainActivity.this,R.string.cheat_limit,Toast.LENGTH_SHORT).show();
          }



      }

      if(isAnswerTrue==userPressedTrue){

              messageResId=R.string.correct_toast;

              score+=16.66667;



          }else {

              messageResId=R.string.incorrect_toast;


          }




        Toast toast= Toast.makeText(MainActivity.this,messageResId,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,0);
        toast.show();




        String scoreDescription = getString(R.string.score_message);
        StringBuilder stringBuilder= new StringBuilder(scoreDescription);
        String message = stringBuilder.append(score).toString();

        Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();






    }


    public void disableButtons(boolean answered){

        if(answered){
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
            mCheatButton.setEnabled(false);
        } else {

            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
            mCheatButton.setEnabled(true);

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
        }


        savedInstanceState.putBooleanArray(ANSWERED,areAnswered);
        savedInstanceState.putBoolean(CHEATER,mIsCheater);
        savedInstanceState.putInt(CHEAT_COUNT,mCheatCount);



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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);

        }
    }
}
