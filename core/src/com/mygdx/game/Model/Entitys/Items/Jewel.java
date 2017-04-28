package com.mygdx.game.Model.Entitys.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.Controller.Entitys.Items.JewelBody;
import com.mygdx.game.Model.Entitys.Hero.Hero;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.Screens.MyScreen;

/**
 * Created by Utilizador on 06-04-2017.
 */

public class Jewel extends Item {
    private int value;
    private JewelBody jewelBody;

    public Jewel(int value, MyScreen screen, float x, float y) {
        super(screen, x, y);
        this.value=value;

        //Texture Dimentions
        if(value>1 && value<50)
            setBounds(getX(),getY(), 7*MyGame.PIXEL_TO_METER,14*MyGame.PIXEL_TO_METER);
        else
            setBounds(getX(),getY(), 12*MyGame.PIXEL_TO_METER,16*MyGame.PIXEL_TO_METER);
        defineTexture(value);

        jewelBody= new JewelBody(world, this, value, x,y);
    }

    private void defineTexture(int value) {
        switch(value){
            case 1:{
                setRegion(screen.getAtlas().findRegion("green_rupee"), 0,0,7,14);
            }
            break;
            case 5:{
                setRegion(screen.getAtlas().findRegion("blue_rupee"), 0,0,7,14);
            }
            break;
            case 20:{
                setRegion(screen.getAtlas().findRegion("red_rupee"), 0,0,7,14);
            }
            break;
            case 50:{
                setRegion(screen.getAtlas().findRegion("big_green_rupee"), 0,0,12,16);
            }
            break;
            case 100:{
                setRegion(screen.getAtlas().findRegion("big_blue_rupee"), 0,0,12,16);
            }
            break;
            case 500:{
                setRegion(screen.getAtlas().findRegion("big_red_rupee"), 0,0,12,16);
            }
            break;

        }
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
            world.destroyBody(jewelBody.getBody());
            destroyed=true;
            hero.addItem(this);
        }
        setPosition(jewelBody.getBody().getPosition().x-getWidth()/2, jewelBody.getBody().getPosition().y-getHeight()/2);
    }

    @Override
    public void pickedUp() {
        Gdx.app.log("Picked UP Rupee", "");
    }

    public int getValue(){
        return value;
    }
}
