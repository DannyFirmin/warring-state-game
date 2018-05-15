package comp1110.ass2.gui;
/**
 * @Author Danny
 * The way to manage multiple screens is learned from
 * https://www.youtube.com/watch?v=5GsdaZWDcdY
 */
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

public class Game extends Application {
    public static String screen1ID = "welcome";
    public static String screen1File = "welcome.fxml";
    public static String screen2ID = "playerNum";
    public static String screen2File = "playernum.fxml";
    public static String screen3ID = "game";
    public static String screen3File = "game.fxml";
    public static String screen4ID = "botnum";
    public static String screen4File = "botnum.fxml";
    public static String screen5ID = "gamebot";
    public static String screen5File = "gamebot.fxml";
    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;

    /* where to find media assets */
    private static final String URI_BASE = "assets/";

    /* Loop in public domain */
    private static final String LOOP_URI = Game.class.getResource(URI_BASE + "BGM.wav").toString();
    private AudioClip loop;


    private void setUpSoundLoop() {
        try {
            loop = new AudioClip(LOOP_URI);
            loop.setCycleCount(AudioClip.INDEFINITE);
        } catch (Exception e) {
            System.err.println(":-( something bad happened (" + LOOP_URI + "): " + e);
        }
    }

    // FIXME Task 9: Implement a basic playable Warring States game in JavaFX
    // Task 9 Code is in GameController.java and game.fxml
    // FIXME Task 11: Allow players of your Warring States game to play against your simple agent

    // FIXME Task 12: Integrate a more advanced opponent into your game

    @Override
    public void start(Stage primaryStage) throws Exception {
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(Game.screen1ID, Game.screen1File);
        mainContainer.loadScreen(Game.screen2ID, Game.screen2File);
        mainContainer.loadScreen(Game.screen3ID, Game.screen3File);
        mainContainer.loadScreen(Game.screen4ID, Game.screen4File);
        mainContainer.loadScreen(Game.screen5ID, Game.screen5File);
        mainContainer.setScreen(Game.screen1ID);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);

        primaryStage.setTitle("Warring States Game (Early Access)");
        primaryStage.setScene(new Scene(root, BOARD_WIDTH, BOARD_HEIGHT));
        primaryStage.show();
        setUpSoundLoop();
        loop.play();

    }
}

