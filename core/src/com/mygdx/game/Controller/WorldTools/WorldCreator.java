package com.mygdx.game.Controller.WorldTools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.TileObjects.Door;
import com.mygdx.game.Controller.Entitys.TileObjects.PitFall;
import com.mygdx.game.Controller.Entitys.TileObjects.SafeGround;
import com.mygdx.game.View.GameScreens.GameScreen;
import com.mygdx.game.Controller.Entitys.TileObjects.Obstacle;

/**
 * Created by Utilizador on 06-04-2017.
 */

public class WorldCreator {
    public WorldCreator(GameScreen screen) {
        TiledMap tiledMap= screen.getMap();

        //Obstacle Fixtures
        for(MapObject object : tiledMap.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            String name="";
            name= (String)object.getProperties().get("behaviour");
            new Obstacle(screen, object, name);
        }

        //Doors Fixtures
        for(MapObject object : tiledMap.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            String name="";
            name= (String)object.getProperties().get("Id");
            new Door(screen, object, Integer.parseInt(name));
        }

        //PitFall Fixtures
        for(MapObject object : tiledMap.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            new PitFall(screen, object);
        }

        //SafeGround Fixtures
        for(MapObject object : tiledMap.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            new SafeGround(screen, object);
        }

    }
}
