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

// TODO: Auto-generated Javadoc
/**
 * Created by Utilizador on 08-05-2017.
 */

public class GameOver extends MenuScreen {
    
    /** The stage. */
    private Stage stage;
    
    /** The texture. */
    private Texture texture;
    
    /** The title. */
    private Texture title;

    /** The loadtex. */
    private Texture loadtex;
    
    /** The load button. */
    private ImageButton loadButton;

    /** The main menutex. */
    private Texture mainMenutex;
    
    /** The main menu button. */
    private ImageButton mainMenuButton;

    /** The quittex. */
    private Texture quittex;
    
    /** The quit button. */
    private ImageButton quitButton;

    /**
     * Instantiates a new game over.
     *
     * @param game the game
     */
    public GameOver(MyGame game) {
        super(game);
        stage= new Stage(viewPort,game.getBatch());
        texture =game.getAssetManager().get("Menus/main_menu.jpg", Texture.class);
        title = game.getAssetManager().get("Menus/game_over.png", Texture.class);
        loadtex =game.getAssetManager().get("Buttons/load_button.png", Texture.class);
        mainMenutex =game.getAssetManager().get("Buttons/main_menu_button.png", Texture.class);
        quittex =game.getAssetManager().get("Buttons/quit_button.png", Texture.class);


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

    /* (non-Javadoc)
     * @see com.mygdx.game.View.MenuScreens.MenuScreen#render(float)
     */
    @Override
    public void render(float delta) {
        //Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();

        game.getBatch().draw(texture, 0, 0, MENU_WIDTH / 3, MENU_HEIGHT / 3);
        game.getBatch().draw(title, 240, 400);
        game.getBatch().end();

        stage.draw();

        if (loadButton.isPressed()) {//fazer load de uma savegame
        }

        if (mainMenuButton.isPressed()) {
            game.getGsm().getStates().clear();
            game.getGsm().push(new GameState(new MainMenu(game)));
        }

        if (quitButton.isPressed()) {
            System.exit(0);
        }


    }
    
    /* (non-Javadoc)
     * @see com.mygdx.game.View.MenuScreens.MenuScreen#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
}

