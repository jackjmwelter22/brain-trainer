package com.jackwelter.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button goButton;
    Button firstButton;
    Button secondButton;
    Button thirdButton;
    Button fourthButton;
    Button playAgainButton;
    TextView resultTextView;
    TextView scoreTextView;
    TextView sumTextView;
    TextView timerTextView;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    Random rand = new Random();
    int locationOfCorrectAnswer;
    int score = 0;
    int numQuestions = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = (Button) findViewById(R.id.goButton);
        firstButton = (Button) findViewById(R.id.firstButton);
        secondButton = (Button) findViewById(R.id.secondButton);
        thirdButton = (Button) findViewById(R.id.thirdButton);
        fourthButton = (Button) findViewById(R.id.fourthButton);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        sumTextView = (TextView) findViewById(R.id.sumTextView);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);

    }

    public void goButton(View view){
        goButton.setVisibility(View.INVISIBLE);
        firstButton.setVisibility(View.VISIBLE);
        secondButton.setVisibility(View.VISIBLE);
        thirdButton.setVisibility(View.VISIBLE);
        fourthButton.setVisibility(View.VISIBLE);
        sumTextView.setVisibility(View.VISIBLE);
        resultTextView.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
        timerTextView.setVisibility(View.VISIBLE);

        playAgain(findViewById(R.id.timerTextView));
    }

    public void generateQuestion(){
        int a = rand.nextInt(21);   //Generate random number between 0 and 20
        int b = rand.nextInt(21);

        sumTextView.setText(Integer.toString(a) +  " + " + Integer.toString(b));

        locationOfCorrectAnswer = rand.nextInt(4);
        int incorrectAnswer;
        answers.clear();

        for(int i = 0; i < 4; i++){
            if (i == locationOfCorrectAnswer){
                answers.add(a+b);
            }else{
                incorrectAnswer = rand.nextInt(41);
                while(incorrectAnswer == a + b){        //insure incorrect answer isn't correct
                    incorrectAnswer = rand.nextInt(41);
                }
                answers.add(incorrectAnswer);
            }
        }
        firstButton.setText(Integer.toString(answers.get(0)));
        secondButton.setText(Integer.toString(answers.get(1)));
        thirdButton.setText(Integer.toString(answers.get(2)));
        fourthButton.setText(Integer.toString(answers.get(3)));
    }

    public void chooseAnswer(View view){

        numQuestions++;

        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){
            score++;
            resultTextView.setText("Correct!");
        }else{
            resultTextView.setText("Wrong!");
        }

        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numQuestions));

        generateQuestion();
    }

    public void playAgain(View view){
        score = 0;
        numQuestions = 0;

        timerTextView.setText("30s");
        scoreTextView.setText("0/0");
        resultTextView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);
        firstButton.setEnabled(true);
        secondButton.setEnabled(true);
        thirdButton.setEnabled(true);
        fourthButton.setEnabled(true);

        generateQuestion();

        new CountDownTimer(30100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }
            @Override
            public void onFinish() {
                timerTextView.setText("0s");
                resultTextView.setText("Your Score: " + scoreTextView.getText());
                playAgainButton.setVisibility(View.VISIBLE);
                firstButton.setEnabled(false);
                secondButton.setEnabled(false);
                thirdButton.setEnabled(false);
                fourthButton.setEnabled(false);
            }
        }.start();
    }
}
