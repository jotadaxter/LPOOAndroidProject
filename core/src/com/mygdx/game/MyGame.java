package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.Model.Entitys.Hero.Hero;
import com.mygdx.game.Model.Entitys.Hero.HeroStats;
import com.mygdx.game.Model.Files.FileReader;
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
	public static final float PLATFORM_VELOCITY_PC= 60f;//60f;
	public static final float PLATFORM_VELOCITY_ANDROID= 5f;//60f;

	public static final short DEFAULT_BIT =1;
	public static final short HERO_BIT =2;
	public static final short SMASH_BIT =4;
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
	public static final short SIGN_BIT = 16384;
	public static final short ALL_BIT =ITEM_BIT
                | DEFAULT_BIT
                | SPIKES_BIT
                | BOULDER_BIT
                | WARP_OBJECT
                | BOMB_BIT
                | PITFALL_BIT
                | CHEST_BIT
                | SMASH_BIT
                | SIGN_BIT
                | MOVING_PLATFORM_BIT
                | MEGA_PRESSING_PLATE_BIT
                | PRESSING_PLATE_BIT;
	//Rupee Info
	public static final int GREEN_RUPEE =1;
	public static final int BLUE_RUPEE =5;
	public static final int RED_RUPEE =20;
	public static final int BIG_GREEN_RUPEE =50;
	public static final int BIG_BLUE_RUPEE =100;
	public static final int BIG_RED_RUPEE =200;
	public static float MUSIC_VOLUME=1f;
	public static float SOUND_VOLUME=1f;
	public static float FIREGROUND_SOUND=0.25f;
	public SpriteBatch batch;
	public GameStateManager gsm;
	public HeroStats heroStats;
	public AssetManager assetManager;
	public FileReader fileReader;

	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		fileReader= new FileReader();
		heroStats= new HeroStats();
		loads();
		gsm= new GameStateManager(this);
	}

	public void muteMusic(){
		MUSIC_VOLUME=0f;
	}
	public void normalizeMusic(){
		MUSIC_VOLUME=1f;
	}
	public void muteSound(){
		SOUND_VOLUME=0f;
		FIREGROUND_SOUND=0f;
	}
	public void normalizeSound(){
		SOUND_VOLUME=1f;
		FIREGROUND_SOUND=0.25f;
	}

	private void loads() {
		loadTextures();
		loadSounds();
	}

	private void loadSounds() {
		assetManager.load("Sounds/bomb_boom.wav", Sound.class);
		assetManager.load("Sounds/bomb_tic_tac.wav", Sound.class);
		assetManager.load("Sounds/fire.wav", Sound.class);
		assetManager.load("Sounds/get_chest_item.wav", Sound.class);
		assetManager.load("Sounds/get_heart.wav", Sound.class);
		assetManager.load("Sounds/get_heart_container.wav", Sound.class);
		assetManager.load("Sounds/get_rupee.wav", Sound.class);
		assetManager.load("Sounds/hero_hurt.wav", Sound.class);
		assetManager.load("Sounds/open_chest.wav", Sound.class);
		assetManager.load("Sounds/pressing_plate_on.wav", Sound.class);
		assetManager.load("Sounds/pushing_boulder.wav", Sound.class);
		assetManager.load("Sounds/secret_unlocked.wav", Sound.class);
		assetManager.load("Sounds/rock_shatter.wav", Sound.class);
	}

	private void loadTextures() {
		gameLoad();
		fontsLoad();
		buttonsLoad();
		menusLoad();
		texturePacksLoad();
		assetManager.finishLoading();
	}

	private void texturePacksLoad() {
		assetManager.load("Game/link_and_objects.pack", TextureAtlas.class);
	}

	private void menusLoad() {
		assetManager.load("Menus/game_title.png", Texture.class);
		assetManager.load("Menus/options_title.png", Texture.class);
		assetManager.load("Menus/main_menu.jpg", Texture.class);
		assetManager.load("Menus/music_text.png", Texture.class);
		assetManager.load("Menus/sounds_text.png", Texture.class);
		assetManager.load("Menus/vibration_text.png", Texture.class);
		assetManager.load("Menus/checked.png", Texture.class);
		assetManager.load("Menus/unchecked.png", Texture.class);
		assetManager.load("Menus/game_over.png", Texture.class);
		assetManager.load("Buttons/main_menu_button.png", Texture.class);
		assetManager.load("Buttons/load_button.png", Texture.class);
	}

	private void buttonsLoad() {
		assetManager.load("Buttons/a_button.png", Texture.class);
		assetManager.load("Buttons/b_button.png", Texture.class);
		assetManager.load("Buttons/down_arrow.png", Texture.class);
		assetManager.load("Buttons/up_arrow.png", Texture.class);
		assetManager.load("Buttons/left_arrow.png", Texture.class);
		assetManager.load("Buttons/right_arrow.png", Texture.class);
		assetManager.load("Buttons/selection_button.png", Texture.class);
		assetManager.load("Buttons/arcade_button.png", Texture.class);
		assetManager.load("Buttons/story_button.png", Texture.class);
		assetManager.load("Buttons/options_button.png", Texture.class);
		assetManager.load("Buttons/quit_button.png", Texture.class);
		assetManager.load("Buttons/options_menu.png", Texture.class);
		assetManager.load("Buttons/back_button.png", Texture.class);
        assetManager.load("Buttons/esc_button.png", Texture.class);
	}

	private void fontsLoad() {
		assetManager.load("Fonts/myFont.fnt", BitmapFont.class);
		assetManager.load("Fonts/textFont.fnt", BitmapFont.class);
	}

	private void gameLoad() {
		assetManager.load("Game/bombs.png", Texture.class);
		assetManager.load("Game/explosion.png", Texture.class);
		assetManager.load("Game/chests.png", Texture.class);
		assetManager.load("Game/fire.png", Texture.class);
		assetManager.load("Game/hero_dying.png", Texture.class);
		assetManager.load("Game/hero_hurt.png", Texture.class);
		assetManager.load("Game/hero_falling.png", Texture.class);
		assetManager.load("Game/life_hearts.png", Texture.class);
		assetManager.load("Game/mega_pressing_plates.png", Texture.class);
		assetManager.load("Game/moving_platform_down.png", Texture.class);
		assetManager.load("Game/moving_platform_up.png", Texture.class);
		assetManager.load("Game/moving_platform_left.png", Texture.class);
		assetManager.load("Game/moving_platform_right.png", Texture.class);
		assetManager.load("Game/log.png", Texture.class);
		assetManager.load("Game/volcano_ruby.png", Texture.class);
		assetManager.load("Game/sign.png", Texture.class);
		assetManager.load("Game/way_blocker.png", Texture.class);
		assetManager.load("Game/way_blocker2.png", Texture.class);
		assetManager.load("Game/door_top.png", Texture.class);
		assetManager.load("Game/door_top2.png", Texture.class);
	}


	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();

	}
}