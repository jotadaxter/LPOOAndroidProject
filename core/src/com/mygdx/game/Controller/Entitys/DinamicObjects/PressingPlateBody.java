package com.mygdx.game.Controller.Entitys.DinamicObjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.CommonBody;
import com.mygdx.game.Model.Entitys.DinamicObjects.PressingPlate;
import com.mygdx.game.MyGame;

/**
 * Created by Jotadaxter on 02/05/2017.
 */

public class PressingPlateBody extends CommonBody{
    public PressingPlateBody(World world, PressingPlate pressingPlate, Vector2 vec) {
        super(world,vec);
        body.setUserData(pressingPlate);
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
        return 0;
    }

    @Override
    protected short setCategoryBits() {
        return MyGame.PRESSING_PLATE_BIT;
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
        return new Vector2(8,8);
    }

    @Override
    protected short setMaskBits() {
        return MyGame.DEFAULT_BIT
                | MyGame.SPIKES_BIT
                | MyGame.BOULDER_BIT
                | MyGame.HERO_BIT;
    }

    public Body getBody() {
        return body;
    }

    public FixtureDef getFdef() {
        return fdef;
    }

    public TextureRegion getFrame(PressingPlate pressingPlate, float dt) {
        TextureRegion region = new TextureRegion();

        if(pressingPlate.isPressed()>0) {
            region=pressingPlate.getPressedTex();
        }else{
            region=pressingPlate.getNotPressedTex();
        }

        return region;
    }
}
