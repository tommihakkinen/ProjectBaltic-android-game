package com.mygdx.tikoprojectbaltic;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

/**
 * MapScreen is an object base class to create a Screen for the map view.
 *
 * @author  Jennina Färm
 * @author  Tommi Häkkinen
 * @version 2020.2204
 * @since 1.8
 */
public class MapScreen extends ApplicationAdapter implements Screen {

    /**
     * Array containing booleans if the coins of farms are added.
     */
    private static boolean [] coinAdded = new boolean[4];
    /**
     * Main to ask SpriteBatch, handle balticSituation and pass over to Actors.
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
     * Amount of MapButtons and their MoneyButtons.
     */
    private int actorAmount = 4;
    /**
     * Actor that creates background for the MapScreen.
     */
    private MapBackground map;
    /**
     * ArrayList containing MoneyButton Actors for collecting money from MapButtons.
     */
    private ArrayList<MoneyButton> coins = new ArrayList<>();
    /**
     * ArrayList containing MapButton Actors for buying them and switching screens.
     */
    private ArrayList<MapButton> farms = new ArrayList<>();
    /**
     * Actor for switching screen to ResearchScreen.
     */
    private MapResearchButton research;
    /**
     * Actor of the ReturnButton to get back to previous screen.
     */
    private ReturnButton returnButton;
    /**
     * Actor for visualizing the progress of the game.
     */
    private Meter meter;
    /**
     * Actor for showing player's amount of money.
     */
    private MoneyLabel moneyLabel;
    /**
     * Actor for showing current income per minute of the farms.
     */
    private IncomeLabel incomeLabel;
    /**
     * Actor for showing information about the MapButtons that have not been bought.
     */
    private InfoLabel infoLabel;
    /**
     * Actor for the boat Texture.
     */
    private Boat boat1;
    /**
     * Actor for collecting money form the boat.
     */
    private MoneyButton boatCoins1;
    /**
     * Actor for the boat Texture.
     */
    private Boat boat2;
    /**
     * Actor for collecting money form the boat.
     */
    private MoneyButton boatCoins2;
    /**
     * Holds the Vector of dragging in the screen.
     */
    private Vector2 dragNew, dragOld;
    /**
     * Camera to move the viewport.
     */
    private Camera camera;
    /**
     * Array for the Tutorial stage 1 Actors.
     */
    private Tutorial [] tutorial_1_Actors = new Tutorial[4];
    /**
     * Array for the Tutorial stage 3 Actors.
     */
    private Tutorial [] tutorial_3_Actors = new Tutorial[4];
    /**
     * Boolean for the tutorial stage that activates when the game's completion condition is met.
     */
    private Tutorial endGame;
    /**
     * Actors for cloud textures.
     */
    private Cloud cloud;
    private Cloud cloud2;
    private Cloud cloud3;
    private Cloud cloud4;
    /**
     * Boolean to detect if boat1 is added
     */
    private boolean boat1Added = false;
    /**
     * Boolean to detect if boat2 is added
     */
    private boolean boat2Added = false;

    /**
     * Constructor. Creates most of the private variables, arrays and objects and adds Actors to the stage.
     *
     * @param m Main contains meta data of the game
     */
    MapScreen(Main m) {

        main = m;
        SpriteBatch batch = main.getBatch();

        stageUI = new Stage(new FitViewport(main.WIDTH, main.HEIGHT), batch);
        stage = new Stage(new FitViewport(main.WIDTH, main.HEIGHT), batch);
        camera = stage.getCamera();

        createActors();
        checkIfTutorial();

        addActorsToStage();

        ((OrthographicCamera)camera).zoom += 27f;
    }

    /**
     * Get -method to collect the coinAdded array.
     *
     * @return Array that contains if the coins are added to the stage or not
     */
    static boolean [] getCoinAdded() {
        return coinAdded;
    }

    /**
     * Set -method to receive updated coinAdded array.
     *
     * @param array Array that is set to be coinAdded
     */
    static void setCoinAdded(boolean [] array) {
        coinAdded = array;
    }

