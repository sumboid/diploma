package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import sample.controller.library.LibraryController;
import sample.controller.problem.ProblemController;
import sample.controller.report.ReportController;
import sample.controller.runtime.RuntimeController;

public class MainController {
    @FXML private Tab runtime;
    @FXML private RuntimeController runtimeController;

    @FXML private Tab problem;
    @FXML private ProblemController problemController;

    @FXML private Tab report;
    @FXML private ReportController reportController;

    @FXML private Tab library;
    @FXML private LibraryController libraryController;

    public RuntimeController getRuntimeController() {
        return runtimeController;
    }

    @FXML public void initialize() {
        System.out.println(problemController);
        System.out.println(runtimeController);
    }
}

