package com.example.rapidmathgame;

import java.io.Serializable;
import java.util.Random;

public class ManyStrat implements Serializable, Strat {

    Strat strat;

    AddStrat add = new AddStrat();
    SubStrat sub = new SubStrat();
    MultStrat mult = new MultStrat();
    private final int modes = 3;

    public ManyStrat(){
        nextProblem();
    }

    private void setAdd(){
        strat = add;
    }
    private void setSub(){
        strat = sub;
    }

    @Override
    public String getProblem() {
        return strat.getProblem();
    }

    @Override
    public int getAnswer() {
        return strat.getAnswer();
    }

    @Override
    public void nextProblem() {
        //which mode to get the question from
        Random rand = new Random();
        int gen = rand.nextInt(modes);
        switch (gen){
            case 0:
                strat = mult;
                break;
            case 1:
                strat = add;
                break;
            case 2:
                strat = sub;
                break;
        }

        strat.nextProblem();
    }

    @Override
    public String toString(){
        return getProblem();
    }
}
