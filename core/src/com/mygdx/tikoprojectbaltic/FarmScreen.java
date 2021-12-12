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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

/**
 * FarmScreen is an object base class to create a Screen for farms.
 *
 * @author  Jennina Färm
 * @author  Tommi Häkkinen
 * @version 2020.2204
 * @since 1.8
 */

public class FarmScreen implements Screen {

    /**
     * Array that contains the amount of workers on the farms.
     */
    private static int [] workerAmount = new int[4];
    /**
     * 2d array that contains the y-coordinates of the FarmButton objects.
     */
    private static float [][] actorY = new float[4][19];
    /**
     * Array for Actors of the Stage.
     */
    private static Array<Actor> actors;
    /**
     * Main contains meta data of the game.
     */
    private Main main;
    /**
     * Index to identify the farmScreen.
     */
    private int farmIndex;
    /**
     * Stage to place Actors that are not moving with the camera.
     */
    private Stage stage;
    /**
     * Stage to place Actors that are still in the camera viewport.
     */
    private Stage stageUI;
    /**
     * Amount of farm upgrades.
     */
    private int upgradeAmount = 19;
    /**
     * Actor of the ReturnButton to get back to previous screen.
     */
    private ReturnButton returnButton;
    /**
     * Actor for the farm upgrade textures.
     */
    private FarmUpgrades farmUpgrades;
    /**
     * ArrayList containing FarmButton Actors for buying farm upgrades.
     */
    private ArrayList<FarmButton> farmButtons = new ArrayList<>();
    /**
     * Actor for FarmWorker buying Button.
     */
    private FarmWorker workerButton;
    /**
     * Actor for showing how many workers a farm has.
     */
    private WorkerLabel workerLabel;
    /**
     * Actor for showing player's current amount of money.
     */
    private MoneyLabel moneyLabel;
    /**
     * Actor for showing current income per minute on the farm.
     */
    private IncomeLabel incomeLabel;
    /**
     * Actor to show information about the upgrades.
     */
    private InfoLabel infoLabel;
    /**
     * Actor that creates background for the screen.
     */
    private Background farmBackground;
    /**
     * Holds the Vector of dragging in the screen.
     */
    private Vector2 dragNew, dragOld;
    /**
     * Camera to move the viewport.
     */
    private Camera camera;
    /**
     * Array for the Tutorial Actors.
     */
    private Tutorial [] tutorial_4_Actors = new Tutorial[6];

    /**
     * Constructor. Creates all the private variables, arrays and objects.
     *
     * @param m Main contains meta data of the game
     * @param i Index of the FarmScreen
     */
    FarmScreen(Main m, int i) {
        main = m;
        farmIndex = i;
        SpriteBatch batch = main.getBatch();

        stage = new Stage(new FitViewport(main.WIDTH, main.HEIGHT), batch);
        stageUI = new Stage(new FitViewport(main.WIDTH, main.HEIGHT), batch);

        camera = stage.getCamera();

        checkIfTutorial();
        createActors();
        addActors();
        setActorY();
    }

    /**
     * Get -method to collect 2d array which contains y-coordinates of the stage Actors.
     *
     * @return 2d array of y-coordinates of the stage Actors
     */
    static float[][] getFarmActorYArray() {
        return actorY;
    }

    /**
     * Set -method to receive saved actorY array.
     *
     * @param array saved actorY array
     */
    static void setFarmActorYArray(float [][] array) {
        actorY = array;
    }

    /**
     * Get -method to collect array which contains amount of workers.
     *
     * @return array of amount of workers
     */
    static int[] getWorkerAmountArray() {
        return workerAmount;
    }

    /**
     * Set -method to receive saved workerAmount array.
     *
     * @param array saved workerAmount array
     */
    static void setWorkerAmountArray(int [] array) { workerAmount = array;}

    /**
     * Checks if tutorial is needed to set true, created and placed on the stage.
     */
    private void checkIfTutorial() {
        if(Tutorial.tutorial_4 && Tutorial.tutorial && farmIndex == 0) {
            Tutorial.tutorial_4_Stages[0] = true;
            for(int j=0;j<4;j++) {
                tutorial_4_Actors[j] = new Tutorial(4, j);
            }
            stage.addActor(tutorial_4_Actors[0]);
        }
    }

    /**
     * Creates all the Actors of the constructor.
     */
    private void createActors() {
        farmBackground = new Background(new Texture(Gdx.files.internal("farm-background.png")));
        farmBackground.setSize(main.WIDTH, main.HEIGHT);
        returnButton = new ReturnButton(main, 2);
        moneyLabel = new MoneyLabel(main);
        workerLabel = new WorkerLabel(main, this);
        incomeLabel = new IncomeLabel(main, farmIndex);
        farmUpgrades = new FarmUpgrades(farmIndex);
        for(int i=0; i<upgradeAmount; i++) {
            farmButtons.add(new FarmButton(main, this, i, farmIndex));
        }
        workerButton = new FarmWorker(main, this);
    }

    /**
     * Places the Actors of the constructor to the Stages.
     */
    private void addActors() {
        for(int i=0; i<upgradeAmount; i++) {
            stage.addActor(farmButtons.get(i).getButton());
        }
        stage.addActor(workerButton.getButton());
        stageUI.addActor(farmBackground);
        stageUI.addActor(farmUpgrades);
        stageUI.addActor(incomeLabel);
        stageUI.addActor(moneyLabel);
        stageUI.addActor(workerLabel);
        stageUI.addActor(farmButtons.get(0));
        stageUI.addActor(returnButton);
    }

