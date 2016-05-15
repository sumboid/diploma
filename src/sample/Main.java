package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.JavaFXBuilderFactory;
import sample.model.Model;
import sample.model.algorithm.data.Parametry;
import sample.model.algorithm.data.*;

public class Main extends Application {
    private Model model = new Model();
    @Override
    public void start(Stage primaryStage) throws Exception{
        DistanzMatrix dm=DistanzMatrix.generate(5,10,20);
        System.out.println();
        PheromonMatrix ph=PheromonMatrix.generate(5);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sample.fxml"));
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());


        Parent root = fxmlLoader.load(getClass().getResource("sample.fxml").openStream());
        Controller controller = fxmlLoader.getController();
        primaryStage.setTitle("The Swarm optimization algorithms");
        primaryStage.setScene(new Scene(root, 700, 600));
        primaryStage.show();


        model.setController(controller);
        Parametry parametry= new Parametry();
        model.start(500, parametry, "DistanzMatrix.m","PhMatrix.m");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
