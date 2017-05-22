package com.mygdx.game.Model.Entitys.InteractiveObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.InteractiveObjects.ChestBody;
import com.mygdx.game.Controller.Entitys.InteractiveObjects.SignBody;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

import java.util.Random;

/**
 * Created by Utilizador on 21-05-2017.
 */

public class Sign extends Sprite{
    private GameScreen screen;
    private World world;
    private TextureRegion signTex;
    private SignBody signBody;
    private boolean openLog;
    private int id;

    public Sign(GameScreen screen, int x, int y) {
        this.world=screen.getWorld();
        this.screen=screen;
        signBody= new SignBody(world,this,x,y);
        openLog=false;
        textureLoad();
        setPosition(x,y);
        setBounds(0,0,16* MyGame.PIXEL_TO_METER,16* MyGame.PIXEL_TO_METER);
        setRegion(signTex);
    }

    private void textureLoad() {
        signTex = new TextureRegion(new Texture("sign.png"), 0,0,15,16);
    }

    public void update(float dt){
        setPosition(signBody.getBody().getPosition().x-getWidth()/2, signBody.getBody().getPosition().y-getHeight()/2);
        if(openLog){
            logDisplay();
            System.out.println("Log display on");
        }
        else System.out.println("Log display off");

    }

    private void logDisplay() {
        System.out.println("Log display on");
    }

    public void setOpenLog(boolean var) {
        openLog=var;
    }

    public boolean isOpenLog() {
        return openLog;
    }

    public void addSignId(int id) {
        this.id=id;
    }

    public int getId() {
        return id;
    }

}
