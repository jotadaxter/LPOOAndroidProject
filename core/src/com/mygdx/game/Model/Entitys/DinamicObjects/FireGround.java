package com.mygdx.game.Model.Entitys.DinamicObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Controller.Entitys.DinamicObjects.FireGroundBody;
import com.mygdx.game.Controller.Entitys.DinamicObjects.SpikesBody;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Utilizador on 20-05-2017.
 */

public class FireGround extends Sprite{
    //public static final int POSX = 250;
    //public static final int POSY = 200;
    private World world;
    private Animation<TextureRegion> fireAnimation;
    private FireGroundBody fireGroundBody;
    private float fire_timer;

    public FireGround(GameScreen screen, int x, int y) {
        super(screen.getAtlas().findRegion("spikes"));
        this.world=screen.getWorld();
        fire_timer=0;
        fireGroundBody= new FireGroundBody(world,this,x,y);
        loadAnimation();
        setPosition(x,y);

    }

    private void loadAnimation() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(new Texture("fire.png"), i * 16, 0, 16, 16));
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
