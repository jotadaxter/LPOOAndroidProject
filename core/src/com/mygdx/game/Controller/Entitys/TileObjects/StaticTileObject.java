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
    
    /** The world. */
    protected World world;
    
    /** The map. */
    protected TiledMap map;
    
    /** The bounds. */
    protected Rectangle bounds;
    
    /** The body. */
    protected Body body;
    
    /** The bdef. */
    protected BodyDef bdef;
    
    /** The object. */
    protected MapObject object;
    
    /** The fdef. */
    protected FixtureDef fdef;
    
    /** The fixture. */
    protected Fixture fixture;
    
    /** The logic controller. */
    protected LogicController logicController;

    /**
     * Instantiates a new static tile object.
     *
     * @param logicController the logic controller
     * @param object the object
     */
    public StaticTileObject(LogicController logicController, MapObject object) {
        this.object=object;
        this.logicController=logicController;
        this.bounds =((RectangleMapObject) object).getRectangle();
        this.map= logicController.getTiledMap();
        this.world= logicController.getWorld();
        defineBody();
    }

    /**
     * Define body.
     */
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

    /**
     * Sets the sensor.
     *
     * @return true, if successful
     */
    protected abstract boolean setSensor();

    /**
     * Sets the category filter.
     *
     * @param filterBit the new category filter
     */
    public void setCategoryFilter(short filterBit){
        Filter filter= new Filter();
        filter.categoryBits=filterBit;
        fixture.setFilterData(filter);
    }

}
