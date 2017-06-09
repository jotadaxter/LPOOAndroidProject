package com.mygdx.game.Model.Entitys.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Controller.Entitys.Items.HeartBody;
import com.mygdx.game.Controller.Entitys.Items.SpecialItemBody;
import com.mygdx.game.Model.Entitys.Hero.Hero;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;
/**
 * Created by Utilizador on 20-05-2017.
 */

public class SpecialItem extends Item{
    private SpecialItemBody specialItemBody;
    private Sound sound;

    public SpecialItem(GameScreen screen, Vector2 vec) {
        super(screen, vec);
        sound=  Gdx.audio.newSound(Gdx.files.internal("Sounds/get_heart_container.wav"));
        specialItemBody=new SpecialItemBody(world,this, vec);
        setRegion(new TextureRegion(screen.getGame().getAssetManager().get("Game/volcano_ruby.png", Texture.class)));
    }

    @Override
    public void defineItem() {
        type="volcano_ruby";
        setBounds(getX(),getY(), 16* MyGame.PIXEL_TO_METER,16*MyGame.PIXEL_TO_METER);
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
        setPosition(specialItemBody.getBody().getPosition().x-getWidth()/2, specialItemBody.getBody().getPosition().y-getHeight()/2);
    }
}
