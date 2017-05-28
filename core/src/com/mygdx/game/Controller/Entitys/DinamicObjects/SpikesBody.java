package com.mygdx.game.Controller.Entitys.DinamicObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.CommonBody;
import com.mygdx.game.Model.Entitys.DinamicObjects.Spikes;
import com.mygdx.game.MyGame;

/**
 * Created by Jotadaxter on 28/04/2017.
 */

public class SpikesBody extends CommonBody{
    public SpikesBody(World world, Spikes spikes, Vector2 vec) {
        super(world,vec);
        body.setUserData(spikes);
    }

    @Override
    protected BodyDef.BodyType bodyDefinitionType() {
        return BodyDef.BodyType.StaticBody;
    }

    @Override
    protected float damping() {
        return 0;
    }

    @Override
    protected float restitution() {
        return 3f;
    }

    @Override
    protected short setCategoryBits() {
        return MyGame.SPIKES_BIT;
    }

    @Override
    protected boolean isSensorVal() {
        return false;
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
        return new Vector2(8,8);
    }

    @Override
    protected short setMaskBits() {
        return MyGame.HERO_BIT
                | MyGame.BOULDER_BIT
                | MyGame.ITEM_BIT
                | MyGame.DEFAULT_BIT
                | MyGame.PRESSING_PLATE_BIT;
    }
}
