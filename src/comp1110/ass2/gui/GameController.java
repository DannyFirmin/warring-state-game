package comp1110.ass2.gui;
/**
 * @Author: Danny
 * @Description: JavaFX Event handler
 */

import comp1110.ass2.Board;
import comp1110.ass2.WarringStatesGame;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import static comp1110.ass2.WarringStatesGame.isPlacementWellFormed;
import static comp1110.ass2.WarringStatesGame.placementToOccupation;
import static comp1110.ass2.WarringStatesGame.updateSetup;

public class GameController implements Initializable, ControlledScreen {

    ScreensController myController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    static final String[] PLACEMENTS = {
            "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"
    };
    String placement="";
    String moveSequence = "";
    private boolean z9IsChosen = false;
    @FXML
    private TextField placetext;

    @FXML
    private GridPane grid;
    @FXML
    private Rectangle z9Rectangle;
    @FXML
    private AnchorPane z9;
    @FXML
    private AnchorPane a0;
    @FXML
    private AnchorPane a1;
    @FXML
    private AnchorPane a2;
    @FXML
    private AnchorPane a3;
    @FXML
    private AnchorPane a4;
    @FXML
    private AnchorPane a5;
    @FXML
    private AnchorPane a6;
    @FXML
    private AnchorPane a7;
    @FXML
    private AnchorPane b0;
    @FXML
    private AnchorPane b1;
    @FXML
    private AnchorPane b2;
    @FXML
    private AnchorPane b3;
    @FXML
    private AnchorPane b4;
    @FXML
    private AnchorPane b5;
    @FXML
    private AnchorPane b6;
    @FXML
    private AnchorPane c0;
    @FXML
    private AnchorPane c1;
    @FXML
    private AnchorPane c2;
    @FXML
    private AnchorPane c3;
    @FXML
    private AnchorPane c4;
    @FXML
    private AnchorPane c5;
    @FXML
    private AnchorPane d0;
    @FXML
    private AnchorPane d1;
    @FXML
    private AnchorPane d2;
    @FXML
    private AnchorPane d3;
    @FXML
    private AnchorPane d4;
    @FXML
    private AnchorPane e0;
    @FXML
    private AnchorPane e1;
    @FXML
    private AnchorPane e2;
    @FXML
    private AnchorPane e3;
    @FXML
    private AnchorPane f0;
    @FXML
    private AnchorPane f1;
    @FXML
    private AnchorPane f2;
    @FXML
    private AnchorPane g0;
    @FXML
    private AnchorPane g1;

    @FXML
    void exitProgram(ActionEvent event) {
        Platform.exit();

    }

    @FXML
    void aboutProgram(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Warring States Game V0.2");
        alert.setHeaderText(null);
        alert.setContentText("This game is developed by ANU COMP1110 wed16m.\n \nDevelopers : Danny Feng (u6611178), Vishnuvardhan Jasti(u6611697) and Chi Ben (u6555078)");

        alert.showAndWait();
    }

    @FXML
    void regret(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Sorry, you can't undo your movement");
        alert.setContentText("Don't Cry Over Spilt Milk.");

        alert.showAndWait();
    }

    /**
     * @Author: Danny Feng
     * @Description: Put the card in the right place according to the user placement input
     */

    @FXML
    void toWelcome(ActionEvent event) {
        myController.setScreen(Game.screen1ID);
    }

    @FXML
    void generateSetup(ActionEvent event) {
        placement(PLACEMENTS[(int) (Math.random() * 19)]);

    }

