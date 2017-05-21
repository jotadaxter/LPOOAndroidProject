package com.mygdx.game.Model.Entitys.DinamicObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.DinamicObjects.MovingPlatformBody;
import com.mygdx.game.Controller.Entitys.DinamicObjects.PressingPlateBody;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Utilizador on 20-05-2017.
 */

public class MovingPlatform extends Sprite {
    private World world;
    private TextureRegion platformTex;
    private MovingPlatformBody platformBody;

    public MovingPlatform(GameScreen screen, int x, int y) {
        this.world=screen.getWorld();
        platformBody= new MovingPlatformBody(world,this,x,y);
        platformTex = new TextureRegion(new Texture("moving_platform.png"), 0,0,32,32);
        setPosition(x,y);
        setBounds(0,0,32* MyGame.PIXEL_TO_METER,32* MyGame.PIXEL_TO_METER);
        setRegion(platformTex);
    }

    public void update(float dt){
        setPosition(platformBody.getBody().getPosition().x-getWidth()/2, platformBody.getBody().getPosition().y-getHeight()/2);
    }

}
