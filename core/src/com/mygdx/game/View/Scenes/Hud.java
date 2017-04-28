package com.mygdx.game.View.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.Screens.MyScreen;

/**
 * Created by Jotadaxter on 21/04/2017.
 */

public class Hud implements Disposable{
    public Stage stage;
    private Viewport viewport;
    private BitmapFont font;

    private Integer health;
    private Integer score;

    private Label scoreLabel;
    private Texture heart_full;
    private Texture heart_empty;
    private Texture heart_half;
    private Texture heart_quarter;
    private Texture heart_three_quarts;
    private TextureRegion rupee;

    public Hud(SpriteBatch sb, MyScreen screen){
        score=0;
        health=3;

        viewport=new FitViewport(MyGame.VIEWPORT_WIDTH,MyGame.VIEWPORT_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        font = new BitmapFont(Gdx.files.internal("myFont.fnt"));

        //Label and Table creation
        labels();

        //Texture Load
        textureLoad(screen);
        
        //Hearts Display
        displayHearts();
        
        //Rupee Display
        
        
    }

    private void displayHearts() {
    }

    private void textureLoad(MyScreen screen) {
        heart_full= new Texture(Gdx.files.internal("heart_full.png"));
        heart_empty= new Texture(Gdx.files.internal("heart_empty.png"));
        heart_half= new Texture(Gdx.files.internal("heart_half.png"));
        heart_quarter= new Texture(Gdx.files.internal("heart_quarter.png"));
        heart_three_quarts= new Texture(Gdx.files.internal("heart_three_quarts.png"));
        rupee = new TextureRegion(screen.getAtlas().findRegion("green_rupee"), 0,0,7,14);
    }

    private void labels() {
        Table table = new Table();
        table.top();
        table.right();
        table.setFillParent(true);

        scoreLabel= new Label(String.format("%03d", score), new Label.LabelStyle(font, Color.WHITE));
        scoreLabel.setSize(5,5);
        table.setScale(5,10);
        table.add(scoreLabel).width(35).height(25);
        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();

    }

    public void update(float dt, MyScreen screen){
        this.score=screen.getHero().getScore();
        scoreLabel.setText(String.format("%03d", score));
    }
}
