package com.mygdx.game.Controller.Entitys.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.Model.Events.WarpEvent;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.DemoScreen;

/**
 * Created by Utilizador on 09-05-2017.
 */

public class Door extends StaticTileObject {
    private Integer id;
    private Sound sound;

    public Door(LogicController logicController, MapObject object, Integer id) {
        super(logicController, object);
        fixture.setUserData(this);
        sound= Gdx.audio.newSound(Gdx.files.internal("Sounds/door.wav"));
        this.id=id;
        setCategoryFilter(MyGame.WARP_OBJECT);
    }

    public void warp() {
        if(logicController.getScreenType() ==DemoScreen.class){
            sound.play(MyGame.SOUND_VOLUME);
            logicController.getGame().getGsm().pop();
        }
        else {
            for (WarpEvent warpEvent : logicController.getWarpEvents())
                if (warpEvent.getId() == this.id) {
                    sound.play(MyGame.SOUND_VOLUME);
                    logicController.getGame().getGsm().push(warpEvent.getTravelPoint());
                }
        }
    }

    public int getId() {
        return id;
    }

    @Override
    protected boolean setSensor() {
        return false;
    }
}
