package com.mygdx.game.Model.Events;

import com.mygdx.game.Controller.LogicController;
import com.mygdx.game.Model.Entitys.DinamicObjects.MegaPressingPlate;
import com.mygdx.game.Model.Entitys.DinamicObjects.PressingPlate;

import java.util.ArrayList;

/**
 * Created by Utilizador on 17-05-2017.
 */

public class PressingEvent {
    
    /** The plates. */
    private ArrayList<PressingPlate> plates;
    
    /** The mega plates. */
    private ArrayList<MegaPressingPlate> megaPlates;
    
    /** The logic controller. */
    private LogicController logicController;

    /**
     * Instantiates a new pressing event.
     *
     * @param plates the plates
     * @param logicController the logic controller
     * @param bla the bla
     */
    public PressingEvent(ArrayList<PressingPlate> plates, LogicController logicController, int bla){//o bla só serve para não haver erro em haver dois construtores...
        this.plates=plates;
        this.logicController=logicController;

        this.megaPlates=null;
    }

    /**
     * Instantiates a new pressing event.
     *
     * @param megaPlates the mega plates
     * @param logicController the logic controller
     */
    public PressingEvent(ArrayList<MegaPressingPlate> megaPlates, LogicController logicController){
        this.plates=null;
        this.logicController=logicController;
        this.megaPlates=megaPlates;
    }

    /**
     * Condition.
     *
     * @return true, if successful
     */
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

    /**
     * Update.
     *
     * @param dt the dt
     */
    public void update(float dt) {
        if (condition()) {
            logicController.setD1blck(false);
            System.out.println("megaPressurePlate was pressed with success");
        }
    }

}
