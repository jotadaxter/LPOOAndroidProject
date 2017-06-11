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

// TODO: Auto-generated Javadoc
/**
 * Created by Jotadaxter on 21/04/2017.
 */

public class Hud implements Disposable{
    
    /** The Constant HEART_WIDTH. */
    public static final int HEART_WIDTH = 10;
    
    /** The Constant HEART_HEIGTH. */
    public static final int HEART_HEIGTH = 10;
    
    /** The Constant HEART_X. */
    public static final int HEART_X = 5;
    
    /** The Constant HEART_Y. */
    public static final int HEART_Y = 145;

    /** The Constant RUPEE_WIDTH. */
    public static final int RUPEE_WIDTH = 10;
    
    /** The Constant RUPEE_HEIGTH. */
    public static final int RUPEE_HEIGTH = 20;
    
    /** The Constant RUPEE_X. */
    public static final int RUPEE_X = 180;
    
    /** The Constant RUPEE_Y. */
    public static final int RUPEE_Y = 135;

    /** The Constant RUBY_WIDTH. */
    public static final int RUBY_WIDTH = 10;
    
    /** The Constant RUBY_HEIGTH. */
    public static final int RUBY_HEIGTH = 10;
    
    /** The Constant RUBY_X. */
    public static final int RUBY_X = 5;
    
    /** The Constant RUBY_Y. */
    public static final int RUBY_Y = 145-12;

    /** The game. */
    private MyGame game;
    
    /** The stage. */
    private Stage stage;
    
    /** The heart stage. */
    private Stage heartStage;
    
    /** The viewport. */
    private Viewport viewport;
    
    /** The font. */
    private BitmapFont font;

    /** The health. */
    private Integer health;
    
    /** The previous health. */
    private Integer previousHealth;
    
    /** The score. */
    private Integer score;
    
    /** The display ruby. */
    private boolean displayRuby;

    /** The score label. */
    private Label scoreLabel;
    
    /** The heart full. */
    private Image heart_full;
    
    /** The heart empty. */
    private Image heart_empty;
    
    /** The heart half. */
    private Image heart_half;
    
    /** The heart quarter. */
    private Image heart_quarter;
    
    /** The heart three quarts. */
    private Image heart_three_quarts;
    
    /** The rupee. */
    private Image rupee;
    
    /** The ruby. */
    private Image ruby;

    /**
     * Instantiates a new hud.
     *
     * @param game the game
     * @param screen the screen
     */
    public Hud(MyGame game, GameScreen screen) {
        this.game=game;
        score = game.getHeroStats().getScore();
        health = game.getHeroStats().getHearts();
        previousHealth=health;
        displayRuby=false;
        viewport = new FitViewport(MyGame.VIEWPORT_WIDTH, MyGame.VIEWPORT_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.getBatch());
        heartStage = new Stage(viewport, game.getBatch());
        font = game.getAssetManager().get("Fonts/myFont.fnt", BitmapFont.class);

        //Label creation
        labels();

        //Image Load and display
        Table heartTable = new Table();
        textureLoad(screen, heartTable);
    
        displayHearts();
        displayVolcano_Ruby();
    }

    /**
     * Display hearts.
     */
    private void displayHearts() {
        int i=0;
        while (i<health){
            Image new_heart= new Image(new TextureRegion(game.getAssetManager().get("Game/life_hearts.png", Texture.class), 0,0,9,8));
            new_heart.setBounds(HEART_X+12*i,HEART_Y,HEART_WIDTH,HEART_HEIGTH);
            heartStage.addActor(new_heart);
            i++;
        }
    }

    /**
     * Display volcano ruby.
     */
    private void displayVolcano_Ruby() {
        ruby = new Image(new TextureRegion(game.getAssetManager().get("Game/volcano_ruby.png", Texture.class)));
        ruby.setBounds(RUBY_X,RUBY_Y,RUBY_WIDTH,RUBY_HEIGTH);
    }

    /**
     * Texture load.
     *
     * @param screen the screen
     * @param table the table
     */
    private void textureLoad(GameScreen screen, Table table) {
        //Rupee image
        rupee = new Image(new TextureRegion(game.getAssetManager().get("Game/green_rupee.png", Texture.class), 0,0,7,14));
        rupee.setBounds(RUPEE_X,RUPEE_Y,RUPEE_WIDTH,RUPEE_HEIGTH);
        stage.addActor(rupee);

        //Heart images
        heart_full=new Image(new TextureRegion(game.getAssetManager().get("Game/life_hearts.png", Texture.class), 0,0,9,8));
        heart_full.setBounds(HEART_X,HEART_Y,HEART_WIDTH,HEART_HEIGTH);

        heart_empty= new Image(new TextureRegion(game.getAssetManager().get("Game/life_hearts.png", Texture.class), 36,0,9,8));
        heart_empty.setBounds(HEART_X,HEART_Y,HEART_WIDTH,HEART_HEIGTH);

        heart_half=new Image(new TextureRegion(game.getAssetManager().get("Game/life_hearts.png", Texture.class), 18,0,9,8));
        heart_half.setBounds(HEART_X,HEART_Y,HEART_WIDTH,HEART_HEIGTH);

        heart_quarter=new Image(new TextureRegion(game.getAssetManager().get("Game/life_hearts.png", Texture.class), 27,0,9,8));
        heart_quarter.setBounds(HEART_X,HEART_Y,HEART_WIDTH,HEART_HEIGTH);

        heart_three_quarts= new Image(new TextureRegion(game.getAssetManager().get("Game/life_hearts.png", Texture.class), 9,0,9,8));
        heart_three_quarts.setBounds(HEART_X,HEART_Y,HEART_WIDTH,HEART_HEIGTH);
    }

    /**
     * Labels.
     */
    private void labels() {
        Table table = new Table();
        table.top();
        table.right();
        table.setFillParent(true);

        scoreLabel= new Label(String.format("%04d", score), new Label.LabelStyle(font, Color.WHITE));
        scoreLabel.setSize(5,5);

        table.add(scoreLabel).width(45).height(25);
        stage.addActor(table);
    }

    /**
     * Dispose.
     */
    @Override
    public void dispose() {
        stage.dispose();

    }

    /**
     * Update.
     */
    public void update(){
        this.score=game.getHeroStats().getScore();
        scoreLabel.setText(String.format("%04d", score));
        this.health=game.getHeroStats().getHearts();
        this.displayRuby=game.getHeroStats().displayVolcanoRuby();
        updateHearts();
        updateRuby();
        previousHealth=health;
    }

    /**
     * Update ruby.
     */
    private void updateRuby() {
        if(displayRuby)
            stage.addActor(ruby);
}

    /**
     * Update hearts.
     */
    private void updateHearts() {
        int delta = health-previousHealth;
        int i = 0;
        heartStage.clear();
        //redesenhar corações
        while (i<health){
            Image new_heart= new Image(new TextureRegion(game.getAssetManager().get("Game/life_hearts.png", Texture.class), 0,0,9,8));
            new_heart.setBounds(HEART_X+12*i,HEART_Y,HEART_WIDTH,HEART_HEIGTH);
            heartStage.addActor(new_heart);
            i++;
        }
    }

    /**
     * Gets the stage.
     *
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Gets the heart stage.
     *
     * @return the heart stage
     */
    public Stage getHeartStage() {
        return heartStage;
    }
}
