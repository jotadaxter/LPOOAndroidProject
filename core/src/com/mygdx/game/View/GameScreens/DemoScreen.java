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
 * Created by Utilizador on 08-05-2017.
 */

public class DemoScreen extends GameScreen {
    //Hero Info
    public static final int POSX = 247;
    public static final int POSY = 35;

    public static final int DOOR_ID=2;

    public DemoScreen(MyGame game) {
        super(game, POSX, POSY);
        type= DemoScreen.class;
        Gdx.input.setInputProcessor(controller.getStage());

    }

    @Override
    public void objectLoad() {
        boulder= new Boulder(this);
        spikes= new Spikes(this);
        spikes= new Spikes(this);
        pp= new PressingPlate(this);

        //Items
        spawnItem(new ItemDef(new Vector2(150,150), Jewel.class));
        spawnItem(new ItemDef(new Vector2(200,150), Heart.class));
    }

    @Override
    public String getMapName() {
        return "level1.tmx";
    }

    @Override
    public void objectsUpdate(float dt) {
        boulder.update(dt);
        spikes.update(dt);
        spikes.update(dt);
        pp.update(dt, this);
    }

    @Override
    public void objectsDraw() {
        spikes.draw(game.batch);
        pp.draw(game.batch);
        boulder.draw(game.batch);
    }
}
