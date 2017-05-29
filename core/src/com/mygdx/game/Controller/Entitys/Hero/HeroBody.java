package com.mygdx.game.Controller.Entitys.Hero;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.Controller.Controller;
import com.mygdx.game.Controller.Entitys.StaticContactShapes;
import com.mygdx.game.Model.Entitys.Hero.Hero;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Jotadaxter on 28/04/2017.
 */

public class HeroBody{
    public static final float DAMPING_NORMAL= 3f;
    private GameScreen screen;
    public Body b2body;
    private FixtureDef fdef;
    private Hero hero;

    public enum State {WALK_UP, WALK_DOWN, WALK_LEFT, WALK_RIGHT, STAND_UP, STAND_DOWN, STAND_RIGHT, STAND_LEFT, HURT, GAME_OVER, DYING, FALLING};
    public State currentState;
    public State previousState;

    public HeroBody(GameScreen screen, Hero hero, Vector2 vec) {
        currentState = State.STAND_DOWN;
        previousState = State.STAND_DOWN;
        this.screen=screen;
        this.hero=hero;
        BodyDef bdef =  new BodyDef();
        bdef.position.set(vec.x*MyGame.PIXEL_TO_METER,vec.y*MyGame.PIXEL_TO_METER);
        bdef.type=BodyDef.BodyType.DynamicBody;
        bdef.linearDamping=DAMPING_NORMAL;
        b2body=screen.getWorld().createBody(bdef);
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
        shapesDefine();
    }

    private void shapesDefine(){
        StaticContactShapes shapeUp = new StaticContactShapes(b2body, new EdgeShape());
        shapeUpConfig(shapeUp);
        StaticContactShapes shapeDown = new StaticContactShapes(b2body, new EdgeShape());
        shapeDownConfig(shapeDown);
        StaticContactShapes shapeRight = new StaticContactShapes(b2body, new EdgeShape());
        shapeRightConfig(shapeRight);
        StaticContactShapes shapeLeft = new StaticContactShapes(b2body, new EdgeShape());
        shapeLeftConfig(shapeLeft);
    }

    private void shapeLeftConfig(StaticContactShapes shape) {
        shape.setName("leftContact");
        shape.setVec1(new Vector2(5*MyGame.PIXEL_TO_METER,-5*MyGame.PIXEL_TO_METER));
        shape.setVec2( new Vector2(5*MyGame.PIXEL_TO_METER,5*MyGame.PIXEL_TO_METER));
        shape.shapeDef();
    }

    private void shapeRightConfig(StaticContactShapes shape) {
        shape.setName("rightContact");
        shape.setVec1(new Vector2(-5*MyGame.PIXEL_TO_METER,-5*MyGame.PIXEL_TO_METER));
        shape.setVec2( new Vector2(-5*MyGame.PIXEL_TO_METER,5*MyGame.PIXEL_TO_METER));
        shape.shapeDef();
    }

    private void shapeDownConfig(StaticContactShapes shape) {
        shape.setName("downContact");
        shape.setVec1(new Vector2(-4*MyGame.PIXEL_TO_METER,-10*MyGame.PIXEL_TO_METER));
        shape.setVec2( new Vector2(4*MyGame.PIXEL_TO_METER,-10*MyGame.PIXEL_TO_METER));
        shape.shapeDef();
    }

    private void shapeUpConfig(StaticContactShapes shape) {
        shape.setName("upContact");
        shape.setVec1(new Vector2(-4*MyGame.PIXEL_TO_METER,10*MyGame.PIXEL_TO_METER));
        shape.setVec2( new Vector2(4*MyGame.PIXEL_TO_METER,10*MyGame.PIXEL_TO_METER));
        shape.shapeDef();
    }

    public State getState() {
        if(hero.getHealth()==0){
            screen.getGame().heroStats.setHearts(-1);
            return State.DYING;
        }else if(hero.getHealth()<0)
            return State.GAME_OVER;
        else if(hero.getWasHit())
            return State.HURT;
        else if (b2body.getLinearVelocity().x != 0 && b2body.getLinearVelocity().y == 0
                || b2body.getLinearVelocity().x == 0 && b2body.getLinearVelocity().y != 0
                || b2body.getLinearVelocity().x != 0 && b2body.getLinearVelocity().y != 0)
            return bodyMovingState();

        else return bodyQuietState();
    }

