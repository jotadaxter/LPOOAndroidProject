package com.mygdx.game.Controller.Entitys.DinamicObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGame;

/**
 * Created by Jotadaxter on 28/04/2017.
 */

public class BoulderBody {
    private Body body;
    private BodyDef bdef;

    public BoulderBody(World world, int x, int y) {
        bdef= new BodyDef();
        bdef.position.set(x* MyGame.PIXEL_TO_METER, y* MyGame.PIXEL_TO_METER);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearDamping=6f;
        body=world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7.5f* MyGame.PIXEL_TO_METER);
        fdef.filter.categoryBits= MyGame.BOULDER_BIT;
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
        body.createFixture(fdef);
    }
    public Body getBody(){
        return body;
    }
}
