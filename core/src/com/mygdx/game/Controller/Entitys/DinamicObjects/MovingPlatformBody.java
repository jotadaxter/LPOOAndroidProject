package com.mygdx.game.Controller.Entitys.DinamicObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.CommonBody;
import com.mygdx.game.Model.Entitys.DinamicObjects.MovingPlatform;
import com.mygdx.game.MyGame;

/**
 * Created by Utilizador on 20-05-2017.
 */

public class MovingPlatformBody extends CommonBody{
    public MovingPlatformBody(World world, MovingPlatform movingPlatform,Vector2 vec) {
        super(world,vec);
        body.setUserData(movingPlatform);
    }

    @Override
    protected BodyDef.BodyType bodyDefinitionType() {
        return BodyDef.BodyType.DynamicBody;
    }

    @Override
    protected float damping() {
        return 0;
    }

    @Override
    protected float restitution() {
        return 0;
    }

    @Override
    protected short setCategoryBits() {
        return MyGame.MOVING_PLATFORM_BIT;
    }

    @Override
    protected boolean isSensorVal() {
        return true;
    }

    @Override
    protected boolean ShapeCircle() {
        return false;
    }

    @Override
    protected float setRadius() {
        return 0;
    }

    @Override
    protected Vector2 shapeDimentions() {
        return new Vector2(16,16);
    }

    @Override
    protected short setMaskBits() {
        return MyGame.DEFAULT_BIT
                | MyGame.SPIKES_BIT
                | MyGame.BOULDER_BIT
                | MyGame.PITFALL_BIT
                | MyGame.HERO_BIT;
    }

    public Body getBody() {
        return body;
    }

    public FixtureDef getFdef() {
        return fdef;
    }
}
