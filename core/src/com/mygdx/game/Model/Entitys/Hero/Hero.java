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
    private Animation<TextureRegion> heroWalkRight;
    private Animation<TextureRegion> heroWalkUp;
    private Animation<TextureRegion> heroWalkDown;
    private Animation<TextureRegion> heroDying;
    private Animation<TextureRegion> heroHurt;
    private float upDownTimer;
    private float leftRightTimer;

    private World world;
    private HeroBody heroBody;
    private GameScreen screen;
    private ArrayList<Bomb> bombs;
    private Bomb bomb;

    private boolean throwBomb;
    private boolean addBomb;
    private float bombCount;
    private boolean bombExploding;
    private boolean isInPlatform;
    private int platformId;
    private boolean isInPitfall;
    private boolean openChest;
    private int openedChestId;
    private int openedSignId;
    private boolean signWasOpened;
    private boolean wasHit;
    private boolean fell;

    private Sound soundHurt;
    private Sound soundDying;
    private boolean fallAnimationOn;

    public Hero(GameScreen screen, Vector2 vec){
        super(screen.getAtlas().findRegion("hero_front"));
        this.screen=screen;
        this.setWorld(screen.getWorld());
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
        setPlatformId(-1);
        setUpDownTimer(0);
        setLeftRightTimer(0);
    }

    private void booleanDefinition() {
        setInPlatform(false);
        setInPitfall(false);
        setOpenChest(false);
        setSignWasOpened(false);
        wasHit=false;
        setFell(false);
        setFallAnimationOn(false);
        setBombExploding(false);
        throwBomb=false;
        addBomb=true;
    }

    private void heroAnimations() {
        setHeroWalkUp(animationAtlasLoad("hero_walk_up", new Vector3(18,24,9)));
        setHeroWalkDown(animationAtlasLoad("hero_walk_down", new Vector3(18,24,9)));
        setHeroWalkRight(animationAtlasLoad("hero_walk_right", new Vector3(23,23,9)));

        setHeroHurt(animationAssetLoad("Game/hero_hurt.png", new Vector3(31,32,2)));
        setHeroDying(animationAssetLoad("Game/hero_dying.png", new Vector3(25,24,5)));
    }

    private Animation<TextureRegion> animationAssetLoad(String name, Vector3 vec) {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < (int)vec.z; i++) {
            frames.add(new TextureRegion(screen.getGame().getAssetManager().get(name, Texture.class), i * (int)vec.x, 0, (int)vec.x, (int)vec.y));
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
        if(isFell()){
            setFallAnimationOn(true);
            if(screen.isD1blck())
                heroBody.getBody().setTransform(RESET_POS1X, RESET_POS1Y, 0);
            else if(!screen.isD1blck())
                heroBody.getBody().setTransform(RESET_POS2X, RESET_POS2Y, 0);
            setFell(false);
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
        if(item.getType()=="jewel")
            addScore(((Jewel)item).getValue());
        else if(item.getType()=="heart")
            screen.getGame().getHeroStats().setHearts(screen.getGame().getHeroStats().getHearts()+1);
        else if(item.getType()=="volcano_ruby")
            screen.getGame().getHeroStats().gotVolcanoRuby();
    }

    public void addScore(int value) {
        screen.getGame().getHeroStats().setScore(screen.getGame().getHeroStats().getScore()+value);
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
        return screen.getGame().getHeroStats().getHearts();
    }

    public void hit(){
        Gdx.input.vibrate(MyGame.VIBRATION);
        screen.getGame().getHeroStats().setHearts(screen.getGame().getHeroStats().getHearts()-1);
        wasHit=true;
    }

    public void fall(){
        Gdx.input.vibrate(MyGame.VIBRATION);
        soundHurt.play(MyGame.SOUND_VOLUME);
        screen.getGame().getHeroStats().setHearts(screen.getGame().getHeroStats().getHearts()-1);
        setFell(true);
    }

    public void throwBomb() {
        if(!addBomb)
            return;
        throwBomb=true;
        Vector2 v1 = getNewBombPosition();
        setBomb(new Bomb(screen,this,new Vector2(0,0)));
        getBomb().setNewPosition(v1.x*MyGame.PIXEL_TO_METER,v1.y*MyGame.PIXEL_TO_METER);
        bombs.add(getBomb());
        addBomb=false;
    }

    private Vector2 getNewBombPosition() {
        float xx=heroBody.getBody().getPosition().x*16, yy=heroBody.getBody().getPosition().y*16;
        if(heroBody.getCurrentState() == HeroBody.State.STAND_UP || heroBody.getCurrentState() == HeroBody.State.WALK_UP)
            yy+=16;
        else if(heroBody.getCurrentState() == HeroBody.State.STAND_DOWN || heroBody.getCurrentState() == HeroBody.State.WALK_DOWN)
            yy-=16;
        else if(heroBody.getCurrentState() == HeroBody.State.STAND_RIGHT || heroBody.getCurrentState() == HeroBody.State.WALK_RIGHT)
            xx+=16;
        else if(heroBody.getCurrentState() == HeroBody.State.STAND_LEFT || heroBody.getCurrentState() == HeroBody.State.WALK_LEFT)
            xx-=16;
        return new Vector2(xx,yy);
    }

    public ArrayList<Bomb> getBombs() {
        return bombs;
    }

    public boolean getThrowBomb() {
        return throwBomb;
    }

    public boolean getAddBomb() {
        return addBomb;
    }

    public void setIsInPlatform(boolean val){
        setInPlatform(val);
    }

    public boolean getIsInPlatform(){
        return isInPlatform();
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

    public Animation<TextureRegion> getHeroWalkRight() {
        return heroWalkRight;
    }

    public void setHeroWalkRight(Animation<TextureRegion> heroWalkRight) {
        this.heroWalkRight = heroWalkRight;
    }

    public Animation<TextureRegion> getHeroWalkUp() {
        return heroWalkUp;
    }

    public void setHeroWalkUp(Animation<TextureRegion> heroWalkUp) {
        this.heroWalkUp = heroWalkUp;
    }

    public Animation<TextureRegion> getHeroWalkDown() {
        return heroWalkDown;
    }

    public void setHeroWalkDown(Animation<TextureRegion> heroWalkDown) {
        this.heroWalkDown = heroWalkDown;
    }

    public Animation<TextureRegion> getHeroDying() {
        return heroDying;
    }

    public void setHeroDying(Animation<TextureRegion> heroDying) {
        this.heroDying = heroDying;
    }

    public Animation<TextureRegion> getHeroHurt() {
        return heroHurt;
    }

    public void setHeroHurt(Animation<TextureRegion> heroHurt) {
        this.heroHurt = heroHurt;
    }

    public float getUpDownTimer() {
        return upDownTimer;
    }

    public void setUpDownTimer(float upDownTimer) {
        this.upDownTimer = upDownTimer;
    }

    public float getLeftRightTimer() {
        return leftRightTimer;
    }

    public void setLeftRightTimer(float leftRightTimer) {
        this.leftRightTimer = leftRightTimer;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Bomb getBomb() {
        return bomb;
    }

    public void setBomb(Bomb bomb) {
        this.bomb = bomb;
    }

    public boolean isBombExploding() {
        return bombExploding;
    }

    public void setBombExploding(boolean bombExploding) {
        this.bombExploding = bombExploding;
    }

    public boolean isInPlatform() {
        return isInPlatform;
    }

    public void setInPlatform(boolean inPlatform) {
        isInPlatform = inPlatform;
    }

    public int getPlatformId() {
        return platformId;
    }

    public boolean isInPitfall() {
        return isInPitfall;
    }

    public void setInPitfall(boolean inPitfall) {
        isInPitfall = inPitfall;
    }

    public boolean isOpenChest() {
        return openChest;
    }

    public void setOpenChest(boolean openChest) {
        this.openChest = openChest;
    }

    public boolean isSignWasOpened() {
        return signWasOpened;
    }

    public void setSignWasOpened(boolean signWasOpened) {
        this.signWasOpened = signWasOpened;
    }

    public boolean isFell() {
        return fell;
    }

    public void setFell(boolean fell) {
        this.fell = fell;
    }

    public boolean isFallAnimationOn() {
        return fallAnimationOn;
    }

    public void setFallAnimationOn(boolean fallAnimationOn) {
        this.fallAnimationOn = fallAnimationOn;
    }
}
