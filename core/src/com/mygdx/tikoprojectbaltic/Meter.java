package com.mygdx.tikoprojectbaltic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Meter is an object base class to create a Texture which extends an Actor.
 *
 * @author  Jennina Färm
 * @author  Tommi Häkkinen
 * @version 2020.2204
 * @since 1.8
 */
public class Meter extends Actor {

    /**
     * Main contains meta data of the game.
     */
    private Main main;
    /**
     * Array containing Textures of the meter.
     */
    private TextureRegion [] meterTexture;
    /**
     * Label for showing current balticSituation.
     */
    private Label balticMeter;
    /**
     * Amount of balticSituation.
     */
    private int balticSituation;

    /**
     * Constructor. Sets all the private variables, array and objects.
     * @param m Main contains meta data of the game
     */
    Meter(Main m) {
        main = m;
        meterTexture = new TextureRegion[9];
        for(int i=0; i<meterTexture.length; i++) {
            meterTexture[i] = new TextureRegion(new Texture (Gdx.files.internal("meter/meter" + i + ".png")));
        }

        balticSituation = main.getBalticSituation();

        balticMeter = new Label(Integer.toString(balticSituation), main.getMySkin());
        balticMeter.setX(753);
        balticMeter.setY(39);

        setX(730);
        setY(30);
        setWidth(meterTexture[0].getRegionWidth()/4f);
        setHeight(meterTexture[0].getRegionHeight()/4f);
        setBounds(getX(), getY(), getWidth(), getHeight());
    }

    /**
     * Draw method of the Actor. Draws texture and sets Label location depending on the balticSituation.
     *
     * @param batch handles drawing
     * @param alpha used to handle transparency
     */
    public void draw(Batch batch, float alpha) {
        balticSituation = main.getBalticSituation();
        int [] balticState = {-1, 7, 15, 27, 43, 60, 70, 85, 99, 100};
        for(int i=0; i<meterTexture.length; i++) {
            if(balticSituation > balticState[i] && balticSituation <= balticState[i+1]) {
                batch.draw(meterTexture[i], getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
            }
        }

        //move label location if the number is 10 or over
        if(balticSituation >= 10 && balticSituation < 30 || balticSituation >= 60 && balticSituation < 100) {
            balticMeter.setX(747);
        } else if(balticSituation >= 30 && balticSituation < 40 || balticSituation >= 50 && balticSituation < 60) {
            balticMeter.setX(746);
        } else if (balticSituation >= 40 && balticSituation < 49) {
            balticMeter.setX(745);
        } else if(balticSituation >= 100) {
            balticMeter.setX(741);
        }
        balticMeter.setText(balticSituation);
        balticMeter.draw(batch, alpha);
    }
}