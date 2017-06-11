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
    public static final int GREEN_RUPEE_RATIO = 40;
    public static final int BLUE_RUPEE_RATIO = 60;
    public static final int RED_RUPEE_RATIO = 100;
    private Sprite sprite;
    private LogicController logicController;
    private Vector2 position;
    private World world;
    private TextureRegion chest_open;
    private TextureRegion chest_closed;
    private ChestBody chestBody;
    private boolean isOpen;
    private int dropLoot;
    private int id;
    private Sound sound1;

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

    private void textureLoad() {
        chest_open = new TextureRegion(logicController.getGame().getAssetManager().get("Game/chests.png", Texture.class), 16,0,16,16);
        chest_closed = new TextureRegion(logicController.getGame().getAssetManager().get("Game/chests.png", Texture.class), 0,0,16,16);
    }

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

    public TextureRegion getClosedTex() {
        return chest_closed;
    }

    public TextureRegion getOpenTex() {
        return chest_open;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        this.isOpen = open;
        if(open && dropLoot!=2)
            dropLoot=1;
    }

    public void addChestId(int id) {
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public TextureRegion getFrame(float dt) {
        TextureRegion region;
        if(isOpen()) {
            region=getOpenTex();
        }else{
            region=getClosedTex();
        }
        return region;
    }

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }
}
