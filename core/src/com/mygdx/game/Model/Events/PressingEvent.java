package com.mygdx.game.Model.Events;

import com.mygdx.game.Controller.Entitys.TileObjects.Door;
import com.mygdx.game.Controller.Entitys.TileObjects.Obstacle;
import com.mygdx.game.Model.Entitys.DinamicObjects.PressingPlate;
import com.mygdx.game.Model.Entitys.Items.Key;
import com.mygdx.game.View.GameScreens.FreeWorld;
import com.mygdx.game.View.GameScreens.GameScreen;

import java.util.ArrayList;

/**
 * Created by Utilizador on 17-05-2017.
 */

public class PressingEvent {
    private ArrayList<PressingPlate> plates;
    private FreeWorld screen;

    public PressingEvent(ArrayList<PressingPlate> plates,FreeWorld screen){
        this.plates=plates;
        this.screen=screen;
    }

    public boolean condition(){
        for(int i =0; i<plates.size();i++){
            if(plates.get(i).isPressed()==0){
                return false;
            }
        }
        return true;
    }

    public void update(float dt){
        if(condition())
            screen.d1blck=false;
    }

}
