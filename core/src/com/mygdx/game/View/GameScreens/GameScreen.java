package com.mygdx.game.View.GameScreens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Controller.Controller;
import com.mygdx.game.Model.Entitys.DinamicObjects.PressingPlate;
import com.mygdx.game.Model.Entitys.DinamicObjects.Spikes;
import com.mygdx.game.Model.Entitys.Items.Key;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.Scenes.Hud;
import com.mygdx.game.Model.Entitys.Items.Heart;
import com.mygdx.game.Model.Entitys.Items.Item;
import com.mygdx.game.Model.Entitys.Items.ItemDef;
import com.mygdx.game.Model.Entitys.Items.Jewel;
import com.mygdx.game.Model.Entitys.DinamicObjects.Boulder;
import com.mygdx.game.Model.Entitys.Hero.Hero;
import com.mygdx.game.Controller.WorldTools.WorldContactListener;
import com.mygdx.game.Controller.WorldTools.WorldCreator;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Jotadaxter on 04/04/2017.
 */

public abstract class GameScreen implements Screen{
    //Rupee Info
    protected static final int GREEN_RUPEE =1;
    protected static final int BLUE_RUPEE =5;
    protected static final int RED_RUPEE =20;
    protected static final int BIG_GREEN_RUPEE =50;
    protected static final int BIG_BLUE_RUPEE =100;
    protected static final int BIG_RED_RUPEE =200;

    protected MyGame game;
    protected TextureAtlas atlas;
    protected OrthographicCamera gameCam;
    protected Viewport viewPort;
    protected Hud hud;
    protected Stage stage;
    protected float accumulator;

    //Controllers
    protected Controller controller;

    //Tiled Map Variables
    protected TmxMapLoader mapLoader;
    protected TiledMap tiledMap;
    protected OrthogonalTiledMapRenderer renderer;

    //Box2d Variables
    protected World world;
    protected Box2DDebugRenderer b2dr;

    //Sprites
    protected Hero player;
    protected Boulder boulder;
    protected Spikes spikes;
    protected PressingPlate pp;
    protected Key key;

    protected Array<Item> items;
    protected LinkedBlockingQueue<ItemDef> itemsToSpawn;

    public GameScreen(MyGame game) {

        atlas=new TextureAtlas("link_and_objects.pack");
        this.game=game;
        float ratio = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());
        gameCam= new OrthographicCamera(MyGame.VIEWPORT_WIDTH , MyGame.VIEWPORT_WIDTH);
        viewPort= new FitViewport(MyGame.VIEWPORT_WIDTH*MyGame.PIXEL_TO_METER,MyGame.VIEWPORT_HEIGHT*MyGame.PIXEL_TO_METER, gameCam);

        //Map Load
        String mapName = getMapName();
        mapLoader = new TmxMapLoader();
        tiledMap = mapLoader.load(mapName);
        renderer = new OrthogonalTiledMapRenderer(tiledMap, 1*MyGame.PIXEL_TO_METER);
        gameCam.position.set(viewPort.getWorldWidth()/2, viewPort.getWorldHeight()/2, 0);
        hud= new Hud(game.batch, this);
        controller= new Controller(game.batch,this);

        //box2d
        world= new World(new Vector2(0,0), true);
        b2dr= new Box2DDebugRenderer();
        new WorldCreator(this);

        //Items
        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();

        //Sprites
        player=new Hero(this);
        objectLoad();

        //Contact Listener
        world.setContactListener(new WorldContactListener());


    }

    protected abstract void objectLoad();

    public void update(float dt){
        handleSpawningItems();
        player.getHeroBody().InputUpdate(controller,dt);

        //takes 1 step in the physics simulation (60 times per second)
        float frameTime = Math.min(dt, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/60f) {
            world.step(1/60f, 6, 2);
            accumulator -= 1/60f;
        }

        //Sprites Update
        player.update(dt);
        objectsUpdate(dt);


        //Items Update
        for(Item item : items)
            item.update(dt, player);

        //ajust the camera to follow the player
        gameCam.position.x=player.getHeroBody().b2body.getPosition().x;
        gameCam.position.y=player.getHeroBody().b2body.getPosition().y;
        hud.update(dt,this);
        gameCam.update();
        renderer.setView(gameCam);
    }

    protected abstract void objectsUpdate(float dt);


    public TextureAtlas getAtlas(){
        return atlas;
    }

    public void spawnItem(ItemDef idef){
        itemsToSpawn.add(idef);
    }

    public void handleSpawningItems(){
        if(!itemsToSpawn.isEmpty()){
            ItemDef idef= itemsToSpawn.poll();
            if(idef.type == Jewel.class){
                items.add(new Jewel(BLUE_RUPEE, this, idef.position.x, idef.position.y));
            }
            else if(idef.type== Heart.class){
                items.add(new Heart(this, idef.position.x, idef.position.y));
            }
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        //Clear the screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Render the game map
        renderer.render();

        //Render Box2DDebugLines
        b2dr.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();

        objactsDraw();
        player.draw(game.batch);

        for(Item item : items)
            item.draw(game.batch);
        game.batch.end();

        //HUD Rendering
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        hud.heartStage.draw();

        //Controller
        if(Gdx.app.getType() == Application.ApplicationType.Android)
            controller.draw();
    }

    protected abstract void objactsDraw();

    public TiledMap getMap(){
        return tiledMap;
    }

    public World getWorld(){
        return world;
    }

    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height);
        controller.resize(width,height);
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
        tiledMap.dispose();
        world.dispose();
        b2dr.dispose();
        renderer.dispose();
        hud.dispose();
    }

    public Hero getHero(){
        return this.player;
    }

    public abstract String getMapName();

}
