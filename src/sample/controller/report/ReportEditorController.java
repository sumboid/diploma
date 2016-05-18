package sample.controller.report;

import javafx.fxml.FXML;
import javafx.scene.web.HTMLEditor;

public class ReportEditorController {
    @FXML HTMLEditor reportEditor;

    public void setHTML(String html) {
        reportEditor.setHtmlText(html);
    }
}
