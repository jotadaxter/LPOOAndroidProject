package com.mygdx.game.Model.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.DemoScreen;
import com.mygdx.game.View.GameScreens.FreeWorld;
import com.mygdx.game.View.GameScreens.GameScreen;
import com.mygdx.game.View.MenuScreens.MainMenu;
import com.mygdx.game.View.MenuScreens.MenuScreen;

import java.util.Stack;

/**
 * Created by Utilizador on 09-05-2017.
 */

public class GameStateManager {
    public Stack<GameState> states;
    private MyGame game;

    public GameStateManager(MyGame game){
        this.game=game;
        states= new Stack<GameState>();
        states.push(new GameState(new MainMenu(game)));
        game.setScreen(states.peek().getMenuScreen());
    }

    public void push(GameScreen screen){
        states.push(new GameState(screen));
        game.setScreen(states.peek().getGameScreen());
        Gdx.input.setInputProcessor(states.peek().getGameScreen().getController().getStage());
        states.peek().getGameScreen().getController().reset();
    }

    public void push(MenuScreen screen){
        states.push(new GameState(screen));
        game.setScreen(states.peek().getMenuScreen());
        Gdx.input.setInputProcessor(states.peek().getGameScreen().getController().getStage());
        states.peek().getGameScreen().getController().reset();
    }

    public void push(GameState state){
        states.push(state);
        if(state.isGameScreen())
            game.setScreen(states.peek().getGameScreen());
        else if(state.isMenuScreen())
            game.setScreen(states.peek().getMenuScreen());
        Gdx.input.setInputProcessor(states.peek().getGameScreen().getController().getStage());
        states.peek().getGameScreen().getController().reset();
    }

    public void pop(){
        states.pop();
        if(states.peek().isGameScreen())
            game.setScreen(states.peek().getGameScreen());
        else if(states.peek().isMenuScreen())
            game.setScreen(states.peek().getMenuScreen());
        Gdx.input.setInputProcessor(states.peek().getGameScreen().getController().getStage());
        states.peek().getGameScreen().getController().reset();
    }

    public void set(GameScreen screen){
        states.pop();
        states.push(new GameState(screen));
        game.setScreen(states.peek().getGameScreen());
        Gdx.input.setInputProcessor(states.peek().getGameScreen().getController().getStage());
        states.peek().getGameScreen().getController().reset();
    }

    public void set(MenuScreen screen){
        states.pop();
        states.push(new GameState(screen));
        game.setScreen(states.peek().getMenuScreen());
        Gdx.input.setInputProcessor(states.peek().getGameScreen().getController().getStage());
        states.peek().getGameScreen().getController().reset();
    }
}
