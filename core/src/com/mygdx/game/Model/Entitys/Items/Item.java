package com.mygdx.game.Model.Entitys.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.Model.Entitys.Hero.Hero;

// TODO: Auto-generated Javadoc
/**
 * Created by Utilizador on 09-04-2017.
 */

public abstract class Item{
    
    /** The world. */
    protected World world;
    
    /** The to destroy. */
    protected boolean toDestroy;
    
    /** The destroyed. */
    protected boolean destroyed;
    
    /** The type. */
    protected String type;
    
    /** The sprite. */
    protected Sprite sprite;
    
    /** The logic controller. */
    protected LogicController logicController;
    
    /** The position. */
    protected Vector2 position;

    /**
     * Instantiates a new item.
     *
     * @param logicController the logic controller
     * @param vec the vec
     */
    public Item(LogicController logicController, Vector2 vec){
        this.logicController=logicController;
        this.world= logicController.getWorld();
        type="";
        this.logicController=logicController;
        position=vec;
        sprite= new Sprite();
        //Define Item
        defineItem();
        toDestroy=false;
        destroyed=false;
    }

    /**
     * Define item.
     */
    public abstract void defineItem();
    
    /**
     * Use.
     *
     * @param hero the hero
     */
    public abstract void use(Hero hero);

    /**
     * Update.
     *
     * @param dt the dt
     * @param hero the hero
     */
    public abstract void update(float dt, Hero hero);

    /**
     * Draw.
     *
     * @param batch the batch
     */
    public void draw(Batch batch){
        if(!destroyed)
            sprite.draw(batch);
    }

    /**
     * Destroy.
     */
    public void destroy(){
        toDestroy=true;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType(){
        return type;
    }
}
