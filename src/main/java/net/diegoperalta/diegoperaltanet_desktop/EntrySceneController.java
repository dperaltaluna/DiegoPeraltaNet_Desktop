package net.diegoperalta.diegoperaltanet_desktop;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EntrySceneController implements Initializable {
    @FXML
    private StackPane parentContainer;
    @FXML
    private BorderPane borderPane2;
    @FXML
    private BorderPane borderPane3;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Slider slider;
    @FXML
    private Label labelTitle0;
    @FXML
    private Label labelContent;
    @FXML
    private Button closeButton3;
    @FXML
    private Button minimizeButton3;
    @FXML
    private Button buttonBack;
    @FXML
    private Button buttonBlackWhite;
    @FXML
    private ProgressIndicator progIndicator;

    private ArrayList<String> entry = new ArrayList<>();
    private String completeEntry = "";
    private int fontSizeSelected = 26;
    private boolean isBlackSelected = true;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadEntryContent(PageBlogEntries.linkEntries.get(PageBlogEntries.indexEntrySelected));
        JMetro jMetro = new JMetro(scrollPane, Style.DARK);
        //setFasterScroller(scrollPane);


        //jMetro.setScene(scrollPane.getScene());
        //JMetro jMetro1 = new JMetro(slider, Style.DARK);
        //labelContent.setStyle("-fx-font-size: 20px;");

        changeLetterSize();
        labelTitle0.setText(PageBlogEntries.entries
                .get(PageBlogEntries.indexEntrySelected).getTitle());
    }

    private void loadEntryContent(String link) {
        HTMLParser parser = new HTMLParser();
        progIndicator.setVisible(true);
        labelTitle0.setVisible(false);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                parser.parseEntry(link);
                ArrayList<String> entries = parser.parseTextEntry();
                for (int i = 0; i < entries.size(); i++) {
                    completeEntry = completeEntry.concat(entries.get(i)+"\n\n");
                }
                //completeEntry = completeEntry.replace(".", ". ");
                completeEntry = completeEntry.replace("  ", " ");
                //System.out.println(completeEntry);
                PageBlogEntries.entries.get(PageBlogEntries.indexEntrySelected).setContent(completeEntry);
                //System.out.println(PageBlogEntries.entries.get(PageBlogEntries.indexEntrySelected).getContent());
                return null;
            }
        };

            // This method allows us to handle any Exceptions thrown by the task
        task.setOnFailed(wse -> {
            System.out.println("Task Entry failed");
            wse.getSource().getException().printStackTrace();
        });

        // If the task completed successfully, perform other updates here
        task.setOnSucceeded(wse -> {
            System.out.println("Entry Done!");
            labelTitle0.setVisible(true);
            labelContent.setText(PageBlogEntries.entries.get(PageBlogEntries.indexEntrySelected).getContent());
            progIndicator.setVisible(false);
        });

        // Now, start the task on a background thread
        new Thread(task).start();
    }

    private void changeLetterSize() {
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                int index = new_val.intValue();
                if (index >= 0 && index < 10) {
                    fontSizeSelected = 20;
                    if (isBlackSelected) {
                        setBlackThemeComponents();
                    } else {
                        setWhiteThemeComponents();
                    }
                    //labelTitle0.setStyle("-fx-font-size: 18px");
                }
                if (index >= 10 && index < 30) {
                    fontSizeSelected = 22;
                    if (isBlackSelected) {
                        setBlackThemeComponents();
                    } else {
                        setWhiteThemeComponents();
                    }
                    //labelContent.setStyle("-fx-font-size: 18px");
                    //labelTitle0.setStyle("-fx-font-size: 20px");
                    //fontSizeSelected = 18;
                }
                if (index >= 30 && index < 50) {
                    fontSizeSelected = 24;
                    if (isBlackSelected) {
                        setBlackThemeComponents();
                    } else {
                        setWhiteThemeComponents();
                    }
                    //labelContent.setStyle("-fx-font-size: 20px");
                    //labelTitle0.setStyle("-fx-font-size: 22px");
                    //fontSizeSelected = 20;
                }
                if (index >= 50 && index < 70) {
                    fontSizeSelected = 26;
                    if (isBlackSelected) {
                        setBlackThemeComponents();
                    } else {
                        setWhiteThemeComponents();
                    }
                    //labelContent.setStyle("-fx-font-size: 22px");
                    //labelTitle0.setStyle("-fx-font-size: 24px");
                    //fontSizeSelected = 22;
                }
                if (index >= 70 && index < 90) {
                    fontSizeSelected = 28;
                    if (isBlackSelected) {
                        setBlackThemeComponents();
                    } else {
                        setWhiteThemeComponents();
                    }
                    //labelContent.setStyle("-fx-font-size: 24px");
                    //labelTitle0.setStyle("-fx-font-size: 26px");
                    //fontSizeSelected = 24;
                }
                if (index >= 90) {
                    fontSizeSelected = 30;
                    if (isBlackSelected) {
                        setBlackThemeComponents();
                    } else {
                        setWhiteThemeComponents();
                    }
                    //labelContent.setStyle("-fx-font-size: 26px");
                    //labelTitle0.setStyle("-fx-font-size: 28px");
                }
            }
        });
    }

    @FXML
    private void handleChangeColorButtonAction(ActionEvent event) {
        isBlackSelected = !isBlackSelected;
        if (isBlackSelected) {
            setBlackThemeComponents();
            //isBlackSelected = false;
        } else {
            setWhiteThemeComponents();
            //isBlackSelected = true;
        }
    }

    private void setBlackThemeComponents() {
        JMetro jMetro = new JMetro(scrollPane, Style.DARK);
        scrollPane.setStyle("-fx-background-color: transparent;");
        labelContent.setStyle("-fx-text-fill: snow; -fx-font-family: Verdana;" +
                "-fx-font-size: " + fontSizeSelected + "px;");
        borderPane3.setStyle("-fx-background-color: #252525;");
        labelTitle0.setStyle("-fx-text-fill: snow; -fx-font-family: Verdana; " +
                "-fx-font-size: " + Integer.toString(fontSizeSelected+2) + "px;");
    }

    private void setWhiteThemeComponents() {
        JMetro jMetro = new JMetro(scrollPane, Style.LIGHT);
        //jMetro.setStyle(Style.valueOf("-fx-background-color: lightgray;"));
        scrollPane.setStyle("-fx-background-color: #e8e8e8;");
        //scrollPane.setStyle(".viewport { -fx-background-color: transparent; }");
        //scrollPane.setStyle(".track {fx-background-color: black;}");
        labelContent.setStyle("-fx-text-fill: black; -fx-font-family: Verdana; -fx-font-weight: normal;" +
                "-fx-font-size: " + fontSizeSelected + "px;");
        borderPane3.setStyle("-fx-background-color: #e8e8e8;");
        labelTitle0.setStyle("-fx-text-fill: black; -fx-font-family: Verdana;" +
                "-fx-font-size: " + Integer.toString(fontSizeSelected+2) + "px;");
    }

    @FXML
    private void handleCloseButtonAction3(ActionEvent event) {
        Stage stage = (Stage) closeButton3.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleMinimizeButtonAction3(ActionEvent event) {
        Stage stage = (Stage) minimizeButton3.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event)
            throws IOException {
        Parent root = FXMLLoader.load(getClass()
                .getResource("blog_scene.fxml"));
        Scene scene = buttonBack.getScene();
        root.translateXProperty().set(-scene.getWidth());
        parentContainer = (StackPane) scene.getRoot();
        parentContainer.getChildren().add(root);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(),
                0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.3), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished( event1 -> {
            parentContainer.getChildren().remove(borderPane2);
        });
        timeline.play();
    }
}
