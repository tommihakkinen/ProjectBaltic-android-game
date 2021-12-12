package com.mygdx.tikoprojectbaltic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

/**
 * IncomeLabel is a object base class to create a TextField with information of how much income farm/farms are producing.
 *
 * @author  Jennina Färm
 * @author  Tommi Häkkinen
 * @version 2020.2204
 * @since 1.8
 */

public class IncomeLabel extends Actor {

    /**
     * TextField that is created and drawn.
     */
    private TextField label;
    /**
     * Index for identifying farm screens and map screen.
     */
    private int index;
    /**
     * Texture to identify the label content.
     */
    private Texture coin;


    /**
     * Constructor. Sets index, Skin, x- and y-coordinates, width, height and Texture of the label.
     *
     * @param main Main where the skin is asked.
     * @param i index value for identifying farms and map.
     */
    IncomeLabel(Main main, int i) {
        index = i;

        label = new TextField(Integer.toString(0), main.getMySkin());
        label.setX(470);
        label.setY(410);
        label.setWidth(220);
        label.setHeight(33);
        coin = new Texture(Gdx.files.internal("coin-icon.png"));
    }

    /**
     * Checks how many farms are bought and adds their income multipliers together.
     * Counts income/min based on the multipliers and sets it to the label.
     * Calls the draw() -method of the TextLabel and draws coin-texture on it.
     *
     * @param batch batch that is used in draw method
     * @param alpha used to handle transparency
     */

    public void draw(Batch batch, float alpha) {
        boolean [] farmsBought = MapButton.getFarmLocks();
        int boughtAmount = 0;
        for(boolean bought : farmsBought) {
            if(bought){
                boughtAmount++;
            }
        }
        float [] multipliers = MoneyButton.getMultipliers();
        int incomePerMin = 0;
        if(index < 5) {
            incomePerMin = (int)(multipliers[index] * 60);
        } else {
            for(int i=0; i<boughtAmount; i++) {
                incomePerMin += (int)(multipliers[i] * 60);
            }
        }
        label.setText("       /min " + incomePerMin);
        label.draw(batch, alpha);
        batch.draw(coin, label.getX(), label.getY() + 2, 30, 30);
    }
}

