package com.mygdx.game.View.GameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.mygdx.game.Controller.Entitys.DinamicObjects.SmashableRockBody;
import com.mygdx.game.Controller.Entitys.TileObjects.D1TopDoor;
import com.mygdx.game.Model.Entitys.DinamicObjects.FireGround;
import com.mygdx.game.Model.Entitys.DinamicObjects.MovingPlatform;
import com.mygdx.game.Model.Entitys.DinamicObjects.SmashableRock;
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

    public Dungeon1(MyGame game) {
        super(game, POSX, POSY);
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

        D1TopDoor topDoor1= new D1TopDoor(this,24*16+24,13*16-8,0);
        topDoors.add(topDoor1);
        D1TopDoor topDoor2= new D1TopDoor(this,6*16+8,34*16+24,1);
        topDoors.add(topDoor2);

        //Smashable Rocks
        SmashableRock sm1= new SmashableRock(this,30,30);
        smashRocks.add(sm1);

        //Fireground
        fireGroundLoad();

    }

    private void fireGroundLoad() {
        for(int i =0; i<8;i++){
            FireGround fg1= new FireGround(this,456+i*16, 232);
            fireGrounds.add(fg1);
        }

    }

    @Override
    public void objectsUpdate(float dt) {
        for(MovingPlatform m : mps)
            m.update(dt);
        for(SmashableRock sm : smashRocks)
            sm.update(dt);
        for(FireGround fg : fireGrounds)
            fg.update(dt);
    }

    @Override
    public void handleSpawningItems() {

    }

    @Override
    public void objectsDraw() {
        for(MovingPlatform m : mps)
            m.draw(game.batch);
        for(SmashableRock sm : smashRocks)
            sm.draw(game.batch);
        for(FireGround fg : fireGrounds)
            fg.draw(game.batch);
    }

    @Override
    public String getMapName() {
        return "first_dungeon.tmx";
    }
}
