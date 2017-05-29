package com.mygdx.game.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Jotadaxter on 22/04/2017.
 */

public class Controller {
    public static final int VIEWPORT_WIDTH=240;
    public static final int VIEWPORT_HEIGHT=160;
    public static final int BTN_WIDTH = 20;
    public static final int BTN_HEIGTH = 20;
    public static final int BTN_AB_WIDTH = 50;
    public static final int BTN_AB_HEIGHT = 50;

    private MyGame game;
    private FitViewport viewport;
    private Stage stage;
    private boolean upPressed, downPressed, leftPressed,rightPressed, aPressed, bPressed, escPressed;//z-a,x-b
    private OrthographicCamera cam;
    private Image downImg;
    private Image upImg;
    private Image rightImg;
    private Image leftImg;
    private Image aImg;
    private Image bImg;

    public Controller(MyGame game){
        this.game=game;
        cam = new OrthographicCamera();
        viewport = new FitViewport(VIEWPORT_WIDTH,VIEWPORT_HEIGHT,cam);
        stage= new Stage(viewport,game.batch);
        addKeyDownListener();
        addKeyUpListener();
        Gdx.input.setInputProcessor(stage);
        imageLoad();
        tableConfig();
    }

    private void tableConfig() {
        Table table = new Table();
        table.left().bottom();
        table.add();
        table.add(upImg).size(upImg.getWidth(),upImg.getHeight());
        table.add();
        table.row().pad(5,5,5,5);
        table.add(leftImg).size(leftImg.getWidth(),leftImg.getHeight());
        table.add();
        table.add(rightImg).size(rightImg.getWidth(),rightImg.getHeight());
        table.row().padBottom(5);
        table.add();
        table.add(downImg).size(downImg.getWidth(),downImg.getHeight());
        table.add();
        stage.addActor(table);
    }

    private void addKeyUpListener() {
        stage.addListener(new InputListener(){
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                keyUpDefine(keycode);
                return true;
            }
        });
    }
    public void keyUpDefine(int keycode) {
        if(keycode==Input.Keys.UP)
            upPressed = false;
        else if(keycode==Input.Keys.DOWN)
            downPressed = false;
        else if(keycode==Input.Keys.LEFT)
            leftPressed = false;
        else if(keycode==Input.Keys.RIGHT)
            rightPressed = false;
        else if(keycode==Input.Keys.X)
            bPressed = false;
        else if(keycode==Input.Keys.Z)
            aPressed = false;
        else if(keycode==Input.Keys.ESCAPE)
            escPressed = false;
    }

    private void addKeyDownListener() {
        stage.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                keyDowntrueDefine(keycode);
                return true;
            }
        });
    }

    public void keyDowntrueDefine(int keycode) {
        if(keycode==Input.Keys.UP)
            upPressed = true;
        else if(keycode==Input.Keys.DOWN)
            downPressed = true;
        else if(keycode==Input.Keys.LEFT)
            leftPressed = true;
        else if(keycode==Input.Keys.RIGHT)
            rightPressed = true;
        else if(keycode==Input.Keys.X)
            bPressed = true;
        else if(keycode==Input.Keys.Z)
            aPressed = true;
        else if(keycode==Input.Keys.ESCAPE)
            escPressed = true;
    }

    private void imageLoad() {
        downImg = new Image(game.assetManager.get("Buttons/down_arrow.png", Texture.class));
        imageConfig(downImg);
        upImg =new Image(game.assetManager.get("Buttons/up_arrow.png", Texture.class));
        imageConfig(upImg);
        rightImg =new Image(game.assetManager.get("Buttons/right_arrow.png", Texture.class));
        imageConfig(rightImg);
        leftImg = new Image(game.assetManager.get("Buttons/left_arrow.png", Texture.class));
        imageConfig(leftImg);
        aImg =new Image(game.assetManager.get("Buttons/a_button.png", Texture.class));
        abConfig(aImg);
        bImg =new Image(game.assetManager.get("Buttons/b_button.png", Texture.class));
        abConfig(bImg);
    }

    private void abConfig(Image img) {
        img.setSize(BTN_AB_WIDTH,BTN_AB_HEIGHT);
        img.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                aPressed=true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                aPressed=false;
            }
        });
    }

    private void imageConfig(Image image) {
        image.setSize(BTN_WIDTH,BTN_HEIGTH);
        image.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                downPressed=true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                downPressed=false;
            }
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                downPressed=true;
            }
        });
    }

    public void draw(){
        stage.draw();
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isaPressed() {
        return aPressed;
    }

    public boolean isbPressed() {
        return bPressed;
    }

    public boolean isEscPressed() {
        return escPressed;
    }

    public void resize(int width, int height){
        viewport.update(width,height);
    }

    public Stage getStage() {
        return stage;
    }

    public void reset() {
        upPressed = false;
        downPressed= false;
        leftPressed= false;
        rightPressed= false;
        aPressed= false;
        bPressed= false;
        escPressed=false;
    }
}
