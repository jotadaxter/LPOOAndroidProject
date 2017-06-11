package com.mygdx.game.Controller.Entitys.DinamicObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Model.Entitys.DinamicObjects.MegaPressingPlate;
import com.mygdx.game.MyGame;

// TODO: Auto-generated Javadoc
/**
 * Created by Utilizador on 20-05-2017.
 */

public class MegaPressingPlateBody {
    
    /** The body. */
    private Body body;
    
    /** The bdef. */
    private BodyDef bdef;
    
    /** The fdef. */
    private FixtureDef fdef;

    /**
     * Instantiates a new mega pressing plate body.
     *
     * @param world the world
     * @param megaPressingPlate the mega pressing plate
     * @param vec the vec
     */
    public MegaPressingPlateBody(World world, MegaPressingPlate megaPressingPlate, Vector2 vec) {
        bdef = new BodyDef();
        bdef.position.set(vec.x * MyGame.PIXEL_TO_METER, vec.y * MyGame.PIXEL_TO_METER);
        bdef.type = BodyDef.BodyType.StaticBody;//ver solidez do objeto
        body = world.createBody(bdef);

        fdef= new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16*MyGame.PIXEL_TO_METER,16*MyGame.PIXEL_TO_METER);
        fdef.filter.categoryBits= MyGame.MEGA_PRESSING_PLATE_BIT;
        fdef.filter.maskBits =MyGame.DEFAULT_BIT
                | MyGame.SPIKES_BIT
                | MyGame.BOULDER_BIT
                | MyGame.HERO_BIT;
        fdef.shape= shape;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData(megaPressingPlate);
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

