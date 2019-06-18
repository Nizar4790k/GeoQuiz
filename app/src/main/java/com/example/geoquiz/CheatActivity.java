package com.example.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.os.Build.VERSION.SDK_INT;

public class CheatActivity extends AppCompatActivity {


    private static final String ANSWER_KEY ="answer_key";

    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.example.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN =
            "com.bignerdranch.android.geoquiz.answer_shown";

    private static final String  CHEAT_COUNT = "com.example.cheatcount";

    private boolean mAnswerIsTrue;

    private TextView mAnswerTextView;
    private TextView mApiTextView;





    private Button mShowAnswerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);




        mAnswerTextView = findViewById(R.id.answer_text_view);

        mAnswerIsTrue=getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);


        mApiTextView = (TextView) findViewById(R.id.api_text_view);

        StringBuilder sb = new StringBuilder(getString(R.string.api_level));
        sb.append(SDK_INT);

        mApiTextView.setText(sb.toString());






        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);



        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    setAnswer(mAnswerIsTrue);
                    setAnswerShownResult(true);





               if(SDK_INT>=Build.VERSION_CODES.LOLLIPOP){


                   int cx = mShowAnswerButton.getWidth() / 2;
                   int cy = mShowAnswerButton.getHeight() / 2;
                   float radius = mShowAnswerButton.getWidth();
                   Animator anim = ViewAnimationUtils
                           .createCircularReveal(mShowAnswerButton, cx, cy, radius, 0);
                   anim.addListener(new AnimatorListenerAdapter() {
                       @Override
                       public void onAnimationEnd(Animator animation) {
                           super.onAnimationEnd(animation);
                           mShowAnswerButton.setVisibility(View.INVISIBLE);
                       }
                   });
                   anim.start();
               } else {

                   mShowAnswerButton.setVisibility(View.INVISIBLE);

               }




            }

        });

        if(savedInstanceState!=null){

            mAnswerIsTrue=savedInstanceState.getBoolean(ANSWER_KEY);
            setAnswer(mAnswerIsTrue);
            setAnswerShownResult(true);




        }






    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);

        setResult(RESULT_OK, data);
    }




    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }




    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);

        return intent;
    }







    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putBoolean(ANSWER_KEY,mAnswerIsTrue);


    }


    public void setAnswer(boolean mAnswerIsTrue){
        if(mAnswerIsTrue){
            mAnswerTextView.setText(R.string.true_button);

        }else {
            mAnswerTextView.setText(R.string.false_button);

        }
    }


}
