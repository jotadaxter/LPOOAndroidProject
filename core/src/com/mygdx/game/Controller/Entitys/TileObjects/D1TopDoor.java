package com.mygdx.game.Controller.Entitys.TileObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Jotadaxter on 24/05/2017.
 */

public class D1TopDoor extends Sprite {
    private World world;
    private Body body;
    private BodyDef bdef;
    private GameScreen screen;
    private FixtureDef fdef;
    private Fixture fixture;
    private TextureRegion textureRegion1;
    private TextureRegion textureRegion2;

    public D1TopDoor(GameScreen screen, float x,float y, int choose){
        setPosition(x,y);
        bdef= new BodyDef();
        this.screen=screen;
        this.world=screen.getWorld();
        fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(x* MyGame.PIXEL_TO_METER,y * MyGame.PIXEL_TO_METER);
        body=world.createBody(bdef);

        if(choose==0){
            shape.setAsBox(24*MyGame.PIXEL_TO_METER, 8*MyGame.PIXEL_TO_METER);
            fdef.shape=shape;
            fdef.isSensor=true;
            fixture=body.createFixture(fdef);
            textureRegion1 = new TextureRegion(screen.getGame().assetManager.get("Game/door_top.png", Texture.class), 0,0,48,16);
            setBounds(0,0,48*MyGame.PIXEL_TO_METER,16*MyGame.PIXEL_TO_METER);
            setRegion(textureRegion1);
        }
        else{
            shape.setAsBox(8*MyGame.PIXEL_TO_METER, 24*MyGame.PIXEL_TO_METER);
            fdef.shape=shape;
            fdef.isSensor=true;
            fixture=body.createFixture(fdef);
            textureRegion2 = new TextureRegion(screen.getGame().assetManager.get("Game/door_top2.png", Texture.class), 0,0,16,48);
            setBounds(0,0,16*MyGame.PIXEL_TO_METER,48*MyGame.PIXEL_TO_METER);
            setRegion(textureRegion2);
        }



    }
    public void update(float dt) {
       setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    public Body getBody() {
        return body;
    }
}
