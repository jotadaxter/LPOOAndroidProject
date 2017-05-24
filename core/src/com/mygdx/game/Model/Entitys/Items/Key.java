package com.mygdx.game.Model.Entitys.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.Controller.Entitys.Items.KeyBody;
import com.mygdx.game.Model.Entitys.Hero.Hero;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Jotadaxter on 02/05/2017.
 */

public class Key extends Item{
    private KeyBody keyBody;
    Sound sound;

    public Key(GameScreen screen, float x, float y) {
        super(screen, x, y);
        sound = Gdx.audio.newSound(Gdx.files.internal("Sounds/get_chest_item.wav"));
        keyBody=new KeyBody(world,this, x,y);
        setRegion(screen.getAtlas().findRegion("key"), 0,0,15,15);
    }

    @Override
    public void defineItem() {
        type="key";
        //Texture Dimentions
        setBounds(getX(),getY(), 15* MyGame.PIXEL_TO_METER,15*MyGame.PIXEL_TO_METER);
    }

    @Override
    public void use(Hero hero) {
        destroy();
    }

    @Override
    public void update(float dt, Hero hero) {
        if(toDestroy && !destroyed){
            sound.play();
            world.destroyBody(keyBody.getBody());
            destroyed=true;
            hero.addItem(this);
        }
        setPosition(keyBody.getBody().getPosition().x-getWidth()/2, keyBody.getBody().getPosition().y-getHeight()/2);
    }

    @Override
    public void pickedUp() {
        Gdx.app.log("Picked Up key", "");
    }
}
