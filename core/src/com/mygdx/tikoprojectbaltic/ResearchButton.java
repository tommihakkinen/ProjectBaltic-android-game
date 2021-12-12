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

/**
 * MapButton is an object base class to create create a buttons to ResearchScreen with ActorGestureListener.
 *
 * @author  Jennina Färm
 * @author  Tommi Häkkinen
 * @version 2020.2204
 * @since 1.8
 */
public class ResearchButton extends Actor {

    /**
     * Array that contains booleans if ResearchButtons are bought or not.
     */
    private static boolean [] researchBought = new boolean [19];
    /**
     * Main for money handling and asking I18NBundle.
     */
    private Main main;
    /**
     * ResearchScreen for InfoLabel.
     */
    private ResearchScreen researchScreen;
    /**
     * Button that created and drawn.
     */
    private Button button1;
    /**
     * Index to identify the Button.
     */
    private int index;
    /**
     * Cost of the ResearchButton.
     */
    private int cost;
    /**
     * Boolean to know if the ResearchButton is available to buy.
     */
    private boolean available = false;
    /**
     * Actor for showing information about the research.
     */
    private InfoLabel infoLabel;
    /**
     * Sound that plays when a research is completed.
     */
     private Sound researchSound = Gdx.audio.newSound(Gdx.files.internal("sounds/research_buy.wav"));

    /**
     * Constructor. Creates all the private variables and sets x- and y-coordinates, width and height of the ResearchButton.
     * It contains anonymous ActorGestureListener to detect touchDown, enter and exit of the ResearchButton to buy the Button or set InfoLabel visible or not.
     *
     * @param m Main contains meta data of the game
     * @param rs ResearchScreen handling InfoLabel
     * @param i Index of the ResearchButton
     */
    ResearchButton(Main m, ResearchScreen rs, int i) {
        main = m;
        researchScreen = rs;
        index = i;

        Skin mySkin = new Skin(Gdx.files.internal("mySkinTest/mySkinTest.json"));
        I18NBundle myBundle = main.getMyBundle();

        cost = Integer.parseInt(myBundle.get("researchCost" + index));
        infoLabel = new InfoLabel(main, myBundle.get("researchInfo" + index), 20, 50, 300, 310);

        float width = 200;
        float height = 60;

        button1 = new TextButton(myBundle.get("research" + i), mySkin);
        button1.setSize(width, height);
        button1.setPosition(Integer.parseInt(myBundle.get("researchX" + i)), Integer.parseInt(myBundle.get("researchY" + i)));

        if(researchBought[index]) {
            button1.getStyle().checked = button1.getStyle().down;
            button1.setChecked(true);
            button1.setDisabled(true);

        } else if(available){
            button1.setChecked(false);
        } else {
            button1.setChecked(true);
            button1.setDisabled(true);
        }

        button1.addListener(new ActorGestureListener(2.5f, 0f, 0.25f, 0f) {
            public void tap(InputEvent event, float x, float y, int count, int button) {
                int currentMoney = main.nonStaticGetMoney();
                //uncomment this to set InfoLabel invisible in desktop
                //researchScreen.setInfoVisible(false);

                if(currentMoney >= cost && !researchBought[index] && available) {
                    if(Main.getSound()) {
                        researchSound.play(0.5f);
                    }
                    main.nonStaticSetMoney(currentMoney - cost);
                    button1.getStyle().checked = button1.getStyle().down;
                    button1.setChecked(true);
                    button1.setDisabled(true);
                    researchBought[index] = true;
                    Save.saveVariables();
                    Save.loadVariables();
                }
            }
            public boolean longPress(Actor actor, float x, float y) {
                researchScreen.addInfoLabel(infoLabel);
                researchScreen.setInfoVisible(true);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                researchScreen.addInfoLabel(infoLabel);
                researchScreen.setInfoVisible(false);
            }
        });
    }

    /**
     * Set -method to receive updated researchBought array.
     *
     * @param research Array that is set to be researchBought
     */
    static void setBooleanArray(boolean[] research) {
        researchBought = research;
    }

    /**
     * Get -method to collect the researchBought array.
     *
     * @return Array that contains if the ResearchButton have been bought or not
     */
    static boolean [] getResearchBooleans() {
        return researchBought;
    }

    /**
     * Sets researchButton available if research is bought already.
     */
    void setResearchAvailable() {
        available = true;
        if(!researchBought[index]) {
            button1.setChecked(false);
            button1.setDisabled(false);
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
}