    /**
     * Sets the current y-coordinate of the FarmButtons
     */
    private void setActorY() {
        actors = stage.getActors();
        for(int j=0; j<upgradeAmount; j++) {
            if(actorY[farmIndex][j] != 0) {
                actors.get(j).setY(actorY[farmIndex][j]);
            }
        }
    }

    @Override
    public void show() {
    }

    /**
     * Adds a worker to the workerAmount array.
     */
    void addWorker() {
        if(workerAmount[farmIndex] < 4) {
            workerAmount[farmIndex]++;
            Save.saveVariables();
            Save.loadVariables();
        }
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

    /**
     * Sets InfoLabel visible or not.
     *
     * @param visible Boolean if InfoLabel is visible or not
     */
    void setInfoVisible(boolean visible) {
        infoLabel.setVisible(visible);
    }

    /**
     * Get method to collect the amount of workers in this screen.
     *
     * @return integer amount of workers
     */
    int getWorkerAmount() {
        return workerAmount[farmIndex];
    }

    /**
     * Updates camera input and location, checks if tutorial is on and if upgrades are available and calls draw and act-methods of the Stages.
     *
     * @param delta delta time of player's device
     */
    @Override
    public void render(float delta) {

        handleInput();
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Tutorial.tutorial && farmIndex == 0) {
            manageTutorial_4();
        }

        setUpgradesAvailable();

        stageUI.draw();
        stageUI.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        stage.act(Gdx.graphics.getDeltaTime());
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
                ((OrthographicCamera)camera).translate(0, dragNew.y - dragOld.y);
                dragOld = dragNew;
            }
        }

        camera.position.x = MathUtils.clamp(camera.position.x, 300, 500);
        camera.position.y = MathUtils.clamp(camera.position.y, -600, 250);
    }

    /**
     * Controls how the tutorial is advancing.
     */
    private void manageTutorial_4() {

        for(int i=0; i<4; i++) {
            if(Tutorial.tutorial_4_Stages[i] && Tutorial.tutorial_4) {
                stageUI.addActor(tutorial_4_Actors[i]);
            }
        }
        if(!Tutorial.tutorial_4) {
            for(int j=0; j<4; j++) {
                tutorial_4_Actors[j].setVisible(false);
            }
            Tutorial.tutorial = false;
            Save.saveVariables();
            Save.loadVariables();
        }
    }

    /**
     * Checks if certain upgrade is bought and farm has enough workers and sets next upgrade available.
     */
    private void setUpgradesAvailable() {
        boolean [][] boughtArray = FarmButton.getBoughtArray();

        farmButtons.get(0).setAvailable();
        farmButtons.get(4).setAvailable();

        if(boughtArray[farmIndex][0]) {
            farmButtons.get(1).setAvailable();
        }
        if(boughtArray[farmIndex][1]) {
            farmButtons.get(2).setAvailable();
        }

        if(workerAmount[farmIndex] >= 1) {
            farmButtons.get(6).setAvailable();
            if(boughtArray[farmIndex][1]) {
                farmButtons.get(9).setAvailable();
            }
            for(int i=0; i<2; i++) {
                if(boughtArray[farmIndex][i+6]) {
                    farmButtons.get(i+7).setAvailable();
                }
            }
            if(boughtArray[farmIndex][9]) {
                farmButtons.get(10).setAvailable();
            }
            if(boughtArray[farmIndex][10]) {
                farmButtons.get(11).setAvailable();
                farmButtons.get(13).setAvailable();
            }
            if(boughtArray[farmIndex][13]) {
                farmButtons.get(14).setAvailable();
                farmButtons.get(15).setAvailable();
            }
        }

        if(workerAmount[farmIndex] >= 2) {
            if(boughtArray[farmIndex][2]) {
                farmButtons.get(3).setAvailable();
            }
            if(boughtArray[farmIndex][4] && boughtArray[farmIndex][8]){
                farmButtons.get(5).setAvailable();
            }
            if(boughtArray[farmIndex][10]) {
                farmButtons.get(12).setAvailable();
            }
        }

        if(workerAmount[farmIndex] >= 3) {
            if(boughtArray[farmIndex][14]) {
                farmButtons.get(16).setAvailable();
            }
            if(boughtArray[farmIndex][15]) {
                farmButtons.get(17).setAvailable();
                farmButtons.get(18).setAvailable();
            }
        }
    }

    /**
     * Updates every following FarmButton y-coordinate of the given buttonIndex.
     *
     * @param buttonIndex y-coordinates are moved relative to this buttonIndex
     */
    void setFarmButtonY(int buttonIndex) {
        actors = stage.getActors();
        for(int i=0; i<upgradeAmount; i++) {
            if(buttonIndex == i) {
                for(int j=i; j<upgradeAmount; j++) {
                    moveY(actors.get(j), actors.get(j).getY(), j);
                }
            }
        }
    }

    /**
     * Moves Actor y-coordinates up by 60 pixels. Saves new location to the actorY array and saves and loads new info.
     *
     * @param actor Actor that is wanted to move
     * @param y Y-coordinate before the moving
     * @param index Index of the moved Actor
     */
    private void moveY(Actor actor, float y, int index) {
        actor.setY(y+60);
        actorY[farmIndex][index] = actor.getY();
        Save.saveVariables();
        Save.loadVariables();
    }

    /**
     * Get -method to collect stage
     *
     * @return stage of the farmScreen
     */
    Stage getStage() {
        return stage;
    }

    /**
     * Get -method to collect stageUI
     *
     * @return stageUI of the farmScreen
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
     * Disposes the Stages and button sounds.
     */
    @Override
    public void dispose() {
        stage.dispose();
        stageUI.dispose();
        for(int i=0; i<upgradeAmount; i++) {
            farmButtons.get(i).disposeCowSound();
        }
        farmUpgrades.disposeCowSound();
    }
}
