package com.ferri.arnus.warp.core;

import com.ferri.arnus.warp.gui.WarpApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Warp {

    public static String USERDIR = System.getProperty("user.dir");
    public static String MINECRAFTLOC = USERDIR + "\\Minecraft";
    public static String INSTANCELOC = USERDIR + "\\Instances";

    public static void main(String[] args) {
        WarpApplication.launch(WarpApplication.class, args);
    }
}
