package sample.controller.report;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class ReportEditorController {
    @FXML private HTMLEditor reportEditor;

    private Window window;
    private Stage stage;
    private ReportController parentController;

    @FXML public void onSave() throws IOException {
        String html = reportEditor.getHtmlText();
        parentController.saveReport(window, html.substring(58, html.length() - 14));
        stage.close();
    }

    @FXML public void onCancel() {
        stage.close();
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setParentController(ReportController controller) {
        this.parentController = controller;
    }
}
