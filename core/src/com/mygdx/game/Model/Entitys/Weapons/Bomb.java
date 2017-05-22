package com.mygdx.game.Model.Entitys.Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Controller.Entitys.Weapons.BombBody;
import com.mygdx.game.Controller.Entitys.Weapons.ExplosionBody;
import com.mygdx.game.Model.Entitys.Hero.Hero;
import com.mygdx.game.MyGame;

/**
 * Created by Jotadaxter on 18/05/2017.
 */

public class Bomb extends Sprite{
    public static final float MAX_TIMING=2.575f;
    private Hero hero;
    private World world;
    private boolean toDestroy;
    private boolean destroyed;
    private BombBody bombBody;
    private ExplosionBody exBody;

    public enum State {TIC_TAC, BOOM};
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


    public Bomb(World world,Hero hero, float x, float y) {
        this.world=world;
        this.hero=hero;
        bombBody= new BombBody(world, this, x,y);
        exBody= new ExplosionBody(world, this, x,y);
        setPosition(x,y);
        toDestroy=false;
        destroyed=false;
        timer=0;
        tic_tac_timer=0;
        textureLoad();
        currentState=State.TIC_TAC;
        animationLoad();
        setRegion(blue);
        setBounds(0, 0, 12*MyGame.PIXEL_TO_METER, 16*MyGame.PIXEL_TO_METER);

    }

    private void animationLoad() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(blue);
        frames.add(red);
        tic_tac = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 11; i++) {
            frames.add(new TextureRegion(new Texture("explosion.png"), i * 47, 0, 47, 51));
        }
        boom = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

    }

    public float counter(){
        return 0.1f;

    }

    private void textureLoad() {
        blue= new TextureRegion(new Texture("bomb_normal.png"));
        red= new TextureRegion(new Texture(Gdx.files.internal("bomb_red.png")));
    }

    public void destroy() {
        toDestroy=true;
    }

    public void update(float dt) {
        if(toDestroy && !destroyed){
            world.destroyBody(bombBody.getBody());
            world.destroyBody(exBody.getBody());

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

    public void setposition(float x, float y){
        bombBody.getBody().setTransform(x,y,0);
        exBody.getBody().setTransform(x,y,0);
    }

    public State getState() {
        if(timer<2){
            hero.bombExploding=false;
            return State.TIC_TAC;
        }
        else {
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
