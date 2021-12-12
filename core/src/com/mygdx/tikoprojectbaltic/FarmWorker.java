package com.mygdx.tikoprojectbaltic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.I18NBundle;

/**
 * FarmWorker is an object base class to create a TextButton where you can buy worker to the farm.
 *
 * @author  Jennina Färm
 * @author  Tommi Häkkinen
 * @version 2020.2204
 * @since 1.8
 */

public class FarmWorker extends Actor {

    /**
     * To handle meta data.
     */
    private Main main;

    /**
     * Button that is created and drawn.
     */
    private Button button1;

    /**
     * The cost of the worker.
     */
    private int cost;

    /**
     * Sound that plays when a new worker is added to the farm.
     */
    private Sound workerSound = Gdx.audio.newSound(Gdx.files.internal("sounds/upgrade_buy.wav"));

    /**
     * Constructor. Sets index, Texture, x- and y-coordinates, width and height of the FarmWorker.
     * It contains anonymous InputListener to detect touchDown of the FarmWorker to buy the worker.
     *
     * @param m Main contains meta data of the game
     * @param farmScreen FarmScreen to add bought workers
     */
    FarmWorker(Main m, final FarmScreen farmScreen) {
        main = m;

        I18NBundle myBundle = main.getMyBundle();

        cost = Integer.parseInt(myBundle.get("workerCost"));

        setX(550);
        setY(340);

        button1 = new TextButton(myBundle.get("worker"), main.getMySkin());
        button1.setSize(200, 60);
        button1.setPosition(getX(), getY());
        button1.setDisabled(true);
        int workerAmount = farmScreen.getWorkerAmount();
        if (workerAmount >= 3) {
            button1.setVisible(false);
        }

        button1.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int currentMoney = main.nonStaticGetMoney();
                int workerAmount = farmScreen.getWorkerAmount();
                if (currentMoney >= cost && workerAmount < 3) {
                    if (Main.getSound()) {
                        workerSound.play(0.6f);
                    }
                    main.nonStaticSetMoney(currentMoney - cost);
                    farmScreen.addWorker();
                    button1.setChecked(false);
                    Save.saveVariables();
                    Save.loadVariables();
                }

                workerAmount = farmScreen.getWorkerAmount();
                if (workerAmount >= 3) {
                    button1.setVisible(false);
                }
                return true;
            }
        });
    }

    /**
     * Get -method to collect created Button.
     *
     * @return Button that is created
     */
    public Button getButton() {
        return button1;
    }
}
