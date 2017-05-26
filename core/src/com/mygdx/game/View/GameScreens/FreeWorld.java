package com.mygdx.game.View.GameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Controller.Entitys.TileObjects.Door;
import com.mygdx.game.Model.Entitys.DinamicObjects.Boulder;
import com.mygdx.game.Model.Entitys.DinamicObjects.FireGround;
import com.mygdx.game.Model.Entitys.DinamicObjects.MegaPressingPlate;
import com.mygdx.game.Model.Entitys.DinamicObjects.PressingPlate;
import com.mygdx.game.Model.Entitys.DinamicObjects.WayBlocker;
import com.mygdx.game.Model.Entitys.InteractiveObjects.Chest;
import com.mygdx.game.Model.Entitys.InteractiveObjects.Sign;
import com.mygdx.game.Model.Entitys.Items.Heart;
import com.mygdx.game.Model.Entitys.Items.ItemDef;
import com.mygdx.game.Model.Entitys.Items.Jewel;
import com.mygdx.game.Model.Entitys.Items.SpecialItem;
import com.mygdx.game.Model.Entitys.Weapons.Bomb;
import com.mygdx.game.Model.Events.PressingEvent;
import com.mygdx.game.Model.States.GameState;
import com.mygdx.game.Model.Events.WarpEvent;
import com.mygdx.game.MyGame;

import java.util.ArrayList;

/**
 * Created by Utilizador on 10-05-2017.
 */

public class FreeWorld extends GameScreen {
    //Hero Info
    public static final int POSX = 30;
    public static final int POSY = 30;
    //Boulder Position
    public static final int BOULDER1_X = 170;
    public static final int BOULDER1_Y = 870;
    public static final int BOULDER2_X = 140;
    public static final int BOULDER2_Y = 870;
    //PressingPlate Position
    public static final int PP1_X = 140;
    public static final int PP1_Y = 570;
    public static final int PP2_X = 200;
    public static final int PP2_Y = 570;
    public static final int PP3_X = 140;
    public static final int PP3_Y = 520;
    public static final int PP4_X = 200;
    public static final int PP4_Y = 520;
    //WayBlockers
    public static final int WB1_X = 8+16*23;//(8+26*8)*2-7*8;
    public static final int WB1_Y = 8+16*50;//(8+51*8)*2-3*8;
    public static final int WB2_X = 8+16*24;
    public static final int WB2_Y = 8+16*50;
    public static final int WB3_X = 8+16*25;
    public static final int WB3_Y = 8+16*50;
    public static final int TUTORIAL_DOOR_ID=1;
    public static final int DUNGEON1_DOOR_ID=3;

    private PressingEvent pressingEvent;

    public FreeWorld(MyGame game) {
        super(game, POSX, POSY);
        type= FreeWorld.class;
        warpEvents.add(new WarpEvent(TUTORIAL_DOOR_ID,Door.class, new GameState(new DemoScreen(game))));
        warpEvents.add(new WarpEvent(DUNGEON1_DOOR_ID,Door.class, new GameState(new Dungeon1(game))));
        Gdx.input.setInputProcessor(controller.getStage());
        d1blck=true;
    }

    @Override
    public String getMapName() {
        return "free_world.tmx";
    }

    @Override
    protected void musicDefine() {
        music = Gdx.audio.newMusic(Gdx.files.internal("Music/hyrule_field_music.mp3"));
        music.setLooping(true);
        music.setVolume(5f);
    }

    @Override
    public void objectLoad() {
        Sign sign1= new Sign(this,29*16+8,35*16-8, sign1Text());
        sign1.addSignId(0);
        signs.add(sign1);
        //Chests
        chestsLoad();
        //PressingPlates
        pressingPlatesLoad();
        //Boulders and WayBlockers
        boulderLoad();
    }

