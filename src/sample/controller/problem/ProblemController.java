package sample.controller.problem;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class ProblemController {
    @FXML private GridPane matrixOptions;
    @FXML private MatrixOptionsController matrixOptionsController;

    @FXML private TextField TEXTANTSNUMBER;
    @FXML private TextField TEXT_P;
    @FXML private TextField TEXT_Q;
    @FXML private TextField TEXT_AL;
    @FXML private TextField TEXT_B;
    @FXML private TextField TEXT_ENDWHILE;
    @FXML private TextField TEXT_lifeCycle;

    @FXML private Pane matrixContainer;

    @FXML public void createNewProblem() {
        matrixContainer.getChildren().removeAll(matrixContainer.getChildren());
        matrixContainer.getChildren().addAll(matrixOptions);
    }
    @FXML public void handleLoadProblem() {}

    @FXML public void initialize() {
        matrixOptionsController.setParentContainer(matrixContainer);
        matrixOptionsController.setSelf(matrixOptions);
    }
}
