package com.mygdx.tikoprojectbaltic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * ResearchScreen is an object base class to create a Screen for the research view.
 *
 * @author  Jennina Färm
 * @author  Tommi Häkkinen
 * @version 2020.2204
 * @since 1.8
 */
public class ResearchScreen implements Screen {

    /**
     * Amount of researches.
     */
    private static int researchAmount = 19;
    /**
     * Array for the ResearchButtons.
     */
    private static ResearchButton [] researchButtons = new ResearchButton [19];
    /**
     * Main to get SpriteBatch and pass it on.
     */
    private Main main;
    /**
     * Stage to place Actors that are not moving with the camera.
     */
    private Stage stage;
    /**
     * Stage to place Actors that are still in the camera viewport.
     */
    private Stage stageUI;
    /**
     * Actor of the ReturnButton to get back to previous screen.
     */
    private ReturnButton returnButton;
    /**
     * Actor for showing player's amount of money.
     */
    private MoneyLabel moneyLabel;
    /**
     * Actor for showing information about the ResearchButtons.
     */
    private InfoLabel infoLabel;
    /**
     * Actor that creates a background for the ResearchScreen.
     */
    private Background background;
    /**
     * Camera to move the viewport.
     */
    private Camera camera;
    /**
     * Holds the Vector of dragging in the screen.
     */
    private Vector2 dragNew, dragOld;
    /**
     * Array for the Tutorial Actors.
     */
    private Tutorial [] tutorial_2_Actors = new Tutorial[6];

    /**
     * Constructor. Creates most of the private variables, arrays and objects and adds Actors to the stage.
     *
     * @param m Main contains meta data of the game
     */
    ResearchScreen(Main m) {
        main = m;
        SpriteBatch batch = main.getBatch();

        stage = new Stage(new FitViewport(main.WIDTH, main.HEIGHT), batch);
        stageUI = new Stage(new FitViewport(main.WIDTH, main.HEIGHT), batch);

        camera = stage.getCamera();

        createActors();

        checkIfTutorial();

        addActors();
    }

    /**
     * Checks if certain research is bought and sets next upgrade available.
     */
    private static void setResearchesAvailable() {
        boolean [] booleans = ResearchButton.getResearchBooleans();
        researchButtons[0].setResearchAvailable();
        researchButtons[4].setResearchAvailable();
        researchButtons[6].setResearchAvailable();

        for(int i=0; i<3; i++) {
            if(booleans[i]) {
                researchButtons[i+1].setResearchAvailable();
            }
        }
        if(booleans[0]) {
            researchButtons[9].setResearchAvailable();
        }
        if(booleans[4] && booleans[8]) {
            researchButtons[5].setResearchAvailable();
        }

        for(int i=0; i<2; i++) {
            if(booleans[i+6]) {
                researchButtons[i+7].setResearchAvailable();
            }
        }
        if(booleans[9]) {
            researchButtons[10].setResearchAvailable();
        }
        if(booleans[10]) {
            researchButtons[11].setResearchAvailable();
            researchButtons[12].setResearchAvailable();
            researchButtons[13].setResearchAvailable();
        }
        if(booleans[13]) {
            researchButtons[14].setResearchAvailable();
            researchButtons[15].setResearchAvailable();
        }
        if(booleans[14]) {
            researchButtons[16].setResearchAvailable();
        }
        if(booleans[15]) {
            researchButtons[17].setResearchAvailable();
            researchButtons[18].setResearchAvailable();
        }
    }

    /**
     * Creates all the Actors of the constructor.
     */
    private void createActors() {
        returnButton = new ReturnButton(main, 2);
        moneyLabel = new MoneyLabel(main);
        background = new Background(new Texture(Gdx.files.internal("researchBackground.png")));
        background.setPosition(0, -570);
        background.setSize(2118, 1200);
        for(int i=0; i<researchAmount; i++){
            researchButtons[i] = (new ResearchButton(main, this, i));
        }
    }

    /**
     * Checks if tutorial is needed to set true, created and placed to the stage.
     */
    private void checkIfTutorial() {
        if(Tutorial.tutorial_2 && Tutorial.tutorial) {
            Tutorial.tutorial_2_Stages[0] = true;
            for(int i=0;i<3;i++) {
                tutorial_2_Actors[i] = new Tutorial(2, i);
            }
            stageUI.addActor(tutorial_2_Actors[0]);
        }
    }

    /**
     * Places the Actors of the constructor to the Stages.
     */
    private void addActors() {
        stage.addActor(background);
        for(int i=0; i<researchAmount; i++) {
            Button button = researchButtons[i].getButton();
            stage.addActor(button);
        }
        stageUI.addActor(moneyLabel);
        stageUI.addActor(returnButton);
    }

    /**
     * Adds InfoLabel to the stage.
     *
     * @param il InfoLabel that is added
     */
    void addInfoLabel(InfoLabel il) {
        infoLabel = il;
        stageUI.addActor(infoLabel);
    }

    @Override
    public void show() {

    }

    /**
     * Sets InfoLabel visible or not.
     *
     * @param visible Boolean if InfoLabel is visible or not
     */
    void setInfoVisible(boolean visible) {
        infoLabel.setVisible(visible);
    }

    /**
     * Updates camera input and location, checks if tutorial is on and calls draw- and act -methods of the Stages.
     *
     * @param delta delta time of player's device
     */
    @Override
    public void render(float delta) {

        handleInput();
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(!Tutorial.tutorial) {
            setResearchesAvailable();
        }

        if(Tutorial.tutorial) {
            researchButtons[0].setResearchAvailable();
            returnButton.setVisible(false);
            manageTutorial_2();
        }

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        stageUI.act(Gdx.graphics.getDeltaTime());
        stageUI.draw();
    }

    /**
     * Controls how the tutorial is advancing in its second stage.
     */
    private void manageTutorial_2() {

        for(int i=0; i<3; i++) {
            if(Tutorial.tutorial_2_Stages[i] && Tutorial.tutorial_2) {
                stageUI.addActor(tutorial_2_Actors[i]);
            }
        }
        if(!Tutorial.tutorial_2 && Tutorial.tutorial) {
            returnButton.setVisible(true);
            for(int j=0; j<3; j++) {
                tutorial_2_Actors[j].setVisible(false);
            }
        }
    }

    /**
     * Handles input of the camera movement and updates the location of the camera.
     */
    private void handleInput() {

        if (Gdx.input.justTouched()){
            dragNew = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            dragOld = dragNew;
        }

        if (Gdx.input.isTouched()){
            dragNew = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            if (!dragNew.equals(dragOld)){
                ((OrthographicCamera)camera).translate(dragOld.x - dragNew.x, dragNew.y - dragOld.y);
                dragOld = dragNew;
            }
        }

        camera.position.x = MathUtils.clamp(camera.position.x, 400, 1720);
        camera.position.y = MathUtils.clamp(camera.position.y, -340, 375);
    }

    /**
     * Get -method to collect stage.
     *
     * @return stage of the ResearchScreen
     */
    Stage getStage() {
        return stage;
    }

    /**
     * Get -method to collect stageUI.
     *
     * @return stageUI of the ResearchScreen
     */
    Stage getStageUI() {
        return stageUI;
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
     * Disposes the stages.
     */
    @Override
    public void dispose() {
        stage.dispose();
        stageUI.dispose();
    }
}
