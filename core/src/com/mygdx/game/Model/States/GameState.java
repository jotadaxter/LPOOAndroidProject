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

    public GameState(GameScreen gamescreen){
        this.gameScreen=gamescreen;
        this.menuScreen=null;
    }

    public GameState(MenuScreen menuscreen){
        this.menuScreen=menuscreen;
        this.gameScreen=null;
    }

    public GameScreen getGameScreen(){
        return gameScreen;
    }
    public MenuScreen getMenuScreen(){
        return menuScreen;
    }

}
