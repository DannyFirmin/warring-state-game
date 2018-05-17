package comp1110.ass2.gui;
/**
 * All codes in this file is done by Danny
 * @Author Danny
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

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
    void goPlayer (ActionEvent event) {
        myController.setScreen(Game.screen2ID);
    }

    @FXML
    void goBot(ActionEvent event) {
        myController.setScreen(Game.screen4ID);
    }

}
