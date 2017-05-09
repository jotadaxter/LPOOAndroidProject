package com.mygdx.game.Model.Entitys.Items;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Controller.Entitys.Items.HeartBody;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;
import com.mygdx.game.Model.Entitys.Hero.Hero;

/**
 * Created by Jotadaxter on 20/04/2017.
 */

public class Heart extends Item {
    private HeartBody heartBody;

    public Heart(GameScreen screen, float x, float y) {
        super(screen, x, y);
        heartBody=new HeartBody(world,this, x,y);
        setRegion(screen.getAtlas().findRegion("heart"), 0,0,15,15);
    }

    @Override
    public void defineItem() {
        type="heart";
        //Texture Dimentions
        setBounds(getX(),getY(), 15*MyGame.PIXEL_TO_METER,15*MyGame.PIXEL_TO_METER);



    }

    @Override
    public void use(Hero hero) {
        destroy();
    }

    @Override
    public void pickedUp() {
        Gdx.app.log("Picked Up Heart", "");
    }

    @Override
    public void update(float dt, Hero hero) {
        if(toDestroy && !destroyed){
            world.destroyBody(heartBody.getBody());
            destroyed=true;
            hero.addItem(this);
        }
        setPosition(heartBody.getBody().getPosition().x-getWidth()/2, heartBody.getBody().getPosition().y-getHeight()/2);
    }
}