    /**
     * Creates all the Actors of the constructor.
     */
    private void createActors() {
        research = new MapResearchButton(main, 680, 270);
        returnButton = new ReturnButton(main, 1);
        moneyLabel = new MoneyLabel(main);
        incomeLabel = new IncomeLabel(main, 5);
        meter = new Meter(main);

        farms.add(new MapButton(main, this, 150, 50, 0));
        farms.add(new MapButton(main, this, 310, 210, 1));
        farms.add(new MapButton(main, this, 620, 100, 2));
        farms.add(new MapButton(main, this, 570, 310, 3));

        boat1 = new Boat(420, 100);
        boatCoins1 = new MoneyButton(main, 460, 130, 4);
        boatCoins1.setClicked();
        boat2 = new Boat(520, 200);
        boatCoins2 = new MoneyButton(main, 560, 230, 5);
        boatCoins2.setClicked();


        coins.add(new MoneyButton(main,197, 82, 0));
        coins.add(new MoneyButton(main,357, 242, 1));
        coins.add(new MoneyButton(main,667, 133, 2));
        coins.add(new MoneyButton(main,617, 342, 3));

        cloud = new Cloud(-540, 0, 900);
        cloud2 = new Cloud(-600, -150, 1100);
        cloud3 = new Cloud(-600, -500, 800);
        cloud4 = new Cloud(30, -40, 900);

        endGame = new Tutorial(5, 1);

        map = new MapBackground();
        map.setSize(main.WIDTH, main.HEIGHT);
        map.addListener(new ActorGestureListener() {
            public void zoom (InputEvent event, float initialDistance, float distance) {
                ((OrthographicCamera)camera).zoom = ((initialDistance / distance) * ((OrthographicCamera)camera).zoom);
                camera.update();
            }
        });
    }

    /**
     * Checks if tutorial is needed to set true, created and placed to the stage.
     */
    private void checkIfTutorial() {
        if (Tutorial.tutorial) {
            Tutorial.tutorial_1_Stages[0] = true;
            for (int i = 0; i < 4; i++) {
                tutorial_1_Actors[i] = new Tutorial(1, i);
            }
            stageUI.addActor(tutorial_1_Actors[0]);
        }

        if (Tutorial.tutorial) {
            Tutorial.tutorial_3_Stages[0] = true;
            for (int i = 0; i < 4; i++) {
                tutorial_3_Actors[i] = new Tutorial(3, i);
            }
            stageUI.addActor(tutorial_3_Actors[0]);
            tutorial_3_Actors[0].setVisible(false);
        }
    }

    /**
     * Places the Actors of the constructor to the Stages.
     */
    private void addActorsToStage() {
        stageUI.addActor(moneyLabel);
        stageUI.addActor(incomeLabel);
        stageUI.addActor(returnButton);

        stage.addActor(map);
        stage.addActor(research);
        stage.addActor(meter);

        for(int i=0; i<actorAmount; i++) {
            stage.addActor(farms.get(i));
        }

        coinAdded[0] = true;
        for(int i=0; i<actorAmount; i++) {
            stage.addActor(coins.get(i));
        }
        for(int i=0; i<actorAmount; i++) {
            if(!coinAdded[i]) {
                coins.get(i).setVisible(false);
            }
            if(MoneyButton.getLastTimeClicked()[i] == 0) {
                coins.get(i).setClicked();
            }
        }

        stage.addActor(boat1);
        stage.addActor(boat2);
        stage.addActor(boatCoins1);
        stage.addActor(boatCoins2);
        if(!boat1Added) {
            boat1.setVisible(false);
            boatCoins1.setVisible(false);
        }
        if(!boat2Added) {
            boat2.setVisible(false);
            boatCoins2.setVisible(false);
        }

        stage.addActor(cloud);
        stage.addActor(cloud2);
        stage.addActor(cloud3);
        stage.addActor(cloud4);

        stageUI.addActor(endGame);
        endGame.setVisible(false);
    }

    /**
     * Sets coin with certain index visible and sets it clicked so it has a starting time.
     *
     * @param index Index of the coin added
     */
    void setCoinVisible(int index) {
        coins.get(index).setVisible(true);
        coinAdded[index] = true;
        coins.get(index).setClicked();

        Save.saveVariables();
        Save.loadVariables();
    }


