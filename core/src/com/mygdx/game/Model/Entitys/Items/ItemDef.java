package com.mygdx.game.Model.Entitys.Items;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Utilizador on 09-04-2017.
 */

public class ItemDef {
    public Vector2 position;
    public  Class<?> type;

    public ItemDef(Vector2 position, Class<?> type){
        this.type=type;
        this.position=position;
    }
}
