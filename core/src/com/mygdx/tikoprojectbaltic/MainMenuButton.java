package com.mygdx.tikoprojectbaltic;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * MainMenuButton is an object base class to create buttons to mainMenuScreen.
 *
 * @author  Jennina Färm
 * @author  Tommi Häkkinen
 * @version 2020.2204
 * @since 1.8
 */

public class MainMenuButton extends Actor {

    /**
     * Main to switch screens and get Skin.
     */
    private Main main;

    /**
     * Index of the button to control which action they have.
     */
    private int index;

    /**
     * TextButton that is created and drawn.
     */
    private Button textButton;

    /**
     * Constructor. Sets index, width, height, TextButton and position of the MainMenuButton.
     * It contains anonymous InputListener to detect touchDown of the MainMenuButton to switch screen.
     *
     * @param m Main that contains meta data
     * @param label String to put in the textButton
     * @param i Index of the TextButton
     */

    MainMenuButton(Main m, String label, int i) {
        main = m;
        index = i;

        setWidth(150);
        setHeight(50);

        textButton = new TextButton(label, main.getMySkin());
        textButton.setSize(getWidth(), getHeight());
        textButton.setPosition(main.WIDTH / 2f - getWidth() / 2f, main.HEIGHT / 3f - getHeight() / 2f - index * getHeight());
        textButton.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (index == 0) {
                    main.switchScreen(2, 0);
                } else if (index == 1) {
                    main.switchScreen(5, 0);
                }
                return true;
            }
        });
    }

    /**
     * Get-method to collect TextButton of the Object.
     *
     * @return TextButton that is created
     */

    public Button getButton() {
        return textButton;
    }
}
