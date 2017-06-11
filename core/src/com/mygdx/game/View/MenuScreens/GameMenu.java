package com.mygdx.game.View.MenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Model.States.GameState;
import com.mygdx.game.MyGame;

/**
 * Created by Utilizador on 08-05-2017.
 */

public class GameMenu extends MenuScreen {
    
    /** The stage. */
    private Stage stage;
    
    /** The texture. */
    private Texture texture;
    
    /** The title. */
    private Texture title;
    
    /** The music. */
    private Texture music;
    
    /** The sounds. */
    private Texture sounds;
    
    /** The vibration. */
    private Texture vibration;

    /** The checked. */
    private Texture checked;
    
    /** The unchecked. */
    private Texture unchecked;
    
    /** The save game tex. */
    private Texture saveGameTex;
    
    /** The load game tex. */
    private Texture loadGameTex;
    
    /** The main menutex. */
    private Texture mainMenutex;

    /** The music check. */
    private CheckBox musicCheck;
    
    /** The sound check. */
    private CheckBox soundCheck;
    
    /** The vibration check. */
    private CheckBox vibrationCheck;

    /** The backtex. */
    private Texture backtex;
    
    /** The back button. */
    private ImageButton backButton;
    
    /** The load button. */
    private ImageButton loadButton;
    
    /** The save button. */
    private ImageButton saveButton;
    
    /** The main menu button. */
    private ImageButton mainMenuButton;


    /**
     * Instantiates a new game menu.
     *
     * @param game the game
     */
    public GameMenu(MyGame game) {
        super(game);
        stage= new Stage(viewPort,game.getBatch());
        loadAssets();
        backButtonDefine();

        CheckBox.CheckBoxStyle style;
        style = new CheckBox.CheckBoxStyle();
        CheckBoxStyleDefine(style);

        musicCheck = new CheckBox("",style);
        soundCheck = new CheckBox("",style);
        vibrationCheck = new CheckBox("",style);
        CheckBoxConfig();

        Drawable loadDrawable = new TextureRegionDrawable(new TextureRegion(loadGameTex));
        loadButton = new ImageButton(loadDrawable);
        loadButton.setPosition(300-150,300-150);
        stage.addActor(loadButton);

        Drawable saveDrawable = new TextureRegionDrawable(new TextureRegion(saveGameTex));
        saveButton = new ImageButton(saveDrawable);
        saveButton.setPosition(300+150,300-150);
        stage.addActor(saveButton);

        Drawable mainMenuDrawable = new TextureRegionDrawable(new TextureRegion(mainMenutex));
        mainMenuButton = new ImageButton(mainMenuDrawable);
        mainMenuButton.setPosition(300-150,240-150-20);
        stage.addActor(mainMenuButton);

        stage.addActor(musicCheck);
        stage.addActor(soundCheck);
         stage.addActor(vibrationCheck);

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Check box config.
     */
    private void CheckBoxConfig() {
        musicCheck.setPosition(MENU_WIDTH/5,300+60);
        musicCheck.setSize(50,50);
        musicCheck.setChecked(true);
        soundCheck.setPosition(MENU_WIDTH/5,240+60);
        soundCheck.setSize(50,50);
        soundCheck.setChecked(true);
        vibrationCheck.setPosition(MENU_WIDTH/5,180+60);
        vibrationCheck.setSize(50,50);
        vibrationCheck.setChecked(true);
    }

    /**
     * Check box style define.
     *
     * @param style the style
     */
    private void CheckBoxStyleDefine(CheckBox.CheckBoxStyle style) {
        Drawable checkedDrawable = new TextureRegionDrawable(new TextureRegion(checked));
        Drawable uncheckedDrawable = new TextureRegionDrawable(new TextureRegion(unchecked));
        style.checkboxOff = uncheckedDrawable;
        style.checkboxOn = checkedDrawable;
        style.font = new BitmapFont();
    }

    /**
     * Back button define.
     */
    private void backButtonDefine() {
        Drawable backDrawable = new TextureRegionDrawable(new TextureRegion(backtex));
        backButton = new ImageButton(backDrawable);
        backButton.setPosition(MENU_WIDTH/3/2 - backButton.getWidth()/2+150,100-30);
        stage.addActor(backButton);
    }

    /**
     * Load assets.
     */
    private void loadAssets() {
        texture =game.getAssetManager().get("Menus/main_menu.jpg", Texture.class);
        title = game.getAssetManager().get("Menus/options_title.png", Texture.class);
        music = game.getAssetManager().get("Menus/music_text.png", Texture.class);
        sounds = game.getAssetManager().get("Menus/sounds_text.png", Texture.class);
        vibration = game.getAssetManager().get("Menus/vibration_text.png", Texture.class);
        checked = game.getAssetManager().get("Menus/checked.png", Texture.class);
        unchecked = game.getAssetManager().get("Menus/unchecked.png", Texture.class);
        backtex = game.getAssetManager().get("Buttons/back_button.png", Texture.class);
        saveGameTex = game.getAssetManager().get("Buttons/save_game_button.png", Texture.class);
        loadGameTex = game.getAssetManager().get("Buttons/load_button.png", Texture.class);
        mainMenutex = game.getAssetManager().get("Buttons/main_menu_button.png", Texture.class);
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
        menuDraw();
        stage.draw();
        buttonUpdate();
    }

    /**
     * Button update.
     */
    private void buttonUpdate() {
        if (backButton.isPressed())
            game.getGsm().pop();
        if(!musicCheck.isChecked())
            game.muteMusic();
        else if(musicCheck.isChecked())
            game.normalizeMusic();
        if(!soundCheck.isChecked())
            game.muteSound();
        else if(soundCheck.isChecked())
            game.normalizeSound();
        if(!vibrationCheck.isChecked())
            game.vibrationOff();
        else if(vibrationCheck.isChecked())
            game.vibrationOn();
        if (loadButton.isPressed()) {
            game.getGsm().push(new GameState(new LoadMenuIG(game)));
        }
        if (saveButton.isPressed()) {
            game.getGsm().push(new GameState(new SaveMenuIG(game)));
        }
        if (mainMenuButton.isPressed()) {
            game.getGsm().getStates().clear();
            game.getGsm().push(new GameState(new MainMenu(game)));
        }
    }

    /**
     * Menu draw.
     */
    private void menuDraw() {
        game.getBatch().begin();
        game.getBatch().draw(texture, 0,0,MENU_WIDTH/3,MENU_HEIGHT/3);
        game.getBatch().draw(title, MENU_WIDTH/3/2 - title.getWidth()/2,400+20);
        game.getBatch().draw(music, MENU_WIDTH/3/4,300+40);
        game.getBatch().draw(sounds, MENU_WIDTH/3/4,240+40);
        game.getBatch().draw(vibration, MENU_WIDTH/3/4,180+40);
        game.getBatch().end();
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
