package com.mygdx.tikoprojectbaltic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

/**
 * OptionsScreen is an object base class to create a Screen for the options.
 *
 * @author  Jennina Färm
 * @author  Tommi Häkkinen
 * @version 2020.2204
 * @since 1.8
 */
public class OptionsScreen implements Screen {

    /**
     * Main to ask I18NBundle and pass over to Actors.
     */
    private Main main;
    /**
     * Handles drawing.
     */
    private SpriteBatch batch;
    /**
     * Stage to place Actors that are not moving with the camera.
     */
    private Stage stage;
    /**
     * Actor that creates background for the MapScreen.
     */
    private Background background;
    /**
     * ArrayList containing OptionsButton Actors.
     */
    private ArrayList<OptionsButton> optionsButtons = new ArrayList<>();
    /**
     * Amount of optionsButtons.
     */
    private int buttonAmount = 5;
    /**
     * Actor of the ReturnButton to get back to previous screen.
     */
    private ReturnButton returnButton;

    /**
     * Constructor. Creates all of the private variables, arrays and objects and adds Actors to the stage.
     *
     * @param m Main contains meta data of the game
     */
    OptionsScreen(Main m) {
        main = m;
        batch = main.getBatch();
        stage = new Stage(new FitViewport(main.WIDTH, main.HEIGHT), batch);

        background = new Background(new Texture(Gdx.files.internal("startscreen2.png")));
        background.setSize(main.WIDTH, main.HEIGHT);

        createButtons();
        addActors();

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Creates OptionsButtons and ReturnButton.
     */
    private void createButtons() {
        I18NBundle myBundle = main.getMyBundle();
        for(int i=0; i<buttonAmount; i++) {
            optionsButtons.add(new OptionsButton(main, myBundle.get("options" + i), i));
        }
        returnButton = new ReturnButton(main, 1);
    }

    /**
     * Adds actors to the stage.
     */
    private void addActors() {
        stage.addActor(background);
        for(int i=0; i<buttonAmount; i++) {
            Button button = optionsButtons.get(i).getButton();
            stage.addActor(button);
        }
        stage.addActor(returnButton);
    }

    @Override
    public void show() {

    }

    /**
     * Calls draw- and act -methods of the Stage.
     *
     * @param delta delta time of player's device
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    /**
     * Get -method to collect stage.
     *
     * @return stage of the MapScreen
     */
    Stage getStage() {
        return stage;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    /**
     * Disposes stage.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
