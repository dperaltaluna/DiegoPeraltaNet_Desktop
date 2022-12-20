package net.diegoperalta.diegoperaltanet_desktop;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Diego
 */
public class BlogSceneController implements Initializable {

    @FXML
    private Button buttonLeft;

    @FXML
    private Button buttonPrevious;

    @FXML
    private Button buttonNext;

    @FXML
    private StackPane parentContainer;

    @FXML
    private BorderPane borderPane2;

    @FXML
    private BorderPane borderPane3;

    @FXML
    private Label titleLabel1;

    @FXML
    private Label paragraphLabel1;

    @FXML
    private Label titleLabel2;

    @FXML
    private Label paragraphLabel2;

    @FXML
    private Label titleLabel3;

    @FXML
    private Label paragraphLabel3;

    @FXML
    private Label titleLabel4;

    @FXML
    private Label paragraphLabel4;

    @FXML
    private Label titleLabel5;

    @FXML
    private Label paragraphLabel5;
    @FXML
    private Label labelLoading;
    @FXML
    private VBox vbox1;
    @FXML
    private VBox vbox2;
    @FXML
    private VBox vbox3;
    @FXML
    private VBox vbox4;
    @FXML
    private VBox vbox5;
    @FXML
    private ProgressIndicator progIndicator;

    @FXML
    public Button closeButton2;

    @FXML
    public Button minimizeButton2;

    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> paragraphs = new ArrayList<>();
    private ArrayList<String> links = new ArrayList<>();

    public static int numPage = 1;
    public PageBlogEntries page;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initialize blog.");

