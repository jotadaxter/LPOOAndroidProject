package com.mygdx.game.Controller.Entitys.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.Model.States.GameState;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.DemoScreen;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Utilizador on 09-05-2017.
 */

public class Door extends StaticTileObject {
    public Door(GameScreen screen, MapObject object) {
        super(screen, object);
    }

    @Override
    public void fisicFilter(FixtureDef fdef) {
        fdef.filter.categoryBits= MyGame.WARP_OBJECT;
        fdef.filter.maskBits = MyGame.ITEM_BIT
                | MyGame.DEFAULT_BIT
                | MyGame.SPIKES_BIT
                | MyGame.HERO_BIT
                | MyGame.BOULDER_BIT
                | MyGame.PRESSING_PLATE_BIT;

    }

  //  @Override
    public void warp() {
       // screen.getGame().gameStateManager.states.push(new GameState(new DemoScreen(screen.getGame())));

    }
}
