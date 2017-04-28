package com.mygdx.game.Model.Entitys.DinamicObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.DinamicObjects.BoulderBody;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.Screens.MyScreen;

/**
 * Created by Utilizador on 06-04-2017.
 */

public class Boulder extends Sprite{
    public static final int POSX = 200;
    public static final int POSY = 200;

    private World world;
    private TextureRegion boulderFigure;

    private BoulderBody boulderBody;

    public Boulder(MyScreen screen) {
        super(screen.getAtlas().findRegion("boulder"));
        this.world=screen.getWorld();

        boulderBody= new BoulderBody(world,POSX,POSY);

        boulderFigure = new TextureRegion(screen.getAtlas().findRegion("boulder"), 0,0,16,16);
        setPosition(POSX,POSY);
        setBounds(0,0,16* MyGame.PIXEL_TO_METER,16* MyGame.PIXEL_TO_METER);
        setRegion(boulderFigure);
    }
    public void update(float dt){
        setPosition(boulderBody.getBody().getPosition().x-getWidth()/2, boulderBody.getBody().getPosition().y-getHeight()/2);
    }

}
