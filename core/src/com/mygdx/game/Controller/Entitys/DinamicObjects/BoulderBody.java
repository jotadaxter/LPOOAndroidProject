package com.mygdx.game.Controller.Entitys.DinamicObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.CommonBody;
import com.mygdx.game.Model.Entitys.DinamicObjects.Boulder;
import com.mygdx.game.MyGame;

/**
 * Created by Jotadaxter on 28/04/2017.
 */

public class BoulderBody extends CommonBody{
    Boulder boulder;

    public BoulderBody(World world,Boulder boulder, float x, float y) {
        super(world,new Vector2(x,y));
        this.boulder=boulder;
    }

    @Override
    protected BodyDef.BodyType bodyDefinitionType() {
        return BodyDef.BodyType.DynamicBody;
    }
    @Override
    protected float damping() {
        return 6f;
    }
    @Override
    protected float restitution() {
        return 0f;
    }

    @Override
    protected short setCategoryBits() {
        return MyGame.BOULDER_BIT;
    }

    @Override
    protected boolean isSensorVal() {
        return false;
    }

    @Override
    protected boolean ShapeCircle() {
        return true;
    }

    @Override
    protected float setRadius() {
        return 7.5f;
    }

    @Override
    protected Vector2 shapeDimentions() {
        return null;
    }

    @Override
    protected short setMaskBits() {
        return MyGame.ITEM_BIT
                | MyGame.DEFAULT_BIT
                | MyGame.SPIKES_BIT
                | MyGame.HERO_BIT
                | MyGame.WARP_OBJECT
                | MyGame.SMASH_BIT
                | MyGame.BOULDER_BIT
                | MyGame.MEGA_PRESSING_PLATE_BIT
                | MyGame.PRESSING_PLATE_BIT;
    }
}
