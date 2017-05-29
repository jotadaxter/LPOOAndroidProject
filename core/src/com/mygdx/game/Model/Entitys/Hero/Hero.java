package com.mygdx.game.Model.Entitys.Hero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Controller.Entitys.Hero.HeroBody;
import com.mygdx.game.Model.Entitys.Items.Item;
import com.mygdx.game.Model.Entitys.Weapons.Bomb;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;
import com.mygdx.game.Model.Entitys.Items.Jewel;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Utilizador on 05-04-2017.
 */

public class Hero extends Sprite{
    public static final float RESET_POS1X = 8+44-7-0.5f;
    public static final float RESET_POS1Y = 8+20-6-0.5f;

    public static final float RESET_POS2X = 8+9-7-0.5f;
    public static final float RESET_POS2Y = 8+40-6-0.5f;

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
    //public Animation<TextureRegion> heroFalling;
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
    public boolean fell;

    //private Sound soundFalling;
    private Sound soundHurt;
    private Sound soundDying;
    public boolean fallAnimationOn;

    public Hero(GameScreen screen, Vector2 vec){
        super(screen.getAtlas().findRegion("hero_front"));
        this.screen=screen;
        this.world=screen.getWorld();
        booleanDefinition();
        resetCounters();
        this.bombs=new ArrayList<Bomb>();
        heroAnimations();
        heroBody = new HeroBody(screen, this,vec);
        heroStandingTextureLoad();
        standRight.flip(true, false);
        setBounds(0, 0, 17*MyGame.PIXEL_TO_METER, 22*MyGame.PIXEL_TO_METER);
        soundHurt=  Gdx.audio.newSound(Gdx.files.internal("Sounds/hero_hurt.wav"));
        soundDying=  Gdx.audio.newSound(Gdx.files.internal("Sounds/hero_dying.wav"));
    }

    private void heroStandingTextureLoad() {
        standLeft = new TextureRegion(screen.getAtlas().findRegion("hero_left"), 1, 1, 16, 21);
        standBack = new TextureRegion(screen.getAtlas().findRegion("hero_back"), 1, 1, 16, 21);
        standFront = new TextureRegion(screen.getAtlas().findRegion("hero_front"), 1, 1, 17, 22);
        standRight = new TextureRegion(screen.getAtlas().findRegion("hero_left"), 1, 1, 16, 21);
    }

    private void resetCounters() {
        bombCount=0;
        openedChestId=-1;
        openedSignId=-1;
        platformId=-1;
        upDownTimer=0;
        leftRightTimer=0;
    }

    private void booleanDefinition() {
        isInPlatform=false;
        isInPitfall=false;
        openChest=false;
        openLog=false;
        signWasOpened=false;
        wasHit=false;
        fell=false;
        fallAnimationOn=false;
        bombExploding=false;
        throwBomb=false;
        addBomb=true;
    }

    private void heroAnimations() {
        heroWalkUp=animationAtlasLoad("hero_walk_up", new Vector3(18,24,9));
        heroWalkDown=animationAtlasLoad("hero_walk_down", new Vector3(18,24,9));
        heroWalkRight=animationAtlasLoad("hero_walk_right", new Vector3(23,23,9));

        heroHurt= animationAssetLoad("Game/hero_hurt.png", new Vector3(31,32,2));
        heroDying= animationAssetLoad("Game/hero_dying.png", new Vector3(25,24,5));
    }

    private Animation<TextureRegion> animationAssetLoad(String name, Vector3 vec) {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < (int)vec.z; i++) {
            frames.add(new TextureRegion(screen.getGame().assetManager.get(name, Texture.class), i * (int)vec.x, 0, (int)vec.x, (int)vec.y));
        }
        Animation<TextureRegion> animation = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
        return animation;
    }

    private Animation<TextureRegion> animationAtlasLoad(String path, Vector3 vec) {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < (int)vec.z; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion(path), i * (int)vec.x, 0, (int)vec.x, (int)vec.y));
        }
        Animation<TextureRegion> animation = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
        return animation;
    }

    public void update(float dt){
        if(fell){
            fallAnimationOn=true;
            if(screen.d1blck)
                heroBody.getBody().setTransform(RESET_POS1X , RESET_POS1Y, 0);
            else if(!screen.d1blck)
                heroBody.getBody().setTransform(RESET_POS2X , RESET_POS2Y, 0);
            fell=false;
        }
      else setPosition(heroBody.getBody().getPosition().x-getWidth()/2, heroBody.getBody().getPosition().y-getHeight()/2);
        setRegion(heroBody.getFrame(this,dt));
        heroBombUpdate(dt);
    }

    private void heroBombUpdate(float dt) {
        if(!addBomb)
            bombCount+=dt;
        if(bombCount>=1){
            bombCount=0;
            addBomb=true;
        }
        if(throwBomb){
            for(Bomb bomb: bombs){
                bomb.update(dt);
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
        screen.getGame().heroStats.setHearts(screen.getGame().heroStats.getHearts()-1);
        wasHit=true;
    }

    public void fall(){
        soundHurt.play();
        screen.getGame().heroStats.setHearts(screen.getGame().heroStats.getHearts()-1);
        fell=true;
    }

    public void throwBomb() {
        if(!addBomb)
            return;
        throwBomb=true;
        Vector2 v1 = getNewBombPosition();
        bomb= new Bomb(screen,this,new Vector2(0,0));
        bomb.setNewPosition(v1.x*MyGame.PIXEL_TO_METER,v1.y*MyGame.PIXEL_TO_METER);
        bombs.add(bomb);
        addBomb=false;
    }

    private Vector2 getNewBombPosition() {
        float xx=heroBody.getBody().getPosition().x*16, yy=heroBody.getBody().getPosition().y*16;
        if(heroBody.currentState == HeroBody.State.STAND_UP || heroBody.currentState == HeroBody.State.WALK_UP)
            yy+=16;
        else if(heroBody.currentState == HeroBody.State.STAND_DOWN || heroBody.currentState == HeroBody.State.WALK_DOWN)
            yy-=16;
        else if(heroBody.currentState == HeroBody.State.STAND_RIGHT || heroBody.currentState == HeroBody.State.WALK_RIGHT)
            xx+=16;
        else if(heroBody.currentState == HeroBody.State.STAND_LEFT || heroBody.currentState == HeroBody.State.WALK_LEFT)
            xx-=16;
        return new Vector2(xx,yy);
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
/*
    public Sound getSoundFalling() {
        return soundFalling;
    }*/
}
