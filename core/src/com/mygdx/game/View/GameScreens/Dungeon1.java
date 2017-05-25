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
import com.mygdx.game.Model.Entitys.InteractiveObjects.Chest;
import com.mygdx.game.Model.Entitys.Items.ItemDef;
import com.mygdx.game.Model.Entitys.Items.SpecialItem;
import com.mygdx.game.Model.Events.PressingEvent;
import com.mygdx.game.MyGame;

/**
 * Created by Utilizador on 20-05-2017.
 */

public class Dungeon1 extends GameScreen{
    //Hero Info
    public static final int POSX = 8+3*16;
    public static final int POSY = 8+3*16;

    public static final int MOV_PLAT_X = 16+16*45;
    public static final int MOV_PLAT_Y = 16+16*33;

    public static final int P1_X = 45*16;
    public static final int P1_Y = 16*23;

    public static final int P2_X = 3*16;
    public static final int P2_Y = 16*3;

    public static final int P3_X = 3*16;
    public static final int P3_Y = 16*11;

    public static final int P4_X = 17*16;
    public static final int P4_Y = 16*11;

    private PressingEvent megaPressingEvent;

    public Dungeon1(MyGame game) {
        super(game, 8+12*16, 8+21*16);
        Gdx.input.setInputProcessor(controller.getStage());
    }

    @Override
    protected void musicDefine() {
        music = Gdx.audio.newMusic(Gdx.files.internal("Music/dungeon1_music.mp3"));
        music.setLooping(true);
    }

    @Override
    public void objectLoad() {

        MovingPlatform m1= new MovingPlatform(this, P1_X,P1_Y);
        m1.setId(0);
        mps.add(m1);

        D1TopDoor topDoor1= new D1TopDoor(this,6*16+8,34*16+24,1);
        topDoors.add(topDoor1);

        //Smashable Rocks
        smashRockLoad();

        //Fireground
        fireGroundLoad();

        //Chests
        chestsLoad();

        //Items
        itemsLoad();

        //PressingPlates
        pressingPlatesLoad();
    }

    private void pressingPlatesLoad() {
        MegaPressingPlate megapp= new MegaPressingPlate(this,60,150);
        mpps.add(megapp);
        megaPressingEvent= new PressingEvent(mpps,this);
        Boulder boulder3= new Boulder(this,60, 100);
        boulders.add(boulder3);
        Boulder boulder4= new Boulder(this,30, 100);
        boulders.add(boulder4);
    }

    private void itemsLoad() {
        spawnItem(new ItemDef(new Vector2(8+46*16,8+42*16), SpecialItem.class));
    }

    private void chestsLoad() {
        Chest c1= new Chest(this, 8+3*16,8+13*16);
        c1.addChestId(0);
        chests.add(c1);
        Chest c2= new Chest(this, 8+21*16,8+3*16);
        c1.addChestId(1);
        chests.add(c2);

        Chest c3= new Chest(this, 8+46*16,8+3*16);
        c1.addChestId(2);
        chests.add(c3);

        Chest c4= new Chest(this, 8+47*16,8+31*16);
        c1.addChestId(3);
        chests.add(c4);

        Chest c5= new Chest(this, 8+20*16,8+23*16);
        c1.addChestId(4);
        chests.add(c5);
        Chest c6= new Chest(this, 8+12*16,8+31*16);
        c1.addChestId(5);
        chests.add(c6);
        Chest c7= new Chest(this, 8+5*16,8+43*16);
        c1.addChestId(6);
        chests.add(c7);
        Chest c8= new Chest(this, 8+45*16,8+39*16);
        c1.addChestId(7);
        chests.add(c8);
        Chest c9= new Chest(this, 8+46*16,8+39*16);
        c1.addChestId(8);
        chests.add(c9);
        Chest c10= new Chest(this, 8+47*16,8+39*16);
        c1.addChestId(9);
        chests.add(c10);
        Chest c11= new Chest(this, 8+19*16,8+43*16);
        c1.addChestId(10);
        chests.add(c11);
        Chest c12= new Chest(this, 8+37*16,8+43*16);
        c1.addChestId(11);
        chests.add(c12);
    }

    private void smashRockLoad() {
        for(int i =0;i<21;i++){
            for(int j =0;j<6;j++) {
                SmashableRock sm1 = new SmashableRock(this, 8 + 2 * 16 + i * 16, 8 + 6 * 16+j*16);
                smashRocks.add(sm1);
            }
        }
        for(int i =0;i<14;i++){
            for(int j =0;j<4;j++) {
                SmashableRock sm1 = new SmashableRock(this, 8 + 6 * 16 + i * 16, 8 + 2 * 16+j*16);
                smashRocks.add(sm1);
            }
        }
        for(int i =0;i<18;i++){
            for(int j =0;j<3;j++) {
                SmashableRock sm1 = new SmashableRock(this, 8 + 5 * 16 + i * 16, 8 + 12 * 16+j*16);
                smashRocks.add(sm1);
            }
        }
        for(int i =0;i<3;i++) {
            SmashableRock sm1 = new SmashableRock(this, 8 + 20 * 16 + i * 16, 8 + 5 * 16);
            smashRocks.add(sm1);
        }
        for(int i =0;i<3;i++){
            for(int j =0;j<3;j++) {
                SmashableRock sm1 = new SmashableRock(this, 8 + 43 * 16 + i * 16, 8 + 16 * 16+j*16);
                smashRocks.add(sm1);
            }
        }

    }

