package com.mygdx.game.Controller.Entitys.Items;

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
    private Jewel jewel;

    public JewelBody(World world,Jewel jewel,int value, float x, float y) {
        //Body Definition
        bdef= new BodyDef();
        bdef.position.set(x*MyGame.PIXEL_TO_METER, y*MyGame.PIXEL_TO_METER);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body=world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        //Fisical Dimentions
        if(value>1 && value<50)
            shape.setAsBox(6*MyGame.PIXEL_TO_METER,8*MyGame.PIXEL_TO_METER);
        else
            shape.setAsBox(7*MyGame.PIXEL_TO_METER,8*MyGame.PIXEL_TO_METER);
        fdef.filter.categoryBits = MyGame.ITEM_BIT;
        fdef.filter.maskBits =  MyGame.HERO_BIT;
        fdef.shape= shape;
        body.createFixture(fdef).setUserData(jewel);
    }
    public Body getBody(){
        return body;
    }
}
