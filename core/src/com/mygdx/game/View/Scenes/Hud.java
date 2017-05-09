package com.mygdx.game.View.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Jotadaxter on 21/04/2017.
 */

public class Hud implements Disposable{
    public static final int HEART_WIDTH = 10;
    public static final int HEART_HEIGTH = 10;
    public static final int HEART_X = 5;
    public static final int HEART_Y = 145;

    public static final int RUPEE_WIDTH = 10;
    public static final int RUPEE_HEIGTH = 20;
    public static final int RUPEE_X = 190;
    public static final int RUPEE_Y = 135;

    public Stage stage;
    public Stage heartStage;
    private Viewport viewport;
    private BitmapFont font;

    private Integer health;
    private Integer previousHealth;
    private Integer score;

    private Label scoreLabel;
    private Image heart_full;
    private Image heart_empty;
    private Image heart_half;
    private Image heart_quarter;
    private Image heart_three_quarts;
    private Image rupee;

    public Hud(SpriteBatch sb, GameScreen screen) {
        score = 0;
        health = 3;
        previousHealth=health;
        viewport = new FitViewport(MyGame.VIEWPORT_WIDTH, MyGame.VIEWPORT_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        heartStage = new Stage(viewport, sb);
        font = new BitmapFont(Gdx.files.internal("myFont.fnt"));

        //Label creation
        labels();

        //Image Load and display
        Table heartTable = new Table();
        textureLoad(screen, heartTable);
    
        displayHearts();
    }

    private void displayHearts() {
        //stage.addActor(heart_full);
        int i=0;
        while (i<health){
            Image new_heart= new Image(new Texture(Gdx.files.internal("heart_full.png")));
            new_heart.setBounds(HEART_X+12*i,HEART_Y,HEART_WIDTH,HEART_HEIGTH);
            heartStage.addActor(new_heart);
            i++;
        }
        //heart_full.setBounds(HEART_X + 10,HEART_Y,HEART_WIDTH,HEART_HEIGTH);
        //stage.addActor(heart_full);

    }

    private void textureLoad(GameScreen screen, Table table) {
        //Rupee image
        rupee = new Image(new TextureRegion(screen.getAtlas().findRegion("green_rupee"), 0,0,7,14));
        rupee.setBounds(RUPEE_X,RUPEE_Y,RUPEE_WIDTH,RUPEE_HEIGTH);
        stage.addActor(rupee);

        //Heart images
        heart_full=new Image(new Texture(Gdx.files.internal("heart_full.png")));
        heart_full.setBounds(HEART_X,HEART_Y,HEART_WIDTH,HEART_HEIGTH);

        heart_empty= new Image(new Texture(Gdx.files.internal("heart_empty.png")));
        heart_empty.setBounds(HEART_X,HEART_Y,HEART_WIDTH,HEART_HEIGTH);

        heart_half= new Image(new Texture(Gdx.files.internal("heart_half.png")));
        heart_half.setBounds(HEART_X,HEART_Y,HEART_WIDTH,HEART_HEIGTH);

        heart_quarter= new Image(new Texture(Gdx.files.internal("heart_quarter.png")));
        heart_quarter.setBounds(HEART_X,HEART_Y,HEART_WIDTH,HEART_HEIGTH);

        heart_three_quarts= new Image(new Texture(Gdx.files.internal("heart_three_quarts.png")));
        heart_three_quarts.setBounds(HEART_X,HEART_Y,HEART_WIDTH,HEART_HEIGTH);
    }

    private void labels() {
        Table table = new Table();
        table.top();
        table.right();
        table.setFillParent(true);

        scoreLabel= new Label(String.format("%03d", score), new Label.LabelStyle(font, Color.WHITE));
        scoreLabel.setSize(5,5);

        table.add(scoreLabel).width(35).height(25);
        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();

    }

    public void update(float dt, GameScreen screen){
        this.score=screen.getHero().getScore();
        scoreLabel.setText(String.format("%03d", score));
        this.health=screen.getHero().getHealth();
        updateHearts();
        previousHealth=health;
    }

    private void updateHearts() {
        int delta = health-previousHealth;
        int i = 0;
        heartStage.clear();
        //redesenhar corações
        while (i<health){
            Image new_heart= new Image(new Texture(Gdx.files.internal("heart_full.png")));
            new_heart.setBounds(HEART_X+12*i,HEART_Y,HEART_WIDTH,HEART_HEIGTH);
            heartStage.addActor(new_heart);
            i++;
        }

    }
}
