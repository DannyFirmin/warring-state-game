package comp1110.ass2.gui;
/**
 * All codes in this file is done by Danny
 *
 * @Author Danny
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;


/**
 * A very simple viewer for card layouts in the Warring States game.
 * <p>
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various card placements.
 */
public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 933;
    private static final int VIEWER_HEIGHT = 700;

    /* where to find media assets */
    private static final String URI_BASE = "assets/";

    /* Loop in public domain */
    private static final String LOOP_URI = Viewer.class.getResource(URI_BASE + "BGM.wav").toString();

    private AudioClip loop;
    private boolean loopPlaying = true;

    private final Group root = new Group();
    private final Group controls = new Group();
    TextField textField;


    private void setUpSoundLoop() {
        try {
            loop = new AudioClip(LOOP_URI);
            loop.setCycleCount(AudioClip.INDEFINITE);
        } catch (Exception e) {
            System.err.println(":-( something bad happened (" + LOOP_URI + "): " + e);
        }
    }


    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     * @author Danny
     */
    void makePlacement(String placement) {
        // FIXME Task 4: implement the simple placement viewer
        // Task 4 has been done correctly.
        // Code is at below and in ViewController.java and viewer.fxml
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {

        Button bgmbutton = new Button("BGM on/off");
        bgmbutton.setLayoutX(VIEWER_WIDTH / 2 + 70);
        bgmbutton.setLayoutY(VIEWER_HEIGHT - 45);
        bgmbutton.setOnAction(new EventHandler<ActionEvent>() {


            @Override
            public void handle(ActionEvent e) {
                if (loopPlaying)
                    loop.stop();
                else
                    loop.play();
                loopPlaying = !loopPlaying;
            }
        });

        Label label1 = new Label("Placement:");
        textField = new TextField();
        textField.setPrefWidth(300);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {

            /** @author Danny */
            @Override
            public void handle(ActionEvent e) {
                makePlacement(textField.getText());
                textField.clear();
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button, bgmbutton);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("viewer.fxml"));
        primaryStage.setTitle("Warring States Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
//        root.getChildren().add(controls);

        makeControls();
        setUpSoundLoop();
        loop.play();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
