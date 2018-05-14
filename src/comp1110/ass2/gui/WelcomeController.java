package comp1110.ass2.gui;
/**
 * @Author Danny
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable, ControlledScreen {
    ScreensController myController;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }


    @FXML
    private Button pf;

    @FXML
    void playerNum(ActionEvent event) {
        myController.setScreen(Game.screen2ID);

    }

}
