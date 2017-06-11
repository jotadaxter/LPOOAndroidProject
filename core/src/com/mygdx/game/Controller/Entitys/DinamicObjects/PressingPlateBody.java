package com.mygdx.game.Controller.Entitys.DinamicObjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Model.Entitys.DinamicObjects.PressingPlate;
import com.mygdx.game.MyGame;

/**
 * Created by Jotadaxter on 02/05/2017.
 */

public class PressingPlateBody{
    
    /** The body. */
    private Body body;
    
    /** The body definition. */
    private BodyDef bdef;
    
    /** The fixture definition. */
    private FixtureDef fdef;

    /**
     * Instantiates a new pressing plate body.
     *
     * @param world the world
     * @param pressingPlate the pressingPlate
     * @param vec the vec
     */
    public PressingPlateBody(World world, PressingPlate pressingPlate, Vector2 vec) {
       bdef = new BodyDef();
       bdef.position.set(vec.x * MyGame.PIXEL_TO_METER, vec.y * MyGame.PIXEL_TO_METER);
       bdef.type = BodyDef.BodyType.StaticBody;
       body = world.createBody(bdef);

       fdef= new FixtureDef();
       PolygonShape shape = new PolygonShape();
       shape.setAsBox(8*MyGame.PIXEL_TO_METER,8*MyGame.PIXEL_TO_METER);
       fdef.filter.categoryBits= MyGame.PRESSING_PLATE_BIT;
       fdef.filter.maskBits =MyGame.DEFAULT_BIT
               | MyGame.SPIKES_BIT
               | MyGame.BOULDER_BIT
               | MyGame.HERO_BIT;
       fdef.shape= shape;
       fdef.isSensor = true;
       body.createFixture(fdef).setUserData(pressingPlate);
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
     * Gets the fixture definition.
     *
     * @return the fdef
     */
    public FixtureDef getFdef() {
        return fdef;
    }
    
    /**
     * Gets the frame.
     *
     * @param pressingPlate the pressing plate
     * @param dt the dt
     * @return the frame
     */
    public TextureRegion getFrame(PressingPlate pressingPlate, float dt) {
        TextureRegion region = new TextureRegion();

        if(pressingPlate.isPressed()>0) {
            region=pressingPlate.getPressedTex();
        }else{
            region=pressingPlate.getNotPressedTex();
        }
        return region;
    }
}
