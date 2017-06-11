package com.mygdx.game.Model.Entitys.DinamicObjects;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.DinamicObjects.SmashableRockBody;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.MyGame;

/**
 * Created by Jotadaxter on 24/05/2017.
 */

public class SmashableRock{
    private World world;
    private TextureRegion blockFigure;
    private boolean toDestroy;
    private boolean destroyed;
    private SmashableRockBody rockBody;
    private int timer;
    private boolean incTimer;
    private Vector2 position;
    private Sprite sprite;
    private LogicController logicController;

    public SmashableRock(LogicController logicController, Vector2 vec) {
        this.world= logicController.getWorld();
        rockBody= new SmashableRockBody(world,this,vec);
        incTimer=false;
        destroyed=false;
        toDestroy=false;
        timer=0;
        position=vec;
        this.logicController=logicController;
        sprite=new Sprite();
        blockFigure = new TextureRegion(logicController.getGame().getAssetManager().get("Game/destroyable_rock.png", Texture.class), 0,0,16,16);
        if(!logicController.getGame().getIsTest()) {
            sprite.setPosition(vec.x, vec.y);
            sprite.setBounds(0, 0, 16 * MyGame.PIXEL_TO_METER, 16 * MyGame.PIXEL_TO_METER);
            sprite.setRegion(blockFigure);
        }
    }

    public void destroy(){
        incTimer=true;
    }

    public void update(float dt){
        if(toDestroy && !destroyed){
            world.destroyBody(rockBody.getBody());
            destroyed=true;
        }

        if(!logicController.getGame().getIsTest()) {
            sprite.setPosition(rockBody.getBody().getPosition().x-sprite.getWidth()/2, rockBody.getBody().getPosition().y-sprite.getHeight()/2);
        }else {
            position=new Vector2(rockBody.getBody().getPosition().x, rockBody.getBody().getPosition().y);
        }

        if(incTimer)
            timer+=dt*100;
        if(timer>=130 && !(Gdx.app.getType() == Application.ApplicationType.Android)){
            toDestroy=true;
        }else if(timer>=220 && (Gdx.app.getType() == Application.ApplicationType.Android)){
            toDestroy=true;
        }
    }

    public void draw(Batch batch) {
        if(!destroyed)
            sprite.draw(batch);
    }
}
