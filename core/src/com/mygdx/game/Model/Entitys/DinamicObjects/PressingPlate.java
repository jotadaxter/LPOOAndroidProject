package com.mygdx.game.Model.Entitys.DinamicObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.DinamicObjects.PressingPlateBody;
import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.MyGame;

/**
 * Created by Jotadaxter on 02/05/2017.
 */

public class PressingPlate {
    private World world;
    private int isPressed;//0 - false, >=1 - true
    private boolean press_and_hold;//indica se é necessário deixar algum peso em cima da placa para q funcione
    private TextureRegion pressedTex;
    private TextureRegion notPressedTex;
    private PressingPlateBody pressingPlateBody;
    private Sound sound1;
    private Sound sound2;
    private Vector2 position;
    private Sprite sprite;
    private LogicController logicController;

    public PressingPlate(LogicController logicController, Vector2 vec) {
        this.world= logicController.getWorld();
        isPressed=0;
        press_and_hold=true;
        position=vec;
        this.logicController=logicController;
        sprite=new Sprite();
        pressingPlateBody= new PressingPlateBody(world,this,vec);
        if(!logicController.getGame().getIsTest()) {
            pressedTex = new TextureRegion(logicController.getGame().getAssetManager().get("Game/pressing_plate_pressed.png", Texture.class), 0, 0, 16, 16);
            notPressedTex = new TextureRegion(logicController.getGame().getAssetManager().get("Game/pressing_plate_not_pressed.png", Texture.class), 0, 0, 16, 16);
            sprite.setPosition(vec.x, vec.y);
            sprite.setBounds(0, 0, 16 * MyGame.PIXEL_TO_METER, 16 * MyGame.PIXEL_TO_METER);
            sprite.setRegion(notPressedTex);
        }
        sound1=  Gdx.audio.newSound(Gdx.files.internal("Sounds/pressing_plate_on.wav"));
        sound2=  Gdx.audio.newSound(Gdx.files.internal("Sounds/lever.wav"));
    }

    public void update(float dt){
        if(!logicController.getGame().getIsTest()) {
            sprite.setRegion(pressingPlateBody.getFrame(this,dt));
            sprite.setPosition(pressingPlateBody.getBody().getPosition().x-sprite.getWidth()/2, pressingPlateBody.getBody().getPosition().y-sprite.getHeight()/2);
        }else {
            position=new Vector2(pressingPlateBody.getBody().getPosition().x, pressingPlateBody.getBody().getPosition().y);
        }
    }

    public int isPressed() {
        return isPressed;
    }

    public void decIsPressed() {
        isPressed--;
        if(isPressed()<1){
            sound2.play(MyGame.SOUND_VOLUME);
        }
    }

    public void incIsPressed() {
        isPressed++;
        if(isPressed()>=1){
            sound1.play(MyGame.SOUND_VOLUME);
        }
    }

    public TextureRegion getPressedTex(){
        return pressedTex;
    }

    public TextureRegion getNotPressedTex(){
        return notPressedTex;
    }

    public boolean isPressAndHold(){
        return press_and_hold;
    }

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }

}
