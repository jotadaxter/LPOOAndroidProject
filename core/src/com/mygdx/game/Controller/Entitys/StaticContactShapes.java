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
    public StaticContactShapes(Body body) {
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
        sensorfix.shape= shape;

        //Up Contact Line
        EdgeShape upContact= new EdgeShape();
        shapeDef(upContact,"upContact" ,new Vector2(-4*MyGame.PIXEL_TO_METER,10*MyGame.PIXEL_TO_METER), new Vector2(4*MyGame.PIXEL_TO_METER,10*MyGame.PIXEL_TO_METER));

        //Down Contact Line
        EdgeShape downContact= new EdgeShape();
        shapeDef(downContact,"downContact" ,new Vector2(-4*MyGame.PIXEL_TO_METER,-10*MyGame.PIXEL_TO_METER), new Vector2(4*MyGame.PIXEL_TO_METER,-10*MyGame.PIXEL_TO_METER));

        //Left Contact Line
        EdgeShape leftContact= new EdgeShape();
        shapeDef(leftContact,"leftContact" ,new Vector2(5*MyGame.PIXEL_TO_METER,-5*MyGame.PIXEL_TO_METER), new Vector2(5*MyGame.PIXEL_TO_METER,5*MyGame.PIXEL_TO_METER));

        //Right Contact Line
        EdgeShape rightContact= new EdgeShape();
        shapeDef(rightContact,"rightContact" ,new Vector2(-5*MyGame.PIXEL_TO_METER, -5*MyGame.PIXEL_TO_METER), new Vector2(-5*MyGame.PIXEL_TO_METER, 5*MyGame.PIXEL_TO_METER));
    }

    public void shapeDef(EdgeShape contact, String name, Vector2 vec1, Vector2 vec2){
        contact.set(vec1, vec2);
        sensorfix.shape=contact;
        sensorfix.isSensor=true;
        body.createFixture(sensorfix).setUserData(name);
    }
}
