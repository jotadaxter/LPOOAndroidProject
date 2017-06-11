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

public class MainMenu extends MenuScreen {
    
    /** The stage. */
    private Stage stage;
    
    /** The texture. */
    private Texture texture;
    
    /** The title. */
    private Texture title;
    
    /** The storytex. */
    private Texture storytex;
    
    /** The story button. */
    private ImageButton storyButton;
    
    /** The optionstex. */
    private Texture optionstex;
    
    /** The options button. */
    private ImageButton optionsButton;
    
    /** The quittex. */
    private Texture quittex;
    
    /** The quit button. */
    private ImageButton quitButton;

    /**
     * Instantiates a new main menu.
     *
     * @param game the game
     */
    public MainMenu(MyGame game) {
        super(game);
        stage= new Stage(viewPort,game.getBatch());
        texture =game.getAssetManager().get("Menus/main_menu.jpg", Texture.class);
        title = game.getAssetManager().get("Menus/game_title.png", Texture.class);
        storytex =game.getAssetManager().get("Buttons/story_button.png", Texture.class);
        optionstex =game.getAssetManager().get("Buttons/options_button.png", Texture.class);
        quittex =game.getAssetManager().get("Buttons/quit_button.png", Texture.class);


        Drawable storydrawable = new TextureRegionDrawable(new TextureRegion(storytex));
        storyButton = new ImageButton(storydrawable);
        storyButton.setPosition(300,300);
        stage.addActor(storyButton);

        Drawable optionsdrawable = new TextureRegionDrawable(new TextureRegion(optionstex));
        optionsButton = new ImageButton(optionsdrawable);
        optionsButton.setPosition(300,240);
        stage.addActor(optionsButton);

        Drawable quitdrawable = new TextureRegionDrawable(new TextureRegion(quittex));
        quitButton = new ImageButton(quitdrawable);
        quitButton.setPosition(300,180);
        stage.addActor(quitButton);

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Render.
     *
     * @param delta the delta
     */
    @Override
    public void render(float delta) {
        //Clear the screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();

        game.getBatch().draw(texture, 0,0,MENU_WIDTH/3,MENU_HEIGHT/3);
        game.getBatch().draw(title, 180,400-20);
        game.getBatch().end();

        stage.draw();

        if(storyButton.isPressed()){
            game.getGsm().push(new GameState(new StoryMenu(game)));
        }

        if (optionsButton.isPressed()){
            game.getGsm().push(new GameState(new OptionsMenu(game)));
        }

        if (quitButton.isPressed()){
            System.exit(0);
        }

    }

    /**
     * Resize.
     *
     * @param width the width
     * @param height the height
     */
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
}
