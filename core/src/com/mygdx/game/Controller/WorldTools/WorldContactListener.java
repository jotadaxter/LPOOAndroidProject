package com.mygdx.game.Controller.WorldTools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Controller.Entitys.TileObjects.Door;
import com.mygdx.game.Controller.Entitys.TileObjects.StaticTileObject;
import com.mygdx.game.Model.Entitys.DinamicObjects.PressingPlate;
import com.mygdx.game.Model.Entitys.DinamicObjects.Spikes;
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

        impactVerify("upContact", fixA, fixB);
        impactVerify("downContact", fixA, fixB);
        impactVerify("leftContact", fixA, fixB);
        impactVerify("leftContact", fixA, fixB);

        //Collision Verify
        switch(cDef){
            case MyGame.ITEM_BIT | MyGame.HERO_BIT:
                if(fixA.getFilterData().categoryBits==MyGame.ITEM_BIT)
                    ((Item) fixA.getUserData()).use((Hero) fixB.getUserData());
                else
                    ((Item) fixB.getUserData()).use((Hero) fixA.getUserData());
                break;
            case MyGame.HERO_BIT | MyGame.SPIKES_BIT:
                if(fixA.getFilterData().categoryBits==MyGame.HERO_BIT)
                    ((Hero) fixA.getUserData()).hitbySpikes();
                else
                    ((Hero) fixB.getUserData()).hitbySpikes();
                break;
            case MyGame.HERO_BIT | MyGame.PRESSING_PLATE_BIT:
                if(fixA.getFilterData().categoryBits==MyGame.PRESSING_PLATE_BIT)
                    ((PressingPlate) fixA.getUserData()).incIsPressed();
                else
                    ((PressingPlate) fixB.getUserData()).incIsPressed();
                break;
            case MyGame.BOULDER_BIT | MyGame.PRESSING_PLATE_BIT:
                if(fixA.getFilterData().categoryBits==MyGame.PRESSING_PLATE_BIT)
                    ((PressingPlate) fixA.getUserData()).incIsPressed();
                else
                    ((PressingPlate) fixB.getUserData()).incIsPressed();
                break;

        }

    }

    private void impactVerify(String surfaceName, Fixture fixA, Fixture fixB) {
        if(fixA.getUserData()==surfaceName || fixB.getUserData() == surfaceName){
            Fixture surface = fixA.getUserData()==surfaceName ? fixA : fixB;
            Fixture object = surface == fixA ? fixB : fixA;

            if(object.getUserData() instanceof StaticTileObject){
                if(object.getUserData() instanceof Door)
                    ((Door)object.getUserData()).warp();
            }
        }

    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        //Collision Verify
        switch(cDef){
            case MyGame.HERO_BIT | MyGame.PRESSING_PLATE_BIT:
                if(fixA.getFilterData().categoryBits==MyGame.PRESSING_PLATE_BIT) {
                    if (((PressingPlate) fixA.getUserData()).isPressAndHold() && ((PressingPlate) fixA.getUserData()).isPressed()>0)
                        ((PressingPlate) fixA.getUserData()).decIsPressed();
                }
                else{
                    if (((PressingPlate) fixB.getUserData()).isPressAndHold() && ((PressingPlate) fixB.getUserData()).isPressed()>0)
                        ((PressingPlate) fixB.getUserData()).decIsPressed();
                }
                break;
            case MyGame.BOULDER_BIT | MyGame.PRESSING_PLATE_BIT:
                if(fixA.getFilterData().categoryBits==MyGame.PRESSING_PLATE_BIT) {
                    if (((PressingPlate) fixA.getUserData()).isPressAndHold() && ((PressingPlate) fixA.getUserData()).isPressed()>0)
                        ((PressingPlate) fixA.getUserData()).decIsPressed();
                }
                else{
                    if (((PressingPlate) fixB.getUserData()).isPressAndHold() && ((PressingPlate) fixB.getUserData()).isPressed()>0)
                        ((PressingPlate) fixB.getUserData()).decIsPressed();
                }
                break;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
