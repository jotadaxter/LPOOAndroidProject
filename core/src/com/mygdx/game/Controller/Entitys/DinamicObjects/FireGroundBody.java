package com.mygdx.game.Controller.Entitys.DinamicObjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Model.Entitys.DinamicObjects.FireGround;
import com.mygdx.game.Model.Entitys.DinamicObjects.Spikes;
import com.mygdx.game.MyGame;

/**
 * Created by Utilizador on 20-05-2017.
 */

public class FireGroundBody {
    private Rectangle bounds;
    private Body body;
    private BodyDef bdef;
    private FixtureDef fdef;

    public FireGroundBody(World world, FireGround fireGround, int x, int y) {
        bdef = new BodyDef();
        bdef.position.set(x * MyGame.PIXEL_TO_METER, y * MyGame.PIXEL_TO_METER);
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);

        fdef= new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8*MyGame.PIXEL_TO_METER,8*MyGame.PIXEL_TO_METER);
        fdef.filter.categoryBits= MyGame.SPIKES_BIT;
        fdef.filter.maskBits = MyGame.HERO_BIT
                | MyGame.BOULDER_BIT
                | MyGame.ITEM_BIT
                | MyGame.DEFAULT_BIT
                | MyGame.PRESSING_PLATE_BIT;
        fdef.shape= shape;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData(fireGround);

    }

    public Body getBody() {
        return body;
    }

    public FixtureDef getFdef() {
        return fdef;
    }

}
