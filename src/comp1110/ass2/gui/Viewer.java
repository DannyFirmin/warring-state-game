package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
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
     */
    void makePlacement(String placement) {
        // FIXME Task 4: implement the simple placement viewer
/*
Display images of cards in the window (anywhere)

 Display images of cards so that their origin is in the correct place.

 Display images so that their cards is in the correct place and their orientation is correct.

 Break placement strings into card placements.

 Fix the makePlacement() method of the Viewer class so that it works correctly.
*/
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
            @Override
            public void handle(ActionEvent e) {
                makePlacement(textField.getText());
                textField.clear();
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button,bgmbutton);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("experiment.fxml"));
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
