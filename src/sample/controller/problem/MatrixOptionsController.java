package sample.controller.problem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import sample.controller.problem.internal.MatrixConverter;
import sample.controller.problem.internal.PathLength;
import sample.model.algorithm.data.DistanzMatrix;

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
        final int NODE_NUMBERS = 3;
        final DistanzMatrix matrix = DistanzMatrix.generate(NODE_NUMBERS, 1, 2);
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
    }

    public void setParentContainer(Node pane) {
        this.pane = pane;
    }

    public void setSelf(Node self) {
        this.self = self;
    }
}
