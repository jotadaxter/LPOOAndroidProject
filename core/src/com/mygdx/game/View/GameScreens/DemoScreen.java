package com.mygdx.game.View.GameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Model.Entitys.DinamicObjects.Boulder;
import com.mygdx.game.Model.Entitys.DinamicObjects.PressingPlate;
import com.mygdx.game.Model.Entitys.DinamicObjects.Spikes;
import com.mygdx.game.Model.Entitys.Items.Heart;
import com.mygdx.game.Model.Entitys.Items.ItemDef;
import com.mygdx.game.Model.Entitys.Items.Jewel;
import com.mygdx.game.Model.Entitys.Items.Key;
import com.mygdx.game.MyGame;

/**
 * Created by Utilizador on 08-05-2017.
 */

public class DemoScreen extends GameScreen {
    //Hero Position
    public static final int POSX = 247;
    public static final int POSY = 35;
    //Boulder Position
    public static final int BOULDER_X = 200;
    public static final int BOULDER_Y = 200;
    //PressingPlate Position
    public static final int PP_X = 200;
    public static final int PP_Y = 250;

    public static final int DOOR_ID=2;

    public DemoScreen(MyGame game) {
        super(game, POSX, POSY);
        type= DemoScreen.class;
        Gdx.input.setInputProcessor(controller.getStage());

    }

    @Override
    protected void musicDefine() {
        music = Gdx.audio.newMusic(Gdx.files.internal("Music/tutorial_music.mp3"));
        music.setLooping(true);
    }

    @Override
    public void objectLoad() {
        Boulder boulder= new Boulder(this,BOULDER_X, BOULDER_Y);
        boulders.add(boulder);
        Spikes spike= new Spikes(this);
        spikes.add(spike);
        PressingPlate pp= new PressingPlate(this, PP_X, PP_Y);
        pps.add(pp);

        //Items
        spawnItem(new ItemDef(new Vector2(150,150), Jewel.class));
        spawnItem(new ItemDef(new Vector2(200,150), Heart.class));
        spawnItem(new ItemDef(new Vector2(150,200), Key.class));
    }

    @Override
    public String getMapName() {
        return "level1.tmx";
    }

    @Override
    public void objectsUpdate(float dt) {
        for(Boulder boulder : boulders)
            boulder.update(dt);
        for(Spikes spike : spikes)
            spike.update(dt);
        for(PressingPlate pp : pps)
            pp.update(dt,this);
    }

    @Override
    public void handleSpawningItems() {
        if(!itemsToSpawn.isEmpty()){
            ItemDef idef= itemsToSpawn.poll();
            if(idef.type == Jewel.class){
                items.add(new Jewel(MyGame.BLUE_RUPEE, this, idef.position.x, idef.position.y));
            }
            else if(idef.type== Heart.class){
                items.add(new Heart(this, idef.position.x, idef.position.y));
            }
            else if(idef.type== Key.class){
                items.add(new Key(this, idef.position.x, idef.position.y));
            }
        }
    }

    @Override
    public void objectsDraw() {
        for(Spikes spike : spikes)
            spike.draw(game.batch);
        for(PressingPlate pp : pps)
            pp.draw(game.batch);
        for(Boulder boulder : boulders)
            boulder.draw(game.batch);

    }
}
