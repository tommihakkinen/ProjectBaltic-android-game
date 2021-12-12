package com.mygdx.tikoprojectbaltic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import java.util.ArrayList;

/**
 * MainMenuScreen is an object base class to create a Screen for main menu.
 *
 * @author  Jennina Färm
 * @author  Tommi Häkkinen
 * @version 2020.2204
 * @since 1.8
 */

public class MainMenuScreen implements Screen {

    /**
     * Main to get SpriteBatch and I18NBundle and pass over to other objects.
     */
    private Main main;

    /**
     * Stage to place Actors.
     */
    private Stage stage;

    /**
     * Background to create background for the screen.
     */
    private Background background;

    /**
     * ArrayList to contain MainMenuButtons.
     */
    private ArrayList<MainMenuButton> mainMenuButtons = new ArrayList<>();

    /**
     * Constructor. Sets stage, background, buttons and inputProcessor of the MainMenuScreen.
     *
     * @param m Main that contains metadata
     */
    MainMenuScreen(Main m) {
        main = m;
        SpriteBatch batch = main.getBatch();

        stage = new Stage(new FitViewport(main.WIDTH, main.HEIGHT), batch);

        background = new Background(new Texture(Gdx.files.internal("startscreen.png")));
        background.setSize(main.WIDTH, main.HEIGHT);

        createButtons();
        addActors();

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Creates buttons for the screen.
     */
    private void createButtons() {
        I18NBundle myBundle = main.getMyBundle();
        mainMenuButtons.add(new MainMenuButton(main, myBundle.get("main1"), 0));
        mainMenuButtons.add(new MainMenuButton(main, myBundle.get("main2"), 1));
    }

    /**
     * Sets Actors to the Stage.
     */
    private void addActors() {
        stage.addActor(background);
        stage.addActor(mainMenuButtons.get(0).getButton());
        stage.addActor(mainMenuButtons.get(1).getButton());
    }

    /**
     * Method to ask Stage of the screen.
     *
     * @return stage of the screen
     */
    Stage getStage() {
        return stage;
    }

    @Override
    public void show() {
    }

    /**
     * Calls for the draw -method of the Stage.
     *
     * @param delta delta time of the device
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
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
     * Calls the dispose -method of the Stage.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }


}
