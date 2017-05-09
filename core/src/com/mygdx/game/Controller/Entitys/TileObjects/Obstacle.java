package com.mygdx.game.Controller.Entitys.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Utilizador on 06-04-2017.
 */

public class Obstacle extends StaticTileObject {
    public Obstacle(GameScreen screen, MapObject object){
        super(screen, object);

    }

    @Override
    public void fisicFilter(FixtureDef fdef) {
        fdef.filter.categoryBits= MyGame.DEFAULT_BIT;
        fdef.filter.maskBits = MyGame.ITEM_BIT
                | MyGame.DEFAULT_BIT
                | MyGame.SPIKES_BIT
                | MyGame.HERO_BIT
                | MyGame.BOULDER_BIT
                | MyGame.PRESSING_PLATE_BIT;

    }

   // @Override
    public void warp() {

    }
}
