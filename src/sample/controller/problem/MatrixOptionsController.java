package sample.controller.problem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class MatrixOptionsController {
    private Node pane;
    private Node self;

    @FXML public void handleLoadMatrix(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        final Window window = ((Node)event.getTarget()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(window);
        if (file != null) {
            System.out.println(file.getAbsolutePath());
        }
    }

    @FXML public void handleGenerateMatrix() {
        ((Pane) pane).getChildren().removeAll(self);
    }

    public void setParentContainer(Node pane) {
        this.pane = pane;
    }

    public void setSelf(Node self) {
        this.self = self;
    }
}
