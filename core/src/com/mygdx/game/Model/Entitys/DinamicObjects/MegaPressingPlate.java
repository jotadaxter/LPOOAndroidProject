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
    private World world;
    private int ispressed;//0 - false, >=2 - true
    private boolean press_and_hold;
    private TextureRegion pressedTex;
    private TextureRegion notpressedTex;
    private MegaPressingPlateBody megaPressingPlateBody;
    private Sound sound1;
    private Sound sound2;
    private Vector2 position;
    private Sprite sprite;
    private LogicController logicController;

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

    public void textureLoad(MyGame game){
        pressedTex = new TextureRegion(game.getAssetManager().get("Game/mega_pressing_plates.png", Texture.class), 64,0,64,64);
        notpressedTex = new TextureRegion(game.getAssetManager().get("Game/mega_pressing_plates.png", Texture.class), 0,0,64,64);
    }

    public void update(float dt){
        if(!logicController.getGame().getIsTest()) {
            sprite.setRegion(getFrame(dt));
            sprite.setPosition(megaPressingPlateBody.getBody().getPosition().x-sprite.getWidth()/2, megaPressingPlateBody.getBody().getPosition().y-sprite.getHeight()/2);
        }else {
            position=new Vector2(megaPressingPlateBody.getBody().getPosition().x, megaPressingPlateBody.getBody().getPosition().y);
        }
    }

    public int isPressed() {
        return ispressed;
    }

    public void decIsPressed() {
        ispressed--;
        if(isPressed()<2){
            sound2.play(MyGame.SOUND_VOLUME);
        }
    }

    public void incIsPressed() {
        ispressed++;
        if(isPressed()>=2){
            sound1.play(MyGame.SOUND_VOLUME);
        }
    }

    public TextureRegion getPressedTex(){
        return pressedTex;
    }

    public TextureRegion getNotPressedTex(){
        return notpressedTex;
    }

    public TextureRegion getFrame(float dt) {
        TextureRegion region;
        if(isPressed()>=2) {
            region=getPressedTex();
        }else{
            region=getNotPressedTex();
        }
        return region;
    }
    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }

}