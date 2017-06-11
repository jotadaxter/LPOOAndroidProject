package com.mygdx.game.Model.Entitys.Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Controller.Entitys.Weapons.BombBody;
import com.mygdx.game.Controller.Entitys.Weapons.ExplosionBody;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.Model.Entitys.Hero.Hero;
import com.mygdx.game.MyGame;

/**
 * Created by Jotadaxter on 18/05/2017.
 */

public class Bomb{
    
    /** The Constant MAX_TIMING. */
    public static final float MAX_TIMING=2.575f;
    
    /** The hero. */
    private Hero hero;
    
    /** The world. */
    private World world;
    
    /** The to destroy. */
    private boolean toDestroy;
    
    /** The destroyed. */
    private boolean destroyed;
    
    /** The bomb body. */
    private BombBody bombBody;
    
    /** The explosion body. */
    private ExplosionBody explosionBody;
    
    /** The sound timer. */
    private float soundTimer;
    
    /**
     * The Enum State.
     */
    public enum State {
/** The tic tac. */
TIC_TAC, 
 /** The boom. */
 BOOM}
    
    /** The current state. */
    private State currentState;
    
    /** The previous state. */
    private State previousState;

    /** The blue. */
    //textures
    private TextureRegion blue;
    
    /** The red. */
    private TextureRegion red;
    
    /** The tic tac. */
    //Animations
    private Animation<TextureRegion> tic_tac;
    
    /** The boom. */
    private Animation<TextureRegion> boom;
    
    /** The timer. */
    private float timer;
    
    /** The tic tac timer. */
    private float tic_tac_timer;
    
    /** The sound 1. */
    private Sound sound1;
    
    /** The sound 2. */
    private Sound sound2;
    
    /** The sprite. */
    private Sprite sprite;
    
    /** The logic controller. */
    private LogicController logicController;
    
    /** The position. */
    private Vector2 position;

    /**
     * Instantiates a new bomb.
     *
     * @param logicController the logic controller
     * @param hero the hero
     * @param vec the vec
     */
    public Bomb(LogicController logicController, Hero hero, Vector2 vec) {
        this.world= logicController.getWorld();
        this.hero=hero;
        bombBody= new BombBody(world,new Vector2( vec.x,vec.y));
        this.logicController=logicController;
        position=vec;
        sprite= new Sprite();
        toDestroy=false;
        destroyed=false;
        setTimer(0);
        explosionBody= new ExplosionBody(world,new Vector2(0,0));
        tic_tac_timer=0;
        soundTimer=0;
        if(!logicController.getGame().getIsTest()) {
            textureLoad();
            setCurrentState(State.TIC_TAC);
            animationLoad();
            sprite.setRegion(blue);
            sprite.setBounds(0, 0, 12 * MyGame.PIXEL_TO_METER, 16 * MyGame.PIXEL_TO_METER);
        }
        sound1=Gdx.audio.newSound(Gdx.files.internal("Sounds/bomb_tic_tac.wav"));
        sound2=Gdx.audio.newSound(Gdx.files.internal("Sounds/bomb_boom.wav"));
    }

