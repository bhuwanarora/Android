package com.csform.android.uiapptemplate.util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by bhuwan on 24/11/15.
 */
public class ColorUtil {

    public static String getRandomColor(){
        ArrayList<String> randomColors = new ArrayList<>(Arrays.asList("#4FBECB", "#B88FAD", "#5261C0", "#FEB08A", "#50667D", "#E35E76", "#6EB0AF", "#EF9191", "#26c6da", "#00acc1", "#0097a7", "#00838f", "#006064"));
        int min = 0;
        int max = randomColors.size() - 1;
        int random = min + (int)(Math.random() * ((max - min) + 1));
        return randomColors.get(random);
    }
}
