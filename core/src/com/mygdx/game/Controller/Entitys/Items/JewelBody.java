package com.mygdx.game.Controller.Entitys.Items;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Model.Entitys.Items.Jewel;
import com.mygdx.game.MyGame;

/**
 * Created by Jotadaxter on 28/04/2017.
 */

public class JewelBody {
    private BodyDef bdef;
    private Body body;

    public JewelBody(World world,Jewel jewel,int value, Vector2 vec) {
        bdef= new BodyDef();
        bdef.position.set(vec.x*MyGame.PIXEL_TO_METER, vec.y*MyGame.PIXEL_TO_METER);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body=world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        if(value>1 && value<50)
            shape.setAsBox(4*MyGame.PIXEL_TO_METER,6*MyGame.PIXEL_TO_METER);
        else
            shape.setAsBox(7*MyGame.PIXEL_TO_METER,8*MyGame.PIXEL_TO_METER);
        fdef.filter.categoryBits = MyGame.ITEM_BIT;
        fdef.filter.maskBits =  MyGame.HERO_BIT
                | MyGame.ITEM_BIT
                | MyGame.BOULDER_BIT
                | MyGame.SPIKES_BIT
                | MyGame.SMASH_BIT
                | MyGame.DEFAULT_BIT
                | MyGame.PRESSING_PLATE_BIT;
        fdef.shape= shape;
        body.createFixture(fdef).setUserData(jewel);
    }
    public Body getBody(){
        return body;
    }
}