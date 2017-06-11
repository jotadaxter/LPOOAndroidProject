package com.mygdx.game.Controller.Entitys.TileObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.MyGame;

/**
 * Created by Jotadaxter on 24/05/2017.
 */

public class D1TopDoor extends Sprite {
    private World world;
    private Body body;
    private BodyDef bdef;
    private FixtureDef fdef;
    private TextureRegion textureRegion1;
    private TextureRegion textureRegion2;

    public D1TopDoor(LogicController logicController, Vector2 vec, int choose){
        setPosition(vec.x,vec.y);
        bdef= new BodyDef();
        this.world= logicController.getWorld();
        fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(vec.x* MyGame.PIXEL_TO_METER,vec.y * MyGame.PIXEL_TO_METER);
        body=world.createBody(bdef);

        if(choose==0){
            shape.setAsBox(24*MyGame.PIXEL_TO_METER, 8*MyGame.PIXEL_TO_METER);
            fdef.shape=shape;
            fdef.isSensor=true;
            body.createFixture(fdef);
            textureRegion1 = new TextureRegion(logicController.getGame().getAssetManager().get("Game/door_top.png", Texture.class), 0,0,48,16);
            setBounds(0,0,48*MyGame.PIXEL_TO_METER,16*MyGame.PIXEL_TO_METER);
            setRegion(textureRegion1);
        }
        else{
            shape.setAsBox(8*MyGame.PIXEL_TO_METER, 24*MyGame.PIXEL_TO_METER);
            fdef.shape=shape;
            fdef.isSensor=true;
            body.createFixture(fdef);
            textureRegion2 = new TextureRegion(logicController.getGame().getAssetManager().get("Game/door_top2.png", Texture.class), 0,0,16,48);
            setBounds(0,0,16*MyGame.PIXEL_TO_METER,48*MyGame.PIXEL_TO_METER);
            setRegion(textureRegion2);
        }



    }
    public void update() {
       setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    public Body getBody() {
        return body;
    }
}
