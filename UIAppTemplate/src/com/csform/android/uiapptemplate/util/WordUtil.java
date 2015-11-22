package com.csform.android.uiapptemplate.util;

/**
 * Created by bhuwan on 23/11/15.
 */
public class WordUtil {

    public static String capitalize(String string){
        String[] tokens = string.split("\\s");
        string = "";
        for(int i = 0; i < tokens.length; i++){
            char capLetter = Character.toUpperCase(tokens[i].charAt(0));
            string +=  " " + capLetter + tokens[i].substring(1);
        }
        return string.trim();
    }
}
