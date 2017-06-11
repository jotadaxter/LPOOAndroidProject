package com.mygdx.game.Model.Entitys.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Controller.Entitys.Items.SpecialItemBody;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.Model.Entitys.Hero.Hero;
import com.mygdx.game.MyGame;

/**
 * Created by Utilizador on 20-05-2017.
 */

public class SpecialItem extends Item{
    private SpecialItemBody specialItemBody;
    private Sound sound;

    public SpecialItem(LogicController logicController, Vector2 vec) {
        super(logicController, vec);
        sound=  Gdx.audio.newSound(Gdx.files.internal("Sounds/get_heart_container.wav"));
        specialItemBody=new SpecialItemBody(world,this, vec);
        if(!logicController.getGame().getIsTest())
            sprite.setRegion(new TextureRegion(logicController.getGame().getAssetManager().get("Game/volcano_ruby.png", Texture.class)));
    }

    @Override
    public void defineItem() {
        type="volcano_ruby";
        if(!logicController.getGame().getIsTest())
            sprite.setBounds(sprite.getX(),sprite.getY(), 16* MyGame.PIXEL_TO_METER,16*MyGame.PIXEL_TO_METER);
    }

    @Override
    public void use(Hero hero) {
        destroy();
    }

    @Override
    public void update(float dt, Hero hero) {
        if(toDestroy && !destroyed){
            sound.play(MyGame.SOUND_VOLUME);
            world.destroyBody(specialItemBody.getBody());
            destroyed=true;
            hero.addItem(this);
        }
        if(!logicController.getGame().getIsTest()) {
            sprite.setPosition(specialItemBody.getBody().getPosition().x-sprite.getWidth()/2, specialItemBody.getBody().getPosition().y-sprite.getHeight()/2);
        }else {
            position=new Vector2(specialItemBody.getBody().getPosition().x, specialItemBody.getBody().getPosition().y);
        }
    }
}
