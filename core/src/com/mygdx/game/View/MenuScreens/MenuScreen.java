package com.mygdx.game.View.MenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.MyGame;

/**
 * Created by Utilizador on 09-05-2017.
 */

public abstract class MenuScreen implements Screen {
    public static final int MENU_WIDTH =2560;
    public static final int MENU_HEIGHT =1600;

    protected FillViewport viewPort;
    protected OrthographicCamera gameCam;
    protected MyGame game;
    private Music music;


    public MenuScreen(MyGame game) {
        this.game=game;
        gameCam= new OrthographicCamera();
        viewPort= new FillViewport(MENU_WIDTH/3,MENU_HEIGHT/3, gameCam);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public Music getMusic() {
        return music;
    }
}
