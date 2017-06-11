package com.mygdx.game.View.GameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Controller.Entitys.DinamicObjects.SmashableRockBody;
import com.mygdx.game.Controller.Entitys.TileObjects.D1TopDoor;
import com.mygdx.game.Model.Entitys.DinamicObjects.Boulder;
import com.mygdx.game.Model.Entitys.DinamicObjects.FireGround;
import com.mygdx.game.Model.Entitys.DinamicObjects.MegaPressingPlate;
import com.mygdx.game.Model.Entitys.DinamicObjects.MovingPlatform;
import com.mygdx.game.Model.Entitys.DinamicObjects.SmashableRock;
import com.mygdx.game.Model.Entitys.DinamicObjects.Spikes;
import com.mygdx.game.Model.Entitys.DinamicObjects.WayBlocker;
import com.mygdx.game.Model.Entitys.InteractiveObjects.Chest;
import com.mygdx.game.Model.Entitys.Items.Heart;
import com.mygdx.game.Model.Entitys.Items.ItemDef;
import com.mygdx.game.Model.Entitys.Items.Jewel;
import com.mygdx.game.Model.Entitys.Items.SpecialItem;
import com.mygdx.game.Model.Events.PressingEvent;
import com.mygdx.game.MyGame;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Utilizador on 20-05-2017.
 */

public class Dungeon1 extends GameScreen{

    public Dungeon1(MyGame game, Vector2 vec) {
        super(game, vec);
        Gdx.input.setInputProcessor(logicController.controller.getStage());
    }

    @Override
    protected Class<?> getDescType() {
        return Dungeon1.class;
    }

    @Override
    protected void musicDefine() {
        music = Gdx.audio.newMusic(Gdx.files.internal("Music/dungeon1_music.mp3"));
        music.setLooping(true);
        music.setVolume(MyGame.MUSIC_VOLUME);
    }

    @Override
    public void objectsDraw() {
        for(WayBlocker wb : logicController.wayblocks)
            wb.draw(game.getBatch());
        for(MegaPressingPlate mpp : logicController.mpps)
            mpp.draw(game.getBatch());
        for(SmashableRock sm : logicController.smashRocks)
            sm.draw(game.getBatch());
        for(FireGround fg : logicController.fireGrounds)
            fg.draw(game.getBatch());
        for(Spikes spike : logicController.spikes)
            spike.draw(game.getBatch());
        for(Chest chest : logicController.chests)
            chest.draw(game.getBatch());
        for(Boulder boulder : logicController.boulders)
            boulder.draw(game.getBatch());
        for(MovingPlatform m :logicController.mps)
            m.draw(game.getBatch());
    }

    @Override
    public String getMapName() {
        return "first_dungeon.tmx";
    }
}
