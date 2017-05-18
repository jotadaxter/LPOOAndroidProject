package com.mygdx.game.Model.Entitys.Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.Weapons.BombBody;
import com.mygdx.game.Model.Entitys.Hero.Hero;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Jotadaxter on 18/05/2017.
 */

public class Bomb extends Sprite{
    private GameScreen screen;
    private World world;
    private boolean toDestroy;
    private boolean destroyed;
    private BombBody bombBody;

    //textures
    private TextureRegion blue;
    private TextureRegion red;
    //Animations
    private Animation<TextureRegion> tic_tac;
    private Animation<TextureRegion> boom;

    public Bomb(GameScreen screen, float x, float y) {
        this.screen=screen;
        this.world=screen.getWorld();
        bombBody= new BombBody(screen.getWorld(), this, x,y);
        setPosition(x,y);
        toDestroy=false;
        destroyed=false;
        textureLoad();
        animationLoad();
        setRegion(blue);
    }

    private void animationLoad() {
    }

    private void textureLoad() {
        blue= new TextureRegion(new Texture(Gdx.files.internal("bomb_normal.png")));
        red= new TextureRegion(new Texture(Gdx.files.internal("bomb_red.png")));
    }

    public void use(Hero hero) {
        toDestroy=true;
    }

    public void update(float dt) {
        if(toDestroy && !destroyed){
            world.destroyBody(bombBody.getBody());
            destroyed=true;
        }
        setPosition(bombBody.getBody().getPosition().x-getWidth()/2, bombBody.getBody().getPosition().y-getHeight()/2);
    }

    public void draw(Batch batch){
        if(!destroyed)
            super.draw(batch);
    }

}
