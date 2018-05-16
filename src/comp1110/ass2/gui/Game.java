package comp1110.ass2.gui;
/**
 * @Author Danny
 * The way to manage multiple screens is learned from
 * https://www.youtube.com/watch?v=5GsdaZWDcdY
 */
import comp1110.ass2.Board;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import static comp1110.ass2.WarringStatesGame.getSupporters;
import static comp1110.ass2.WarringStatesGame.isMoveLegal;

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
    // Task 9 Code for implement the game is separated in GameController.java, WarringStatesGame.java and game.fxml

    // FIXME Task 11: Allow players of your Warring States game to play against your simple agent
    // Task 11 Code is in GameBotController.java
    // FIXME Task 12: Integrate a more advanced opponent into your game
    //improve the performance of generator
    public static char betterMove(char zhangyi;String placement) {

        char location='\0';
        char[][] board = Board.board;
        for (int i=0;i<=36;i++) {
            if (i >= 0 & i <= 9) {
                if (isMoveLegal(placement, (char) (i + 48))) {
                    location = (char) (i + 48);
                }
            } else {
                if (isMoveLegal(placement, (char) (i + 65))) {
                    location = (char) (i + 65);
                }
            }
            char kingdom=placement.charAt(placement.indexOf(location)-2);
            if (zhangyi%6 == location%6) {
                if (zcol > lcol) {
                    for (int i = lcol - 1; i >= 0; i--) {
                        if (occupation[zrow][i].charAt(0) == kingdom) {
                            noFurther = false;
                            break;
                        }
                    }
                } else {
                    for (int i = lcol + 1; i < 6; i++) {
                        if (occupation[zrow][i].charAt(0) == kingdom) {
                            noFurther = false;
                            break;
                        }
                    }
                }
            }
            if (zcol == lcol) {
                if (zrow > lrow) {
                    for (int i = lrow - 1; i >= 0; i--) {
                        if (occupation[i][zcol].charAt(0) == kingdom) {
                            noFurther = false;
                            break;
                        }
                    }
                } else {
                    for (int i = lrow + 1; i < 6; i++) {
                        if (occupation[i][zcol].charAt(0) == kingdom) {
                            noFurther = false;
                            break;
                        }
                    }
                }
            }
        }




        boolean result = false;
        char b;
        char c = '0'; //Zhangyi's 3rd char
        int zrow = 0;//Zhangyi's row on board
        int zcol = 0;//Zhangyi's column on board
        int lrow = 0;//destination's row on board
        int lcol = 0;//destination's column on board
        char kingdom = 'z';//kingdom card of destination

        //find Zhangyi on board
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[i][j] == c) {
                    zrow = i;
                    zcol = j;
                    break;
                }
            }
        }



        return location;
    }


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

        primaryStage.setTitle("Warring States Game (Early Access) V0.5");
        primaryStage.setScene(new Scene(root, BOARD_WIDTH, BOARD_HEIGHT));
        primaryStage.show();
        setUpSoundLoop();
        loop.play();

    }
}

