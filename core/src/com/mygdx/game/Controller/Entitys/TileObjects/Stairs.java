package com.mygdx.game.Controller.Entitys.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.Model.States.GameState;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.DemoScreen;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Utilizador on 09-05-2017.
 */

public class Stairs extends StaticTileObject {
    public Stairs(GameScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
    }

    //@Override
    public void warp() {

    }
}
