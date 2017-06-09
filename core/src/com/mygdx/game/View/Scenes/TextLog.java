package com.mygdx.game.View.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.FreeWorld;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Utilizador on 22-05-2017.
 */

public class TextLog {
    private GameScreen screen;
    private MyGame game;
    public Stage stage;
    private Viewport viewport;
    private BitmapFont font;
    private String text;
    private Label textLabel;
    private Image logImage;
    private int id;

    public TextLog(MyGame game, GameScreen screen) {
        this.game=game;
        this.screen=screen;
        viewport = new FitViewport(MyGame.VIEWPORT_WIDTH, MyGame.VIEWPORT_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        font = screen.getGame().assetManager.get("Fonts/score.fnt", BitmapFont.class);

        logImage=new Image(screen.getGame().assetManager.get("Game/log.png", Texture.class));
        logImage.setPosition(5,0);
        stage.addActor(logImage);

        //Label creation
        labels();
    }

    private void labels() {
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        textLabel= new Label(text, new Label.LabelStyle(font, Color.WHITE));
        textLabel.setSize(5,5);

        table.add(textLabel).width(500).height(500);
        stage.addActor(table);
    }
    public void update(float dt){
        textLabel.setText(text);
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
