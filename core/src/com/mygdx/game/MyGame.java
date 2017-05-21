package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Model.Entitys.Hero.Hero;
import com.mygdx.game.Model.Entitys.Hero.HeroStats;
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
	public static final float PLATFORM_VELOCITY= 200f;//60f;

	public static final short DEFAULT_BIT =1;
	public static final short HERO_BIT =2;
	public static final short ENEMY_BIT =4;
	public static final short ITEM_BIT =8;
	public static final short SPIKES_BIT =16;
	public static final short BOULDER_BIT =32;
	public static final short PRESSING_PLATE_BIT=64;
	public static final short MEGA_PRESSING_PLATE_BIT=128;
	public static final short WARP_OBJECT=256;//DOORS
	public static final short CHEST_BIT=512;
	public static final short BOMB_BIT=1024;
	public static final short PITFALL_BIT=2048;
	public static final short MOVING_PLATFORM_BIT =4096;
	public static final short SAFE_GROUND_BIT=8192;

	//Rupee Info
	public static final int GREEN_RUPEE =1;
	public static final int BLUE_RUPEE =5;
	public static final int RED_RUPEE =20;
	public static final int BIG_GREEN_RUPEE =50;
	public static final int BIG_BLUE_RUPEE =100;
	public static final int BIG_RED_RUPEE =200;

	public SpriteBatch batch;
	public GameStateManager gsm;
	public HeroStats heroStats;


	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm= new GameStateManager(this);
		heroStats= new HeroStats();
	}

	@Override
	public void render () {
		super.render();
	}

}
