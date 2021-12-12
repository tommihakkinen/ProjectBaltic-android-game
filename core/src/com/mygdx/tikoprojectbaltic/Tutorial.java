package com.mygdx.tikoprojectbaltic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Tutorial is an object base class to create a Texture which extends an Actor.
 *
 * @author  Jennina Färm
 * @author  Tommi Häkkinen
 * @version 2020.2204
 * @since 1.8
 */
public class Tutorial extends Actor {

    /**
     * Boolean if tutorial is true or not.
     */
    public static boolean tutorial = true;
    /**
     * Boolean if first part is true or not.
     */
    public static boolean tutorial_1 = true;
    /**
     * Boolean if second part is true or not.
     */
    public static boolean tutorial_2 = true;
    /**
     * Boolean if third part is true or not.
     */
    public static boolean tutorial_3 = true;
    /**
     * Boolean if fourth part is true or not.
     */
    public static boolean tutorial_4 = true;

    public static boolean endGame = true;
    /**
     * Boolean array containing information if first part stages have been completed.
     */
    public static boolean [] tutorial_1_Stages = new boolean [4];
    /**
     * Boolean array containing information if second part stages have been completed.
     */
    public static boolean [] tutorial_2_Stages = new boolean [5];
    /**
     * Boolean array containing information if third part stages have been completed.
     */
    public static boolean [] tutorial_3_Stages = new boolean [5];
    /**
     * Boolean array containing information if fourth part stages have been completed.
     */
    public static boolean [] tutorial_4_Stages = new boolean [5];
    /**
     * Index to identify the Tutorial.
     */
    private int index;
    /**
     * Texture that is created and drawn in Finnish.
     */
    private Texture tutorialTexture;
    /**
     * Texture that is created and drawn is English.
     */
    private Texture tutorialTexture_EN;
    /**
     * Width of the Texture.
     */
    private float width = 730f;
    /**
     * Height of the Texture.
     */
    private float height = 400f;

    /**
     * Constructor. Creates all the private variables, arrays and Textures and sets x- and y-coordinates of the MapButton.
     * It contains anonymous InputListener to detect touchDown to switch Texture.
     *
     * @param tutorialIndex Index to identify the Tutorial
     * @param stageIndex Index to identify the stage
     */
    Tutorial(int tutorialIndex, int stageIndex) {

        switch (tutorialIndex) {

            case 1:
                index = stageIndex;
                tutorialTexture = new Texture(Gdx.files.internal("tutorial1/tutorial-1-" + index + ".png"));
                tutorialTexture_EN = new Texture(Gdx.files.internal("tutorial1-EN/tutorial-1-" + index + "-en.png"));
                setX(200);
                setY(0);
                setWidth(width);
                setHeight(height);
                setBounds(getX(), getY(), getWidth(), getHeight());

                addListener(new InputListener() {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (index < 3) {
                            tutorial_1_Stages[++index] = true;
                        } else {
                            tutorial_1 = false;
                        }
                        return true;
                    }
                });
                break;

            case 2:
                index = stageIndex;
                tutorialTexture = new Texture(Gdx.files.internal("tutorial2/tutorial-2-" + index + ".png"));
                tutorialTexture_EN = new Texture(Gdx.files.internal("tutorial2-EN/tutorial-2-" + index + "-en.png"));
                setX(400);
                setY(0);
                setWidth(width);
                setHeight(height);
                setBounds(getX(), getY(), getWidth(), getHeight());

                addListener(new InputListener() {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (index < 2) {
                            tutorial_2_Stages[++index] = true;
                        } else {
                            tutorial_2 = false;
                        }
                        return true;
                    }
                });
                break;

            case 3:
                index = stageIndex;
                tutorialTexture = new Texture(Gdx.files.internal("tutorial3/tutorial-3-" + index + ".png"));
                tutorialTexture_EN = new Texture(Gdx.files.internal("tutorial3-EN/tutorial-3-" + index + "-en.png"));
                setX(350);
                setY(0);
                setWidth(width);
                setHeight(height);
                setBounds(getX(), getY(), getWidth(), getHeight());

                addListener(new InputListener() {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (index < 3) {
                            tutorial_3_Stages[++index] = true;
                        } else {
                            tutorial_3 = false;
                        }
                        return true;
                    }
                });
                break;

            case 4:
                index = stageIndex;
                tutorialTexture = new Texture(Gdx.files.internal("tutorial4/tutorial-4-" + index + ".png"));
                tutorialTexture_EN = new Texture(Gdx.files.internal("tutorial4-EN/tutorial-4-" + index + "-en.png"));
                setX(30);
                setY(0);
                setWidth(width);
                setHeight(height);
                setBounds(getX(), getY(), getWidth(), getHeight());

                addListener(new InputListener() {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (index < 3) {
                            tutorial_4_Stages[++index] = true;
                        } else {
                            tutorial_4 = false;
                        }
                        return true;
                    }
                });
                break;

            case 5:
                tutorialTexture = new Texture(Gdx.files.internal("endgame/end_screen_fin.png"));
                tutorialTexture_EN = new Texture(Gdx.files.internal("endgame/end_screen_en.png"));
                setX(0);
                setY(0);
                setWidth(800);
                setHeight(450);
                setBounds(getX(), getY(), getWidth(), getHeight());

                addListener(new InputListener() {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        endGame = false;
                        Save.saveVariables();
                        Save.loadVariables();
                        return true;
                    }
                });
                break;
        }
    }

    /**
     * Draw method of the Actor. Draws texture depending on the language used.
     *
     * @param batch handles drawing
     * @param alpha used to handle transparency
     */
    public void draw(Batch batch, float alpha) {
        if(Main.finnish){
            batch.draw(tutorialTexture, getX(), getY(), getWidth(), getHeight());
        } else {
            batch.draw(tutorialTexture_EN, getX(), getY(), getWidth(), getHeight());
        }
    }
}
