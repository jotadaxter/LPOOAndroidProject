package com.mygdx.game.Model.Entitys.Hero;

/**
 * Created by Utilizador on 16-05-2017.
 */

public class HeroStats implements java.io.Serializable {
    
    /** The Constant MIN_HEALTH. */
    public static final int MIN_HEALTH = 1;
    
    /** The Constant MAX__HEALTH. */
    public static final int MAX__HEALTH = 10;

    /** The score. */
    private int score;
    
    /** The hearts. */
    private int hearts;
    
    /** The volcano ruby. */
    private boolean volcanoRuby;

    /**
     * Instantiates a new hero stats.
     */
    public HeroStats() {
        this.score=0;
        this.hearts=3;
        this.volcanoRuby=false;
    }

    /**
     * Sets the score.
     *
     * @param score the new score
     */
    //Sets
    public void setScore(int score) {
        this.score = score;
    }
    
    /**
     * Sets the hearts.
     *
     * @param hearts the new hearts
     */
    public void setHearts(int hearts) {
        this.hearts = hearts;
    }

    /**
     * Gets the score.
     *
     * @return the score
     */
    //Gets
    public int getScore() {
        return score;
    }
    
    /**
     * Gets the hearts.
     *
     * @return the hearts
     */
    public int getHearts() {
        return hearts;
    }

    /**
     * Got volcano ruby.
     */
    public void gotVolcanoRuby() {
       volcanoRuby=true;
    }

    /**
     * Checks for volcano ruby.
     *
     * @return true, if successful
     */
    public boolean hasVolcanoRuby(){
        return volcanoRuby;
    }

    /**
     * Display volcano ruby.
     *
     * @return true, if successful
     */
    public boolean displayVolcanoRuby() {
        return volcanoRuby;
    }

    /**
     * Reset stats.
     */
    public void resetStats() {
        this.score=0;
        this.hearts=3;
        this.volcanoRuby=false;
    }
}
