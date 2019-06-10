package com.example.geoquiz;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    private Button mTrueButton;
    private Button mFalseButton;

    private ImageButton mNextButton;
    private ImageButton mPreviousButton;


    private TextView mQuestionTextView;

    private Question [] mQuestionBank;

    private   int mCurrentIndex;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




     mQuestionBank = new Question[]{
             new Question(R.string.question_australia, true),
             new Question(R.string.question_oceans, true),
             new Question(R.string.question_mideast, false),
             new Question(R.string.question_africa, false),
             new Question(R.string.question_americas, true),
             new Question(R.string.question_asia, true),

        };


        mCurrentIndex=0;

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

            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAnswer(false);

            }

          });



        mNextButton = (ImageButton) findViewById(R.id.next_button);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
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

        }else {

            Toast toast= Toast.makeText(MainActivity.this,R.string.incorrect_toast,Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP,0,0);
            toast.show();

        }







    }





}
