package com.mygdx.game.Model.Entitys.DinamicObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.DinamicObjects.WayBlockerBody;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

/**
 * Created by Utilizador on 17-05-2017.
 */

public class WayBlocker extends Sprite{
    private World world;
    private TextureRegion blockFigure;
    private TextureRegion blockFigure2;
    private boolean toDestroy;
    private boolean destroyed;
    private WayBlockerBody wayBlockerBody;
    private Sound sound;

    public WayBlocker(GameScreen screen, Vector2 vec, int texChoose) {
        this.world=screen.getWorld();
        wayBlockerBody= new WayBlockerBody(world,this,vec);
        destroyed=false;
        toDestroy=false;
        if(texChoose==0){
            blockFigure = new TextureRegion(screen.getGame().assetManager.get("Game/way_blocker.png", Texture.class));
            setRegion(blockFigure);
        }
        else {
            blockFigure2 = new TextureRegion(screen.getGame().assetManager.get("Game/way_blocker2.png", Texture.class));
            setRegion(blockFigure2);
        }
        setPosition(vec.x,vec.y);
        setBounds(0,0,16* MyGame.PIXEL_TO_METER,16* MyGame.PIXEL_TO_METER);
        sound=Gdx.audio.newSound(Gdx.files.internal("Sounds/secret.wav"));
    }

    public void destroy(){
        toDestroy=true;
        Gdx.app.log("destroyed","");
    }

    public void update(float dt){
        if(toDestroy && !destroyed){
            sound.play();
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
