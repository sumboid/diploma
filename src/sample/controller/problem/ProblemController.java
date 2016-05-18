package sample.controller.problem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import sample.model.algorithm.data.DistanzMatrix;
import sample.model.algorithm.data.OverParams;
import sample.model.algorithm.data.Parameters;
import sample.model.algorithm.data.PheromonMatrix;
import sample.model.problem.Problem;
import sample.model.utils.FileWorker;

import java.io.*;

public class ProblemController {
    @FXML private GridPane matrixOptions;
    @FXML private MatrixOptionsController matrixOptionsController;
    @FXML private TextField problemName;

    @FXML private Button saveProblem;
    @FXML private Pane matrixContainer;

    @FXML public void handleSaveProblem(ActionEvent event) {
        DistanzMatrix dm = matrixOptionsController.getMatrix();
        PheromonMatrix phm = PheromonMatrix.generate(dm.n);

        Problem problem = new Problem(phm, dm, problemName.getText());

        final FileChooser fileChooser = new FileChooser();
        final Window window = ((Node)event.getTarget()).getScene().getWindow();
        File file = fileChooser.showSaveDialog(window);

        if (file != null) {
            String path = file.getAbsolutePath();
            FileWorker.writeObjectToFile(problem, path);
        }
    }

    @FXML public void handleLoadProblem(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        final Window window = ((Node)event.getTarget()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(window);
        if (file != null) {
            String path = file.getAbsolutePath();
            Problem problem = (Problem) FileWorker.readObjectFromFile(path);
            if (problem != null) {
                matrixOptionsController.setMatrix(problem.getDMatrix());
            }
        }
    }

    @FXML public void handleCreateNewProblem() {
        matrixContainer.getChildren().removeAll(matrixContainer.getChildren());
        matrixContainer.getChildren().addAll(matrixOptions);
        saveProblem.setDisable(true);
    }

    @FXML public void initialize() {
        matrixOptionsController.setParentContainer(matrixContainer);
        matrixOptionsController.setSelf(matrixOptions);
        matrixOptionsController.setSaveButton(saveProblem);
    }
}
