package com.mygdx.game.Model.Entitys.DinamicObjects;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Controller.Entitys.DinamicObjects.MovingPlatformBody;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.MyGame;

/**
 * Created by Utilizador on 20-05-2017.
 */

public class MovingPlatform {
    
    /** The world. */
    private World world;
    
    /** The moving down. */
    private Animation<TextureRegion> movingDown;
    
    /** The moving up. */
    private Animation<TextureRegion> movingUp;
    
    /** The moving left. */
    private Animation<TextureRegion> movingLeft;
    
    /** The platform body. */
    private MovingPlatformBody platformBody;
    
    /** The id. */
    private int id;
    
    /** The moving timer. */
    private float moving_timer;
    
    /** The type. */
    private int type;
    
    /** The position. */
    private Vector2 position;
    
    /** The sprite. */
    private Sprite sprite;
    
    /** The logic controller. */
    private LogicController logicController;

    /**
     * The Enum State.
     */
    public enum State {/** The up. */
UP, /** The down. */
 DOWN, /** The left. */
 LEFT, /** The right. */
 RIGHT};
    
    /** The current state. */
    public State currentState;
    
    /** The previous state. */
    public State previousState;

    /**
     * Instantiates a new moving platform.
     *
     * @param logicController the logic controller
     * @param vec the vec
     * @param type the type
     */
    public MovingPlatform(LogicController logicController, Vector2 vec, int type) {
        this.world = logicController.getWorld();
        this.type=type;
        id = 0;
        moving_timer=0;
        position=vec;
        this.logicController=logicController;
        sprite=new Sprite();
        platformBody = new MovingPlatformBody(world, this, vec);
        if(!logicController.getGame().getIsTest()) {
            loadAnimation(logicController.getGame());
            sprite.setBounds(0, 0, 32 * MyGame.PIXEL_TO_METER, 32 * MyGame.PIXEL_TO_METER);
            sprite.setPosition(vec.x, vec.y);
        }
    }

    /**
     * Load animation.
     *
     * @param game the game
     */
    private void loadAnimation(MyGame game) {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(game.getAssetManager().get("Game/moving_platform_down.png", Texture.class), i * 32, 0, 32, 32));
        }
        movingDown = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(game.getAssetManager().get("Game/moving_platform_up.png", Texture.class), i * 32, 0, 32, 32));
        }
        movingUp = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(game.getAssetManager().get("Game/moving_platform_left.png", Texture.class), 0, i * 32, 32, 32));
        }
        movingLeft = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
    }

    /**
     * Update.
     *
     * @param dt the dt
     */
    public void update(float dt) {
        float speed;
        if(Gdx.app.getType() == Application.ApplicationType.Android)
            speed=MyGame.PLATFORM_VELOCITY_ANDROID;
        else speed=MyGame.PLATFORM_VELOCITY_PC;
        movementType(dt, speed);
        if(!logicController.getGame().getIsTest()) {
            sprite.setRegion(getFrame(dt));
            sprite.setPosition(platformBody.getBody().getPosition().x-sprite.getWidth()/2, platformBody.getBody().getPosition().y-sprite.getHeight()/2);
        }else {
            position=new Vector2(platformBody.getBody().getPosition().x, platformBody.getBody().getPosition().y);
        }
    }

    /**
     * Movement type.
     *
     * @param dt the dt
     * @param speed the speed
     */
    private void movementType(float dt, float speed) {
        if(type==0){
           firstPitfall(dt,speed);
        }
        else if(type==1){
            secondPitfall(dt,speed);
        }
        else if(type==2){
            thirdPitfall(dt,speed);
        }
    }

    /**
     * Third pitfall.
     *
     * @param dt the dt
     * @param speed the speed
     */
    private void thirdPitfall(float dt, float speed) {
        if (platformBody.getBody().getPosition().x >= 42)
            platformBody.getBody().setLinearVelocity(new Vector2(-speed * dt, 0));
        else if (platformBody.getBody().getPosition().x <= 30)
            platformBody.getBody().setLinearVelocity(new Vector2(speed * dt, 0));
    }

    /**
     * Second pitfall.
     *
     * @param dt the dt
     * @param speed the speed
     */
    private void secondPitfall(float dt, float speed) {
        if (platformBody.getBody().getPosition().x >= 25.5)
            platformBody.getBody().setLinearVelocity(new Vector2(-speed * dt, 0));
        else if (platformBody.getBody().getPosition().x <= 13)
            platformBody.getBody().setLinearVelocity(new Vector2(speed * dt, 0));
    }

    /**
     * First pitfall.
     *
     * @param dt the dt
     * @param speed the speed
     */
    private void firstPitfall(float dt, float speed) {
        if (platformBody.getBody().getPosition().y <= 23 && platformBody.getBody().getPosition().x >= 31) {
            platformBody.getBody().setLinearVelocity(new Vector2(-speed * dt, 0));
        } else if (platformBody.getBody().getPosition().x <= 31 && platformBody.getBody().getPosition().y <= 31) {
            platformBody.getBody().setLinearVelocity(new Vector2(0,speed * dt));
        } else if (platformBody.getBody().getPosition().y >= 31 && platformBody.getBody().getPosition().x <= 45) {
            platformBody.getBody().setLinearVelocity(new Vector2(speed * dt, 0));
        } else if (platformBody.getBody().getPosition().x >= 45 && platformBody.getBody().getPosition().y >= 23) {
            platformBody.getBody().setLinearVelocity(new Vector2(0, -speed * dt));
        }
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public State getState(){
        if(platformBody.getBody().getLinearVelocity().x<0)
            return State.LEFT;
        else if(platformBody.getBody().getLinearVelocity().x>0)
            return State.RIGHT;
        else if(platformBody.getBody().getLinearVelocity().y<0)
            return State.DOWN;
        else if(platformBody.getBody().getLinearVelocity().y>0)
            return State.UP;
        else
            return State.UP;
    }

    /**
     * Gets the frame.
     *
     * @param dt the dt
     * @return the frame
     */
    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region = new TextureRegion();
        if(currentState==State.UP)
            region=movingUp.getKeyFrame(moving_timer,true);
        else if(currentState==State.DOWN)
            region=movingDown.getKeyFrame(moving_timer,true);
        else if(currentState==State.LEFT)
            region=movingLeft.getKeyFrame(moving_timer,true);
        else region=movingLeft.getKeyFrame(moving_timer,true);
        timerUpdate(dt);
        return region;
    }

    /**
     * Timer update.
     *
     * @param dt the dt
     */
    private void timerUpdate(float dt) {
        if(previousState==currentState){
            moving_timer+=(dt/2);
        }else{
            moving_timer=0;
        }
        previousState=currentState;
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
     * @param n the new id
     */
    public void setId(int n) {
        id = n;
    }

    /**
     * Draw.
     *
     * @param batch the batch
     */
    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }

}
