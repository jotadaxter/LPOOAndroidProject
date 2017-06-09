package com.mygdx.game.Model.Entitys.DinamicObjects;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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
    private int timer;
    private boolean incTimer;

    public SmashableRock(GameScreen screen, Vector2 vec) {
        this.world=screen.getWorld();
        rockBody= new SmashableRockBody(world,this,vec);
        incTimer=false;
        destroyed=false;
        toDestroy=false;
        timer=0;
        blockFigure = new TextureRegion(screen.getAtlas().findRegion("destroyable_rock"), 0,0,16,16);
        setPosition(vec.x,vec.y);
        setBounds(0,0,16* MyGame.PIXEL_TO_METER,16* MyGame.PIXEL_TO_METER);
        setRegion(blockFigure);
    }

    public void destroy(){
        incTimer=true;
    }

    public void update(float dt){
        if(toDestroy && !destroyed){
            world.destroyBody(rockBody.getBody());
            destroyed=true;
        }
        setPosition(rockBody.getBody().getPosition().x-getWidth()/2, rockBody.getBody().getPosition().y-getHeight()/2);
        if(incTimer)
            timer+=dt*100;
        if(timer>=130 && !(Gdx.app.getType() == Application.ApplicationType.Android)){
            toDestroy=true;
        }else if(timer>=220 && (Gdx.app.getType() == Application.ApplicationType.Android)){
            toDestroy=true;
        }
    }

    @Override
    public void draw(Batch batch) {
        if(!destroyed)
            super.draw(batch);
    }
}
