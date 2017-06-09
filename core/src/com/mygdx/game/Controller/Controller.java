package com.mygdx.game.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGame;

/**
 * Created by Jotadaxter on 22/04/2017.
 */

public class Controller {
    public static final int VIEWPORT_WIDTH=240;
    public static final int VIEWPORT_HEIGHT=160;
    public static final int BTN_WIDTH = 20;
    public static final int BTN_HEIGHT = 20;
    public static final int BTN_AB_WIDTH = 50;
    public static final int BTN_AB_HEIGHT = 50;

    private MyGame game;
    private FitViewport viewport;
    private Stage stage;
    private boolean upPressed, downPressed, leftPressed,rightPressed, aPressed, bPressed, escPressed, optionsPressed;//z-a,x-b
    private OrthographicCamera cam;
    private Image downImg;
    private Image upImg;
    private Image rightImg;
    private Image leftImg;
    private Image aImg;
    private Image bImg;
    private Image escImg;
    private Image optionsImg;

    public Controller(MyGame game){
        this.game=game;
        cam = new OrthographicCamera();
        viewport = new FitViewport(VIEWPORT_WIDTH,VIEWPORT_HEIGHT,cam);
        stage= new Stage(viewport,game.getBatch());
        addKeyDownListener();
        addKeyUpListener();
        Gdx.input.setInputProcessor(stage);
        imageLoad();
        tableConfig();
        abConfig();
    }

    private void abConfig() {
        stage.addActor(escImg);
        stage.addActor(optionsImg);
        stage.addActor(aImg);
        stage.addActor(bImg);
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
        else if(keycode==Input.Keys.ENTER){
            optionsPressed=false;
        }
    }

    private void addKeyDownListener() {
        stage.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                keyDownDefine(keycode);
                return true;
            }
        });
    }

    public void keyDownDefine(int keycode) {
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
        else if(keycode==Input.Keys.ENTER){
            optionsPressed=true;
        }
    }

    private void imageLoad() {
        downImg = new Image(game.getAssetManager().get("Buttons/down_arrow.png", Texture.class));
        btnDownConfig(downImg);
        upImg =new Image(game.getAssetManager().get("Buttons/up_arrow.png", Texture.class));
        btnUpConfig(upImg);
        rightImg =new Image(game.getAssetManager().get("Buttons/right_arrow.png", Texture.class));
        btnRightConfig(rightImg);
        leftImg = new Image(game.getAssetManager().get("Buttons/left_arrow.png", Texture.class));
        btnLeftConfig(leftImg);
        aImg =new Image(game.getAssetManager().get("Buttons/a_button.png", Texture.class));
        aConfig(aImg);
        bImg =new Image(game.getAssetManager().get("Buttons/b_button.png", Texture.class));
        bConfig(bImg);
        escImg =new Image(game.getAssetManager().get("Buttons/esc_button.png", Texture.class));
        escConfig(escImg);
        optionsImg =new Image(game.getAssetManager().get("Buttons/options_menu.png", Texture.class));
        optionsConfig(optionsImg);
    }

    private void escConfig(Image img) {
        img.setSize(20,20);
        img.setPosition(VIEWPORT_WIDTH/2+10,VIEWPORT_HEIGHT-20);
        img.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                escPressed=true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                escPressed=false;
            }
        });
    }

    private void optionsConfig(Image img) {
        img.setSize(20,20);
        img.setPosition(VIEWPORT_WIDTH/2+10+20,VIEWPORT_HEIGHT-20);
        img.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                optionsPressed=true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                optionsPressed=false;
            }
        });
    }

    private void aConfig(Image img) {
        img.setSize(BTN_AB_WIDTH,BTN_AB_HEIGHT);
        img.setPosition(VIEWPORT_WIDTH-BTN_AB_WIDTH,BTN_AB_WIDTH-10);
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

    private void bConfig(Image img) {
        img.setSize(BTN_AB_WIDTH,BTN_AB_HEIGHT);
        img.setPosition(VIEWPORT_WIDTH-2*BTN_AB_WIDTH+20,0);
        img.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bPressed=true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                bPressed=false;
            }
        });
    }

    private void btnDownConfig(Image image) {
        image.setSize(BTN_WIDTH, BTN_HEIGHT);
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
        });
    }

    private void btnUpConfig(Image image) {
        image.setSize(BTN_WIDTH, BTN_HEIGHT);
        image.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed=true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed=false;
            }
        });
    }

    private void btnRightConfig(Image image) {
        image.setSize(BTN_WIDTH, BTN_HEIGHT);
        image.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed=true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed=false;
            }
        });
    }

    private void btnLeftConfig(Image image) {
        image.setSize(BTN_WIDTH, BTN_HEIGHT);
        image.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed=true;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed=false;
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

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public void setaPressed(boolean aPressed) {
        this.aPressed = aPressed;
    }

    public void setbPressed(boolean bPressed) {
        this.bPressed = bPressed;
    }

    public void setEscPressed(boolean escPressed) {
        this.escPressed = escPressed;
    }

    public void setOptionsPressed(boolean optionsPressed) {
        this.optionsPressed = optionsPressed;
    }

    public boolean isOptionsPressed() {
        return optionsPressed;
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
