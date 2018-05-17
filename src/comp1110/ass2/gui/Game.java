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

import static comp1110.ass2.WarringStatesGame.*;

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
    /**
     * to give the number of cards will be taken on a certain destination
     *
     * @param location one move to
     * @param placement current placement string
     * @return number of taken cards after one move.
     * @authur:Ben
     */
    public static int takeCards(char location, String placement) {

        char[][] board = Board.board;
        char kingdom='0' ;
        char b;
        char c = '0'; //Zhangyi's 3rd char
        int zrow = 0;//Zhangyi's row on board
        int zcol = 0;//Zhangyi's column on board
        int lrow = 0;//destination's row on board
        int lcol = 0;//destination's column on board
        String[][] occupation = placementToOccupation(placement, board);
        int count = 0;
        for(int i=2;i<placement.length();i=i+3){
            if (location==placement.charAt(i)){
                kingdom=placement.charAt(i-2);
                break;
            }
        }

        //find Zhangyi
        for (int i = 0; i < placement.length(); i = i + 3) {
            if (placement.charAt(i) == 'z') {
                c = placement.charAt(i + 2);
                break;
            }
        }
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
        //find the kingdom card on location
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[i][j] == location) {
                    kingdom = occupation[i][j].charAt(0);
                }
            }
        }

        //find location on board
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[i][j] == location) {
                    lrow = i;
                    lcol = j;
                }
            }
        }

        if (zrow == lrow) {
            if (zcol > lcol) {
                for (int i = lcol - 1; i >= 0; i--) {
                    if (occupation[zrow][i].charAt(0) == kingdom) {
                        count++;
                    }
                }
            } else {
                for (int i = lcol + 1; i < 6; i++) {
                    if (occupation[zrow][i].charAt(0) == kingdom) {
                        count++;
                    }
                }
            }
        }
        if (zcol == lcol) {
            if (zrow > lrow) {
                for (int i = lrow - 1; i >= 0; i--) {
                    if (occupation[i][zcol].charAt(0) == kingdom) {
                        count++;
                    }
                }
            } else {
                for (int i = lrow + 1; i < 6; i++) {
                    if (occupation[i][zcol].charAt(0) == kingdom) {
                        count++;
                    }
                }
            }
        }
        return count;
    }


    /**
     * return a new placement after one move
     *
     * @param location one move to
     * @param placement current placement string
     * @return a new string of placement after one move.
     * @authur:Ben
     */
    public static String newPlacement(char location, String placement) {
        char[][] board = Board.board;
        char kingdom='0' ;
        char b;
        char c = '0'; //Zhangyi's 3rd char
        int zrow = 0;//Zhangyi's row on board
        int zcol = 0;//Zhangyi's column on board
        int lrow = 0;//destination's row on board
        int lcol = 0;//destination's column on board
        String[][] occupation = placementToOccupation(placement, board);
        String placementafter="";
        // replace the destination on occupation to "z9", delete the information of taken cards
        occupation[lrow][lcol] = "z9";
        if (zrow == lrow) {
            if (zcol > lcol) {
                for (int i = lcol - 1; i >= 0; i--) {
                    if (occupation[zrow][i].charAt(0) == kingdom) {
                        occupation[zrow][i] = "~~";
                        board[zrow][i]='~';
                        break;
                    }
                }
            } else {
                for (int i = lcol + 1; i < 6; i++) {
                    if (occupation[zrow][i].charAt(0) == kingdom) {
                        occupation[zrow][i] = "~~";
                        board[zrow][i]='~';
                        break;
                    }
                }
            }
        }
        if (zcol == lcol) {
            if (zrow > lrow) {
                for (int i = lrow - 1; i >= 0; i--) {
                    if (occupation[i][zcol].charAt(0) == kingdom) {
                        occupation[i][zcol] = "~~";
                        board[i][zcol]='~';
                        break;
                    }
                }
            } else {
                for (int i = lrow + 1; i < 6; i++) {
                    if (occupation[i][zcol].charAt(0) == kingdom) {
                        occupation[i][zcol] = "~~";
                        board[i][zcol]='~';
                        break;
                    }
                }
            }
        }
        //transfer the occupation back to placement after Zhangyi moves
        placementafter=occupationToPlacement(occupation,board);
        return placementafter;
    }

    /**
     * return a location which takes most cards in all legal moves
     * @param placement current placement string
     * @return a location char
     * @authur:Ben
     */
    public static char generateMaxMove(String placement) {
        char location = '\0';
        char[][] board = Board.board;
        int count=-1;
        char loc='\0';
        for (int i = 0; i < 36; i++) {
            if (i >= 0 & i <= 9) {
                if (isMoveLegal(placement, (char) (i + 48))) {
                    location = (char) (i + 48);
                    if(takeCards(location, placement)>count) {
                        count = takeCards(location, placement);
                        loc = location;
                    }
                }
            }
            if (i >= 10 & i <36) {
                if (isMoveLegal(placement, (char) (i + 65-10))) {
                    location = (char) (i + 65-10);
                    if(takeCards(location, placement)>count){
                        count=takeCards(location,placement);
                        loc=location;
                    }
                }
            }
        }
        return loc;
    }

    public static String generateMaxMoves(String placement) {
        char location = '\0';
        char[][] board = Board.board;
        int count=-1;
        String loc="";
        for (int i = 0; i < 36; i++) {
            if (i >= 0 & i <= 9) {
                if (isMoveLegal(placement, (char) (i + 48))) {
                    location = (char) (i + 48);
                    if(takeCards(location, placement)>count) {
                        count = takeCards(location, placement);
                        loc = location+"";
                    }else if(takeCards(location, placement)==count){
                        loc = loc+location;
                    }
                }
            }
            if (i >= 10 & i <36) {
                if (isMoveLegal(placement, (char) (i + 65-10))) {
                    location = (char) (i + 65-10);
                    if(takeCards(location, placement)>count){
                        count=takeCards(location,placement);
                        loc = location+"";
                    }else if(takeCards(location, placement)==count){
                        loc = loc+location;
                    }
                }
            }
        }
        return loc;
    }


    /**
     * return a location which minimum the upper number of cards of the next move in all legal moves
     * @param placement current placement string
     * @return a location char
     * @authur:Ben
     */
    public static char generateNextMinMove(String placement) {
        char location = '\0';
        char[][] board = Board.board;
        String placementafter="";
        int cardnum=0;
        String cardsnum="";
        for (int i = 0; i < 36; i++) {
            if (i >= 0 & i <= 9) {
                if (isMoveLegal(placement, (char) (i + 48))) {
                    location = (char) (i + 48);
                    placementafter = newPlacement(location, placement);
                    if (takeCards(location, placementafter) > cardnum) {
                        cardnum = takeCards(location, placement);
                        cardsnum = cardsnum + location + cardnum;
                        cardnum=0;
                    }
                }
            }
            if (i >= 10 & i < 36) {
                if (isMoveLegal(placement, (char) (i + 65 -10 ))) {
                    location = (char) (i + 65 -10);
                    placementafter = newPlacement(location, placement);
                    if (takeCards(location, placementafter) > cardnum) {
                        cardnum = takeCards(location, placement);
                        cardsnum = cardsnum + location + cardnum;
                        cardnum=0;
                    }
                }
            }
        }
        int least=6;
        if(cardsnum.length()>1) {
            for (int i = 1; i < cardsnum.length(); i = i + 2) {
                if (cardsnum.charAt(i) - 48 < least) {
                    least = cardsnum.charAt(i) - 48;
                    location = cardsnum.charAt(i - 1);
                }
            }
        }
        return location;
    }

    public static String generateNextMinMoves(String placement) {
        char location = '\0';
        String loc="";
        char[][] board = Board.board;
        String placementafter="";
        int cardnum=0;
        String cardsnum="";
        for (int i = 0; i < 36; i++) {
            if (i >= 0 & i <= 9) {
                if (isMoveLegal(placement, (char) (i + 48))) {
                    location = (char) (i + 48);
                    placementafter = newPlacement(location, placement);
                    if (takeCards(location, placementafter) > cardnum) {
                        cardnum = takeCards(location, placement);
                        cardsnum = cardsnum + location + cardnum;
                        cardnum=0;
                    }
                }
            }
            if (i >= 10 & i < 36) {
                if (isMoveLegal(placement, (char) (i + 65 -10 ))) {
                    location = (char) (i + 65 -10);
                    placementafter = newPlacement(location, placement);
                    if (takeCards(location, placementafter) > cardnum) {
                        cardnum = takeCards(location, placement);
                        cardsnum = cardsnum + location + cardnum;
                        cardnum=0;
                    }
                }
            }
        }
        int least=6;
        if(cardsnum.length()>1) {
            for (int i = 1; i < cardsnum.length(); i = i + 2) {
                if (cardsnum.charAt(i) - 48 < least) {
                    least = cardsnum.charAt(i) - 48;
                    location = cardsnum.charAt(i - 1);
                    loc=location+"";
                }else if(cardsnum.charAt(i) - 48 == least){
                    loc=loc+location;
                }
            }
        }
        return loc;
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

