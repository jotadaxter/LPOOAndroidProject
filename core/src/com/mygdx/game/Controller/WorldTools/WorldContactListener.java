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
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        impactVerify("upContact", fixA, fixB);
        impactVerify("downContact", fixA, fixB);
        impactVerify("leftContact", fixA, fixB);
        impactVerify("leftContact", fixA, fixB);

        dinamicBeginContactVerify1(cDef, fixA, fixB);
        dinamicBeginContactVerify2(cDef, fixA, fixB);
        dinamicBeginContactVerify3(cDef, fixA, fixB);
    }

    private void dinamicBeginContactVerify1(int cDef, Fixture fixA, Fixture fixB) {
        switch(cDef) {
            case MyGame.ITEM_BIT | MyGame.HERO_BIT:
                heroItemBegin(fixA, fixB);
                break;
            case MyGame.HERO_BIT | MyGame.SPIKES_BIT:
                heroSpikesBegin(fixA,fixB);
                break;
            case MyGame.HERO_BIT | MyGame.PRESSING_PLATE_BIT:
                heroPlateBegin(fixA,fixB);
                break;
            case MyGame.BOULDER_BIT | MyGame.PRESSING_PLATE_BIT:
                boulderPlateBegin(fixA,fixB);
                break;
        }
    }

    private void dinamicBeginContactVerify2(int cDef, Fixture fixA, Fixture fixB) {
        switch(cDef){
            case MyGame.BOULDER_BIT | MyGame.MEGA_PRESSING_PLATE_BIT:
                bolderMegaPlateBegin(fixA,fixB);
                break;
            case MyGame.PITFALL_BIT | MyGame.HERO_BIT:
                heroPitfallBegin(fixA,fixB);
                break;
            case MyGame.MOVING_PLATFORM_BIT | MyGame.HERO_BIT:
                heroPlatformBegin(fixA, fixB);
                break;
            case MyGame.HERO_BIT | MyGame.CHEST_BIT:
                heroChestBegin(fixA, fixB);
                break;
        }
    }

    private void dinamicBeginContactVerify3(int cDef, Fixture fixA, Fixture fixB) {
        switch(cDef){
            case MyGame.HERO_BIT | MyGame.MEGA_PRESSING_PLATE_BIT:
                heroMegaPlateBegin(fixA, fixB);
                break;
            case MyGame.HERO_BIT | MyGame.SIGN_BIT:
                signalHeroBegin(fixA,fixB);
                break;
            case  MyGame.BOMB_BIT | MyGame.SMASH_BIT:
                bombSmashBegin(fixA,fixB);
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

    private void impactVerify(String surfaceName, Fixture fixA, Fixture fixB)  {
        if(fixA.getUserData()==surfaceName || fixB.getUserData() == surfaceName){
            Fixture surface = fixA.getUserData()==surfaceName ? fixA : fixB;
            Fixture object = surface == fixA ? fixB : fixA;

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
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        dinamicEndContactVerify1(cDef, fixA, fixB);
        dinamicEndContactVerify2(cDef, fixA, fixB);
    }

    private void dinamicEndContactVerify1(int cDef, Fixture fixA, Fixture fixB) {
        switch(cDef) {
            case MyGame.HERO_BIT | MyGame.PRESSING_PLATE_BIT:
                heroPlateEnd(fixA, fixB);
                break;
            case MyGame.BOULDER_BIT | MyGame.PRESSING_PLATE_BIT:
                boulderPlateEnd(fixA, fixB);
                break;
            case MyGame.HERO_BIT | MyGame.MEGA_PRESSING_PLATE_BIT:
                heroMegaPlateEnd(fixA, fixB);
                break;
        }
    }

    private void dinamicEndContactVerify2(int cDef, Fixture fixA, Fixture fixB) {
        switch(cDef) {
            case MyGame.BOULDER_BIT | MyGame.MEGA_PRESSING_PLATE_BIT:
                boulderMegaPlateEnd(fixA, fixB);
                break;
            case MyGame.MOVING_PLATFORM_BIT | MyGame.HERO_BIT:
                heroMovingPlatformEnd(fixA, fixB);
                break;
            case MyGame.PITFALL_BIT | MyGame.HERO_BIT:
                pitfallHeroEnd(fixA, fixB);
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
