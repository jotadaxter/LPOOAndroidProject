package com.mygdx.game.Model.Events;

import com.mygdx.game.Model.States.GameState;

/**
 * Created by Utilizador on 10-05-2017.
 */

public class WarpEvent {
    private int id;
    private Class<?> type;//door or stairs
    private GameState travelPoint;//next screen

    public WarpEvent(int id, Class<?> type, GameState gs){
        this.setType(type);
        this.setId(id);
        this.setTravelPoint(gs);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public GameState getTravelPoint() {
        return travelPoint;
    }

    public void setTravelPoint(GameState travelPoint) {
        this.travelPoint = travelPoint;
    }
}
