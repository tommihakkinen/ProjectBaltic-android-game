package com.mygdx.tikoprojectbaltic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Main is an object base class that holds meta data of the game and handles screen switching.
 *
 * @author  Jennina Färm
 * @author  Tommi Häkkinen
 * @version 2020.2204
 * @since 1.8
 */
public class Main extends Game {

	/**
	 * Boolean if game is in Finnish or English.
	 */
	static boolean finnish;
	/**
	 * Boolean if music is on.
	 */
	static boolean music_ON;
	/**
	 * Amount of current money.
	 */
	private static int money;
	/**
	 * Amount of current balticSituation.
	 */
	private static int balticSituation;
	/**
	 * Boolean if sound effects are on.
	 */
	private static boolean soundEffects_ON;
	/**
	 * Viewport width
	 */
	final int WIDTH = 800;
	/**
	 * Viewport height
	 */
	final int HEIGHT = 450;
	/**
	 * SpriteBatch handles drawing.
	 */
	private SpriteBatch batch;
	/**
	 * Screen for main menu.
	 */
	private MainMenuScreen mainMenuScreen;
	/**
	 * Screen for map.
	 */
	private MapScreen mapScreen;
	/**
	 * ArrayList holding screens for farms.
	 */
	private ArrayList<FarmScreen> farmScreens;
	/**
	 * Screen for research center.
	 */
	private ResearchScreen researchScreen;
	/**
	 * Screen for chancing options.
	 */
	private OptionsScreen optionsScreen;
	/**
	 * Amount of farms.
	 */
	private int farmAmount = 4;
	/**
	 * Handles button and text styles.
	 */
	private Skin mySkin;
	/**
	 * Has stable information of the game.
	 */
	private I18NBundle myBundle;
	/**
	 * Handles languages.
	 */
	private Locale locale = new Locale("fi", "FI");
	/**
	 * Music of map screen.
	 */
	private Music mapMusic;
	/**
	 * Music of research screen.
	 */
	private Music researchMusic;
	/**
	 * Music of farm screen.
	 */
	private Music farmMusic;

	/**
	 * Set -method to receive language boolean.
	 * @param language sets if languages is Finnish not
	 */
	static void setLanguage(boolean language) {
		finnish = language;
	}

	/**
	 * Get -method to collect if music is on or not.
	 * @return boolean if music is on
	 */
	static boolean getMusic() {
		return music_ON;
	}

	/**
	 * Set -method to receive music boolean.
	 * @param music sets if music is on or not
	 */
	static void setMusic(boolean music) {
		music_ON = music;
	}

	/**
	 * Get -method to collect boolean if sound effects are on.
	 * @return if sound effects are on
	 */
	static boolean getSound() {
		return soundEffects_ON;
	}

	/**
	 * Set -method to receive sound boolean.
	 * @param sound sets if sound effects are on or not
	 */
	static void setSound(boolean sound) {
		soundEffects_ON = sound;
	}

	/**
	 * Get -method to collect current amount of money.
	 * @return current amount of money
	 */
	public static int getMoney() {
		return money;
	}

	/**
	 * Set -method to receive updated amount of money.
	 *
	 * @param amount sets new amount of money
	 */
	public static void setMoney(int amount) {
		if(amount < 0) {
		} else {
			money = amount;
		}
	}

	/**
	 * Get -method to collect current balticSituation.
	 * @return current amount of balticSituation
	 */
	static int getBalticSituation() { return balticSituation; }

	/**
	 * Set -method to receive balticSituation.
	 * @param bs int that is new balticSituation
	 */
	static void setBalticSituation(int bs) {
		balticSituation = bs;
	}

	/**
	 * Calls create of the given Main and stops the Music.
	 * @param m Main contains meta data of the game
	 */
	static void callCreate(Main m) {
		m.stopMusic();
		m.create();
	}