    void placement(String setup) {
        placement = setup;
        if (isPlacementWellFormed(setup)) {
            grid.getChildren().clear();
            char[][] board = Board.board;
            String[][] occupation = placementToOccupation(setup, board);
            HashMap map = new HashMap();
            map.put("z9", z9);
            map.put("a0", a0);
            map.put("a1", a1);
            map.put("a2", a2);
            map.put("a3", a3);
            map.put("a4", a4);
            map.put("a5", a5);
            map.put("a6", a6);
            map.put("a7", a7);
            map.put("b0", b0);
            map.put("b1", b1);
            map.put("b2", b2);
            map.put("b3", b3);
            map.put("b4", b4);
            map.put("b5", b5);
            map.put("b6", b6);
            map.put("c0", c0);
            map.put("c1", c1);
            map.put("c2", c2);
            map.put("c3", c3);
            map.put("c4", c4);
            map.put("c5", c5);
            map.put("d0", d0);
            map.put("d1", d1);
            map.put("d2", d2);
            map.put("d3", d3);
            map.put("d4", d4);
            map.put("e0", e0);
            map.put("e1", e1);
            map.put("e2", e2);
            map.put("e3", e3);
            map.put("f0", f0);
            map.put("f1", f1);
            map.put("f2", f2);
            map.put("g0", g0);
            map.put("g1", g1);

            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    String A = "" + occupation[i][j].charAt(0) + occupation[i][j].charAt(1);
                    if (!A.equals("~~")) {
                        grid.add((Node) map.get(A), j, i);
                    }
                }
            }
        }

        if (!isPlacementWellFormed(setup)) {
            System.out.println(setup + "is invalid");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Invalid Placement Format");
            alert.setContentText("Please check the Placement format\n" +
                    "* - it consists of exactly three characters\n" +
                    "     * - the first character is in the range a..g (kingdom) or is z (Zhang Yi)\n" +
                    "     * - the second character is numeric, and is a valid character number for that kingdom (9 for Zhang Yi)\n" +
                    "     * - the third character is in the range A .. Z or 0..9 (location)\n" +
                    "* - it consists of exactly N three-character card placements (where N = 1 .. 36);\n" +
                    "     * - each card placement is well-formed\n" +
                    "     * - no card appears more than once in the placement\n" +
                    "     * - no location contains more than one card");
            alert.showAndWait();
        }
        System.out.println(placement);
    }

    @FXML
    void makePlacement(ActionEvent event) {
        String newPlacement = placetext.getText();
        placement(newPlacement);
        placetext.clear();

    }


    @FXML
    void handleDragOver(DragEvent event) {
        event.acceptTransferModes(TransferMode.ANY);

    }

    @FXML
    void handleDrop(DragEvent event) {
        grid.getChildren().remove(a1);

    }

    @FXML
    void handleDragDetection(MouseEvent event) {
        Dragboard db = z9.startDragAndDrop(TransferMode.ANY);
        event.consume();
    }

    ArrayList storePosition = new ArrayList<>();

    @FXML
    void handlePressZ9(MouseEvent event) {
        if (!z9IsChosen) {
            z9Rectangle.setStroke(Color.RED);
            z9Rectangle.setStrokeWidth(3);
            storePosition.add(grid.getRowIndex(z9));
            z9IsChosen = true;
        } else {
            z9Rectangle.setStroke(Color.BLACK);
            z9Rectangle.setStrokeWidth(1);
            z9IsChosen = false;
        }

    }


    static char getLocation(int row, int col) {
        char[][] board = Board.board;
        return board[row][col];
    }

    @FXML
    void handlePress(MouseEvent event) {
        if (z9IsChosen) {
            Node source = (Node) event.getSource();
            int row;
            int col;
            if (GridPane.getRowIndex(source) == null) {
                row = 0;
            } else {
                row = GridPane.getRowIndex(source);
            }
            if (GridPane.getColumnIndex(source) == null) {
                col = 0;
            } else {
                col = GridPane.getColumnIndex(source);
            }

            if (WarringStatesGame.isMoveLegal(placement,getLocation(row,col))){
            grid.getChildren().remove(z9);
            grid.getChildren().remove(source);

            grid.add(z9, col, row);
            moveSequence = moveSequence + getLocation(row, col);

            z9Rectangle.setStroke(Color.BLACK);
            z9Rectangle.setStrokeWidth(1);
            z9IsChosen = false;
            placement = updateSetup(placement,getLocation(row,col));
        }else {        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Movement is invalid");
                alert.setHeaderText(null);
                alert.setContentText("To be valid, each movement must meet the following conditions\n" +
                        "     * - there is a card at the chosen location;\n" +
                        "     * - the location is in the same row or column of the grid as Zhang Yi's current position; and drawing a line from Zhang Yi's current location through the card at the chosen location,\n" +
                        "     * there are no other cards along the line from the same kingdom as the chosen card that are further away from Zhang Yi.");
                alert.showAndWait();
                z9Rectangle.setStroke(Color.BLACK);
                z9Rectangle.setStrokeWidth(1);}
        }
    }
}