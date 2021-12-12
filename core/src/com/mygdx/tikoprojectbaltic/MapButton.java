package com.mygdx.tikoprojectbaltic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.I18NBundle;

/**
 * MapButton is an object base class to create Texture with ActorGestureListener.
 *
 * @author  Jennina Färm
 * @author  Tommi Häkkinen
 * @version 2020.2204
 * @since 1.8
 */
public class MapButton extends Actor {

    /**
     * Array for storing if the MapButtons are bought or not.
     */
    private static boolean [] bought = new boolean[4];
    /**
     * Main to handle money, switch screens and ask I18NBundle.
     */
    private Main main;
    /**
     * MapScreen to handle coins (MoneyButtons) and infoLabels.
     */
    private MapScreen mapScreen;
    /**
     * Texture that is created and drawn.
     */
    private Texture button;
    /**
     * Texture that is created and drawn if MapButton has not been bought.
     */
    private Texture lock;
    /**
     * InfoLabel of the not yet bought MapButton.
     */
    private InfoLabel infoLabel;
    /**
     * Index to identify the MapButton.
     */
    private int index;
    /**
     * Cost of the MapButton.
     */
    private int cost;
    /**
     * Sound that plays when a new farm is unlocked.
     */
    private Sound farmSound = Gdx.audio.newSound(Gdx.files.internal("sounds/upgrade_buy.wav"));

    /**
     * Constructor. Creates all the private variables and sets x- and y-coordinates, Textures, width and height of the MapButton.
     * It contains anonymous ActorGestureListener to detect touchDown, enter and exit of the MoneyButton to switch screens or show more info.
     *
     * @param m Main contains meta data of the game
     * @param ms MapScreen for InfoLabel and coin handling
     * @param x X-coordinate of the MapButton
     * @param y Y-coordinate of the MapButton
     * @param i Index of the MapButton
     */
    MapButton(Main m, MapScreen ms, int x, int y, int i) {

        index = i;
        main = m;
        mapScreen = ms;
        I18NBundle myBundle = main.getMyBundle();
        infoLabel = new InfoLabel(main, myBundle.get("farmInfo" + index), 20, 120, 300, 70);
        cost = (int)(1000* Math.pow(10, (i-1)));
        button = new Texture(Gdx.files.internal("farm-icon.png"));
        lock = new Texture(Gdx.files.internal("lock-icon.png"));
        setX(x);
        setY(y);
        setWidth(button.getWidth()/7f);
        setHeight(button.getHeight()/7f);
        setBounds(getX(), getY(), getWidth(), getHeight());

        //set first farm bought
        if(index == 0) {
            bought[index] = true;
        }

        addListener(new ActorGestureListener(2.5f, 0f, 0.25f, 0f) {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if(bought[index]) {
                    main.switchScreen(3, index);
                } else {
                    //mapScreen.setInfoVisible(false);
                    int currentMoney = main.nonStaticGetMoney();
                    boolean [] researchBooleans = ResearchButton.getResearchBooleans();
                    if(index == 2) {
                        //requires livestock research
                        if(researchBooleans[6]) {
                            if(currentMoney >= cost) {
                                bought[index] = true;
                                if (Main.getSound()) {
                                    farmSound.play(0.5f);
                                }
                                main.nonStaticSetMoney(currentMoney-cost);
                                mapScreen.setCoinVisible(index);
                                Save.saveVariables();
                                Save.loadVariables();
                            }
                        }
                    } else if(index == 3) {
                        //requires organic farm research
                        if(researchBooleans[16]) {
                            if(currentMoney >= cost) {
                                bought[index] = true;
                                if (Main.getSound()) {
                                    farmSound.play(0.6f);
                                }
                                main.nonStaticSetMoney(currentMoney-cost);
                                mapScreen.setCoinVisible(index);
                                Save.saveVariables();
                                Save.loadVariables();
                            }
                        }
                    } else {
                        if (currentMoney >= cost) {
                            bought[index] = true;
                            if (Main.getSound()) {
                                farmSound.play(0.6f);
                            }
                            main.nonStaticSetMoney(currentMoney - cost);
                            mapScreen.setCoinVisible(index);
                            Save.saveVariables();
                            Save.loadVariables();
                        }
                    }
                }
            }
            public boolean longPress(Actor actor, float x, float y) {
                if(!bought[index]) {
                    mapScreen.addInfoLabel(infoLabel);
                    mapScreen.setInfoVisible(true);

                }
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(!bought[index]) {
                    mapScreen.addInfoLabel(infoLabel);
                    mapScreen.setInfoVisible(false);
                }
            }
        });
    }

    /**
     * Get -method to collect the bought array.
     *
     * @return Array that contains if the MapButtons have been bought or not
     */
    static boolean[] getFarmLocks() {
        return bought;
    }

    /**
     * Set -method to receive updated bought array.
     *
     * @param array Array that is set to be bought
     */
    static void setFarmLocksArray(boolean[] array) {
        bought = array;
    }

    /**
     * Draws button Texture with specific location and size. If the MapButton has not been bought, draws a lock over the button Texture.
     *
     * @param batch Batch is used to handle the drawing
     * @param alpha Alpha determines transparency of the drawing
     */
    public void draw(Batch batch, float alpha) {
        batch.draw(button, this.getX(), this.getY(), getWidth(), getHeight());
        if(!bought[index] && index != 0) {
            batch.draw(lock, this.getX()+8, this.getY()+15, getWidth()/1.5f, getHeight()/1.5f);
        }
    }
}
