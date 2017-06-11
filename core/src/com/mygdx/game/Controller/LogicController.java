package com.mygdx.game.Controller;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Controller.Entitys.TileObjects.D1TopDoor;
import com.mygdx.game.Controller.Entitys.TileObjects.Door;
import com.mygdx.game.Controller.WorldTools.WorldContactListener;
import com.mygdx.game.Controller.WorldTools.WorldCreator;
import com.mygdx.game.Model.Entitys.DinamicObjects.Boulder;
import com.mygdx.game.Model.Entitys.DinamicObjects.FireGround;
import com.mygdx.game.Model.Entitys.DinamicObjects.MegaPressingPlate;
import com.mygdx.game.Model.Entitys.DinamicObjects.MovingPlatform;
import com.mygdx.game.Model.Entitys.DinamicObjects.PressingPlate;
import com.mygdx.game.Model.Entitys.DinamicObjects.SmashableRock;
import com.mygdx.game.Model.Entitys.DinamicObjects.Spikes;
import com.mygdx.game.Model.Entitys.DinamicObjects.WayBlocker;
import com.mygdx.game.Model.Entitys.Hero.Hero;
import com.mygdx.game.Model.Entitys.InteractiveObjects.Chest;
import com.mygdx.game.Model.Entitys.InteractiveObjects.Sign;
import com.mygdx.game.Model.Entitys.Items.Heart;
import com.mygdx.game.Model.Entitys.Items.Item;
import com.mygdx.game.Model.Entitys.Items.ItemDef;
import com.mygdx.game.Model.Entitys.Items.Jewel;
import com.mygdx.game.Model.Entitys.Items.SpecialItem;
import com.mygdx.game.Model.Events.PressingEvent;
import com.mygdx.game.Model.Events.WarpEvent;
import com.mygdx.game.Model.States.GameState;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.DemoScreen;
import com.mygdx.game.View.GameScreens.Dungeon1;
import com.mygdx.game.View.GameScreens.FreeWorld;
import com.mygdx.game.View.GameScreens.GameScreen;
import com.mygdx.game.View.Scenes.TextLog;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Jotadaxter on 11/06/2017.
 */

public class LogicController {
    //Hero Info
    public static final int DUNGEON1_POSX = 8+3*16;
    public static final int DUNGEON1_POSY = 8+3*16;
    public static final int TUTORIAL_DOOR_ID=1;
    public static final int DUNGEON1_DOOR_ID=3;
    public static final int BOULDER_X = 8+11*16;
    public static final int BOULDER_Y = 8+8*16;
    //PressingPlate Position
    public static final int PP_X = 8+14*16;
    public static final int PP_Y = 8+8*16;
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
    public static final int MOV_PLAT1_X = 45*16;
    public static final int MOV_PLAT1_Y = 16*23;
    public static final int MOV_PLAT2_X = 13*16;
    public static final int MOV_PLAT2_Y = 41*16;
    public static final int MOV_PLAT3_X = 30*16;
    public static final int MOV_PLAT3_Y = 41*16;
    public Class<?> screenType;
    public MyGame game;
    public World world;
    public float accumulator;
    public TmxMapLoader mapLoader;
    public TiledMap tiledMap;
    //Controllers
    public Controller controller;
    //Sprites
    public Hero player;
    public ArrayList<Boulder> boulders;
    public ArrayList<Spikes> spikes;
    public ArrayList<PressingPlate> pps;
    public ArrayList<MegaPressingPlate>mpps;
    public ArrayList<WayBlocker> wayblocks;
    public ArrayList<Chest> chests;
    public ArrayList<MovingPlatform> mps;
    public Array<Item> items;
    public ArrayList<Sign> signs;
    public LinkedBlockingQueue<ItemDef> itemsToSpawn;
    public Array<WarpEvent> warpEvents;
    public Array<D1TopDoor> topDoors;
    public ArrayList<FireGround> fireGrounds;
    public ArrayList<SmashableRock> smashRocks;
    public ArrayList<TextLog> textlogs;
    public boolean d1blck;
    public PressingEvent pressingEvent;
    public PressingEvent megaPressingEvent;

    public LogicController(MyGame game, Vector2 vec,Class<?> type){
        this.screenType=type;
        this.game=game;
        world= new World(new Vector2(0,0), true);
        objectsDefine(vec);
        objectsLoad();
        controller= new Controller(game);
        world.setContactListener(new WorldContactListener());
        if(type== FreeWorld.class){
            warpEvents.add(new WarpEvent(TUTORIAL_DOOR_ID,Door.class, new GameState(new DemoScreen(game,new Vector2(247,35)))));
            warpEvents.add(new WarpEvent(DUNGEON1_DOOR_ID,Door.class, new GameState(new Dungeon1(game,new Vector2(DUNGEON1_POSX,DUNGEON1_POSY)))));
        }
    }

