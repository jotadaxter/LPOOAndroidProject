package com.mygdx.game.Controller.Entitys.Hero;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Controller;
import com.mygdx.game.Model.Entitys.Hero.Hero;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

import java.util.concurrent.TimeUnit;

/**
 * Created by Jotadaxter on 28/04/2017.
 */

public class HeroBody {
    public static final float DAMPING_NORMAL= 3f;
    private GameScreen screen;
    public boolean isInIce;
    public Body b2body;
    private FixtureDef fdef;
    private Hero hero;

    public enum State {WALK_UP, WALK_DOWN, WALK_LEFT, WALK_RIGHT, STAND_UP, STAND_DOWN, STAND_RIGHT, STAND_LEFT, HURT, GAME_OVER, DYING, FALLING};
    public State currentState;
    public State previousState;

    public HeroBody(GameScreen screen, Hero hero, float x, float y) {
        currentState = State.STAND_DOWN;
        previousState = State.STAND_DOWN;
        this.screen=screen;
        this.hero=hero;
        BodyDef bdef =  new BodyDef();
        bdef.position.set(x*MyGame.PIXEL_TO_METER,y*MyGame.PIXEL_TO_METER);
        bdef.type=BodyDef.BodyType.DynamicBody;
        bdef.linearDamping=DAMPING_NORMAL;
        b2body=screen.getWorld().createBody(bdef);
        //b2body.setGravityScale(0);

        fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(4*MyGame.PIXEL_TO_METER,6.5f*MyGame.PIXEL_TO_METER);
        fdef.filter.categoryBits= MyGame.HERO_BIT;
        fdef.filter.maskBits = MyGame.ITEM_BIT
                | MyGame.DEFAULT_BIT
                | MyGame.SPIKES_BIT
                | MyGame.BOULDER_BIT
                | MyGame.WARP_OBJECT
                | MyGame.BOMB_BIT
                | MyGame.PITFALL_BIT
                | MyGame.CHEST_BIT
                | MyGame.SMASH_BIT
                | MyGame.SIGN_BIT
                | MyGame.MOVING_PLATFORM_BIT
                | MyGame.MEGA_PRESSING_PLATE_BIT
                | MyGame.PRESSING_PLATE_BIT;
        fdef.shape= shape;
        b2body.createFixture(fdef).setUserData(hero);


        //Edgeshapes (contact lines) - Surfaces to detect contact with Tiled objects
        FixtureDef sensorfix= new FixtureDef();
        sensorfix.filter.categoryBits= MyGame.DEFAULT_BIT;
        sensorfix.filter.maskBits = MyGame.ITEM_BIT
                | MyGame.DEFAULT_BIT
                | MyGame.SPIKES_BIT
                | MyGame.BOULDER_BIT
                | MyGame.WARP_OBJECT
                | MyGame.PRESSING_PLATE_BIT;
        sensorfix.shape= shape;

        //Up Contact Line
        EdgeShape upContact= new EdgeShape();
        upContact.set(new Vector2(-4*MyGame.PIXEL_TO_METER,10*MyGame.PIXEL_TO_METER), new Vector2(4*MyGame.PIXEL_TO_METER,10*MyGame.PIXEL_TO_METER));
        sensorfix.shape=upContact;
        sensorfix.isSensor=true;
        b2body.createFixture(sensorfix).setUserData("upContact");

        //Down Contact Line
        EdgeShape downContact= new EdgeShape();
        downContact.set(new Vector2(-4*MyGame.PIXEL_TO_METER,-10*MyGame.PIXEL_TO_METER), new Vector2(4*MyGame.PIXEL_TO_METER,-10*MyGame.PIXEL_TO_METER));
        sensorfix.shape=downContact;
        sensorfix.isSensor=true;
        b2body.createFixture(sensorfix).setUserData("downContact");

        //Left Contact Line
        EdgeShape leftContact= new EdgeShape();
        leftContact.set(new Vector2(5*MyGame.PIXEL_TO_METER,-5*MyGame.PIXEL_TO_METER), new Vector2(5*MyGame.PIXEL_TO_METER,5*MyGame.PIXEL_TO_METER));
        sensorfix.shape=leftContact;
        sensorfix.isSensor=true;
        b2body.createFixture(sensorfix).setUserData("leftContact");

        //Down Contact Line
        EdgeShape rightContact= new EdgeShape();
        rightContact.set(new Vector2(-5*MyGame.PIXEL_TO_METER, -5*MyGame.PIXEL_TO_METER), new Vector2(-5*MyGame.PIXEL_TO_METER, 5*MyGame.PIXEL_TO_METER));
        sensorfix.shape=rightContact;
        sensorfix.isSensor=true;
        b2body.createFixture(sensorfix).setUserData("rightContact");
    }

