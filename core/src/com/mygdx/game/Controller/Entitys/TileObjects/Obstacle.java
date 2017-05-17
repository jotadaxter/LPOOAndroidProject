package com.mygdx.game.Controller.Entitys.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Utilizador on 06-04-2017.
 */

public class Obstacle extends StaticTileObject {
    private String code;

    public Obstacle(GameScreen screen, MapObject object, String code){
        super(screen, object);
        fixture.setUserData(this);
        this.code=code;
    }

   // @Override
    public void warp() {

    }
}
