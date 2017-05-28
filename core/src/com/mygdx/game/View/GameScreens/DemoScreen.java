package com.mygdx.game.View.GameScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Model.Entitys.DinamicObjects.Boulder;
import com.mygdx.game.Model.Entitys.DinamicObjects.PressingPlate;
import com.mygdx.game.Model.Entitys.DinamicObjects.SmashableRock;
import com.mygdx.game.Model.Entitys.DinamicObjects.Spikes;
import com.mygdx.game.Model.Entitys.InteractiveObjects.Sign;
import com.mygdx.game.Model.Entitys.Items.Heart;
import com.mygdx.game.Model.Entitys.Items.ItemDef;
import com.mygdx.game.Model.Entitys.Items.Jewel;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.Scenes.TextLog;

/**
 * Created by Utilizador on 08-05-2017.
 */

public class DemoScreen extends GameScreen {
    //Hero Position
    //public static final int POSX = 247;
    //public static final int POSY = 35;
    //public static final int POSX = 8+9*16+8;
    //public static final int POSY = 8+11*16;
    //Boulder Position
    public static final int BOULDER_X = 8+11*16;
    public static final int BOULDER_Y = 8+8*16;
    //PressingPlate Position
    public static final int PP_X = 8+14*16;
    public static final int PP_Y = 8+8*16;

    public static final int DOOR_ID=2;

    public DemoScreen(MyGame game, float px, float py) {
        super(game, px, py);
        type= DemoScreen.class;
        Gdx.input.setInputProcessor(controller.getStage());

    }

    @Override
    protected void musicDefine() {
        music = Gdx.audio.newMusic(Gdx.files.internal("Music/tutorial_music.mp3"));
        music.setLooping(true);
        music.setVolume(5f);
    }

    @Override
    public void objectLoad() {
        Boulder boulder= new Boulder(this,new Vector2(BOULDER_X, BOULDER_Y));
        boulders.add(boulder);
        PressingPlate pp= new PressingPlate(this, new Vector2(PP_X, PP_Y));
        pps.add(pp);
        SmashableRock sm = new SmashableRock(this, new Vector2(8+5*16,8+8*16));
        smashRocks.add(sm);
        signLoad();
        //Items
        spawnItem(new ItemDef(new Vector2(8+14*16,8+15*16), Jewel.class));
        spawnItem(new ItemDef(new Vector2(8+5*16,8+15*16), Heart.class));
    }

    private void signLoad() {
        Sign sign1= new Sign(this,5*16+8,13*16+8);
        sign1.addSignId(0);
        signs.add(sign1);
        TextLog log1 = new TextLog(game, this, game.fileReader.getSignText("sign1"), 0);
        textlogs.add(log1);

        Sign sign2= new Sign(this,14*16+8,13*16+8);
        sign2.addSignId(1);
        signs.add(sign2);
        TextLog log2 = new TextLog(game, this, game.fileReader.getSignText("sign2"), 1);
        textlogs.add(log2);

        Sign sign3= new Sign(this,5*16+8,6*16+8);
        sign3.addSignId(2);
        signs.add(sign3);
        TextLog log3 = new TextLog(game, this, game.fileReader.getSignText("sign3"), 2);
        textlogs.add(log3);

        Sign sign4= new Sign(this,13*16+8,6*16+8);
        sign4.addSignId(3);
        signs.add(sign4);
        TextLog log4 = new TextLog(game, this, game.fileReader.getSignText("sign4"), 3);
        textlogs.add(log4);
    }

    @Override
    public String getMapName() {
        return "level1.tmx";
    }

    @Override
    public void objectsUpdate(float dt) {
        for(Boulder boulder : boulders)
            boulder.update(dt);
        for(SmashableRock sm : smashRocks)
            sm.update(dt);
        for(PressingPlate pp : pps)
            pp.update(dt);
        for(Sign sign :signs)
            sign.update(dt);
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
        }
    }

    @Override
    public void objectsDraw() {
        for(SmashableRock sm : smashRocks)
            sm.draw(game.batch);
        for(PressingPlate pp : pps)
            pp.draw(game.batch);
        for(Boulder boulder : boulders)
            boulder.draw(game.batch);
        for(Sign sign :signs)
            sign.draw(game.batch);

    }
}
