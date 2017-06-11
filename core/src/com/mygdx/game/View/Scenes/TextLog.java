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
    
    /** The stage. */
    private Stage stage;
    
    /** The viewport. */
    private Viewport viewport;
    
    /** The font. */
    private BitmapFont font;
    
    /** The text. */
    private String text;
    
    /** The text label. */
    private Label textLabel;
    
    /** The log image. */
    private Image logImage;
    
    /** The id. */
    private int id;

    /**
     * Instantiates a new text log.
     *
     * @param game the game
     */
    public TextLog(MyGame game) {
        if(!game.getIsTest()) {
            viewport = new FitViewport(MyGame.VIEWPORT_WIDTH, MyGame.VIEWPORT_HEIGHT, new OrthographicCamera());
            stage = new Stage(viewport, game.getBatch());
            font = game.getAssetManager().get("Fonts/textFont.fnt", BitmapFont.class);
            logImage = new Image(game.getAssetManager().get("Game/log.png", Texture.class));
            logImage.setPosition(5, 0);
            stage.addActor(logImage);
            labels();
        }
    }

    /**
     * Labels.
     */
    private void labels() {
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        textLabel= new Label(text, new Label.LabelStyle(font, Color.WHITE));
        textLabel.setSize(5,5);

        table.add(textLabel).width(70).height(100);
        stage.addActor(table);
    }
    
    /**
     * Update.
     */
    public void update(){
        textLabel.setText(text);
    }

    /**
     * Sets the text.
     *
     * @param text the new text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the stage.
     *
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }
}
