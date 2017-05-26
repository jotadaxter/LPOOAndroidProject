package com.mygdx.game.Model.Entitys.DinamicObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.DinamicObjects.SpikesBody;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Jotadaxter on 28/04/2017.
 */

public class Spikes extends Sprite {
    //public static final int POSX = 250;
    //public static final int POSY = 200;

    private World world;
    private TextureRegion spikesFigure;

    private SpikesBody spikesBody;

    public Spikes(GameScreen screen,float x,float y) {
        super(screen.getAtlas().findRegion("spikes"));
        this.world=screen.getWorld();

        spikesBody= new SpikesBody(world,this,x,y);


        spikesFigure = new TextureRegion(screen.getAtlas().findRegion("spikes"), 0,0,16,16);
        setPosition(x,y);
        setBounds(0,0,16* MyGame.PIXEL_TO_METER,16* MyGame.PIXEL_TO_METER);
        setRegion(spikesFigure);
    }

    public void update(float dt){
        setPosition(spikesBody.getBody().getPosition().x-getWidth()/2, spikesBody.getBody().getPosition().y-getHeight()/2);
    }
}
