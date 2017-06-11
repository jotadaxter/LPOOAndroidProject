package com.mygdx.game.Model.States;

import com.badlogic.gdx.Screen;
import com.mygdx.game.View.GameScreens.GameScreen;
import com.mygdx.game.View.MenuScreens.MenuScreen;

/**
 * Created by Utilizador on 09-05-2017.
 */

public class GameState {
    
    /** The game screen. */
    private GameScreen gameScreen;
    
    /** The menu screen. */
    private MenuScreen menuScreen;
    
    /** The is game screen. */
    private boolean isGameScreen;
    
    /** The is menu screen. */
    private boolean isMenuScreen;

    /**
     * Instantiates a new game state.
     *
     * @param gamescreen the gamescreen
     */
    public GameState(GameScreen gamescreen){
        this.gameScreen=gamescreen;
        this.menuScreen=null;
        isGameScreen=true;
        isMenuScreen=false;
    }

    /**
     * Instantiates a new game state.
     *
     * @param menuscreen the menuscreen
     */
    public GameState(MenuScreen menuscreen){
        this.menuScreen=menuscreen;
        this.gameScreen=null;
        isGameScreen=false;
        isMenuScreen=true;
    }

    /**
     * Gets the game screen.
     *
     * @return the game screen
     */
    public GameScreen getGameScreen(){
        return gameScreen;
    }
    
    /**
     * Gets the menu screen.
     *
     * @return the menu screen
     */
    public MenuScreen getMenuScreen(){
        return menuScreen;
    }
    
    /**
     * Checks if is game screen.
     *
     * @return true, if is game screen
     */
    public boolean isGameScreen() {
        return isGameScreen;
    }
    
    /**
     * Checks if is menu screen.
     *
     * @return true, if is menu screen
     */
    public boolean isMenuScreen() {
        return isMenuScreen;
    }



}
