package com.mygdx.game.Model.Entitys.DinamicObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.DinamicObjects.BoulderBody;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.MyGame;

// TODO: Auto-generated Javadoc
/**
 * Created by Utilizador on 06-04-2017.
 */

public class Boulder{
    
    /** The world. */
    private World world;
    
    /** The boulder figure. */
    private TextureRegion boulderFigure;
    
    /** The boulder body. */
    private BoulderBody boulderBody;
    
    /** The sprite. */
    private Sprite sprite;
    
    /** The logic controller. */
    private LogicController logicController;
    
    /** The position. */
    private Vector2 position;

    /**
     * Instantiates a new boulder.
     *
     * @param logicController the logic controller
     * @param vec the vec
     */
    public Boulder(LogicController logicController, Vector2 vec) {
        this.world= logicController.getWorld();
        this.logicController=logicController;
        position=vec;
        sprite= new Sprite();
        boulderBody= new BoulderBody(world,vec);
        if(!logicController.getGame().getIsTest()) {
            boulderFigure = new TextureRegion(logicController.getGame().getAssetManager().get("Game/boulder.png", Texture.class), 0, 0, 16, 16);
            sprite.setPosition(vec.x, vec.y);
            sprite.setBounds(0, 0, 16 * MyGame.PIXEL_TO_METER, 16 * MyGame.PIXEL_TO_METER);
            sprite.setRegion(boulderFigure);
        }
    }
    
    /**
     * Update.
     */
    public void update(){
        if(!logicController.getGame().getIsTest()) {
            sprite.setPosition(boulderBody.getBody().getPosition().x-sprite.getWidth()/2, boulderBody.getBody().getPosition().y-sprite.getHeight()/2);
        }else {
            position=new Vector2(boulderBody.getBody().getPosition().x, boulderBody.getBody().getPosition().y);
        }
    }

    /**
     * Gets the boulder body.
     *
     * @return the boulder body
     */
    public BoulderBody getBoulderBody() {
        return boulderBody;
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
