package com.mygdx.game.Model.Files;

import com.mygdx.game.Model.Entitys.Hero.HeroStats;

import java.io.IOException;

/**
 * Created by Francisco Moreira on 10/06/2017.
 */

public class SaveFile implements java.io.Serializable {
    
    /** The hero stats. */
    public HeroStats heroStats;
    
    /** The Player X. */
    public float PlayerX;
    
    /** The Player Y. */
    public float PlayerY;
    
    /** The top stack name. */
    public String topStackName;

    /**
     * Instantiates a new save file.
     *
     * @param heroStats the hero stats
     * @param PlayerX the player X
     * @param PlayerY the player Y
     * @param topStackName the top stack name
     */
    public SaveFile(HeroStats heroStats, float PlayerX, float PlayerY, String topStackName){
        this.heroStats = heroStats;
        this.PlayerX = PlayerX;
        this.PlayerY = PlayerY;
        this.topStackName = topStackName;
    }

    /**
     * Read object.
     *
     * @param stream the stream
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ClassNotFoundException the class not found exception
     */
    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        heroStats = (HeroStats) stream.readObject();
        PlayerX = stream.readFloat();
        PlayerY = stream.readFloat();
        topStackName = (String) stream.readObject();
    }

    /**
     * Write object.
     *
     * @param stream the stream
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ClassNotFoundException the class not found exception
     */
    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException, ClassNotFoundException {
        stream.writeObject(heroStats);
        stream.writeFloat(PlayerX);
        stream.writeFloat(PlayerY);
        stream.writeObject(topStackName);
    }
}
