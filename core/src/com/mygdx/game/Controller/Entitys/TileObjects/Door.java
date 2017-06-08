package com.mygdx.game.Controller.Entitys.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.game.Model.Events.WarpEvent;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.DemoScreen;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Utilizador on 09-05-2017.
 */

public class Door extends StaticTileObject {
    private Integer id;
    private Sound sound;

    public Door(GameScreen screen, MapObject object, Integer id) {
        super(screen, object);
        fixture.setUserData(this);
        sound= Gdx.audio.newSound(Gdx.files.internal("Sounds/door.wav"));
        this.id=id;
        setCategoryFilter(MyGame.WARP_OBJECT);
    }

    public void warp() {
        if(screen.getClass()==DemoScreen.class){
            sound.setVolume(sound.play(),MyGame.SOUND_VOLUME);
            screen.getGame().gsm.pop();
        }
        else {
            for (WarpEvent warpEvent : screen.getWarpEvents())
                if (warpEvent.id == this.id) {
                    sound.setVolume(sound.play(),MyGame.SOUND_VOLUME);
                    screen.getGame().gsm.push(warpEvent.travelPoint);
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
