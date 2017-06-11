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

// TODO: Auto-generated Javadoc
/**
 * Created by Jotadaxter on 11/06/2017.
 */

public class LogicController implements Disposable{
    
    /** The Constant DUNGEON1_POSX. */
    //Hero Info
    public static final int DUNGEON1_POSX = 8+3*16;
    
    /** The Constant DUNGEON1_POSY. */
    public static final int DUNGEON1_POSY = 8+3*16;
    
    /** The Constant TUTORIAL_DOOR_ID. */
    public static final int TUTORIAL_DOOR_ID=1;
    
    /** The Constant DUNGEON1_DOOR_ID. */
    public static final int DUNGEON1_DOOR_ID=3;
    
    /** The Constant BOULDER_X. */
    public static final int BOULDER_X = 8+11*16;
    
    /** The Constant BOULDER_Y. */
    public static final int BOULDER_Y = 8+8*16;
    
    /** The Constant PP_X. */
    //PressingPlate Position
    public static final int PP_X = 8+14*16;
    
    /** The Constant PP_Y. */
    public static final int PP_Y = 8+8*16;
    
    /** The Constant PP1_X. */
    //PressingPlate Position
    public static final int PP1_X = 8+16*8;
    
    /** The Constant PP1_Y. */
    public static final int PP1_Y = 8+16*35;
    
    /** The Constant PP2_X. */
    public static final int PP2_X = 8+16*12;
    
    /** The Constant PP2_Y. */
    public static final int PP2_Y = 8+16*35;
    
    /** The Constant PP3_X. */
    public static final int PP3_X = 8+16*8;
    
    /** The Constant PP3_Y. */
    public static final int PP3_Y = 8+16*32;
    
    /** The Constant PP4_X. */
    public static final int PP4_X = 8+16*12;
    
    /** The Constant PP4_Y. */
    public static final int PP4_Y = 8+16*32;
    
    /** The Constant WB1_X. */
    //WayBlockers
    public static final int WB1_X = 8+16*23;
    
    /** The Constant WB1_Y. */
    public static final int WB1_Y = 8+16*50;
    
    /** The Constant WB2_X. */
    public static final int WB2_X = 8+16*24;
    
    /** The Constant WB2_Y. */
    public static final int WB2_Y = 8+16*50;
    
    /** The Constant WB3_X. */
    public static final int WB3_X = 8+16*25;
    
    /** The Constant WB3_Y. */
    public static final int WB3_Y = 8+16*50;
    
    /** The Constant MOV_PLAT1_X. */
    public static final int MOV_PLAT1_X = 45*16;
    
    /** The Constant MOV_PLAT1_Y. */
    public static final int MOV_PLAT1_Y = 16*23;
    
    /** The Constant MOV_PLAT2_X. */
    public static final int MOV_PLAT2_X = 13*16;
    
    /** The Constant MOV_PLAT2_Y. */
    public static final int MOV_PLAT2_Y = 41*16;
    
    /** The Constant MOV_PLAT3_X. */
    public static final int MOV_PLAT3_X = 30*16;
    
    /** The Constant MOV_PLAT3_Y. */
    public static final int MOV_PLAT3_Y = 41*16;
    
    /** The screen type. */
    private Class<?> screenType;
    
    /** The game. */
    private MyGame game;
    
    /** The world. */
    private World world;
    
    /** The accumulator. */
    private float accumulator;
    
    /** The map loader. */
    private TmxMapLoader mapLoader;
    
    /** The tiled map. */
    private TiledMap tiledMap;
    
    /** The controller. */
    //Controllers
    private Controller controller;
    
    /** The player. */
    //Sprites
    private Hero player;
    
    /** The boulders. */
    private ArrayList<Boulder> boulders;
    
    /** The spikes. */
    private ArrayList<Spikes> spikes;
    
    /** The pps. */
    private ArrayList<PressingPlate> pps;
    
    /** The mpps. */
    private ArrayList<MegaPressingPlate>mpps;
    
    /** The wayblocks. */
    private ArrayList<WayBlocker> wayblocks;
    
    /** The chests. */
    private ArrayList<Chest> chests;
    
    /** The mps. */
    private ArrayList<MovingPlatform> mps;
    
    /** The items. */
    private Array<Item> items;
    
    /** The signs. */
    private ArrayList<Sign> signs;
    
    /** The items to spawn. */
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;
    
    /** The warp events. */
    private Array<WarpEvent> warpEvents;
    
    /** The top doors. */
    private Array<D1TopDoor> topDoors;
    
    /** The fire grounds. */
    private ArrayList<FireGround> fireGrounds;
    
    /** The smash rocks. */
    private ArrayList<SmashableRock> smashRocks;
    
    /** The textlogs. */
    private ArrayList<TextLog> textlogs;
    
    /** The d 1 blck. */
    private boolean d1blck;
    
