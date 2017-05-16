package com.mygdx.game.Model.Entitys.Hero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Controller.Entitys.Hero.HeroBody;
import com.mygdx.game.Model.Entitys.Items.Item;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;
import com.mygdx.game.Model.Entitys.Items.Jewel;

/**
 * Created by Utilizador on 05-04-2017.
 */

public class Hero extends Sprite {
    //Standing Textures
    private TextureRegion standRight;
    private TextureRegion standLeft;
    private TextureRegion standBack;
    private TextureRegion standFront;

    //Animations
    public Animation<TextureRegion> heroWalkRight;
    public Animation<TextureRegion> heroWalkUp;
    public Animation<TextureRegion> heroWalkDown;
    public float upDownTimer;
    public float leftRightTimer;

    public World world;
    private HeroBody heroBody;
    private GameScreen screen;

    public Hero(GameScreen screen, int x, int y){
        super(screen.getAtlas().findRegion("hero_front"));
        this.screen=screen;
        this.world=screen.getWorld();
        //Movement States

        upDownTimer=0;
        leftRightTimer=0;

        //Animations
        heroAnimations(screen);

        //Hero Definition
        heroBody = new HeroBody(world, this,x,y);

        standLeft = new TextureRegion(screen.getAtlas().findRegion("hero_left"), 1, 1, 16, 21);
        standBack = new TextureRegion(screen.getAtlas().findRegion("hero_back"), 1, 1, 16, 21);
        standFront = new TextureRegion(screen.getAtlas().findRegion("hero_front"), 1, 1, 17, 22);
        standRight=new TextureRegion(screen.getAtlas().findRegion("hero_left"), 1, 1, 16, 21);
        standRight.flip(true, false);
        setBounds(0, 0, 17*MyGame.PIXEL_TO_METER, 22*MyGame.PIXEL_TO_METER);
        //setRegion(standFront);
    }

    private void heroAnimations(GameScreen screen) {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        //Up Animation

        for (int i = 0; i < 9; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("hero_walk_up"), i * 18, 0, 18, 24));
        }
        heroWalkUp = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        //Down Animation
        for (int i = 0; i < 9; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("hero_walk_down"), i * 18, 0, 18, 24));
        }
        heroWalkDown = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        //Right Animation
        for (int i = 0; i < 9; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("hero_walk_right"), i * 23, 0, 23, 23));
        }
        heroWalkRight = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
    }

    public void update(float dt){
        setPosition(heroBody.b2body.getPosition().x-getWidth()/2, heroBody.b2body.getPosition().y-getHeight()/2);
        setRegion(heroBody.getFrame(this,dt));
    }

    public void addItem(Item item) {
        if(item.getType()=="jewel"){
            addScore(((Jewel)item).getValue());
            Gdx.app.log("Hero got ","");
            String v = Integer.toString(getScore());
            Gdx.app.log(v,"");
        }
        else if(item.getType()=="heart"){
            screen.getGame().heroStats.setHearts(screen.getGame().heroStats.getHearts()+1);
            Gdx.app.log("Hero got 1 hearth.\n Current health: ","");
            String v = Integer.toString(getHealth());
            Gdx.app.log(v,"");
        }
        else if(item.getType()=="key"){

            screen.getGame().heroStats.setKeys(screen.getGame().heroStats.getKeys()+1);
            Gdx.app.log("Hero got key.\n","");
            String v = Integer.toString(getKeys());
            Gdx.app.log(v,"");
        }
        else{
            Gdx.app.log("Errror","");
        }

    }

    public void addScore(int value) {
        screen.getGame().heroStats.setScore(screen.getGame().heroStats.getScore()+value);
    }

    public int getScore(){
        return screen.getGame().heroStats.getScore();
    }

    public TextureRegion getStandRight(){
        return standRight;
    }

    public TextureRegion getStandLeft(){
        return standLeft;
    }

    public TextureRegion getStandBack(){
        return standBack;
    }

    public TextureRegion getStandFront(){
        return standFront;
    }

    public HeroBody getHeroBody() {
        return heroBody;
    }

    public int getHealth() {
        return screen.getGame().heroStats.getHearts();
    }

    public int hitBySpikes(){
        screen.getGame().heroStats.setHearts(screen.getGame().heroStats.getHearts()-1);
        return 1;
    }

    public int getKeys() {
        return screen.getGame().heroStats.getKeys();
    }
}
