package com.example.rapidmathgame;

import java.io.Serializable;
import java.util.Random;

public class AddStrat implements Strat, Serializable {
    Random rand = new Random();
    private int UPPER = 100 + 1;

    private int first;
    private int second;

    public AddStrat(){
        nextProblem();
    }

    @Override
    public String getProblem(){
        return first + " + " + second + " = ?";
    }

    @Override
    public int getAnswer(){
        return first + second;
    }

    public int GenerateNum(){
        int num = rand.nextInt(UPPER); //generate the random int

        while(num ==0 || num == second){ //ensure that the int is NOT 0, or a repeat number
            num = rand.nextInt(UPPER);
        }
        return num;
    }

    @Override
    public void nextProblem(){
        first = GenerateNum();
        second = GenerateNum();
    }

    @Override
    public String getMode() {
        return "Addition";
    }

    @Override
    public String toString(){
        return getProblem();
    }
}
