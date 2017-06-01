package com.mygdx.game.Model.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.DemoScreen;
import com.mygdx.game.View.GameScreens.FreeWorld;
import com.mygdx.game.View.GameScreens.GameScreen;
import com.mygdx.game.View.GameScreens.TestScreen;
import com.mygdx.game.View.MenuScreens.MainMenu;
import com.mygdx.game.View.MenuScreens.MenuScreen;

import java.util.Stack;

/**
 * Created by Utilizador on 09-05-2017.
 */

public class GameStateManager {
    public Stack<GameState> states;
    private MyGame game;
    public boolean isTest;

    public GameStateManager(MyGame game, boolean isTest){
        this.game=game;
        states= new Stack<GameState>();
        this.isTest=isTest;
        if(!isTest) {
            states.push(new GameState(new MainMenu(game)));
            game.setScreen(states.peek().getMenuScreen());
        }else {
            states.push(new GameState(new TestScreen(game)));
            game.setScreen(states.peek().getGameScreen());
        }

    }

    public void push(GameScreen screen){
        if(states.size()!=0 && states.peek().isGameScreen())
            states.peek().getGameScreen().getMusic().stop();
        states.push(new GameState(screen));
        game.setScreen(states.peek().getGameScreen());
        Gdx.input.setInputProcessor(states.peek().getGameScreen().getController().getStage());
        states.peek().getGameScreen().getController().reset();
        states.peek().getGameScreen().getMusic().play();
    }

    public void push(MenuScreen screen){
       if(states.size()!=0 && states.peek().isMenuScreen())
           //states.peek().getMenuScreen().getMusic().stop();

        states.push(new GameState(screen));
        game.setScreen(states.peek().getMenuScreen());
        //Gdx.input.setInputProcessor(states.peek().getGameScreen().getController().getStage());
        //states.peek().getGameScreen().getController().reset();
        //states.peek().getGameScreen().getMusic().play();
    }

    public void push(GameState state){
        if(state.isGameScreen()){
           if(states.size()!=0)
                states.peek().getGameScreen().getMusic().stop();
        }
        else if(state.isMenuScreen()){
            if(states.size()!=0)
                states.peek().getMenuScreen().getMusic().stop();
        }

        states.push(state);

        if(state.isGameScreen()){
           game.setScreen(states.peek().getGameScreen());
            states.peek().getGameScreen().getController().reset();
            Gdx.input.setInputProcessor(states.peek().getGameScreen().getController().getStage());
            states.peek().getGameScreen().getMusic().play();
        }
        else if(state.isMenuScreen())
            game.setScreen(states.peek().getMenuScreen());
    }

    public void pop(){
        if(states.peek().isGameScreen()){
            if(states.size()!=0)
                states.peek().getGameScreen().getMusic().stop();
        }
        else if(states.peek().isMenuScreen()){
//            if(states.size()!=0)
//                states.peek().getMenuScreen().getMusic().stop();
        }
        states.pop();
        System.out.println(states.peek().getMenuScreen().getClass());
        if(states.peek().isGameScreen()) {
            game.setScreen(states.peek().getGameScreen());
            states.peek().getGameScreen().getMusic().play();
        }
        else if(states.peek().isMenuScreen()) {
            game.setScreen(states.peek().getMenuScreen());
//            states.peek().getMenuScreen().getMusic().play();
        }
//        Gdx.input.setInputProcessor(states.peek().getGameScreen().getController().getStage());
//        states.peek().getGameScreen().getController().reset();

    }

    public void set(GameScreen screen){
        if(states.size()!=0 && states.peek().isGameScreen());
            states.peek().getGameScreen().getMusic().stop();
        states.pop();
        states.push(new GameState(screen));
        game.setScreen(states.peek().getGameScreen());
        Gdx.input.setInputProcessor(states.peek().getGameScreen().getController().getStage());
        states.peek().getGameScreen().getController().reset();
        states.peek().getGameScreen().getMusic().play();
    }

    public void set(MenuScreen screen){
       // if(states.size()!=0 && states.peek().isMenuScreen());
            //states.peek().getMenuScreen().getMusic().stop();
        states.pop();
        states.push(new GameState(screen));
        game.setScreen(states.peek().getMenuScreen());
    }
}
