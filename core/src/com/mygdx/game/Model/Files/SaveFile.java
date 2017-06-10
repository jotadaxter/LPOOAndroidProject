package com.mygdx.game.Model.Files;

import com.mygdx.game.Model.Entitys.Hero.HeroStats;

import java.io.IOException;

/**
 * Created by Francisco Moreira on 10/06/2017.
 */

public class SaveFile implements java.io.Serializable {
    public HeroStats heroStats;
    public float PlayerX;
    public float PlayerY;
    public String topStackName;

    public SaveFile(HeroStats heroStats, float PlayerX, float PlayerY, String topStackName){
        this.heroStats = heroStats;
        this.PlayerX = PlayerX;
        this.PlayerY = PlayerY;
        this.topStackName = topStackName;
    }

    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        heroStats = (HeroStats) stream.readObject();
        PlayerX = stream.readFloat();
        PlayerY = stream.readFloat();
        topStackName = (String) stream.readObject();
    }

    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException, ClassNotFoundException {
        stream.writeObject(heroStats);
        stream.writeFloat(PlayerX);
        stream.writeFloat(PlayerY);
        stream.writeObject(topStackName);
    }
}
