package com.mygdx.game.Model.Entitys.Items;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Utilizador on 09-04-2017.
 */

public class ItemDef {
    
    /** The position. */
    public Vector2 position;
    
    /** The type type. */
    public Class<?> type;
    
    /** The val. */
    public int val;

    /**
     * Instantiates a new item def.
     *
     * @param position the position
     * @param type the type
     */
    public ItemDef(Vector2 position, Class<?> type){
        this.type=type;
        this.position=position;
    }

    /**
     * Sets the val.
     *
     * @param val the new val
     */
    public void setVal(int val){
        this.val=val;
    }
}
