package com.mygdx.game.View.GameScreens;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Model.Entitys.DinamicObjects.Boulder;
import com.mygdx.game.Model.Entitys.DinamicObjects.PressingPlate;
import com.mygdx.game.Model.Entitys.DinamicObjects.Spikes;
import com.mygdx.game.Model.Entitys.Items.Heart;
import com.mygdx.game.Model.Entitys.Items.ItemDef;
import com.mygdx.game.Model.Entitys.Items.Jewel;
import com.mygdx.game.MyGame;

/**
 * Created by Utilizador on 08-05-2017.
 */

public class DemoScreen extends GameScreen {

    public DemoScreen(MyGame game) {
        super(game);

    }

    @Override
    protected void objectLoad() {
        boulder= new Boulder(this);
        spikes= new Spikes(this);
        spikes= new Spikes(this);
        pp= new PressingPlate(this);

        //Items
        spawnItem(new ItemDef(new Vector2(150,150), Jewel.class));
        spawnItem(new ItemDef(new Vector2(200,150), Heart.class));
    }

    @Override
    public String getMapName() {
        return "level1.tmx";
    }

    @Override
    protected void objectsUpdate(float dt) {
        boulder.update(dt);
        spikes.update(dt);
        spikes.update(dt);
        pp.update(dt, this);
    }

    @Override
    protected void objactsDraw() {
        spikes.draw(game.batch);
        pp.draw(game.batch);
        boulder.draw(game.batch);
    }
}
