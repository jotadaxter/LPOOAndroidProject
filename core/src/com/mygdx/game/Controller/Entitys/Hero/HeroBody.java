package com.mygdx.game.Controller.Entitys.Hero;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.Controller.Controller;
import com.mygdx.game.Controller.Entitys.StaticContactShapes;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.Model.Entitys.Hero.Hero;
import com.mygdx.game.MyGame;

/**
 * Created by Jotadaxter on 28/04/2017.
 */

public class HeroBody{
    public static final float DAMPING_NORMAL= 3f;
    private Body b2body;
    private LogicController logicController;
    private FixtureDef fdef;
    private Hero hero;
    public enum State {WALK_UP, WALK_DOWN, WALK_LEFT, WALK_RIGHT, STAND_UP, STAND_DOWN, STAND_RIGHT, STAND_LEFT, HURT, GAME_OVER, DYING}
    private State currentState;
    private State previousState;

    public HeroBody(LogicController logicController, Hero hero, Vector2 vec) {
        this.logicController= logicController;
        setCurrentState(State.STAND_DOWN);
        setPreviousState(State.STAND_DOWN);
        this.hero=hero;
        BodyDef bdef =  new BodyDef();
        bdef.position.set(vec.x*MyGame.PIXEL_TO_METER,vec.y*MyGame.PIXEL_TO_METER);
        bdef.type=BodyDef.BodyType.DynamicBody;
        bdef.linearDamping=DAMPING_NORMAL;
        setB2body(logicController.getWorld().createBody(bdef));
        fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(4*MyGame.PIXEL_TO_METER,6.5f*MyGame.PIXEL_TO_METER);
        fdef.filter.categoryBits= MyGame.HERO_BIT;
        fdef.filter.maskBits = MyGame.ALL_BIT;
        fdef.shape= shape;
        getB2body().createFixture(fdef).setUserData(this);
        shapesDefine();
    }

    private void shapesDefine(){
        StaticContactShapes shapeUp = new StaticContactShapes(getB2body(), new EdgeShape());
        shapeUpConfig(shapeUp);
        StaticContactShapes shapeDown = new StaticContactShapes(getB2body(), new EdgeShape());
        shapeDownConfig(shapeDown);
        StaticContactShapes shapeRight = new StaticContactShapes(getB2body(), new EdgeShape());
        shapeRightConfig(shapeRight);
        StaticContactShapes shapeLeft = new StaticContactShapes(getB2body(), new EdgeShape());
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
            logicController.getGame().getHeroStats().setHearts(-1);
            return State.DYING;
        }else if(hero.getHealth()<0)
            return State.GAME_OVER;
        else if(hero.getWasHit())
            return State.HURT;
        else if (getB2body().getLinearVelocity().x != 0 && getB2body().getLinearVelocity().y == 0
                || getB2body().getLinearVelocity().x == 0 && getB2body().getLinearVelocity().y != 0
                || getB2body().getLinearVelocity().x != 0 && getB2body().getLinearVelocity().y != 0)
            return bodyMovingState();

