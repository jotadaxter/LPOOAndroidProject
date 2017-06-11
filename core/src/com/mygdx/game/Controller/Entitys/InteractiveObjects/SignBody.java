package com.mygdx.game.Controller.Entitys.InteractiveObjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Model.Entitys.InteractiveObjects.Sign;
import com.mygdx.game.MyGame;

/**
 * Created by Utilizador on 21-05-2017.
 */

public class SignBody{
    
    /** The body. */
    private Body body;
    
    /** The body definition. */
    private BodyDef bdef;

    /**
     * Instantiates a new sign body.
     *
     * @param world the world
     * @param sign the sign
     * @param vec the vec
     */
    public SignBody(World world, Sign sign, Vector2 vec) {
        bdef= new BodyDef();
        bdef.position.set(vec.x* MyGame.PIXEL_TO_METER, vec.y* MyGame.PIXEL_TO_METER);
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.linearDamping=6f;
        body=world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8*MyGame.PIXEL_TO_METER,8*MyGame.PIXEL_TO_METER);
        fdef.filter.categoryBits= MyGame.SIGN_BIT;
        fdef.filter.maskBits = MyGame.ITEM_BIT
                | MyGame.DEFAULT_BIT
                | MyGame.SPIKES_BIT
                | MyGame.HERO_BIT
                | MyGame.WARP_OBJECT
                | MyGame.BOULDER_BIT
                | MyGame.MEGA_PRESSING_PLATE_BIT
                | MyGame.PRESSING_PLATE_BIT;
        fdef.shape=shape;
        fdef.restitution = 0f;
        body.createFixture(fdef).setUserData(sign);
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
