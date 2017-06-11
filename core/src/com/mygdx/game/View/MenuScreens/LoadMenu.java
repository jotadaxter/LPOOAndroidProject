package com.mygdx.game.View.MenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Model.Files.SaveFile;
import com.mygdx.game.Model.States.GameState;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.DemoScreen;
import com.mygdx.game.View.GameScreens.Dungeon1;
import com.mygdx.game.View.GameScreens.FreeWorld;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

// TODO: Auto-generated Javadoc
/**
 * Created by Utilizador on 08-05-2017.
 */

public class LoadMenu extends MenuScreen {

    /** The stage. */
    private Stage stage;

    /** The texture. */
    private Texture texture;

    /** The s1. */
    private Texture S1;
    
    /** The s2. */
    private Texture S2;
    
    /** The s3. */
    private Texture S3;
    
    /** The back. */
    private Texture back;

    /** The S 1 button. */
    private ImageButton S1Button;
    
    /** The S 2 button. */
    private ImageButton S2Button;
    
    /** The S 3 button. */
    private ImageButton S3Button;
    
    /** The back button. */
    private ImageButton backButton;

    /**
     * Instantiates a new load menu.
     *
     * @param game the game
     */
    public LoadMenu(MyGame game) {
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

    /**
     * Back button define.
     */
    private void backButtonDefine() {
        Drawable backDrawable = new TextureRegionDrawable(new TextureRegion(back));
        backButton = new ImageButton(backDrawable);
        backButton.setPosition(300, 120);
        stage.addActor(backButton);
    }

    /**
     * Load assets.
     */
    private void loadAssets() {
        texture = game.getAssetManager().get("Menus/main_menu.jpg", Texture.class);
        S1 =game.getAssetManager().get("Buttons/slot1.png", Texture.class);
        S2 = game.getAssetManager().get("Buttons/slot2.png", Texture.class);
        S3 = game.getAssetManager().get("Buttons/slot3.png", Texture.class);
        back = game.getAssetManager().get("Buttons/back_button.png", Texture.class);
    }

    /* (non-Javadoc)
     * @see com.mygdx.game.View.MenuScreens.MenuScreen#render(float)
     */
    @Override
    public void render(float delta) {
        //Clear the screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        menuDraw();
        stage.draw();
        buttonUpdate();
    }

    /**
     * Button update.
     */
    private void buttonUpdate() {

        if (S1Button.isPressed() || S2Button.isPressed() || S3Button.isPressed()){

            SaveFile save = null;

            FileHandle saveFile = null;

            if(S1Button.isPressed())
                if(!Gdx.files.local("/Saves/Save1.sav").exists())
                    return;
                else
                    saveFile = Gdx.files.local("/Saves/Save1.sav");
            else if(S2Button.isPressed())
                if(!Gdx.files.local("/Saves/Save2.sav").exists())
                    return;
                else
                    saveFile = Gdx.files.local("/Saves/Save2.sav");
            else if(S3Button.isPressed())
                if(!Gdx.files.local("/Saves/Save3.sav").exists())
                    return;
                else
                    saveFile = Gdx.files.local("/Saves/Save3.sav");

            byte[] bytes = saveFile.readBytes();

            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInput in;

            try{
                in = new ObjectInputStream(bis);
                save = (SaveFile) in.readObject();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            catch(ClassNotFoundException e){
                e.printStackTrace();
            }

            //All save information is ready from this point
            System.out.println(save.PlayerX);
            System.out.println(save.PlayerY);
            System.out.println(save.topStackName);
            System.out.println("Hearts " + save.heroStats.getHearts());

            if(save.topStackName=="free_world"){
                game.getHeroStats().setHearts(save.heroStats.getHearts());
                game.getHeroStats().setScore(save.heroStats.getScore());
                Vector2 vec = new Vector2(save.PlayerX,save.PlayerY);
                game.getGsm().getStates().clear();
                game.getGsm().push(new GameState(new FreeWorld(game, vec)));
            }
            else if(save.topStackName=="dungeon1"){
                game.getHeroStats().setHearts(save.heroStats.getHearts());
                game.getHeroStats().setScore(save.heroStats.getScore());
                Vector2 vec = new Vector2(save.PlayerX,save.PlayerY);
                game.getGsm().getStates().clear();
                game.getGsm().push(new GameState(new Dungeon1(game, vec)));
            }
            else if(save.topStackName=="dungeon1"){
                game.getHeroStats().setHearts(save.heroStats.getHearts());
                game.getHeroStats().setScore(save.heroStats.getScore());
                Vector2 vec = new Vector2(save.PlayerX,save.PlayerY);
                game.getGsm().getStates().clear();
                game.getGsm().push(new GameState(new DemoScreen(game, vec)));
            }
        }

        if (backButton.isPressed()){
            game.getGsm().set(new MainMenu(game));
        }
    }

    /**
     * Menu draw.
     */
    private void menuDraw() {
        game.getBatch().begin();
        game.getBatch().draw(texture, 0,0,MENU_WIDTH/3,MENU_HEIGHT/3);
        game.getBatch().end();
    }

    /* (non-Javadoc)
     * @see com.mygdx.game.View.MenuScreens.MenuScreen#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
}
