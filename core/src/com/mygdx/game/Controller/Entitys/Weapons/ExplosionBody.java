package com.mygdx.game.Controller.Entitys.Weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGame;

/**
 * Created by Jotadaxter on 18/05/2017.
 */

public class ExplosionBody {
    public static final float DAMPING_NORMAL= 3f;
    private BodyDef bdef;
    private Body body;
    private CircleShape shape;
    private FixtureDef fdef;

    public ExplosionBody(World world, Vector2 vec) {
        bdef= new BodyDef();
        bdef.position.set(vec.x* MyGame.PIXEL_TO_METER, vec.y*MyGame.PIXEL_TO_METER);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearDamping=DAMPING_NORMAL;
        body=world.createBody(bdef);
        fdef = new FixtureDef();
        shape= new CircleShape();
        shape.setRadius(15*MyGame.PIXEL_TO_METER);

        //Contact Filters
        fdef.filter.categoryBits = MyGame.BOMB_BIT;
        fdef.filter.maskBits =  MyGame.HERO_BIT
                | MyGame.ITEM_BIT
                | MyGame.BOULDER_BIT
                | MyGame.SPIKES_BIT
                | MyGame.SMASH_BIT
                | MyGame.DEFAULT_BIT
                | MyGame.PRESSING_PLATE_BIT;
        fdef.shape=shape;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData(this);
    }

    public Body getBody(){
        return body;
    }

}
