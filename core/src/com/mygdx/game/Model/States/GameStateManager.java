package com.mygdx.game.Model.States;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;
import com.mygdx.game.View.MenuScreens.MainMenu;
import com.mygdx.game.View.MenuScreens.MenuScreen;

import java.util.Stack;

/**
 * Created by Utilizador on 09-05-2017.
 */

public class GameStateManager {
    private Stack<GameState> states;
    private MyGame game;

    public GameStateManager(MyGame game){
        this.game=game;
        setStates(new Stack<GameState>());
        getStates().clear();
        getStates().push(new GameState(new MainMenu(game)));
        game.setScreen(getStates().peek().getMenuScreen());
    }

    public void push(GameState state){
        if(state.isGameScreen() && getStates().size()!=0 && !getStates().peek().isMenuScreen())
               getStates().peek().getGameScreen().getMusic().setVolume(0f);
        else if(state.isMenuScreen() && getStates().size()!=0 && !getStates().peek().isMenuScreen())
                getStates().peek().getGameScreen().getMusic().setVolume(0f);
        getStates().push(state);
        if(state.isGameScreen()){
            game.setScreen(getStates().peek().getGameScreen());
            getStates().peek().getGameScreen().getController().reset();
            Gdx.input.setInputProcessor(getStates().peek().getGameScreen().getController().getStage());
            getStates().peek().getGameScreen().getMusic().setVolume(MyGame.MUSIC_VOLUME);
            getStates().peek().getGameScreen().getMusic().play();
        }
        else if(state.isMenuScreen())
            game.setScreen(getStates().peek().getMenuScreen());
    }

    public void pop(){
        if(getStates().peek().isGameScreen() && getStates().size()!=0)
                getStates().peek().getGameScreen().getMusic().setVolume(0f);
        getStates().pop();
        if(getStates().peek().isGameScreen()) {
            game.setScreen(getStates().peek().getGameScreen());
            Gdx.input.setInputProcessor(getStates().peek().getGameScreen().getController().getStage());
            getStates().peek().getGameScreen().getController().reset();
            getStates().peek().getGameScreen().getMusic().setVolume(MyGame.MUSIC_VOLUME);
            getStates().peek().getGameScreen().getMusic().play();
        }
    }

    public void set(GameScreen screen){
        if(getStates().size()!=0 && getStates().peek().isGameScreen())
            getStates().peek().getGameScreen().getMusic().setVolume(0f);
        getStates().pop();
        getStates().push(new GameState(screen));
        game.setScreen(getStates().peek().getGameScreen());
        Gdx.input.setInputProcessor(getStates().peek().getGameScreen().getController().getStage());
        getStates().peek().getGameScreen().getController().reset();
        getStates().peek().getGameScreen().getMusic().play();
    }

    public void set(MenuScreen screen){
        if(getStates().size()!=0 && getStates().peek().isGameScreen())
            getStates().peek().getGameScreen().getMusic().setVolume(0f);
        getStates().pop();
        getStates().push(new GameState(screen));
        game.setScreen(getStates().peek().getMenuScreen());
    }

    public Stack<GameState> getStates() {
        return states;
    }

    public void setStates(Stack<GameState> states) {
        this.states = states;
    }
}
