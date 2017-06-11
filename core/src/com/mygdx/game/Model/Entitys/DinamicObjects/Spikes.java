package com.mygdx.game.Model.Entitys.DinamicObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.DinamicObjects.SpikesBody;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.MyGame;

/**
 * Created by Jotadaxter on 28/04/2017.
 */

public class Spikes{
    private World world;
    private TextureRegion spikesFigure;
    private SpikesBody spikesBody;
    private Vector2 position;
    private Sprite sprite;
    private LogicController logicController;

    public Spikes(LogicController logicController, Vector2 vec) {
        this.world= logicController.getWorld();
        position=vec;
        this.logicController=logicController;
        sprite=new Sprite();
        spikesBody= new SpikesBody(world,this,vec);
        spikesFigure = new TextureRegion(logicController.getGame().getAssetManager().get("Game/spikes.png", Texture.class), 0,0,16,16);
        if(!logicController.getGame().getIsTest()) {
            sprite.setPosition(vec.x, vec.y);
            sprite.setBounds(0, 0, 16 * MyGame.PIXEL_TO_METER, 16 * MyGame.PIXEL_TO_METER);
            sprite.setRegion(spikesFigure);
        }
    }

    public void update(){
        if(!logicController.getGame().getIsTest()) {
            sprite.setPosition(spikesBody.getBody().getPosition().x-sprite.getWidth()/2, spikesBody.getBody().getPosition().y-sprite.getHeight()/2);
        }else {
            position=new Vector2(spikesBody.getBody().getPosition().x, spikesBody.getBody().getPosition().y);
        }
    }
    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }

}
