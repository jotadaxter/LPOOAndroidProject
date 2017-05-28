package com.mygdx.game.Controller.Entitys.InteractiveObjects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.CommonBody;
import com.mygdx.game.Model.Entitys.DinamicObjects.MegaPressingPlate;
import com.mygdx.game.Model.Entitys.InteractiveObjects.Chest;
import com.mygdx.game.MyGame;

/**
 * Created by Utilizador on 21-05-2017.
 */

public class ChestBody extends CommonBody{
    private Chest chest;

    public ChestBody(World world, Chest chest, Vector2 vec) {
        super(world, vec);
        this.chest=chest;
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
        return  MyGame.CHEST_BIT;
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
        return MyGame.ITEM_BIT
                | MyGame.DEFAULT_BIT
                | MyGame.SPIKES_BIT
                | MyGame.HERO_BIT
                | MyGame.WARP_OBJECT
                | MyGame.BOULDER_BIT
                | MyGame.MEGA_PRESSING_PLATE_BIT
                | MyGame.PRESSING_PLATE_BIT;
    }

    public Body getBody(){
        return body;
    }
}
