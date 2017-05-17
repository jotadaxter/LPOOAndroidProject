package com.mygdx.game.Model.Entitys.DinamicObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controller.Entitys.DinamicObjects.PressingPlateBody;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.GameScreen;

import java.util.ArrayList;

/**
 * Created by Jotadaxter on 02/05/2017.
 */

public class PressingPlate extends Sprite {
    private World world;
    private int ispressed;//0 - false, >=1 - true
    private boolean press_and_hold;//indica se é necessário deixar algum peso em cima da placa para q funcione

    private TextureRegion pressedTex;
    private TextureRegion notpressedTex;

    private ArrayList<PressingPlate> connections;

    private PressingPlateBody pressingPlateBody;

    public PressingPlate(GameScreen screen, int x, int y) {
        super(screen.getAtlas().findRegion("pressing_plate_not_pressed"));
        this.world=screen.getWorld();
        ispressed=0;
        press_and_hold=true;
        pressingPlateBody= new PressingPlateBody(world,this,x,y);

        connections= new ArrayList<PressingPlate>();

        pressedTex = new TextureRegion(screen.getAtlas().findRegion("pressing_plate_pressed"), 0,0,16,16);
        notpressedTex = new TextureRegion(screen.getAtlas().findRegion("pressing_plate_not_pressed"), 0,0,16,16);
        setPosition(x,y);
        setBounds(0,0,16* MyGame.PIXEL_TO_METER,16* MyGame.PIXEL_TO_METER);
        setRegion(notpressedTex);
    }

    public void update(float dt, GameScreen screen){
        setPosition(pressingPlateBody.getBody().getPosition().x-getWidth()/2, pressingPlateBody.getBody().getPosition().y-getHeight()/2);
        setRegion(pressingPlateBody.getFrame(this,dt));
        action(dt, screen);
    }

    //Efetua a acao designada pelo pressionar da placa
    public void action(float dt, GameScreen screen){
      /* if(press_and_hold){
           if(ispressed)
               app.log("Placa premida - com acao\n","");
           else{
               app.log("Placa não premida - sem acao\n","");
           }
       }
       else{
           if(ispressed)
               app.log("(sem Hold) Placa premida - com acao\n","");
       }
*/
    }

    public int isPressed() {
        return ispressed;
    }

    public void decIsPressed() {
       ispressed--;
    }

    public void incIsPressed() {
        ispressed++;
    }

    public TextureRegion getPressedTex(){
        return pressedTex;
    }

    public TextureRegion getNotPressedTex(){
        return notpressedTex;
    }

    public boolean isPressAndHold(){
        return press_and_hold;
    }

    public void setPressAndHold(boolean val){
        press_and_hold=val;
    }

    public void addConnection(PressingPlate connection){
        connections.add(connection);
    }

    public int nConnections(){
        return connections.size();
    }
}
