package com.mygdx.game.View.MenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Model.States.GameState;
import com.mygdx.game.MyGame;

/**
 * Created by Utilizador on 08-05-2017.
 */

public class GameCompleted extends MenuScreen {
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
        stage= new Stage(viewPort,game.getBatch());
        texture =game.getAssetManager().get("Menus/main_menu.jpg", Texture.class);
        title = game.getAssetManager().get("Menus/game_completed.png", Texture.class);
        mainMenutex =game.getAssetManager().get("Buttons/main_menu_button.png", Texture.class);
        quittex =game.getAssetManager().get("Buttons/quit_button.png", Texture.class);
        font = game.getAssetManager().get("Fonts/score.fnt", BitmapFont.class);

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
        String temp="Score: " + game.getHeroStats().getScore() + "\n" + "Hearts: " + game.getHeroStats().getHearts();
        return temp;
    }

    @Override
    public void render(float delta) {
        //Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        game.getBatch().draw(texture, 0, 0, MENU_WIDTH / 3, MENU_HEIGHT / 3);
        game.getBatch().draw(title, 150, 400-20);
        game.getBatch().end();

        stage.draw();

        if (mainMenuButton.isPressed()) {
            game.getGsm().getStates().clear();
            game.getGsm().push(new GameState(new MainMenu(game)));
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

