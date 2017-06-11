package com.mygdx.game.Model.Events;

import com.mygdx.game.Model.States.GameState;

// TODO: Auto-generated Javadoc
/**
 * Created by Utilizador on 10-05-2017.
 */

public class WarpEvent {
    
    /** The id. */
    private int id;
    
    /** The type. */
    private Class<?> type;//door or stairs
    
    /** The travel point. */
    private GameState travelPoint;//next screen

    /**
     * Instantiates a new warp event.
     *
     * @param id the id
     * @param type the type
     * @param gs the gs
     */
    public WarpEvent(int id, Class<?> type, GameState gs){
        this.setType(type);
        this.setId(id);
        this.setTravelPoint(gs);
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public Class<?> getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    public void setType(Class<?> type) {
        this.type = type;
    }

    /**
     * Gets the travel point.
     *
     * @return the travel point
     */
    public GameState getTravelPoint() {
        return travelPoint;
    }

    /**
     * Sets the travel point.
     *
     * @param travelPoint the new travel point
     */
    public void setTravelPoint(GameState travelPoint) {
        this.travelPoint = travelPoint;
    }
}