    private void fireGroundLoad() {
        for(int i =0; i<8;i++){
            FireGround fg1= new FireGround(this,456+i*16, 232);
            fireGrounds.add(fg1);
        }
        for(int i =0; i<6;i++){
            FireGround fg1= new FireGround(this,456+i*16, 232-3*16);
            fireGrounds.add(fg1);
            FireGround fg2= new FireGround(this,456+8*16, 232-16-i*16);
            fireGrounds.add(fg2);
        }

        FireGround fg1= new FireGround(this,456+5*16, 232-4*16);
        fireGrounds.add(fg1);

        for(int i =0; i<5;i++){
            FireGround fg2= new FireGround(this,456+5*16-i*16, 232-5*16-i*16);
            fireGrounds.add(fg2);
        }
        for(int i =0; i<3;i++){
            FireGround fg2= new FireGround(this,456+i*16+16, 232-i*16-160);
            fireGrounds.add(fg2);
        }
        for(int i =0; i<17;i++){
            FireGround fg2= new FireGround(this,456+3*16+16+16*i, 232-16-176);
            fireGrounds.add(fg2);
        }
        for(int i =0; i<3;i++){
            FireGround fg2= new FireGround(this,456+3*16+16*17, 232-176+i*16);
            fireGrounds.add(fg2);
        }
        for(int i =0; i<3;i++){
            FireGround fg2= new FireGround(this,456+2*16+16*17, 232-176+3*16+i*16);
            fireGrounds.add(fg2);
        }
        for(int i =0; i<6;i++){
            FireGround fg2= new FireGround(this,456+2*16+16*16, 232-176+6*16+i*16);
            fireGrounds.add(fg2);
        }
        for(int i =0; i<5;i++){
            FireGround fg2= new FireGround(this,456-2*16+16*16, 232-176+6*16+5*16-i*16);
            fireGrounds.add(fg2);
        }
        for(int i =0; i<2;i++){
            FireGround fg2= new FireGround(this,456+13*16, 232-4*16-16*i);
            fireGrounds.add(fg2);
        }
        for(int i =0; i<2;i++){
            FireGround fg2= new FireGround(this,456+12*16, 232-5*16-16*i);
            fireGrounds.add(fg2);
        }
        for(int i =0; i<4;i++){
            FireGround fg2= new FireGround(this,456+11*16, 232-6*16-16*i);
            fireGrounds.add(fg2);
        }
        for(int i =0; i<6;i++){
            FireGround fg2= new FireGround(this,456+10*16-16*i, 232-5*16-16*4);
            fireGrounds.add(fg2);
        }
        for(int i =0; i<2;i++){
            FireGround fg2= new FireGround(this,456+10*16-16*4+16*i, 232-5*16-16*3+16*i);
            fireGrounds.add(fg2);
        }
        for(int i =0; i<5;i++){
            FireGround fg2= new FireGround(this,8+44*16,  8+16*3+16*i);
            fireGrounds.add(fg2);
        }
        for(int i =0; i<4;i++){
            FireGround fg2= new FireGround(this,8+43*16,  8+16*3+16*i);
            fireGrounds.add(fg2);
        }
        for(int i =0; i<4;i++){
            FireGround fg2= new FireGround(this,8+42*16,  8+16*3+16*i);
            fireGrounds.add(fg2);
        }
        FireGround fg2= new FireGround(this,8+3*16, 8+16);
        fireGrounds.add(fg2);
    }

    @Override
    public void objectsUpdate(float dt) {
        for(MovingPlatform m : mps)
            m.update(dt);
        for(SmashableRock sm : smashRocks)
            sm.update(dt);
        for(FireGround fg : fireGrounds)
            fg.update(dt);
        for(Chest chest : chests)
            chest.update(dt);
        megaPressingEvent.update(dt);
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
    public void objectsDraw() {
        for(MovingPlatform m : mps)
            m.draw(game.batch);
        for(SmashableRock sm : smashRocks)
            sm.draw(game.batch);
        for(FireGround fg : fireGrounds)
            fg.draw(game.batch);
        for(Chest chest : chests)
            chest.draw(game.batch);
        for(MegaPressingPlate mpp : mpps)
            mpp.draw(game.batch);
    }

    @Override
    public String getMapName() {
        return "first_dungeon.tmx";
    }
}