    private void objectsDefine(Vector2 vec) {
        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();
        player=new Hero(this,vec);
        boulders=new ArrayList<Boulder>();
        spikes = new ArrayList<Spikes>();
        pps= new ArrayList<PressingPlate>();
        mpps = new ArrayList<MegaPressingPlate>();
        wayblocks = new ArrayList<WayBlocker>();
        mps=new ArrayList<MovingPlatform>();
        chests= new ArrayList<Chest>();
        signs=new ArrayList<Sign>();
        topDoors= new Array<D1TopDoor>();
        smashRocks= new ArrayList<SmashableRock>();
        fireGrounds= new ArrayList<FireGround>();
        warpEvents= new Array<WarpEvent>();
        textlogs=new ArrayList<TextLog>();
        d1blck=true;
    }

    public void defineMap(String mapName) {
        mapLoader = new TmxMapLoader();
        tiledMap = mapLoader.load(mapName);
        new WorldCreator(this);
    }

    public void update(float dt) {
        handleSpawningItems();
        player.getHeroBody().InputUpdate(controller, dt);
        //takes 1 step in the physics simulation (60 times per second)
        framesPerSecUpdate(dt);
        //Sprites Update
        spritesUpdate(dt);
    }

    private void handleSpawningItems() {
        if(!itemsToSpawn.isEmpty()){
            ItemDef idef= itemsToSpawn.poll();
            if(idef.type == Jewel.class){
                items.add(new Jewel(MyGame.BLUE_RUPEE, this,  new Vector2(idef.position.x, idef.position.y)));
            }
            else if(idef.type== Heart.class){
                items.add(new Heart(this, new Vector2( idef.position.x, idef.position.y)));
            }
            else if(idef.type == SpecialItem.class) {
                items.add(new SpecialItem(this, idef.position));
            }
        }
    }

    private void spritesUpdate(float dt) {
        player.update(dt);
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
        for(D1TopDoor top : topDoors)
            top.update();
        for(MovingPlatform m : mps)
            m.update(dt);
        for(SmashableRock sm : smashRocks)
            sm.update(dt);
        for(Spikes spike : spikes)
            spike.update();
        for(FireGround fg : fireGrounds)
            fg.update(dt);
        if(pressingEvent!=null)
            pressingEvent.update(dt);
        if(megaPressingEvent!=null)
            megaPressingEvent.update(dt);
        //Items Update
        for(Item item : items)
            item.update(dt, player);
        for(TextLog tlog: textlogs) {
            tlog.update();
        }
    }

    private void framesPerSecUpdate(float dt) {
        float frameTime = Math.min(dt, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/60f) {
            world.step(1/60f, 6, 2);
            accumulator -= 1/60f;
        }
    }

    public void resetBoulders() {
        for(int i=0;i<2;i++)
            boulders.get(i).getBoulderBody().getBody().setTransform(0.5f+8+2*i ,0.5f+54, 0);
    }

    private void objectsLoad() {
        if(screenType==FreeWorld.class){
            freeWorldLoad();
        }else if(screenType==Dungeon1.class){
            dungeon1Load();
        }else if(screenType==DemoScreen.class){
            demoScreenLoad();
        }
    }

    public void spawnItem(ItemDef idef){
        itemsToSpawn.add(idef);
    }

