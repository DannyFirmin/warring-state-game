package comp1110.ass2.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;

public class Controller {

    @FXML
    private MenuItem quit;

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
}
