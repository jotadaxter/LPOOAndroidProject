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
import com.mygdx.game.Model.Entitys.DinamicObjects.WayBlocker;
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

    public static final int MOV_PLAT1_X = 45*16;
    public static final int MOV_PLAT1_Y = 16*23;
    public static final int MOV_PLAT2_X = 13*16;
    public static final int MOV_PLAT2_Y = 41*16;
    public static final int MOV_PLAT3_X = 30*16;
    public static final int MOV_PLAT3_Y = 41*16;

    private PressingEvent megaPressingEvent;

    public Dungeon1(MyGame game) {
        super(game, POSX, POSY);
        Gdx.input.setInputProcessor(controller.getStage());
        d1blck=true;
    }

    @Override
    protected void musicDefine() {
        music = Gdx.audio.newMusic(Gdx.files.internal("Music/dungeon1_music.mp3"));
        music.setLooping(true);
        music.setVolume(1f);
    }

    @Override
    public void objectLoad() {
        D1TopDoor topDoor1= new D1TopDoor(this,6*16+8,34*16+24,1);
        topDoors.add(topDoor1);
        //Moving Platforms
        movingPlatformsLoad();
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
        //Boulders
        boulderLoad();
    }

    private void movingPlatformsLoad() {
        MovingPlatform m1= new MovingPlatform(this, MOV_PLAT1_X,MOV_PLAT1_Y, 0);
        m1.setId(0);
        mps.add(m1);
        MovingPlatform m2= new MovingPlatform(this, MOV_PLAT2_X,MOV_PLAT2_Y, 1);
        m1.setId(1);
        mps.add(m2);
        MovingPlatform m3= new MovingPlatform(this, MOV_PLAT3_X,MOV_PLAT3_Y, 2);
        m1.setId(2);
        mps.add(m3);
    }

    private void boulderLoad() {
        Boulder boulder1= new Boulder(this,8+10*16, 8+22*16);
        boulders.add(boulder1);
        Boulder boulder2= new Boulder(this,8+11*16, 8+22*16);
        boulders.add(boulder2);
        Boulder boulder3= new Boulder(this,8+17*16, 8+22*16);
        boulders.add(boulder3);
        WayBlocker wb =  new WayBlocker(this,8+6*16,8+33*16,1);
        wayblocks.add(wb);
    }

    private void pressingPlatesLoad() {
        MegaPressingPlate megapp1= new MegaPressingPlate(this,4*16+8,23*16+8);
        mpps.add(megapp1);
        MegaPressingPlate megapp2= new MegaPressingPlate(this,18*16+8,29*16+8);
        mpps.add(megapp2);
        megaPressingEvent= new PressingEvent(mpps,this);
    }

    private void itemsLoad() {
        spawnItem(new ItemDef(new Vector2(8+46*16,8+42*16), SpecialItem.class));
    }

    private void chestsLoad() {
        int x=8+3*16;
        int y= 8+13*16;
        Chest c1= new Chest(this, x,y);
        c1.addChestId(0);
        chests.add(c1);
        x=8+21*16;
        y=8+3*16;
        Chest c2= new Chest(this, 8+21*16,8+3*16);
        c2.addChestId(1);
        chests.add(c2);
        x=8+46*16;
        y=8+3*16;
        Chest c3= new Chest(this, 8+46*16,8+3*16);
        c3.addChestId(2);
        chests.add(c3);
        x=8+47*16;
        y=8+31*16;
        Chest c4= new Chest(this, 8+47*16,8+31*16);
        c4.addChestId(3);
        chests.add(c4);
        x= 8+20*16;
        y=8+23*16;
        Chest c5= new Chest(this, 8+20*16,8+23*16);
        c5.addChestId(4);
        chests.add(c5);
        x=8+12*16;
        y=8+31*16;
        Chest c6= new Chest(this, 8+12*16,8+31*16);
        c6.addChestId(5);
        chests.add(c6);
        x=8+5*16;
        y=8+43*16;
        Chest c7= new Chest(this, 8+5*16,8+43*16);
        c7.addChestId(6);
        chests.add(c7);
        x=8+45*16;
        y=8+39*16;
        Chest c8= new Chest(this, 8+45*16,8+39*16);
        c8.addChestId(7);
        chests.add(c8);
        x=8+46*16;
        y=8+39*16;
        Chest c9= new Chest(this, 8+46*16,8+39*16);
        c9.addChestId(8);
        chests.add(c9);
        x=8+47*16;
        y=8+39*16;
        Chest c10= new Chest(this, 8+47*16,8+39*16);
        c10.addChestId(9);
        chests.add(c10);
        x=8+19*16;
        y=8+43*16;
        Chest c11= new Chest(this, 8+19*16,8+43*16);
        c11.addChestId(10);
        chests.add(c11);
        x=8+37*16;
        y=8+43*16;
        Chest c12= new Chest(this, 8+37*16,8+43*16);
        c12.addChestId(11);
        chests.add(c12);
    }

    private void smashRockLoad() {
        for(int i =0;i<21;i++){
            for(int j =0;j<6;j++) {
                int x=8 + 2 * 16 + i * 16;
                int y= 8 + 6 * 16+j*16;
                SmashableRock sm1 = new SmashableRock(this, x,y);
                smashRocks.add(sm1);
            }
        }
        for(int i =0;i<14;i++){
            for(int j =0;j<4;j++) {
                int x=8 + 6 * 16 + i * 16;
                int y= 8 + 2 * 16+j*16;
                SmashableRock sm1 = new SmashableRock(this, x, y);
                smashRocks.add(sm1);
            }
        }
        for(int i =0;i<18;i++){
            for(int j =0;j<3;j++) {
                int x= 8 + 5 * 16 + i * 16;
                int y=8 + 12 * 16+j*16;
                SmashableRock sm1 = new SmashableRock(this,x, y);
                smashRocks.add(sm1);
            }
        }
        for(int i =0;i<3;i++) {
            int x=8 + 20 * 16 + i * 16;
            int y=8 + 5 * 16;
            SmashableRock sm1 = new SmashableRock(this, x, y);
            smashRocks.add(sm1);
        }
        for(int i =0;i<3;i++){
            for(int j =0;j<3;j++) {
                int x=8 + 43 * 16 + i * 16;
                int y=8 + 16 * 16+j*16;
                SmashableRock sm1 = new SmashableRock(this, x, y);
                smashRocks.add(sm1);
            }
        }

    }

    private void fireGroundLoad() {
        for(int i =0; i<8;i++){
            int x=456+i*16;
            int y=232;
            FireGround fg1= new FireGround(this,456+i*16, 232);
            fireGrounds.add(fg1);
            System.out.println(x + " " + y);
        }
        for(int i =0; i<6;i++){
            int x=456+i*16;
            int y= 232-3*16;
            FireGround fg1= new FireGround(this,456+i*16, 232-3*16);
            fireGrounds.add(fg1);
            System.out.println(x + " " + y);
            int x1=456+8*16;
            int y1=232-16-i*16;
            FireGround fg2= new FireGround(this,456+8*16, 232-16-i*16);
            fireGrounds.add(fg2);
            System.out.println(x1 + " " + y1);
        }
        int xx=456+5*16;
        int yy=232-4*16;
        FireGround fg1= new FireGround(this,456+5*16, 232-4*16);
        fireGrounds.add(fg1);
        System.out.println(xx + " " + yy);

        for(int i =0; i<5;i++){
            int x=456+5*16-i*16;
            int y=232-5*16-i*16;
            FireGround fg2= new FireGround(this,456+5*16-i*16, 232-5*16-i*16);
            fireGrounds.add(fg2);
            System.out.println(x + " " + y);
        }
        for(int i =0; i<3;i++){
            int x=456+i*16+16;
            int y=232-i*16-160;
            FireGround fg2= new FireGround(this,456+i*16+16, 232-i*16-160);
            fireGrounds.add(fg2);
            System.out.println(x + " " + y);
        }
        for(int i =0; i<17;i++){
            int x=456+3*16+16+16*i;
            int y=232-16-176;
            FireGround fg2= new FireGround(this,456+3*16+16+16*i, 232-16-176);
            fireGrounds.add(fg2);
            System.out.println(x + " " + y);
        }
        for(int i =0; i<3;i++){
            int x=456+3*16+16*17;
            int y=232-176+i*16;
            FireGround fg2= new FireGround(this,456+3*16+16*17, 232-176+i*16);
            fireGrounds.add(fg2);
            System.out.println(x + " " + y);
        }
        for(int i =0; i<3;i++){
            int x=456+2*16+16*17;
            int y=232-176+3*16+i*16;
            FireGround fg2= new FireGround(this,456+2*16+16*17, 232-176+3*16+i*16);
            fireGrounds.add(fg2);
            System.out.println(x + " " + y);
        }
        for(int i =0; i<6;i++){
            int x=456+2*16+16*16;
            int y=232-176+6*16+i*16;
            FireGround fg2= new FireGround(this,456+2*16+16*16, 232-176+6*16+i*16);
            fireGrounds.add(fg2);
            System.out.println(x + " " + y);
        }
        for(int i =0; i<5;i++){
            int x=456-2*16+16*16;
            int y=232-176+6*16+5*16-i*16;
            FireGround fg2= new FireGround(this,456-2*16+16*16, 232-176+6*16+5*16-i*16);
            fireGrounds.add(fg2);
            System.out.println(x + " " + y);
        }
        for(int i =0; i<2;i++){
            int x=456+13*16;
            int y=232-4*16-16*i;
            FireGround fg2= new FireGround(this,456+13*16, 232-4*16-16*i);
            fireGrounds.add(fg2);
            System.out.println(x + " " + y);
        }
        for(int i =0; i<2;i++){
            int x=456+12*16;
            int y=232-5*16-16*i;
            FireGround fg2= new FireGround(this,456+12*16, 232-5*16-16*i);
            fireGrounds.add(fg2);
            System.out.println(x + " " + y);
        }
        for(int i =0; i<4;i++){
            int x=456+11*16;
            int y=232-6*16-16*i;
            FireGround fg2= new FireGround(this,456+11*16, 232-6*16-16*i);
            fireGrounds.add(fg2);
            System.out.println(x + " " + y);
        }
        for(int i =0; i<6;i++){
            int x=456+10*16-16*i;
            int y= 232-5*16-16*4;
            FireGround fg2= new FireGround(this,456+10*16-16*i, 232-5*16-16*4);
            fireGrounds.add(fg2);
            System.out.println(x + " " + y);
        }
        for(int i =0; i<2;i++){
            int x=456+10*16-16*4+16*i;
            int y=232-5*16-16*3+16*i;
            FireGround fg2= new FireGround(this,456+10*16-16*4+16*i, 232-5*16-16*3+16*i);
            fireGrounds.add(fg2);
            System.out.println(x + " " + y);
        }
        for(int i =0; i<5;i++){
            int x=8+44*16;
            int y=8+16*3+16*i;
            FireGround fg2= new FireGround(this,8+44*16,  8+16*3+16*i);
            fireGrounds.add(fg2);
            System.out.println(x + " " + y);
        }
        for(int i =0; i<4;i++){
            int x=8+43*16;
            int y=8+16*3+16*i;
            FireGround fg2= new FireGround(this,8+43*16,  8+16*3+16*i);
            fireGrounds.add(fg2);
            System.out.println(x + " " + y);
        }
        for(int i =0; i<4;i++){
            int x=8+42*16;
            int y=8+16*3+16*i;
            FireGround fg2= new FireGround(this,8+42*16,  8+16*3+16*i);
            fireGrounds.add(fg2);
            System.out.println(x + " " + y);
        }
        int xxx=8+3*16;
        int yyy=8+16;
        FireGround fg2= new FireGround(this,8+3*16, 8+16);
        fireGrounds.add(fg2);
        System.out.println(xxx + " " + yyy);
    }

    @Override
    public void objectsUpdate(float dt) {
        for(MegaPressingPlate mpp : mpps)
            mpp.update(dt);
        for(MovingPlatform m : mps)
            m.update(dt);
        for(SmashableRock sm : smashRocks)
            sm.update(dt);
        for(FireGround fg : fireGrounds)
            fg.update(dt);
        for(WayBlocker wb : wayblocks){
            wb.update(dt);
            if(!d1blck)
                wb.destroy();
        }
        for(Boulder boulder : boulders)
            boulder.update(dt);
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
        for(WayBlocker wb : wayblocks)
            wb.draw(game.batch);
        for(MegaPressingPlate mpp : mpps)
            mpp.draw(game.batch);
        for(SmashableRock sm : smashRocks)
            sm.draw(game.batch);
        for(FireGround fg : fireGrounds)
            fg.draw(game.batch);
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
