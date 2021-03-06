package com.mygdx.game.Controller.Entitys.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Utilizador on 06-04-2017.
 */

public class Obstacle extends StaticTileObject {
    
    /** The code. */
    private String code;

    /**
     * Instantiates a new obstacle.
     *
     * @param logicController the logic controller
     * @param object the object
     * @param code the code
     */
    public Obstacle(LogicController logicController, MapObject object, String code) {
        super(logicController, object);
        setCategoryFilter(MyGame.DEFAULT_BIT);
        fixture.setUserData(this);
        this.code=code;
    }

    /**
     * Sets the sensor
     *
     * @return false
     */
    @Override
    protected boolean setSensor() {
        return false;
    }
}
