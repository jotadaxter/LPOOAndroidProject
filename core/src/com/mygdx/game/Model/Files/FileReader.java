package com.mygdx.game.Model.Files;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Utilizador on 26-05-2017.
 */

public class FileReader {
    
    /** The filename. */
    private String filename;
    
    /** The screen type. */
    private String screenType;

    /**
     * Read file.
     *
     * @param filename the filename
     * @param screenType the screen type
     * @return the array list
     */
    public ArrayList<Vector2> ReadFile(String filename, String screenType){
        this.filename=filename;
        this.screenType=screenType;
        ArrayList<Vector2> positions = readPositions();
        return positions;
    }

    /**
     * Read positions.
     *
     * @return the array list
     */
    private ArrayList<Vector2> readPositions() {
        ArrayList<Vector2> positions = new ArrayList<Vector2>();
        FileHandle handle = Gdx.files.internal("Locations/" + filename + ".txt");
        String text = handle.readString();
        String wordsArray[] = text.split("\n");
        for(String word : wordsArray) {
            String words_temp[]= word.split("-");
            if (words_temp[0].equals(screenType)) {
                positions.add(new Vector2(Float.parseFloat(words_temp[1]), Float.parseFloat(words_temp[2])));
            }
        }
        return positions;
    }

    /**
     * Gets the sign text.
     *
     * @param filename the filename
     * @return the sign text
     */
    public String getSignText(String filename) {
        FileHandle handle = Gdx.files.internal("Locations/" + filename + ".txt");
        String text = handle.readString();
        return text;
    }
}
