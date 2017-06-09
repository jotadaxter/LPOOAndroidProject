package com.mygdx.game.Model.Entitys.Hero;

/**
 * Created by Utilizador on 16-05-2017.
 */

public class HeroStats {
    public static final int MIN_HEALTH = 1;
    public static final int MAX__HEALTH = 10;

    private int score;
    private int hearts;
    private boolean volcanoRuby;

    public HeroStats() {
        this.score=0;
        this.hearts=3;
        this.volcanoRuby=false;
    }

    //Sets
    public void setScore(int score) {
        this.score = score;
    }
    public void setHearts(int hearts) {
        this.hearts = hearts;
    }

    //Gets
    public int getScore() {
        return score;
    }
    public int getHearts() {
        return hearts;
    }

    public void gotVolcanoRuby() {
       volcanoRuby=true;
    }

    public boolean hasVolcanoRuby(){
        return volcanoRuby;
    }

    public boolean displayVolcanoRuby() {
        return volcanoRuby;
    }

    public void resetStats() {
        this.score=0;
        this.hearts=3;
        this.volcanoRuby=false;
    }
}
