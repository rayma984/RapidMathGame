package com.example.rapidmathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class GamePage extends AppCompatActivity {

    Strat strat;
    int answer;
    TextView lblProb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        //load in the mode label
        String mode = (String) getIntent().getStringExtra("mode");
        TextView lblMode = findViewById(R.id.lblMode);
        lblMode.setText(mode);

        //set the first question
        strat = (Strat) getIntent().getSerializableExtra("STRAT");
        lblProb = findViewById(R.id.lblProblem);
        lblProb.setText(strat.getProblem());
        answer = strat.getAnswer();
    }

    public void loadQuestion(){

    }
}