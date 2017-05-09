package com.mygdx.game.Model.States;

import com.badlogic.gdx.Screen;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.DemoScreen;
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
        states= new Stack<GameState>();
        states.push(new GameState(new MainMenu(game)));
        game.setScreen(states.peek().getMenuScreen());

    }

    public void push(DemoScreen screen){
        states.push(new GameState(screen));
        game.setScreen(states.peek().getGameScreen());
    }


    public void pop(){
        states.pop();
    }

    public void set(GameState state){
        states.pop();
        states.push(state);
    }

}
