package com.mygdx.tikoprojectbaltic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;


/**
 * MapBackground is an object base class to create Textures which extends an Actor.
 *
 * @author  Jennina Färm
 * @author  Tommi Häkkinen
 * @version 2020.2204
 * @since 1.8
 */
public class MapBackground extends Actor {

    /**
     * Texture that is created and drawn under condition.
     */
    private Texture map1;
    /**
     * Texture that is created and drawn under condition.
     */
    private Texture map2;
    /**
     * Texture that is created and drawn under condition.
     */
    private Texture map3;
    /**
     * Texture that is created and drawn under condition.
     */
    private Texture map4;

    /**
     * Constructor. Sets Textures.
     */
    MapBackground() {
        map1 = new Texture(Gdx.files.internal("mapBackGrounds/map.png"));
        map2 = new Texture(Gdx.files.internal("mapBackGrounds/map2.png"));
        map3 = new Texture(Gdx.files.internal("mapBackGrounds/map3.png"));
        map4 = new Texture(Gdx.files.internal("mapBackGrounds/map4.png"));
    }

    /**
     * Draw method of the Actor. Draws specific texture.
     *
     * @param batch handles drawing
     * @param alpha used to handle transparency
     */
    public void draw(Batch batch, float alpha) {
        int balticSituation = Main.getBalticSituation();
        if (balticSituation < 25) {
            batch.draw(map4, getX(), getY(), getWidth(), getHeight());
        } else if (balticSituation >= 25 && balticSituation < 50) {
            batch.draw(map3, getX(), getY(), getWidth(), getHeight());
        } else if (balticSituation >= 50 && balticSituation < 75) {
            batch.draw(map2, getX(), getY(), getWidth(), getHeight());
        } else if (balticSituation >= 75) {
            batch.draw(map1, getX(), getY(), getWidth(), getHeight());
        }
    }
}
