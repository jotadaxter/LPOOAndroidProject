package com.mygdx.game.View.GameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Controller.Entitys.DinamicObjects.SmashableRockBody;
import com.mygdx.game.Controller.Entitys.TileObjects.D1TopDoor;
import com.mygdx.game.Model.Entitys.DinamicObjects.Boulder;
import com.mygdx.game.Model.Entitys.DinamicObjects.FireGround;
import com.mygdx.game.Model.Entitys.DinamicObjects.MegaPressingPlate;
import com.mygdx.game.Model.Entitys.DinamicObjects.MovingPlatform;
import com.mygdx.game.Model.Entitys.DinamicObjects.SmashableRock;
import com.mygdx.game.Model.Entitys.DinamicObjects.Spikes;
import com.mygdx.game.Model.Entitys.DinamicObjects.WayBlocker;
import com.mygdx.game.Model.Entitys.InteractiveObjects.Chest;
import com.mygdx.game.Model.Entitys.Items.Heart;
import com.mygdx.game.Model.Entitys.Items.ItemDef;
import com.mygdx.game.Model.Entitys.Items.Jewel;
import com.mygdx.game.Model.Entitys.Items.SpecialItem;
import com.mygdx.game.Model.Events.PressingEvent;
import com.mygdx.game.MyGame;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Utilizador on 20-05-2017.
 */

public class Dungeon1 extends GameScreen{
    //Hero Info
    public static final int POSX = 8+3*16;
    public static final int POSY = 8+3*16;

    public static final int MOV_PLAT1_X = 45*16;
    public static final int MOV_PLAT1_Y = 16*23;
    public static final int MOV_PLAT2_X = 13*16;
    public static final int MOV_PLAT2_Y = 41*16;
    public static final int MOV_PLAT3_X = 30*16;
    public static final int MOV_PLAT3_Y = 41*16;

    private PressingEvent megaPressingEvent;

    public Dungeon1(MyGame game) {
        super(game, new Vector2(44*16+8, 20*16+8));
        Gdx.input.setInputProcessor(controller.getStage());
        d1blck=true;
    }

    @Override
    protected void musicDefine() {
        music = Gdx.audio.newMusic(Gdx.files.internal("Music/dungeon1_music.mp3"));
        music.setLooping(true);
        music.setVolume(MyGame.MUSIC_VOLUME);
    }

    @Override
    public void objectLoad() {
        D1TopDoor topDoor1= new D1TopDoor(this,new Vector2(6*16+8,34*16+24),1);
        topDoors.add(topDoor1);
        movingPlatformsLoad();
        smashRockLoad();
        fireGroundLoad();
        spikesLoad();
        chestsLoad();
        itemsLoad();
        pressingPlatesLoad();
        boulderLoad();
    }

    private void spikesLoad() {
        ArrayList<Vector2> positions = game.fileReader.ReadFile("spikes_locations","dungeon1");
        for(int i=0; i<positions.size();i++){
            Spikes sp = new Spikes(this, positions.get(i));
            spikes.add(sp);
        }
    }

    private void movingPlatformsLoad() {
        MovingPlatform m1= new MovingPlatform(this, new Vector2(MOV_PLAT1_X,MOV_PLAT1_Y), 0);
        m1.setId(0);
        mps.add(m1);
        MovingPlatform m2= new MovingPlatform(this, new Vector2(MOV_PLAT2_X,MOV_PLAT2_Y), 1);

        m1.setId(1);
        mps.add(m2);
        MovingPlatform m3= new MovingPlatform(this, new Vector2(MOV_PLAT3_X,MOV_PLAT3_Y), 2);
        m1.setId(2);
        mps.add(m3);
    }

    private void boulderLoad() {
        Boulder boulder1= new Boulder(this,new Vector2(8+10*16, 8+22*16));
        boulders.add(boulder1);
        Boulder boulder2= new Boulder(this,new Vector2(8+11*16, 8+22*16));
        boulders.add(boulder2);
        Boulder boulder3= new Boulder(this,new Vector2(8+17*16, 8+22*16));
        boulders.add(boulder3);
        WayBlocker wb =  new WayBlocker(this,new Vector2(8+6*16,8+33*16),1);
        wayblocks.add(wb);
    }

    private void pressingPlatesLoad() {
        MegaPressingPlate megapp1= new MegaPressingPlate(this,new Vector2(4*16+8,23*16+8));
        mpps.add(megapp1);
        MegaPressingPlate megapp2= new MegaPressingPlate(this,new Vector2(18*16+8,29*16+8));
        mpps.add(megapp2);
        megaPressingEvent= new PressingEvent(mpps,this);
    }

    private void itemsLoad() {
        spawnItem(new ItemDef(new Vector2(8+46*16,8+42*16), SpecialItem.class));
        ArrayList<Vector2> positions = game.fileReader.ReadFile("heart_locations","dungeon1");
        for(int i=0; i<positions.size();i++){
            spawnItem(new ItemDef(new Vector2(positions.get(i).x,positions.get(i).y), Heart.class));
        }
    }

    private void chestsLoad() {
        ArrayList<Vector2> positions = game.fileReader.ReadFile("chest_locations","dungeon1");
        for(int i=0; i<positions.size();i++){
            Chest c= new Chest(this, positions.get(i));
            c.addChestId(i);
            chests.add(c);
        }
    }

    private void smashRockLoad() {
        ArrayList<Vector2> positions = game.fileReader.ReadFile("rock_locations","dungeon1");
        for(Vector2 vec :positions){
            SmashableRock sm = new SmashableRock(this, vec);
            smashRocks.add(sm);
        }
    }

    private void fireGroundLoad() {
        ArrayList<Vector2> positions = game.fileReader.ReadFile("fireground_locations","dungeon1");
        for(Vector2 vec :positions){
            FireGround fg= new FireGround(this,vec);
            fireGrounds.add(fg);
        }
    }

    @Override
    public void objectsUpdate(float dt) {
        for(MegaPressingPlate mpp : mpps)
            mpp.update(dt);
        for(MovingPlatform m : mps)
            m.update(dt);
        for(SmashableRock sm : smashRocks)
            sm.update(dt);
        for(Spikes spike : spikes)
            spike.update();
        for(FireGround fg : fireGrounds)
            fg.update(dt);
        for(WayBlocker wb : wayblocks){
            wb.update();
            if(!d1blck)
                wb.destroy();
        }
        for(Boulder boulder : boulders)
            boulder.update();
        for(Chest chest : chests)
            chest.update(dt);
        megaPressingEvent.update(dt);
    }

    @Override
    public void handleSpawningItems() {
        if(!itemsToSpawn.isEmpty()){
            ItemDef idef= itemsToSpawn.poll();
            if(idef.type == SpecialItem.class) {
                items.add(new SpecialItem(this, idef.position));
            }
            else if(idef.type == Heart.class) {
                items.add(new Heart(this, idef.position));
            }
        }
    }

    @Override
    public void objectsDraw() {
        for(WayBlocker wb : wayblocks)
            wb.draw(game.batch);
        for(MegaPressingPlate mpp : mpps)
            mpp.draw(game.batch);
        for(SmashableRock sm : smashRocks)
            sm.draw(game.batch);
        for(FireGround fg : fireGrounds)
            fg.draw(game.batch);
        for(Spikes spike : spikes)
            spike.draw(game.batch);
        for(Chest chest : chests)
            chest.draw(game.batch);
        for(Boulder boulder : boulders)
            boulder.draw(game.batch);
        for(MovingPlatform m : mps)
            m.draw(game.batch);
    }

    @Override
    public String getMapName() {
        return "first_dungeon.tmx";
    }
}
