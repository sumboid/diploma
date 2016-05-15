package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import sample.controller.problem.ProblemController;
import sample.controller.runtime.RuntimeController;

public class MainController {
    @FXML private Tab runtime;
    @FXML private RuntimeController runtimeController;

    @FXML private Tab problem;
    @FXML private ProblemController problemController;

    public RuntimeController getRuntimeController() {
        return runtimeController;
    }

    @FXML public void initialize() {
        System.out.println(problemController);
        System.out.println(runtimeController);
    }
}

