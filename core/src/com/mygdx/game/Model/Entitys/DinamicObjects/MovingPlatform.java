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
    private Vector2[] patrol;
    private int prevWaypoint = 0;
    private int nextWaypoint = 1;
    private int patrolDir;

    public MovingPlatform(GameScreen screen, int x, int y) {
        this.world=screen.getWorld();
        // Setup Waypoints
        patrol = new Vector2[6];
        patrol[0] = new Vector2(17,3);
        patrol[1] = new Vector2(3,3);
        patrol[2] = new Vector2(3,11);
        patrol[3] = new Vector2(17,11);
        patrolDir = 1;
        platformBody= new MovingPlatformBody(world,this,x,y);
        platformTex = new TextureRegion(new Texture("moving_platform.png"), 0,0,32,32);
        setPosition(x,y);
        setBounds(0,0,32* MyGame.PIXEL_TO_METER,32* MyGame.PIXEL_TO_METER);
        setRegion(platformTex);
    }

    public void update(float dt){
        //patrolApply(dt);

        if(platformBody.getBody().getPosition().y==3.0f && platformBody.getBody().getPosition().x>=3.0f){
            platformBody.getBody().setLinearVelocity(new Vector2(-MyGame.PLATFORM_VELOCITY*dt,0));
        }
        else if(platformBody.getBody().getPosition().x==3.0f && platformBody.getBody().getPosition().y<=11.0f){
            platformBody.getBody().setLinearVelocity(new Vector2(0,MyGame.PLATFORM_VELOCITY*dt));
        }
        else if(platformBody.getBody().getPosition().y==11.0f && platformBody.getBody().getPosition().x<=17){
            platformBody.getBody().setLinearVelocity(new Vector2(MyGame.PLATFORM_VELOCITY*dt,0));
        }
        else if(platformBody.getBody().getPosition().x==17.0f && platformBody.getBody().getPosition().y>=11.0f){
            platformBody.getBody().setLinearVelocity(new Vector2(0,-MyGame.PLATFORM_VELOCITY*dt));
        }
        System.out.println(platformBody.getBody().getPosition().x + " - " + platformBody.getBody().getPosition().y);
        setPosition(platformBody.getBody().getPosition().x-getWidth()/2, platformBody.getBody().getPosition().y-getHeight()/2);
    }

    private void patrolApply(float dt) {
        for (int i = 0; i < patrol.length; i++) {
            if (platformBody.getBody().getPosition().x == patrol[nextWaypoint].x && platformBody.getBody().getPosition().x == patrol[nextWaypoint].y) {

                prevWaypoint = nextWaypoint;

                if(patrolDir == 1){
                    nextWaypoint++;
                    if (nextWaypoint > patrol.length - 1){
                        nextWaypoint = 0;
                        prevWaypoint = patrol.length - 1;
                    }
                    break;
                }

                if(patrolDir == -1){
                    nextWaypoint--;
                    if (nextWaypoint < 0){
                        nextWaypoint = patrol.length-1;
                        prevWaypoint = 0;
                    }
                    break;
                }

            }
        }

        float prevX = patrol[prevWaypoint].x;
        float prevY = patrol[prevWaypoint].y;
        float nextX = patrol[nextWaypoint].x;
        float nextY = patrol[nextWaypoint].y;

        if (prevY > nextY)
            platformBody.getBody().setLinearVelocity(new Vector2(0,MyGame.PLATFORM_VELOCITY*dt));
        if (prevY < nextY)
            platformBody.getBody().setLinearVelocity(new Vector2(0,-MyGame.PLATFORM_VELOCITY*dt));
        if (prevX > nextX)
            platformBody.getBody().setLinearVelocity(new Vector2(-MyGame.PLATFORM_VELOCITY*dt,0));
        if (prevX < nextX)
            platformBody.getBody().setLinearVelocity(new Vector2(MyGame.PLATFORM_VELOCITY*dt,0));
    }

}
