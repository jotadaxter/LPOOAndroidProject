package com.mygdx.game.Controller.Entitys.Items;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Model.Entitys.Items.Heart;
import com.mygdx.game.MyGame;

/**
 * Created by Jotadaxter on 28/04/2017.
 */

public class HeartBody {
    
    /** The body definition. */
    private BodyDef bdef;
    
    /** The body. */
    private Body body;

    /**
     * Instantiates a new heart body.
     *
     * @param world the world
     * @param heart the heart
     * @param vec the vec
     */
    public HeartBody(World world, Heart heart, Vector2 vec) {
        bdef= new BodyDef();
        bdef.position.set(vec.x*MyGame.PIXEL_TO_METER, vec.y*MyGame.PIXEL_TO_METER);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body=world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(4*MyGame.PIXEL_TO_METER);
        fdef.filter.categoryBits = MyGame.ITEM_BIT;
        fdef.filter.maskBits =  MyGame.HERO_BIT
                | MyGame.ITEM_BIT
                | MyGame.SMASH_BIT
                | MyGame.BOULDER_BIT
                | MyGame.SPIKES_BIT
                | MyGame.DEFAULT_BIT
                | MyGame.PRESSING_PLATE_BIT;
        fdef.shape= shape;
        body.createFixture(fdef).setUserData(heart);
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