package com.mygdx.game.View.GameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Model.Entitys.DinamicObjects.Boulder;
import com.mygdx.game.Model.Entitys.DinamicObjects.PressingPlate;
import com.mygdx.game.Model.Entitys.DinamicObjects.SmashableRock;
import com.mygdx.game.Model.Entitys.DinamicObjects.Spikes;
import com.mygdx.game.Model.Entitys.InteractiveObjects.Sign;
import com.mygdx.game.Model.Entitys.Items.Heart;
import com.mygdx.game.Model.Entitys.Items.ItemDef;
import com.mygdx.game.Model.Entitys.Items.Jewel;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.Scenes.TextLog;
import com.sun.org.apache.bcel.internal.generic.LOOKUPSWITCH;

/**
 * Created by Utilizador on 08-05-2017.
 */

public class DemoScreen extends GameScreen {


    public DemoScreen(MyGame game, Vector2 vec) {
        super(game, vec);
        type= DemoScreen.class;
        Gdx.input.setInputProcessor(logicController.controller.getStage());
    }

    @Override
    protected Class<?> getDescType() {
        return DemoScreen.class;
    }

    @Override
    protected void musicDefine() {
        music = Gdx.audio.newMusic(Gdx.files.internal("Music/tutorial_music.mp3"));
        music.setLooping(true);
        music.setVolume(MyGame.MUSIC_VOLUME);
    }

    @Override
    public String getMapName() {
        return "level1.tmx";
    }

    @Override
    public void objectsDraw() {
        for(SmashableRock sm : logicController.smashRocks)
            sm.draw(game.getBatch());
        for(PressingPlate pp : logicController.pps)
            pp.draw(game.getBatch());
        for(Boulder boulder : logicController.boulders)
            boulder.draw(game.getBatch());
        for(Sign sign :logicController.signs)
            sign.draw(game.getBatch());

    }
}
