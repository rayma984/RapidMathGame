package com.example.rapidmathgame;

import java.util.ArrayList;

public class GameSession {
    ArrayList<String> questions = new ArrayList<>();
    ArrayList<Boolean> results = new ArrayList<>();
    Player player;
    int score;

    public GameSession(){
        score = 0;
    }

    public void correctAnswer(){
        score += 10;
        
    }


}
