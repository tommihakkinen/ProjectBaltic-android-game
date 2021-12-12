package com.mygdx.tikoprojectbaltic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Save is a static class for handling memory.
 *
 * @author  Jennina Färm
 * @author  Tommi Häkkinen
 * @version 2020.2204
 * @since 1.8
 */

public class Save {

    /**
     * Array containing information if researchButton has been bought.
     */
    private static boolean [] research = ResearchButton.getResearchBooleans();
    /**
     * 2d array containing information if FarmButtons have been bought.
     */
    private static boolean [][] farmBought = FarmButton.getBoughtArray();
    /**
     * Array containing information of the current income multipliers.
     */
    private static float [] multipliers = MoneyButton.getMultipliers();
    /**
     * Array containing information if MapButtons have been bought.
     */
    private static boolean [] farmLocks = MapButton.getFarmLocks();
    /**
     * Array for containing information when MoneyButton has been clicked last.
     */
    private static int [] lastTimeClicked = MoneyButton.getLastTimeClicked();
    /**
     * Array for containing information if MoneyButton has been added to the Stage.
     */
    private static boolean [] coinAdded = MapScreen.getCoinAdded();
    /**
     * Array containing information of the amount of workers in FarmScreen.
     */
    private static int [] workerAmount = FarmScreen.getWorkerAmountArray();
    /**
     * Array containing information of the maximum amount of money that could be collected.
     */
    private static int [] maxAmount = MoneyButton.getMaxAmount();
    /**
     * 2d array containing information of FarmButtons's current Y-axis locations.
     */
    private static float [][] farmActorY = FarmScreen.getFarmActorYArray();
    /**
     * Preferences object that creates a save file and handles the saving and loading methods.
     */
    private static Preferences prefs = Gdx.app.getPreferences("balticproject_savefile");
    /**
     * Amount of the farmScreens.
     */
    private static int farmAmount = 4;
    /**
     * Amount of the researches.
     */
    private static int researchAmount = 19;

    /**
     * Saves all the private arrays and some crucial variables of the game.
     */
    static void saveVariables() {

        prefs.putInteger("money", Main.getMoney());
        prefs.putInteger("balticSituation", Main.getBalticSituation());
        prefs.putBoolean("tutorial", Tutorial.tutorial);
        prefs.putBoolean("endgame", Tutorial.endGame);
        prefs.putBoolean("music", Main.getMusic());
        prefs.putBoolean("sound", Main.getSound());
        prefs.putBoolean("language", Main.finnish);

        for(int i=0; i<farmAmount; i++) {
            prefs.putFloat("multiplier" + i, multipliers[i]);
        }

        for(int i=0; i<researchAmount; i++) {
            prefs.putBoolean("research" + i, research[i]);
        }

        for(int i=0; i<farmAmount; i++) {
            for(int j=0; j<researchAmount; j++) {
                prefs.putBoolean("farmbought" + i + j, farmBought[i][j]);
            }
        }

        for(int i=0; i<farmAmount; i++) {
            prefs.putBoolean("farmlock" + i, farmLocks[i]);
        }

        for(int i=0; i<farmAmount; i++) {
            prefs.putInteger("lastTimeClicked" + i, lastTimeClicked[i]);
        }

        for(int i=0; i<farmAmount; i++) {
            prefs.putBoolean("coinAdded" + i, coinAdded[i]);
        }

        for(int i=0; i<farmAmount; i++) {
            prefs.putInteger("workerAmount" + i, workerAmount[i]);
        }

        for(int i=0; i<farmAmount; i++) {
            prefs.putInteger("maxAmount" + i, maxAmount[i]);
        }

        for(int i=0; i<farmAmount; i++) {
            for (int j = 0; j < researchAmount; j++) {
                prefs.putFloat("farmActorY" + i + j, farmActorY[i][j]);
            }
        }
        prefs.flush();
    }

