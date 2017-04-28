package com.mygdx.game.Controller.WorldTools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.View.Screens.MyScreen;
import com.mygdx.game.Controller.Entitys.TileObjects.Obstacle;

/**
 * Created by Utilizador on 06-04-2017.
 */

public class WorldCreator {
    public WorldCreator(MyScreen screen) {
        World world=screen.getWorld();
        TiledMap tiledMap= screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //Wall Fixtures
        for(MapObject object : tiledMap.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            new Obstacle(screen, object);
        }

        //Obstacle Fixtures
        for(MapObject object : tiledMap.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            new Obstacle(screen, object);
        }
    }
}
