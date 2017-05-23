package com.mygdx.game.Model.Entitys.DinamicObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.DinamicObjects.MovingPlatformBody;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Utilizador on 20-05-2017.
 */

public class MovingPlatform extends Sprite {
    /*public static final int P1_X = 272;
    public static final int P1_Y = 48;

    public static final int P2_X = 48;
    public static final int P2_Y = 48;

    public static final int P3_X = 48;
    public static final int P3_Y = 176;

    public static final int P4_X = 272;
    public static final int P4_Y = 176;*/

    public static final int P1_X = 17;
    public static final int P1_Y = 3;

    public static final int P2_X = 3;
    public static final int P2_Y = 3;

    public static final int P3_X = 3;
    public static final int P3_Y = 11;

    public static final int P4_X = 17;
    public static final int P4_Y = 11;
    private World world;
    private TextureRegion platformTex;
    private MovingPlatformBody platformBody;
    private int id;
    public float lastPosX;
    public float lastPosY;
    public float currentPosX;
    public float currentPosY;

    public MovingPlatform(GameScreen screen, int x, int y) {
        this.world=screen.getWorld();
        // Setup Waypoints
        id=0;
        setPosition(x,y);
        lastPosX=x;
        lastPosY=y;
        currentPosX=x;
        currentPosY=y;
        platformBody= new MovingPlatformBody(world,this,x,y);
        platformTex = new TextureRegion(new Texture("moving_platform.png"), 0,0,32,32);

        setBounds(0,0,32* MyGame.PIXEL_TO_METER,32* MyGame.PIXEL_TO_METER);
        setRegion(platformTex);
    }

    public void update(float dt){
        lastPosX=currentPosX;
        lastPosY=currentPosY;
        if(platformBody.getBody().getPosition().y<=3.0f && platformBody.getBody().getPosition().x>=3.0f){
            platformBody.getBody().setLinearVelocity(new Vector2(-MyGame.PLATFORM_VELOCITY*dt,0));
        }
        else if(platformBody.getBody().getPosition().x<=3.0f && platformBody.getBody().getPosition().y<=11.0f){
            platformBody.getBody().setLinearVelocity(new Vector2(0,MyGame.PLATFORM_VELOCITY*dt));
        }
        else if(platformBody.getBody().getPosition().y>=11.0f && platformBody.getBody().getPosition().x<=17){
            platformBody.getBody().setLinearVelocity(new Vector2(MyGame.PLATFORM_VELOCITY*dt,0));
        }
        else if(platformBody.getBody().getPosition().x>=17.0f && platformBody.getBody().getPosition().y>=11.0f){
            platformBody.getBody().setLinearVelocity(new Vector2(0,-MyGame.PLATFORM_VELOCITY*dt));
        }
        setPosition(platformBody.getBody().getPosition().x-getWidth()/2, platformBody.getBody().getPosition().y-getHeight()/2);
        currentPosX=platformBody.getBody().getPosition().x;
        currentPosY=platformBody.getBody().getPosition().y;
    }

    public int getId() {
        return id;
    }

    public void setId(int n) {
        id=n;
    }

    public MovingPlatformBody getPlatformBody() {
        return platformBody;
    }
    public float getDeltaX(){
        return currentPosX-lastPosX;
    }

    public float getDeltaY(){
        return currentPosY-lastPosY;
    }
}
