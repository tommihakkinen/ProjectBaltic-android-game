package com.mygdx.tikoprojectbaltic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.I18NBundle;

import static java.lang.Float.parseFloat;

/**
 * FarmButton is an object base class to create buttons to FarmScreen with ActorGestureListener.
 *
 * @author  Jennina Färm
 * @author  Tommi Häkkinen
 * @version 2020.2204
 * @since 1.8
 */

public class FarmButton extends Actor {

    /**
     * 2d array that contains booleans if the buttons are bought or not.
     */
    private static boolean[][] bought = new boolean[4][19];
    /**
     * Array that contains booleans if the upgrades are researched or not.
     */
    private static boolean[] researched = new boolean[19];
    /**
     * Main for money and balticSituation handling and asking I18NBundle.
     */
    private Main main;
    /**
     * FarmScreen for InfoLabel and button position handling.
     */
    private FarmScreen farmScreen;
    /**
     * Button that is created and drawn.
     */
    private Button button1;
    /**
     * Index of the Button.
     */
    private int buttonIndex;
    /**
     * Index of the FarmScreen where the Button is to be created.
     */
    private int farmIndex;
    /**
     * Cost of the Button.
     */
    private int cost;
    /**
     * Multiplier to increase the income speed.
     */
    private float multiplier;
    /**
     * Boolean if the Button is available to buy.
     */
    private boolean available;
    /**
     * BalticSituation affects game progress.
     */
    private int balticSituation;
    /**
     * InfoLabel that is drawn if player is hovering over the button.
     */
    private InfoLabel infoLabel;
    /**
     * Sound of the cow when bought livestock upgrade.
     */
    private Sound cowSound = Gdx.audio.newSound(Gdx.files.internal("sounds/cow.wav"));
    /**
     * Sound that plays when an upgrade is purchased.
     */
    private Sound upgradeSound = Gdx.audio.newSound(Gdx.files.internal("sounds/upgrade_buy.wav"));

    /**
     * Constructor. Sets I18NBundle, Skin, width, height, x- and y-coordinates and Button of the FarmButton.
     * It contains anonymous ActorGestureListener to detect touchDown, enter and exit of the FarmButton to buy the Button or set InfoLabel visible or hidden.
     *
     * @param m Main contains meta data
     * @param fs FarmScreen contains the Stage of the Button Actor
     * @param buttonI To identify the Buttons
     * @param farmI To identify the FarmScreen
     */
    FarmButton(Main m, FarmScreen fs, final int buttonI, int farmI) {
        main = m;
        farmScreen = fs;
        buttonIndex = buttonI;
        farmIndex = farmI;

        I18NBundle myBundle = main.getMyBundle();
        cost = Integer.parseInt(myBundle.get("researchCost" + buttonIndex)) / 2;
        multiplier = parseFloat(myBundle.get("upgradeMultiplier" + buttonIndex));
        balticSituation = Integer.parseInt(myBundle.get("upgradeBaltic" + buttonIndex));
        infoLabel = new InfoLabel(main, myBundle.get("researchInfo" + buttonIndex), 20, 50, 300, 310);

        Skin mySkin = new Skin(Gdx.files.internal("mySkinTest/mySkinTest.json"));

        setWidth(200);
        setHeight(60);

        setX(550);
        setY(310 - getHeight() / 2f - buttonIndex * getHeight());

        button1 = new TextButton(myBundle.get("upgrade" + buttonIndex), mySkin);
        button1.setSize(getWidth(), getHeight());
        button1.setPosition(getX(), getY());
        setBounds(getX(), getY(), getWidth(), getHeight());

        if (bought[farmIndex][buttonIndex]) {
            button1.setVisible(false);
        } else if (available) {
            button1.setChecked(false);
        } else {
            button1.setChecked(true);
            button1.setDisabled(true);
        }

        button1.addListener(new ActorGestureListener(2.5f, 0f, 0.25f, 0f) {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                int currentMoney = main.nonStaticGetMoney();

                if (currentMoney < cost || bought[farmIndex][buttonIndex] || !available) {
                    button1.setDisabled(true);
                } else {
                    button1.setDisabled(false);
                }
                if (available && currentMoney >= cost && !bought[farmIndex][buttonIndex]) {
                    if(Main.getSound()) {
                        upgradeSound.play(0.5f);
                    }
                    bought[farmIndex][buttonIndex] = true;
                    farmScreen.setFarmButtonY(buttonI);
                    button1.setVisible(false);
                    //set new amount of money and balticSituation
                    main.nonStaticSetMoney(currentMoney - cost);
                    main.addBalticSituation(balticSituation);
                    //set MoneyButton information
                    MoneyButton.addToMultiplier(multiplier, farmIndex);
                    MoneyButton.addToMaxAmount(cost / 2, farmIndex);
                    //save and load
                    Save.saveVariables();
                    Save.loadVariables();
                    if(Main.getSound() && buttonIndex == 6) {
                        cowSound.play(0.8f);
                    }
                }
            }
            public boolean longPress(Actor actor, float x, float y) {
                farmScreen.addInfoLabel(infoLabel);
                farmScreen.setInfoVisible(true);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                farmScreen.addInfoLabel(infoLabel);
                farmScreen.setInfoVisible(false);
            }
        });
    }

    /**
     * Set -method to receive array of researched upgrades.
     *
     * @param array array that is received
     */
    static void setResearched(boolean[] array) {
        researched = array;
    }

    /**
     * Get -method to collect a 2d array that contains if FarmButtons are bought.
     *
     * @return 2d boolean array if FarmButtons are bought.
     */
    static boolean[][] getBoughtArray() {
        return bought;
    }

    /**
     * Set -method to receive 2d boolean array if FarmButtons are bought.
     *
     * @param array 2d boolean array that is updated.
     */
    static void setBoughtArray(boolean[][] array) {
        bought = array;
    }

    /**
     * Sets Button available if the Button is researched already and not bought yet.
     */
    void setAvailable() {
        if (researched[buttonIndex] && !bought[farmIndex][buttonIndex]) {
            button1.setChecked(false);
            button1.setDisabled(false);
            available = true;
        }
    }

    /**
     * Get -method to collect Button of the object.
     *
     * @return TextButton that is created
     */
    public Button getButton() {
        return button1;
    }

    /**
     * Disposes the cowSound.
     */
    void disposeCowSound() {
        cowSound.dispose();
    }
}