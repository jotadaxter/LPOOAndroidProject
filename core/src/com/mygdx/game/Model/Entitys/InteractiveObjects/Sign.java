package com.mygdx.game.Model.Entitys.InteractiveObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.InteractiveObjects.SignBody;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.FreeWorld;

/**
 * Created by Utilizador on 21-05-2017.
 */

public class Sign extends Sprite{
    private World world;
    private TextureRegion signTex;
    private SignBody signBody;
    private boolean openLog;
    private int id;
    private LogicController logicController;

    public Sign(LogicController logicController, Vector2 vec) {
        this.world= logicController.getWorld();
        this.logicController=logicController;
        signBody= new SignBody(world,this,vec);
        openLog=false;
        textureLoad();
        setPosition(vec.x,vec.y);
        setBounds(0,0,16* MyGame.PIXEL_TO_METER,16* MyGame.PIXEL_TO_METER);
        setRegion(signTex);
    }

    private void textureLoad() {
        signTex = new TextureRegion(logicController.getGame().getAssetManager().get("Game/sign.png", Texture.class), 0,0,15,16);
    }

    public void update(){
        setPosition(signBody.getBody().getPosition().x-getWidth()/2, signBody.getBody().getPosition().y-getHeight()/2);
       if(openLog){
           if(logicController.getScreenType() == FreeWorld.class) {
               logicController.resetBoulders();
           }
        }
    }

    public void setOpenLog(boolean var) {
        openLog=var;
    }

    public void addSignId(int id) {
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public boolean getIsOpen() {
        return openLog;
    }
}
