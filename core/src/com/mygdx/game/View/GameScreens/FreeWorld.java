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
    //Boulder Position
    public static final int BOULDER1_X = 170;
    public static final int BOULDER1_Y = 870;
    public static final int BOULDER2_X = 140;
    public static final int BOULDER2_Y = 870;
    //PressingPlate Position
    public static final int PP1_X = 140;
    public static final int PP1_Y = 570;

    public static final int PP2_X = 200;
    public static final int PP2_Y = 570;

    public static final int PP3_X = 140;
    public static final int PP3_Y = 520;

    public static final int PP4_X = 200;
    public static final int PP4_Y = 520;

    public static final int PP5_X = 30;
    public static final int PP5_Y = 10;

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
        Boulder boulder1= new Boulder(this,BOULDER1_X, BOULDER1_Y);
        boulders.add(boulder1);
        Boulder boulder2= new Boulder(this,BOULDER2_X, BOULDER2_Y);
        boulders.add(boulder2);
        PressingPlate pp1= new PressingPlate(this, PP1_X, PP1_Y);
        pps.add(pp1);
        PressingPlate pp2= new PressingPlate(this, PP2_X, PP2_Y);
        pps.add(pp2);
        PressingPlate pp3= new PressingPlate(this, PP3_X, PP3_Y);
        pps.add(pp3);
        PressingPlate pp4= new PressingPlate(this, PP4_X, PP4_Y);
        pps.add(pp4);
    }

    @Override
    public void objectsUpdate(float dt) {
        for(Boulder boulder : boulders)
            boulder.update(dt);
        for(PressingPlate pp : pps)
            pp.update(dt,this);
    }

    @Override
    public void objectsDraw() {
        for(PressingPlate pp : pps)
            pp.draw(game.batch);
        for(Boulder boulder : boulders)
            boulder.draw(game.batch);
    }
}
