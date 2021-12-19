package com.example.rapidmathgame;

import java.io.Serializable;
import java.util.Random;

public class MultStrat implements Strat, Serializable {
    Random rand = new Random();
    private int UPPER = 20 + 1;

    private int first;
    private int second;

    public MultStrat(){
        first = GenerateNum();

        second = GenerateNum();
        while(first == second)
            second = GenerateNum(); //ensures first != second
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

        while(num ==0){ //ensure that the int is NOT 0
            num = rand.nextInt(UPPER);
        }
        return num;
    }
}
