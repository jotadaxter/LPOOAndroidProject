package com.mygdx.game.Controller.Entitys.DinamicObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Model.Entitys.DinamicObjects.FireGround;
import com.mygdx.game.MyGame;

// TODO: Auto-generated Javadoc
/**
 * Created by Utilizador on 20-05-2017.
 */

public class FireGroundBody{
    
    /** The body. */
    private Body body;
    
    /** The bdef. */
    private BodyDef bdef;
    
    /** The fdef. */
    private FixtureDef fdef;

    /**
     * Instantiates a new fire ground body.
     *
     * @param world the world
     * @param fireGround the fire ground
     * @param vec the vec
     */
    public FireGroundBody(World world, FireGround fireGround, Vector2 vec) {
        bdef = new BodyDef();
        bdef.position.set(vec.x * MyGame.PIXEL_TO_METER, vec.y * MyGame.PIXEL_TO_METER);
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);

        fdef= new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8*MyGame.PIXEL_TO_METER,8*MyGame.PIXEL_TO_METER);
        fdef.filter.categoryBits= MyGame.SPIKES_BIT;
        fdef.filter.maskBits = MyGame.HERO_BIT
                | MyGame.BOULDER_BIT
                | MyGame.ITEM_BIT
                | MyGame.DEFAULT_BIT
                | MyGame.PRESSING_PLATE_BIT;
        fdef.shape= shape;
        fdef.restitution = 3f;
        body.createFixture(fdef).setUserData(fireGround);
    }

    /**
     * Gets the body.
     *
     * @return the body
     */
    public Body getBody() {
        return body;
    }

    /**
     * Gets the fdef.
     *
     * @return the fdef
     */
    public FixtureDef getFdef() {
        return fdef;
    }

}
