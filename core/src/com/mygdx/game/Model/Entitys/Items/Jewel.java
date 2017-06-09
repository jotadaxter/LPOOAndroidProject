package com.mygdx.game.Model.Entitys.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Controller.Entitys.Items.JewelBody;
import com.mygdx.game.Model.Entitys.Hero.Hero;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Utilizador on 06-04-2017.
 */

public class Jewel extends Item {
    private int value;
    private JewelBody jewelBody;
    private Sound sound;

    public Jewel(int value, GameScreen screen, Vector2 vec) {
        super(screen, vec);
        this.value=value;
        sound=  Gdx.audio.newSound(Gdx.files.internal("Sounds/get_rupee.wav"));
        if(value>1 && value<50)
            setBounds(getX(),getY(), 7*MyGame.PIXEL_TO_METER,14*MyGame.PIXEL_TO_METER);
        else
            setBounds(getX(),getY(), 12*MyGame.PIXEL_TO_METER,16*MyGame.PIXEL_TO_METER);
        defineTexture(value);

        jewelBody= new JewelBody(world, this, value, vec);
    }

    private void defineTexture(int value) {
        if(value==1)
            setRegion(screen.getAtlas().findRegion("green_rupee"), 0,0,7,14);
        if(value==5)
            setRegion(screen.getAtlas().findRegion("blue_rupee"), 0,0,7,14);
        if(value==20)
            setRegion(screen.getAtlas().findRegion("red_rupee"), 0,0,7,14);
        if(value==50)
            setRegion(screen.getAtlas().findRegion("big_green_rupee"), 0,0,12,16);
        if(value==100)
            setRegion(screen.getAtlas().findRegion("big_blue_rupee"), 0,0,12,16);
        if(value==500)
            setRegion(screen.getAtlas().findRegion("big_red_rupee"), 0,0,12,16);
    }

    @Override
    public void defineItem() {
        type="jewel";
    }

    @Override
    public void use(Hero hero) {
        destroy();
    }

    @Override
    public void update(float dt, Hero hero) {
        if(toDestroy && !destroyed){
            sound.play(MyGame.SOUND_VOLUME);
            world.destroyBody(jewelBody.getBody());
            destroyed=true;
            hero.addItem(this);
        }
        setPosition(jewelBody.getBody().getPosition().x-getWidth()/2, jewelBody.getBody().getPosition().y-getHeight()/2);
    }

   public int getValue(){
        return value;
    }

    public JewelBody getJewelBody() {
        return jewelBody;
    }
}
