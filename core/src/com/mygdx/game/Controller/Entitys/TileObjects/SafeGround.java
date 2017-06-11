package com.mygdx.game.Controller.Entitys.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

// TODO: Auto-generated Javadoc
/**
 * Created by Utilizador on 21-05-2017.
 */

public class SafeGround extends StaticTileObject{
    
    /**
     * Instantiates a new safe ground.
     *
     * @param logicController the logic controller
     * @param object the object
     */
    public SafeGround(LogicController logicController, MapObject object) {
        super(logicController, object);
        setCategoryFilter(MyGame.SAFE_GROUND_BIT);
    }

    /* (non-Javadoc)
     * @see com.mygdx.game.Controller.Entitys.TileObjects.StaticTileObject#setSensor()
     */
    @Override
    protected boolean setSensor() {
        return true;
    }

}
