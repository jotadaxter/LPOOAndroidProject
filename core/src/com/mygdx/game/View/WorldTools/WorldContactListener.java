package com.mygdx.game.View.WorldTools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.MyGame;
import com.mygdx.game.Model.Entitys.Hero.Hero;
import com.mygdx.game.Model.Entitys.Items.Item;

/**
 * Created by Utilizador on 09-04-2017.
 */

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        /*impactVerify("upContact", fixA, fixB);
        impactVerify("downContact", fixA, fixB);
        impactVerify("leftContact", fixA, fixB);
        impactVerify("leftContact", fixA, fixB);*/

        //Collision Verify
        switch(cDef){
            case MyGame.ITEM_BIT | MyGame.HERO_BIT:
                if(fixA.getFilterData().categoryBits==MyGame.ITEM_BIT)
                    ((Item) fixA.getUserData()).use((Hero) fixB.getUserData());
                else
                    ((Item) fixB.getUserData()).use((Hero) fixA.getUserData());
                break;

        }

    }

    private void impactVerify(String surfaceName, Fixture fixA, Fixture fixB) {
        if(fixA.getUserData()==surfaceName || fixB.getUserData() == surfaceName){
            Fixture surface = fixA.getUserData() ==surfaceName ? fixA : fixB;
            Fixture object = surface == fixA ? fixB : fixA;

            if(object.getUserData() instanceof Item){
                ((Item) object.getUserData()).pickedUp();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
