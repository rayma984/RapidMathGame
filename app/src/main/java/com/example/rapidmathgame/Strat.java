package com.example.rapidmathgame;

import java.io.Serializable;

public interface Strat extends Serializable {
    public String getProblem();
    public int getAnswer();
    public void nextProblem();
    public String getMode();
}