    /**
     * Animation load.
     */
    private void animationLoad() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(blue);
        frames.add(red);
        tic_tac = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 11; i++) {
            frames.add(new TextureRegion(logicController.getGame().getAssetManager().get("Game/explosion.png", Texture.class), i * 47, 0, 47, 51));
        }
        boom = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

    }

    /**
     * Texture load.
     */
    private void textureLoad() {
        blue= new TextureRegion(logicController.getGame().getAssetManager().get("Game/bombs.png", Texture.class), 0,0,12,16);
        red= new TextureRegion(logicController.getGame().getAssetManager().get("Game/bombs.png", Texture.class), 12,0,12,16);
    }

    /**
     * Destroy.
     */
    public void destroy() {
        toDestroy=true;
    }

    /**
     * Update.
     *
     * @param dt the dt
     */
    public void update(float dt) {
        if(toDestroy && !destroyed){
            world.destroyBody(bombBody.getBody());
            world.destroyBody(explosionBody.getBody());
            destroyed=true;
        }
        setTimer(getTimer() + dt);
        soundUpdate();
        if(!logicController.getGame().getIsTest()) {
            if(this.getTimer() <MAX_TIMING)
                sprite.setRegion(getFrame(dt));
            else destroy();
            sprite.setPosition(bombBody.getBody().getPosition().x-sprite.getWidth()/2, bombBody.getBody().getPosition().y-sprite.getHeight()/2);
        }else {
            position=new Vector2(bombBody.getBody().getPosition().x, bombBody.getBody().getPosition().y);
        }
    }

    /**
     * Sound update.
     */
    private void soundUpdate() {
        if(soundTimer>=0)
            soundTimer+=Gdx.graphics.getDeltaTime();
        if(soundTimer>0 && soundTimer<2){
            sound1.play(MyGame.SOUND_VOLUME);
        }
        else if(soundTimer>=2){
            Gdx.input.vibrate(MyGame.VIBRATION);
            sound1.stop();
            sound2.play(MyGame.SOUND_VOLUME);
            soundTimer=-1;
        }
    }

    /**
     * Draw.
     *
     * @param batch the batch
     */
    public void draw(Batch batch){
        if(!destroyed)
            sprite.draw(batch);
    }

    /**
     * Gets the body.
     *
     * @return the body
     */
    public Body getBody(){
        return bombBody.getBody();
    }

    /**
     * Sets the new position.
     *
     * @param x the x
     * @param y the y
     */
    public void setNewPosition(float x, float y){
        bombBody.getBody().setTransform(x,y,0);
        explosionBody.getBody().setTransform(x,y,0);
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public State getState() {
        if(getTimer() <2){
            hero.setBombExploding(false);
            return State.TIC_TAC;
        }
        else{
            hero.setBombExploding(true);
            return State.BOOM;
        }
    }

    /**
     * Gets the frame.
     *
     * @param dt the dt
     * @return the frame
     */
    public TextureRegion getFrame(float dt) {
        setCurrentState(getState());
        TextureRegion region = getRegion();
        timerUpdate(dt);
        return region;
    }

    /**
     * Gets the region.
     *
     * @return the region
     */
    public TextureRegion getRegion() {
        TextureRegion region= new TextureRegion();
        if(getCurrentState() ==State.TIC_TAC){
            sprite.setBounds(sprite.getX(), sprite.getY(), 12*MyGame.PIXEL_TO_METER, 16*MyGame.PIXEL_TO_METER);
            region = tic_tac.getKeyFrame(tic_tac_timer, true);
        }else if(getCurrentState() ==State.BOOM){
            sprite.setBounds(sprite.getX(), sprite.getY(), 47*MyGame.PIXEL_TO_METER, 51*MyGame.PIXEL_TO_METER);
            region = boom.getKeyFrame(tic_tac_timer, false);
        }
        return region;
    }

    /**
     * Timer update.
     *
     * @param dt the dt
     */
    private void timerUpdate(float dt) {
        tic_tac_timer= getCurrentState() == previousState ?tic_tac_timer + dt : 0;
        if(previousState== getCurrentState()){
            tic_tac_timer+=(dt);
        }else{
            tic_tac_timer=0;
        }
        previousState= getCurrentState();
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
     * Gets the current state.
     *
     * @return the current state
     */
    public State getCurrentState() {
        return currentState;
    }

    /**
     * Sets the current state.
     *
     * @param currentState the new current state
     */
    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    /**
     * Gets the timer.
     *
     * @return the timer
     */
    public float getTimer() {
        return timer;
    }

    /**
     * Sets the timer.
     *
     * @param timer the new timer
     */
    public void setTimer(float timer) {
        this.timer = timer;
    }

}
