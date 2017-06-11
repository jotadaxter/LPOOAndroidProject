package com.mygdx.game.Model.Entitys.DinamicObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.DinamicObjects.WayBlockerBody;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.MyGame;

// TODO: Auto-generated Javadoc
/**
 * Created by Utilizador on 17-05-2017.
 */

public class WayBlocker{
    
    /** The world. */
    private World world;
    
    /** The block figure. */
    private TextureRegion blockFigure;
    
    /** The block figure 2. */
    private TextureRegion blockFigure2;
    
    /** The to destroy. */
    private boolean toDestroy;
    
    /** The destroyed. */
    private boolean destroyed;
    
    /** The way blocker body. */
    private WayBlockerBody wayBlockerBody;
    
    /** The sound. */
    private Sound sound;
    
    /** The position. */
    private Vector2 position;
    
    /** The sprite. */
    private Sprite sprite;
    
    /** The logic controller. */
    private LogicController logicController;

    /**
     * Instantiates a new way blocker.
     *
     * @param logicController the logic controller
     * @param vec the vec
     * @param texChoose the tex choose
     */
    public WayBlocker(LogicController logicController, Vector2 vec, int texChoose) {
        this.world= logicController.getWorld();
        wayBlockerBody= new WayBlockerBody(world,this,vec);
        destroyed=false;
        toDestroy=false;
        position=vec;
        this.logicController=logicController;
        sprite=new Sprite();
        if(!logicController.getGame().getIsTest()){
            if (texChoose == 0) {
                blockFigure = new TextureRegion(logicController.getGame().getAssetManager().get("Game/way_blocker.png", Texture.class));
                sprite.setRegion(blockFigure);
            } else {
                blockFigure2 = new TextureRegion(logicController.getGame().getAssetManager().get("Game/way_blocker2.png", Texture.class));
                sprite.setRegion(blockFigure2);
            }
            sprite.setPosition(vec.x, vec.y);
            sprite.setBounds(0, 0, 16 * MyGame.PIXEL_TO_METER, 16 * MyGame.PIXEL_TO_METER);
        }
        sound=Gdx.audio.newSound(Gdx.files.internal("Sounds/secret.wav"));
    }

    /**
     * Destroy.
     */
    public void destroy(){
        toDestroy=true;
        Gdx.app.log("destroyed","");
    }

    /**
     * Update.
     */
    public void update(){
        if(toDestroy && !destroyed){
            sound.play(MyGame.SOUND_VOLUME);
            world.destroyBody(wayBlockerBody.getBody());
            destroyed=true;
        }
        if(!logicController.getGame().getIsTest()) {
            sprite.setPosition(wayBlockerBody.getBody().getPosition().x-sprite.getWidth()/2, wayBlockerBody.getBody().getPosition().y-sprite.getHeight()/2);
        }else {
            position=new Vector2(wayBlockerBody.getBody().getPosition().x, wayBlockerBody.getBody().getPosition().y);
        }    }

    /**
     * Draw.
     *
     * @param batch the batch
     */
    public void draw(Batch batch) {
        if(!destroyed)
            sprite.draw(batch);
    }
}
