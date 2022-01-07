package com.example.rapidmathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Strat strat;
    Spinner timeMode;
    private int timeSelected = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeMode = (Spinner) findViewById(R.id.spTime);
        timeMode.setOnItemSelectedListener(this);
    }
    //here, the player will choose their playtime
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        switch(pos){
            case 0:
                timeSelected = 30;
                break;
            case 1:
                timeSelected = 60;
                break;
            case 2:
                timeSelected = 120;
                break;
        }
    }
    public void onNothingSelected(AdapterView<?> parent) {
        timeSelected = 30; //30s is the default time
    }


    public void LoadGame(String mode){
        Intent intent = new Intent(this, GamePage.class);
        intent.putExtra("mode", mode);
        intent.putExtra("STRAT", strat);
        intent.putExtra("time", timeSelected);
        startActivity(intent);
        finish();
    }

    public void additionMode(View view){
        strat = new AddStrat();
        LoadGame(getString(R.string.Addition));
    }

    public void subtractionMode(View view){
        strat = new SubStrat();
        LoadGame(getString(R.string.Subtraction));
    }

    public void multiplicationMode(View view){
        strat = new MultStrat();
        LoadGame(getString(R.string.Multiplication));
    }

    public void debug(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void viewScores(View view){
        Intent intent = new Intent(this, ViewScores.class);
        startActivity(intent);
    }

    public void wipeScores(View view){
        File file = new File(getApplicationContext().getFilesDir(), getString(R.string.filename));
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            debug("No Scores on Record");
        }
    }

    public void Exit(View view){
        finish();
        System.exit(0);
    }
}
