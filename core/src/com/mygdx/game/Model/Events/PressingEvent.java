package com.mygdx.game.Model.Events;

import com.mygdx.game.Controller.Entitys.TileObjects.Door;
import com.mygdx.game.Controller.Entitys.TileObjects.Obstacle;
import com.mygdx.game.Model.Entitys.DinamicObjects.MegaPressingPlate;
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
    private ArrayList<MegaPressingPlate> megaPlates;
    private FreeWorld screen;

    public PressingEvent(ArrayList<PressingPlate> plates,FreeWorld screen, int bla){//o bla só serve para não haver erro em haver dois construtores...
        this.plates=plates;
        this.screen=screen;
        this.megaPlates=null;
    }

    public PressingEvent(ArrayList<MegaPressingPlate> megaPlates, FreeWorld screen){
        this.plates=null;
        this.screen=screen;
        this.megaPlates=megaPlates;
    }

    public boolean condition(){
        if(plates!=null) {
            for (int i = 0; i < plates.size(); i++) {
                if (plates.get(i).isPressed() == 0) {
                    return false;
                }
            }
            return true;
        }
        else{
            for (int i = 0; i < megaPlates.size(); i++) {
                if (megaPlates.get(i).isPressed() <2) {
                    return false;
                }
            }
            return true;
        }
    }

    public void update(float dt) {
        if (condition()) {
            screen.d1blck = false;
            System.out.println("megaPressurePlate was pressed with success");
        }
    }

}
