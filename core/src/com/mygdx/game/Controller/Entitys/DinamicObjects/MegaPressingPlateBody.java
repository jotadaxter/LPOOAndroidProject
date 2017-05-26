package com.mygdx.game.Controller.Entitys.DinamicObjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Model.Entitys.DinamicObjects.MegaPressingPlate;
import com.mygdx.game.Model.Entitys.DinamicObjects.PressingPlate;
import com.mygdx.game.MyGame;

/**
 * Created by Utilizador on 20-05-2017.
 */

public class MegaPressingPlateBody {
    private Rectangle bounds;
    private Body body;
    private BodyDef bdef;
    private FixtureDef fdef;
    private MegaPressingPlate megaPressingPlate;
    public MegaPressingPlateBody(World world, MegaPressingPlate megaPressingPlate, float x, float y) {
        this.megaPressingPlate=megaPressingPlate;
        bdef = new BodyDef();
        bdef.position.set(x * MyGame.PIXEL_TO_METER, y * MyGame.PIXEL_TO_METER);
        bdef.type = BodyDef.BodyType.StaticBody;//ver solidez do objeto
        body = world.createBody(bdef);

        fdef= new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16*MyGame.PIXEL_TO_METER,16*MyGame.PIXEL_TO_METER);
        fdef.filter.categoryBits= MyGame.MEGA_PRESSING_PLATE_BIT;
        fdef.filter.maskBits =MyGame.DEFAULT_BIT
                | MyGame.SPIKES_BIT
                | MyGame.BOULDER_BIT
                | MyGame.HERO_BIT;
        fdef.shape= shape;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData(megaPressingPlate);
    }
    public Body getBody() {
        return body;
    }

    public FixtureDef getFdef() {
        return fdef;
    }

    public TextureRegion getFrame(float dt) {
        TextureRegion region;

        if(megaPressingPlate.isPressed()>=2) {
            region=megaPressingPlate.getPressedTex();
        }else{
            region=megaPressingPlate.getNotPressedTex();
        }

        return region;
    }
}
