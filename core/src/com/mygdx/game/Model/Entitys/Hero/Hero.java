package com.mygdx.game.Model.Entitys.Hero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Controller.Entitys.Hero.HeroBody;
import com.mygdx.game.Model.Entitys.Items.Item;
import com.mygdx.game.Model.Entitys.Weapons.Bomb;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;
import com.mygdx.game.Model.Entitys.Items.Jewel;

import java.util.ArrayList;

/**
 * Created by Utilizador on 05-04-2017.
 */

public class Hero extends Sprite{
    public static final int RESET_POSX = 8+44*16;
    public static final int RESET_POSY = 8+21*16;

    //Standing Textures
    private TextureRegion standRight;
    private TextureRegion standLeft;
    private TextureRegion standBack;
    private TextureRegion standFront;

    //Animations
    public Animation<TextureRegion> heroWalkRight;
    public Animation<TextureRegion> heroWalkUp;
    public Animation<TextureRegion> heroWalkDown;
    public Animation<TextureRegion> heroDying;
    public Animation<TextureRegion> heroHurt;
    public float upDownTimer;
    public float leftRightTimer;

    public World world;
    private HeroBody heroBody;
    private GameScreen screen;
    private ArrayList<Bomb> bombs;
    public Bomb bomb;
    private boolean throwBomb;
    private boolean addBomb;
    private float bombCount;
    public boolean bombExploding;
    public boolean isInPlatform;
    public int platformId;
    public boolean isInPitfall;
    public boolean openChest;
    private int openedChestId;
    private boolean openLog;
    private int openedSignId;
    public boolean signWasOpened;
    private boolean wasHit;
    private boolean fell;

    private Sound soundFalling;
    private Sound soundHurt;
    private Sound soundDying;

