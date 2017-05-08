package com.mygdx.game.View.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Controller.Controller;
import com.mygdx.game.Controller.WorldTools.WorldContactListener;
import com.mygdx.game.Controller.WorldTools.WorldCreator;
import com.mygdx.game.Model.Entitys.DinamicObjects.Boulder;
import com.mygdx.game.Model.Entitys.DinamicObjects.Spikes;
import com.mygdx.game.Model.Entitys.Hero.Hero;
import com.mygdx.game.Model.Entitys.Items.Heart;
import com.mygdx.game.Model.Entitys.Items.Item;
import com.mygdx.game.Model.Entitys.Items.ItemDef;
import com.mygdx.game.Model.Entitys.Items.Jewel;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.Scenes.Hud;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Utilizador on 08-05-2017.
 */

public class MainMenu implements Screen{
    public static final int VIEWPORT_WIDTH =2560;//GameBoy Advance settings
    public static final int VIEWPORT_HEIGHT =1600;

    private FillViewport viewPort;
    private OrthographicCamera gameCam;
    private MyGame game;
    private Stage stage;

    private Texture texture;
    private Texture title;
    private Texture arcadetex;
    private ImageButton arcadeButton;

    public MainMenu(MyGame game) {
        this.game=game;
        gameCam= new OrthographicCamera();
        viewPort= new FillViewport(VIEWPORT_WIDTH/3,VIEWPORT_HEIGHT/3, gameCam);
        stage= new Stage(viewPort,game.batch);
        texture = new Texture(Gdx.files.internal("main_menu.jpg"));
        title = new Texture(Gdx.files.internal("game_title.png"));
        arcadetex =new Texture(Gdx.files.internal("arcade_button.png"));

        Drawable drawable = new TextureRegionDrawable(new TextureRegion(arcadetex));
        arcadeButton = new ImageButton(drawable);
        arcadeButton.setPosition(300,300);
        stage.addActor(arcadeButton);
        Gdx.input.setInputProcessor(stage);
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

        game.batch.begin();
        game.batch.draw(texture, 0,0,VIEWPORT_WIDTH/3,VIEWPORT_HEIGHT/3);
        game.batch.draw(title, 180,400);
        game.batch.end();
        stage.draw();


        if(arcadeButton.isPressed()){
            game.setScreen(new MyScreen(game));
        }
    }

    private void update(float delta) {


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
}
