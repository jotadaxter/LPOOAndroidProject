package com.mygdx.game.Controller.Entitys.DinamicObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Model.Entitys.DinamicObjects.MovingPlatform;
import com.mygdx.game.MyGame;

/**
 * Created by Utilizador on 20-05-2017.
 */

public class MovingPlatformBody {
    private Rectangle bounds;
    private Body body;
    private BodyDef bdef;
    private FixtureDef fdef;

    public MovingPlatformBody(World world, MovingPlatform movingPlatform, float x, float y) {
        bdef = new BodyDef();
        bdef.position.set(x * MyGame.PIXEL_TO_METER, y * MyGame.PIXEL_TO_METER);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        fdef= new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16*MyGame.PIXEL_TO_METER,16*MyGame.PIXEL_TO_METER);
        fdef.filter.categoryBits= MyGame.MOVING_PLATFORM_BIT;
        fdef.filter.maskBits =MyGame.DEFAULT_BIT
                | MyGame.SPIKES_BIT
                | MyGame.BOULDER_BIT
                | MyGame.PITFALL_BIT
                | MyGame.HERO_BIT;
        fdef.shape= shape;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData(movingPlatform);
    }
    public Body getBody() {
        return body;
    }

    public FixtureDef getFdef() {
        return fdef;
    }

}
