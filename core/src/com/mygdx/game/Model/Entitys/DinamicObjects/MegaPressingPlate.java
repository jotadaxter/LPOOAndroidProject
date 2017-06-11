package com.mygdx.game.Model.Entitys.DinamicObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.DinamicObjects.MegaPressingPlateBody;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.MyGame;

/**
 * Created by Utilizador on 20-05-2017.
 */

public class MegaPressingPlate{
    
    /** The world. */
    private World world;
    
    /** The ispressed. */
    private int ispressed;//0 - false, >=2 - true
    
    /** The press and hold. */
    private boolean press_and_hold;
    
    /** The pressed tex. */
    private TextureRegion pressedTex;
    
    /** The notpressed tex. */
    private TextureRegion notpressedTex;
    
    /** The mega pressing plate body. */
    private MegaPressingPlateBody megaPressingPlateBody;
    
    /** The sound 1. */
    private Sound sound1;
    
    /** The sound 2. */
    private Sound sound2;
    
    /** The position. */
    private Vector2 position;
    
    /** The sprite. */
    private Sprite sprite;
    
    /** The logic controller. */
    private LogicController logicController;

    /**
     * Instantiates a new mega pressing plate.
     *
     * @param logicController the logic controller
     * @param vec the vec
     */
    public MegaPressingPlate(LogicController logicController, Vector2 vec) {
        this.world= logicController.getWorld();
        ispressed=0;
        press_and_hold=true;
        this.logicController=logicController;
        position=vec;
        sprite= new Sprite();
        megaPressingPlateBody= new MegaPressingPlateBody(world,this,vec);
        if(!logicController.getGame().getIsTest()) {
            textureLoad(logicController.getGame());
            sprite.setPosition(vec.x, vec.y);
            sprite.setBounds(0, 0, 64 * MyGame.PIXEL_TO_METER, 64 * MyGame.PIXEL_TO_METER);
            sprite.setRegion(notpressedTex);
        }
        sound1=  Gdx.audio.newSound(Gdx.files.internal("Sounds/pressing_plate_on.wav"));
        sound2=  Gdx.audio.newSound(Gdx.files.internal("Sounds/lever.wav"));
    }

    /**
     * Texture load.
     *
     * @param game the game
     */
    public void textureLoad(MyGame game){
        pressedTex = new TextureRegion(game.getAssetManager().get("Game/mega_pressing_plates.png", Texture.class), 64,0,64,64);
        notpressedTex = new TextureRegion(game.getAssetManager().get("Game/mega_pressing_plates.png", Texture.class), 0,0,64,64);
    }

    /**
     * Update.
     *
     * @param dt the dt
     */
    public void update(float dt){
        if(!logicController.getGame().getIsTest()) {
            sprite.setRegion(getFrame(dt));
            sprite.setPosition(megaPressingPlateBody.getBody().getPosition().x-sprite.getWidth()/2, megaPressingPlateBody.getBody().getPosition().y-sprite.getHeight()/2);
        }else {
            position=new Vector2(megaPressingPlateBody.getBody().getPosition().x, megaPressingPlateBody.getBody().getPosition().y);
        }
    }

    /**
     * Checks if is pressed.
     *
     * @return the int
     */
    public int isPressed() {
        return ispressed;
    }

    /**
     * Dec is pressed.
     */
    public void decIsPressed() {
        ispressed--;
        if(isPressed()<2){
            sound2.play(MyGame.SOUND_VOLUME);
        }
    }

    /**
     * Inc is pressed.
     */
    public void incIsPressed() {
        ispressed++;
        if(isPressed()>=2){
            sound1.play(MyGame.SOUND_VOLUME);
        }
    }

    /**
     * Gets the pressed tex.
     *
     * @return the pressed tex
     */
    public TextureRegion getPressedTex(){
        return pressedTex;
    }

    /**
     * Gets the not pressed tex.
     *
     * @return the not pressed tex
     */
    public TextureRegion getNotPressedTex(){
        return notpressedTex;
    }

    /**
     * Gets the frame.
     *
     * @param dt the dt
     * @return the frame
     */
    public TextureRegion getFrame(float dt) {
        TextureRegion region;
        if(isPressed()>=2) {
            region=getPressedTex();
        }else{
            region=getNotPressedTex();
        }
        return region;
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