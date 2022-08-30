package net.diegoperalta.diegoperaltanet_desktop;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Diego
 */
public class MenuController implements Initializable {

    @FXML
    private StackPane parentContainer;

    @FXML
    private BorderPane borderPane1;

    @FXML
    private Button button1;

    @FXML
    private Label labelBlackScreen;

    @FXML
    private Button blogButton;

    @FXML
    public Button closeButton;

    @FXML
    public Button minimizeButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleButtonAction(ActionEvent event)
            throws IOException {
        //if (getClickCount)
        //label.setText("Hello World!");
        button1.setDisable(true);
        blogButton.setDisable(true);
        //event.getClass().get

        //blogButton.disarm();

        Parent root = FXMLLoader.load(getClass()
                .getResource("blog_scene.fxml"));
        Scene scene = button1.getScene();
        root.translateXProperty().set(scene.getWidth());
        parentContainer.getChildren().add(root);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(),
                0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.3), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished( event1 -> {
            parentContainer.getChildren().remove(borderPane1);
        });
        timeline.play();
    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleMinimizeButtonAction(ActionEvent event) {
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }


    @FXML
    protected void handleOnMouseEnteredPodButton(MouseEvent event) {
        Image img = new Image(getClass().getResourceAsStream("images/podcast-pantalla-2.jpg"));
        ImageView view = new ImageView(img);
        labelBlackScreen.setGraphic(view);
    }

    @FXML
    protected void handleOnMouseExitedPodButton(MouseEvent event) {
        Image img = new Image(getClass().getResourceAsStream("images/default-pantalla-2.jpg"));
        ImageView view = new ImageView(img);
        labelBlackScreen.setGraphic(view);
    }

    @FXML
    protected void handleOnMouseEnteredRSButton(MouseEvent event) {
        Image img = new Image(getClass().getResourceAsStream("images/rs-pantalla-3.jpg"));
        ImageView view = new ImageView(img);
        labelBlackScreen.setGraphic(view);
    }

    @FXML
    protected void handleOnMouseExitedRSButton(MouseEvent event) {
        Image img = new Image(getClass().getResourceAsStream("images/default-pantalla-2.jpg"));
        ImageView view = new ImageView(img);
        labelBlackScreen.setGraphic(view);
    }

    @FXML
    protected void handleOnMouseEnteredBlogButton(MouseEvent event) {
        Image img = new Image(getClass().getResourceAsStream("images/blog-pantalla-3.jpg"));
        ImageView view = new ImageView(img);
        labelBlackScreen.setGraphic(view);
    }

    @FXML
    protected void handleOnMouseExitedBlogButton(MouseEvent event) {
        Image img = new Image(getClass().getResourceAsStream("images/default-pantalla-2.jpg"));
        ImageView view = new ImageView(img);
        labelBlackScreen.setGraphic(view);
    }
}