        else return bodyQuietState();
    }

    private State bodyQuietState() {
        if (getPreviousState() == State.WALK_UP)
            return State.STAND_UP;
        else if (getPreviousState() == State.WALK_RIGHT)
            return State.STAND_RIGHT;
        else if (getPreviousState() == State.WALK_LEFT)
            return State.STAND_LEFT;
        else if(getPreviousState() == State.WALK_DOWN)
            return State.STAND_DOWN;
        else{
            return getPreviousState();
        }
    }

    private State bodyMovingState() {
        if (Math.abs(getB2body().getLinearVelocity().x) > Math.abs(getB2body().getLinearVelocity().y)) {
            if (getB2body().getLinearVelocity().x > 0)
                return State.WALK_RIGHT;
            else
                return State.WALK_LEFT;
        }
        else{
            if (getB2body().getLinearVelocity().y < 0)
                return State.WALK_DOWN;
            else
                return State.WALK_UP;
        }
    }

    public TextureRegion getFrame(Hero hero,float dt) {
        setCurrentState(getState());
        TextureRegion region=regionStateUpdate();
        flipBodyAnimation(region);
        updateTimers(dt);
        return region;
    }

    private TextureRegion regionStateUpdate() {
        if(getCurrentState() == State.HURT || getCurrentState() == State.DYING || getCurrentState() == State.GAME_OVER
                || getCurrentState() == State.STAND_UP || getCurrentState() == State.STAND_DOWN)
            return regionStateUpdate1();
        else if(getCurrentState() == State.STAND_LEFT || getCurrentState() == State.STAND_RIGHT
                || getCurrentState() == State.WALK_UP || getCurrentState() == State.WALK_DOWN
                || getCurrentState() == State.WALK_LEFT || getCurrentState() == State.WALK_RIGHT)
            return regionStateUpdate2();
        else return hero.getStandFront();
    }

    private TextureRegion regionStateUpdate1() {
        if(getCurrentState() == State.HURT || getCurrentState() == State.DYING)
            return regionUpdate1();
        else
            return regionUpdate2();
    }

    private TextureRegion regionStateUpdate2(){
        if(getCurrentState() == State.STAND_LEFT || getCurrentState() == State.STAND_RIGHT || getCurrentState() == State.WALK_UP)
            return regionUpdate3();
        else
            return regionUpdate4();
    }

    private TextureRegion regionUpdate4() {
        TextureRegion region = new TextureRegion();
        if(getCurrentState() == State.WALK_DOWN){
            hero.setBounds(hero.getX(), hero.getY(), 17 * MyGame.PIXEL_TO_METER, 22 * MyGame.PIXEL_TO_METER);
            region = hero.getHeroWalkDown().getKeyFrame(hero.getUpDownTimer(), true);
        }
        else if(getCurrentState() == State.WALK_LEFT){
            hero.setBounds(hero.getX(), hero.getY(), 23 * MyGame.PIXEL_TO_METER, 23 * MyGame.PIXEL_TO_METER);
            region = hero.getHeroWalkRight().getKeyFrame(hero.getLeftRightTimer(), true);
        }
        else if(getCurrentState() == State.WALK_RIGHT){
            hero.setBounds(hero.getX(), hero.getY(), 23 * MyGame.PIXEL_TO_METER, 23 * MyGame.PIXEL_TO_METER);
            region = hero.getHeroWalkRight().getKeyFrame(hero.getLeftRightTimer(), true);
        }
        return region;
    }

    private TextureRegion regionUpdate3() {
        TextureRegion region = new TextureRegion();
       if(getCurrentState() == State.STAND_LEFT){
            hero.setBounds(hero.getX(), hero.getY(), 17 * MyGame.PIXEL_TO_METER, 22 * MyGame.PIXEL_TO_METER);
            region = hero.getStandLeft();
        }
       else if(getCurrentState() == State.STAND_RIGHT){
            hero.setBounds(hero.getX(), hero.getY(), 17 * MyGame.PIXEL_TO_METER, 22 * MyGame.PIXEL_TO_METER);
            region = hero.getStandRight();
        }
       else if(getCurrentState() == State.WALK_UP){
            hero.setBounds(hero.getX(), hero.getY(), 17 * MyGame.PIXEL_TO_METER, 22 * MyGame.PIXEL_TO_METER);
            region = hero.getHeroWalkUp().getKeyFrame(hero.getUpDownTimer(), true);
        }
        return region;
    }

    private TextureRegion regionUpdate2() {
        TextureRegion region = new TextureRegion();
        if(getCurrentState() ==State.GAME_OVER){
            hero.setBounds(hero.getX(), hero.getY(), 25 * MyGame.PIXEL_TO_METER, 24 * MyGame.PIXEL_TO_METER);
            region = hero.getHeroDying().getKeyFrame(hero.getUpDownTimer(), false);
        }
        else if(getCurrentState() ==State.STAND_UP){
            hero.setBounds(hero.getX(), hero.getY(), 17 * MyGame.PIXEL_TO_METER, 22 * MyGame.PIXEL_TO_METER);
            region = hero.getStandBack();
        }
        else if(getCurrentState() ==State.STAND_DOWN){
            hero.setBounds(hero.getX(), hero.getY(), 17 * MyGame.PIXEL_TO_METER, 22 * MyGame.PIXEL_TO_METER);
            region = hero.getStandFront();
        }
       return region;
    }

    private TextureRegion regionUpdate1() {
        TextureRegion region = new TextureRegion();
        if(getCurrentState() ==State.HURT){
                hero.setBounds(hero.getX(), hero.getY(), 31 * MyGame.PIXEL_TO_METER, 32 * MyGame.PIXEL_TO_METER);
                region = hero.getHeroHurt().getKeyFrame(hero.getUpDownTimer(), false);
                hero.getSoundHurt().play(MyGame.SOUND_VOLUME);
                hero.setWasHit(false);
            }
            else if(getCurrentState() ==State.DYING){
                hero.getSoundDying().play(MyGame.SOUND_VOLUME);
                hero.setBounds(hero.getX(), hero.getY(), 25 * MyGame.PIXEL_TO_METER, 24 * MyGame.PIXEL_TO_METER);
                region = hero.getHeroDying().getKeyFrame(1);
            }
        return region;
    }

    private void updateTimers(float dt) {
        hero.setUpDownTimer(getCurrentState() == getPreviousState() ? hero.getUpDownTimer() + dt : 0);
        if(getPreviousState() == getCurrentState()){
            hero.setLeftRightTimer(hero.getLeftRightTimer() + (dt));
        }else{
            hero.setLeftRightTimer(0);
        }
        setPreviousState(getCurrentState());
    }

    private void flipBodyAnimation(TextureRegion region) {
        if((getB2body().getLinearVelocity().x<0|| getCurrentState() ==State.WALK_LEFT)&&!region.isFlipX()){
            region.flip(true, false);
            setCurrentState(State.WALK_LEFT);
        }
        else if((getB2body().getLinearVelocity().x>0|| getCurrentState() ==State.WALK_RIGHT)&&region.isFlipX()){
            region.flip(true, false);
            setCurrentState(State.WALK_RIGHT);
        }
    }

    public void InputUpdate(Controller controller, float dt){
        if(controller.isRightPressed()){
            getB2body().applyLinearImpulse(new Vector2(MyGame.VELOCITY*dt,0), getB2body().getWorldCenter(), true);
            movementPress(controller, dt);
        }
        else if(controller.isLeftPressed()) {
            getB2body().applyLinearImpulse(new Vector2(-MyGame.VELOCITY * dt, 0), getB2body().getWorldCenter(), true);
            movementPress(controller, dt);
        }
        else if(controller.isUpPressed()){
            getB2body().applyLinearImpulse(new Vector2(0,MyGame.VELOCITY*dt), getB2body().getWorldCenter(), true);
            movementPress(controller, dt);
        }
        else if(controller.isDownPressed()){
            getB2body().applyLinearImpulse(new Vector2(0,-MyGame.VELOCITY*dt), getB2body().getWorldCenter(), true);
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
                logicController.getChests().get(hero.getOpenedChestId()).setOpen(true);
                hero.setOpenedChestId(-1);
            }
            else if(hero.getOpenedSignId()>-1){
                logicController.getSigns().get(hero.getOpenedSignId()).setOpenLog(true);
            }
        }
        else if(controller.isEscPressed()){
            if(hero.getOpenedSignId()>-1){
                logicController.getSigns().get(hero.getOpenedSignId()).setOpenLog(false);
                hero.setSignWasOpened(false);
                hero.setOpenedSignId(-1);
            }
        }
        else getB2body().setLinearVelocity(0, 0);
    }

    private void movementPress(Controller controller, float dt) {
        if(controller.isbPressed()){
            if(hero.getAddBomb())
                this.hero.throwBomb();
        }
        else if(controller.isaPressed()){
            if(hero.getOpenedChestId()>-1) {
                logicController.getChests().get(hero.getOpenedChestId()).setOpen(true);
                hero.setOpenedChestId(-1);
            }
            else if(hero.getOpenedSignId()>-1 && !hero.isSignWasOpened()){
                logicController.getSigns().get(hero.getOpenedSignId()).setOpenLog(true);
                hero.setSignWasOpened(true);
            }
        }
    }

    public Body getBody() {
        return getB2body();
    }

    public FixtureDef getFdef() {
        return fdef;
    }

    public Hero getHero() {
        return hero;
    }

    public Body getB2body() {
        return b2body;
    }

    public void setB2body(Body b2body) {
        this.b2body = b2body;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public State getPreviousState() {
        return previousState;
    }

    public void setPreviousState(State previousState) {
        this.previousState = previousState;
    }

}
