package com.mygdx.game.Model.Entitys.DinamicObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.DinamicObjects.MegaPressingPlateBody;
import com.mygdx.game.Controller.Entitys.DinamicObjects.PressingPlateBody;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Utilizador on 20-05-2017.
 */

public class MegaPressingPlate extends Sprite {
    private World world;
    private int ispressed;//0 - false, >=2 - true
    private boolean press_and_hold;
    private TextureRegion pressedTex;
    private TextureRegion notpressedTex;
    private MegaPressingPlateBody megaPressingPlateBody;
    private Sound sound1;
    private Sound sound2;

    public MegaPressingPlate(LogicController logicController, Vector2 vec) {
        this.world=logicController.world;
        ispressed=0;
        press_and_hold=true;
        megaPressingPlateBody= new MegaPressingPlateBody(world,this,vec);
        textureLoad(logicController.game);
        setPosition(vec.x,vec.y);
        setBounds(0,0,64* MyGame.PIXEL_TO_METER,64* MyGame.PIXEL_TO_METER);
        setRegion(notpressedTex);
        sound1=  Gdx.audio.newSound(Gdx.files.internal("Sounds/pressing_plate_on.wav"));
        sound2=  Gdx.audio.newSound(Gdx.files.internal("Sounds/lever.wav"));
    }

    public void textureLoad(MyGame game){
        pressedTex = new TextureRegion(game.getAssetManager().get("Game/mega_pressing_plates.png", Texture.class), 64,0,64,64);
        notpressedTex = new TextureRegion(game.getAssetManager().get("Game/mega_pressing_plates.png", Texture.class), 0,0,64,64);
    }

    public void update(float dt){
        setPosition(megaPressingPlateBody.getBody().getPosition().x-getWidth()/2, megaPressingPlateBody.getBody().getPosition().y-getHeight()/2);
        setRegion(getFrame(dt));
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
}