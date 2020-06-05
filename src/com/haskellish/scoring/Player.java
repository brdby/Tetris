package com.haskellish.scoring;

public class Player {

    private long score = 0;

    public long addScore(long score){
        return this.score += score;
    }

    public long getScore() {
        return score;
    }
}
