package com.mygdx.game.Model.Events;

import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.Model.Entitys.DinamicObjects.MegaPressingPlate;
import com.mygdx.game.Model.Entitys.DinamicObjects.PressingPlate;

import java.util.ArrayList;

/**
 * Created by Utilizador on 17-05-2017.
 */

public class PressingEvent {
    private ArrayList<PressingPlate> plates;
    private ArrayList<MegaPressingPlate> megaPlates;
    private LogicController logicController;

    public PressingEvent(ArrayList<PressingPlate> plates, LogicController logicController, int bla){//o bla só serve para não haver erro em haver dois construtores...
        this.plates=plates;
        this.logicController=logicController;

        this.megaPlates=null;
    }

    public PressingEvent(ArrayList<MegaPressingPlate> megaPlates, LogicController logicController){
        this.plates=null;
        this.logicController=logicController;
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
          //  logicController.setD1blck(false);
            System.out.println("megaPressurePlate was pressed with success");
        }
    }

}
