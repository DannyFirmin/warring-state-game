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


public class PlayerNumController implements Initializable, ControlledScreen {
    ScreensController myController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }
    public static int numPlayers =2;
    @FXML
    private Button p2;
    @FXML
    private Button p3;
    @FXML
    private Button p4;
    @FXML
    void startp2(ActionEvent event) {
        myController.setScreen(Game.screen3ID);
        numPlayers = 2;

    }
    @FXML
    void startp3(ActionEvent event) {
        myController.setScreen(Game.screen3ID);
        numPlayers = 3;
    }
    @FXML
    void startp4(ActionEvent event) {
        myController.setScreen(Game.screen3ID);
        numPlayers = 4;


    }
}