    /** The pressing event. */
    private PressingEvent pressingEvent;
    
    /** The mega pressing event. */
    private PressingEvent megaPressingEvent;

    /**
     * Instantiates a new logic controller.
     *
     * @param game the game
     * @param vec the vec
     * @param type the type
     */
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

    /**
     * Objects define.
     *
     * @param vec the vec
     */
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

    /**
     * Define map.
     *
     * @param mapName the map name
     */
    public void defineMap(String mapName) {
        mapLoader=new TmxMapLoader();
        tiledMap=mapLoader.load(mapName);
        new WorldCreator(this);
    }

    /**
     * Update.
     *
     * @param dt the dt
     */
    public void update(float dt) {
        handleSpawningItems();
        getPlayer().getHeroBody().InputUpdate(getController(), dt);
        //takes 1 step in the physics simulation (60 times per second)
        framesPerSecUpdate(dt);
        //Sprites Update
        spritesUpdate(dt);
    }

    /**
     * Handle spawning items.
     */
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

    /**
     * Sprites update.
     *
     * @param dt the dt
     */
    private void spritesUpdate(float dt) {
        getPlayer().update(dt);
        for(Boulder boulder : getBoulders())
            boulder.update();
       for(PressingPlate pp : getPps())
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
        }
    }

    /**
     * Frames per sec update.
     *
     * @param dt the dt
     */
    private void framesPerSecUpdate(float dt) {
        float frameTime = Math.min(dt, 0.25f);
        setAccumulator(getAccumulator() + frameTime);
        while (getAccumulator() >= 1/60f) {
            getWorld().step(1/60f, 6, 2);
            setAccumulator(getAccumulator() - 1/60f);
        }
    }

    /**
     * Reset boulders.
     */
    public void resetBoulders() {
        for(int i=0;i<2;i++)
            getBoulders().get(i).getBoulderBody().getBody().setTransform(0.5f+8+2*i ,0.5f+54, 0);
    }

    /**
     * Objects load.
     */
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

    /**
     * Test load.
     */
    private void testLoad() {
    }

    /**
     * Spawn item.
     *
     * @param idef the idef
     */
    public void spawnItem(ItemDef idef){
        getItemsToSpawn().add(idef);
    }

    /**
     * Free world load.
     */
    private void freeWorldLoad() {
        FWsignLoad();
        FWchestsLoad();
        FWpressingPlatesLoad();
        FWboulderLoad();
        FWitemsLoad();
    }
    
    /**
     * F witems load.
     */
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

    /**
     * F wsign load.
     */
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

    /**
     * F wchests load.
     */
    private void FWchestsLoad() {
        ArrayList<Vector2> positions = getGame().getFileReader().ReadFile("chest_locations","free_world");
        for(int i=0; i<positions.size();i++){
            Chest c= new Chest(this, positions.get(i));
            c.addChestId(i);
            getChests().add(c);
        }
    }

    /**
     * F wpressing plates load.
     */
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

    /**
     * F wboulder load.
     */
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

    /**
     * Dungeon 1 load.
     */
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

    /**
     * D 1 spikes load.
     */
    private void D1spikesLoad() {
        ArrayList<Vector2> positions = getGame().getFileReader().ReadFile("spikes_locations","dungeon1");
        for(int i=0; i<positions.size();i++){
            Spikes sp = new Spikes(this, positions.get(i));
            getSpikes().add(sp);
        }
    }

    /**
     * D 1 moving platforms load.
     */
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

    /**
     * D 1 boulder load.
     */
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

    /**
     * D 1 pressing plates load.
     */
    private void D1pressingPlatesLoad() {
        MegaPressingPlate megapp1= new MegaPressingPlate(this,new Vector2(4*16+8,23*16+8));
        getMpps().add(megapp1);
        MegaPressingPlate megapp2= new MegaPressingPlate(this,new Vector2(18*16+8,29*16+8));
        getMpps().add(megapp2);
        setMegaPressingEvent(new PressingEvent(getMpps(),this));
    }

    /**
     * D 1 items load.
     */
    private void D1itemsLoad() {
        spawnItem(new ItemDef(new Vector2(8+46*16,8+42*16), SpecialItem.class));
        ArrayList<Vector2> positions = getGame().getFileReader().ReadFile("heart_locations","dungeon1");
        for(int i=0; i<positions.size();i++){
            spawnItem(new ItemDef(new Vector2(positions.get(i).x,positions.get(i).y), Heart.class));
        }
    }

    /**
     * D 1 chests load.
     */
    private void D1chestsLoad() {
        ArrayList<Vector2> positions = getGame().getFileReader().ReadFile("chest_locations","dungeon1");
        for(int i=0; i<positions.size();i++){
            Chest c= new Chest(this, positions.get(i));
            c.addChestId(i);
            getChests().add(c);
        }
    }

    /**
     * D 1 smash rock load.
     */
    private void D1smashRockLoad() {
        ArrayList<Vector2> positions = getGame().getFileReader().ReadFile("rock_locations","dungeon1");
        for(Vector2 vec :positions){
            SmashableRock sm = new SmashableRock(this, vec);
            getSmashRocks().add(sm);
        }
    }

    /**
     * D 1 fire ground load.
     */
    private void D1fireGroundLoad() {
        ArrayList<Vector2> positions = getGame().getFileReader().ReadFile("fireground_locations","dungeon1");
        for(Vector2 vec :positions){
            FireGround fg= new FireGround(this,vec);
            getFireGrounds().add(fg);
        }
    }

    /**
     * Demo screen load.
     */
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

    /**
     * D ssign load.
     */
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


    /**
     * Gets the screen type.
     *
     * @return the screen type
     */
    public Class<?> getScreenType() {
        return screenType;
    }

    /**
     * Sets the screen type.
     *
     * @param screenType the new screen type
     */
    public void setScreenType(Class<?> screenType) {
        this.screenType = screenType;
    }

    /**
     * Gets the game.
     *
     * @return the game
     */
    public MyGame getGame() {
        return game;
    }

    /**
     * Sets the game.
     *
     * @param game the new game
     */
    public void setGame(MyGame game) {
        this.game = game;
    }

    /**
     * Gets the world.
     *
     * @return the world
     */
    public World getWorld() {
        return world;
    }

    /**
     * Sets the world.
     *
     * @param world the new world
     */
    public void setWorld(World world) {
        this.world = world;
    }

    /**
     * Gets the accumulator.
     *
     * @return the accumulator
     */
    public float getAccumulator() {
        return accumulator;
    }

    /**
     * Sets the accumulator.
     *
     * @param accumulator the new accumulator
     */
    public void setAccumulator(float accumulator) {
        this.accumulator = accumulator;
    }

    /**
     * Gets the map loader.
     *
     * @return the map loader
     */
    public TmxMapLoader getMapLoader() {
        return mapLoader;
    }

    /**
     * Sets the map loader.
     *
     * @param mapLoader the new map loader
     */
    public void setMapLoader(TmxMapLoader mapLoader) {
        this.mapLoader = mapLoader;
    }

    /**
     * Gets the tiled map.
     *
     * @return the tiled map
     */
    public TiledMap getTiledMap() {
        return tiledMap;
    }

    /**
     * Sets the tiled map.
     *
     * @param tiledMap the new tiled map
     */
    public void setTiledMap(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
    }

    /**
     * Gets the controller.
     *
     * @return the controller
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Sets the controller.
     *
     * @param controller the new controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Gets the player.
     *
     * @return the player
     */
    public Hero getPlayer() {
        return player;
    }

    /**
     * Sets the player.
     *
     * @param player the new player
     */
    public void setPlayer(Hero player) {
        this.player = player;
    }

    /**
     * Gets the boulders.
     *
     * @return the boulders
     */
    public ArrayList<Boulder> getBoulders() {
        return boulders;
    }

    /**
     * Sets the boulders.
     *
     * @param boulders the new boulders
     */
    public void setBoulders(ArrayList<Boulder> boulders) {
        this.boulders = boulders;
    }


    /**
     * Gets the spikes.
     *
     * @return the spikes
     */
    public ArrayList<Spikes> getSpikes() {
        return spikes;
    }

    /**
     * Sets the spikes.
     *
     * @param spikes the new spikes
     */
    public void setSpikes(ArrayList<Spikes> spikes) {
        this.spikes = spikes;
    }

    /**
     * Gets the pps.
     *
     * @return the pps
     */
    public ArrayList<PressingPlate> getPps() {
        return pps;
    }

    /**
     * Sets the pps.
     *
     * @param pps the new pps
     */
    public void setPps(ArrayList<PressingPlate> pps) {
        this.pps = pps;
    }

    /**
     * Gets the mpps.
     *
     * @return the mpps
     */
    public ArrayList<MegaPressingPlate> getMpps() {
        return mpps;
    }

    /**
     * Sets the mpps.
     *
     * @param mpps the new mpps
     */
    public void setMpps(ArrayList<MegaPressingPlate> mpps) {
        this.mpps = mpps;
    }

    /**
     * Gets the wayblocks.
     *
     * @return the wayblocks
     */
    public ArrayList<WayBlocker> getWayblocks() {
        return wayblocks;
    }

    /**
     * Sets the wayblocks.
     *
     * @param wayblocks the new wayblocks
     */
    public void setWayblocks(ArrayList<WayBlocker> wayblocks) {
        this.wayblocks = wayblocks;
    }

    /**
     * Gets the chests.
     *
     * @return the chests
     */
    public ArrayList<Chest> getChests() {
        return chests;
    }

    /**
     * Sets the chests.
     *
     * @param chests the new chests
     */
    public void setChests(ArrayList<Chest> chests) {
        this.chests = chests;
    }

    /**
     * Gets the mps.
     *
     * @return the mps
     */
    public ArrayList<MovingPlatform> getMps() {
        return mps;
    }

    /**
     * Sets the mps.
     *
     * @param mps the new mps
     */
    public void setMps(ArrayList<MovingPlatform> mps) {
        this.mps = mps;
    }

    /**
     * Gets the items.
     *
     * @return the items
     */
    public Array<Item> getItems() {
        return items;
    }

    /**
     * Sets the items.
     *
     * @param items the new items
     */
    public void setItems(Array<Item> items) {
        this.items = items;
    }

    /**
     * Gets the signs.
     *
     * @return the signs
     */
    public ArrayList<Sign> getSigns() {
        return signs;
    }

    /**
     * Sets the signs.
     *
     * @param signs the new signs
     */
    public void setSigns(ArrayList<Sign> signs) {
        this.signs = signs;
    }

    /**
     * Gets the items to spawn.
     *
     * @return the items to spawn
     */
    public LinkedBlockingQueue<ItemDef> getItemsToSpawn() {
        return itemsToSpawn;
    }

    /**
     * Sets the items to spawn.
     *
     * @param itemsToSpawn the new items to spawn
     */
    public void setItemsToSpawn(LinkedBlockingQueue<ItemDef> itemsToSpawn) {
        this.itemsToSpawn = itemsToSpawn;
    }

    /**
     * Gets the warp events.
     *
     * @return the warp events
     */
    public Array<WarpEvent> getWarpEvents() {
        return warpEvents;
    }

    /**
     * Sets the warp events.
     *
     * @param warpEvents the new warp events
     */
    public void setWarpEvents(Array<WarpEvent> warpEvents) {
        this.warpEvents = warpEvents;
    }

    /**
     * Gets the top doors.
     *
     * @return the top doors
     */
    public Array<D1TopDoor> getTopDoors() {
        return topDoors;
    }

    /**
     * Sets the top doors.
     *
     * @param topDoors the new top doors
     */
    public void setTopDoors(Array<D1TopDoor> topDoors) {
        this.topDoors = topDoors;
    }

    /**
     * Gets the fire grounds.
     *
     * @return the fire grounds
     */
    public ArrayList<FireGround> getFireGrounds() {
        return fireGrounds;
    }

    /**
     * Sets the fire grounds.
     *
     * @param fireGrounds the new fire grounds
     */
    public void setFireGrounds(ArrayList<FireGround> fireGrounds) {
        this.fireGrounds = fireGrounds;
    }

    /**
     * Gets the smash rocks.
     *
     * @return the smash rocks
     */
    public ArrayList<SmashableRock> getSmashRocks() {
        return smashRocks;
    }

    /**
     * Sets the smash rocks.
     *
     * @param smashRocks the new smash rocks
     */
    public void setSmashRocks(ArrayList<SmashableRock> smashRocks) {
        this.smashRocks = smashRocks;
    }

    /**
     * Gets the textlogs.
     *
     * @return the textlogs
     */
    public ArrayList<TextLog> getTextlogs() {
        return textlogs;
    }

    /**
     * Sets the textlogs.
     *
     * @param textlogs the new textlogs
     */
    public void setTextlogs(ArrayList<TextLog> textlogs) {
        this.textlogs = textlogs;
    }

    /**
     * Checks if is d 1 blck.
     *
     * @return true, if is d 1 blck
     */
    public boolean isD1blck() {
        return d1blck;
    }

    /**
     * Sets the d 1 blck.
     *
     * @param d1blck the new d 1 blck
     */
    public void setD1blck(boolean d1blck) {
        this.d1blck = d1blck;
    }

    /**
     * Gets the pressing event.
     *
     * @return the pressing event
     */
    public PressingEvent getPressingEvent() {
        return pressingEvent;
    }

    /**
     * Sets the pressing event.
     *
     * @param pressingEvent the new pressing event
     */
    public void setPressingEvent(PressingEvent pressingEvent) {
        this.pressingEvent = pressingEvent;
    }

    /**
     * Gets the mega pressing event.
     *
     * @return the mega pressing event
     */
    public PressingEvent getMegaPressingEvent() {
        return megaPressingEvent;
    }

    /**
     * Sets the mega pressing event.
     *
     * @param megaPressingEvent the new mega pressing event
     */
    public void setMegaPressingEvent(PressingEvent megaPressingEvent) {
        this.megaPressingEvent = megaPressingEvent;
    }

    /**
     * Dispose.
     */
    @Override
    public void dispose() {
        tiledMap.dispose();
        world.dispose();
    }
}
