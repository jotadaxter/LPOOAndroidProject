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
    
    /** The Constant DAMPING_NORMAL. */
    public static final float DAMPING_NORMAL= 3f;
    
    /** The body. */
    private Body body;
    
    /** The logic controller. */
    private LogicController logicController;
    
    /** The fixture definition. */
    private FixtureDef fdef;
    
    /** The hero. */
    private Hero hero;
    
    /**
     * The Enum State.
     */
    public enum State {
/** The walk up. */
WALK_UP, 
 /** The walk down. */
 WALK_DOWN, 
 /** The walk left. */
 WALK_LEFT, 
 /** The walk right. */
 WALK_RIGHT, 
 /** The stand up. */
 STAND_UP, 
 /** The stand down. */
 STAND_DOWN, 
 /** The stand right. */
 STAND_RIGHT, 
 /** The stand left. */
 STAND_LEFT, 
 /** The hurt. */
 HURT, 
 /** The game over. */
 GAME_OVER, 
 /** The dying. */
 DYING}
    
    /** The current state. */
    private State currentState;
    
    /** The previous state. */
    private State previousState;

    /**
     * Instantiates a new hero body.
     *
     * @param logicController the logic controller
     * @param hero the hero
     * @param vec the vec
     */
    public HeroBody(LogicController logicController, Hero hero, Vector2 vec) {
        this.logicController= logicController;
        setCurrentState(State.STAND_DOWN);
        setPreviousState(State.STAND_DOWN);
        this.hero=hero;
        BodyDef bdef =  new BodyDef();
        bdef.position.set(vec.x*MyGame.PIXEL_TO_METER,vec.y*MyGame.PIXEL_TO_METER);
        bdef.type=BodyDef.BodyType.DynamicBody;
        bdef.linearDamping=DAMPING_NORMAL;
        setBody(logicController.getWorld().createBody(bdef));
        fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(4*MyGame.PIXEL_TO_METER,6.5f*MyGame.PIXEL_TO_METER);
        fdef.filter.categoryBits= MyGame.HERO_BIT;
        fdef.filter.maskBits = MyGame.ALL_BIT;
        fdef.shape= shape;
        getBody().createFixture(fdef).setUserData(this);
        shapesDefine();
    }

    /**
     * Shapes define.
     */
    private void shapesDefine(){
        StaticContactShapes shapeUp = new StaticContactShapes(getBody(), new EdgeShape());
        shapeUpConfig(shapeUp);
        StaticContactShapes shapeDown = new StaticContactShapes(getBody(), new EdgeShape());
        shapeDownConfig(shapeDown);
        StaticContactShapes shapeRight = new StaticContactShapes(getBody(), new EdgeShape());
        shapeRightConfig(shapeRight);
        StaticContactShapes shapeLeft = new StaticContactShapes(getBody(), new EdgeShape());
        shapeLeftConfig(shapeLeft);
    }

    /**
     * Shape left config.
     *
     * @param shape the shape
     */
    private void shapeLeftConfig(StaticContactShapes shape) {
        shape.setName("leftContact");
        shape.setVec1(new Vector2(5*MyGame.PIXEL_TO_METER,-5*MyGame.PIXEL_TO_METER));
        shape.setVec2( new Vector2(5*MyGame.PIXEL_TO_METER,5*MyGame.PIXEL_TO_METER));
        shape.shapeDef();
    }

    /**
     * Shape right config.
     *
     * @param shape the shape
     */
    private void shapeRightConfig(StaticContactShapes shape) {
        shape.setName("rightContact");
        shape.setVec1(new Vector2(-5*MyGame.PIXEL_TO_METER,-5*MyGame.PIXEL_TO_METER));
        shape.setVec2( new Vector2(-5*MyGame.PIXEL_TO_METER,5*MyGame.PIXEL_TO_METER));
        shape.shapeDef();
    }

    /**
     * Shape down config.
     *
     * @param shape the shape
     */
    private void shapeDownConfig(StaticContactShapes shape) {
        shape.setName("downContact");
        shape.setVec1(new Vector2(-4*MyGame.PIXEL_TO_METER,-10*MyGame.PIXEL_TO_METER));
        shape.setVec2( new Vector2(4*MyGame.PIXEL_TO_METER,-10*MyGame.PIXEL_TO_METER));
        shape.shapeDef();
    }

    /**
     * Shape up config.
     *
     * @param shape the shape
     */
    private void shapeUpConfig(StaticContactShapes shape) {
        shape.setName("upContact");
        shape.setVec1(new Vector2(-4*MyGame.PIXEL_TO_METER,10*MyGame.PIXEL_TO_METER));
        shape.setVec2( new Vector2(4*MyGame.PIXEL_TO_METER,10*MyGame.PIXEL_TO_METER));
        shape.shapeDef();
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public State getState() {
        if(hero.getHealth()==0){
            logicController.getGame().getHeroStats().setHearts(-1);
            return State.DYING;
        }else if(hero.getHealth()<0)
            return State.GAME_OVER;
        else if(hero.getWasHit())
            return State.HURT;
        else if (getBody().getLinearVelocity().x != 0 && getBody().getLinearVelocity().y == 0
                || getBody().getLinearVelocity().x == 0 && getBody().getLinearVelocity().y != 0
                || getBody().getLinearVelocity().x != 0 && getBody().getLinearVelocity().y != 0)
            return bodyMovingState();

        else return bodyQuietState();
    }

    /**
     * Body quiet state.
     *
     * @return the state
     */
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

    /**
     * Body moving state.
     *
     * @return the state
     */
    private State bodyMovingState() {
        if (Math.abs(getBody().getLinearVelocity().x) > Math.abs(getBody().getLinearVelocity().y)) {
            if (getBody().getLinearVelocity().x > 0)
                return State.WALK_RIGHT;
            else
                return State.WALK_LEFT;
        }
        else{
            if (getBody().getLinearVelocity().y < 0)
                return State.WALK_DOWN;
            else
                return State.WALK_UP;
        }
    }

    /**
     * Gets the frame.
     *
     * @param hero the hero
     * @param dt the dt
     * @return the frame
     */
    public TextureRegion getFrame(Hero hero,float dt) {
        setCurrentState(getState());
        TextureRegion region=regionStateUpdate();
        flipBodyAnimation(region);
        updateTimers(dt);
        return region;
    }

    /**
     * Region state update.
     *
     * @return the texture region
     */
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

    /**
     * Region state update part1.
     *
     * @return the texture region
     */
    private TextureRegion regionStateUpdate1() {
        if(getCurrentState() == State.HURT || getCurrentState() == State.DYING)
            return regionUpdate1();
        else
            return regionUpdate2();
    }

    /**
     * Region state update part2.
     *
     * @return the texture region
     */
    private TextureRegion regionStateUpdate2(){
        if(getCurrentState() == State.STAND_LEFT || getCurrentState() == State.STAND_RIGHT || getCurrentState() == State.WALK_UP)
            return regionUpdate3();
        else
            return regionUpdate4();
    }

    /**
     * Region update part4.
     *
     * @return the texture region
     */
    private TextureRegion regionUpdate4() {
        TextureRegion region = new TextureRegion();
        if(getCurrentState() == State.WALK_DOWN){
            hero.getSprite().setBounds(hero.getX(), hero.getY(), 17 * MyGame.PIXEL_TO_METER, 22 * MyGame.PIXEL_TO_METER);
            region = hero.getHeroWalkDown().getKeyFrame(hero.getUpDownTimer(), true);
        }
        else if(getCurrentState() == State.WALK_LEFT){
            hero.getSprite().setBounds(hero.getX(), hero.getY(), 23 * MyGame.PIXEL_TO_METER, 23 * MyGame.PIXEL_TO_METER);
            region = hero.getHeroWalkRight().getKeyFrame(hero.getLeftRightTimer(), true);
        }
        else if(getCurrentState() == State.WALK_RIGHT){
            hero.getSprite().setBounds(hero.getX(), hero.getY(), 23 * MyGame.PIXEL_TO_METER, 23 * MyGame.PIXEL_TO_METER);
            region = hero.getHeroWalkRight().getKeyFrame(hero.getLeftRightTimer(), true);
        }
        return region;
    }

    /**
     * Region update part3.
     *
     * @return the texture region
     */
    private TextureRegion regionUpdate3() {
        TextureRegion region = new TextureRegion();
       if(getCurrentState() == State.STAND_LEFT){
            hero.getSprite().setBounds(hero.getX(), hero.getY(), 17 * MyGame.PIXEL_TO_METER, 22 * MyGame.PIXEL_TO_METER);
            region = hero.getStandLeft();
        }
       else if(getCurrentState() == State.STAND_RIGHT){
            hero.getSprite().setBounds(hero.getX(), hero.getY(), 17 * MyGame.PIXEL_TO_METER, 22 * MyGame.PIXEL_TO_METER);
            region = hero.getStandRight();
        }
       else if(getCurrentState() == State.WALK_UP){
            hero.getSprite().setBounds(hero.getX(), hero.getY(), 17 * MyGame.PIXEL_TO_METER, 22 * MyGame.PIXEL_TO_METER);
            region = hero.getHeroWalkUp().getKeyFrame(hero.getUpDownTimer(), true);
        }
        return region;
    }

    /**
     * Region update part2.
     *
     * @return the texture region
     */
    private TextureRegion regionUpdate2() {
        TextureRegion region = new TextureRegion();
        if(getCurrentState() ==State.GAME_OVER){
            hero.getSprite().setBounds(hero.getX(), hero.getY(), 25 * MyGame.PIXEL_TO_METER, 24 * MyGame.PIXEL_TO_METER);
            region = hero.getHeroDying().getKeyFrame(hero.getUpDownTimer(), false);
        }
        else if(getCurrentState() ==State.STAND_UP){
            hero.getSprite().setBounds(hero.getX(), hero.getY(), 17 * MyGame.PIXEL_TO_METER, 22 * MyGame.PIXEL_TO_METER);
            region = hero.getStandBack();
        }
        else if(getCurrentState() ==State.STAND_DOWN){
            hero.getSprite().setBounds(hero.getX(), hero.getY(), 17 * MyGame.PIXEL_TO_METER, 22 * MyGame.PIXEL_TO_METER);
            region = hero.getStandFront();
        }
       return region;
    }

    /**
     * Region update part1.
     *
     * @return the texture region
     */
    private TextureRegion regionUpdate1() {
        TextureRegion region = new TextureRegion();
        if(getCurrentState() ==State.HURT){
                hero.getSprite().setBounds(hero.getX(), hero.getY(), 31 * MyGame.PIXEL_TO_METER, 32 * MyGame.PIXEL_TO_METER);
                region = hero.getHeroHurt().getKeyFrame(hero.getUpDownTimer(), false);
                hero.getSoundHurt().play(MyGame.SOUND_VOLUME);
                hero.setWasHit(false);
            }
            else if(getCurrentState() ==State.DYING){
                hero.getSoundDying().play(MyGame.SOUND_VOLUME);
                hero.getSprite().setBounds(hero.getX(), hero.getY(), 25 * MyGame.PIXEL_TO_METER, 24 * MyGame.PIXEL_TO_METER);
                region = hero.getHeroDying().getKeyFrame(1);
            }
        return region;
    }

    /**
     * Update timers.
     *
     * @param dt the dt
     */
    private void updateTimers(float dt) {
        hero.setUpDownTimer(getCurrentState() == getPreviousState() ? hero.getUpDownTimer() + dt : 0);
        if(getPreviousState() == getCurrentState()){
            hero.setLeftRightTimer(hero.getLeftRightTimer() + (dt));
        }else{
            hero.setLeftRightTimer(0);
        }
        setPreviousState(getCurrentState());
    }

    /**
     * Flip body animation.
     *
     * @param region the region
     */
    private void flipBodyAnimation(TextureRegion region) {
        if((getBody().getLinearVelocity().x<0|| getCurrentState() ==State.WALK_LEFT)&&!region.isFlipX()){
            region.flip(true, false);
            setCurrentState(State.WALK_LEFT);
        }
        else if((getBody().getLinearVelocity().x>0|| getCurrentState() ==State.WALK_RIGHT)&&region.isFlipX()){
            region.flip(true, false);
            setCurrentState(State.WALK_RIGHT);
        }
    }

    /**
     * Input update.
     *
     * @param controller the controller
     * @param dt the dt
     */
    public void InputUpdate(Controller controller, float dt){
        if(controller.isRightPressed()){
            getBody().applyLinearImpulse(new Vector2(MyGame.VELOCITY*dt,0), getBody().getWorldCenter(), true);
            movementPress(controller, dt);
        }
        else if(controller.isLeftPressed()) {
            getBody().applyLinearImpulse(new Vector2(-MyGame.VELOCITY * dt, 0), getBody().getWorldCenter(), true);
            movementPress(controller, dt);
        }
        else if(controller.isUpPressed()){
            getBody().applyLinearImpulse(new Vector2(0,MyGame.VELOCITY*dt), getBody().getWorldCenter(), true);
            movementPress(controller, dt);
        }
        else if(controller.isDownPressed()){
            getBody().applyLinearImpulse(new Vector2(0,-MyGame.VELOCITY*dt), getBody().getWorldCenter(), true);
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

    /**
     * Aux control.
     *
     * @param controller the controller
     * @param dt the dt
     */
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
        else getBody().setLinearVelocity(0, 0);
    }

    /**
     * Movement press.
     *
     * @param controller the controller
     * @param dt the dt
     */
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

    /**
     * Gets the fixture definition.
     *
     * @return the fdef
     */
    public FixtureDef getFdef() {
        return fdef;
    }

    /**
     * Gets the hero.
     *
     * @return the hero
     */
    public Hero getHero() {
        return hero;
    }

    /**
     * Gets the body.
     *
     * @return the body
     */
    public Body getBody() {
        return body;
    }

    /**
     * Sets the body.
     *
     * @param body the new body
     */
    public void setBody(Body body) {
        this.body = body;
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
     * Gets the previous state.
     *
     * @return the previous state
     */
    public State getPreviousState() {
        return previousState;
    }

    /**
     * Sets the previous state.
     *
     * @param previousState the new previous state
     */
    public void setPreviousState(State previousState) {
        this.previousState = previousState;
    }

}
