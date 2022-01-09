package com.example.rapidmathgame;

import java.io.Serializable;
import java.util.ArrayList;

public class GameSession implements Serializable {
    ArrayList<String> questions = new ArrayList<>();
    ArrayList<Boolean> results = new ArrayList<>();
    ArrayList<Integer> answers = new ArrayList<>();
    Player player;
    int score;
    int totalQs;
    int correctQs;
    private int time;

    public GameSession(){
        score = 0;
    }

    //add the question data and user response to game session fields and update score
    public void answer(String question, int answer, boolean correct){

        if (correct) {
            score += 10;
            correctQs++;
        }
        else
            score -= 10;

        totalQs++;

        questions.add(question);
        results.add(correct);
        answers.add(answer);
    }

    public void makePlayer(String name){
        player = new Player(name, score);
    }

    public String getPlayerName(){return player.name;}

    public int getPlayerScore(){return score;}

    public void setTime(int time) {
        this.time = time;
    }
    public int getTime(){
        return time;
    }

    @Override
    public String toString(){
        return getPlayerScore() + " " + getPlayerName();
    }
}
