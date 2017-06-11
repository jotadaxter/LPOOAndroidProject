package com.mygdx.game.View.MenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Model.Files.SaveFile;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.DemoScreen;
import com.mygdx.game.View.GameScreens.Dungeon1;
import com.mygdx.game.View.GameScreens.FreeWorld;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * Created by Utilizador on 08-05-2017.
 */

public class SaveMenuIG extends MenuScreen {

    private Stage stage;

    private Texture texture;

    private Texture S1;
    private Texture S2;
    private Texture S3;
    private Texture back;

    private ImageButton S1Button;
    private ImageButton S2Button;
    private ImageButton S3Button;
    private ImageButton backButton;

    public SaveMenuIG(MyGame game) {
        super(game);
        stage= new Stage(viewPort,game.getBatch());
        loadAssets();
        backButtonDefine();

        Drawable S1Drawable = new TextureRegionDrawable(new TextureRegion(S1));
        S1Button = new ImageButton(S1Drawable);
        S1Button.setPosition(300,300);
        stage.addActor(S1Button);

        Drawable S2Drawable = new TextureRegionDrawable(new TextureRegion(S2));
        S2Button = new ImageButton(S2Drawable);
        S2Button.setPosition(300,240);
        stage.addActor(S2Button);

        Drawable S3Drawable = new TextureRegionDrawable(new TextureRegion(S3));
        S3Button = new ImageButton(S3Drawable);
        S3Button.setPosition(300,180);
        stage.addActor(S3Button);

        Gdx.input.setInputProcessor(stage);
    }

    private void backButtonDefine() {
        Drawable backDrawable = new TextureRegionDrawable(new TextureRegion(back));
        backButton = new ImageButton(backDrawable);
        backButton.setPosition(300, 120);
        stage.addActor(backButton);
    }

    private void loadAssets() {
        texture = game.getAssetManager().get("Menus/main_menu.jpg", Texture.class);
        S1 =game.getAssetManager().get("Buttons/slot1.png", Texture.class);
        S2 = game.getAssetManager().get("Buttons/slot2.png", Texture.class);
        S3 = game.getAssetManager().get("Buttons/slot3.png", Texture.class);
        back = game.getAssetManager().get("Buttons/back_button.png", Texture.class);
    }

    @Override
    public void render(float delta) {
        //Clear the screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        menuDraw();
        stage.draw();
        buttonUpdate();
    }

    private void buttonUpdate() {
        if (S1Button.isPressed() || S2Button.isPressed() || S3Button.isPressed()){

            SaveFile save = null;

            for (int i = 0; i < game.getGsm().getStates().size(); i++){
                if (game.getGsm().getStates().get(i).getGameScreen() != null){
                    save = new SaveFile(game.getHeroStats(),
                            game.getGsm().getStates().get(i).getGameScreen().getLogicController().player.getHeroBody().getBody().getPosition().x,
                            game.getGsm().getStates().get(i).getGameScreen().getLogicController().player.getHeroBody().getBody().getPosition().y,
                            stackTopName());
                    break;
                }
            }

            FileHandle saveFile = null;

            if(S1Button.isPressed())
                saveFile = Gdx.files.local("/Saves/Save1.sav");
            else if(S2Button.isPressed())
                saveFile = Gdx.files.local("/Saves/Save2.sav");
            else if(S3Button.isPressed())
                saveFile = Gdx.files.local("/Saves/Save3.sav");

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out;

            try{
                out = new ObjectOutputStream(bos);
                out.writeObject(save);
                out.flush();
                byte[] bytes = bos.toByteArray();
                saveFile.writeBytes(bytes,false);
            }
            catch(IOException e){
                e.printStackTrace();
            }

        }
        if (backButton.isPressed()){
            game.getGsm().pop();
        }
    }

    private String stackTopName() {
        if(game.getGsm().getStates().peek().getGameScreen().getType()==FreeWorld.class){
            return "free_world";
        }
        else if(game.getGsm().getStates().peek().getGameScreen().getType()==Dungeon1.class){
            return "dungeon1";
        }
        else if(game.getGsm().getStates().peek().getGameScreen().getType()==DemoScreen.class){
            return "demoScreen";
        }
        else return "";
    }

    private void menuDraw() {
        game.getBatch().begin();
        game.getBatch().draw(texture, 0,0,MENU_WIDTH/3,MENU_HEIGHT/3);
        game.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
}
