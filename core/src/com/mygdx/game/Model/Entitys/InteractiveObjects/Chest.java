package com.mygdx.game.Model.Entitys.InteractiveObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.InteractiveObjects.ChestBody;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.Model.Entitys.Items.Jewel;
import com.mygdx.game.MyGame;

import java.util.Random;

/**
 * Created by Utilizador on 21-05-2017.
 */

public class Chest{
    
    /** The Constant GREEN_RUPEE_RATIO. */
    public static final int GREEN_RUPEE_RATIO = 40;
    
    /** The Constant BLUE_RUPEE_RATIO. */
    public static final int BLUE_RUPEE_RATIO = 60;
    
    /** The Constant RED_RUPEE_RATIO. */
    public static final int RED_RUPEE_RATIO = 100;
    
    /** The sprite. */
    private Sprite sprite;
    
    /** The logic controller. */
    private LogicController logicController;
    
    /** The position. */
    private Vector2 position;
    
    /** The world. */
    private World world;
    
    /** The chest open. */
    private TextureRegion chest_open;
    
    /** The chest closed. */
    private TextureRegion chest_closed;
    
    /** The chest body. */
    private ChestBody chestBody;
    
    /** The is open. */
    private boolean isOpen;
    
    /** The drop loot. */
    private int dropLoot;
    
    /** The id. */
    private int id;
    
    /** The sound 1. */
    private Sound sound1;

    /**
     * Instantiates a new chest.
     *
     * @param logicController the logic controller
     * @param vec the vec
     */
    public Chest(LogicController logicController, Vector2 vec) {
        this.world= logicController.getWorld();
        this.logicController=logicController;
        position=vec;
        sprite= new Sprite();
        sound1=  Gdx.audio.newSound(Gdx.files.internal("Sounds/get_chest_item.wav"));
        chestBody= new ChestBody(world,this,vec);
        isOpen=false;
        dropLoot=0;
        if(!logicController.getGame().getIsTest()) {
            textureLoad();
            sprite.setPosition(vec.x, vec.y);
            sprite.setBounds(0, 0, 16 * MyGame.PIXEL_TO_METER, 16 * MyGame.PIXEL_TO_METER);
            sprite.setRegion(chest_closed);
        }
    }

    /**
     * Texture load.
     */
    private void textureLoad() {
        chest_open = new TextureRegion(logicController.getGame().getAssetManager().get("Game/chests.png", Texture.class), 16,0,16,16);
        chest_closed = new TextureRegion(logicController.getGame().getAssetManager().get("Game/chests.png", Texture.class), 0,0,16,16);
    }

    /**
     * Update.
     *
     * @param dt the dt
     */
    public void update(float dt) {
        if(!logicController.getGame().getIsTest()) {
            sprite.setRegion(getFrame(dt));
            sprite.setPosition(chestBody.getBody().getPosition().x-sprite.getWidth()/2, chestBody.getBody().getPosition().y-sprite.getHeight()/2);
        }else {
            position=new Vector2(chestBody.getBody().getPosition().x, chestBody.getBody().getPosition().y);
        }
        if (dropLoot == 1) {
            loot();
            sound1.play(MyGame.SOUND_VOLUME);
            dropLoot = 2;
        }
    }

    /**
     * Loot.
     */
    private void loot() {
        //Random Loot
        Random random= new Random();
        int rand=random.nextInt(100) + 1;
        Jewel j;
        if(rand>0 && rand<=GREEN_RUPEE_RATIO){
            j= new Jewel(MyGame.BIG_GREEN_RUPEE,logicController, new Vector2(0, 0));
            logicController.getPlayer().addItem(j);
            world.destroyBody(j.getJewelBody().getBody());
        }
        else if(rand> GREEN_RUPEE_RATIO && rand<= BLUE_RUPEE_RATIO){
            j= new Jewel(MyGame.BIG_BLUE_RUPEE,logicController,  new Vector2(0, 0));
            logicController.getPlayer().addItem(j);
            world.destroyBody(j.getJewelBody().getBody());
        }
        else if(rand> BLUE_RUPEE_RATIO && rand<= RED_RUPEE_RATIO){
            j= new Jewel(MyGame.BIG_RED_RUPEE,logicController, new Vector2( 0, 0));
            logicController.getPlayer().addItem(j);
            world.destroyBody(j.getJewelBody().getBody());
        }
    }

    /**
     * Gets the closed tex.
     *
     * @return the closed tex
     */
    public TextureRegion getClosedTex() {
        return chest_closed;
    }

    /**
     * Gets the open tex.
     *
     * @return the open tex
     */
    public TextureRegion getOpenTex() {
        return chest_open;
    }

    /**
     * Checks if is open.
     *
     * @return true, if is open
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * Sets the open.
     *
     * @param open the new open
     */
    public void setOpen(boolean open) {
        this.isOpen = open;
        if(open && dropLoot!=2)
            dropLoot=1;
    }

    /**
     * Adds the chest id.
     *
     * @param id the id
     */
    public void addChestId(int id) {
        this.id=id;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the frame.
     *
     * @param dt the dt
     * @return the frame
     */
    public TextureRegion getFrame(float dt) {
        TextureRegion region;
        if(isOpen()) {
            region=getOpenTex();
        }else{
            region=getClosedTex();
        }
        return region;
    }

    /**
     * Draw.
     *
     * @param batch the batch
     */
    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }
}
