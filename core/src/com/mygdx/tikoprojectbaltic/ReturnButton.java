package com.mygdx.tikoprojectbaltic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * ReturnButton is an object base class to create a button which switches screen to previous screen.
 *
 * @author  Jennina Färm
 * @author  Tommi Häkkinen
 * @version 2020.2204
 * @since 1.8
 */

public class ReturnButton extends Actor {

    /**
     * Main for managing screen switch.
     */
    private Main main;

    /**
     * Texture of the Actor.
     */
    private Texture button;

    /**
     * Index of the screen where the Actor is switching screen to when clicked.
     */
    private int index;

    /**
     * Constructor. Sets index, Texture, x- and y-coordinates, width and height of the ReturnButton.
     * It contains anonymous InputListener to detect touchDown of the ReturnButton to switch screen.
     *
     * @param m Main contains meta data
     * @param i Index of the screen where the Actor is switching screen to when clicked
     */
    ReturnButton(Main m, int i) {

        index = i;
        main = m;
        button = new Texture(Gdx.files.internal("back_button.png"));
        setX(6);
        setY(390);
        setWidth(button.getWidth()/4f);
        setHeight(button.getHeight()/4f);
        setBounds(getX(), getY(), getWidth(), getHeight());

        addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                main.switchScreen(index, 0);
                Save.saveVariables();
                Save.loadVariables();
                return true;
            }
        });
    }

    /**
     * Draw method of the Actor. Draws texture set to the ReturnButton.
     *
     * @param batch handles drawing
     * @param alpha used to handle transparency
     */
    public void draw(Batch batch, float alpha) {
        batch.draw(button, this.getX(), this.getY(), getWidth(), getHeight());
    }
}
