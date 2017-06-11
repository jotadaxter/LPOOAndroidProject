package com.mygdx.game.Controller;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
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
import com.mygdx.game.View.Scenes.TextLog;

import org.mockito.internal.matchers.Null;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Jotadaxter on 11/06/2017.
 */

public class LogicController implements Disposable{
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
    private Class<?> screenType;
    private MyGame game;
    private World world;
    private float accumulator;
    private TmxMapLoader mapLoader;
    private TiledMap tiledMap;
    //Controllers
    private Controller controller;
    //Sprites
    private Hero player;
    private ArrayList<Boulder> boulders;
    private ArrayList<Spikes> spikes;
    private ArrayList<PressingPlate> pps;
    private ArrayList<MegaPressingPlate>mpps;
    private ArrayList<WayBlocker> wayblocks;
    private ArrayList<Chest> chests;
    private ArrayList<MovingPlatform> mps;
    private Array<Item> items;
    private ArrayList<Sign> signs;
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;
    private Array<WarpEvent> warpEvents;
    private Array<D1TopDoor> topDoors;
    private ArrayList<FireGround> fireGrounds;
    private ArrayList<SmashableRock> smashRocks;
    private ArrayList<TextLog> textlogs;
    private boolean d1blck;
    private PressingEvent pressingEvent;
    private PressingEvent megaPressingEvent;

    public LogicController(MyGame game, Vector2 vec,Class<?> type){
        this.setScreenType(type);
        this.setGame(game);
        setWorld(new World(new Vector2(0,0), true));
        objectsDefine(vec);
        objectsLoad();
        setController(new Controller(game));
        getWorld().setContactListener(new WorldContactListener());
        if(type== FreeWorld.class){
            getWarpEvents().add(new WarpEvent(TUTORIAL_DOOR_ID,Door.class, new GameState(new DemoScreen(game,new Vector2(247,35)))));
            getWarpEvents().add(new WarpEvent(DUNGEON1_DOOR_ID,Door.class, new GameState(new Dungeon1(game,new Vector2(DUNGEON1_POSX,DUNGEON1_POSY)))));
        }
    }

    private void objectsDefine(Vector2 vec) {
        setItems(new Array<Item>());
        setItemsToSpawn(new LinkedBlockingQueue<ItemDef>());
        setPlayer(new Hero(this,vec));
        setBoulders(new ArrayList<Boulder>());
        setSpikes(new ArrayList<Spikes>());
        setPps(new ArrayList<PressingPlate>());
        setMpps(new ArrayList<MegaPressingPlate>());
        setWayblocks(new ArrayList<WayBlocker>());
        setMps(new ArrayList<MovingPlatform>());
        setChests(new ArrayList<Chest>());
        setSigns(new ArrayList<Sign>());
        setTopDoors(new Array<D1TopDoor>());
        setSmashRocks(new ArrayList<SmashableRock>());
        setFireGrounds(new ArrayList<FireGround>());
        setWarpEvents(new Array<WarpEvent>());
        setTextlogs(new ArrayList<TextLog>());
        setD1blck(true);
    }

    public void defineMap(String mapName) {
        mapLoader=new TmxMapLoader();
        tiledMap=mapLoader.load(mapName);
        new WorldCreator(this);
    }

    public void update(float dt) {
       // handleSpawningItems();
        getPlayer().getHeroBody().InputUpdate(getController(), dt);
        //takes 1 step in the physics simulation (60 times per second)
        framesPerSecUpdate(dt);
        //Sprites Update
        spritesUpdate(dt);
    }

    private void handleSpawningItems() {
        if(!getItemsToSpawn().isEmpty()){
            ItemDef idef= getItemsToSpawn().poll();
            if(idef.type == Jewel.class){
                getItems().add(new Jewel(MyGame.BLUE_RUPEE, this,  new Vector2(idef.position.x, idef.position.y)));
            }
            else if(idef.type== Heart.class){
                getItems().add(new Heart(this, new Vector2( idef.position.x, idef.position.y)));
            }
            else if(idef.type == SpecialItem.class) {
                getItems().add(new SpecialItem(this, idef.position));
            }
        }
    }

