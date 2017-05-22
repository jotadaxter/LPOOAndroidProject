package com.mygdx.game.Controller.Entitys.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Utilizador on 06-04-2017.
 */

public abstract class StaticTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected BodyDef bdef;
    protected GameScreen screen;
    protected MapObject object;
    protected FixtureDef fdef;
    protected Fixture fixture;

    public StaticTileObject(GameScreen screen, MapObject object) {
        this.screen=screen;
        this.object=object;
        this.bounds =((RectangleMapObject) object).getRectangle();
        this.map=screen.getMap();
        this.world=screen.getWorld();
        defineBody();
    }

    private void defineBody() {
        bdef= new BodyDef();
        fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth()/2)* MyGame.PIXEL_TO_METER, (bounds.getY() + bounds.getHeight()/2)*MyGame.PIXEL_TO_METER);
        body=world.createBody(bdef);
        shape.setAsBox((bounds.getWidth()/2)*MyGame.PIXEL_TO_METER, (bounds.getHeight()/2)*MyGame.PIXEL_TO_METER);
        fdef.shape=shape;
        fdef.restitution = 0f;
        fixture=body.createFixture(fdef);
    }

}
