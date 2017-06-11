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
    
    /** The states. */
    private Stack<GameState> states;
    
    /** The game. */
    private MyGame game;

    /**
     * Instantiates a new game state manager.
     *
     * @param game the game
     */
    public GameStateManager(MyGame game) {
        this.game = game;
        if (!game.getIsTest()) {
            setStates(new Stack<GameState>());
            getStates().clear();
            getStates().push(new GameState(new MainMenu(game)));
            game.setScreen(getStates().peek().getMenuScreen());
        }
    }

    /**
     * Push.
     *
     * @param state the state
     */
    public void push(GameState state){
        if(state.isGameScreen() && getStates().size()!=0 && !getStates().peek().isMenuScreen())
               getStates().peek().getGameScreen().getMusic().setVolume(0f);
        else if(state.isMenuScreen() && getStates().size()!=0 && !getStates().peek().isMenuScreen())
                getStates().peek().getGameScreen().getMusic().setVolume(0f);
        getStates().push(state);
        if(state.isGameScreen()){
            game.setScreen(getStates().peek().getGameScreen());
            getStates().peek().getGameScreen().getLogicController().getController().reset();
            Gdx.input.setInputProcessor(getStates().peek().getGameScreen().getLogicController().getController().getStage());
            getStates().peek().getGameScreen().getMusic().setVolume(MyGame.MUSIC_VOLUME);
            getStates().peek().getGameScreen().getMusic().play();
        }
        else if(state.isMenuScreen())
            game.setScreen(getStates().peek().getMenuScreen());
    }

    /**
     * Pop.
     */
    public void pop(){
        if(getStates().peek().isGameScreen() && getStates().size()!=0)
                getStates().peek().getGameScreen().getMusic().setVolume(0f);
        getStates().pop();
        if(getStates().peek().isGameScreen()) {
            game.setScreen(getStates().peek().getGameScreen());
            Gdx.input.setInputProcessor(getStates().peek().getGameScreen().getLogicController().getController().getStage());
            getStates().peek().getGameScreen().getLogicController().getController().reset();
            getStates().peek().getGameScreen().getMusic().setVolume(MyGame.MUSIC_VOLUME);
            getStates().peek().getGameScreen().getMusic().play();
        }
    }

    /**
     * Sets the.
     *
     * @param screen the screen
     */
    public void set(GameScreen screen){
        if(getStates().size()!=0 && getStates().peek().isGameScreen())
            getStates().peek().getGameScreen().getMusic().setVolume(0f);
        getStates().pop();
        getStates().push(new GameState(screen));
        game.setScreen(getStates().peek().getGameScreen());
        Gdx.input.setInputProcessor(getStates().peek().getGameScreen().getLogicController().getController().getStage());
        getStates().peek().getGameScreen().getLogicController().getController().reset();
        getStates().peek().getGameScreen().getMusic().play();
    }

    /**
     * Sets the.
     *
     * @param screen the screen
     */
    public void set(MenuScreen screen){
        if(getStates().size()!=0 && getStates().peek().isGameScreen())
            getStates().peek().getGameScreen().getMusic().setVolume(0f);
        getStates().pop();
        getStates().push(new GameState(screen));
        game.setScreen(getStates().peek().getMenuScreen());
    }

    /**
     * Gets the states.
     *
     * @return the states
     */
    public Stack<GameState> getStates() {
        return states;
    }

    /**
     * Sets the states.
     *
     * @param states the new states
     */
    public void setStates(Stack<GameState> states) {
        this.states = states;
    }
}