    /**
     * Loads all the private arrays and some crucial variables of the game from the saved file.
     */
    static void loadVariables() {

        Main.setMoney(prefs.getInteger("money", 7000));
        Main.setBalticSituation(prefs.getInteger("balticSituation", 0));
        Tutorial.tutorial = prefs.getBoolean("tutorial", true);
        Tutorial.endGame = prefs.getBoolean("endgame", true);
        Main.setMusic(prefs.getBoolean("music", true));
        Main.setSound(prefs.getBoolean("sound", true));
        Main.finnish = prefs.getBoolean("language");

        for(int i=0; i<farmAmount; i++) {
            multipliers[i] = prefs.getFloat("multiplier" + i, 4);
        }
        MoneyButton.setMultipliers(multipliers);

        for(int i=0; i<researchAmount; i++) {
            research[i] = prefs.getBoolean("research" + i);
        }
        ResearchButton.setBooleanArray(research);
        FarmButton.setResearched(research);

        for(int i=0; i<farmAmount; i++) {
            for(int j=0; j<researchAmount; j++) {
                farmBought[i][j] = prefs.getBoolean("farmbought" + i + j);
            }
        }
        FarmButton.setBoughtArray(farmBought);

        for(int i=0; i<farmAmount; i++) {
            farmLocks[i] = prefs.getBoolean("farmlock" + i);
        }
        MapButton.setFarmLocksArray(farmLocks);

        for(int i=0; i<farmAmount; i++) {
            lastTimeClicked[i] = prefs.getInteger("lastTimeClicked" + i);
        }
        MoneyButton.setLastTimeClicked(lastTimeClicked);

        for(int i=0; i<farmAmount; i++) {
            coinAdded[i] = prefs.getBoolean("coinAdded" + i);
        }
        MapScreen.setCoinAdded(coinAdded);

        for(int i=0; i<farmAmount; i++) {
            workerAmount[i] = prefs.getInteger("workerAmount" + i);
        }
        FarmScreen.setWorkerAmountArray(workerAmount);

        for(int i=0; i<farmAmount; i++) {
            maxAmount[i] = prefs.getInteger("maxAmount" + i);
        }
        MoneyButton.setMaxAmount(maxAmount);

        for(int i=0; i<farmAmount; i++) {
            for(int j=0; j<researchAmount; j++) {
                farmActorY[i][j] = prefs.getFloat("farmActorY" + i + j);
            }
        }
        FarmScreen.setFarmActorYArray(farmActorY);
    }

    /**
     * Resets all the private arrays and crucial variables as they should be in the beginning of the game.
     */
    static void newGame() {

        prefs.putInteger("money", 7000);
        prefs.putBoolean("gameBegan", false);
        prefs.putInteger("balticSituation", 0);
        prefs.putBoolean("tutorial", false);
        prefs.putBoolean("endgame", true);

        for(int i=0; i<farmAmount; i++) {
            prefs.putFloat("multiplier" + i, 4);
            if(i<5) {
                prefs.putFloat("multiplier" + i, 4);
            } else {
                prefs.putFloat("multiplier" + i, 30);
            }
        }

        for(int i=0; i<researchAmount; i++) {
            prefs.putBoolean("research" + i, false);
        }

        for(int i=0; i<farmAmount; i++) {
            for(int j=0; j<researchAmount; j++) {
                prefs.putBoolean("farmbought" + i + j, false);
            }
        }

        for(int i=0; i<farmAmount; i++) {
            prefs.putBoolean("farmlock" + i, false);
        }

        for(int i=0; i<farmAmount; i++) {
            prefs.putInteger("lastTimeClicked" + i, Utils.getCurrentTimeInSeconds());
        }

        for(int i=0; i<farmAmount; i++) {
            prefs.putBoolean("coinAdded" + i, false);
        }

        for(int i=0; i<farmAmount; i++) {
            prefs.putInteger("workerAmount" + i, 0);
        }

        for(int i=0; i<farmAmount; i++) {
            prefs.putInteger("maxAmount" + i, 500);
            if(i < 5) {
                prefs.putInteger("maxAmount" + i, 0);
            } else {
                prefs.putInteger("maxAmount" + i, 20000);
            }
        }

        for(int i=0; i<farmAmount; i++) {
            for (int j = 0; j < researchAmount; j++) {
                prefs.putFloat("farmActorY" + i + j, (283 - j * 60));
            }
        }

        prefs.flush();

        loadVariables();
    }
}
