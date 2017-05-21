package com.mygdx.game.Model.Entitys.DinamicObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.DinamicObjects.MegaPressingPlateBody;
import com.mygdx.game.Controller.Entitys.DinamicObjects.PressingPlateBody;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Utilizador on 20-05-2017.
 */

public class MegaPressingPlate extends Sprite {
    private World world;
    private int ispressed;//0 - false, >=2 - true
    private boolean press_and_hold;//indica se é necessário deixar algum peso em cima da placa para q funcione
    private TextureRegion pressedTex;
    private TextureRegion notpressedTex;
    private MegaPressingPlateBody megaPressingPlateBody;

    public MegaPressingPlate(GameScreen screen, int x, int y) {
        this.world=screen.getWorld();
        ispressed=0;
        press_and_hold=true;
        megaPressingPlateBody= new MegaPressingPlateBody(world,this,x,y);

        textureLoad();

        setPosition(x,y);
        setBounds(0,0,64* MyGame.PIXEL_TO_METER,64* MyGame.PIXEL_TO_METER);
        setRegion(notpressedTex);
    }

    public void textureLoad(){
        pressedTex = new TextureRegion(new Texture("mega_pressing_plates.png"), 64,0,64,64);
        notpressedTex = new TextureRegion(new Texture("mega_pressing_plates.png"), 0,0,64,64);
    }

    public void update(float dt, GameScreen screen){
        setPosition(megaPressingPlateBody.getBody().getPosition().x-getWidth()/2, megaPressingPlateBody.getBody().getPosition().y-getHeight()/2);
        setRegion(megaPressingPlateBody.getFrame(this,dt));
    }

    public int isPressed() {
        return ispressed;
    }

    public void decIsPressed() {
        ispressed--;
    }

    public void incIsPressed() {
        ispressed++;
    }

    public TextureRegion getPressedTex(){
        return pressedTex;
    }

    public TextureRegion getNotPressedTex(){
        return notpressedTex;
    }

    public boolean isPressAndHold(){
        return press_and_hold;
    }

    public void setPressAndHold(boolean val){
        press_and_hold=val;
    }
}