    private void spritesUpdate(float dt) {
        getPlayer().update(dt);
        for(Boulder boulder : getBoulders())
            boulder.update();
       /*for(PressingPlate pp : getPps())
            pp.update(dt);
        for(MegaPressingPlate mpp : getMpps())
            mpp.update(dt);
        for(WayBlocker wb : getWayblocks()){
            wb.update();
            if(!isD1blck())
                wb.destroy();
        }
        for(Chest chest : getChests())
            chest.update(dt);
        for(Sign sign : getSigns())
            sign.update();
        for(D1TopDoor top : getTopDoors())
            top.update();
        for(MovingPlatform m : getMps())
            m.update(dt);
        for(SmashableRock sm : getSmashRocks())
            sm.update(dt);
        for(Spikes spike : getSpikes())
            spike.update();
        for(FireGround fg : getFireGrounds())
            fg.update(dt);
        if(getPressingEvent() !=null)
            getPressingEvent().update(dt);
        if(getMegaPressingEvent() !=null)
            getMegaPressingEvent().update(dt);
        //Items Update
        for(Item item : getItems())
            item.update(dt, getPlayer());
        for(TextLog tlog: getTextlogs()) {
            tlog.update();
        }*/
    }

    private void framesPerSecUpdate(float dt) {
        float frameTime = Math.min(dt, 0.25f);
        setAccumulator(getAccumulator() + frameTime);
        while (getAccumulator() >= 1/60f) {
            getWorld().step(1/60f, 6, 2);
            setAccumulator(getAccumulator() - 1/60f);
        }
    }

    public void resetBoulders() {
        for(int i=0;i<2;i++)
            getBoulders().get(i).getBoulderBody().getBody().setTransform(0.5f+8+2*i ,0.5f+54, 0);
    }

    private void objectsLoad() {
        if(getScreenType() ==FreeWorld.class){
            freeWorldLoad();
        }else if(getScreenType() ==Dungeon1.class){
            dungeon1Load();
        }else if(getScreenType() ==DemoScreen.class){
            demoScreenLoad();
        }else if(getScreenType() ==Null.class){
            testLoad();
        }
    }

    private void testLoad() {
    }

    public void spawnItem(ItemDef idef){
        getItemsToSpawn().add(idef);
    }

    private void freeWorldLoad() {
        FWsignLoad();
        FWchestsLoad();
        FWpressingPlatesLoad();
        FWboulderLoad();
        FWitemsLoad();
    }
    private void FWitemsLoad() {
        ArrayList<Vector2> positions = getGame().getFileReader().ReadFile("rupee_locations","free_world");
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
        getSigns().add(sign1);
        TextLog log1 = new TextLog(getGame());
        log1.setText(getGame().getFileReader().getSignText("sign5"));
        log1.setId(0);
        getTextlogs().add(log1);

        Sign sign2= new Sign(this, new Vector2(9*16+8,6*16-8));
        sign2.addSignId(1);
        getSigns().add(sign2);
        TextLog log2 = new TextLog(getGame());
        log2.setText(getGame().getFileReader().getSignText("sign0"));
        log2.setId(1);
        getTextlogs().add(log2);
    }

    private void FWchestsLoad() {
        ArrayList<Vector2> positions = getGame().getFileReader().ReadFile("chest_locations","free_world");
        for(int i=0; i<positions.size();i++){
            Chest c= new Chest(this, positions.get(i));
            c.addChestId(i);
            getChests().add(c);
        }
    }

    private void FWpressingPlatesLoad() {
        PressingPlate pp1= new PressingPlate(this,new Vector2( PP1_X, PP1_Y));
        PressingPlate pp2= new PressingPlate(this,new Vector2( PP2_X, PP2_Y));
        PressingPlate pp3= new PressingPlate(this,new Vector2(PP3_X, PP3_Y));
        PressingPlate pp4= new PressingPlate(this,new Vector2( PP4_X, PP4_Y));
        ArrayList<PressingPlate> dungeon1_plates= new ArrayList<PressingPlate>();
        dungeon1_plates.add(pp2);//ordem: 2-4-3
        dungeon1_plates.add(pp4);
        dungeon1_plates.add(pp3);
        getPps().add(pp1);
        getPps().add(pp4);
        getPps().add(pp2);
        getPps().add(pp3);
        setPressingEvent(new PressingEvent(dungeon1_plates, this, 0));
    }

    private void FWboulderLoad() {
        WayBlocker wb =  new WayBlocker(this,new Vector2(WB1_X,WB1_Y),0);
        getWayblocks().add(wb);
        WayBlocker wb2 =  new WayBlocker(this,new Vector2(WB2_X,WB2_Y),0);
        getWayblocks().add(wb2);
        WayBlocker wb3 =  new WayBlocker(this,new Vector2(WB3_X,WB3_Y),0);
        getWayblocks().add(wb3);
        Boulder boulder1= new Boulder(this,new Vector2(9*16+8, 31*16+8));
        getBoulders().add(boulder1);
        Boulder boulder2= new Boulder(this,new Vector2(11*16+8, 31*16+8));
        getBoulders().add(boulder2);
    }

