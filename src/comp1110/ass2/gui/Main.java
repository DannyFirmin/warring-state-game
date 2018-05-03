package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("wsg.fxml"));
        primaryStage.setTitle("Warring States Game (Preview)");
        primaryStage.setScene(new Scene(root, 933, 700));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
