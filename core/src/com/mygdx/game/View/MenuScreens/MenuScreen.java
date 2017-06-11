package com.mygdx.game.View.MenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
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

// TODO: Auto-generated Javadoc
/**
 * Created by Utilizador on 09-05-2017.
 */

public abstract class MenuScreen implements Screen {
    
    /** The Constant MENU_WIDTH. */
    public static final int MENU_WIDTH =2560;
    
    /** The Constant MENU_HEIGHT. */
    public static final int MENU_HEIGHT =1600;

    /** The view port. */
    protected FillViewport viewPort;
    
    /** The game cam. */
    protected OrthographicCamera gameCam;
    
    /** The game. */
    protected MyGame game;

    /**
     * Instantiates a new menu screen.
     *
     * @param game the game
     */
    public MenuScreen(MyGame game) {
        this.game=game;
        gameCam= new OrthographicCamera();
        viewPort= new FillViewport(MENU_WIDTH/3,MENU_HEIGHT/3, gameCam);
    }

    /**
     * Show.
     */
    @Override
    public void show() {

    }

    /**
     * Render.
     *
     * @param delta the delta
     */
    @Override
    public void render(float delta) {

    }

    /**
     * Resize.
     *
     * @param width the width
     * @param height the height
     */
    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height);
    }

    /**
     * Pause.
     */
    @Override
    public void pause() {

    }

    /**
     * Resume.
     */
    @Override
    public void resume() {

    }

    /**
     * Hide.
     */
    @Override
    public void hide() {

    }

    /**
     * Dispose.
     */
    @Override
    public void dispose() {
    }
}
