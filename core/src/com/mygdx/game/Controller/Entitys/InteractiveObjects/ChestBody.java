package com.mygdx.game.Controller.Entitys.InteractiveObjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Model.Entitys.InteractiveObjects.Chest;
import com.mygdx.game.MyGame;

/**
 * Created by Utilizador on 21-05-2017.
 */

public class ChestBody{
    private Body body;
    private BodyDef bdef;
    private Chest chest;

    public ChestBody(World world, Chest chest, Vector2 vec) {
        this.chest = chest;
        bdef = new BodyDef();
        bdef.position.set(vec.x * MyGame.PIXEL_TO_METER, vec.y * MyGame.PIXEL_TO_METER);
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.linearDamping = 6f;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8 * MyGame.PIXEL_TO_METER, 8 * MyGame.PIXEL_TO_METER);
        fdef.filter.categoryBits = MyGame.CHEST_BIT;
        fdef.filter.maskBits = MyGame.ITEM_BIT
                | MyGame.DEFAULT_BIT
                | MyGame.SPIKES_BIT
                | MyGame.HERO_BIT
                | MyGame.WARP_OBJECT
                | MyGame.BOULDER_BIT
                | MyGame.MEGA_PRESSING_PLATE_BIT
                | MyGame.PRESSING_PLATE_BIT;
        fdef.shape = shape;
        fdef.restitution = 0f;
        body.createFixture(fdef).setUserData(chest);
    }

    public Body getBody() {
        return body;
    }

    public TextureRegion getFrame(float dt) {
        TextureRegion region;

        if (chest.isOpen()) {
            region = chest.getOpenTex();
        } else {
            region = chest.getClosedTex();
        }

        return region;
    }
}
