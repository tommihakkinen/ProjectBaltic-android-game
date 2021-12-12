package com.mygdx.tikoprojectbaltic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Background is an object base class to create a Texture which extends an Actor.
 *
 * @author  Jennina Färm
 * @author  Tommi Häkkinen
 * @version 2020.2204
 * @since 1.8
 */

public class Background extends Actor {

    /**
     * Texture that is created and drawn.
     */
    private Texture texture;

    /**
     * Constructor. Sets Texture.
     *
     * @param t Texture that is created and drawn
     */
    public Background (Texture t) {
        texture = t;
    }

    /**
     * Draw method of the Actor. Draws texture set to the Background.
     *
     * @param batch handles drawing
     * @param alpha used to handle transparency
     */
    public void draw(Batch batch, float alpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }
}
