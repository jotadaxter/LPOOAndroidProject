package com.mygdx.game.Controller.Entitys.Items;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Model.Entitys.Items.Heart;
import com.mygdx.game.Model.Entitys.Items.SpecialItem;
import com.mygdx.game.MyGame;

/**
 * Created by Utilizador on 20-05-2017.
 */

public class SpecialItemBody {
    private BodyDef bdef;
    private Body body;
    private SpecialItem specialItem;

    public SpecialItemBody(World world, SpecialItem specialItem, float x, float y) {
        //Body Definition
        this.specialItem=specialItem;
        bdef= new BodyDef();
        bdef.position.set(x* MyGame.PIXEL_TO_METER, y*MyGame.PIXEL_TO_METER);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body=world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        //Fisical Dimentions
        shape.setRadius(4*MyGame.PIXEL_TO_METER);

        //Contact Filters
        fdef.filter.categoryBits = MyGame.ITEM_BIT;
        fdef.filter.maskBits =  MyGame.HERO_BIT
                | MyGame.ITEM_BIT
                | MyGame.BOULDER_BIT
                | MyGame.SPIKES_BIT
                | MyGame.SMASH_BIT
                | MyGame.DEFAULT_BIT
                | MyGame.PRESSING_PLATE_BIT;
        fdef.shape= shape;
        body.createFixture(fdef).setUserData(specialItem);
    }

    public Body getBody(){
        return body;
    }

}
