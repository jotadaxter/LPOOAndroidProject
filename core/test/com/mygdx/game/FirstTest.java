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
    
    /** The Constant FIRST_POSX. */
    private static final double FIRST_POSX = 0.5;
    
    /** The Constant FIRST_POSY. */
    private static final double FIRST_POSY = 0.5;
    
    /** The logic controller. */
    private LogicController logicController;
    
    /** The game. */
    private MyGame game;
    
    /**
     * Before.
     */
    @Before
    public void before(){
        game=new MyGame(true);
        logicController= new LogicController(game, new Vector2(8+4*16,8+2*16), Null.class);
        logicController.defineMap("Maps/test_map.tmx");
    }

    /**
     * After.
     */
    @After
    public void after(){
       // logicController.dispose();
    }

    /**
     * Test player move.
     */
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

    @Test
    public void testPlayerBomb() {
        int initialBombs = logicController.getPlayer().getBombs().size();
        logicController.getController().setbPressed(true);
        for(float dt= 0; dt <1; dt++) {
            logicController.update(dt);
        }
        int finalBombs = logicController.getPlayer().getBombs().size();
        assertTrue(initialBombs < finalBombs);

    }
}