package com.mygdx.game.View.MenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.FreeWorld;

import java.awt.Color;

/**
 * Created by Utilizador on 08-05-2017.
 */

public class OptionsMenu extends MenuScreen {
    private Stage stage;
    private Texture texture;
    private Texture title;
    private Texture music;
    private Texture sounds;
    private Texture vibration;

    private Texture checked;
    private Texture unchecked;

    private CheckBox musicCheck;
    private CheckBox soundCheck;
    private CheckBox vibrationCheck;

    private Texture backtex;
    private ImageButton backButton;

    public OptionsMenu(MyGame game) {
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

        stage.addActor(musicCheck);
        stage.addActor(soundCheck);
        stage.addActor(vibrationCheck);

        Gdx.input.setInputProcessor(stage);
    }

    private void CheckBoxConfig() {
        musicCheck.setPosition(MENU_WIDTH/5,300);
        musicCheck.setSize(50,50);
        musicCheck.setChecked(true);
        soundCheck.setPosition(MENU_WIDTH/5,240);
        soundCheck.setSize(50,50);
        soundCheck.setChecked(true);
        vibrationCheck.setPosition(MENU_WIDTH/5,180);
        vibrationCheck.setSize(50,50);
        vibrationCheck.setChecked(true);
    }

    private void CheckBoxStyleDefine(CheckBox.CheckBoxStyle style) {
        Drawable checkedDrawable = new TextureRegionDrawable(new TextureRegion(checked));
        Drawable uncheckedDrawable = new TextureRegionDrawable(new TextureRegion(unchecked));
        style.checkboxOff = uncheckedDrawable;
        style.checkboxOn = checkedDrawable;
        style.font = new BitmapFont();
    }

    private void backButtonDefine() {
        Drawable backDrawable = new TextureRegionDrawable(new TextureRegion(backtex));
        backButton = new ImageButton(backDrawable);
        backButton.setPosition(MENU_WIDTH/3/2 - backButton.getWidth()/2,100);
        stage.addActor(backButton);
    }

    private void loadAssets() {
        texture =game.getAssetManager().get("Menus/main_menu.jpg", Texture.class);
        title = game.getAssetManager().get("Menus/options_title.png", Texture.class);
        music = game.getAssetManager().get("Menus/music_text.png", Texture.class);
        sounds = game.getAssetManager().get("Menus/sounds_text.png", Texture.class);
        vibration = game.getAssetManager().get("Menus/vibration_text.png", Texture.class);
        checked = game.getAssetManager().get("Menus/checked.png", Texture.class);
        unchecked = game.getAssetManager().get("Menus/unchecked.png", Texture.class);
        backtex = game.getAssetManager().get("Buttons/back_button.png", Texture.class);
    }

    @Override
    public void render(float delta) {
        //Clear the screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        menuDraw();
        stage.draw();
        buttonUpdate();
    }

    private void buttonUpdate() {
        if (backButton.isPressed())
            game.getGsm().set(new MainMenu(game));
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
    }

    private void menuDraw() {
        game.getBatch().begin();
        game.getBatch().draw(texture, 0,0,MENU_WIDTH/3,MENU_HEIGHT/3);
        game.getBatch().draw(title, MENU_WIDTH/3/2 - title.getWidth()/2,400);
        game.getBatch().draw(music, MENU_WIDTH/3/4,300);
        game.getBatch().draw(sounds, MENU_WIDTH/3/4,240);
        game.getBatch().draw(vibration, MENU_WIDTH/3/4,180);
        game.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
}
