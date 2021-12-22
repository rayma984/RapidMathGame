package com.example.rapidmathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class GamePage extends AppCompatActivity {

    Strat strat;
    int answer;
    TextView lblProb;
    Button btnSubmit;
    private final int milli_to_sec = 1000;
    TextView lblTime;
    EditText txtInput;
    TextView lblMode;
    GameSession session;
    TextView lblquestionNumber;
    private int questionNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        btnSubmit = findViewById(R.id.btnAnswer);
        txtInput = findViewById(R.id.txtInput);
        lblquestionNumber = findViewById(R.id.lblQustionNumber);
        lblquestionNumber.setText("Question 0:");

        //load in the mode label
        String mode = getIntent().getStringExtra("mode");
        lblMode = findViewById(R.id.lblMode);
        lblMode.setText(mode);


        //set the first question
        strat = (Strat) getIntent().getSerializableExtra("STRAT");
        lblProb = findViewById(R.id.lblProblem);
        loadQuestion();
        session = new GameSession();

        //set the timer

        lblTime = findViewById(R.id.lblTimeLeft);
        lblTime.setText("TIME");

        new CountDownTimer(milli_to_sec * 120, milli_to_sec){
            @Override
            public void onTick(long millisUntilFinished) {
                NumberFormat f = new DecimalFormat("00");
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                lblTime.setText(f.format(min) + ":" + f.format(sec));
            }
            @Override
            public void onFinish() { //when time runs out, stop the game
                Toast toast = Toast.makeText(getApplicationContext(), "Time's Up!", Toast.LENGTH_SHORT);
                toast.show();

                //send it to the next activity
            }
        }.start();
    }

    public void loadQuestion(){
        strat.nextProblem();
        lblProb.setText(strat.getProblem());
        answer = strat.getAnswer();
    }

    public void submitAnswer(View view){
        String input = txtInput.getText().toString();
        int submission = Integer.parseInt(input);

        //check the answer and add it to the session
        boolean correct = (submission == answer);
        session.answer(strat.getProblem(), submission, correct);

        //clear the textview and load the next question
        txtInput.setText("");
        //new question
        loadQuestion();
        questionNumber++;
        lblquestionNumber.setText("Question " + questionNumber + ":");
    }

    public void debug(String msg){
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}