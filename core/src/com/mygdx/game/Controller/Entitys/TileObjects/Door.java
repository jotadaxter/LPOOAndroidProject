package com.mygdx.game.Controller.Entitys.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.google.gwt.user.client.rpc.core.java.lang.Integer_CustomFieldSerializer;
import com.mygdx.game.Model.States.GameState;
import com.mygdx.game.Model.States.WarpEvent;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.DemoScreen;
import com.mygdx.game.View.GameScreens.GameScreen;
import com.mygdx.game.View.MenuScreens.MainMenu;
import com.mygdx.game.View.MenuScreens.MenuScreen;

/**
 * Created by Utilizador on 09-05-2017.
 */

public class Door extends StaticTileObject {
    private Integer id;

    public Door(GameScreen screen, MapObject object, Integer id) {
        super(screen, object);
        fixture.setUserData(this);
        this.id=id;
    }

    public void warp() {
        Gdx.app.log("door touched", "");
        if(screen.getClass()==DemoScreen.class){
            screen.getGame().gameStateManager.states.pop();
            screen.getGame().setScreen(screen.getGame().gameStateManager.states.peek().getGameScreen());
            Gdx.input.setInputProcessor(screen.getGame().gameStateManager.states.peek().getGameScreen().getController().getStage());
        }
        else {
            for (WarpEvent warpEvent : screen.getWarpEvents())
                if (warpEvent.id == this.id) {
                    //screen.getGame().gameStateManager.states.pop();
                    screen.getGame().gameStateManager.states.push(warpEvent.travelPoint);
                    screen.getGame().setScreen(screen.getGame().gameStateManager.states.peek().getGameScreen());
                    Gdx.input.setInputProcessor(warpEvent.travelPoint.getGameScreen().getController().getStage());
                }
        }
    }

    public int getId() {
        return id;
    }
}
