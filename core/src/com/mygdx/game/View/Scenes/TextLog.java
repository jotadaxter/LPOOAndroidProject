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
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Utilizador on 22-05-2017.
 */

public class TextLog {
    private MyGame game;
    public Stage stage;
    private Viewport viewport;
    private BitmapFont font;
    private String text;
    private Label textLabel;
    private Image logImage;

    public TextLog(MyGame game, GameScreen screen, String text) {
        this.game=game;
        this.text=text;
        viewport = new FitViewport(MyGame.VIEWPORT_WIDTH, MyGame.VIEWPORT_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        font = new BitmapFont(Gdx.files.internal("textFont.fnt"));

        logImage=new Image(new Texture(Gdx.files.internal("log.png")));
        logImage.setPosition(5,0);
        stage.addActor(logImage);

        //Label creation
        labels();
    }

    private void labels() {
        Table table = new Table();
        table.center();
       // table.bottom();
        table.setFillParent(true);

        textLabel= new Label(text, new Label.LabelStyle(font, Color.WHITE));
        textLabel.setSize(5,5);

        table.add(textLabel).width(35).height(25);
        stage.addActor(table);
    }

    public void update(float dt){
       textLabel.setText(text);
    }

    public void setText(String text) {
        this.text = text;
    }
}