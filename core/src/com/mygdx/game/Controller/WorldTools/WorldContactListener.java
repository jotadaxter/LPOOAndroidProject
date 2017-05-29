package com.mygdx.game.Controller.WorldTools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Controller.Controller;
import com.mygdx.game.Controller.Entitys.TileObjects.Door;
import com.mygdx.game.Controller.Entitys.TileObjects.StaticTileObject;
import com.mygdx.game.Model.Entitys.DinamicObjects.MegaPressingPlate;
import com.mygdx.game.Model.Entitys.DinamicObjects.MovingPlatform;
import com.mygdx.game.Model.Entitys.DinamicObjects.PressingPlate;
import com.mygdx.game.Model.Entitys.DinamicObjects.SmashableRock;
import com.mygdx.game.Model.Entitys.InteractiveObjects.Chest;
import com.mygdx.game.Model.Entitys.InteractiveObjects.Sign;
import com.mygdx.game.Model.Entitys.Weapons.Bomb;
import com.mygdx.game.MyGame;
import com.mygdx.game.Model.Entitys.Hero.Hero;
import com.mygdx.game.Model.Entitys.Items.Item;

/**
 * Created by Utilizador on 09-04-2017.
 */

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        FixtureVector fixVec = new FixtureVector(contact.getFixtureA(), contact.getFixtureB());
        int cDef = fixVec.getFixA().getFilterData().categoryBits | fixVec.getFixB().getFilterData().categoryBits;

        impactVerify("upContact", fixVec);
        impactVerify("downContact", fixVec);
        impactVerify("leftContact", fixVec);
        impactVerify("leftContact", fixVec);

        dinamicBeginContactVerify1(cDef, fixVec);
        dinamicBeginContactVerify2(cDef, fixVec);
        dinamicBeginContactVerify3(cDef, fixVec);
    }

    private void dinamicBeginContactVerify1(int cDef,FixtureVector fixVec) {
        switch(cDef) {
            case MyGame.ITEM_BIT | MyGame.HERO_BIT:
                heroItemBegin(fixVec.getFixA(), fixVec.getFixB());
                break;
            case MyGame.HERO_BIT | MyGame.SPIKES_BIT:
                heroSpikesBegin(fixVec.getFixA(),fixVec.getFixB());
                break;
            case MyGame.HERO_BIT | MyGame.PRESSING_PLATE_BIT:
                heroPlateBegin(fixVec.getFixA(),fixVec.getFixB());
                break;
            case MyGame.BOULDER_BIT | MyGame.PRESSING_PLATE_BIT:
                boulderPlateBegin(fixVec.getFixA(),fixVec.getFixB());
                break;
        }
    }

    private void dinamicBeginContactVerify2(int cDef,FixtureVector fixVec) {
        switch(cDef){
            case MyGame.BOULDER_BIT | MyGame.MEGA_PRESSING_PLATE_BIT:
                bolderMegaPlateBegin(fixVec.getFixA(),fixVec.getFixB());
                break;
            case MyGame.PITFALL_BIT | MyGame.HERO_BIT:
                heroPitfallBegin(fixVec.getFixA(),fixVec.getFixB());
                break;
            case MyGame.MOVING_PLATFORM_BIT | MyGame.HERO_BIT:
                heroPlatformBegin(fixVec.getFixA(), fixVec.getFixB());
                break;
            case MyGame.HERO_BIT | MyGame.CHEST_BIT:
                heroChestBegin(fixVec.getFixA(), fixVec.getFixB());
                break;
        }
    }

    private void dinamicBeginContactVerify3(int cDef,FixtureVector fixVec) {
        switch(cDef){
            case MyGame.HERO_BIT | MyGame.MEGA_PRESSING_PLATE_BIT:
                heroMegaPlateBegin(fixVec.getFixA(), fixVec.getFixB());
                break;
            case MyGame.HERO_BIT | MyGame.SIGN_BIT:
                signalHeroBegin(fixVec.getFixA(),fixVec.getFixB());
                break;
            case  MyGame.BOMB_BIT | MyGame.SMASH_BIT:
                bombSmashBegin(fixVec.getFixA(),fixVec.getFixB());
                break;
        }
    }

    private void heroItemBegin(Fixture fixA, Fixture fixB) {
        if(fixA.getFilterData().categoryBits==MyGame.ITEM_BIT)
            ((Item) fixA.getUserData()).use((Hero) fixB.getUserData());
        else
            ((Item) fixB.getUserData()).use((Hero) fixA.getUserData());
    }

    private void heroSpikesBegin(Fixture fixA, Fixture fixB) {
        if(fixA.getFilterData().categoryBits==MyGame.HERO_BIT)
            ((Hero) fixA.getUserData()).hit();
        else
            ((Hero) fixB.getUserData()).hit();
    }

    private void heroPlateBegin(Fixture fixA, Fixture fixB) {
        if(fixA.getFilterData().categoryBits==MyGame.PRESSING_PLATE_BIT)
            ((PressingPlate) fixA.getUserData()).incIsPressed();
        else
            ((PressingPlate) fixB.getUserData()).incIsPressed();
    }

    private void boulderPlateBegin(Fixture fixA, Fixture fixB) {
        if(fixA.getFilterData().categoryBits==MyGame.PRESSING_PLATE_BIT)
            ((PressingPlate) fixA.getUserData()).incIsPressed();
        else
            ((PressingPlate) fixB.getUserData()).incIsPressed();
    }

    private void heroMegaPlateBegin(Fixture fixA, Fixture fixB) {
        if(fixA.getFilterData().categoryBits==MyGame.MEGA_PRESSING_PLATE_BIT){
            ((MegaPressingPlate) fixA.getUserData()).incIsPressed();
            System.out.println(((MegaPressingPlate) fixA.getUserData()).isPressed());
        }
        else {
            ((MegaPressingPlate) fixB.getUserData()).incIsPressed();
            System.out.println(((MegaPressingPlate) fixB.getUserData()).isPressed());
        }
    }

    private void bolderMegaPlateBegin(Fixture fixA, Fixture fixB) {
        if(fixA.getFilterData().categoryBits==MyGame.MEGA_PRESSING_PLATE_BIT){
            ((MegaPressingPlate) fixA.getUserData()).incIsPressed();
            System.out.println(((MegaPressingPlate) fixA.getUserData()).isPressed());
        }
        else {
            ((MegaPressingPlate) fixB.getUserData()).incIsPressed();
            System.out.println(((MegaPressingPlate) fixB.getUserData()).isPressed());
        }
    }

    private void heroPitfallBegin(Fixture fixA, Fixture fixB) {
        if(fixA.getFilterData().categoryBits==MyGame.HERO_BIT) {
            if(!((Hero) fixA.getUserData()).getIsInPlatform())
                ((Hero) fixA.getUserData()).fall();
            ((Hero) fixA.getUserData()).isInPitfall=true;
        }
        else {
            if(!((Hero) fixB.getUserData()).getIsInPlatform())
                ((Hero) fixB.getUserData()).fall();
            ((Hero) fixB.getUserData()).isInPitfall=true;
        }
    }

    private void heroPlatformBegin(Fixture fixA, Fixture fixB) {
        if(fixA.getFilterData().categoryBits==MyGame.HERO_BIT){
            ((Hero) fixA.getUserData()).setIsInPlatform(true);
            ((Hero) fixA.getUserData()).setPlatformId(((MovingPlatform) fixB.getUserData()).getId());
            ((Hero) fixA.getUserData()).isInPitfall=true;
        }
        else {
            ((Hero) fixB.getUserData()).setIsInPlatform(true);
            ((Hero) fixB.getUserData()).setPlatformId(((MovingPlatform) fixA.getUserData()).getId());
            ((Hero) fixB.getUserData()).isInPitfall=true;
        }
    }

    private void heroChestBegin(Fixture fixA, Fixture fixB) {
        if(fixA.getFilterData().categoryBits==MyGame.CHEST_BIT)
            ((Hero) fixB.getUserData()).setOpenedChestId(((Chest) fixA.getUserData()).getId());
        else
            ((Hero) fixA.getUserData()).setOpenedChestId(((Chest) fixB.getUserData()).getId());
    }

    private void bombSmashBegin(Fixture fixA, Fixture fixB) {
        if(fixA.getFilterData().categoryBits==MyGame.SMASH_BIT)
            ((SmashableRock) fixA.getUserData()).destroy();
        else
            ((SmashableRock) fixB.getUserData()).destroy();
    }

    private void signalHeroBegin(Fixture fixA, Fixture fixB) {
        if(fixA.getFilterData().categoryBits==MyGame.SIGN_BIT)
            ((Hero) fixB.getUserData()).setOpenedSignId(((Sign) fixA.getUserData()).getId());
        else
            ((Hero) fixA.getUserData()).setOpenedSignId(((Sign) fixB.getUserData()).getId());
    }

    private void impactVerify(String surfaceName,FixtureVector fixVec)  {
        if(fixVec.getFixA().getUserData()==surfaceName || fixVec.getFixB().getUserData() == surfaceName){
            Fixture surface = fixVec.getFixA().getUserData()==surfaceName ? fixVec.getFixA() : fixVec.getFixB();
            Fixture object = surface == fixVec.getFixA() ? fixVec.getFixB() : fixVec.getFixA();

            if(object.getUserData() instanceof StaticTileObject){
                if(object.getUserData() instanceof Door) {
                    Gdx.app.log("door touched" , surfaceName);
                    ((Door) object.getUserData()).warp();
                }
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        FixtureVector fixVec = new FixtureVector(contact.getFixtureA(), contact.getFixtureB());
        int cDef = fixVec.getFixA().getFilterData().categoryBits | fixVec.getFixB().getFilterData().categoryBits;
        dinamicEndContactVerify1(cDef, fixVec);
        dinamicEndContactVerify2(cDef, fixVec);
    }

    private void dinamicEndContactVerify1(int cDef,FixtureVector fixVec) {
        switch(cDef) {
            case MyGame.HERO_BIT | MyGame.PRESSING_PLATE_BIT:
                heroPlateEnd(fixVec.getFixA(), fixVec.getFixB());
                break;
            case MyGame.BOULDER_BIT | MyGame.PRESSING_PLATE_BIT:
                boulderPlateEnd(fixVec.getFixA(), fixVec.getFixB());
                break;
            case MyGame.HERO_BIT | MyGame.MEGA_PRESSING_PLATE_BIT:
                heroMegaPlateEnd(fixVec.getFixA(), fixVec.getFixB());
                break;
        }
    }

    private void dinamicEndContactVerify2(int cDef,FixtureVector fixVec) {
        switch(cDef) {
            case MyGame.BOULDER_BIT | MyGame.MEGA_PRESSING_PLATE_BIT:
                boulderMegaPlateEnd(fixVec.getFixA(), fixVec.getFixB());
                break;
            case MyGame.MOVING_PLATFORM_BIT | MyGame.HERO_BIT:
                heroMovingPlatformEnd(fixVec.getFixA(), fixVec.getFixB());
                break;
            case MyGame.PITFALL_BIT | MyGame.HERO_BIT:
                pitfallHeroEnd(fixVec.getFixA(), fixVec.getFixB());
                break;
        }
    }

    private void boulderPlateEnd(Fixture fixA, Fixture fixB) {
        if(fixA.getFilterData().categoryBits==MyGame.PRESSING_PLATE_BIT) {
            if (((PressingPlate) fixA.getUserData()).isPressAndHold() && ((PressingPlate) fixA.getUserData()).isPressed()>0)
                ((PressingPlate) fixA.getUserData()).decIsPressed();
        }
        else{
            if (((PressingPlate) fixB.getUserData()).isPressAndHold() && ((PressingPlate) fixB.getUserData()).isPressed()>0)
                ((PressingPlate) fixB.getUserData()).decIsPressed();
        }
    }

    private void heroMegaPlateEnd(Fixture fixA, Fixture fixB) {
        if(fixA.getFilterData().categoryBits==MyGame.MEGA_PRESSING_PLATE_BIT) {
            ((MegaPressingPlate) fixA.getUserData()).decIsPressed();
            System.out.println(((MegaPressingPlate) fixA.getUserData()).isPressed());
        }
        else{
            ((MegaPressingPlate) fixB.getUserData()).decIsPressed();
            System.out.println(((MegaPressingPlate) fixB.getUserData()).isPressed());
        }
    }

    private void boulderMegaPlateEnd(Fixture fixA, Fixture fixB) {
        if(fixA.getFilterData().categoryBits==MyGame.MEGA_PRESSING_PLATE_BIT) {
            ((MegaPressingPlate) fixA.getUserData()).decIsPressed();
            System.out.println(((MegaPressingPlate) fixA.getUserData()).isPressed());
        }
        else{
            ((MegaPressingPlate) fixB.getUserData()).decIsPressed();
            System.out.println(((MegaPressingPlate) fixB.getUserData()).isPressed());
        }
    }

    private void heroMovingPlatformEnd(Fixture fixA, Fixture fixB) {
        if(fixA.getFilterData().categoryBits==MyGame.HERO_BIT){
            if(((Hero) fixA.getUserData()).isInPitfall){
                ((Hero) fixA.getUserData()).fall();
            }
            ((Hero) fixA.getUserData()).setIsInPlatform(false);
            ((Hero) fixA.getUserData()).setPlatformId(-1);
        }
        else{
            if(((Hero) fixA.getUserData()).isInPitfall){
                ((Hero) fixB.getUserData()).fall();
            }
            ((Hero) fixB.getUserData()).setIsInPlatform(false);
            ((Hero) fixB.getUserData()).setPlatformId(-1);
        }
    }

    private void pitfallHeroEnd(Fixture fixA, Fixture fixB) {
        if(fixA.getFilterData().categoryBits==MyGame.HERO_BIT) {
            ((Hero) fixA.getUserData()).isInPitfall=false;
        }
        else {
            ((Hero) fixB.getUserData()).isInPitfall=false;
        }
    }

    private void heroPlateEnd(Fixture fixA, Fixture fixB) {
        if(fixA.getFilterData().categoryBits==MyGame.PRESSING_PLATE_BIT) {
            if (((PressingPlate) fixA.getUserData()).isPressAndHold() && ((PressingPlate) fixA.getUserData()).isPressed()>0)
                ((PressingPlate) fixA.getUserData()).decIsPressed();
        }
        else{
            if (((PressingPlate) fixB.getUserData()).isPressAndHold() && ((PressingPlate) fixB.getUserData()).isPressed()>0)
                ((PressingPlate) fixB.getUserData()).decIsPressed();
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
