package com.mygdx.game.View.MenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Model.States.GameState;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.DemoScreen;

/**
 * Created by Utilizador on 08-05-2017.
 */

public class MainMenu extends MenuScreen {
    private Stage stage;

    private Texture texture;
    private Texture title;
    private Texture arcadetex;
    private ImageButton arcadeButton;

    public MainMenu(MyGame game) {
        super(game);
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
    public void render(float delta) {
        //Clear the screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(texture, 0,0,MENU_WIDTH/3,MENU_HEIGHT/3);
        game.batch.draw(title, 180,400);
        game.batch.end();
        stage.draw();


        if(arcadeButton.isPressed()){
            game.gameStateManager.states.push(new GameState(new DemoScreen(game)));
            game.setScreen(game.gameStateManager.states.peek().getGameScreen());
            //game.gameStateManager.push(new DemoScreen(game));

        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
}
