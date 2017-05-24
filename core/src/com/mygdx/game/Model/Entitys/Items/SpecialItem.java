package com.mygdx.game.Model.Entitys.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    public SpecialItem(GameScreen screen, float x, float y) {
        super(screen, x, y);
        specialItemBody=new SpecialItemBody(world,this, x,y);
        setRegion(new TextureRegion(screen.getGame().assetManager.get("Game/volcano_ruby.png", Texture.class)));
    }

    @Override
    public void defineItem() {
        type="volcano_ruby";
        //Texture Dimentions
        setBounds(getX(),getY(), 16* MyGame.PIXEL_TO_METER,16*MyGame.PIXEL_TO_METER);
    }

    @Override
    public void use(Hero hero) {
        destroy();
    }

    @Override
    public void pickedUp() {
        Gdx.app.log("Picked Up Volcano Ruby", "");
    }

    @Override
    public void update(float dt, Hero hero) {
        if(toDestroy && !destroyed){
            world.destroyBody(specialItemBody.getBody());
            destroyed=true;
            hero.addItem(this);
        }
        setPosition(specialItemBody.getBody().getPosition().x-getWidth()/2, specialItemBody.getBody().getPosition().y-getHeight()/2);
    }
}
