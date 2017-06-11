package com.mygdx.game.Controller.WorldTools;

import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * Created by Utilizador on 29-05-2017.
 */

public class FixtureVector {
    private Fixture fixA;
    private Fixture fixB;

    public FixtureVector(Fixture fixA, Fixture fixB) {
        this.fixA = fixA;
        this.fixB = fixB;
    }

    public Fixture getFixA() {
        return fixA;
    }

    public Fixture getFixB() {
        return fixB;
    }
}
