package com.mygdx.game.View.GameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Model.Entitys.DinamicObjects.Boulder;
import com.mygdx.game.Model.Entitys.DinamicObjects.PressingPlate;
import com.mygdx.game.Model.Entitys.DinamicObjects.WayBlocker;
import com.mygdx.game.Model.Entitys.InteractiveObjects.Chest;
import com.mygdx.game.Model.Entitys.InteractiveObjects.Sign;
import com.mygdx.game.MyGame;

/**
 * Created by Utilizador on 10-05-2017.
 */

public class FreeWorld extends GameScreen {

    /**
     * Instantiates a new free world.
     *
     * @param game the game
     * @param vec the vec
     */
    public FreeWorld(MyGame game, Vector2 vec) {
        super(game,vec);
        Gdx.input.setInputProcessor(logicController.getController().getStage());
    }

    /**
     * Gets the desc type.
     *
     * @return the desc type
     */
    @Override
    protected Class<?> getDescType() {
        return FreeWorld.class;
    }

    /**
     * Gets the map name.
     *
     * @return the map name
     */
    @Override
    public String getMapName() {
        return "free_world.tmx";
    }

    /**
     * Music define.
     */
    @Override
    protected void musicDefine() {
        music = Gdx.audio.newMusic(Gdx.files.internal("Music/hyrule_field_music.mp3"));
        music.setLooping(true);
        music.setVolume(MyGame.MUSIC_VOLUME);
    }

    /**
     * Objects draw.
     */
    @Override
    public void objectsDraw() {
        for(WayBlocker wb : logicController.getWayblocks())
            wb.draw(game.getBatch());
        for(PressingPlate pp : logicController.getPps())
            pp.draw(game.getBatch());
        for(Boulder boulder : logicController.getBoulders())
            boulder.draw(game.getBatch());
        for(Chest chest : logicController.getChests())
            chest.draw(game.getBatch());
        for(Sign sign : logicController.getSigns())
            sign.draw(game.getBatch());
    }


}
