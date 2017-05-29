package com.mygdx.game.Controller.Entitys;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.MyGame;

/**
 * Created by Utilizador on 28-05-2017.
 */

public class StaticContactShapes {
    private FixtureDef sensorfix;
    private Body body;
    private EdgeShape contact;
    private Vector2 vec1;
    private Vector2 vec2;
    private String name;

    public StaticContactShapes(Body body, EdgeShape edgeShape) {
        this.contact=edgeShape;
        this.body=body;
        sensorfix= new FixtureDef();
        sensorfix.filter.categoryBits= MyGame.DEFAULT_BIT;
        sensorfix.filter.maskBits = MyGame.ITEM_BIT
                | MyGame.DEFAULT_BIT
                | MyGame.SPIKES_BIT
                | MyGame.BOULDER_BIT
                | MyGame.WARP_OBJECT
                | MyGame.PRESSING_PLATE_BIT;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(4*MyGame.PIXEL_TO_METER,6.5f*MyGame.PIXEL_TO_METER);
    }

    public void setVec1(Vector2 vec1) {
        this.vec1 = vec1;
    }

    public void setVec2(Vector2 vec2) {
        this.vec2 = vec2;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void shapeDef(){
        contact.set(vec1, vec2);
        sensorfix.shape=contact;
        sensorfix.isSensor=true;
        body.createFixture(sensorfix).setUserData(name);
    }
}