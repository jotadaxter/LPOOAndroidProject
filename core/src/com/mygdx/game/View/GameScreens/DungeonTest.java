package com.mygdx.game.View.GameScreens;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Model.Entitys.DinamicObjects.MovingPlatform;
import com.mygdx.game.MyGame;

/**
 * Created by Utilizador on 20-05-2017.
 */

public class DungeonTest extends GameScreen{
    //Hero Info
    public static final int POSX = 8+16*16;
    public static final int POSY = 8+5;

    public DungeonTest(MyGame game) {
        super(game, POSX, POSY);
        Gdx.input.setInputProcessor(controller.getStage());
    }

    @Override
    public void objectLoad() {
        movingPlatform= new MovingPlatform(this, 16+16*16,16+16*2);
    }

    @Override
    public void objectsUpdate(float dt) {
        movingPlatform.update(dt);
    }

    @Override
    public void handleSpawningItems() {

    }

    @Override
    public void objectsDraw() {
        movingPlatform.draw(game.batch);
    }

    @Override
    public String getMapName() {
        return "platform_test.tmx";
    }
}
