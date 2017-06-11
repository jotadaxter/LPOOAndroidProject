package com.mygdx.game.View.GameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Model.Entitys.DinamicObjects.Boulder;
import com.mygdx.game.Model.Entitys.DinamicObjects.FireGround;
import com.mygdx.game.Model.Entitys.DinamicObjects.MegaPressingPlate;
import com.mygdx.game.Model.Entitys.DinamicObjects.MovingPlatform;
import com.mygdx.game.Model.Entitys.DinamicObjects.SmashableRock;
import com.mygdx.game.Model.Entitys.DinamicObjects.Spikes;
import com.mygdx.game.Model.Entitys.DinamicObjects.WayBlocker;
import com.mygdx.game.Model.Entitys.InteractiveObjects.Chest;
import com.mygdx.game.MyGame;

/**
 * Created by Utilizador on 20-05-2017.
 */

public class Dungeon1 extends GameScreen{
    public Dungeon1(MyGame game, Vector2 vec) {
        super(game, vec);
        Gdx.input.setInputProcessor(logicController.getController().getStage());
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
        /*for(WayBlocker wb : logicController.getWayblocks())
            wb.draw(game.getBatch());
        for(MegaPressingPlate mpp : logicController.getMpps())
            mpp.draw(game.getBatch());
        for(SmashableRock sm : logicController.getSmashRocks())
            sm.draw(game.getBatch());
        for(FireGround fg : logicController.getFireGrounds())
            fg.draw(game.getBatch());
        for(Spikes spike : logicController.getSpikes())
            spike.draw(game.getBatch());
        for(Chest chest : logicController.getChests())
            chest.draw(game.getBatch());
        for(Boulder boulder : logicController.getBoulders())
            boulder.draw(game.getBatch());
        for(MovingPlatform m : logicController.getMps())
            m.draw(game.getBatch());*/
    }

    @Override
    public String getMapName() {
        return "first_dungeon.tmx";
    }
}
