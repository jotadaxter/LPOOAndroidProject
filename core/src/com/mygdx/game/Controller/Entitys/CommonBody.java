package com.mygdx.game.Controller.Entitys;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGame;

/**
 * Created by Utilizador on 27-05-2017.
 */

public abstract class CommonBody {
    protected World world;
    protected BodyDef bdef;
    protected Body body;
    protected CircleShape shape;
    protected FixtureDef fdef;

    public CommonBody(World world, Vector2 vec){
        bdef = new BodyDef();
        bdef.position.set(vec.x * MyGame.PIXEL_TO_METER, vec.y * MyGame.PIXEL_TO_METER);
        bdef.type = bodyDefinitionType();
        bdef.linearDamping=damping();
        body = world.createBody(bdef);
        fdef= new FixtureDef();
        if(ShapeCircle()) {
            CircleShape shape = new CircleShape();
            shape.setRadius(setRadius() * MyGame.PIXEL_TO_METER);
            fdef.shape=shape;
        }else{
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(shapeDimentions().x*MyGame.PIXEL_TO_METER,shapeDimentions().y*MyGame.PIXEL_TO_METER);
            fdef.shape=shape;
        }
        //setFilters();
        fdef.filter.categoryBits= setCategoryBits();
        fdef.filter.maskBits = setMaskBits();
        fdef.isSensor=isSensorVal();

        fdef.restitution = restitution();
        body.createFixture(fdef);
    }

    protected abstract BodyDef.BodyType bodyDefinitionType();

    protected abstract float damping();

    protected abstract float restitution();

    protected abstract short setCategoryBits();

    protected abstract boolean isSensorVal();

    protected abstract boolean ShapeCircle();

    protected abstract float setRadius();

    protected abstract Vector2 shapeDimentions();

    protected abstract short setMaskBits();

    public Body getBody(){
        return body;
    }
}
