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

// TODO: Auto-generated Javadoc
/**
 * The Class MyGame.
 */
public class MyGame extends Game {
    
    /** The Constant VIEWPORT_WIDTH. */
    public static final int VIEWPORT_WIDTH =240;//GameBoy Advance settings
    
    /** The Constant VIEWPORT_HEIGHT. */
    public static final int VIEWPORT_HEIGHT =160;
    
    /** The Constant PIXEL_TO_METER. */
    public static final float PIXEL_TO_METER = 0.0625f;
    
    /** The Constant VELOCITY. */
    public static final float VELOCITY= 10f;
	
	/** The Constant PLATFORM_VELOCITY_PC. */
	public static final float PLATFORM_VELOCITY_PC= 60f;//60f;
	
	/** The Constant PLATFORM_VELOCITY_ANDROID. */
	public static final float PLATFORM_VELOCITY_ANDROID= 5f;//60f;

	/** The Constant DEFAULT_BIT. */
	public static final short DEFAULT_BIT =1;
	
	/** The Constant HERO_BIT. */
	public static final short HERO_BIT =2;
	
	/** The Constant SMASH_BIT. */
	public static final short SMASH_BIT =4;
	
	/** The Constant ITEM_BIT. */
	public static final short ITEM_BIT =8;
	
	/** The Constant SPIKES_BIT. */
	public static final short SPIKES_BIT =16;
	
	/** The Constant BOULDER_BIT. */
	public static final short BOULDER_BIT =32;
	
	/** The Constant PRESSING_PLATE_BIT. */
	public static final short PRESSING_PLATE_BIT=64;
	
	/** The Constant MEGA_PRESSING_PLATE_BIT. */
	public static final short MEGA_PRESSING_PLATE_BIT=128;
	
	/** The Constant WARP_OBJECT. */
	public static final short WARP_OBJECT=256;//DOORS
	
	/** The Constant CHEST_BIT. */
	public static final short CHEST_BIT=512;
	
	/** The Constant BOMB_BIT. */
	public static final short BOMB_BIT=1024;
	
	/** The Constant PITFALL_BIT. */
	public static final short PITFALL_BIT=2048;
	
	/** The Constant MOVING_PLATFORM_BIT. */
	public static final short MOVING_PLATFORM_BIT =4096;
	
	/** The Constant SAFE_GROUND_BIT. */
	public static final short SAFE_GROUND_BIT=8192;
	
	/** The Constant SIGN_BIT. */
	public static final short SIGN_BIT = 16384;
	
	/** The Constant ALL_BIT. */
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
	
	/** The Constant GREEN_RUPEE. */
	//Rupee Info
	public static final int GREEN_RUPEE =1;
	
	/** The Constant BLUE_RUPEE. */
	public static final int BLUE_RUPEE =5;
	
	/** The Constant RED_RUPEE. */
	public static final int RED_RUPEE =20;
	
	/** The Constant BIG_GREEN_RUPEE. */
	public static final int BIG_GREEN_RUPEE =50;
	
	/** The Constant BIG_BLUE_RUPEE. */
	public static final int BIG_BLUE_RUPEE =100;
	
	/** The Constant BIG_RED_RUPEE. */
	public static final int BIG_RED_RUPEE =200;
	
	/** The music volume. */
	public static float MUSIC_VOLUME=1f;
	
	/** The sound volume. */
	public static float SOUND_VOLUME=0.5f;
	
	/** The vibration. */
	public static int VIBRATION=500;

	/** The batch. */
	private SpriteBatch batch;
	
	/** The gsm. */
	private GameStateManager gsm;
	
	/** The hero stats. */
	private HeroStats heroStats;
	
	/** The asset manager. */
	private AssetManager assetManager;
	
	/** The file reader. */
	private FileReader fileReader;
	
	/** The is test. */
	private boolean isTest;

	/**
	 * Instantiates a new my game.
	 *
	 * @param isTest the is test
	 */
	public MyGame(boolean isTest) {
		this.isTest = isTest;
	}

