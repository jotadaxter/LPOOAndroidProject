package com.mygdx.game.Model.States;

import com.badlogic.gdx.Screen;
import com.mygdx.game.View.GameScreens.GameScreen;
import com.mygdx.game.View.MenuScreens.MenuScreen;

/**
 * Created by Utilizador on 09-05-2017.
 */

public class GameState {
    private GameScreen gameScreen;
    private MenuScreen menuScreen;
    private boolean isGameScreen;
    private boolean isMenuScreen;

    public GameState(GameScreen gamescreen){
        this.gameScreen=gamescreen;
        this.menuScreen=null;
        isGameScreen=true;
        isMenuScreen=false;
    }

    public GameState(MenuScreen menuscreen){
        this.menuScreen=menuscreen;
        this.gameScreen=null;
        isGameScreen=false;
        isMenuScreen=true;
    }

    public GameScreen getGameScreen(){
        return gameScreen;
    }
    public MenuScreen getMenuScreen(){
        return menuScreen;
    }
    public boolean isGameScreen() {
        return isGameScreen;
    }
    public boolean isMenuScreen() {
        return isMenuScreen;
    }


}
