package com.mygdx.game.Controller.Entitys.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.mygdx.game.Model.Events.WarpEvent;
import com.mygdx.game.View.GameScreens.DemoScreen;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Utilizador on 09-05-2017.
 */

public class Door extends StaticTileObject {
    private Integer id;

    public Door(GameScreen screen, MapObject object, Integer id) {
        super(screen, object);
        fixture.setUserData(this);
        this.id=id;
    }

    public void warp() {
        if(screen.getClass()==DemoScreen.class){
            screen.getGame().gsm.pop();
        }
        else {
            for (WarpEvent warpEvent : screen.getWarpEvents())
                if (warpEvent.id == this.id) {
                    screen.getGame().gsm.push(warpEvent.travelPoint);
                }
        }
    }

    public int getId() {
        return id;
    }
}