        buttonLeft.setDisable(false);
        if (numPage > 1) {
            buttonPrevious.setVisible(true);
        } else {
            buttonPrevious.setVisible(false);
            buttonPrevious.setDisable(true);
        }
        labelLoading.setVisible(true);
        progIndicator.setVisible(true);
        buttonNext.setVisible(false);
        loadEntries(numPage);
    }

    private void loadEntries(int pageIndex) {
        progIndicator.setVisible(true);
        labelLoading.setText("Cargando entradas...");
        labelLoading.setVisible(true);
        buttonPrevious.setDisable(true);

            // Create a background Task
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    hideVBoxes();
                    page = new PageBlogEntries();
                    /** if (page.getPages().size() >= pageIndex) {
                        System.out.println("Entrooooooo");
                        //page.getPage(pageIndex-1).get(pageIndex-1).getTitle();
                    }*/
                    HTMLParser parser = new HTMLParser();
                    if (pageIndex == 1) {
                        parser.parseDoc();
                    } else {
                        parser.parseDoc(pageIndex);
                    }
                    titles = parser.parseTitles();
                    paragraphs = parser.parseParagraphs();
                    links = parser.parseLinks();
                    PageBlogEntries.linkEntries = links;
                    page.fillPageBlogEntries(titles, paragraphs);
                    return null;
                }
            };

            // This method allows us to handle any Exceptions thrown by the task
            task.setOnFailed(wse -> {
                setVBoxesVisible();
                buttonPrevious.setDisable(false);
                if (numPage == PageBlogEntries.numLastPage) {
                    buttonNext.setVisible(false);
                    buttonNext.setDisable(true);
                } else {
                    buttonNext.setVisible(true);
                    buttonNext.setDisable(false);
                }
                labelLoading.setVisible(false);
                progIndicator.setVisible(false);
                System.out.println("Task failed");
                wse.getSource().getException().printStackTrace();
            });

            // If the task completed successfully, perform other updates here
            task.setOnSucceeded(wse -> {
                fillBlogEntries();
                setVBoxesVisible();
                page.addEntriesToPage(pageIndex - 1, page.getEntries());
                page.printPage();
                // if (pageIndex > 1)
                buttonPrevious.setDisable(false);
                //buttonPrevious.setVisible(true);
                // else
                   // buttonPrevious.setDisable(true);
                if (numPage == PageBlogEntries.numLastPage) {
                    buttonNext.setVisible(false);
                    buttonNext.setDisable(true);
                    PageBlogEntries.numLastPage = numPage;
                } else {
                    buttonNext.setVisible(true);
                    buttonNext.setDisable(false);
                }
                labelLoading.setVisible(false);
                progIndicator.setVisible(false);
                System.out.println("Done! \n");
            });
            // Before starting our task, we need to bind our UI values to the properties on the task
            //progIndicator.progressProperty().bind(task.progressProperty());
            //labelLoading.textProperty().bind(task.messageProperty());

            // Now, start the task on a background thread
            new Thread(task).start();
    }

    @FXML
    public void handleCloseButtonAction2(ActionEvent event) {
        Stage stage = (Stage) closeButton2.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleMinimizeButtonAction2(ActionEvent event) {
        Stage stage = (Stage) minimizeButton2.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void handleLeftButtonAction(ActionEvent event)
            throws IOException {
        buttonLeft.setDisable(true);
        numPage = 1;
        Parent root = FXMLLoader.load(getClass()
                .getResource("menu.fxml"));
        Scene scene = buttonLeft.getScene();
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

    @FXML
    private void handleNextButtonAction(ActionEvent event)
            throws IOException {
        labelLoading.setVisible(true);
        progIndicator.setVisible(true);
        buttonNext.setDisable(true);
        buttonPrevious.setDisable(true);
        //if (numPage == HTMLParser.numLastPage) {
        if (numPage == PageBlogEntries.numLastPage) {
            buttonNext.setVisible(false);
            buttonNext.setDisable(true);
        } else {
            numPage++;
            buttonPrevious.setVisible(true);
            loadEntries(numPage);
        }
    }

    @FXML
    private void handlePreviousButtonAction(ActionEvent event)
            throws IOException {
        int pageIndex = numPage-1;
        page.getPages().remove(pageIndex);
        labelLoading.setVisible(true);
        progIndicator.setVisible(true);
        buttonPrevious.setDisable(true);
        buttonNext.setDisable(true);
        if (PageBlogEntries.isLastPage) {
            buttonNext.setVisible(true);
            buttonNext.setDisable(true);
            PageBlogEntries.isLastPage = false;
        }
        if (numPage == PageBlogEntries.numLastPage) {
            buttonNext.setVisible(false);
            loadEntries(--numPage);
        } else {
            buttonNext.setVisible(true);
            --numPage;
            if (numPage == 1) {
                buttonPrevious.setVisible(false);
            }
            loadEntries(numPage);
        }
    }

    @FXML
    private void handleOnMouseEnteredVBox1(MouseEvent event) {
        titleLabel1.setStyle("-fx-text-fill: snow;");
        paragraphLabel1.setStyle("-fx-text-fill: snow;");
    }

    @FXML
    private void handleOnMouseExitedVBox1(MouseEvent event) {
        titleLabel1.setStyle("-fx-text-fill: #999999;");
        paragraphLabel1.setStyle("-fx-text-fill: #999999;");
    }

    @FXML
    private void handleOnMouseEnteredVBox2(MouseEvent event) {
        titleLabel2.setStyle("-fx-text-fill: snow;");
        paragraphLabel2.setStyle("-fx-text-fill: snow;");
    }

    @FXML
    private void handleOnMouseExitedVBox2(MouseEvent event) {
        titleLabel2.setStyle("-fx-text-fill: #999999;");
        paragraphLabel2.setStyle("-fx-text-fill: #999999;");
    }

    @FXML
    private void handleOnMouseEnteredVBox3(MouseEvent event) {
        titleLabel3.setStyle("-fx-text-fill: snow;");
        paragraphLabel3.setStyle("-fx-text-fill: snow;");
    }

    @FXML
    private void handleOnMouseExitedVBox3(MouseEvent event) {
        titleLabel3.setStyle("-fx-text-fill: #999999;");
        paragraphLabel3.setStyle("-fx-text-fill: #999999;");
    }

    @FXML
    private void handleOnMouseEnteredVBox4(MouseEvent event) {
        titleLabel4.setStyle("-fx-text-fill: snow;");
        paragraphLabel4.setStyle("-fx-text-fill: snow;");
    }

    @FXML
    private void handleOnMouseExitedVBox4(MouseEvent event) {
        titleLabel4.setStyle("-fx-text-fill: #999999;");
        paragraphLabel4.setStyle("-fx-text-fill: #999999;");
    }

    @FXML
    private void handleOnMouseEnteredVBox5(MouseEvent event) {
        titleLabel5.setStyle("-fx-text-fill: snow;");
        paragraphLabel5.setStyle("-fx-text-fill: snow;");
    }

    @FXML
    private void handleOnMouseExitedVBox5(MouseEvent event) {
        titleLabel5.setStyle("-fx-text-fill: #999999;");
        paragraphLabel5.setStyle("-fx-text-fill: #999999;");
    }

    @FXML
    private void handleBlogEntryAction(MouseEvent event)
            throws IOException {
        PageBlogEntries.indexEntrySelected = 0;
        loadEntryScene(event);
    }

    @FXML
    private void handleBlogEntryAction2(MouseEvent event)
            throws IOException {
        PageBlogEntries.indexEntrySelected = 1;
        loadEntryScene(event);
    }

    @FXML
    private void handleBlogEntryAction3(MouseEvent event)
            throws IOException {
        PageBlogEntries.indexEntrySelected = 2;
        loadEntryScene(event);
    }

    @FXML
    private void handleBlogEntryAction4(MouseEvent event)
            throws IOException {
        PageBlogEntries.indexEntrySelected = 3;
        loadEntryScene(event);
    }

    @FXML
    private void handleBlogEntryAction5(MouseEvent event)
            throws IOException {
        PageBlogEntries.indexEntrySelected = 4;
        loadEntryScene(event);
    }

    private void loadEntryScene(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass()
                .getResource("entry_scene.fxml"));
        Scene scene = buttonLeft.getScene();
        root.translateXProperty().set(scene.getWidth());
        parentContainer.getChildren().add(root);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(),
                0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.3), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished( event1 -> {
            parentContainer.getChildren().remove(borderPane3);
        });
        timeline.play();
    }

    public void hideVBoxes() {
        vbox1.setDisable(true);
        vbox2.setDisable(true);
        vbox3.setDisable(true);
        vbox4.setDisable(true);
        vbox5.setDisable(true);
    }

    public void setVBoxesVisible() {
        vbox1.setDisable(false);
        vbox2.setDisable(false);
        vbox3.setDisable(false);
        vbox4.setDisable(false);
        vbox5.setDisable(false);
    }

    public void fillBlogEntries() {
        //if (HTMLParser.numLastPage == numPage) {
        //loadEntries();
        if (numPage == PageBlogEntries.numLastPage) {
            buttonNext.setVisible(false);
            buttonNext.setDisable(true);
        }

        switch(page.getEntries().size()) {
            case 0:
                buttonNext.setVisible(false);
                PageBlogEntries.numLastPage = numPage;
                break;
            case 1:
                titleLabel1.setText(page.getEntries().get(0).getTitle());
                paragraphLabel1.setText(page.getEntries().get(0).getParagraph());
                titleLabel2.setText("");
                paragraphLabel2.setText("");
                titleLabel3.setText("");
                paragraphLabel3.setText("");
                titleLabel4.setText("");
                paragraphLabel4.setText("");
                titleLabel5.setText("");
                paragraphLabel5.setText("");
                vbox2.setVisible(false);
                vbox3.setVisible(false);
                vbox4.setVisible(false);
                vbox5.setVisible(false);
                buttonNext.setVisible(false);
                //HTMLParser.numLastPage = numPage;
                PageBlogEntries.numLastPage = numPage;
                break;
            case 2:
                titleLabel1.setText(page.getEntries().get(0).getTitle());
                paragraphLabel1.setText(page.getEntries().get(0).getParagraph());
                titleLabel2.setText(page.getEntries().get(1).getTitle());
                paragraphLabel2.setText(page.getEntries().get(1).getParagraph());
                titleLabel3.setText("");
                paragraphLabel3.setText("");
                titleLabel4.setText("");
                paragraphLabel4.setText("");
                titleLabel5.setText("");
                paragraphLabel5.setText("");
                vbox3.setVisible(false);
                vbox4.setVisible(false);
                vbox5.setVisible(false);
                buttonNext.setVisible(false);
                //HTMLParser.numLastPage = numPage;
                PageBlogEntries.numLastPage = numPage;
                break;
            case 3:
                titleLabel1.setText(page.getEntries().get(0).getTitle());
                paragraphLabel1.setText(page.getEntries().get(0).getParagraph());
                titleLabel2.setText(page.getEntries().get(1).getTitle());
                paragraphLabel2.setText(page.getEntries().get(1).getParagraph());
                titleLabel3.setText(page.getEntries().get(2).getTitle());
                paragraphLabel3.setText(page.getEntries().get(2).getParagraph());
                titleLabel4.setText("");
                paragraphLabel4.setText("");
                titleLabel5.setText("");
                paragraphLabel5.setText("");
                vbox4.setVisible(false);
                vbox5.setVisible(false);
                buttonNext.setVisible(false);
                //HTMLParser.numLastPage = numPage;
                PageBlogEntries.numLastPage = numPage;
                break;
            case 4:
                titleLabel1.setText(page.getEntries().get(0).getTitle());
                paragraphLabel1.setText(page.getEntries().get(0).getParagraph());
                titleLabel2.setText(page.getEntries().get(1).getTitle());
                paragraphLabel2.setText(page.getEntries().get(1).getParagraph());
                titleLabel3.setText(page.getEntries().get(2).getTitle());
                paragraphLabel3.setText(page.getEntries().get(2).getParagraph());
                titleLabel4.setText(page.getEntries().get(3).getTitle());
                paragraphLabel4.setText(page.getEntries().get(3).getParagraph());
                titleLabel5.setText("");
                paragraphLabel5.setText("");
                vbox5.setVisible(false);
                buttonNext.setVisible(false);
                //HTMLParser.numLastPage = numPage;
                PageBlogEntries.numLastPage = numPage;
                break;
            default:
                vbox1.setVisible(true);
                vbox2.setVisible(true);
                vbox3.setVisible(true);
                vbox4.setVisible(true);
                vbox5.setVisible(true);
                titleLabel1.setText(page.getEntries().get(0).getTitle());
                paragraphLabel1.setText(page.getEntries().get(0).getParagraph());
                titleLabel2.setText(page.getEntries().get(1).getTitle());
                paragraphLabel2.setText(page.getEntries().get(1).getParagraph());
                titleLabel3.setText(page.getEntries().get(2).getTitle());
                paragraphLabel3.setText(page.getEntries().get(2).getParagraph());
                titleLabel4.setText(page.getEntries().get(3).getTitle());
                paragraphLabel4.setText(page.getEntries().get(3).getParagraph());
                titleLabel5.setText(page.getEntries().get(4).getTitle());
                paragraphLabel5.setText(page.getEntries().get(4).getParagraph());
                if (numPage == PageBlogEntries.numLastPage) {
                    buttonNext.setVisible(false);
                    buttonNext.setDisable(true);
                    buttonPrevious.setDisable(false);
                } else {
                    buttonNext.setDisable(false);
                    buttonPrevious.setDisable(false);
                }
                if (titleLabel5.getText()
                        .equalsIgnoreCase("Los 3 pilares que mejorarán tu calidad de vida de inmediato y a largo plazo.")) {
                    PageBlogEntries.numLastPage = numPage;
                    buttonNext.setVisible(false);
                    buttonNext.setDisable(true);
                    buttonPrevious.setDisable(false);
                    System.out.println("Entró en última página.");
                } else {
                    buttonNext.setDisable(false);
                    buttonPrevious.setDisable(false);
                }
                labelLoading.setVisible(false);
                progIndicator.setVisible(false);
        }
    }
}