    private void chestsLoad() {
        int x=8+32*16;
        int y= 8+2*16;

        Chest c1= new Chest(this, 8+32*16,8+2*16);
        c1.addChestId(0);
        chests.add(c1);
        x=8+29*16;
        y=8+19*16;
        Chest c2= new Chest(this, 8+29*16,8+19*16);
        c2.addChestId(1);
        chests.add(c2);
        x=8+30*16;
        y=8+19*16;
        Chest c3= new Chest(this, 8+30*16,8+19*16);
        c3.addChestId(2);
        chests.add(c3);
        x=8+26*16;
        y=8+30*16;
        Chest c4= new Chest(this, 8+26*16,8+30*16);
        c4.addChestId(3);
        chests.add(c4);
        x=8+22*16;
        y=8+52*16;
        Chest c5= new Chest(this, 8+22*16,8+52*16);
        c5.addChestId(4);
        chests.add(c5);
        x=8+26*16;
        y=8+52*16;
        Chest c6= new Chest(this, 8+26*16,8+52*16);
        c6.addChestId(5);
        chests.add(c6);
        x=8+1*16;
        y=8+25*16;
        Chest c7= new Chest(this, 8+1*16,8+25*16);
        c7.addChestId(6);
        chests.add(c7);
    }

    private void pressingPlatesLoad() {
        PressingPlate pp1= new PressingPlate(this, PP1_X, PP1_Y);
        PressingPlate pp2= new PressingPlate(this, PP2_X, PP2_Y);
        PressingPlate pp3= new PressingPlate(this, PP3_X, PP3_Y);
        PressingPlate pp4= new PressingPlate(this, PP4_X, PP4_Y);
        ArrayList<PressingPlate> dungeon1_plates= new ArrayList<PressingPlate>();
        dungeon1_plates.add(pp1);//ordem: 1-4-2-3
        dungeon1_plates.add(pp4);
        dungeon1_plates.add(pp2);
        pps.add(pp1);
        pps.add(pp4);
        pps.add(pp2);
        pps.add(pp3);
        pressingEvent= new PressingEvent(dungeon1_plates, this, 0);
    }

    private void boulderLoad() {
        WayBlocker wb =  new WayBlocker(this,WB1_X,WB1_Y,0);
        wayblocks.add(wb);
        WayBlocker wb2 =  new WayBlocker(this,WB2_X,WB2_Y,0);
        wayblocks.add(wb2);
        WayBlocker wb3 =  new WayBlocker(this,WB3_X,WB3_Y,0);
        wayblocks.add(wb3);
        Boulder boulder1= new Boulder(this,BOULDER1_X, BOULDER1_Y);
        boulders.add(boulder1);
        Boulder boulder2= new Boulder(this,BOULDER2_X, BOULDER2_Y);
        boulders.add(boulder2);
    }

    private String sign1Text() {
        String text=   "XXX     XXX\n"+
                "X1X      X2X\n"+
                "XXX     XXX\n"+
                "      423  \n"+
                "XXX     XXX\n"+
                "X3 X     X4 X\n"+
                "XXX     XXX\n";
        return text;
    }

    @Override
    public void handleSpawningItems() {
        if(!itemsToSpawn.isEmpty()){
            ItemDef idef= itemsToSpawn.poll();
            if(idef.type == SpecialItem.class) {
                items.add(new SpecialItem(this, idef.position.x, idef.position.y));
            }
        }
    }

    @Override
    public void objectsUpdate(float dt) {
        for(Boulder boulder : boulders)
            boulder.update(dt);
        for(PressingPlate pp : pps)
            pp.update(dt, this);
        for(MegaPressingPlate mpp : mpps)
            mpp.update(dt);
        for(WayBlocker wb : wayblocks){
            wb.update(dt);
            if(!d1blck)
                wb.destroy();
        }
        for(Chest chest : chests)
            chest.update(dt);
        for(Sign sign :signs)
            sign.update(dt);
        pressingEvent.update(dt);
    }

    @Override
    public void objectsDraw() {
        for(WayBlocker wb : wayblocks)
            wb.draw(game.batch);
        for(PressingPlate pp : pps)
            pp.draw(game.batch);
        for(Boulder boulder : boulders)
            boulder.draw(game.batch);
        for(Chest chest : chests)
            chest.draw(game.batch);
        for(Sign sign :signs)
            sign.draw(game.batch);
    }

    public void resetBoulders() {
        for(int i=0;i<2;i++)
            boulders.get(i).getBoulderBody().getBody().setTransform(0.5f+8+2*i ,0.5f+54, 0);
    }

}
