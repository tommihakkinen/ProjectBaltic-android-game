package com.mygdx.tikoprojectbaltic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
/**
 * MapResearchButton is an object base class to create a Texture which extends an Actor and has a InputListener.
 *
 * @author  Jennina Färm
 * @author  Tommi Häkkinen
 * @version 2020.2204
 * @since 1.8
 */
public class MapResearchButton extends Actor {

    /**
     * Main that is used to switch the screen.
     */
    private Main main;

    /**
     * Texture that is created and drawn.
     */
    private Texture button;

    /**
     * Constructor. Sets x- and y-coordinates, width, height and boundaries of the Boat.
     * It contains anonymous InputListener to detect touchDown of the MapResearchButton to switch screen.
     *
     * @param m Main that contains meta data
     * @param x x-coordinate of the Actor
     * @param y y-coordinate of the Actor
     */

    MapResearchButton(Main m, int x, int y) {
        main = m;
        button = new Texture(Gdx.files.internal("researchmapicon.png"));
        setX(x);
        setY(y);
        setWidth(button.getWidth() / 6f);
        setHeight(button.getWidth() / 6f);
        setBounds(getX(), getY(), getWidth(), getHeight());

        addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                main.switchScreen(4, 0);
                return true;
            }
        });
    }

    /**
     * Draws the Texture with specific location and size.
     *
     * @param batch batch that is used in draw method
     * @param alpha used to handle transparency
     */

    public void draw(Batch batch, float alpha) {
        batch.draw(button, getX(), getY(), getWidth(), getHeight());
    }
}
