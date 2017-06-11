package com.mygdx.game.Model.Entitys.DinamicObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Controller.Entitys.DinamicObjects.FireGroundBody;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.MyGame;

/**
 * Created by Utilizador on 20-05-2017.
 */

public class FireGround{
    private World world;
    private Vector2 position;
    private Sprite sprite;
    private LogicController logicController;
    private Animation<TextureRegion> fireAnimation;
    private FireGroundBody fireGroundBody;
    private float fire_timer;

    public FireGround(LogicController logicController, Vector2 vec) {
        this.world= logicController.getWorld();
        fire_timer=0;
        position=vec;
        this.logicController=logicController;
        sprite=new Sprite();
        fireGroundBody= new FireGroundBody(world,this,vec);
        if(!logicController.getGame().getIsTest()) {
            loadAnimation(logicController.getGame());
            sprite.setPosition(vec.x, vec.y);
        }
    }

    private void loadAnimation(MyGame game) {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(game.getAssetManager().get("Game/fire.png", Texture.class), i * 16, 0, 16, 16));
        }
        fireAnimation = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        sprite.setBounds(0,0,16* MyGame.PIXEL_TO_METER,16* MyGame.PIXEL_TO_METER);
    }

    public void update(float dt){
        if(!logicController.getGame().getIsTest()) {
            sprite.setRegion(getFrame(dt));
            sprite.setPosition(fireGroundBody.getBody().getPosition().x-sprite.getWidth()/2, fireGroundBody.getBody().getPosition().y-sprite.getHeight()/2);
        }else {
            position=new Vector2(fireGroundBody.getBody().getPosition().x, fireGroundBody.getBody().getPosition().y);
        }
    }

    private TextureRegion getFrame(float dt) {
        TextureRegion region = new TextureRegion(fireAnimation.getKeyFrame(fire_timer, true));
        fire_timer+=(dt);
        return region;
    }
    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }


}
