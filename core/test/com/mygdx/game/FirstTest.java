package com.mygdx.game;

import static org.junit.Assert.*;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Controller.LogicController;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.Null;

/**
 * Created by Utilizador on 30-05-2017.
 */
public class FirstTest extends GameTest{
    private static final double FIRST_POSX = 0.5;
    private static final double FIRST_POSY = 0.5;
    private LogicController logicController;
    private MyGame game;
    @Before
    public void before(){
        game=new MyGame(true);
        logicController= new LogicController(game, new Vector2(8+4*16,8+2*16), Null.class);
        logicController.defineMap("Maps/test_map.tmx");
    }

    @After
    public void after(){
       // logicController.dispose();
    }

    @Test
    public void testPlayerMove() {
        assertEquals(4, logicController.getPlayer().getHeroBody().getBody().getPosition().x, FIRST_POSX);
        assertEquals(2, logicController.getPlayer().getHeroBody().getBody().getPosition().y, FIRST_POSY);
        logicController.getController().setRightPressed(true);
        for(float dt= 0; dt <1000; dt++) {
            logicController.update(dt);
        }
        assertTrue(logicController.getPlayer().getHeroBody().getBody().getPosition().x>=3.0);
        assertEquals(2, logicController.getPlayer().getHeroBody().getBody().getPosition().y, FIRST_POSY);
    }
}