    private State bodyQuietState() {
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

    private State bodyMovingState() {
        if (Math.abs(b2body.getLinearVelocity().x) > Math.abs(b2body.getLinearVelocity().y)) {
            if (b2body.getLinearVelocity().x > 0)
                return State.WALK_RIGHT;
            else
                return State.WALK_LEFT;
        }
        else{
            if (b2body.getLinearVelocity().y < 0)
                return State.WALK_DOWN;
            else
                return State.WALK_UP;
        }
    }

    public TextureRegion getFrame(Hero hero,float dt) {
        currentState = getState();
        TextureRegion region=regionStateUpdate();
        flipBodyAnimation(region);
        updateTimers(dt);
        return region;
    }

    private TextureRegion regionStateUpdate() {
        if(currentState == State.HURT || currentState == State.DYING)
            return regionUpdate1();
        else if(currentState == State.GAME_OVER || currentState == State.STAND_UP || currentState == State.STAND_DOWN)
            return regionUpdate2();
        else if(currentState == State.STAND_LEFT || currentState == State.STAND_RIGHT || currentState == State.WALK_UP)
            return regionUpdate3();
        else if(currentState == State.WALK_DOWN || currentState == State.WALK_LEFT || currentState == State.WALK_RIGHT)
            return regionUpdate4();
        else return hero.getStandFront();
    }

    private TextureRegion regionUpdate4() {
        TextureRegion region = new TextureRegion();
        if(currentState== State.WALK_DOWN){
            hero.setBounds(hero.getX(), hero.getY(), 17 * MyGame.PIXEL_TO_METER, 22 * MyGame.PIXEL_TO_METER);
            region = hero.heroWalkDown.getKeyFrame(hero.upDownTimer, true);
        }
        else if(currentState== State.WALK_LEFT){
            hero.setBounds(hero.getX(), hero.getY(), 23 * MyGame.PIXEL_TO_METER, 23 * MyGame.PIXEL_TO_METER);
            region = hero.heroWalkRight.getKeyFrame(hero.leftRightTimer, true);
        }
        else if(currentState== State.WALK_RIGHT){
            hero.setBounds(hero.getX(), hero.getY(), 23 * MyGame.PIXEL_TO_METER, 23 * MyGame.PIXEL_TO_METER);
            region = hero.heroWalkRight.getKeyFrame(hero.leftRightTimer, true);
        }
        return region;
    }

    private TextureRegion regionUpdate3() {
        TextureRegion region = new TextureRegion();
       if(currentState== State.STAND_LEFT){
            hero.setBounds(hero.getX(), hero.getY(), 17 * MyGame.PIXEL_TO_METER, 22 * MyGame.PIXEL_TO_METER);
            region = hero.getStandLeft();
        }
       else if(currentState== State.STAND_RIGHT){
            hero.setBounds(hero.getX(), hero.getY(), 17 * MyGame.PIXEL_TO_METER, 22 * MyGame.PIXEL_TO_METER);
            region = hero.getStandRight();
        }
       else if(currentState== State.WALK_UP){
            hero.setBounds(hero.getX(), hero.getY(), 17 * MyGame.PIXEL_TO_METER, 22 * MyGame.PIXEL_TO_METER);
            region = hero.heroWalkUp.getKeyFrame(hero.upDownTimer, true);
        }
        return region;
    }

    private TextureRegion regionUpdate2() {
        TextureRegion region = new TextureRegion();
        if(currentState==State.GAME_OVER){
            hero.setBounds(hero.getX(), hero.getY(), 25 * MyGame.PIXEL_TO_METER, 24 * MyGame.PIXEL_TO_METER);
            region = hero.heroDying.getKeyFrame(hero.upDownTimer, false);
        }
        else if(currentState==State.STAND_UP){
            hero.setBounds(hero.getX(), hero.getY(), 17 * MyGame.PIXEL_TO_METER, 22 * MyGame.PIXEL_TO_METER);
            region = hero.getStandBack();
        }
        else if(currentState==State.STAND_DOWN){
            hero.setBounds(hero.getX(), hero.getY(), 17 * MyGame.PIXEL_TO_METER, 22 * MyGame.PIXEL_TO_METER);
            region = hero.getStandFront();
        }
       return region;
    }

    private TextureRegion regionUpdate1() {
        TextureRegion region = new TextureRegion();
        if(currentState==State.HURT){
                hero.setBounds(hero.getX(), hero.getY(), 31 * MyGame.PIXEL_TO_METER, 32 * MyGame.PIXEL_TO_METER);
                region = hero.heroHurt.getKeyFrame(hero.upDownTimer, false);
                hero.getSoundHurt().play();
                hero.setWasHit(false);
            }
            else if(currentState==State.DYING){
                hero.getSoundDying().play();
                hero.setBounds(hero.getX(), hero.getY(), 25 * MyGame.PIXEL_TO_METER, 24 * MyGame.PIXEL_TO_METER);
                region = hero.heroDying.getKeyFrame(1);
            }
        return region;
    }

    private void updateTimers(float dt) {
        hero.upDownTimer=currentState == previousState ? hero.upDownTimer + dt : 0;
        if(previousState==currentState){
            hero.leftRightTimer+=(dt);
        }else{
            hero.leftRightTimer=0;
        }
        previousState=currentState;
    }

    private void flipBodyAnimation(TextureRegion region) {
        if((b2body.getLinearVelocity().x<0||currentState==State.WALK_LEFT)&&!region.isFlipX()){
            region.flip(true, false);
            currentState = State.WALK_LEFT;
        }
        else if((b2body.getLinearVelocity().x>0||currentState==State.WALK_RIGHT)&&region.isFlipX()){
            region.flip(true, false);
            currentState = State.WALK_RIGHT;
        }
    }

    public void InputUpdate(Controller controller, float dt){
        if(controller.isRightPressed()){
            b2body.applyLinearImpulse(new Vector2(MyGame.VELOCITY*dt,0), b2body.getWorldCenter(), true);
            movementPress(controller, dt);
        }
        else if(controller.isLeftPressed()) {
            b2body.applyLinearImpulse(new Vector2(-MyGame.VELOCITY * dt, 0), b2body.getWorldCenter(), true);
            movementPress(controller, dt);
        }
        else if(controller.isUpPressed()){
            b2body.applyLinearImpulse(new Vector2(0,MyGame.VELOCITY*dt), b2body.getWorldCenter(), true);
            movementPress(controller, dt);
        }
        else if(controller.isDownPressed()){
            b2body.applyLinearImpulse(new Vector2(0,-MyGame.VELOCITY*dt), b2body.getWorldCenter(), true);
            movementPress(controller, dt);
        }
        else if(controller.isbPressed()){
            if(hero.getAddBomb())
                this.hero.throwBomb();
        }
        else{
            auxControl(controller, dt);
        }
    }

    private void auxControl(Controller controller, float dt) {
        if(controller.isaPressed()){
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
        else b2body.setLinearVelocity(0, 0);
    }

    private void movementPress(Controller controller, float dt) {
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

    public Body getBody() {
        return b2body;
    }

    public FixtureDef getFdef() {
        return fdef;
    }
}