	/**
	 * Creates all the private variables, arrays, and objects and sets the first screen.
	 */
	@Override
	public void create () {
		batch = new SpriteBatch();

		Save.loadVariables();

		if(finnish) {
			changeLocale(new Locale("fi", "FI"));
		} else {
			changeLocale(new Locale("en", "GB"));
		}
		myBundle = I18NBundle.createBundle(Gdx.files.internal("MyBundle"), locale);
		mySkin = new Skin(Gdx.files.internal("mySkinTest/mySkinTest.json"));

		Save.loadVariables();

		mainMenuScreen = new MainMenuScreen(this);
		mapScreen = new MapScreen(this);

		farmScreens = new ArrayList<>();
		for(int i=0; i<farmAmount; i++) {
			farmScreens.add(new FarmScreen(this, i));
		}
		researchScreen = new ResearchScreen(this);
		optionsScreen = new OptionsScreen(this);

		mapMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/map_background_music.mp3"));
		mapMusic.setLooping(true);
		mapMusic.setVolume(0.2f);
		researchMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/research_background_music.mp3"));
		researchMusic.setLooping(true);
		researchMusic.setVolume(0.2f);
		farmMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/farm_background_music.mp3"));
		farmMusic.setLooping(true);
		farmMusic.setVolume(0.6f);

		setScreen(mainMenuScreen);
		Gdx.input.setInputProcessor(mainMenuScreen.getStage());
		if (music_ON) {
			mapMusic.play();
		}
	}

	/**
	 * Changes locale to given Locale.
	 *
	 * @param l new Locale
	 */
	void changeLocale(Locale l) {
		locale = l;
	}

	/**
	 * Switches Screen based on given x and y indexes.
	 *
	 * @param x index of the different screen objects
	 * @param y index of the different screens of an object
	 */
	void switchScreen(int x, int y) {
		if(x == 1) {
			setScreen(mainMenuScreen);
			Gdx.input.setInputProcessor(mainMenuScreen.getStage());
			mapMusic.pause();
			if (music_ON) {
				mapMusic.play();
			}
		} else if (x == 2) {
			setScreen(mapScreen);
			InputMultiplexer multiplexer = new InputMultiplexer(mapScreen.getStageUI(), mapScreen.getStage());
			Gdx.input.setInputProcessor(multiplexer);
			farmMusic.stop();
			researchMusic.stop();
			mapMusic.pause();
			if (music_ON) {
				mapMusic.play();
			}
		} else if(x == 3) {
			if(y >= 0) {
				setScreen(farmScreens.get(y));
				InputMultiplexer multiplexer = new InputMultiplexer(farmScreens.get(y).getStage(), farmScreens.get(y).getStageUI());
				Gdx.input.setInputProcessor(multiplexer);
				mapMusic.pause();
				if (music_ON) {
					mapMusic.play();
					farmMusic.play();
				}
			}
		} else if (x == 4) {
			setScreen(researchScreen);
			InputMultiplexer multiplexer = new InputMultiplexer(researchScreen.getStage(), researchScreen.getStageUI());
			Gdx.input.setInputProcessor(multiplexer);
			mapMusic.stop();
			if (music_ON) {
				researchMusic.play();
			}
		} else if(x == 5) {
			setScreen(optionsScreen);
			Gdx.input.setInputProcessor(optionsScreen.getStage());
		}
	}

	/**
	 * Adds given amount to current amount of balticSituation if given amount is between 0-4.
	 *
	 * @param amount amount that is dried to add to baltic situation
	 */
	void addBalticSituation(int amount) {
		if(amount < 0 || amount > 4) {
		} else {
			balticSituation += amount;
		}
	}

	/**
	 * Non static set -method to receive updated amount of money.
	 * @param amount sets new amount of money
	 */
	void nonStaticSetMoney(int amount) {
		if(amount < 0) {
		} else {
			money = amount;
		}
	}

	/**
	 * Stops playing music.
	 */
	void stopMusic() {
		mapMusic.stop();
	}

	/**
	 * Non static get -method to collect current amount of money.
	 * @return current amount of money
	 */
	int nonStaticGetMoney() {
		return money;
	}

	/**
	 * Get -method to collect SpriteBatch to handle drawing.
	 * @return SpriteBatch
	 */
	public SpriteBatch getBatch() {
		return batch;
	}

	/**
	 * Get -method to collect I18NBundle containing stable information of the game.
	 * @return I18NBundle
	 */
	I18NBundle getMyBundle() {
		return myBundle;
	}

	/**
	 * Get -method to collect Skin of the buttons and text.
	 * @return Skin
	 */
	Skin getMySkin() { return mySkin;}

	@Override
	public void render () {
		super.render();
	}

	/**
	 * Disposes SpriteBatch, Skin, all the music and Actors.
	 */
	@Override
	public void dispose () {
		batch.dispose();
		mySkin.dispose();
		mainMenuScreen.dispose();
		mapScreen.dispose();
		for(int i=0; i<farmAmount; i++) {
			farmScreens.get(i).dispose();
		}
		optionsScreen.dispose();
		researchScreen.dispose();
		mapMusic.dispose();
		researchMusic.dispose();
		farmMusic.dispose();
	}

}
