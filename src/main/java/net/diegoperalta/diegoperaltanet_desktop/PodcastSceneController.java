package net.diegoperalta.diegoperaltanet_desktop;

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

public class PodcastSceneController implements Initializable {
    public Button buttonPrevious;
    public Button buttonNext;
    public ProgressIndicator progIndicator;
    @FXML
    private StackPane parentContainer;
    @FXML
    private BorderPane borderPane4;
    @FXML
    private Button buttonLeft;

    @FXML
    private Button minimizeButton;

    @FXML
    private Button closeButton;

    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> paragraphs = new ArrayList<>();
    private ArrayList<String> links = new ArrayList<>();

    @FXML
    private Label labelLoading;

    @FXML
    private VBox vbox1;
    @FXML
    private Label titleLabel1;
    @FXML
    private Label paragraphLabel1;
    @FXML
    private VBox vbox2;
    @FXML
    private Label titleLabel2;
    @FXML
    private Label paragraphLabel2;
    @FXML
    private VBox vbox3;
    @FXML
    private Label titleLabel3;
    @FXML
    private Label paragraphLabel3;
    @FXML
    private VBox vbox4;
    @FXML
    private Label titleLabel4;
    @FXML
    private Label paragraphLabel4;
    @FXML
    private VBox vbox5;
    @FXML
    private Label titleLabel5;
    @FXML
    private Label paragraphLabel5;

    public static int numPage = 1;
    public PageBlogEntries page;

    public int pageIndex;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initialize podcast.");
        buttonLeft.setDisable(false);
        if (numPage > 1) {
            buttonPrevious.setVisible(true);
        }
        labelLoading.setVisible(true);
        progIndicator.setVisible(true);
        buttonNext.setVisible(false);
        loadPodcastEpisodes(numPage);
    }

    private void loadPodcastEpisodes(int pageIndex) {
        //webView.getEngine().load("https://www.youtube.com/embed/1cGmWPI5TaI");
        //webView.getEngine().load("https://blubrry.com/el_show_del_liderazgo_con/");
        //webEngine = webView.getEngine();
/*        webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED) {
                            //stage.setTitle(webEngine.getLocation());
                        }
                    }
                });*/
        //webEngine.getDocument().getElementsByTagNameNS(, "enclosure");https://diegoperalta.net/?p=5805&preview=1&_ppp=584abcb6f2");
        //webEngine.load("https://oembed.libsyn.com/embed?item_id=17059811");
        progIndicator.setVisible(true);
        labelLoading.setText("Cargando episodios de Podcast...");
        labelLoading.setVisible(true);

        // Create a background Task
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                hideVBoxes();
                page = new PageBlogEntries();
                HTMLParser parser = new HTMLParser();
                if (pageIndex == 1) {
                    parser.parsePodcastDoc();
                } else {
                    parser.parsePodcastDoc(pageIndex);
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

        task.setOnSucceeded(wse -> {
            fillPodcastEpisodes();
            setVBoxesVisible();
            page.addEntriesToPage(pageIndex - 1, page.getEntries());
            page.printPage();
            // if (pageIndex > 1)
            buttonPrevious.setDisable(false);
            // else
            // buttonPrevious.setDisable(true);
            if (numPage == PageBlogEntries.numLastPage) {
                buttonNext.setVisible(false);
                buttonNext.setDisable(true);
                PageBlogEntries.numLastPage = numPage;
            } else {
                buttonNext.setVisible(true);
                buttonNext.setDisable(false);
                System.out.println("No es ultima pagina.");
            }
            labelLoading.setVisible(false);
            progIndicator.setVisible(false);
            System.out.println("Done! \n");
        });
        new Thread(task).start();
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

    public void fillPodcastEpisodes() {
/*        if (titleLabel5.getText().contains("#001")) {
            buttonNext.setVisible(false);
            buttonNext.setDisable(true);
            System.out.println("Caso muy especial.");
        }*/

        if (numPage == PageBlogEntries.numLastPage) {
            buttonNext.setVisible(false);
            buttonNext.setDisable(true);
            System.out.println("Entró en última página.");
        }

        switch(page.getEntries().size()) {
            case 0:
                buttonNext.setVisible(false);
                //buttonNext.setDisable(true);
                PageBlogEntries.numLastPage = numPage;
                System.out.println("Sí entrooo.");
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
                if (titleLabel5.getText().contains("#001")) {
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

    @FXML
    private void handleMenuButtonAction(ActionEvent event)
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
            //webView.getEngine().reload();
            parentContainer.getChildren().remove(borderPane4);
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

    public void handlePreviousButtonAction(ActionEvent actionEvent) {
        int pageIndex = numPage-1;
        page.getPages().remove(pageIndex);
        labelLoading.setVisible(true);
        progIndicator.setVisible(true);
        buttonPrevious.setDisable(true);
        buttonNext.setDisable(true);
        if (PageBlogEntries.isLastPage) {
            buttonNext.setVisible(false);
            buttonNext.setDisable(true);
            PageBlogEntries.isLastPage = false;
        }
        if (numPage == PageBlogEntries.numLastPage) {
            buttonNext.setVisible(false);
            loadPodcastEpisodes(--numPage);
        } else {
            buttonNext.setVisible(true);
            --numPage;
            if (numPage == 1) {
                buttonPrevious.setVisible(false);
            }
            loadPodcastEpisodes(numPage);
        }
    }

    public void handleOnMouseEnteredVBox1(MouseEvent mouseEvent) {
    }

    public void handleOnMouseExitedVBox1(MouseEvent mouseEvent) {
    }

    public void handleBlogEntryAction(MouseEvent mouseEvent) {
    }

    public void handleNextButtonAction(ActionEvent actionEvent) {
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
            loadPodcastEpisodes(numPage);
        }
    }

    public void handleOnMouseExitedVBox2(MouseEvent mouseEvent) {
    }

    public void handleOnMouseEnteredVBox2(MouseEvent mouseEvent) {
    }

    public void handleBlogEntryAction2(MouseEvent mouseEvent) {
    }

    public void handleOnMouseEnteredVBox3(MouseEvent mouseEvent) {
    }

    public void handleBlogEntryAction3(MouseEvent mouseEvent) {
    }

    public void handleOnMouseExitedVBox3(MouseEvent mouseEvent) {
    }

    public void handleOnMouseExitedVBox4(MouseEvent mouseEvent) {
    }

    public void handleOnMouseEnteredVBox4(MouseEvent mouseEvent) {
    }

    public void handleBlogEntryAction4(MouseEvent mouseEvent) {
    }

    public void handleOnMouseExitedVBox5(MouseEvent mouseEvent) {
    }

    public void handleOnMouseEnteredVBox5(MouseEvent mouseEvent) {
    }

    public void handleBlogEntryAction5(MouseEvent mouseEvent) {
    }
}
