package com.mygdx.game.Model.Entitys.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Controller.Entitys.Items.JewelBody;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.Model.Entitys.Hero.Hero;
import com.mygdx.game.MyGame;

// TODO: Auto-generated Javadoc
/**
 * Created by Utilizador on 06-04-2017.
 */

public class Jewel extends Item {
    
    /** The value. */
    private int value;
    
    /** The jewel body. */
    private JewelBody jewelBody;
    
    /** The sound. */
    private Sound sound;

    /**
     * Instantiates a new jewel.
     *
     * @param value the value
     * @param logicController the logic controller
     * @param vec the vec
     */
    public Jewel(int value, LogicController logicController, Vector2 vec) {
        super(logicController, vec);
        this.value=value;
        sound=  Gdx.audio.newSound(Gdx.files.internal("Sounds/get_rupee.wav"));
        if(!logicController.getGame().getIsTest()) {
            if (value > 1 && value < 50)
                sprite.setBounds(sprite.getX(), sprite.getY(), 7 * MyGame.PIXEL_TO_METER, 14 * MyGame.PIXEL_TO_METER);
            else
                sprite.setBounds(sprite.getX(), sprite.getY(), 12 * MyGame.PIXEL_TO_METER, 16 * MyGame.PIXEL_TO_METER);
            defineTexture(value);
        }
        jewelBody= new JewelBody(world, this, value, vec);
    }

    /**
     * Define texture.
     *
     * @param value the value
     */
    private void defineTexture(int value) {
        if(value==1)
            sprite.setRegion(new TextureRegion(logicController.getGame().getAssetManager().get("Game/green_rupee.png", Texture.class), 0,0,7,14));
        if(value==5)
            sprite.setRegion(new TextureRegion(logicController.getGame().getAssetManager().get("Game/blue_rupee.png", Texture.class), 0,0,7,14));
        if(value==20)
            sprite.setRegion(new TextureRegion(logicController.getGame().getAssetManager().get("Game/red_rupee.png", Texture.class), 0,0,7,14));
        if(value==50)
            sprite.setRegion(new TextureRegion(logicController.getGame().getAssetManager().get("Game/big_green_rupee.png", Texture.class), 0,0,12,16));
        if(value==100)
            sprite.setRegion(new TextureRegion(logicController.getGame().getAssetManager().get("Game/big_blue_rupee.png", Texture.class), 0,0,12,16));
        if(value==500)
            sprite.setRegion(new TextureRegion(logicController.getGame().getAssetManager().get("Game/big_red_rupee.png", Texture.class), 0,0,12,16));
    }

    /* (non-Javadoc)
     * @see com.mygdx.game.Model.Entitys.Items.Item#defineItem()
     */
    @Override
    public void defineItem() {
        type="jewel";
    }

    /* (non-Javadoc)
     * @see com.mygdx.game.Model.Entitys.Items.Item#use(com.mygdx.game.Model.Entitys.Hero.Hero)
     */
    @Override
    public void use(Hero hero) {
        destroy();
    }

    /* (non-Javadoc)
     * @see com.mygdx.game.Model.Entitys.Items.Item#update(float, com.mygdx.game.Model.Entitys.Hero.Hero)
     */
    @Override
    public void update(float dt, Hero hero) {
        if(toDestroy && !destroyed){
            sound.play(MyGame.SOUND_VOLUME);
            world.destroyBody(jewelBody.getBody());
            destroyed=true;
            hero.addItem(this);
        }
        if(!logicController.getGame().getIsTest()) {
            sprite.setPosition(jewelBody.getBody().getPosition().x-sprite.getWidth()/2, jewelBody.getBody().getPosition().y-sprite.getHeight()/2);
        }else {
            position=new Vector2(jewelBody.getBody().getPosition().x, jewelBody.getBody().getPosition().y);
        }
    }

   /**
    * Gets the value.
    *
    * @return the value
    */
   public int getValue(){
        return value;
    }

    /**
     * Gets the jewel body.
     *
     * @return the jewel body
     */
    public JewelBody getJewelBody() {
        return jewelBody;
    }
}
