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
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.Model.Entitys.Items.Item;
import com.mygdx.game.Model.Entitys.Weapons.Bomb;
import com.mygdx.game.MyGame;
import com.mygdx.game.Model.Entitys.Items.Jewel;

import java.util.ArrayList;

/**
 * Created by Utilizador on 05-04-2017.
 */

public class Hero{
    
    /** The Constant RESET_POS1X. */
    public static final float RESET_POS1X = 8+44-7-0.5f;
    
    /** The Constant RESET_POS1Y. */
    public static final float RESET_POS1Y = 8+20-6-0.5f;
    
    /** The Constant RESET_POS2X. */
    public static final float RESET_POS2X = 8+9-7-0.5f;
    
    /** The Constant RESET_POS2Y. */
    public static final float RESET_POS2Y = 8+40-6-0.5f;
    
    /** The position. */
    protected Vector2 position;
    
    /** The stand right. */
    private TextureRegion standRight;
    
    /** The stand left. */
    private TextureRegion standLeft;
    
    /** The stand back. */
    private TextureRegion standBack;
    
    /** The stand front. */
    private TextureRegion standFront;
    
    /** The hero walk right. */
    private Animation<TextureRegion> heroWalkRight;
    
    /** The hero walk up. */
    private Animation<TextureRegion> heroWalkUp;
    
    /** The hero walk down. */
    private Animation<TextureRegion> heroWalkDown;
    
    /** The hero dying. */
    private Animation<TextureRegion> heroDying;
    
    /** The hero hurt. */
    private Animation<TextureRegion> heroHurt;
    
    /** The up down timer. */
    private float upDownTimer;
    
    /** The left right timer. */
    private float leftRightTimer;
    
    /** The world. */
    private World world;
    
    /** The hero body. */
    private HeroBody heroBody;
    
    /** The bombs. */
    private ArrayList<Bomb> bombs;
    
    /** The bomb. */
    private Bomb bomb;
    
    /** The logic controller. */
    private LogicController logicController;
    
    /** The throw bomb. */
    private boolean throwBomb;
    
    /** The add bomb. */
    private boolean addBomb;
    
    /** The bomb count. */
    private float bombCount;
    
    /** The bomb exploding. */
    private boolean bombExploding;
    
    /** The is in platform. */
    private boolean isInPlatform;
    
    /** The platform id. */
    private int platformId;
    
    /** The is in pitfall. */
    private boolean isInPitfall;
    
    /** The open chest. */
    private boolean openChest;
    
    /** The opened chest id. */
    private int openedChestId;
    
    /** The opened sign id. */
    private int openedSignId;
    
    /** The sign was opened. */
    private boolean signWasOpened;
    
    /** The was hit. */
    private boolean wasHit;
    
    /** The fell. */
    private boolean fell;
    
    /** The sprite. */
    private Sprite sprite;
    
    /** The sound hurt. */
    private Sound soundHurt;
    
    /** The sound dying. */
    private Sound soundDying;

    /**
     * Instantiates a new hero.
     *
     * @param logicController the logic controller
     * @param vec the vec
     */
    public Hero(LogicController logicController, Vector2 vec){
        this.setWorld(logicController.getWorld());
        this.logicController=logicController;
        sprite = new Sprite();
        position=vec;
        booleanDefinition();
        resetCounters();
        this.bombs=new ArrayList<Bomb>();
        if(!logicController.getGame().getIsTest())
            heroAnimations();
        heroBody = new HeroBody(logicController, this,vec);
        if(!logicController.getGame().getIsTest()) {
            heroStandingTextureLoad();
            standRight.flip(true, false);
            sprite.setBounds(0, 0, 17*MyGame.PIXEL_TO_METER, 22*MyGame.PIXEL_TO_METER);
        }
        soundHurt=  Gdx.audio.newSound(Gdx.files.internal("Sounds/hero_hurt.wav"));
        soundDying=  Gdx.audio.newSound(Gdx.files.internal("Sounds/hero_dying.wav"));
    }

    /**
     * Hero standing texture load.
     */
    private void heroStandingTextureLoad() {
        standLeft = new TextureRegion(logicController.getGame().getAssetManager().get("Game/hero_left.png", Texture.class), 1, 1, 16, 21);
        standBack = new TextureRegion(logicController.getGame().getAssetManager().get("Game/hero_back.png", Texture.class), 1, 1, 16, 21);
        standFront = new TextureRegion(logicController.getGame().getAssetManager().get("Game/hero_front.png", Texture.class), 1, 1, 17, 22);
        standRight = new TextureRegion(logicController.getGame().getAssetManager().get("Game/hero_left.png", Texture.class), 1, 1, 16, 21);
    }