	/**
	 * Creates the.
	 */
	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		fileReader= new FileReader();
		heroStats= new HeroStats();
		loads();
		gsm= new GameStateManager(this);
	}

	/**
	 * Mute music.
	 */
	public void muteMusic(){
		MUSIC_VOLUME=0f;
	}
	
	/**
	 * Normalize music.
	 */
	public void normalizeMusic(){
		MUSIC_VOLUME=1f;
	}
	
	/**
	 * Mute sound.
	 */
	public void muteSound(){
		SOUND_VOLUME=0f;
	}
	
	/**
	 * Normalize sound.
	 */
	public void normalizeSound(){
		SOUND_VOLUME=0.5f;
	}
	
	/**
	 * Vibration on.
	 */
	public void vibrationOn(){
		VIBRATION=500;
	}
	
	/**
	 * Vibration off.
	 */
	public void vibrationOff(){
		VIBRATION=0;
	}
	
	/**
	 * Loads.
	 */
	private void loads() {
		loadTextures();
		loadSounds();
	}

	/**
	 * Load sounds.
	 */
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

	/**
	 * Load textures.
	 */
	private void loadTextures() {
		gameLoad();
		fontsLoad();
		buttonsLoad();
		menusLoad();
		assetManager.finishLoading();
	}

	/**
	 * Menus load.
	 */
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
		assetManager.load("Menus/game_completed.png", Texture.class);
		assetManager.load("Buttons/main_menu_button.png", Texture.class);
		assetManager.load("Buttons/load_button.png", Texture.class);
		assetManager.load("Buttons/newgame_button.png", Texture.class);
		assetManager.load("Buttons/save_game_button.png", Texture.class);
	}

	/**
	 * Buttons load.
	 */
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
		assetManager.load("Buttons/slot1.png", Texture.class);
		assetManager.load("Buttons/slot2.png", Texture.class);
		assetManager.load("Buttons/slot3.png", Texture.class);
	}

	/**
	 * Fonts load.
	 */
	private void fontsLoad() {
		assetManager.load("Fonts/myFont.fnt", BitmapFont.class);
		assetManager.load("Fonts/textFont.fnt", BitmapFont.class);
		assetManager.load("Fonts/score.fnt", BitmapFont.class);
	}

	/**
	 * Game load.
	 */
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
		assetManager.load("Game/hero_walk_down.png", Texture.class);
		assetManager.load("Game/hero_walk_right.png", Texture.class);
		assetManager.load("Game/hero_walk_up.png", Texture.class);
		assetManager.load("Game/pressing_plate_not_pressed.png", Texture.class);
		assetManager.load("Game/pressing_plate_pressed.png", Texture.class);
		assetManager.load("Game/spikes.png", Texture.class);
		assetManager.load("Game/destroyable_rock.png", Texture.class);
		assetManager.load("Game/big_blue_rupee.png", Texture.class);
		assetManager.load("Game/big_green_rupee.png", Texture.class);
		assetManager.load("Game/big_red_rupee.png", Texture.class);
		assetManager.load("Game/blue_rupee.png", Texture.class);
		assetManager.load("Game/green_rupee.png", Texture.class);
		assetManager.load("Game/red_rupee.png", Texture.class);
		assetManager.load("Game/hero_back.png", Texture.class);
		assetManager.load("Game/hero_left.png", Texture.class);
		assetManager.load("Game/hero_front.png", Texture.class);
		assetManager.load("Game/hero_right.png", Texture.class);
		assetManager.load("Game/boulder.png", Texture.class);
		assetManager.load("Game/heart.png", Texture.class);
	}

	/**
	 * Gets the batch.
	 *
	 * @return the batch
	 */
	public SpriteBatch getBatch() {
		return batch;
	}

	/**
	 * Gets the gsm.
	 *
	 * @return the gsm
	 */
	public GameStateManager getGsm() {
		return gsm;
	}

	/**
	 * Gets the hero stats.
	 *
	 * @return the hero stats
	 */
	public HeroStats getHeroStats() {
		return heroStats;
	}

	/**
	 * Gets the asset manager.
	 *
	 * @return the asset manager
	 */
	public AssetManager getAssetManager() {
		return assetManager;
	}

	/**
	 * Gets the file reader.
	 *
	 * @return the file reader
	 */
	public FileReader getFileReader() {
		return fileReader;
	}

	/**
	 * Render.
	 */
	@Override
	public void render () {
		super.render();
	}

	/**
	 * Dispose.
	 */
	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();

	}


	/**
	 * Gets the checks if is test.
	 *
	 * @return the checks if is test
	 */
	public boolean getIsTest() {
		return isTest;
	}
}
