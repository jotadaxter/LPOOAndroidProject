package com.mygdx.game.Model.Entitys.DinamicObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.DinamicObjects.PressingPlateBody;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

import java.util.ArrayList;

/**
 * Created by Jotadaxter on 02/05/2017.
 */

public class PressingPlate extends Sprite {
    private World world;
    private int isPressed;//0 - false, >=1 - true
    private boolean press_and_hold;//indica se é necessário deixar algum peso em cima da placa para q funcione
    private TextureRegion pressedTex;
    private TextureRegion notPressedTex;
    private PressingPlateBody pressingPlateBody;

    public PressingPlate(GameScreen screen, int x, int y) {
        super(screen.getAtlas().findRegion("pressing_plate_not_pressed"));
        this.world=screen.getWorld();
        isPressed=0;
        press_and_hold=true;
        pressingPlateBody= new PressingPlateBody(world,this,x,y);
        pressedTex = new TextureRegion(screen.getAtlas().findRegion("pressing_plate_pressed"), 0,0,16,16);
        notPressedTex = new TextureRegion(screen.getAtlas().findRegion("pressing_plate_not_pressed"), 0,0,16,16);
        setPosition(x,y);
        setBounds(0,0,16* MyGame.PIXEL_TO_METER,16* MyGame.PIXEL_TO_METER);
        setRegion(notPressedTex);
    }

    public void update(float dt, GameScreen screen){
        setPosition(pressingPlateBody.getBody().getPosition().x-getWidth()/2, pressingPlateBody.getBody().getPosition().y-getHeight()/2);
        setRegion(pressingPlateBody.getFrame(this,dt));

    }

    public int isPressed() {
        return isPressed;
    }

    public void decIsPressed() {
        isPressed--;
    }

    public void incIsPressed() {
        isPressed++;
    }

    public TextureRegion getPressedTex(){
        return pressedTex;
    }

    public TextureRegion getNotPressedTex(){
        return notPressedTex;
    }

    public boolean isPressAndHold(){
        return press_and_hold;
    }

    public void setPressAndHold(boolean val){
        press_and_hold=val;
    }
}
