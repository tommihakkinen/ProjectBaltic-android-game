package com.mygdx.tikoprojectbaltic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

/**
 * * MoneyLabel is an object base class to create a TextField with information of how much money player currently has.
 *  *
 *  * @author  Jennina Färm
 *  * @author  Tommi Häkkinen
 *  * @version 2020.2204
 *  * @since 1.8
 */

public class MoneyLabel extends Actor {

    /**
     * Main to ask the current money.
     */
    private Main main;
    /**
     * TextField that is created and drawn.
     */
    private TextField moneyLabel;
    /**
     * Variable to hold current amount of money.
     */
    private int currentMoney;
    /**
     * Texture to identify the label content.
     */
    private Texture coin;

    /**
     * Constructor. Sets Main, Skin, currentMoney, TextField, x- and y-coordinates, width, height and Texture of the label.
     *
     * @param m Main where the currentMoney is asked
     */

    MoneyLabel(Main m) {
        main = m;
        currentMoney = main.nonStaticGetMoney();

        moneyLabel = new TextField(Integer.toString(currentMoney), main.getMySkin());
        moneyLabel.setX(300);
        moneyLabel.setY(410);
        moneyLabel.setWidth(150);
        moneyLabel.setHeight(33);
        coin = new Texture(Gdx.files.internal("coin-icon.png"));
    }

    /**
     * Sets current amount of money to the label.
     * Calls the draw() -method of the TextLabel and draws coin-texture over it.
     *
     * @param batch batch that is used in draw method
     * @param alpha used to handle transparency
     */

    public void draw(Batch batch, float alpha) {
        currentMoney = main.nonStaticGetMoney();
        moneyLabel.setText("        " + currentMoney);
        moneyLabel.draw(batch, alpha);
        batch.draw(coin, moneyLabel.getX(), moneyLabel.getY() + 2, 30, 30);
    }
}
