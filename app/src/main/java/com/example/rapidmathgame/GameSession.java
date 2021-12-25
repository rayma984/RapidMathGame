package com.example.rapidmathgame;

import java.io.Serializable;
import java.util.ArrayList;

public class GameSession implements Serializable {
    ArrayList<String> questions = new ArrayList<>();
    ArrayList<Boolean> results = new ArrayList<>();
    ArrayList<Integer> answers = new ArrayList<>();
    Player player;
    int score;

    public GameSession(){
        score = 0;
    }

    public void answer(String question, int answer, boolean correct){

        if (correct)
            score += 10;
        else
            score -= 10;

        questions.add(question);
        results.add(correct);
        answers.add(answer);
    }

    public void makePlayer(String name){
        player = new Player(name, score);
    }

    public String getPlayerName(){return player.name;}
    public int getPlayerScore(){return score;}

}
