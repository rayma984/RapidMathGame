package com.example.rapidmathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Strat strat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void LoadGame(){

    }

    public void additionMode(View view){
        strat = new AddStrat();
        LoadGame();
    }

    public void subtractionMode(View view){
        strat = new SubStrat();
        LoadGame();
    }

    public void multiplicationMode(View view){
        strat = new MultStrat();
        LoadGame();
    }

    public void result(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
