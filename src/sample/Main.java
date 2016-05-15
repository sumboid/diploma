package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.JavaFXBuilderFactory;
import sample.controller.MainController;
import sample.model.Model;
import sample.model.algorithm.data.*;
import javafx.scene.input.KeyEvent;
import sample.model.problem.Problem;

import java.awt.event.InputEvent;

public class Main extends Application {
    private Model model = new Model();
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("view/main.fxml"));
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());


        Parent root = fxmlLoader.load(getClass().getResource("view/main.fxml").openStream());
        MainController controller = fxmlLoader.getController();
        primaryStage.setTitle("The Swarm optimization algorithms");
        primaryStage.setScene(new Scene(root, 700, 600));
        primaryStage.show();


        model.setController(controller.getRuntimeController());
        controller.setModel(model);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
