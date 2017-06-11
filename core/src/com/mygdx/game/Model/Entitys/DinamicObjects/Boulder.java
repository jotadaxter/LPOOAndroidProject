package com.mygdx.game.Model.Entitys.DinamicObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.DinamicObjects.BoulderBody;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.MyGame;

/**
 * Created by Utilizador on 06-04-2017.
 */

public class Boulder{
    private LogicController logicController;
    private World world;
    private Vector2 position;
    private TextureRegion boulderFigure;
    private BoulderBody boulderBody;
    private Sprite sprite;

    public Boulder(LogicController logicController, Vector2 vec) {
        this.world= logicController.getWorld();
        this.logicController=logicController;
        position=vec;
        boulderBody= new BoulderBody(world,vec);
        sprite= new Sprite();
        if(!logicController.getGame().getIsTest()) {
            boulderFigure = new TextureRegion(logicController.getGame().getAssetManager().get("Game/boulder.png", Texture.class), 0, 0, 16, 16);
            sprite.setPosition(vec.x, vec.y);
            sprite.setBounds(0, 0, 16 * MyGame.PIXEL_TO_METER, 16 * MyGame.PIXEL_TO_METER);
            sprite.setRegion(boulderFigure);
        }
    }
    public void update(){
        if(!logicController.getGame().getIsTest()) {
            sprite.setPosition(boulderBody.getBody().getPosition().x-sprite.getWidth()/2, boulderBody.getBody().getPosition().y-sprite.getHeight()/2);
        }else {
            position=new Vector2(boulderBody.getBody().getPosition().x, boulderBody.getBody().getPosition().y);
        }
    }

    public BoulderBody getBoulderBody() {
        return boulderBody;
    }

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }
}
