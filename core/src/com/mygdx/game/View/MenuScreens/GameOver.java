package com.mygdx.game.View.MenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Model.States.GameState;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.DemoScreen;
import com.mygdx.game.View.GameScreens.Dungeon1;
import com.mygdx.game.View.GameScreens.FreeWorld;

import java.util.ArrayList;

/**
 * Created by Utilizador on 08-05-2017.
 */

public class GameOver extends MenuScreen {
    //Hero starting position in the tutorial
    public static final int POSX = 8+9*16+8;
    public static final int POSY = 8+11*16;
    private Stage stage;
    private Texture texture;
    private Texture title;

    private Texture loadtex;
    private ImageButton loadButton;

    private Texture mainMenutex;
    private ImageButton mainMenuButton;

    private Texture quittex;
    private ImageButton quitButton;

    public GameOver(MyGame game) {
        super(game);
        stage= new Stage(viewPort,game.batch);
        texture =game.assetManager.get("Menus/main_menu.jpg", Texture.class);
        title = game.assetManager.get("Menus/game_over.png", Texture.class);
        loadtex =game.assetManager.get("Buttons/load_button.png", Texture.class);
        mainMenutex =game.assetManager.get("Buttons/main_menu_button.png", Texture.class);
        quittex =game.assetManager.get("Buttons/quit_button.png", Texture.class);


        Drawable loadDrawable = new TextureRegionDrawable(new TextureRegion(loadtex));
        loadButton = new ImageButton(loadDrawable);
        loadButton.setPosition(300,300);
        stage.addActor(loadButton);

        Drawable mainMenuDrawable = new TextureRegionDrawable(new TextureRegion(mainMenutex));
        mainMenuButton = new ImageButton(mainMenuDrawable);
        mainMenuButton.setPosition(300,240);
        stage.addActor(mainMenuButton);

        Drawable quitDrawable = new TextureRegionDrawable(new TextureRegion(quittex));
        quitButton = new ImageButton(quitDrawable);
        quitButton.setPosition(300,180);
        stage.addActor(quitButton);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        //Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(texture, 0, 0, MENU_WIDTH / 3, MENU_HEIGHT / 3);
        game.batch.draw(title, 240, 400);
        game.batch.end();

        stage.draw();

        if (loadButton.isPressed()) {//fazer load de uma savegame
        }

        if (mainMenuButton.isPressed()) {
            game.gsm.states.clear();
            game.gsm.push(new GameState(new MainMenu(game)));
        }

        if (quitButton.isPressed()) {
            System.exit(0);
        }


    }
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
}

