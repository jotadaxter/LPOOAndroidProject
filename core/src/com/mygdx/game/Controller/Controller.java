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
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Jotadaxter on 22/04/2017.
 */

public class Controller {
    public static final int VIEWPORT_WIDTH=240;
    public static final int VIEWPORT_HEIGHT=160;
    public static final int BTN_WIDTH = 20;
    public static final int BTN_HEIGTH = 20;


    private FitViewport viewport;
    private Stage stage;
    private boolean upPressed, downPressed, leftPressed,rightPressed, aPressed, bPressed;
    private OrthographicCamera cam;

    public Controller(SpriteBatch sb, GameScreen screen){
        cam = new OrthographicCamera();
        viewport = new FitViewport(VIEWPORT_WIDTH,VIEWPORT_HEIGHT,cam);
        stage= new Stage(viewport,sb);

        stage.addListener(new InputListener(){

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch(keycode) {
                    case Input.Keys.UP:
                        upPressed = true;
                        break;
                    case Input.Keys.DOWN:
                        downPressed = true;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = true;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = true;
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch(keycode) {
                    case Input.Keys.UP:
                        upPressed = false;
                        break;
                    case Input.Keys.DOWN:
                        downPressed = false;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = false;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = false;
                        break;
                }
                return true;
            }
        });

        Gdx.input.setInputProcessor(stage);

        imageLoad();
    }

    private void imageLoad() {
        Table table = new Table();
        table.left().bottom();

        //Down Arrow Button
        Image downImg = new Image(new Texture("down_arrow.png"));
        downImg.setSize(BTN_WIDTH,BTN_HEIGTH);
        downImg.addListener(new InputListener(){
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

        //Up Arrow Button
        Image upImg = new Image(new Texture("up_arrow.png"));
        upImg.setSize(BTN_WIDTH,BTN_HEIGTH);
        upImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed=true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed=false;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                upPressed=true;
            }
        });

        //Right Arrow Button
        Image rightImg = new Image(new Texture("right_arrow.png"));
        rightImg.setSize(BTN_WIDTH,BTN_HEIGTH);
        rightImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed=true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed=false;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                rightPressed=true;
            }
        });

        //Left Arrow Button
        Image leftImg = new Image(new Texture("left_arrow.png"));
        leftImg.setSize(BTN_WIDTH,BTN_HEIGTH);
        leftImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed=true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed=false;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                leftPressed=true;
            }
        });
/*
        //A Button
        Image aImg = new Image(new Texture("a_button.png"));
        aImg.setSize(50,50);
        aImg.addListener(new InputListener(){
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

        //B Button
        Image bImg = new Image(new Texture("b_button.png"));
        bImg.setSize(50,50);
        bImg.addListener(new InputListener(){
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
*/
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
    }
}