    @Override
    public void show() {
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
     * Updates camera input and location, checks if tutorial is on, moves the cloud textures and calls draw- and act -methods of the Stages.
     *
     * @param delta delta time of player's device
     */
    @Override
    public void render(float delta) {

        handleInput();
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        addBoatIfNeeded();

        if(Main.getBalticSituation() >= 100) {
            triggerEndGame();
        }

        cloud.cloudMove();
        cloud2.cloudMove();
        cloud3.cloudMove();
        cloud4.cloudMove();

        if(Tutorial.tutorial_1 && Tutorial.tutorial) {
            hideIconsTutorial_1();
            manageTutorial_1();
        }
        if(!Tutorial.tutorial_1 && Tutorial.tutorial) {
            for(int j=0; j<4; j++) {
                tutorial_1_Actors[j].setVisible(false);
            }
            research.setVisible(true);
        }

        if(Tutorial.tutorial_3 && !Tutorial.tutorial_2) {
            for (int i = 0; i < 2; i++) {
                farms.get(i).setVisible(true);
            }
            research.setVisible(false);
            coins.get(0).setVisible(true);
            tutorial_3_Actors[0].setVisible(true);
            manageTutorial_3();
        }

        if(!Tutorial.tutorial_3 && Tutorial.tutorial) {
            for (int j = 0; j < 4; j++) {
                tutorial_3_Actors[j].setVisible(false);
            }
            farms.get(2).setVisible(true);
            farms.get(3).setVisible(true);
            research.setVisible(true);
        }

        stage.draw();
        stage.act(Gdx.graphics.getDeltaTime());

        stageUI.draw();
    }

    /**
     * Adds boat and boatCoin if balticSituation is good enough.
     */
    private void addBoatIfNeeded() {
        if (Main.getBalticSituation() >= 25 && !boat1Added) {
            boat1.setVisible(true);
            boatCoins1.setVisible(true);
            boat1Added = true;
        }
        if (Main.getBalticSituation() >= 50 && !boat2Added) {
            boat2.setVisible(true);
            boatCoins2.setVisible(true);
            boat2Added = true;
        }
    }

    /**
     * Hides all unneeded Actors in the MapScreen for the tutorial.
     */
    private void hideIconsTutorial_1() {

        research.setVisible(false);
        for (int i = 0; i < actorAmount; i++) {
            farms.get(i).setVisible(false);
        }
        coins.get(0).setVisible(false);
    }

    /**
     * Controls how the tutorial is advancing in its first stage.
     */
    private void manageTutorial_1() {

        for(int i=0; i<4; i++) {
            if(Tutorial.tutorial_1_Stages[i]) {
                stageUI.addActor(tutorial_1_Actors[i]);
            }
        }
    }

    /**
     * Controls how the tutorial is advancing in its third stage.
     */
    private void manageTutorial_3() {

        for(int i=0; i<4; i++) {
            if(Tutorial.tutorial_3_Stages[i] && Tutorial.tutorial_3) {
                stageUI.addActor(tutorial_3_Actors[i]);
            }
        }
    }

    /**
     * Runs the end game tutorial screen when the game's completion condition is met and hides other actors from the background.
     */
    private void triggerEndGame() {
        if(Tutorial.endGame) {
            moneyLabel.setVisible(false);
            incomeLabel.setVisible(false);
            returnButton.setVisible(false);
            boatCoins1.setVisible(false);
            boatCoins2.setVisible(false);
            boat1.setVisible(false);
            boat2.setVisible(false);
            for(int i=0; i<4; i++) {
                coins.get(i).setVisible(false);
            }
            for(int i=0; i<4; i++) {
                farms.get(i).setVisible(false);
            }
            research.setVisible(false);
            meter.setVisible(false);
            endGame.setVisible(true);
        } else {
            endGame.setVisible(false);
            moneyLabel.setVisible(true);
            incomeLabel.setVisible(true);
            returnButton.setVisible(true);
            boatCoins1.setVisible(true);
            boatCoins2.setVisible(true);
            boat1.setVisible(true);
            boat2.setVisible(true);
            for(int i=0; i<4; i++) {
                coins.get(i).setVisible(true);
            }
            for(int i=0; i<4; i++) {
                farms.get(i).setVisible(true);
            }
            research.setVisible(true);
            meter.setVisible(true);
        }
    }

    /**
     * Handles input of the camera movement and updates the location of the camera.
     */
    private void handleInput() {

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            ((OrthographicCamera)camera).zoom += 0.3f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            ((OrthographicCamera)camera).zoom -= 0.3f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.translate(-7, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.translate(7, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.translate(0, -7, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(0, 7, 0);
        }

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

        ((OrthographicCamera)camera).zoom = MathUtils.clamp(((OrthographicCamera)camera).zoom, 10, 800/camera.viewportWidth);
        float effectiveViewportWidth = camera.viewportWidth * ((OrthographicCamera)camera).zoom;
        float effectiveViewportHeight = camera.viewportHeight * ((OrthographicCamera)camera).zoom;

        camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, main.WIDTH - effectiveViewportWidth / 2f);
        camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, main.HEIGHT - effectiveViewportHeight / 2f);
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
     * Resize the viewport width and height to 30 units to be congruence despite of device resolution.
     *
     * @param width width of the viewport
     * @param height height of the viewport
     */
    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 30f;
        camera.viewportHeight = 30f * height / width;
        camera.update();
    }

    /**
     * Disposes stages and coin sounds.
     */
    @Override
    public void dispose() {
        stage.dispose();
        stageUI.dispose();
        for(int i=0; i<actorAmount; i++) {
            coins.get(i).disposeCoinSound();
        }
    }

    /**
     * Get -method to collect stage.
     *
     * @return stage of the MapScreen
     */
    Stage getStage() {
        return stage;
    }

    /**
     * Get -method to collect stageUI.
     *
     * @return stageUI of the MapScreen
     */
    Stage getStageUI() {
        return stageUI;
    }
}
