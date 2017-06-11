package com.mygdx.game.View.GameScreens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Controller.Entitys.TileObjects.D1TopDoor;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.Model.Entitys.Weapons.Bomb;
import com.mygdx.game.Model.States.GameState;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.MenuScreens.GameCompleted;
import com.mygdx.game.View.MenuScreens.GameMenu;
import com.mygdx.game.View.MenuScreens.GameOver;
import com.mygdx.game.View.Scenes.Hud;
import com.mygdx.game.Model.Entitys.Items.Item;
import com.mygdx.game.View.Scenes.TextLog;

/**
 * Created by Jotadaxter on 04/04/2017.
 */

public abstract class GameScreen implements Screen{
    
    /** The game. */
    protected MyGame game;
    
    /** The game cam. */
    protected OrthographicCamera gameCam;
    
    /** The view port. */
    protected Viewport viewPort;
    
    /** The hud. */
    protected Hud hud;
    
    /** The end timer. */
    protected float endTimer;
    
    /** The logic controller. */
    protected LogicController logicController;
    
    /** The renderer. */
    //Tiled Map Variables
    protected OrthogonalTiledMapRenderer renderer;
    
    /** The type. */
    protected Class<?> type;
    
    /** The b 2 dr. */
    //Box2d Variables
    protected Box2DDebugRenderer b2dr;
    
    /** The music. */
    protected Music music;

    /**
     * Instantiates a new game screen.
     *
     * @param game the game
     * @param vec the vec
     */
    public GameScreen(MyGame game, Vector2 vec) {
        this.game=game;
        type=getDescType();
        logicController= new LogicController(game, vec, getType());
        gameCam= new OrthographicCamera(MyGame.VIEWPORT_WIDTH , MyGame.VIEWPORT_WIDTH);
        viewPort= new FitViewport(MyGame.VIEWPORT_WIDTH*MyGame.PIXEL_TO_METER,MyGame.VIEWPORT_HEIGHT*MyGame.PIXEL_TO_METER, gameCam);
        mapDefine();
        musicDefine();
    }

    /**
     * Gets the desc type.
     *
     * @return the desc type
     */
    protected abstract Class<?> getDescType();

    /**
     * Map define.
     */
    private void mapDefine() {
        String mapName = "Maps/" + getMapName();
        logicController.defineMap(mapName);
        renderer = new OrthogonalTiledMapRenderer(logicController.getTiledMap(), 1*MyGame.PIXEL_TO_METER);
        gameCam.position.set(viewPort.getWorldWidth()/2, viewPort.getWorldHeight()/2, 0);
        hud= new Hud(game, this);
        //box2d
        b2dr= new Box2DDebugRenderer();
        endTimer=-1;
    }
    
    /**
     * Music define.
     */
    protected abstract void musicDefine();

    /**
     * Update.
     *
     * @param dt the dt
     */
    public void update(float dt){
        logicController.update(dt);
        gameOptions();
        gameCam.position.x= logicController.getPlayer().getHeroBody().getBody().getPosition().x;
        gameCam.position.y= logicController.getPlayer().getHeroBody().getBody().getPosition().y;
        hud.update();
        gameCam.update();
        renderer.setView(gameCam);
        endUpdate(dt);
    }

    /**
     * End update.
     *
     * @param dt the dt
     */
    private void endUpdate(float dt) {
        if(game.getHeroStats().getHearts()<=0){
            game.getGsm().push(new GameState(new GameOver(game)));
            game.getHeroStats().resetStats();
        }
        if(game.getHeroStats().hasVolcanoRuby() && endTimer<0)
            endTimer=0;
        if(endTimer>=0)
            endTimer+=dt;
        if(endTimer>=10){
            game.getGsm().push(new GameState(new GameCompleted(game)));
        }
    }

    /**
     * Game options.
     */
    private void gameOptions() {
        if(logicController.getController().isOptionsPressed()){
            game.getGsm().push(new GameState(new GameMenu(game)));
            logicController.getController().setOptionsPressed(false);
        }
    }

    /**
     * Show.
     */
    @Override
    public void show() {}

    /**
     * Render.
     *
     * @param delta the delta
     */
    @Override
    public void render(float delta) {
        update(delta);
        clearScreen();
        //Render the game map
        renderer.render();
        //Render Box2DDebugLines
        b2dr.render(logicController.getWorld(), gameCam.combined);
        game.getBatch().setProjectionMatrix(gameCam.combined);
        gameDraw();
        hudDraw();
        //Controller
        if(Gdx.app.getType() == Application.ApplicationType.Android)
            logicController.getController().draw();
    }

    /**
     * Hud draw.
     */
    private void hudDraw() {
        //HUD Rendering
        game.getBatch().setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().draw();
        hud.getHeartStage().draw();
        for(TextLog tlog: logicController.getTextlogs()) {
            if(logicController.getSigns().get(tlog.getId()).getIsOpen())
                tlog.getStage().draw();
        }
    }

    /**
     * Clear screen.
     */
    private void clearScreen() {
        //Clear the screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    /**
     * Game draw.
     */
    private void gameDraw() {
        game.getBatch().begin();
        objectsDraw();
        if(logicController.getPlayer().getThrowBomb()){
            for(Bomb bombs: logicController.getPlayer().getBombs())
                bombs.draw(game.getBatch());
        }
        logicController.getPlayer().draw();
        for(D1TopDoor top : logicController.getTopDoors())
            top.draw(game.getBatch());
        for(Item item : logicController.getItems())
            item.draw(game.getBatch());
        game.getBatch().end();
    }

    /**
     * Objects draw.
     */
    public abstract void objectsDraw();

    /**
     * Resize.
     *
     * @param width the width
     * @param height the height
     */
    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height);
        logicController.getController().resize(width,height);
    }

    /**
     * Pause.
     */
    @Override
    public void pause() {}

    /**
     * Resume.
     */
    @Override
    public void resume() {}

    /**
     * Hide.
     */
    @Override
    public void hide() {}

    /**
     * Dispose.
     */
    @Override
    public void dispose() {
        b2dr.dispose();
        renderer.dispose();
        hud.dispose();
    }

    /**
     * Gets the map name.
     *
     * @return the map name
     */
    public abstract String getMapName();

    /**
     * Gets the game.
     *
     * @return the game
     */
    public MyGame getGame() {
        return game;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public Class<?> getType() {
        return type;
    }

    /**
     * Gets the music.
     *
     * @return the music
     */
    public Music getMusic() {
        return music;
    }

    /**
     * Gets the logic controller.
     *
     * @return the logic controller
     */
    public LogicController getLogicController() {
        return logicController;
    }
}