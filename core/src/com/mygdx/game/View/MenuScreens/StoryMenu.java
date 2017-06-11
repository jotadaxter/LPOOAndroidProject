package com.mygdx.game.View.MenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Model.States.GameState;
import com.mygdx.game.MyGame;
import com.mygdx.game.View.GameScreens.FreeWorld;

/**
 * Created by Utilizador on 08-05-2017.
 */

public class StoryMenu extends MenuScreen {
    //Hero Info
    public static final int POSX = 8+11*16;
    public static final int POSY = 8+2*16;
    private Stage stage;
    private Texture texture;
    private Texture NG;
    private Texture LG;
    private Texture back;
    private ImageButton NGButton;
    private ImageButton LGButton;
    private ImageButton backButton;

    public StoryMenu(MyGame game) {
        super(game);
        stage= new Stage(viewPort,game.getBatch());
        loadAssets();
        backButtonDefine();

        Drawable S1Drawable = new TextureRegionDrawable(new TextureRegion(NG));
        NGButton = new ImageButton(S1Drawable);
        NGButton.setPosition(300,300);
        stage.addActor(NGButton);

        Drawable S2Drawable = new TextureRegionDrawable(new TextureRegion(LG));
        LGButton = new ImageButton(S2Drawable);
        LGButton.setPosition(300,240);
        stage.addActor(LGButton);

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
        NG =game.getAssetManager().get("Buttons/newgame_button.png", Texture.class);
        LG = game.getAssetManager().get("Buttons/load_button.png", Texture.class);
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
        if (NGButton.isPressed()){
            game.getGsm().push(new GameState(new FreeWorld(game, new Vector2(POSX,POSY))));
        }
        if (LGButton.isPressed()){
            game.getGsm().push(new GameState(new LoadMenu(game)));
        }
        if (backButton.isPressed()){
            if(game.getGsm().getStates().size() > 0){
                game.getGsm().set(new MainMenu(game));
            }else
                game.getGsm().pop();
        }
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
