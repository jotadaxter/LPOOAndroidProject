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
import com.mygdx.game.View.Scenes.TextLog;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Utilizador on 10-05-2017.
 */

public class FreeWorld extends GameScreen {
    //Hero Info
    public static final int POSX = 8+11*16;
    public static final int POSY = 8+2*16;
    public static final int TUTORIAL_PX = 8+9*16+8;
    public static final int TUTORIAL_PY = 8+11*16;
    //Boulder Position
    public static final int BOULDER1_X = 8+16*8;
    public static final int BOULDER1_Y = 8+16*54;
    public static final int BOULDER2_X = 8+16*10;
    public static final int BOULDER2_Y = 8+16*54;
    //PressingPlate Position
    public static final int PP1_X = 8+16*8;
    public static final int PP1_Y = 8+16*35;
    public static final int PP2_X = 8+16*12;
    public static final int PP2_Y = 8+16*35;
    public static final int PP3_X = 8+16*8;
    public static final int PP3_Y = 8+16*32;
    public static final int PP4_X = 8+16*12;
    public static final int PP4_Y = 8+16*32;
    //WayBlockers
    public static final int WB1_X = 8+16*23;
    public static final int WB1_Y = 8+16*50;
    public static final int WB2_X = 8+16*24;
    public static final int WB2_Y = 8+16*50;
    public static final int WB3_X = 8+16*25;
    public static final int WB3_Y = 8+16*50;
    public static final int TUTORIAL_DOOR_ID=1;
    public static final int DUNGEON1_DOOR_ID=3;

    private PressingEvent pressingEvent;

    public FreeWorld(MyGame game) {
        super(game,new Vector2(POSX, POSY));
        type= FreeWorld.class;
        warpEvents.add(new WarpEvent(TUTORIAL_DOOR_ID,Door.class, new GameState(new DemoScreen(game,new Vector2(247+8,35)))));
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
        music.setVolume(MyGame.MUSIC_VOLUME);
    }

    @Override
    public void objectLoad() {
        signLoad();
        chestsLoad();
        pressingPlatesLoad();
        boulderLoad();
        itemsLoad();
    }

    private void itemsLoad() {
        ArrayList<Vector2> positions = game.fileReader.ReadFile("rupee_locations","free_world");
        for(int i=0; i<positions.size();i++){
            int val;
            Random random= new Random();
            int rand=random.nextInt(100) + 1;
            if(rand>0 && rand<50)
                val=MyGame.GREEN_RUPEE;
            else if(rand>=50 && rand<75)
                val=MyGame.BLUE_RUPEE;
            else val=MyGame.RED_RUPEE;
            ItemDef idef=new ItemDef(new Vector2(positions.get(i).x,positions.get(i).y), Jewel.class);
            idef.setVal(val);
            spawnItem(idef);
        }
    }

    private void signLoad() {
        Sign sign1= new Sign(this, new Vector2(29*16+8,35*16-8));
        sign1.addSignId(0);
        signs.add(sign1);
        TextLog log1 = new TextLog(game, this);
        log1.setText(game.fileReader.getSignText("sign5"));
        log1.setId(0);
        textlogs.add(log1);

        Sign sign2= new Sign(this, new Vector2(9*16+8,6*16-8));
        sign2.addSignId(1);
        signs.add(sign2);
        TextLog log2 = new TextLog(game, this);
        log2.setText(game.fileReader.getSignText("sign0"));
        log2.setId(1);
        textlogs.add(log2);
    }

    private void chestsLoad() {
        ArrayList<Vector2> positions = game.fileReader.ReadFile("chest_locations","free_world");
        for(int i=0; i<positions.size();i++){
            Chest c= new Chest(this, positions.get(i));
            c.addChestId(i);
            chests.add(c);
        }
    }

    private void pressingPlatesLoad() {
        PressingPlate pp1= new PressingPlate(this,new Vector2( PP1_X, PP1_Y));
        PressingPlate pp2= new PressingPlate(this,new Vector2( PP2_X, PP2_Y));
        PressingPlate pp3= new PressingPlate(this,new Vector2( PP3_X, PP3_Y));
        PressingPlate pp4= new PressingPlate(this,new Vector2( PP4_X, PP4_Y));
        ArrayList<PressingPlate> dungeon1_plates= new ArrayList<PressingPlate>();
        dungeon1_plates.add(pp2);//ordem: 2-4-3
        dungeon1_plates.add(pp4);
        dungeon1_plates.add(pp3);
        pps.add(pp1);
        pps.add(pp4);
        pps.add(pp2);
        pps.add(pp3);
        pressingEvent= new PressingEvent(dungeon1_plates, this, 0);
    }

    private void boulderLoad() {
        WayBlocker wb =  new WayBlocker(this,new Vector2(WB1_X,WB1_Y),0);
        wayblocks.add(wb);
        WayBlocker wb2 =  new WayBlocker(this,new Vector2(WB2_X,WB2_Y),0);
        wayblocks.add(wb2);
        WayBlocker wb3 =  new WayBlocker(this,new Vector2(WB3_X,WB3_Y),0);
        wayblocks.add(wb3);
        Boulder boulder1= new Boulder(this,new Vector2(9*16+8, 31*16+8));
        boulders.add(boulder1);
        Boulder boulder2= new Boulder(this,new Vector2(11*16+8, 31*16+8));
        boulders.add(boulder2);
    }

   @Override
    public void handleSpawningItems() {
        if(!itemsToSpawn.isEmpty()){
            ItemDef idef= itemsToSpawn.poll();
            if(idef.type == Jewel.class) {
                items.add(new Jewel(idef.val,this, idef.position));
            }
        }
    }

    @Override
    public void objectsUpdate(float dt) {
        for(Boulder boulder : boulders)
            boulder.update();
        for(PressingPlate pp : pps)
            pp.update(dt);
        for(MegaPressingPlate mpp : mpps)
            mpp.update(dt);
        for(WayBlocker wb : wayblocks){
            wb.update();
            if(!d1blck)
                wb.destroy();
        }
        for(Chest chest : chests)
            chest.update(dt);
        for(Sign sign :signs)
            sign.update();
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
