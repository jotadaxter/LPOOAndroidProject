package com.mygdx.game.Controller.Entitys.Weapons;

import com.badlogic.gdx.math.Vector2;
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

public class BombBody {
    
    /** The Constant DAMPING_NORMAL. */
    public static final float DAMPING_NORMAL= 0f;
    
    /** The body definition. */
    private BodyDef bdef;
    
    /** The body. */
    private Body body;
    
    /** The normal shape. */
    private CircleShape normalShape;
    
    /** The fixture definition. */
    private FixtureDef fdef;

    /**
     * Instantiates a new bomb body.
     *
     * @param world the world
     * @param vec the vec
     */
    public BombBody(World world,Vector2 vec) {
        bdef= new BodyDef();
        bdef.position.set(vec.x* MyGame.PIXEL_TO_METER, vec.y*MyGame.PIXEL_TO_METER);
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.linearDamping=DAMPING_NORMAL;
        body=world.createBody(bdef);
        fdef = new FixtureDef();
        normalShape= new CircleShape();
        normalShape.setRadius(4*MyGame.PIXEL_TO_METER);
        fdef.filter.categoryBits = MyGame.DEFAULT_BIT;
        fdef.filter.maskBits =  MyGame.HERO_BIT
                | MyGame.ITEM_BIT
                | MyGame.BOULDER_BIT
                | MyGame.SMASH_BIT
                | MyGame.SPIKES_BIT
                | MyGame.DEFAULT_BIT
                | MyGame.PRESSING_PLATE_BIT;
        fdef.shape=normalShape;
        body.createFixture(fdef).setUserData(this);
    }

    /**
     * Gets the body.
     *
     * @return the body
     */
    public Body getBody(){
        return body;
    }

}