    public State getState() {
        if(hero.getHealth()==0){
            screen.getGame().heroStats.setHearts(-1);
            return State.DYING;
        }else if(hero.getHealth()<0){
            return State.GAME_OVER;
        }
        else if(hero.getWasHit()){
            return State.HURT;
        }/*
        else if(hero.isInPitfall && !hero.isInPlatform){
            return State.FALLING;
        }*/
        else if (b2body.getLinearVelocity().x != 0 && b2body.getLinearVelocity().y == 0
                || b2body.getLinearVelocity().x == 0 && b2body.getLinearVelocity().y != 0
                || b2body.getLinearVelocity().x != 0 && b2body.getLinearVelocity().y != 0) {
            if (Math.abs(b2body.getLinearVelocity().x) > Math.abs(b2body.getLinearVelocity().y)) {
                if (b2body.getLinearVelocity().x > 0)
                    return State.WALK_RIGHT;
                else
                    return State.WALK_LEFT;
            }
            else
            {
                if (b2body.getLinearVelocity().y < 0)
                    return State.WALK_DOWN;
                else //if (b2body.getLinearVelocity().y>0)
                    return State.WALK_UP;
            }
        }
        else {
            if (previousState == State.WALK_UP)
                return State.STAND_UP;
            else if (previousState == State.WALK_RIGHT)
                return State.STAND_RIGHT;
            else if (previousState == State.WALK_LEFT)
                return State.STAND_LEFT;
            else if(previousState== State.WALK_DOWN)
                return State.STAND_DOWN;
            else{
                return previousState;
            }
        }

    }

    public TextureRegion getFrame(Hero hero,float dt) {
        currentState = getState();
        TextureRegion region = new TextureRegion();

        switch (currentState) {
            case HURT:{
                hero.setBounds(hero.getX(), hero.getY(), 31 * MyGame.PIXEL_TO_METER, 32 * MyGame.PIXEL_TO_METER);
                region = hero.heroHurt.getKeyFrame(hero.upDownTimer, false);
                hero.getSoundHurt().play();
                hero.setWasHit(false);
            }
            break;/*
            case FALLING:{
                hero.setBounds(hero.getX(), hero.getY(), 22 * MyGame.PIXEL_TO_METER, 21 * MyGame.PIXEL_TO_METER);
                region = hero.heroFalling.getKeyFrame(hero.upDownTimer, false);
                //hero.getSoundFalling().play();
            }
            break;*/
            case DYING:{
                hero.getSoundDying().play();
                hero.setBounds(hero.getX(), hero.getY(), 25 * MyGame.PIXEL_TO_METER, 24 * MyGame.PIXEL_TO_METER);
                region=hero.heroDying.getKeyFrame(1);
            }
            break;
            case GAME_OVER:{
                hero.setBounds(hero.getX(), hero.getY(), 25 * MyGame.PIXEL_TO_METER, 24 * MyGame.PIXEL_TO_METER);
                region = hero.heroDying.getKeyFrame(hero.upDownTimer, false);
            }
            break;
            case STAND_UP: {
                hero.setBounds(hero.getX(), hero.getY(), 17 * MyGame.PIXEL_TO_METER, 22 * MyGame.PIXEL_TO_METER);
                region = hero.getStandBack();
            }
            break;
            case STAND_DOWN: {
                hero.setBounds(hero.getX(), hero.getY(), 17 * MyGame.PIXEL_TO_METER, 22 * MyGame.PIXEL_TO_METER);
                region = hero.getStandFront();
            }
            break;
            case STAND_LEFT: {
                hero.setBounds(hero.getX(), hero.getY(), 17 * MyGame.PIXEL_TO_METER, 22 * MyGame.PIXEL_TO_METER);
                region = hero.getStandLeft();
            }
            break;
            case STAND_RIGHT: {
                hero.setBounds(hero.getX(), hero.getY(), 17 * MyGame.PIXEL_TO_METER, 22 * MyGame.PIXEL_TO_METER);
                region = hero.getStandRight();
            }
            break;
            case WALK_UP: {
                hero.setBounds(hero.getX(), hero.getY(), 17 * MyGame.PIXEL_TO_METER, 22 * MyGame.PIXEL_TO_METER);
                region = hero.heroWalkUp.getKeyFrame(hero.upDownTimer, true);
            }
            break;
            case WALK_DOWN: {
                hero.setBounds(hero.getX(), hero.getY(), 17 * MyGame.PIXEL_TO_METER, 22 * MyGame.PIXEL_TO_METER);
                region = hero.heroWalkDown.getKeyFrame(hero.upDownTimer, true);
            }
            break;
            case WALK_LEFT: {
                hero.setBounds(hero.getX(), hero.getY(), 23 * MyGame.PIXEL_TO_METER, 23 * MyGame.PIXEL_TO_METER);
                region = hero.heroWalkRight.getKeyFrame(hero.leftRightTimer, true);
            }
            break;
            case WALK_RIGHT: {
                hero.setBounds(hero.getX(), hero.getY(), 23 * MyGame.PIXEL_TO_METER, 23 * MyGame.PIXEL_TO_METER);
                region = hero.heroWalkRight.getKeyFrame(hero.leftRightTimer, true);
            }
            break;
            default:
                region = hero.getStandFront();
                break;
        }

        if((b2body.getLinearVelocity().x<0||currentState==State.WALK_LEFT)&&!region.isFlipX())
        {
            region.flip(true, false);
            currentState = State.WALK_LEFT;
        }
        else if((b2body.getLinearVelocity().x>0||currentState==State.WALK_RIGHT)&&region.isFlipX())

        {
            region.flip(true, false);
            currentState = State.WALK_RIGHT;
        }
        hero.upDownTimer=currentState == previousState ? hero.upDownTimer + dt : 0;
        if(previousState==currentState){
            hero.leftRightTimer+=(dt);
        }else{
            hero.leftRightTimer=0;
        }
        previousState=currentState;
        return region;
    }

