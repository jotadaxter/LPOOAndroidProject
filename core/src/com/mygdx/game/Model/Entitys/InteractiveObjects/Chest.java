package com.mygdx.game.Model.Entitys.InteractiveObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.InteractiveObjects.ChestBody;
import com.mygdx.game.Model.Entitys.Items.Jewel;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Utilizador on 21-05-2017.
 */

public class Chest extends Sprite{
    //Loot Ratios
    public static final int GREEN_RUPEE_RATIO = 40;
    public static final int BLUE_RUPEE_RATIO = 60;
    public static final int RED_RUPEE_RATIO = 100;

    private GameScreen screen;
    private World world;
    private TextureRegion chest_open;
    private TextureRegion chest_closed;
    private ChestBody chestBody;
    private boolean isOpen;
    private int dropLoot;
    private int id;
    private Sound sound1;

    public Chest(GameScreen screen, Vector2 vec) {
        this.world=screen.getWorld();
        this.screen=screen;
        sound1=  Gdx.audio.newSound(Gdx.files.internal("Sounds/get_chest_item.wav"));
        chestBody= new ChestBody(world,this,vec);
        isOpen=false;
        dropLoot=0;
        textureLoad();
        setPosition(vec.x,vec.y);
        setBounds(0,0,16* MyGame.PIXEL_TO_METER,16* MyGame.PIXEL_TO_METER);
        setRegion(chest_closed);
    }

    private void textureLoad() {
        chest_open = new TextureRegion(screen.getGame().assetManager.get("Game/chests.png", Texture.class), 16,0,16,16);
        chest_closed = new TextureRegion(screen.getGame().assetManager.get("Game/chests.png", Texture.class), 0,0,16,16);
    }

    public void update(float dt) {
        setPosition(chestBody.getBody().getPosition().x - getWidth() / 2, chestBody.getBody().getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
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
            j= new Jewel(MyGame.BIG_GREEN_RUPEE,screen, new Vector2(0, 0));
            screen.getHero().addItem(j);
            world.destroyBody(j.getJewelBody().getBody());
        }
        else if(rand> GREEN_RUPEE_RATIO && rand<= BLUE_RUPEE_RATIO){
            j= new Jewel(MyGame.BIG_BLUE_RUPEE,screen,  new Vector2(0, 0));
            screen.getHero().addItem(j);
            world.destroyBody(j.getJewelBody().getBody());
        }
        else if(rand> BLUE_RUPEE_RATIO && rand<= RED_RUPEE_RATIO){
            j= new Jewel(MyGame.BIG_RED_RUPEE,screen, new Vector2( 0, 0));
            screen.getHero().addItem(j);
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
}
