package com.mygdx.game.Controller.Entitys.Weapons;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Model.Entitys.Items.Heart;
import com.mygdx.game.Model.Entitys.Weapons.Bomb;
import com.mygdx.game.MyGame;

/**
 * Created by Jotadaxter on 18/05/2017.
 */

public class ExplosionBody {
    public static final float DAMPING_NORMAL= 3f;
    private World world;
    private BodyDef bdef;
    private Body body;
    private Bomb bomb;
    private CircleShape shape;
    private FixtureDef fdef;

    public ExplosionBody(World world, Bomb bomb, float x, float y) {
        //Body Definition
        this.bomb=bomb;
        this.world=world;
        bdef= new BodyDef();
        bdef.position.set(x* MyGame.PIXEL_TO_METER, y*MyGame.PIXEL_TO_METER);
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
                | MyGame.DEFAULT_BIT
                | MyGame.PRESSING_PLATE_BIT;
        fdef.shape=shape;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData(bomb);
    }

    public Body getBody(){
        return body;
    }

    public Bomb getBomb() {
        return bomb;
    }
}