    private void freeWorldLoad() {
        FWsignLoad();
        FWchestsLoad();
        FWpressingPlatesLoad();
        FWboulderLoad();
        FWitemsLoad();
    }
    private void FWitemsLoad() {
        ArrayList<Vector2> positions = game.getFileReader().ReadFile("rupee_locations","free_world");
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

    private void FWsignLoad() {
        Sign sign1= new Sign(this, new Vector2(29*16+8,35*16-8));
        sign1.addSignId(0);
        signs.add(sign1);
        TextLog log1 = new TextLog(game);
        log1.setText(game.getFileReader().getSignText("sign5"));
        log1.setId(0);
        textlogs.add(log1);

        Sign sign2= new Sign(this, new Vector2(9*16+8,6*16-8));
        sign2.addSignId(1);
        signs.add(sign2);
        TextLog log2 = new TextLog(game);
        log2.setText(game.getFileReader().getSignText("sign0"));
        log2.setId(1);
        textlogs.add(log2);
    }

    private void FWchestsLoad() {
        ArrayList<Vector2> positions = game.getFileReader().ReadFile("chest_locations","free_world");
        for(int i=0; i<positions.size();i++){
            Chest c= new Chest(this, positions.get(i));
            c.addChestId(i);
            chests.add(c);
        }
    }

    private void FWpressingPlatesLoad() {
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

    private void FWboulderLoad() {
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

    private void dungeon1Load() {
        D1TopDoor topDoor1= new D1TopDoor(this,new Vector2(6*16+8,34*16+24),1);
        topDoors.add(topDoor1);
        D1movingPlatformsLoad();
        D1smashRockLoad();
        D1fireGroundLoad();
        D1spikesLoad();
        D1chestsLoad();
        D1itemsLoad();
        D1pressingPlatesLoad();
        D1boulderLoad();
    }

    private void D1spikesLoad() {
        ArrayList<Vector2> positions = game.getFileReader().ReadFile("spikes_locations","dungeon1");
        for(int i=0; i<positions.size();i++){
            Spikes sp = new Spikes(this, positions.get(i));
            spikes.add(sp);
        }
    }

    private void D1movingPlatformsLoad() {
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

    private void D1boulderLoad() {
        Boulder boulder1= new Boulder(this,new Vector2(8+10*16, 8+22*16));
        boulders.add(boulder1);
        Boulder boulder2= new Boulder(this,new Vector2(8+11*16, 8+22*16));
        boulders.add(boulder2);
        Boulder boulder3= new Boulder(this,new Vector2(8+17*16, 8+22*16));
        boulders.add(boulder3);
        WayBlocker wb =  new WayBlocker(this,new Vector2(8+6*16,8+33*16),1);
        wayblocks.add(wb);
    }

    private void D1pressingPlatesLoad() {
        MegaPressingPlate megapp1= new MegaPressingPlate(this,new Vector2(4*16+8,23*16+8));
        mpps.add(megapp1);
        MegaPressingPlate megapp2= new MegaPressingPlate(this,new Vector2(18*16+8,29*16+8));
        mpps.add(megapp2);
        megaPressingEvent= new PressingEvent(mpps,this);
    }

    private void D1itemsLoad() {
        spawnItem(new ItemDef(new Vector2(8+46*16,8+42*16), SpecialItem.class));
        ArrayList<Vector2> positions = game.getFileReader().ReadFile("heart_locations","dungeon1");
        for(int i=0; i<positions.size();i++){
            spawnItem(new ItemDef(new Vector2(positions.get(i).x,positions.get(i).y), Heart.class));
        }
    }

    private void D1chestsLoad() {
        ArrayList<Vector2> positions = game.getFileReader().ReadFile("chest_locations","dungeon1");
        for(int i=0; i<positions.size();i++){
            Chest c= new Chest(this, positions.get(i));
            c.addChestId(i);
            chests.add(c);
        }
    }

    private void D1smashRockLoad() {
        ArrayList<Vector2> positions = game.getFileReader().ReadFile("rock_locations","dungeon1");
        for(Vector2 vec :positions){
            SmashableRock sm = new SmashableRock(this, vec);
            smashRocks.add(sm);
        }
    }

    private void D1fireGroundLoad() {
        ArrayList<Vector2> positions = game.getFileReader().ReadFile("fireground_locations","dungeon1");
        for(Vector2 vec :positions){
            FireGround fg= new FireGround(this,vec);
            fireGrounds.add(fg);
        }
    }

    private void demoScreenLoad() {
        Boulder boulder= new Boulder(this,new Vector2(BOULDER_X, BOULDER_Y));
        boulders.add(boulder);
        PressingPlate pp= new PressingPlate(this, new Vector2(PP_X, PP_Y));
        pps.add(pp);
        SmashableRock sm = new SmashableRock(this, new Vector2(8+5*16,8+8*16));
        smashRocks.add(sm);
        DSsignLoad();
        //Items
        spawnItem(new ItemDef(new Vector2(8+14*16,8+15*16), Jewel.class));
        spawnItem(new ItemDef(new Vector2(8+5*16,8+15*16), Heart.class));
    }

    private void DSsignLoad() {
        Sign sign1= new Sign(this, new Vector2(5*16+8,13*16+8));
        sign1.addSignId(0);
        signs.add(sign1);
        TextLog log1 = new TextLog(game);
        log1.setText(game.getFileReader().getSignText("sign1"));
        log1.setId(0);
        textlogs.add(log1);

        Sign sign2= new Sign(this, new Vector2(14*16+8,13*16+8));
        sign2.addSignId(1);
        signs.add(sign2);
        TextLog log2 = new TextLog(game);
        log2.setText(game.getFileReader().getSignText("sign2"));
        log2.setId(1);
        textlogs.add(log2);

        Sign sign3= new Sign(this, new Vector2(5*16+8,6*16+8));
        sign3.addSignId(2);
        signs.add(sign3);
        TextLog log3 = new TextLog(game);
        log3.setText(game.getFileReader().getSignText("sign3"));
        log3.setId(2);
        textlogs.add(log3);

        Sign sign4= new Sign(this, new Vector2(13*16+8,6*16+8));
        sign4.addSignId(3);
        signs.add(sign4);
        TextLog log4 = new TextLog(game);
        log4.setText(game.getFileReader().getSignText("sign4"));
        log4.setId(3);
        textlogs.add(log4);
    }


}
