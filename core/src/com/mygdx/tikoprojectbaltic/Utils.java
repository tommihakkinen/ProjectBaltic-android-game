package com.mygdx.tikoprojectbaltic;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Utils is a static class that contains methods that are universal.
 *
 * @author  Jennina Färm
 * @author  Tommi Häkkinen
 * @version 2020.2204
 * @since 1.8
 */

public class Utils {
    /**
     * Converts Long currentTimeMillis() to integer containing time in seconds.
     *
     * @return current time in seconds
     */
    public static int getCurrentTimeInSeconds() {
        Long timeMillis = System.currentTimeMillis();
        int tempMillis = timeMillis.intValue();
        int tempSec = tempMillis/1000;

        return tempSec;
    }

    /**
     * Transforms 2d TextureRegion array to 1d.
     *
     * @param tmp 2d array to be converted
     * @param frameCols amount of cols
     * @param frameRows amount of rows
     * @return 1d array of the given 2d array
     */

    public static TextureRegion[] transformTo1D(TextureRegion [][] tmp, int frameCols, int frameRows) {
        TextureRegion [] frames = new TextureRegion[frameCols * frameRows];
        int index = 0;

        for(int i=0; i < frameRows; i++) {
            for(int j=0; j < frameCols; j++) {
                frames[index] = tmp[i][j];
                index++;
            }
        }
        return frames;
    }

    /**
     * Contains TextureArray.split method to split Texture to given amount of columns and rows.
     *
     * @param t Texture to split
     * @param columns amount of columns
     * @param rows amount of rows
     * @return TextureRegion[][] of the given Texture
     */

    public static TextureRegion[][] createTextureRegion2DArray(Texture t, int columns, int rows) {
        TextureRegion [][] regionArray = TextureRegion.split(
                t,
                t.getWidth() / columns,
                t.getHeight() / rows);
        return regionArray;
    }
}