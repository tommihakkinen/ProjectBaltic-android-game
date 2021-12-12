package com.mygdx.tikoprojectbaltic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;

/**
 * InfoLabel is a object base class to create a TextArea with information of given info.
 *
 * @author  Jennina Färm
 * @author  Tommi Häkkinen
 * @version 2020.2204
 * @since 1.8
 */

public class InfoLabel extends Actor {

    /**
     * TextArea that is created and drawn.
     */
    private TextArea textArea;

    /**
     * Constructor. Sets x- and y-coordinates, width and height of the TextArea.
     *
     * @param main Main where the skin is asked
     * @param info String that contains the content of the TextArea
     * @param x x-coordinate of the TextArea
     * @param y y-coordinate of the TextArea
     * @param width width of the TextArea
     * @param height height of the TextArea
     */

    InfoLabel(Main main, String info, int x, int y, int width, int height) {

        textArea = new TextArea(info, main.getMySkin());
        textArea.setX(x);
        textArea.setY(y);
        textArea.setWidth(width);
        textArea.setHeight(height);
    }

    /**
     * Calls the TextArea draw(batch, parentAlpha)-method.
     *
     * @param batch batch that is used in draw method
     * @param parentAlpha used to handle transparency
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        textArea.draw(batch, parentAlpha);
    }

}
