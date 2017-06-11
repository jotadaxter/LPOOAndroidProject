package com.mygdx.game.Model.Entitys.DinamicObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Controller.Entitys.DinamicObjects.FireGroundBody;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Utilizador on 20-05-2017.
 */

public class FireGround extends Sprite{
    private World world;
    private Animation<TextureRegion> fireAnimation;
    private FireGroundBody fireGroundBody;
    private float fire_timer;

    public FireGround(LogicController logicController, Vector2 vec) {
        this.world=logicController.world;
        fire_timer=0;
        fireGroundBody= new FireGroundBody(world,this,vec);
        loadAnimation(logicController.game);
        setPosition(vec.x,vec.y);
    }

    private void loadAnimation(MyGame game) {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(game.getAssetManager().get("Game/fire.png", Texture.class), i * 16, 0, 16, 16));
        }
        fireAnimation = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        setBounds(0,0,16* MyGame.PIXEL_TO_METER,16* MyGame.PIXEL_TO_METER);
    }

    public void update(float dt){
        setPosition(fireGroundBody.getBody().getPosition().x-getWidth()/2, fireGroundBody.getBody().getPosition().y-getHeight()/2);
        setRegion(getFrame(dt));
    }

    private TextureRegion getFrame(float dt) {
        TextureRegion region = new TextureRegion(fireAnimation.getKeyFrame(fire_timer, true));
        fire_timer+=(dt);
        return region;
    }
}
