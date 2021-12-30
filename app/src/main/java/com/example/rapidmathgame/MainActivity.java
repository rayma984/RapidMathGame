package com.example.rapidmathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    Strat strat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void LoadGame(String mode){
        Intent intent = new Intent(this, GamePage.class);
        intent.putExtra("mode", mode);
        intent.putExtra("STRAT", strat);
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
}
