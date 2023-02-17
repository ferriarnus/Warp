package com.ferri.arnus.warp.gui;

import com.ferri.arnus.warp.core.GameInstance;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

import static com.ferri.arnus.warp.core.Constants.MINECRAFTEXE;
import static com.ferri.arnus.warp.core.Constants.MINECRAFTLOC;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        String[] args = new String[]{MINECRAFTEXE, "-w", MINECRAFTLOC};
        try {
            Runtime.getRuntime().exec(args);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        GameInstance instance = new GameInstance("test", "fabric-loader-0.14.9-1.18.2");
        instance.makeVersion();
        instance.makeJson();
        instance.injectProfile();
    }

    protected void makeInstance() {

    }
}