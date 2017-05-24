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
                    ((Hero) fixA.getUserData()).hit();
                else
                    ((Hero) fixB.getUserData()).hit();
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
            case MyGame.HERO_BIT | MyGame.MEGA_PRESSING_PLATE_BIT:
                if(fixA.getFilterData().categoryBits==MyGame.MEGA_PRESSING_PLATE_BIT){
                    ((MegaPressingPlate) fixA.getUserData()).incIsPressed();
                    System.out.println(((MegaPressingPlate) fixA.getUserData()).isPressed());
                }
                else {
                    ((MegaPressingPlate) fixB.getUserData()).incIsPressed();
                    System.out.println(((MegaPressingPlate) fixB.getUserData()).isPressed());
                }
                break;
            case MyGame.BOULDER_BIT | MyGame.MEGA_PRESSING_PLATE_BIT:
                if(fixA.getFilterData().categoryBits==MyGame.MEGA_PRESSING_PLATE_BIT){
                    ((MegaPressingPlate) fixA.getUserData()).incIsPressed();
                    System.out.println(((MegaPressingPlate) fixA.getUserData()).isPressed());
                }
                else {
                    ((MegaPressingPlate) fixB.getUserData()).incIsPressed();
                    System.out.println(((MegaPressingPlate) fixB.getUserData()).isPressed());
                }
                break;
            case MyGame.HERO_BIT | MyGame.BOMB_BIT:
                if(fixA.getFilterData().categoryBits==MyGame.BOMB_BIT){
                   if(((Bomb)fixA.getUserData()).getState()== Bomb.State.BOOM) {
                       System.out.println(((Bomb)fixA.getUserData()));
                       System.out.println("hit");
                       ((Hero) fixB.getUserData()).hit();
                   }
                }
                else {
                    if(((Bomb)fixB.getUserData()).getState()== Bomb.State.BOOM) {
                        System.out.println("hit");
                        ((Hero) fixA.getUserData()).hit();
                    }
                }
                break;
            case MyGame.PITFALL_BIT | MyGame.HERO_BIT:
                if(fixA.getFilterData().categoryBits==MyGame.HERO_BIT) {
                   if(!((Hero) fixA.getUserData()).getIsInPlatform())
                       ((Hero) fixA.getUserData()).fall();
                    ((Hero) fixA.getUserData()).isInPitfall=true;
                    System.out.println("is in platform: "+((Hero) fixA.getUserData()).getIsInPlatform());
                    System.out.println("is in pitfall: "+ ((Hero) fixA.getUserData()).isInPitfall);
                }
                else {
                    if(!((Hero) fixB.getUserData()).getIsInPlatform())
                        ((Hero) fixB.getUserData()).fall();
                    ((Hero) fixB.getUserData()).isInPitfall=true;
                    System.out.println("is in platform: "+((Hero) fixB.getUserData()).getIsInPlatform());
                    System.out.println("is in pitfall: "+ ((Hero) fixB.getUserData()).isInPitfall);
                }
                break;
            case MyGame.MOVING_PLATFORM_BIT | MyGame.HERO_BIT:
                if(fixA.getFilterData().categoryBits==MyGame.HERO_BIT){
                    ((Hero) fixA.getUserData()).setIsInPlatform(true);
                    ((Hero) fixA.getUserData()).setPlatformId(((MovingPlatform) fixB.getUserData()).getId());
                    ((Hero) fixA.getUserData()).isInPitfall=true;
                    System.out.println("is in platform: "+((Hero) fixA.getUserData()).getIsInPlatform());
                    System.out.println("is in pitfall: "+ ((Hero) fixA.getUserData()).isInPitfall);
                }
                else {
                    ((Hero) fixB.getUserData()).setIsInPlatform(true);
                    ((Hero) fixB.getUserData()).setPlatformId(((MovingPlatform) fixA.getUserData()).getId());
                    ((Hero) fixB.getUserData()).isInPitfall=true;
                    System.out.println("is in platform: "+((Hero) fixB.getUserData()).getIsInPlatform());
                    System.out.println("is in pitfall: "+ ((Hero) fixB.getUserData()).isInPitfall);
                }
                break;
                case MyGame.HERO_BIT | MyGame.CHEST_BIT:
                if(fixA.getFilterData().categoryBits==MyGame.CHEST_BIT)
                    ((Hero) fixB.getUserData()).setOpenedChestId(((Chest) fixA.getUserData()).getId());
                else
                    ((Hero) fixA.getUserData()).setOpenedChestId(((Chest) fixB.getUserData()).getId());
                break;
            case MyGame.HERO_BIT | MyGame.SIGN_BIT:
                if(fixA.getFilterData().categoryBits==MyGame.SIGN_BIT)
                    ((Hero) fixB.getUserData()).setOpenedSignId(((Sign) fixA.getUserData()).getId());
                else
                    ((Hero) fixA.getUserData()).setOpenedSignId(((Sign) fixB.getUserData()).getId());
                break;

        }

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
            case MyGame.HERO_BIT | MyGame.MEGA_PRESSING_PLATE_BIT:
                if(fixA.getFilterData().categoryBits==MyGame.MEGA_PRESSING_PLATE_BIT) {
                    ((MegaPressingPlate) fixA.getUserData()).decIsPressed();
                    System.out.println(((MegaPressingPlate) fixA.getUserData()).isPressed());
                }
                else{
                    ((MegaPressingPlate) fixB.getUserData()).decIsPressed();
                    System.out.println(((MegaPressingPlate) fixB.getUserData()).isPressed());
                }
                break;
            case MyGame.BOULDER_BIT | MyGame.MEGA_PRESSING_PLATE_BIT:
                if(fixA.getFilterData().categoryBits==MyGame.MEGA_PRESSING_PLATE_BIT) {
                    ((MegaPressingPlate) fixA.getUserData()).decIsPressed();
                    System.out.println(((MegaPressingPlate) fixA.getUserData()).isPressed());
                }
                else{
                    ((MegaPressingPlate) fixB.getUserData()).decIsPressed();
                    System.out.println(((MegaPressingPlate) fixB.getUserData()).isPressed());
                }
                break;
            case MyGame.MOVING_PLATFORM_BIT | MyGame.HERO_BIT:
                if(fixA.getFilterData().categoryBits==MyGame.HERO_BIT){
                    if(((Hero) fixA.getUserData()).isInPitfall){
                        ((Hero) fixA.getUserData()).fall();
                    }
                   ((Hero) fixA.getUserData()).setIsInPlatform(false);
                    ((Hero) fixA.getUserData()).setPlatformId(-1);
                    System.out.println("is in platform: "+((Hero) fixA.getUserData()).getIsInPlatform());
                    System.out.println("is in pitfall: "+ ((Hero) fixA.getUserData()).isInPitfall);
                }
                else{
                    if(((Hero) fixA.getUserData()).isInPitfall){
                        ((Hero) fixB.getUserData()).fall();
                    }
                    ((Hero) fixB.getUserData()).setIsInPlatform(false);
                    ((Hero) fixB.getUserData()).setPlatformId(-1);
                    System.out.println("is in platform: "+((Hero) fixB.getUserData()).getIsInPlatform());
                    System.out.println("is in pitfall: "+ ((Hero) fixB.getUserData()).isInPitfall);
                }
                break;
            case MyGame.PITFALL_BIT | MyGame.HERO_BIT:
                if(fixA.getFilterData().categoryBits==MyGame.HERO_BIT) {
                    ((Hero) fixA.getUserData()).isInPitfall=false;
                    System.out.println("is in platform: "+((Hero) fixA.getUserData()).getIsInPlatform());
                    System.out.println("is in pitfall: "+ ((Hero) fixA.getUserData()).isInPitfall);
                }
                else {
                    ((Hero) fixB.getUserData()).isInPitfall=false;
                    System.out.println("is in platform: "+((Hero) fixB.getUserData()).getIsInPlatform());
                    System.out.println("is in pitfall: "+ ((Hero) fixB.getUserData()).isInPitfall);
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
