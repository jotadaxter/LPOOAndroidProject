package com.mygdx.game.Model.States;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Utilizador on 10-05-2017.
 */

public class WarpEvent {
    public int id;
    public Class<?> type;//door or stairs
    public GameState travelPoint;//next screen

    public WarpEvent(int id, Class<?> type, GameState gs){
        this.type=type;
        this.id=id;
        this.travelPoint=gs;
    }
}
