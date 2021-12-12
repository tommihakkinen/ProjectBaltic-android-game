package com.mygdx.tikoprojectbaltic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

/**
 * WorkerLabel is an object base class to create a TextField with information of how many workers a specific farm has.
 *
 * @author  Jennina Färm
 * @author  Tommi Häkkinen
 * @version 2020.2204
 * @since 1.8
 */

public class WorkerLabel extends Actor {

    /**
     * TextField that is created and drawn.
     */
    private TextField workerLabel;
    /**
     * FarmScreen where the textField is added.
     */
    private FarmScreen farmScreen;
    /**
     * Texture that is created and  drawn.
     */
    private Texture worker;

    /**
     * Constructor. Sets FarmScreen, x- and y-coordinates, width, height and Texture for the TextField.
     *
     * @param main Main that contains metadata
     * @param fs FarmScreen where the WorkerLabel TextField is added.
     */
    WorkerLabel(Main main,FarmScreen fs) {
        farmScreen = fs;

        workerLabel = new TextField(Integer.toString(farmScreen.getWorkerAmount()), main.getMySkin());
        workerLabel.setX(700);
        workerLabel.setY(410);
        workerLabel.setWidth(70);
        workerLabel.setHeight(33);
        worker = new Texture(Gdx.files.internal("worker.png"));
    }

    /**
     * Calls the TextField's draw()-method and draws the Texture over the TextField.
     * Sets up the current number of workers in the farmScreen and adds one to it since every farm has one worker as default.
     *
     * @param batch batch that is used in draw method
     * @param alpha used to handle transparency
     */

    public void draw(Batch batch, float alpha) {
        int workerAmount = farmScreen.getWorkerAmount() + 1;
        workerLabel.setText("         " + workerAmount);
        workerLabel.draw(batch, alpha);
        batch.draw(worker, workerLabel.getX() + 1, workerLabel.getY() + 1, worker.getWidth()/4.5f, worker.getHeight()/4.5f);
    }
}
