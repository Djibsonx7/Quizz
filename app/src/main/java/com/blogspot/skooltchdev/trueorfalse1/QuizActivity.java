package com.blogspot.skooltchdev.trueorfalse1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    private TextView mScoreView, mQuestion;
    private ImageView mImageView;
    private Button mTrueButton, mFalseButton, jokerBtn;
    private int [] tabRand = new int[QuizBook.questions.length];
    private boolean mAnswer;
    private int mScore = 0;
    private int mQuestionNumber = 0;
    private int addToScore = 2;
    private int cmptJoker=2;
    private Random rand = new Random();
    int  indexAlea = rand.nextInt(QuizBook.questions.length);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mScoreView = (TextView)findViewById(R.id.points);
        mImageView = (ImageView)findViewById(R.id.imageView);
        mQuestion = (TextView)findViewById(R.id.question);
        mTrueButton = (Button)findViewById(R.id.trueButton);
        mFalseButton = (Button)findViewById(R.id.falseButton);
        jokerBtn = (Button)findViewById(R.id.jokerBtn);
        updateQuestion();

        //Logic for true button
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAnswer == true) {
                    mScore +=addToScore;
                    updateScore(mScore);

                    //perform check before you update the question
                    if (mQuestionNumber == 5) {
                        Intent i = new Intent(QuizActivity.this, ResultsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("finalScore", mScore);
                        i.putExtras(bundle);
                        QuizActivity.this.finish();
                        startActivity(i);
                    } else {
                        updateQuestion();
                    }
                }
                    else {
                    if (mQuestionNumber == 5) {
                        Intent i = new Intent(QuizActivity.this, ResultsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("FinalScore", mScore);
                        i.putExtras(bundle);
                        QuizActivity.this.finish();
                        startActivity(i);
                    } else {
                        updateQuestion();
                    }
                }
                    addToScore = 2;
            }
        });

        jokerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cmptJoker>0){
                    addToScore = 1;
                    Toast.makeText(getApplicationContext(),
                            mAnswer+"",
                            Toast.LENGTH_LONG).show();
                    cmptJoker --;
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "Jokers épuisés",
                            Toast.LENGTH_LONG).show();
                }

            }
        });




        //Logic for false button
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAnswer == false) {
                    mScore += addToScore;
                    updateScore(mScore);

                    //perform check before you update the question
                    if (mQuestionNumber == 5) {
                        Intent i = new Intent(QuizActivity.this, ResultsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("finalScore", mScore);
                        i.putExtras(bundle);
                        QuizActivity.this.finish();
                        startActivity(i);
                    } else {
                        updateQuestion();
                    }
                }
                else {
                    if (mQuestionNumber == 5) {
                        Intent i = new Intent(QuizActivity.this, ResultsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("finalScore", mScore);
                        i.putExtras(bundle);
                        QuizActivity.this.finish();
                        startActivity(i);
                    } else {
                        updateQuestion();
                    }
                }
                addToScore = 2;
            }
        });

    }

    private void updateQuestion() {

        mImageView.setImageResource(QuizBook.images[indexAlea]);
        mQuestion.setText(QuizBook.questions[indexAlea]);
        mAnswer = QuizBook.answers[indexAlea];
        changeIndex();
    }

    private void updateScore(int point) {
        mScoreView.setText("" + mScore);
    }

    public void clickExit(View view) {
        askToClose();
    }


    @Override
    public void onBackPressed() {
        askToClose();
    }

    private void askToClose (){
        AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
        builder.setMessage("Are you sure you want to quit?");
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void changeIndex(){
            int tmp=0;
            do{
                indexAlea = rand.nextInt(QuizBook.questions.length);
                for(int j=0;j<tabRand.length;j++){
                    if(tabRand[j] == indexAlea){
                        tmp = 1;
                    }else
                        tmp = 0;
                }

            }while(tmp==1);

            tabRand[mQuestionNumber] = indexAlea;
            mQuestionNumber++;
    }
}
