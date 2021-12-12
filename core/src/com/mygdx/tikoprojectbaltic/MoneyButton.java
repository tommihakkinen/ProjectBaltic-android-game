package com.mygdx.tikoprojectbaltic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

/**
 * MoneyButton is an object base class to create a Texture extending Actor with inputListener.
 *
 * @author  Jennina Färm
 * @author  Tommi Häkkinen
 * @version 2020.2204
 * @since 1.8
 */
public class MoneyButton extends Actor {

    /**
     * Main to handle meta data of the game.
     */
    private Main main;
    /**
     * Texture that is created and drawn.
     */
    private Texture coin;
    /**
     * Sound of the coin when collected.
     */
    private Sound coinSound = Gdx.audio.newSound(Gdx.files.internal("sounds/coin.wav"));
    /**
     * Array for storing maximum amounts of moneyCollected.
     */
    private static int [] maxAmount = {0, 0, 0, 0, 20000, 20000};
    /**
     * Array for storing multipliers of the MoneyButtons.
     */
    private static float [] multipliers = {4, 4, 4, 4, 30, 30};
    /**
     * Array for storing last time in seconds when MoneyButton was clicked.
     */
    private static int [] lastTimeClicked = new int[6];
    /**
     * Original x-coordination.
     */
    private int originalX;
    /**
     * Original y-coordination.
     */
    private int originalY;
    /**
     * Index to identify the coins.
     */
    private int index;
    /**
     * Amount of money collected form the MoneyButton.
     */
    private int moneyCollected;
    /**
     * Time in seconds when the MoneyButton was last clicked.
     */
    private int timeWhenClickedInSec;

    /**
     * Constructor. Creates all the private variables and sets x- and y-coordinates, Texture, width and height of the MoneyButton.
     * It contains anonymous InputListener to detect touchDown of the MoneyButton to collect earned money.
     *
     * @param m Main contains meta data of the game
     * @param x X-coordinate that will be the original
     * @param y Y-coordinate that will be the original
     * @param i Index of the button
     */
    MoneyButton(Main m, final int x, final int y, int i) {
        main = m;
        originalX = x;
        originalY = y;
        index = i;
        setX(originalX);
        setY(originalY);
        coin = new Texture(Gdx.files.internal("coin-icon.png"));
        setWidth(coin.getWidth() / 1.7f);
        setHeight(coin.getHeight() / 1.7f);
        setBounds(getX(), getY(), getWidth(), getHeight());

        addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                timeWhenClickedInSec = Utils.getCurrentTimeInSeconds();
                moneyCollected = countMoney(timeWhenClickedInSec);
                if (Main.getSound()) {
                    coinSound.play(0.8f);
                }
                main.nonStaticSetMoney(main.nonStaticGetMoney() + moneyCollected);
                lastTimeClicked[index] = timeWhenClickedInSec;
                Save.saveVariables();
                Save.loadVariables();

                MoveToAction moveToAction = new MoveToAction();
                moveToAction.setPosition(300, 410);
                moveToAction.setDuration(0.5f);
                MoneyButton.this.addAction(moveToAction);

                return true;
            }
        });
    }

    /**
     * Adds given amount to the maximum amount of money that can be collected.
     *
     * @param amount Amount that is added to the already existing amount
     * @param index Index of the MoneyButton where it's added
     */
    static void addToMaxAmount(int amount, int index) {
        if(maxAmount[index] + amount > 80000) {
            maxAmount[index] = 80000;
        } else if(maxAmount[index] + amount < 1000)
            maxAmount[index] = 1000;
        else {
            maxAmount[index] += amount;
        }
    }

    /**
     * Adds given amount to the multiplier.
     *
     * @param addedMp Amount that is added to the already existing amount
     * @param farmIndex Index of the farmScreen
     */
    static void addToMultiplier(float addedMp, int farmIndex) {
        multipliers[farmIndex] += addedMp;
    }

    /**
     * Get -method to collect the maxAmount array.
     *
     * @return Array that contains maximum amounts of money that can be collected
     */
    static int[] getMaxAmount() {
        return maxAmount;
    }

    /**
     * Set -method to receive updated maxAmount array.
     *
     * @param array Array that is set to be maxAmount
     */
    static void setMaxAmount(int [] array) {
        maxAmount = array;
    }

    /**
     * Get -method to collect the lastTimeArray array.
     *
     * @return Array that contains last time the MoneyButton has been clicked
     */
    static int[] getLastTimeClicked() {
        return lastTimeClicked;
    }

    /**
     * Set -method to receive updated lastTimeClicked array.
     *
     * @param array Array that is set to be lastTimeClicked
     */
    static void setLastTimeClicked(int [] array) {
        lastTimeClicked = array;
    }

    /**
     * Get -method to collect the multipliers array.
     *
     * @return Array that contains multipliers of the farmScreens
     */
    static float[] getMultipliers() {
        return multipliers;
    }

    /**
     * Set -method to receive updated multipliers array.
     *
     * @param array Array that is set to be multipliers
     */
    static void setMultipliers(float [] array) {
        multipliers = array;
    }

    /**
     * Counts money that can be collected based on the time that has passed since the lastTimeClicked.
     *
     * @param timeNowInSec Time when counted
     * @return amount of money the farm or boat has produced
     */
    private int countMoney(int timeNowInSec) {
        int countedMoney;
        int timePassedInSec = timeNowInSec - lastTimeClicked[index];

        int possibleAmount = (int)(timePassedInSec*multipliers[index]);
        if(possibleAmount > maxAmount[index]) {
            countedMoney = maxAmount[index];
        } else {
            countedMoney = possibleAmount;
        }
        return countedMoney;
    }

    /**
     * Draws the Texture with specific location and size. Sets Actor Touchable if it has money to collect.
     * Moves coin back to original position when it has completed its action.
     *
     * @param batch Batch is used to handle the drawing
     * @param alpha Alpha determines transparency of the drawing
     */
    public void draw(Batch batch, float alpha) {
        int potentialMoney = countMoney(Utils.getCurrentTimeInSeconds());
        setTouchable(Touchable.disabled);
        if(potentialMoney > 7 * multipliers[index] || potentialMoney < 2 * multipliers[index] && getX() != originalX) {
            setTouchable(Touchable.enabled);
            batch.draw(coin, getX(), getY(), getWidth(), getHeight());

            // if coin is moving, it can not be touched
            if(getX() != originalX && getY() != originalY) {
                setTouchable(Touchable.disabled);
            }
        }
        //when coin is att 300, 410 location, then it comes to back it's original location
        if(getX() == 300 && getY() == 410) {
            MoveToAction moveToAction = new MoveToAction();
            moveToAction.setPosition(originalX, originalY);
            moveToAction.setDuration(0f);
            MoneyButton.this.addAction(moveToAction);
        }
    }

    /**
     * Sets current time in seconds to the lastTimeClicked.
     */
    //called when new coin is added to stage. It set's the lastTimeClicked to lastTimeClicked;
    void setClicked() {
        lastTimeClicked[index] = Utils.getCurrentTimeInSeconds();
    }

    /**
     * Disposes the coinSound.
     */
    void disposeCoinSound() {
        coinSound.dispose();
    }
}
