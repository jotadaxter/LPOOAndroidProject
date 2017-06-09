package com.mygdx.game.View.MenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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

public class GameCompleted extends MenuScreen {
    //Hero starting position in the tutorial
    public static final int POSX = 8+9*16+8;
    public static final int POSY = 8+11*16;
    private Stage stage;
    private Texture texture;
    private Texture title;
    private Label textLabel;
    private Texture mainMenutex;
    private ImageButton mainMenuButton;
    private BitmapFont font;
    private Texture quittex;
    private ImageButton quitButton;

    public GameCompleted(MyGame game) {
        super(game);
        stage= new Stage(viewPort,game.batch);
        texture =game.assetManager.get("Menus/main_menu.jpg", Texture.class);
        title = game.assetManager.get("Menus/game_completed.png", Texture.class);
        mainMenutex =game.assetManager.get("Buttons/main_menu_button.png", Texture.class);
        quittex =game.assetManager.get("Buttons/quit_button.png", Texture.class);
        font = game.assetManager.get("Fonts/score.fnt", BitmapFont.class);

        String text=textDefine();

        textLabel= new Label(text, new Label.LabelStyle(font, Color.BLACK));
        textLabel.setSize(5,5);
        textLabel.setPosition(300,325);

        stage.addActor(textLabel);

        Drawable mainMenuDrawable = new TextureRegionDrawable(new TextureRegion(mainMenutex));
        mainMenuButton = new ImageButton(mainMenuDrawable);
        mainMenuButton.setPosition(300,240-50);
        stage.addActor(mainMenuButton);

        Drawable quitDrawable = new TextureRegionDrawable(new TextureRegion(quittex));
        quitButton = new ImageButton(quitDrawable);
        quitButton.setPosition(300,180-100);
        stage.addActor(quitButton);

        Gdx.input.setInputProcessor(stage);
    }

    private String textDefine() {
        String temp="Score: " + game.heroStats.getScore() + "\n" + "Hearts: " + game.heroStats.getHearts();
        return temp;
    }

    @Override
    public void render(float delta) {
        //Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(texture, 0, 0, MENU_WIDTH / 3, MENU_HEIGHT / 3);
        game.batch.draw(title, 150, 400-20);
        game.batch.end();

        stage.draw();

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