    /**
     * Reset counters.
     */
    private void resetCounters() {
        bombCount=0;
        openedChestId=-1;
        openedSignId=-1;
        setPlatformId(-1);
        setUpDownTimer(0);
        setLeftRightTimer(0);
    }

    /**
     * Boolean definition.
     */
    private void booleanDefinition() {
        setInPlatform(false);
        setInPitfall(false);
        setOpenChest(false);
        setSignWasOpened(false);
        wasHit=false;
        setFell(false);
        setBombExploding(false);
        throwBomb=false;
        addBomb=true;
    }

    /**
     * Hero animations.
     */
    private void heroAnimations() {
        setHeroWalkUp(animationAssetLoad("Game/hero_walk_up.png", new Vector3(18,24,9)));
        setHeroWalkDown(animationAssetLoad("Game/hero_walk_down.png", new Vector3(18,24,9)));
        setHeroWalkRight(animationAssetLoad("Game/hero_walk_right.png", new Vector3(23,23,9)));

        setHeroHurt(animationAssetLoad("Game/hero_hurt.png", new Vector3(31,32,2)));
        setHeroDying(animationAssetLoad("Game/hero_dying.png", new Vector3(25,24,5)));
    }

    /**
     * Animation asset load.
     *
     * @param name the name
     * @param vec the vec
     * @return the animation
     */
    private Animation<TextureRegion> animationAssetLoad(String name, Vector3 vec) {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < (int)vec.z; i++) {
            frames.add(new TextureRegion(logicController.getGame().getAssetManager().get(name, Texture.class), i * (int)vec.x, 0, (int)vec.x, (int)vec.y));
        }
        Animation<TextureRegion> animation = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
        return animation;
    }

    /**
     * Update.
     *
     * @param dt the dt
     */
    public void update(float dt){
        if(isFell()){
            if(logicController.isD1blck())
                heroBody.getBody().setTransform(RESET_POS1X, RESET_POS1Y, 0);
            else if(!logicController.isD1blck())
                heroBody.getBody().setTransform(RESET_POS2X, RESET_POS2Y, 0);
            setFell(false);
        }
      else {
            if(!logicController.getGame().getIsTest()) {
                sprite.setRegion(heroBody.getFrame(this, dt));
                sprite.setPosition(heroBody.getBody().getPosition().x-sprite.getWidth()/2, heroBody.getBody().getPosition().y-sprite.getHeight()/2);
            }else {
                position=new Vector2(heroBody.getBody().getPosition().x, heroBody.getBody().getPosition().y);
            }
       }
        heroBombUpdate(dt);
    }

    /**
     * Hero bomb update.
     *
     * @param dt the dt
     */
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

    /**
     * Adds the item.
     *
     * @param item the item
     */
    public void addItem(Item item) {
        if(item.getType()=="jewel")
            addScore(((Jewel)item).getValue());
        else if(item.getType()=="heart")
            logicController.getGame().getHeroStats().setHearts(logicController.getGame().getHeroStats().getHearts()+1);
        else if(item.getType()=="volcano_ruby")
            logicController.getGame().getHeroStats().gotVolcanoRuby();
    }

    /**
     * Adds the score.
     *
     * @param value the value
     */
    public void addScore(int value) {
        logicController.getGame().getHeroStats().setScore(logicController.getGame().getHeroStats().getScore()+value);
    }

    /**
     * Gets the stand right.
     *
     * @return the stand right
     */
    public TextureRegion getStandRight(){
        return standRight;
    }

    /**
     * Gets the stand left.
     *
     * @return the stand left
     */
    public TextureRegion getStandLeft(){
        return standLeft;
    }

    /**
     * Gets the stand back.
     *
     * @return the stand back
     */
    public TextureRegion getStandBack(){
        return standBack;
    }

    /**
     * Gets the stand front.
     *
     * @return the stand front
     */
    public TextureRegion getStandFront(){
        return standFront;
    }

    /**
     * Gets the hero body.
     *
     * @return the hero body
     */
    public HeroBody getHeroBody() {
        return heroBody;
    }

    /**
     * Gets the health.
     *
     * @return the health
     */
    public int getHealth() {
        return logicController.getGame().getHeroStats().getHearts();
    }

    /**
     * Hit.
     */
    public void hit(){
        Gdx.input.vibrate(MyGame.VIBRATION);
        logicController.getGame().getHeroStats().setHearts(logicController.getGame().getHeroStats().getHearts()-1);
        wasHit=true;
    }

    /**
     * Fall.
     */
    public void fall(){
        Gdx.input.vibrate(MyGame.VIBRATION);
        soundHurt.play(MyGame.SOUND_VOLUME);
        logicController.getGame().getHeroStats().setHearts(logicController.getGame().getHeroStats().getHearts()-1);
        setFell(true);
    }

    /**
     * Throw bomb.
     */
    public void throwBomb() {
        if(!addBomb)
            return;
        throwBomb=true;
        Vector2 v1 = getNewBombPosition();
        setBomb(new Bomb(logicController,this,new Vector2(0,0)));
        getBomb().setNewPosition(v1.x*MyGame.PIXEL_TO_METER,v1.y*MyGame.PIXEL_TO_METER);
        bombs.add(getBomb());
        addBomb=false;
    }

    /**
     * Gets the new bomb position.
     *
     * @return the new bomb position
     */
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

    /**
     * Draw.
     */
    public void draw(){
        sprite.draw(logicController.getGame().getBatch());
    }

    /**
     * Gets the x.
     *
     * @return the x
     */
    public float getX(){
        return sprite.getX();
    }

    /**
     * Gets the y.
     *
     * @return the y
     */
    public float getY(){
        return sprite.getY();
    }

    /**
     * Gets the sprite.
     *
     * @return the sprite
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Gets the bombs.
     *
     * @return the bombs
     */
    public ArrayList<Bomb> getBombs() {
        return bombs;
    }

    /**
     * Gets the throw bomb.
     *
     * @return the throw bomb
     */
    public boolean getThrowBomb() {
        return throwBomb;
    }

    /**
     * Gets the adds the bomb.
     *
     * @return the adds the bomb
     */
    public boolean getAddBomb() {
        return addBomb;
    }

    /**
     * Sets the checks if is in platform.
     *
     * @param val the new checks if is in platform
     */
    public void setIsInPlatform(boolean val){
        setInPlatform(val);
    }

    /**
     * Gets the checks if is in platform.
     *
     * @return the checks if is in platform
     */
    public boolean getIsInPlatform(){
        return isInPlatform();
    }

    /**
     * Sets the opened chest id.
     *
     * @param id the new opened chest id
     */
    public void setOpenedChestId(int id) {
        this.openedChestId=id;
    }

    /**
     * Gets the opened chest id.
     *
     * @return the opened chest id
     */
    public int getOpenedChestId() {
        return openedChestId;
    }

    /**
     * Gets the opened sign id.
     *
     * @return the opened sign id
     */
    public int getOpenedSignId() {
        return openedSignId;
    }

    /**
     * Sets the opened sign id.
     *
     * @param openedSignId the new opened sign id
     */
    public void setOpenedSignId(int openedSignId) {
        this.openedSignId = openedSignId;
    }

    /**
     * Gets the was hit.
     *
     * @return the was hit
     */
    public boolean getWasHit() {
        return wasHit;
    }

    /**
     * Sets the was hit.
     *
     * @param var the new was hit
     */
    public void setWasHit(boolean var) {
        wasHit=var;
    }

    /**
     * Sets the platform id.
     *
     * @param platformId the new platform id
     */
    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    /**
     * Gets the sound hurt.
     *
     * @return the sound hurt
     */
    public Sound getSoundHurt() {
        return soundHurt;
    }

    /**
     * Gets the sound dying.
     *
     * @return the sound dying
     */
    public Sound getSoundDying() {
        return soundDying;
    }

    /**
     * Gets the hero walk right.
     *
     * @return the hero walk right
     */
    public Animation<TextureRegion> getHeroWalkRight() {
        return heroWalkRight;
    }

    /**
     * Sets the hero walk right.
     *
     * @param heroWalkRight the new hero walk right
     */
    public void setHeroWalkRight(Animation<TextureRegion> heroWalkRight) {
        this.heroWalkRight = heroWalkRight;
    }

    /**
     * Gets the hero walk up.
     *
     * @return the hero walk up
     */
    public Animation<TextureRegion> getHeroWalkUp() {
        return heroWalkUp;
    }

    /**
     * Sets the hero walk up.
     *
     * @param heroWalkUp the new hero walk up
     */
    public void setHeroWalkUp(Animation<TextureRegion> heroWalkUp) {
        this.heroWalkUp = heroWalkUp;
    }

    /**
     * Gets the hero walk down.
     *
     * @return the hero walk down
     */
    public Animation<TextureRegion> getHeroWalkDown() {
        return heroWalkDown;
    }

    /**
     * Sets the hero walk down.
     *
     * @param heroWalkDown the new hero walk down
     */
    public void setHeroWalkDown(Animation<TextureRegion> heroWalkDown) {
        this.heroWalkDown = heroWalkDown;
    }

    /**
     * Gets the hero dying.
     *
     * @return the hero dying
     */
    public Animation<TextureRegion> getHeroDying() {
        return heroDying;
    }

    /**
     * Sets the hero dying.
     *
     * @param heroDying the new hero dying
     */
    public void setHeroDying(Animation<TextureRegion> heroDying) {
        this.heroDying = heroDying;
    }

    /**
     * Gets the hero hurt.
     *
     * @return the hero hurt
     */
    public Animation<TextureRegion> getHeroHurt() {
        return heroHurt;
    }

    /**
     * Sets the hero hurt.
     *
     * @param heroHurt the new hero hurt
     */
    public void setHeroHurt(Animation<TextureRegion> heroHurt) {
        this.heroHurt = heroHurt;
    }

    /**
     * Gets the up down timer.
     *
     * @return the up down timer
     */
    public float getUpDownTimer() {
        return upDownTimer;
    }

    /**
     * Sets the up down timer.
     *
     * @param upDownTimer the new up down timer
     */
    public void setUpDownTimer(float upDownTimer) {
        this.upDownTimer = upDownTimer;
    }

    /**
     * Gets the left right timer.
     *
     * @return the left right timer
     */
    public float getLeftRightTimer() {
        return leftRightTimer;
    }

    /**
     * Sets the left right timer.
     *
     * @param leftRightTimer the new left right timer
     */
    public void setLeftRightTimer(float leftRightTimer) {
        this.leftRightTimer = leftRightTimer;
    }

    /**
     * Gets the world.
     *
     * @return the world
     */
    public World getWorld() {
        return world;
    }

    /**
     * Sets the world.
     *
     * @param world the new world
     */
    public void setWorld(World world) {
        this.world = world;
    }

    /**
     * Gets the bomb.
     *
     * @return the bomb
     */
    public Bomb getBomb() {
        return bomb;
    }

    /**
     * Sets the bomb.
     *
     * @param bomb the new bomb
     */
    public void setBomb(Bomb bomb) {
        this.bomb = bomb;
    }

    /**
     * Sets the bomb exploding.
     *
     * @param bombExploding the new bomb exploding
     */
    public void setBombExploding(boolean bombExploding) {
        this.bombExploding = bombExploding;
    }

    /**
     * Checks if is in platform.
     *
     * @return true, if is in platform
     */
    public boolean isInPlatform() {
        return isInPlatform;
    }

    /**
     * Sets the in platform.
     *
     * @param inPlatform the new in platform
     */
    public void setInPlatform(boolean inPlatform) {
        isInPlatform = inPlatform;
    }


    /**
     * Checks if is in pitfall.
     *
     * @return true, if is in pitfall
     */
    public boolean isInPitfall() {
        return isInPitfall;
    }

    /**
     * Sets the in pitfall.
     *
     * @param inPitfall the new in pitfall
     */
    public void setInPitfall(boolean inPitfall) {
        isInPitfall = inPitfall;
    }


    /**
     * Sets the open chest.
     *
     * @param openChest the new open chest
     */
    public void setOpenChest(boolean openChest) {
        this.openChest = openChest;
    }

    /**
     * Checks if is sign was opened.
     *
     * @return true, if is sign was opened
     */
    public boolean isSignWasOpened() {
        return signWasOpened;
    }

    /**
     * Sets the sign was opened.
     *
     * @param signWasOpened the new sign was opened
     */
    public void setSignWasOpened(boolean signWasOpened) {
        this.signWasOpened = signWasOpened;
    }

    /**
     * Checks if is fell.
     *
     * @return true, if is fell
     */
    public boolean isFell() {
        return fell;
    }

    /**
     * Sets the fell.
     *
     * @param fell the new fell
     */
    public void setFell(boolean fell) {
        this.fell = fell;
    }

}
