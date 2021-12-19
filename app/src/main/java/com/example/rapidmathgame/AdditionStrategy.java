package com.example.rapidmathgame;

import java.util.Random;

public class AdditionStrategy implements Strategy{
    Random rand = new Random();
    private int UPPER = 200;

    private int first;
    private int second;

    public AdditionStrategy(){
        first = GenerateNum();

        second = GenerateNum();
        while(first == second)
            second = GenerateNum(); //ensures first != second
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
        int num = rand.nextInt(UPPER + 1); //generate the random int

        while(num ==0){ //ensure that the int is NOT 0
            num = rand.nextInt(UPPER + 1);
        }
        return num;
    }
}
