package com.example.rapidmathgame;

import java.io.Serializable;

public interface Strat extends Serializable {
    public String getProblem(); //the string version of the problem for display
    public int getAnswer();     //generate the answer to the current question
    public void nextProblem();  //generate the next problem
    public String getMode();    //output the String version of the mode
}
