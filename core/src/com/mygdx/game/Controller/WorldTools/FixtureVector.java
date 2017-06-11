package com.mygdx.game.Controller.WorldTools;

import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * Created by Utilizador on 29-05-2017.
 */

public class FixtureVector {
    
    /** The fix A. */
    private Fixture fixA;
    
    /** The fix B. */
    private Fixture fixB;

    /**
     * Instantiates a new fixture vector.
     *
     * @param fixA the fix A
     * @param fixB the fix B
     */
    public FixtureVector(Fixture fixA, Fixture fixB) {
        this.fixA = fixA;
        this.fixB = fixB;
    }

    /**
     * Gets the fix A.
     *
     * @return the fix A
     */
    public Fixture getFixA() {
        return fixA;
    }

    /**
     * Gets the fix B.
     *
     * @return the fix B
     */
    public Fixture getFixB() {
        return fixB;
    }
}
