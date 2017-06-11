package com.mygdx.game.View.GameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Controller.Entitys.TileObjects.Door;
import com.mygdx.game.Model.Entitys.DinamicObjects.Boulder;
import com.mygdx.game.Model.Entitys.DinamicObjects.FireGround;
import com.mygdx.game.Model.Entitys.DinamicObjects.MegaPressingPlate;
import com.mygdx.game.Model.Entitys.DinamicObjects.PressingPlate;
import com.mygdx.game.Model.Entitys.DinamicObjects.WayBlocker;
import com.mygdx.game.Model.Entitys.InteractiveObjects.Chest;
import com.mygdx.game.Model.Entitys.InteractiveObjects.Sign;
import com.mygdx.game.Model.Entitys.Items.Heart;
import com.mygdx.game.Model.Entitys.Items.ItemDef;
import com.mygdx.game.Model.Entitys.Items.Jewel;
import com.mygdx.game.Model.Entitys.Items.SpecialItem;
import com.mygdx.game.Model.Entitys.Weapons.Bomb;
import com.mygdx.game.Model.Events.PressingEvent;
import com.mygdx.game.Model.States.GameState;
import com.mygdx.game.Model.Events.WarpEvent;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.Scenes.TextLog;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Utilizador on 10-05-2017.
 */

public class FreeWorld extends GameScreen {

    public FreeWorld(MyGame game, Vector2 vec) {
        super(game,vec);
        Gdx.input.setInputProcessor(logicController.controller.getStage());
    }

    @Override
    protected Class<?> getDescType() {
        return FreeWorld.class;
    }

    @Override
    public String getMapName() {
        return "free_world.tmx";
    }

    @Override
    protected void musicDefine() {
        music = Gdx.audio.newMusic(Gdx.files.internal("Music/hyrule_field_music.mp3"));
        music.setLooping(true);
        music.setVolume(MyGame.MUSIC_VOLUME);
    }

    @Override
    public void objectsDraw() {
        for(WayBlocker wb : logicController.wayblocks)
            wb.draw(game.getBatch());
        for(PressingPlate pp : logicController.pps)
            pp.draw(game.getBatch());
        for(Boulder boulder : logicController.boulders)
            boulder.draw(game.getBatch());
        for(Chest chest : logicController.chests)
            chest.draw(game.getBatch());
        for(Sign sign :logicController.signs)
            sign.draw(game.getBatch());
    }


}
