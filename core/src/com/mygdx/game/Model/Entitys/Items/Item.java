package com.mygdx.game.Model.Entitys.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.View.GameScreens.GameScreen;
import com.mygdx.game.Model.Entitys.Hero.Hero;

/**
 * Created by Utilizador on 09-04-2017.
 */

public abstract class Item extends Sprite {
    protected GameScreen screen;
    protected World world;
    protected boolean toDestroy;
    protected boolean destroyed;
    protected String type;

    public Item(GameScreen screen,Vector2 vec){
        this.screen=screen;
        this.world=screen.getWorld();
        type="";
        setPosition(vec.x,vec.y);

        //Define Item
        defineItem();

        toDestroy=false;
        destroyed=false;
    }

    public abstract void defineItem();
    public abstract void use(Hero hero);

    public abstract void update(float dt, Hero hero);

    public void draw(Batch batch){
        if(!destroyed)
            super.draw(batch);
    }

    public void destroy(){
        toDestroy=true;
    }

    public String getType(){
        return type;
    }
}
