package com.mygdx.game.View.Screens;

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
import com.mygdx.game.MyGame;
import com.mygdx.game.View.Scenes.Hud;
import com.mygdx.game.Model.Entitys.Items.Heart;
import com.mygdx.game.Model.Entitys.Items.Item;
import com.mygdx.game.Model.Entitys.Items.ItemDef;
import com.mygdx.game.Model.Entitys.Items.Jewel;
import com.mygdx.game.Model.Entitys.DinamicObjects.Boulder;
import com.mygdx.game.Model.Entitys.Hero.Hero;
import com.mygdx.game.View.WorldTools.WorldContactListener;
import com.mygdx.game.View.WorldTools.WorldCreator;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Jotadaxter on 04/04/2017.
 */

public class MyScreen implements Screen{
    //Rupee Info
    public static final int GREEN_RUPEE =1;
    public static final int BLUE_RUPEE =5;
    public static final int RED_RUPEE =20;
    public static final int BIG_GREEN_RUPEE =50;
    public static final int BIG_BLUE_RUPEE =100;
    public static final int BIG_RED_RUPEE =200;

    private MyGame game;
    private TextureAtlas atlas;
    private OrthographicCamera gameCam;
    private Viewport viewPort;
    private Hud hud;
    private Stage stage;
    private float accumulator;

    //Controllers
    private Controller controller;

    //Tiled Map Variables
    private TmxMapLoader mapLoader;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d Variables
    private World world;
    private Box2DDebugRenderer b2dr;

    //Sprites
    private Hero player;
    private Boulder boulder;

    private Array<Item> items;
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;

    public MyScreen(MyGame game) {
        atlas=new TextureAtlas("link_and_objects.pack");
        this.game=game;
        float ratio = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());
        gameCam= new OrthographicCamera(MyGame.VIEWPORT_WIDTH , MyGame.VIEWPORT_WIDTH);
        viewPort= new FitViewport(MyGame.VIEWPORT_WIDTH*MyGame.PIXEL_TO_METER,MyGame.VIEWPORT_HEIGHT*MyGame.PIXEL_TO_METER, gameCam);
        mapLoader = new TmxMapLoader();
        tiledMap = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(tiledMap, 1*MyGame.PIXEL_TO_METER);
        gameCam.position.set(viewPort.getWorldWidth()/2, viewPort.getWorldHeight()/2, 0);
        hud= new Hud(game.batch, this);
        controller= new Controller(game.batch,this);

        //box2d
        world= new World(new Vector2(0,0), true);
        b2dr= new Box2DDebugRenderer();

        new WorldCreator(this);

        //Sprites
        player=new Hero(this);
        boulder= new Boulder(this);

        //Items
        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();
        spawnItem(new ItemDef(new Vector2(150,150), Jewel.class));
        spawnItem(new ItemDef(new Vector2(200,150), Heart.class));

        //Contact Listener
        world.setContactListener(new WorldContactListener());


    }

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
        boulder.update(dt);

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
        player.draw(game.batch);
        boulder.draw(game.batch);
        for(Item item : items)
            item.draw(game.batch);
        game.batch.end();

        //HUD Rendering
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        //Controller
        if(Gdx.app.getType() == Application.ApplicationType.Android)
            controller.draw();
    }

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
}
