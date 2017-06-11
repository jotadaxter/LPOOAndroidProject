package com.mygdx.game.Model.Entitys.InteractiveObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.InteractiveObjects.SignBody;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.FreeWorld;

/**
 * Created by Utilizador on 21-05-2017.
 */

public class Sign{
    
    /** The world. */
    private World world;
    
    /** The sign tex. */
    private TextureRegion signTex;
    
    /** The sign body. */
    private SignBody signBody;
    
    /** The open log. */
    private boolean openLog;
    
    /** The id. */
    private int id;
    
    /** The sprite. */
    private Sprite sprite;
    
    /** The logic controller. */
    private LogicController logicController;
    
    /** The position. */
    private Vector2 position;

    /**
     * Instantiates a new sign.
     *
     * @param logicController the logic controller
     * @param vec the vec
     */
    public Sign(LogicController logicController, Vector2 vec) {
        this.world= logicController.getWorld();
        this.logicController=logicController;
        this.logicController=logicController;
        position=vec;
        sprite= new Sprite();
        signBody= new SignBody(world,this,vec);
        openLog=false;
        if(!logicController.getGame().getIsTest()) {
            textureLoad();
            sprite.setPosition(vec.x, vec.y);
            sprite.setBounds(0, 0, 16 * MyGame.PIXEL_TO_METER, 16 * MyGame.PIXEL_TO_METER);
            sprite.setRegion(signTex);
        }
    }

    /**
     * Texture load.
     */
    private void textureLoad() {
        signTex = new TextureRegion(logicController.getGame().getAssetManager().get("Game/sign.png", Texture.class), 0,0,15,16);
    }

    /**
     * Update.
     */
    public void update(){
        if(!logicController.getGame().getIsTest()) {
        sprite.setPosition(signBody.getBody().getPosition().x-sprite.getWidth()/2, signBody.getBody().getPosition().y-sprite.getHeight()/2);
        }else {
        position=new Vector2(signBody.getBody().getPosition().x, signBody.getBody().getPosition().y);
        }
       if(openLog){
           if(logicController.getScreenType() == FreeWorld.class) {
               logicController.resetBoulders();
           }
        }
    }

    /**
     * Sets the open log.
     *
     * @param var the new open log
     */
    public void setOpenLog(boolean var) {
        openLog=var;
    }

    /**
     * Adds the sign id.
     *
     * @param id the id
     */
    public void addSignId(int id) {
        this.id=id;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the checks if is open.
     *
     * @return the checks if is open
     */
    public boolean getIsOpen() {
        return openLog;
    }

    /**
     * Draw.
     *
     * @param batch the batch
     */
    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }
}