    public Hero(GameScreen screen, int x, int y){
        super(screen.getAtlas().findRegion("hero_front"));
        this.screen=screen;
        this.world=screen.getWorld();
        bombCount=0;
        isInPlatform=false;
        isInPitfall=false;
        openChest=false;
        openedChestId=-1;
        openLog=false;
        openedSignId=-1;
        signWasOpened=false;
        wasHit=false;
        platformId=-1;
        fell=false;
       // bomb= new Bomb(world,this,0,0);
        setBombExploding(false);
        //Movement States
        this.bombs=new ArrayList<Bomb>();
        upDownTimer=0;
        leftRightTimer=0;
        throwBomb=false;
        addBomb=true;
        //Animations
        heroAnimations(screen);

        //Hero Definition
        heroBody = new HeroBody(screen, this,x,y);

        standLeft = new TextureRegion(screen.getAtlas().findRegion("hero_left"), 1, 1, 16, 21);
        standBack = new TextureRegion(screen.getAtlas().findRegion("hero_back"), 1, 1, 16, 21);
        standFront = new TextureRegion(screen.getAtlas().findRegion("hero_front"), 1, 1, 17, 22);
        standRight = new TextureRegion(screen.getAtlas().findRegion("hero_left"), 1, 1, 16, 21);
        standRight.flip(true, false);
        setBounds(0, 0, 17*MyGame.PIXEL_TO_METER, 22*MyGame.PIXEL_TO_METER);
        soundHurt=  Gdx.audio.newSound(Gdx.files.internal("Sounds/hero_hurt.wav"));
        soundDying=  Gdx.audio.newSound(Gdx.files.internal("Sounds/hero_dying.wav"));
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

        //Hurt Animation
        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(screen.getGame().assetManager.get("Game/hero_hurt.png", Texture.class), i * 31, 0, 31, 32));
        }
        heroHurt = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        //Dying Animation
        for (int i = 0; i < 5; i++) {
            frames.add(new TextureRegion(screen.getGame().assetManager.get("Game/hero_dying.png", Texture.class), i * 25, 0, 24, 25));
        }
        heroDying = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
    }

    public void update(float dt){
        if(fell){
            heroBody.b2body.setTransform(RESET_POSX , RESET_POSY, 0);
            fell=false;
        }
      else setPosition(heroBody.b2body.getPosition().x-getWidth()/2, heroBody.b2body.getPosition().y-getHeight()/2);
        setRegion(heroBody.getFrame(this,dt));
        if(!addBomb)
            bombCount+=dt;
        if(bombCount>=1){
            bombCount=0;
            addBomb=true;
        }
        
        if(throwBomb){
            for(Bomb bomb: bombs){
                bomb.update(dt);
                /*if(bomb.exploded()) {
                    bombs.remove(bomb);
                    screen.getBombPool().free(bomb);
//                }*/
            }
        }
    }

    public void addItem(Item item) {
        if(item.getType()=="jewel"){
            addScore(((Jewel)item).getValue());
            String v = Integer.toString(getScore());
            Gdx.app.log("Hero got ",v);
        }
        else if(item.getType()=="heart"){
            screen.getGame().heroStats.setHearts(screen.getGame().heroStats.getHearts()+1);
            String v = Integer.toString(getHealth());
            Gdx.app.log("Hero got 1 hearth.\n Current health: ",v);
        }
        else if(item.getType()=="key"){

            screen.getGame().heroStats.setKeys(screen.getGame().heroStats.getKeys()+1);
            String v = Integer.toString(getKeys());
            Gdx.app.log("Hero got key.\n",v);
        }
        else if(item.getType()=="volcano_ruby"){
            screen.getGame().heroStats.gotVolcanoRuby();
            Gdx.app.log("Picked Up Volcano Ruby", "");
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

    public void hit(){
        /*if(bombs.get(0).timer>2)
            System.out.println("hit");*/
        screen.getGame().heroStats.setHearts(screen.getGame().heroStats.getHearts()-1);
        wasHit=true;
    }

    public void fall(){
        System.out.println(heroBody.b2body.getPosition().x);
        screen.getGame().heroStats.setHearts(screen.getGame().heroStats.getHearts()-1);
        fell=true;
    }

    public int getKeys() {
        return screen.getGame().heroStats.getKeys();
    }

    public void throwBomb() {
        if(!addBomb)
            return;
        throwBomb=true;
        float xx=heroBody.getBody().getPosition().x*16, yy=heroBody.getBody().getPosition().y*16;
        switch(heroBody.currentState){
            case STAND_UP:{
                yy+=16;
            }
            break;
            case STAND_DOWN:{
                yy-=16;
            }
            break;
            case STAND_RIGHT:{
                xx+=16;
            }break;
            case STAND_LEFT:{
                xx-=16;
            }
            break;
            case WALK_UP:{
                yy+=16;
            }
            break;
            case WALK_DOWN:{
                yy-=16;
            }
            break;
            case WALK_RIGHT:{
                xx+=16;
            }break;
            case WALK_LEFT:{
                xx-=16;
            }
            break;

        }
        //bomb= screen.getBombPool().obtain();
        bomb= new Bomb(screen,this,0,0);
        bomb.setNewPosition(xx*MyGame.PIXEL_TO_METER,yy*MyGame.PIXEL_TO_METER);
        bombs.add(bomb);
        addBomb=false;
    }

    public ArrayList<Bomb> getBombs() {
        return bombs;
    }

    public boolean getThrowBomb() {
        return throwBomb;
    }

    public void switchAddBomb() {
        addBomb=true;
    }

    public boolean getAddBomb() {
        return addBomb;
    }

    public boolean isBombExploding() {
        return bombExploding;
    }

    public void setBombExploding(boolean bombExploding) {
        this.bombExploding = bombExploding;
    }

    public void setIsInPlatform(boolean val){
        isInPlatform=val;
    }

    public boolean getIsInPlatform(){
        return isInPlatform;
    }

    public void setOpenedChestId(int id) {
        this.openedChestId=id;
    }

    public GameScreen getScreen() {
        return screen;
    }

    public int getOpenedChestId() {
        return openedChestId;
    }

    public int getOpenedSignId() {
        return openedSignId;
    }

    public boolean isOpenLog() {
        return openLog;
    }

    public void setOpenLog(boolean openLog) {
        this.openLog = openLog;
    }

    public void setOpenedSignId(int openedSignId) {
        this.openedSignId = openedSignId;
    }

    public boolean getWasHit() {
        return wasHit;
    }

    public void setWasHit(boolean var) {
        wasHit=var;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public Sound getSoundHurt() {
        return soundHurt;
    }

    public Sound getSoundDying() {
        return soundDying;
    }
}