    public void InputUpdate(Controller controller, float dt){
        if(controller.isRightPressed()){
            b2body.applyLinearImpulse(new Vector2(MyGame.VELOCITY*dt,0), b2body.getWorldCenter(), true);
            if(controller.isbPressed()){
                if(hero.getAddBomb())
                    this.hero.throwBomb();
            }
            else if(controller.isaPressed()){
                if(hero.getOpenedChestId()>-1) {
                    hero.getScreen().getChests().get(hero.getOpenedChestId()).setOpen(true);
                    hero.setOpenedChestId(-1);
                }
                else if(hero.getOpenedSignId()>-1 && !hero.signWasOpened){
                    hero.getScreen().getSigns().get(hero.getOpenedSignId()).setOpenLog(true);
                    hero.signWasOpened=true;

                }
            }
        }
       else if(controller.isLeftPressed()) {
            b2body.applyLinearImpulse(new Vector2(-MyGame.VELOCITY * dt, 0), b2body.getWorldCenter(), true);
            if(controller.isbPressed()){
                if(hero.getAddBomb())
                    this.hero.throwBomb();
            }
            else if(controller.isaPressed()){
                if(hero.getOpenedChestId()>-1) {
                    hero.getScreen().getChests().get(hero.getOpenedChestId()).setOpen(true);
                    hero.setOpenedChestId(-1);
                }
                else if(hero.getOpenedSignId()>-1){
                    hero.getScreen().getSigns().get(hero.getOpenedSignId()).setOpenLog(true);
                   }
            }
        }
        else if(controller.isUpPressed()){
            b2body.applyLinearImpulse(new Vector2(0,MyGame.VELOCITY*dt), b2body.getWorldCenter(), true);
            if(controller.isbPressed()){
                if(hero.getAddBomb())
                    this.hero.throwBomb();
            }
            else if(controller.isaPressed()){
                if(hero.getOpenedChestId()>-1) {
                    hero.getScreen().getChests().get(hero.getOpenedChestId()).setOpen(true);
                    hero.setOpenedChestId(-1);
                }
                else if(hero.getOpenedSignId()>-1){
                    hero.getScreen().getSigns().get(hero.getOpenedSignId()).setOpenLog(true);
                }
            }
        }
        else if(controller.isDownPressed()){
            b2body.applyLinearImpulse(new Vector2(0,-MyGame.VELOCITY*dt), b2body.getWorldCenter(), true);
            if(controller.isbPressed()){
                if(hero.getAddBomb())
                    this.hero.throwBomb();
            }
            else if(controller.isaPressed()){
                if(hero.getOpenedChestId()>-1) {
                    hero.getScreen().getChests().get(hero.getOpenedChestId()).setOpen(true);
                    hero.setOpenedChestId(-1);
                }
                else if(hero.getOpenedSignId()>-1){
                    hero.getScreen().getSigns().get(hero.getOpenedSignId()).setOpenLog(true);
                }
            }
        }
        else if(controller.isbPressed()){
            if(hero.getAddBomb())
            this.hero.throwBomb();
        }

        else if(controller.isaPressed()){
            if(hero.getOpenedChestId()>-1) {
                hero.getScreen().getChests().get(hero.getOpenedChestId()).setOpen(true);
                hero.setOpenedChestId(-1);
            }
            else if(hero.getOpenedSignId()>-1){
                hero.getScreen().getSigns().get(hero.getOpenedSignId()).setOpenLog(true);
            }
        }
        else if(controller.isEscPressed()){
            if(hero.getOpenedSignId()>-1){
                hero.getScreen().getSigns().get(hero.getOpenedSignId()).setOpenLog(false);
                hero.signWasOpened=false;
                hero.setOpenedSignId(-1);
            }
        }
        else {
            if(isInIce){
                b2body.setLinearVelocity(0, 0);
            }
            else b2body.setLinearVelocity(0, 0);
        }
    }

    public Body getBody() {
        return b2body;
    }

    public FixtureDef getFdef() {
        return fdef;
    }
}
