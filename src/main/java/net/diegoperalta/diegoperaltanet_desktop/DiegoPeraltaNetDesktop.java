package net.diegoperalta.diegoperaltanet_desktop;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

/**
 *
 * @author Diego
 */
public class DiegoPeraltaNetDesktop extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass()
                .getResource("menu.fxml"));
        Scene scene = new Scene(root, 1200, 750);
        //JMetro jMetro = new JMetro(root, Style.DARK);
        //jMetro.setScene(scene);
        stage.setResizable(false);
        stage.setMaximized(true);
        stage.setAlwaysOnTop(true);
        stage.centerOnScreen();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("diegoperalta.net");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
