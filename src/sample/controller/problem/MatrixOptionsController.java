package sample.controller.problem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import sample.controller.problem.internal.MatrixConverter;
import sample.controller.problem.internal.PathLength;
import sample.model.algorithm.data.DistanzMatrix;

import java.io.File;
import java.util.Optional;

public class MatrixOptionsController {
    private Node pane;
    private Node self;
    private Button saveButton;

    private DistanzMatrix matrix = null;

    public void renderMatrix(DistanzMatrix matrix) {
        ((Pane) pane).getChildren().removeAll(self);
        TableView table = new TableView();
        table.setEditable(true);

        TableColumn aCol = new TableColumn("A");
        TableColumn bCol = new TableColumn("B");
        TableColumn lengthCol = new TableColumn("Length");

        aCol.setCellValueFactory(new PropertyValueFactory<PathLength, Integer>("a"));
        bCol.setCellValueFactory(new PropertyValueFactory<PathLength, Integer>("b"));
        lengthCol.setCellValueFactory(new PropertyValueFactory<PathLength, Integer>("length"));

        aCol.setMinWidth(100);
        bCol.setMinWidth(100);
        lengthCol.setMinWidth(100);

        table.setItems(MatrixConverter.matrixToPath(matrix));
        table.getColumns().addAll(aCol, bCol, lengthCol);


        ((AnchorPane) pane).setTopAnchor(table, 0.0);
        ((AnchorPane) pane).setBottomAnchor(table, 0.0);
        ((AnchorPane) pane).setLeftAnchor(table, 0.0);
        ((AnchorPane) pane).setRightAnchor(table, 0.0);
        ((Pane) pane).getChildren().add(table);

        saveButton.setDisable(false);
    }

    @FXML public void handleLoadMatrix(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        final Window window = ((Node)event.getTarget()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(window);
        if (file != null) {
            DistanzMatrix matrix = DistanzMatrix.buildDMatrixFromFile(file.getAbsolutePath());
            this.matrix = matrix;
            renderMatrix(matrix);
        }
    }

    @FXML public void handleGenerateMatrix() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Matrix size");
        dialog.setHeaderText("Matrix size");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            int NODE_NUMBERS = 0;
            try {
                NODE_NUMBERS = Integer.parseInt(result.get());
                if (NODE_NUMBERS > 1000) return;
            } catch(java.lang.NumberFormatException e) {
                return;
            }

            final DistanzMatrix matrix = DistanzMatrix.generate(NODE_NUMBERS, 1, 300);
            this.matrix = matrix;
            renderMatrix(matrix);
        }
    }

    public void setParentContainer(Node pane) {
        this.pane = pane;
    }
    public void setSelf(Node self) {
        this.self = self;
    }
    public void setSaveButton(Button button) {
        this.saveButton = button;
    }
    public DistanzMatrix getMatrix() {
        return this.matrix;
    }
    public void setMatrix(DistanzMatrix matrix) {
        this.matrix = matrix;
        renderMatrix(matrix);
    }
}
