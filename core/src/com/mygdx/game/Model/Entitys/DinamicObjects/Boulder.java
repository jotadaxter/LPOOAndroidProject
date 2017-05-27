package com.mygdx.game.Model.Entitys.DinamicObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.DinamicObjects.BoulderBody;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Utilizador on 06-04-2017.
 */

public class Boulder extends Sprite{
    private World world;
    private TextureRegion boulderFigure;
    private BoulderBody boulderBody;
    private Sound sound;

    public Boulder(GameScreen screen, float x, float y) {
        super(screen.getAtlas().findRegion("boulder"));
        this.world=screen.getWorld();
        boulderBody= new BoulderBody(world,this, x,y);
        sound=  Gdx.audio.newSound(Gdx.files.internal("Sounds/pushing_boulder.wav"));
        boulderFigure = new TextureRegion(screen.getAtlas().findRegion("boulder"), 0,0,16,16);
        setPosition(x,y);
        setBounds(0,0,16* MyGame.PIXEL_TO_METER,16* MyGame.PIXEL_TO_METER);
        setRegion(boulderFigure);
    }
    public void update(float dt){
       /* if(boulderBody.getBody().getLinearVelocity().x>0 || boulderBody.getBody().getLinearVelocity().y>0 ||
                (boulderBody.getBody().getLinearVelocity().x>0 && boulderBody.getBody().getLinearVelocity().y>0))
            sound.play();*/
        setPosition(boulderBody.getBody().getPosition().x-getWidth()/2, boulderBody.getBody().getPosition().y-getHeight()/2);
    }

    public BoulderBody getBoulderBody() {
        return boulderBody;
    }
}
