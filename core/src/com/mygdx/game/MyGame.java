package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.View.Screens.MainMenu;
import com.mygdx.game.View.Screens.MyScreen;

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

	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}
	

}
