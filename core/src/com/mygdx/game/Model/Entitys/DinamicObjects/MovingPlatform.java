package com.mygdx.game.Model.Entitys.DinamicObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
    public static final float P1_X = 45 * 16;//17;
    public static final float P1_Y = 23 * 16;//3;

    public static final float P2_X = 30 * 16;
    public static final float P2_Y = 23 * 16;

    public static final float P3_X = 30 * 16;
    public static final float P3_Y = 31 * 16;

    public static final float P4_X = 45 * 16;
    public static final float P4_Y = 31 * 16;
    private World world;
    private TextureRegion platformTex;
    private MovingPlatformBody platformBody;
    private int id;
    private Sound sound;
    private int soundTimer;

    public MovingPlatform(GameScreen screen, int x, int y) {
        this.world = screen.getWorld();
        id = 0;
        setPosition(x, y);
        sound = Gdx.audio.newSound(Gdx.files.internal("Sounds/moving_platform.wav"));
        soundTimer=0;
        platformBody = new MovingPlatformBody(world, this, x, y);
        platformTex = new TextureRegion(screen.getGame().assetManager.get("Game/moving_platform.png", Texture.class), 0, 0, 32, 32);
        setBounds(0, 0, 32 * MyGame.PIXEL_TO_METER, 32 * MyGame.PIXEL_TO_METER);
        setRegion(platformTex);
    }

    public void update(float dt) {
        if(soundTimer>6){
            sound.play();
            soundTimer=0;
        }
        if (platformBody.getBody().getPosition().y <= 23 && platformBody.getBody().getPosition().x >= 31) {
            platformBody.getBody().setLinearVelocity(new Vector2(-MyGame.PLATFORM_VELOCITY * dt, 0));
        } else if (platformBody.getBody().getPosition().x <= 31 && platformBody.getBody().getPosition().y <= 31) {
            platformBody.getBody().setLinearVelocity(new Vector2(0, MyGame.PLATFORM_VELOCITY * dt));
        } else if (platformBody.getBody().getPosition().y >= 31 && platformBody.getBody().getPosition().x <= 45) {
            platformBody.getBody().setLinearVelocity(new Vector2(MyGame.PLATFORM_VELOCITY * dt, 0));
        } else if (platformBody.getBody().getPosition().x >= 45 && platformBody.getBody().getPosition().y >= 23) {
            platformBody.getBody().setLinearVelocity(new Vector2(0, -MyGame.PLATFORM_VELOCITY * dt));
        }
        setPosition(platformBody.getBody().getPosition().x - getWidth() / 2, platformBody.getBody().getPosition().y - getHeight() / 2);
        soundTimer+=dt;
    }

    public int getId() {
        return id;
    }

    public void setId(int n) {
        id = n;
    }

    public MovingPlatformBody getPlatformBody() {
        return platformBody;

    }
}
