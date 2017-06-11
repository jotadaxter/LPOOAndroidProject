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
    public static final float MAX_TIMING=2.575f;
    private Hero hero;
    private World world;
    private boolean toDestroy;
    private boolean destroyed;
    private BombBody bombBody;
    private ExplosionBody explosionBody;
    private float soundTimer;
    public enum State {TIC_TAC, BOOM}
    private State currentState;
    private State previousState;

    //textures
    private TextureRegion blue;
    private TextureRegion red;
    //Animations
    private Animation<TextureRegion> tic_tac;
    private Animation<TextureRegion> boom;
    private float timer;
    private float tic_tac_timer;
    private Sound sound1;
    private Sound sound2;
    private Sprite sprite;
    private LogicController logicController;
    private Vector2 position;

    public Bomb(LogicController logicController, Hero hero, Vector2 vec) {
        this.world= logicController.getWorld();
        this.hero=hero;
        bombBody= new BombBody(world, this,new Vector2( vec.x,vec.y));
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

    private void textureLoad() {
        blue= new TextureRegion(logicController.getGame().getAssetManager().get("Game/bombs.png", Texture.class), 0,0,12,16);
        red= new TextureRegion(logicController.getGame().getAssetManager().get("Game/bombs.png", Texture.class), 12,0,12,16);
    }

    public void destroy() {
        toDestroy=true;
    }

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

    public void draw(Batch batch){
        if(!destroyed)
            sprite.draw(batch);
    }

    public Body getBody(){
        return bombBody.getBody();
    }

    public void setNewPosition(float x, float y){
        bombBody.getBody().setTransform(x,y,0);
        explosionBody.getBody().setTransform(x,y,0);
    }

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

    public TextureRegion getFrame(float dt) {
        setCurrentState(getState());
        TextureRegion region = getRegion();
        timerUpdate(dt);
        return region;
    }

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

    private void timerUpdate(float dt) {
        tic_tac_timer= getCurrentState() == previousState ?tic_tac_timer + dt : 0;
        if(previousState== getCurrentState()){
            tic_tac_timer+=(dt);
        }else{
            tic_tac_timer=0;
        }
        previousState= getCurrentState();
    }

    public World getWorld() {
        return world;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public float getTimer() {
        return timer;
    }

    public void setTimer(float timer) {
        this.timer = timer;
    }

}
