package com.mygdx.game.View.GameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Controller.Entitys.TileObjects.Door;
import com.mygdx.game.Model.Entitys.DinamicObjects.Boulder;
import com.mygdx.game.Model.Entitys.DinamicObjects.PressingPlate;
import com.mygdx.game.Model.Entitys.DinamicObjects.Spikes;
import com.mygdx.game.Model.Entitys.Items.Heart;
import com.mygdx.game.Model.Entitys.Items.ItemDef;
import com.mygdx.game.Model.Entitys.Items.Jewel;
import com.mygdx.game.Model.States.GameState;
import com.mygdx.game.Model.States.WarpEvent;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.MenuScreens.MainMenu;

/**
 * Created by Utilizador on 10-05-2017.
 */

public class FreeWorld extends GameScreen {
    //Hero Info
    public static final int POSX = 30;
    public static final int POSY = 30;

    public static final int DOOR_ID=1;

    public FreeWorld(MyGame game) {
        super(game, POSX, POSY);
        type= FreeWorld.class;
        warpEvents.add(new WarpEvent(DOOR_ID,Door.class, new GameState(new DemoScreen(game))));
        Gdx.input.setInputProcessor(controller.getStage());
    }

    @Override
    public String getMapName() {
        return "free_world.tmx";
    }

    @Override
    public void objectLoad() {

    }

    @Override
    public void objectsUpdate(float dt) {

    }

    @Override
    public void objectsDraw() {

    }
}
