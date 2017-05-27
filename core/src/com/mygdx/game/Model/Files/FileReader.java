package com.mygdx.game.Model.Files;

import com.badlogic.gdx.math.Vector2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Utilizador on 26-05-2017.
 */

public class FileReader {
    private Scanner scan;
    private String filename;
    private String screenType;
    private Scanner textScan;

    public ArrayList<Vector2> ReadFile(String filename, String screenType){
        this.filename=filename;
        this.screenType=screenType;
        openFile();
        ArrayList<Vector2> positions = readPositions();
        closeFile();
        return positions;
    }

    private void openFile() {
        String temp = "Locations/" + filename + ".txt";
        try {
            scan = new Scanner(new File(temp));
        } catch (FileNotFoundException r) {
            System.out.println(r.getMessage());
        }
    }

    private ArrayList<Vector2> readPositions() {
        String strx, stry, temp;
        ArrayList<Vector2> positions = new ArrayList<Vector2>();
        while (scan.hasNext()) {
            temp = scan.next();
            if (temp.equals(screenType)) {
                strx = scan.next();
                stry = scan.next();
                positions.add(new Vector2(Integer.parseInt(strx), Integer.parseInt(stry)));
            }
        }
        return positions;
    }

    private void closeFile() {
        scan.close();
    }

    public String getSignText(String filename) {
        String temp1 = "Locations/" + filename + ".txt";
        String temp2="";
        try {
            textScan = new Scanner(new File(temp1));
        } catch (FileNotFoundException r) {
            System.out.println(r.getMessage());
        }
        while (textScan.hasNext()) {
            temp2+=(textScan.nextLine()+"\n");
        }
        textScan.close();
        return temp2;
    }
}
