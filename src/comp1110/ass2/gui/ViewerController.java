
package comp1110.ass2.gui;
/**
 * The codes here is for Task 4
 * All codes in this file is done by Danny
 *
 * @Author: Danny
 * @Description: JavaFX Event handler
 */

import comp1110.ass2.Board;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.HashMap;

import static comp1110.ass2.WarringStatesGame.isPlacementWellFormed;
import static comp1110.ass2.WarringStatesGame.placementToOccupation;

public class ViewerController {

    @FXML
    private TextField placetext;

    @FXML
    private GridPane grid;

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
        alert.setTitle("About Warring States Game V0.1");
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
     * @Author: Danny
     * @Description: Put the card in the right place according to the user placement input
     */
    @FXML
    void makePlacement(ActionEvent event) {
        String newPlacement = placetext.getText();
        if (isPlacementWellFormed(newPlacement)) {
            grid.getChildren().clear();
            char[][] board = Board.board;
            String[][] occupation = placementToOccupation(newPlacement, board);
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

        if (!isPlacementWellFormed(newPlacement)) {
            System.out.println(newPlacement + "is invalid");
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
            placetext.clear();
        }
    }


}

