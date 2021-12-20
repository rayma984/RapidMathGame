package com.example.rapidmathgame;

import java.io.Serializable;

public class Player implements Serializable {
    String name;
    int score;

    public Player(String name, int score){
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString(){
        return name + ": " + score;
    }
}
