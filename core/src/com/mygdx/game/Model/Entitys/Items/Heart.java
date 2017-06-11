package com.mygdx.game.Model.Entitys.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Controller.Entitys.Items.HeartBody;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.MyGame;
import com.mygdx.game.Model.Entitys.Hero.Hero;

/**
 * Created by Jotadaxter on 20/04/2017.
 */

public class Heart extends Item {
    
    /** The heart body. */
    private HeartBody heartBody;
    
    /** The sound. */
    private Sound sound;

    /**
     * Instantiates a new heart.
     *
     * @param logicController the logic controller
     * @param vec the vec
     */
    public Heart(LogicController logicController, Vector2 vec) {
        super(logicController, vec);
        sound=  Gdx.audio.newSound(Gdx.files.internal("Sounds/get_heart.wav"));
        heartBody=new HeartBody(world,this, vec);
        if(!logicController.getGame().getIsTest())
            sprite.setRegion(new TextureRegion(logicController.getGame().getAssetManager().get("Game/heart.png", Texture.class), 0,0,15,15));
    }

    /**
     * Define item.
     */
    @Override
    public void defineItem() {
        type="heart";
        if(!logicController.getGame().getIsTest())
            sprite.setBounds(sprite.getX(),sprite.getY(), 15*MyGame.PIXEL_TO_METER,15*MyGame.PIXEL_TO_METER);
    }

    /**
     * Use.
     *
     * @param hero the hero
     */
    @Override
    public void use(Hero hero) {
        destroy();
    }

    /**
     * Update.
     *
     * @param dt the dt
     * @param hero the hero
     */
    @Override
    public void update(float dt, Hero hero) {
        if(toDestroy && !destroyed){
            sound.play(MyGame.SOUND_VOLUME);
            world.destroyBody(heartBody.getBody());
            destroyed=true;
            hero.addItem(this);
        }
        if(!logicController.getGame().getIsTest()) {
            sprite.setPosition(heartBody.getBody().getPosition().x-sprite.getWidth()/2, heartBody.getBody().getPosition().y-sprite.getHeight()/2);
        }else {
            position=new Vector2(heartBody.getBody().getPosition().x, heartBody.getBody().getPosition().y);
        }
    }
}
