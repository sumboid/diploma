package sample.controller.problem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import sample.model.algorithm.data.DistanzMatrix;
import sample.model.algorithm.data.Parameters;
import sample.model.algorithm.data.PheromonMatrix;
import sample.model.problem.Problem;
import sample.model.utils.FileWorker;

import java.io.*;

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

    @FXML private Button saveProblem;

    @FXML private Pane matrixContainer;

    @FXML public void handleSaveProblem(ActionEvent event) {
        Parameters params = new Parameters();
        params.ANTS_NUMBER=Integer.parseInt(TEXTANTSNUMBER.getText());
        params.al=Double.parseDouble(TEXT_AL.getText());
        params.b=Double.parseDouble(TEXT_B.getText());
        params.p=Double.parseDouble(TEXT_P.getText());
        params.q=Double.parseDouble(TEXT_Q.getText());
        params.endWhile=Integer.parseInt(TEXT_ENDWHILE.getText());
        params.lifeСycle=Integer.parseInt(TEXT_lifeCycle.getText());

        DistanzMatrix dm = matrixOptionsController.getMatrix();
        PheromonMatrix phm = PheromonMatrix.generate(dm.n);

        Problem problem = new Problem(phm, dm, params);

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
                Parameters params = problem.getParams();
                setParameters(params);
                matrixOptionsController.setMatrix(problem.getDMatrix());
            }
        }

    }

    @FXML public void handleCreateNewProblem() {
        matrixContainer.getChildren().removeAll(matrixContainer.getChildren());
        matrixContainer.getChildren().addAll(matrixOptions);
        saveProblem.setDisable(true);
        setParameters(new Parameters());
    }

    @FXML public void initialize() {
        setParameters(new Parameters());
        matrixOptionsController.setParentContainer(matrixContainer);
        matrixOptionsController.setSelf(matrixOptions);
        matrixOptionsController.setSaveButton(saveProblem);
    }

    @FXML private void setParameters(Parameters params) {
        TEXTANTSNUMBER.setText(new Integer(params.ANTS_NUMBER).toString());
        TEXT_AL.setText(new Double(params.al).toString());
        TEXT_B.setText(new Double(params.b).toString());
        TEXT_P.setText(new Double(params.p).toString());
        TEXT_Q.setText(new Double(params.q).toString());
        TEXT_ENDWHILE.setText(new Integer(params.endWhile).toString());
        TEXT_lifeCycle.setText(new Integer(params.endWhile).toString());
    }
}
