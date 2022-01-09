package com.example.rapidmathgame;

import java.io.Serializable;
import java.util.Random;

public class MultStrat implements Strat, Serializable {
    Random rand = new Random();
    private int UPPER = 20 + 1;

    private int first;
    private int second;

    public MultStrat(){
        nextProblem();
    }

    @Override
    public String getProblem(){
        return first + " x " + second + " = ?";
    }

    @Override
    public int getAnswer(){
        return first * second;
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
        return "Multiplication";
    }

    @Override
    public String toString(){
        return getProblem();
    }
}
