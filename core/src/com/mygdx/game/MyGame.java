package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Model.States.GameState;
import com.mygdx.game.Model.States.GameStateManager;
import com.mygdx.game.View.GameScreens.DemoScreen;
import com.mygdx.game.View.MenuScreens.MainMenu;

import java.util.Stack;

public class MyGame extends Game {
    public static final int VIEWPORT_WIDTH =240;//GameBoy Advance settings
    public static final int VIEWPORT_HEIGHT =160;
    public static final float PIXEL_TO_METER = 0.0625f;
    public static final float VELOCITY= 10f;

	public static final short DEFAULT_BIT =1;
	public static final short HERO_BIT =2;
	public static final short ENEMY_BIT =4;
	public static final short ITEM_BIT =8;
	public static final short SPIKES_BIT =16;
	public static final short BOULDER_BIT =32;
	public static final short PRESSING_PLATE_BIT=64;
	public static final short WARP_OBJECT=128;//DOORS AND STAIRS


	public SpriteBatch batch;
	public GameStateManager gameStateManager;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gameStateManager= new GameStateManager(this);

	}

	@Override
	public void render () {
		super.render();
	}

}
