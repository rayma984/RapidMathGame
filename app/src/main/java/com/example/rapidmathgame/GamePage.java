package com.example.rapidmathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GamePage extends AppCompatActivity {

    Strat strat;
    int answer;
    TextView lblProb;
    EditText txtInput;
    GameSession session;
    TextView lblquestionNumber;
    private int questionNumber = 1;
    private boolean quit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        //set the time
        Button btnSubmit;
        final int milli_to_sec = 1000;
        int durationSecs = getIntent().getIntExtra("time", 30);

        //set the first question and the game session
        strat = (Strat) getIntent().getSerializableExtra("STRAT");
        lblProb = findViewById(R.id.lblProblem);
        loadQuestion();
        session = new GameSession();
        session.setTime(durationSecs);

        //set up the labels and other views
        btnSubmit = findViewById(R.id.btnAnswer);
        txtInput = findViewById(R.id.txtInput);
        lblquestionNumber = findViewById(R.id.lblQustionNumber);
        lblquestionNumber.setText("Question " + questionNumber + ":");
        TextView lblMode = findViewById(R.id.lblMode);
        lblMode.setText(strat.getMode());

        //set the timer
        TextView lblTime = findViewById(R.id.lblTimeLeft);
        lblTime.setText("TIME");

        new CountDownTimer(milli_to_sec * durationSecs, milli_to_sec){
            //let the player see the time view a textview
            @Override
            public void onTick(long millisUntilFinished) {
                NumberFormat f = new DecimalFormat("00");
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                lblTime.setText(f.format(min) + ":" + f.format(sec));
            }
            @Override
            public void onFinish() { //when time runs out, stop the game
                if(!quit) {//if quit were to be true, we would not want to be sent to post game
                    debug("Time's Up!");
                    //disable the button and send us to the next activity
                    btnSubmit.setEnabled(false);
                    sendToPost();
                }
            }
        }.start();
    }

    //method to end the game and allow the user to submit score in the next activity
    public void sendToPost(){
        Intent intent = new Intent(this, PostGame.class);
        intent.putExtra("session", session);
        intent.putExtra("mode", strat.getMode());
        startActivity(intent);
        finish();
    }

    //get and display the next question
    public void loadQuestion(){
        strat.nextProblem();
        lblProb.setText(strat.getProblem());
        answer = strat.getAnswer();
    }

    //submit an answer to the game session
    public void submitAnswer(View view){
        String input = txtInput.getText().toString();

        //Do not allow for empty inputs
        if(isEmpty(input)){
            debug("Empty answers are not allowed!");
        } else if (isValid(input)) {
            int submission = Integer.parseInt(input);

            //check the answer and add it to the session
            boolean correct = (submission == answer);
            session.answer(strat.getProblem(), submission, correct);

            //clear the textview and load the next question
            txtInput.setText("");
            loadQuestion();
            questionNumber++;
            lblquestionNumber.setText("Question " + questionNumber + ":");
        } else {
            //in the case that the submitted answer is of an invalid format
            debug("Invalid Answer");
            txtInput.setText("");
        }
    }

    //display a short toast message
    public void debug(String msg){
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    //prematurely end the game session
    public void Quit(View view){
        Intent intent = new Intent(this, MainActivity.class);
        quit = true;
        debug("Quit Session");
        startActivity(intent);
        finish();
    }

    //------------------------------------------------Helper functions for input validation
    private boolean isValid(String input){
        Pattern p = Pattern.compile("-{0,1}[0-9]+");
        Matcher m = p.matcher(input);
        return m.matches();
    }

    private boolean isEmpty(String input){
        return (input == "" || input == null || input.length() <= 0);
    }
}