    private void dungeon1Load() {
        D1TopDoor topDoor1= new D1TopDoor(this,new Vector2(6*16+8,34*16+24),1);
        getTopDoors().add(topDoor1);
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
        ArrayList<Vector2> positions = getGame().getFileReader().ReadFile("spikes_locations","dungeon1");
        for(int i=0; i<positions.size();i++){
            Spikes sp = new Spikes(this, positions.get(i));
            getSpikes().add(sp);
        }
    }

    private void D1movingPlatformsLoad() {
        MovingPlatform m1= new MovingPlatform(this, new Vector2(MOV_PLAT1_X,MOV_PLAT1_Y), 0);
        m1.setId(0);
        getMps().add(m1);
        MovingPlatform m2= new MovingPlatform(this, new Vector2(MOV_PLAT2_X,MOV_PLAT2_Y), 1);

        m1.setId(1);
        getMps().add(m2);
        MovingPlatform m3= new MovingPlatform(this, new Vector2(MOV_PLAT3_X,MOV_PLAT3_Y), 2);
        m1.setId(2);
        getMps().add(m3);
    }

    private void D1boulderLoad() {
        Boulder boulder1= new Boulder(this,new Vector2(8+10*16, 8+22*16));
        getBoulders().add(boulder1);
        Boulder boulder2= new Boulder(this,new Vector2(8+11*16, 8+22*16));
        getBoulders().add(boulder2);
        Boulder boulder3= new Boulder(this,new Vector2(8+17*16, 8+22*16));
        getBoulders().add(boulder3);
        WayBlocker wb =  new WayBlocker(this,new Vector2(8+6*16,8+33*16),1);
        getWayblocks().add(wb);
    }

    private void D1pressingPlatesLoad() {
        MegaPressingPlate megapp1= new MegaPressingPlate(this,new Vector2(4*16+8,23*16+8));
        getMpps().add(megapp1);
        MegaPressingPlate megapp2= new MegaPressingPlate(this,new Vector2(18*16+8,29*16+8));
        getMpps().add(megapp2);
        setMegaPressingEvent(new PressingEvent(getMpps(),this));
    }

    private void D1itemsLoad() {
        spawnItem(new ItemDef(new Vector2(8+46*16,8+42*16), SpecialItem.class));
        ArrayList<Vector2> positions = getGame().getFileReader().ReadFile("heart_locations","dungeon1");
        for(int i=0; i<positions.size();i++){
            spawnItem(new ItemDef(new Vector2(positions.get(i).x,positions.get(i).y), Heart.class));
        }
    }

    private void D1chestsLoad() {
        ArrayList<Vector2> positions = getGame().getFileReader().ReadFile("chest_locations","dungeon1");
        for(int i=0; i<positions.size();i++){
            Chest c= new Chest(this, positions.get(i));
            c.addChestId(i);
            getChests().add(c);
        }
    }

    private void D1smashRockLoad() {
        ArrayList<Vector2> positions = getGame().getFileReader().ReadFile("rock_locations","dungeon1");
        for(Vector2 vec :positions){
            SmashableRock sm = new SmashableRock(this, vec);
            getSmashRocks().add(sm);
        }
    }

    private void D1fireGroundLoad() {
        ArrayList<Vector2> positions = getGame().getFileReader().ReadFile("fireground_locations","dungeon1");
        for(Vector2 vec :positions){
            FireGround fg= new FireGround(this,vec);
            getFireGrounds().add(fg);
        }
    }

    private void demoScreenLoad() {
        Boulder boulder= new Boulder(this,new Vector2(BOULDER_X, BOULDER_Y));
        getBoulders().add(boulder);
        PressingPlate pp= new PressingPlate(this, new Vector2(PP_X, PP_Y));
        getPps().add(pp);
        SmashableRock sm = new SmashableRock(this, new Vector2(8+5*16,8+8*16));
        getSmashRocks().add(sm);
        DSsignLoad();
        //Items
        spawnItem(new ItemDef(new Vector2(8+14*16,8+15*16), Jewel.class));
        spawnItem(new ItemDef(new Vector2(8+5*16,8+15*16), Heart.class));
    }

    private void DSsignLoad() {
        Sign sign1= new Sign(this, new Vector2(5*16+8,13*16+8));
        sign1.addSignId(0);
        getSigns().add(sign1);
        TextLog log1 = new TextLog(getGame());
        log1.setText(getGame().getFileReader().getSignText("sign1"));
        log1.setId(0);
        getTextlogs().add(log1);

        Sign sign2= new Sign(this, new Vector2(14*16+8,13*16+8));
        sign2.addSignId(1);
        getSigns().add(sign2);
        TextLog log2 = new TextLog(getGame());
        log2.setText(getGame().getFileReader().getSignText("sign2"));
        log2.setId(1);
        getTextlogs().add(log2);

        Sign sign3= new Sign(this, new Vector2(5*16+8,6*16+8));
        sign3.addSignId(2);
        getSigns().add(sign3);
        TextLog log3 = new TextLog(getGame());
        log3.setText(getGame().getFileReader().getSignText("sign3"));
        log3.setId(2);
        getTextlogs().add(log3);

        Sign sign4= new Sign(this, new Vector2(13*16+8,6*16+8));
        sign4.addSignId(3);
        getSigns().add(sign4);
        TextLog log4 = new TextLog(getGame());
        log4.setText(getGame().getFileReader().getSignText("sign4"));
        log4.setId(3);
        getTextlogs().add(log4);
    }


