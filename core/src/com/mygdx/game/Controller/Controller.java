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
    
    /** The Constant VIEWPORT_WIDTH. */
    public static final int VIEWPORT_WIDTH=240;
    
    /** The Constant VIEWPORT_HEIGHT. */
    public static final int VIEWPORT_HEIGHT=160;
    
    /** The Constant BTN_WIDTH. */
    public static final int BTN_WIDTH = 20;
    
    /** The Constant BTN_HEIGHT. */
    public static final int BTN_HEIGHT = 20;
    
    /** The Constant BTN_AB_WIDTH. */
    public static final int BTN_AB_WIDTH = 50;
    
    /** The Constant BTN_AB_HEIGHT. */
    public static final int BTN_AB_HEIGHT = 50;

    /** The game. */
    private MyGame game;
    
    /** The viewport. */
    private FitViewport viewport;
    
    /** The stage. */
    private Stage stage;
    
    /** The options pressed. */
    private boolean upPressed, downPressed, leftPressed,rightPressed, aPressed, bPressed, escPressed, optionsPressed;//z-a,x-b
    
    /** The cam. */
    private OrthographicCamera cam;
    
    /** The down img. */
    private Image downImg;
    
    /** The up img. */
    private Image upImg;
    
    /** The right img. */
    private Image rightImg;
    
    /** The left img. */
    private Image leftImg;
    
    /** The a img. */
    private Image aImg;
    
    /** The b img. */
    private Image bImg;
    
    /** The esc img. */
    private Image escImg;
    
    /** The options img. */
    private Image optionsImg;

    /**
     * Instantiates a new controller.
     *
     * @param game the game
     */
    public Controller(MyGame game){
        this.game=game;
        if(!game.getIsTest()) {
            cam = new OrthographicCamera();
            viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, cam);
            stage = new Stage(viewport, game.getBatch());
            Gdx.input.setInputProcessor(stage);
            addKeyDownListener();
            addKeyUpListener();
            imageLoad();
            tableConfig();
            abConfig();
        }
    }

    /**
     * Ab config.
     */
    private void abConfig() {
        stage.addActor(escImg);
        stage.addActor(optionsImg);
        stage.addActor(aImg);
        stage.addActor(bImg);
    }

    /**
     * Table config.
     */
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

    /**
     * Adds the key up listener.
     */
    private void addKeyUpListener() {
        stage.addListener(new InputListener(){
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                keyUpDefine(keycode);
                return true;
            }
        });
    }
    
    /**
     * Key up define.
     *
     * @param keycode the keycode
     */
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

    /**
     * Adds the key down listener.
     */
    private void addKeyDownListener() {
        stage.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                keyDownDefine(keycode);
                return true;
            }
        });
    }

    /**
     * Key down define.
     *
     * @param keycode the keycode
     */
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

    /**
     * Image load.
     */
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

    /**
     * Esc config.
     *
     * @param img the img
     */
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

    /**
     * Options config.
     *
     * @param img the img
     */
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

    /**
     * A config.
     *
     * @param img the img
     */
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

    /**
     * B config.
     *
     * @param img the img
     */
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

    /**
     * Btn down config.
     *
     * @param image the image
     */
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

    /**
     * Btn up config.
     *
     * @param image the image
     */
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

    /**
     * Btn right config.
     *
     * @param image the image
     */
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

    /**
     * Btn left config.
     *
     * @param image the image
     */
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

    /**
     * Draw.
     */
    public void draw(){
        stage.draw();
    }

    /**
     * Checks if is up pressed.
     *
     * @return true, if is up pressed
     */
    public boolean isUpPressed() {
        return upPressed;
    }

    /**
     * Checks if is down pressed.
     *
     * @return true, if is down pressed
     */
    public boolean isDownPressed() {
        return downPressed;
    }

    /**
     * Checks if is left pressed.
     *
     * @return true, if is left pressed
     */
    public boolean isLeftPressed() {
        return leftPressed;
    }

    /**
     * Checks if is right pressed.
     *
     * @return true, if is right pressed
     */
    public boolean isRightPressed() {
        return rightPressed;
    }

    /**
     * Checks if is a pressed.
     *
     * @return true, if is a pressed
     */
    public boolean isaPressed() {
        return aPressed;
    }

    /**
     * Checks if is b pressed.
     *
     * @return true, if is b pressed
     */
    public boolean isbPressed() {
        return bPressed;
    }

    /**
     * Checks if is esc pressed.
     *
     * @return true, if is esc pressed
     */
    public boolean isEscPressed() {
        return escPressed;
    }

    /**
     * Sets the up pressed.
     *
     * @param upPressed the new up pressed
     */
    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    /**
     * Sets the down pressed.
     *
     * @param downPressed the new down pressed
     */
    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    /**
     * Sets the left pressed.
     *
     * @param leftPressed the new left pressed
     */
    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    /**
     * Sets the right pressed.
     *
     * @param rightPressed the new right pressed
     */
    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    /**
     * Sets the a pressed.
     *
     * @param aPressed the new a pressed
     */
    public void setaPressed(boolean aPressed) {
        this.aPressed = aPressed;
    }

    /**
     * Sets the b pressed.
     *
     * @param bPressed the new b pressed
     */
    public void setbPressed(boolean bPressed) {
        this.bPressed = bPressed;
    }

    /**
     * Sets the esc pressed.
     *
     * @param escPressed the new esc pressed
     */
    public void setEscPressed(boolean escPressed) {
        this.escPressed = escPressed;
    }

    /**
     * Sets the options pressed.
     *
     * @param optionsPressed the new options pressed
     */
    public void setOptionsPressed(boolean optionsPressed) {
        this.optionsPressed = optionsPressed;
    }

    /**
     * Checks if is options pressed.
     *
     * @return true, if is options pressed
     */
    public boolean isOptionsPressed() {
        return optionsPressed;
    }

    /**
     * Resize.
     *
     * @param width the width
     * @param height the height
     */
    public void resize(int width, int height){
        viewport.update(width,height);
    }

    /**
     * Gets the stage.
     *
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Reset.
     */
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
