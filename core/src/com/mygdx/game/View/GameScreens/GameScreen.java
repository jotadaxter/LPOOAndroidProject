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
    protected MyGame game;
    protected OrthographicCamera gameCam;
    protected Viewport viewPort;
    protected Hud hud;
    protected float endTimer;
    protected LogicController logicController;
    //Tiled Map Variables
    protected OrthogonalTiledMapRenderer renderer;
    protected Class<?> type;
    //Box2d Variables
    protected Box2DDebugRenderer b2dr;
    protected Music music;

    public GameScreen(MyGame game, Vector2 vec) {
        this.game=game;
        type=getDescType();
        logicController= new LogicController(game, vec, getType());
        gameCam= new OrthographicCamera(MyGame.VIEWPORT_WIDTH , MyGame.VIEWPORT_WIDTH);
        viewPort= new FitViewport(MyGame.VIEWPORT_WIDTH*MyGame.PIXEL_TO_METER,MyGame.VIEWPORT_HEIGHT*MyGame.PIXEL_TO_METER, gameCam);
        mapDefine();
        musicDefine();
    }

    protected abstract Class<?> getDescType();

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
    protected abstract void musicDefine();

    public void update(float dt){
        logicController.update(dt);
        gameOptions();
        //ajust the camera to follow the player
        gameCam.position.x= logicController.getPlayer().getHeroBody().getBody().getPosition().x;
        gameCam.position.y= logicController.getPlayer().getHeroBody().getBody().getPosition().y;
        hud.update();
        gameCam.update();
        renderer.setView(gameCam);
        endUpdate(dt);
    }

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

    private void gameOptions() {
        if(logicController.getController().isOptionsPressed()){
            game.getGsm().push(new GameState(new GameMenu(game)));
            logicController.getController().setOptionsPressed(false);
        }
    }

    @Override
    public void show() {}

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

    private void hudDraw() {
        //HUD Rendering
        game.getBatch().setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().draw();
        hud.getHeartStage().draw();
        /*for(TextLog tlog: logicController.getTextlogs()) {
            if(logicController.getSigns().get(tlog.getId()).getIsOpen())
                tlog.getStage().draw();
        }*/
    }

    private void clearScreen() {
        //Clear the screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void gameDraw() {
        game.getBatch().begin();
        objectsDraw();
        if(logicController.getPlayer().getThrowBomb()){
            for(Bomb bombs: logicController.getPlayer().getBombs())
                bombs.draw(game.getBatch());
        }
        logicController.getPlayer().draw();
       /* for(D1TopDoor top : logicController.getTopDoors())
            top.draw(game.getBatch());
        for(Item item : logicController.getItems())
            item.draw(game.getBatch());*/
        game.getBatch().end();
    }

    public abstract void objectsDraw();

    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height);
        logicController.getController().resize(width,height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        b2dr.dispose();
        renderer.dispose();
        hud.dispose();
    }

    public abstract String getMapName();

    public MyGame getGame() {
        return game;
    }

    public Class<?> getType() {
        return type;
    }

    public Music getMusic() {
        return music;
    }

    public LogicController getLogicController() {
        return logicController;
    }
}