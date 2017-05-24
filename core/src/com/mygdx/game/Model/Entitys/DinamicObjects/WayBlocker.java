package com.mygdx.game.Model.Entitys.DinamicObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.DinamicObjects.SpikesBody;
import com.mygdx.game.Controller.Entitys.DinamicObjects.WayBlockerBody;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Utilizador on 17-05-2017.
 */

public class WayBlocker extends Sprite{
    private World world;
    private TextureRegion blockFigure;
    private boolean toDestroy;
    private boolean destroyed;
    private WayBlockerBody wayBlockerBody;

    public WayBlocker(GameScreen screen, int x, int y) {
        this.world=screen.getWorld();
        wayBlockerBody= new WayBlockerBody(world,this,x,y);
        destroyed=false;
        toDestroy=false;
        blockFigure = new TextureRegion(screen.getGame().assetManager.get("Game/way_blocker.png", Texture.class));
        setPosition(x,y);
        setBounds(0,0,16* MyGame.PIXEL_TO_METER,16* MyGame.PIXEL_TO_METER);
        setRegion(blockFigure);
    }

    public void destroy(){
        toDestroy=true;
        Gdx.app.log("destroyed","");
    }

    public void update(float dt){
        if(toDestroy && !destroyed){
            world.destroyBody(wayBlockerBody.getBody());
            destroyed=true;
        }
        setPosition(wayBlockerBody.getBody().getPosition().x-getWidth()/2, wayBlockerBody.getBody().getPosition().y-getHeight()/2);
    }

    @Override
    public void draw(Batch batch) {
        if(!destroyed)
            super.draw(batch);
    }
}
