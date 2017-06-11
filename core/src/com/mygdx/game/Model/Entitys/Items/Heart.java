package com.mygdx.game.Model.Entitys.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Controller.Entitys.Items.HeartBody;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;
import com.mygdx.game.Model.Entitys.Hero.Hero;

/**
 * Created by Jotadaxter on 20/04/2017.
 */

public class Heart extends Item {
    private HeartBody heartBody;
    private Sound sound;

    public Heart(LogicController logicController, Vector2 vec) {
        super(logicController, vec);
        sound=  Gdx.audio.newSound(Gdx.files.internal("Sounds/get_heart.wav"));
        heartBody=new HeartBody(world,this, vec);
        setRegion(new TextureRegion(logicController.game.getAssetManager().get("Game/heart.png", Texture.class), 0,0,15,15));
    }

    @Override
    public void defineItem() {
        type="heart";
        setBounds(getX(),getY(), 15*MyGame.PIXEL_TO_METER,15*MyGame.PIXEL_TO_METER);
    }

    @Override
    public void use(Hero hero) {
        destroy();
    }

    @Override
    public void update(float dt, Hero hero) {
        if(toDestroy && !destroyed){
            sound.play(MyGame.SOUND_VOLUME);
            world.destroyBody(heartBody.getBody());
            destroyed=true;
            hero.addItem(this);
        }
        setPosition(heartBody.getBody().getPosition().x-getWidth()/2, heartBody.getBody().getPosition().y-getHeight()/2);
    }
}
