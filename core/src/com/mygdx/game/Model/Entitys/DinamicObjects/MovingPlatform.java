package com.mygdx.game.Model.Entitys.DinamicObjects;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Controller.Entitys.DinamicObjects.MovingPlatformBody;
import com.mygdx.game.Controller.Entitys.Hero.HeroBody;
import com.mygdx.game.Model.Entitys.Hero.Hero;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

import static sun.misc.VM.getState;

/**
 * Created by Utilizador on 20-05-2017.
 */

public class MovingPlatform extends Sprite {
    private GameScreen screen;
    private World world;
    private Animation<TextureRegion> movingDown;
    private Animation<TextureRegion> movingUp;
    private Animation<TextureRegion> movingLeft;
    private MovingPlatformBody platformBody;
    private int id;
    private float moving_timer;
    private int type;

    public enum State {UP, DOWN, LEFT, RIGHT};
    public State currentState;
    public State previousState;

    public MovingPlatform(GameScreen screen, Vector2 vec, int type) {
        this.world = screen.getWorld();
        this.screen=screen;
        this.type=type;
        id = 0;
        moving_timer=0;
        platformBody = new MovingPlatformBody(world, this, vec);
        loadAnimation();
        setBounds(0, 0, 32 * MyGame.PIXEL_TO_METER, 32 * MyGame.PIXEL_TO_METER);
        setPosition(vec.x, vec.y);
    }

    private void loadAnimation() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(screen.getGame().getAssetManager().get("Game/moving_platform_down.png", Texture.class), i * 32, 0, 32, 32));
        }
        movingDown = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(screen.getGame().getAssetManager().get("Game/moving_platform_up.png", Texture.class), i * 32, 0, 32, 32));
        }
        movingUp = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(screen.getGame().getAssetManager().get("Game/moving_platform_left.png", Texture.class), 0, i * 32, 32, 32));
        }
        movingLeft = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
    }

    public void update(float dt) {
        float speed;
        if(Gdx.app.getType() == Application.ApplicationType.Android)
            speed=MyGame.PLATFORM_VELOCITY_ANDROID;
        else speed=MyGame.PLATFORM_VELOCITY_PC;
        movementType(dt, speed);
        setPosition(platformBody.getBody().getPosition().x - getWidth() / 2, platformBody.getBody().getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

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

    private void thirdPitfall(float dt, float speed) {
        if (platformBody.getBody().getPosition().x >= 42)
            platformBody.getBody().setLinearVelocity(new Vector2(-speed * dt, 0));
        else if (platformBody.getBody().getPosition().x <= 30)
            platformBody.getBody().setLinearVelocity(new Vector2(speed * dt, 0));
    }

    private void secondPitfall(float dt, float speed) {
        if (platformBody.getBody().getPosition().x >= 25.5)
            platformBody.getBody().setLinearVelocity(new Vector2(-speed * dt, 0));
        else if (platformBody.getBody().getPosition().x <= 13)
            platformBody.getBody().setLinearVelocity(new Vector2(speed * dt, 0));
    }

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

    private void timerUpdate(float dt) {
        if(previousState==currentState){
            moving_timer+=(dt/2);
        }else{
            moving_timer=0;
        }
        previousState=currentState;
    }

    public int getId() {
        return id;
    }

    public void setId(int n) {
        id = n;
    }
}
