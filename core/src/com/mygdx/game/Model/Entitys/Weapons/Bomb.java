package com.mygdx.game.Model.Entitys.Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Controller.Entitys.Weapons.BombBody;
import com.mygdx.game.Controller.Entitys.Weapons.ExplosionBody;
import com.mygdx.game.Model.Entitys.Hero.Hero;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Jotadaxter on 18/05/2017.
 */

public class Bomb extends Sprite{
    public static final float MAX_TIMING=2.575f;
    private GameScreen screen;
    private Hero hero;
    private World world;
    private boolean toDestroy;
    private boolean destroyed;
    private BombBody bombBody;
    private ExplosionBody explosionBody;

    public enum State {TIC_TAC, BOOM, LAST};
    public State currentState;
    private State previousState;

    //textures
    private TextureRegion blue;
    private TextureRegion red;
    //Animations
    private Animation<TextureRegion> tic_tac;
    private Animation<TextureRegion> boom;
    public float timer;
    private float tic_tac_timer;
    private float boom_timer;

    private Sound sound1;
    private Sound sound2;

    public Bomb(GameScreen screen, Hero hero, float x, float y) {
        this.screen=screen;
        this.world=screen.getWorld();
        this.hero=hero;
        bombBody= new BombBody(world, this, x,y);
        setPosition(x,y);
        toDestroy=false;
        destroyed=false;
        timer=0;
        explosionBody= new ExplosionBody(world,0,0);
        tic_tac_timer=0;
        textureLoad();
        currentState=State.TIC_TAC;
        animationLoad();
        setRegion(blue);
        setBounds(0, 0, 12*MyGame.PIXEL_TO_METER, 16*MyGame.PIXEL_TO_METER);
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
            frames.add(new TextureRegion(screen.getGame().assetManager.get("Game/explosion.png", Texture.class), i * 47, 0, 47, 51));
        }
        boom = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

    }

    public float counter(){
        return 0.1f;

    }

    private void textureLoad() {
        blue= new TextureRegion(screen.getGame().assetManager.get("Game/bombs.png", Texture.class), 0,0,12,16);
        red= new TextureRegion(screen.getGame().assetManager.get("Game/bombs.png", Texture.class), 12,0,12,16);
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
        setPosition(bombBody.getBody().getPosition().x-getWidth()/2, bombBody.getBody().getPosition().y-getHeight()/2);
        timer+=dt;

        if(this.timer<MAX_TIMING)
            setRegion(getFrame(dt));
        else destroy();
    }

    public void draw(Batch batch){
        if(!destroyed)
            super.draw(batch);
    }

    public Body getBody(){
        return bombBody.getBody();
    }

    public void setNewPosition(float x, float y){
        bombBody.getBody().setTransform(x,y,0);
        explosionBody.getBody().setTransform(x,y,0);
    }

    public State getState() {
        if(timer<2){
            hero.bombExploding=false;
            return State.TIC_TAC;
        }
        else if(timer>=2 && timer<=2.1){
            hero.bombExploding=true;
            return State.LAST;
        }
        else{
            hero.bombExploding=true;
            return State.BOOM;
        }
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region = new TextureRegion();
        switch (currentState) {
            case TIC_TAC: {
                setBounds(getX(), getY(), 12*MyGame.PIXEL_TO_METER, 16*MyGame.PIXEL_TO_METER);
                region = tic_tac.getKeyFrame(tic_tac_timer, true);
                sound1.play();
            }
            break;
            case LAST: {
                setBounds(getX(), getY(), 47*MyGame.PIXEL_TO_METER, 51*MyGame.PIXEL_TO_METER);
                region = boom.getKeyFrame(1);
                sound2.play();
            }
            break;
            case BOOM: {
                setBounds(getX(), getY(), 47*MyGame.PIXEL_TO_METER, 51*MyGame.PIXEL_TO_METER);
                region = boom.getKeyFrame(tic_tac_timer, false);
            }
            break;

        }
       tic_tac_timer=currentState == previousState ?tic_tac_timer + dt : 0;
        if(previousState==currentState){
            tic_tac_timer+=(dt);
        }else{
            tic_tac_timer=0;
        }
        previousState=currentState;
        return region;
    }

    public void hitHero() {
        hero.hit();
    }


    public World getWorld() {
        return world;
    }

}
