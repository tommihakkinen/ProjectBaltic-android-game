package com.mygdx.tikoprojectbaltic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Cloud is an object base class to create a Texture which extends an Actor.
 *
 * @author  Jennina Färm
 * @author  Tommi Häkkinen
 * @version 2020.2204
 * @since 1.8
 */

public class Cloud extends Actor {

    /**
     * X which is controlling the movement
     */
    private float textureX;
    /**
     * Y which is controlling the movement
     */
    private float textureY;
    /**
     * Texture that is created and drawn.
     */
    private Texture cloudTexture;
    /**
     * Object's speed on the x-axel.
     */
    private float cloudSpeedX;
    /**
     * Object's speed on the y-axel
     */
    private float cloudSpeedY;
    /**
     * Maximum distance the Cloud is moving.
     */
    private float maxDistance;

    /**
     * Constructor. Sets all the private variables and objects.
     *
     * @param x x-coordinate to be set as an originalX
     * @param y y-coordinate to be set as an originalY
     * @param max Maximum distance moved
     */
    Cloud(int x, int y, float max) {

        textureX = x;
        textureY = y;
        int random = MathUtils.random(3, 6);
        cloudSpeedX = 0.4f;
        cloudSpeedY = 0.2f;
        maxDistance = max;
        cloudTexture = new Texture(Gdx.files.internal("clouds/cloud" + MathUtils.random(1, 5) + ".png"));
        setX(textureX);
        setY(textureY);
        setWidth(cloudTexture.getWidth() / random);
        setHeight(cloudTexture.getHeight() / random);
    }

    /**
     * Moves the Cloud with set speed and resets its position when maxDistance is reached.
     */
    void cloudMove() {

        this.textureX += cloudSpeedX;
        this.textureY += cloudSpeedY;
        if (this.textureX >= maxDistance) {
            this.textureX = getX();
            this.textureY = getY();
        }
    }

    /**
     * Draw method of the Actor. Draws texture set to the Cloud.
     *
     * @param batch handles drawing
     * @param alpha used to handle transparency
     */
    public void draw(Batch batch, float alpha) {
        batch.draw(this.cloudTexture, this.textureX, this.textureY, this.getWidth(), this.getHeight());
    }
}

