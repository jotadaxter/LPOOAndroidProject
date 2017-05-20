package com.mygdx.game.View.GameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Controller.Entitys.TileObjects.Door;
import com.mygdx.game.Model.Entitys.DinamicObjects.Boulder;
import com.mygdx.game.Model.Entitys.DinamicObjects.FireGround;
import com.mygdx.game.Model.Entitys.DinamicObjects.PressingPlate;
import com.mygdx.game.Model.Entitys.DinamicObjects.WayBlocker;
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
    public static final int DOOR_ID=1;

    private PressingEvent pressingEvent;
    private ArrayList<PressingPlate> dungeon1_plates;
    public boolean d1blck;


    public FreeWorld(MyGame game) {
        super(game, POSX, POSY);
        type= FreeWorld.class;
        warpEvents.add(new WarpEvent(DOOR_ID,Door.class, new GameState(new DemoScreen(game))));
        Gdx.input.setInputProcessor(controller.getStage());
        d1blck=true;

    }

    @Override
    public String getMapName() {
        return "free_world.tmx";
    }

    @Override
    public void objectLoad() {
        WayBlocker wb =  new WayBlocker(this,WB1_X,WB1_Y,"dungeon1_block");
        wayblocks.add(wb);
        WayBlocker wb2 =  new WayBlocker(this,WB2_X,WB2_Y,"dungeon1_block");
        wayblocks.add(wb2);
        WayBlocker wb3 =  new WayBlocker(this,WB3_X,WB3_Y,"dungeon1_block");
        wayblocks.add(wb3);
        Boulder boulder1= new Boulder(this,BOULDER1_X, BOULDER1_Y);
        boulders.add(boulder1);
        Boulder boulder2= new Boulder(this,BOULDER2_X, BOULDER2_Y);
        boulders.add(boulder2);

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
        pressingEvent= new PressingEvent(dungeon1_plates, this);

        fireGround= new FireGround(this, 80, 20);

        //Items
        spawnItem(new ItemDef(new Vector2(20,80), SpecialItem.class));
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
        for(WayBlocker wb : wayblocks)
            wb.update(dt);
        for(Boulder boulder : boulders)
            boulder.update(dt);
        int cnt=0;
        for(PressingPlate pp : pps) {
            pp.update(dt, this);
            /*if (pp.isPressed() > 0) {
                cnt++;
            }*/
        }
        for(WayBlocker wb : wayblocks){
            wb.update(dt);
            if(!d1blck)
                wb.destroy();
        }
        pressingEvent.update(dt);
        fireGround.update(dt);

    }

    @Override
    public void objectsDraw() {
        for(WayBlocker wb : wayblocks)
            wb.draw(game.batch);
        for(PressingPlate pp : pps)
            pp.draw(game.batch);
        for(Boulder boulder : boulders)
            boulder.draw(game.batch);
        fireGround.draw(game.batch);
    }

}
