package com.mygdx.game.Controller.Entitys.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.MyGame;

/**
 * Created by Utilizador on 06-04-2017.
 */

public abstract class StaticTileObject {
    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;
    protected BodyDef bdef;
    protected MapObject object;
    protected FixtureDef fdef;
    protected Fixture fixture;
    protected LogicController logicController;

    public StaticTileObject(LogicController logicController, MapObject object) {
        this.object=object;
        this.logicController=logicController;
        this.bounds =((RectangleMapObject) object).getRectangle();
        this.map= logicController.getTiledMap();
        this.world= logicController.getWorld();
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
        fdef.isSensor=setSensor();
        fdef.filter.maskBits = MyGame.ALL_BIT;
        fixture=body.createFixture(fdef);
    }

    protected abstract boolean setSensor();

    public void setCategoryFilter(short filterBit){
        Filter filter= new Filter();
        filter.categoryBits=filterBit;
        fixture.setFilterData(filter);
    }

}
