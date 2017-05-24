package com.mygdx.game.Model.Entitys.DinamicObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.DinamicObjects.SmashableRockBody;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

import java.util.concurrent.TimeUnit;

/**
 * Created by Jotadaxter on 24/05/2017.
 */

public class SmashableRock extends Sprite{
    private World world;
    private TextureRegion blockFigure;
    private boolean toDestroy;
    private boolean destroyed;
    private SmashableRockBody rockBody;
    private Sound sound;
    private int timer;
    private boolean incTimer;

    public SmashableRock(GameScreen screen, int x, int y) {
        this.world=screen.getWorld();
        rockBody= new SmashableRockBody(world,this,x,y);
        incTimer=false;
        destroyed=false;
        toDestroy=false;
        timer=0;
        blockFigure = new TextureRegion(screen.getAtlas().findRegion("destroyable_rock"), 0,0,16,16);
        setPosition(x,y);
        setBounds(0,0,16* MyGame.PIXEL_TO_METER,16* MyGame.PIXEL_TO_METER);
        setRegion(blockFigure);
        sound= Gdx.audio.newSound(Gdx.files.internal("Sounds/rock_shatter.wav"));
    }

    public void destroy(){
        incTimer=true;
    }

    public void update(float dt){
        if(toDestroy && !destroyed){
            sound.play();
            world.destroyBody(rockBody.getBody());
            destroyed=true;
            Gdx.app.log("destroyed","");
        }
        setPosition(rockBody.getBody().getPosition().x-getWidth()/2, rockBody.getBody().getPosition().y-getHeight()/2);
        if(incTimer)
            timer+=dt*100;
        System.out.println(timer);
        if(timer>=130){
            toDestroy=true;
            System.out.println(toDestroy);
        }
    }

    @Override
    public void draw(Batch batch) {
        if(!destroyed)
            super.draw(batch);
    }
}
