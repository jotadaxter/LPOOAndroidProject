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
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.MyGame;

/**
 * Created by Utilizador on 17-05-2017.
 */

public class WayBlocker{
    private World world;
    private TextureRegion blockFigure;
    private TextureRegion blockFigure2;
    private boolean toDestroy;
    private boolean destroyed;
    private WayBlockerBody wayBlockerBody;
    private Sound sound;
    private Vector2 position;
    private Sprite sprite;
    private LogicController logicController;

    public WayBlocker(LogicController logicController, Vector2 vec, int texChoose) {
        this.world= logicController.getWorld();
        wayBlockerBody= new WayBlockerBody(world,this,vec);
        destroyed=false;
        toDestroy=false;
        position=vec;
        this.logicController=logicController;
        sprite=new Sprite();
        if(!logicController.getGame().getIsTest()){
            if (texChoose == 0) {
                blockFigure = new TextureRegion(logicController.getGame().getAssetManager().get("Game/way_blocker.png", Texture.class));
                sprite.setRegion(blockFigure);
            } else {
                blockFigure2 = new TextureRegion(logicController.getGame().getAssetManager().get("Game/way_blocker2.png", Texture.class));
                sprite.setRegion(blockFigure2);
            }
            sprite.setPosition(vec.x, vec.y);
            sprite.setBounds(0, 0, 16 * MyGame.PIXEL_TO_METER, 16 * MyGame.PIXEL_TO_METER);
        }
        sound=Gdx.audio.newSound(Gdx.files.internal("Sounds/secret.wav"));
    }

    public void destroy(){
        toDestroy=true;
        Gdx.app.log("destroyed","");
    }

    public void update(){
        if(toDestroy && !destroyed){
            sound.play(MyGame.SOUND_VOLUME);
            world.destroyBody(wayBlockerBody.getBody());
            destroyed=true;
        }
        if(!logicController.getGame().getIsTest()) {
            sprite.setPosition(wayBlockerBody.getBody().getPosition().x-sprite.getWidth()/2, wayBlockerBody.getBody().getPosition().y-sprite.getHeight()/2);
        }else {
            position=new Vector2(wayBlockerBody.getBody().getPosition().x, wayBlockerBody.getBody().getPosition().y);
        }    }

    public void draw(Batch batch) {
        if(!destroyed)
            sprite.draw(batch);
    }
}
