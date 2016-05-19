package sample.controller.problem;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GenerateDialogController {
    @FXML private TextField size;
    @FXML private TextField min;
    @FXML private TextField max;

    private MatrixOptionsController ctrl;
    private Stage stage;

    public int getMin() {
        return Integer.parseInt(min.getText());
    }
    public int getMax() {
        return Integer.parseInt(max.getText());
    }
    public int getMatrixSize() {
        return Integer.parseInt(size.getText());
    }

    public void setController(MatrixOptionsController ctrl) {
        this.ctrl = ctrl;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML public void onOkay() {
        try {
            this.ctrl.handleOkay(getMatrixSize(), getMin(), getMax());
        } catch(java.lang.NumberFormatException e) {
                return;
        }
        stage.close();

    }
}