    public Class<?> getScreenType() {
        return screenType;
    }

    public void setScreenType(Class<?> screenType) {
        this.screenType = screenType;
    }

    public MyGame getGame() {
        return game;
    }

    public void setGame(MyGame game) {
        this.game = game;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public float getAccumulator() {
        return accumulator;
    }

    public void setAccumulator(float accumulator) {
        this.accumulator = accumulator;
    }

    public TmxMapLoader getMapLoader() {
        return mapLoader;
    }

    public void setMapLoader(TmxMapLoader mapLoader) {
        this.mapLoader = mapLoader;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public void setTiledMap(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Hero getPlayer() {
        return player;
    }

    public void setPlayer(Hero player) {
        this.player = player;
    }

    public ArrayList<Boulder> getBoulders() {
        return boulders;
    }

    public void setBoulders(ArrayList<Boulder> boulders) {
        this.boulders = boulders;
    }


    public ArrayList<Spikes> getSpikes() {
        return spikes;
    }

    public void setSpikes(ArrayList<Spikes> spikes) {
        this.spikes = spikes;
    }

    public ArrayList<PressingPlate> getPps() {
        return pps;
    }

    public void setPps(ArrayList<PressingPlate> pps) {
        this.pps = pps;
    }

    public ArrayList<MegaPressingPlate> getMpps() {
        return mpps;
    }

    public void setMpps(ArrayList<MegaPressingPlate> mpps) {
        this.mpps = mpps;
    }

    public ArrayList<WayBlocker> getWayblocks() {
        return wayblocks;
    }

    public void setWayblocks(ArrayList<WayBlocker> wayblocks) {
        this.wayblocks = wayblocks;
    }

    public ArrayList<Chest> getChests() {
        return chests;
    }

    public void setChests(ArrayList<Chest> chests) {
        this.chests = chests;
    }

    public ArrayList<MovingPlatform> getMps() {
        return mps;
    }

    public void setMps(ArrayList<MovingPlatform> mps) {
        this.mps = mps;
    }

    public Array<Item> getItems() {
        return items;
    }

    public void setItems(Array<Item> items) {
        this.items = items;
    }

    public ArrayList<Sign> getSigns() {
        return signs;
    }

    public void setSigns(ArrayList<Sign> signs) {
        this.signs = signs;
    }

    public LinkedBlockingQueue<ItemDef> getItemsToSpawn() {
        return itemsToSpawn;
    }

    public void setItemsToSpawn(LinkedBlockingQueue<ItemDef> itemsToSpawn) {
        this.itemsToSpawn = itemsToSpawn;
    }

    public Array<WarpEvent> getWarpEvents() {
        return warpEvents;
    }

    public void setWarpEvents(Array<WarpEvent> warpEvents) {
        this.warpEvents = warpEvents;
    }

    public Array<D1TopDoor> getTopDoors() {
        return topDoors;
    }

    public void setTopDoors(Array<D1TopDoor> topDoors) {
        this.topDoors = topDoors;
    }

    public ArrayList<FireGround> getFireGrounds() {
        return fireGrounds;
    }

    public void setFireGrounds(ArrayList<FireGround> fireGrounds) {
        this.fireGrounds = fireGrounds;
    }

    public ArrayList<SmashableRock> getSmashRocks() {
        return smashRocks;
    }

    public void setSmashRocks(ArrayList<SmashableRock> smashRocks) {
        this.smashRocks = smashRocks;
    }

    public ArrayList<TextLog> getTextlogs() {
        return textlogs;
    }

    public void setTextlogs(ArrayList<TextLog> textlogs) {
        this.textlogs = textlogs;
    }

    public boolean isD1blck() {
        return d1blck;
    }

    public void setD1blck(boolean d1blck) {
        this.d1blck = d1blck;
    }

    public PressingEvent getPressingEvent() {
        return pressingEvent;
    }

    public void setPressingEvent(PressingEvent pressingEvent) {
        this.pressingEvent = pressingEvent;
    }

    public PressingEvent getMegaPressingEvent() {
        return megaPressingEvent;
    }

    public void setMegaPressingEvent(PressingEvent megaPressingEvent) {
        this.megaPressingEvent = megaPressingEvent;
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        world.dispose();
    }
}
