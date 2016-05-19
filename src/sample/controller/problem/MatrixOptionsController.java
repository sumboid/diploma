package sample.controller.problem;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import sample.controller.problem.internal.MatrixConverter;
import sample.controller.problem.internal.PathLength;
import sample.controller.report.ReportEditorController;
import sample.model.algorithm.data.DistanzMatrix;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class MatrixOptionsController {
    private Node pane;
    private Node self;
    private Button saveButton;

    private DistanzMatrix matrix = null;
    private ObservableList<PathLength> pathData = null;

    public void renderMatrix(DistanzMatrix matrix) {
        ((Pane) pane).getChildren().removeAll(self);
        TableView table = new TableView();
        table.setEditable(true);

        TableColumn aCol = new TableColumn("A");
        TableColumn bCol = new TableColumn("B");
        TableColumn lengthCol = new TableColumn("Length");

        aCol.setCellValueFactory(new PropertyValueFactory<PathLength, Integer>("a"));
        bCol.setCellValueFactory(new PropertyValueFactory<PathLength, Integer>("b"));
        lengthCol.setCellValueFactory(new PropertyValueFactory<PathLength, String>("length"));

        lengthCol.setCellFactory(TextFieldTableCell.forTableColumn());

        lengthCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<PathLength, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<PathLength, String> t) {
                        try {
                            Double.parseDouble(t.getNewValue());
                            t.getTableView().getItems().get(t.getTablePosition().getRow()).setLength(t.getNewValue());
                        } catch (NumberFormatException e) {
                            t.getTableView().getItems().get(t.getTablePosition().getRow()).setLength(t.getOldValue());
                            t.getTableView().getColumns().get(0).setVisible(false);
                            t.getTableView().getColumns().get(0).setVisible(true);
                        }
                    }
                }
        );

        aCol.setMinWidth(100);
        bCol.setMinWidth(100);
        lengthCol.setMinWidth(100);

        pathData = MatrixConverter.matrixToPath(matrix);
        table.setItems(pathData);
        table.getColumns().addAll(aCol, bCol, lengthCol);


        AnchorPane.setTopAnchor(table, 0.0);
        AnchorPane.setBottomAnchor(table, 0.0);
        AnchorPane.setLeftAnchor(table, 0.0);
        AnchorPane.setRightAnchor(table, 0.0);
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

    @FXML public void handleGenerateMatrix(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("view/htmlEditor.fxml"));
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

            Parent root = fxmlLoader.load(getClass().getResource("../../view/generateDialog.fxml").openStream());
            GenerateDialogController controller = fxmlLoader.getController();
            Stage stage = new Stage();
            controller.setStage(stage);
            controller.setController(this);

            stage.setTitle("Параметры матрицы");
            stage.setScene(new Scene(root, 400, 200));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleOkay(Integer size, Integer max, Integer min) {
        final DistanzMatrix matrix = DistanzMatrix.generate(size, min, max);
        this.matrix = matrix;
        renderMatrix(matrix);
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
        return MatrixConverter.pathToMatrix(pathData);
    }
    public void setMatrix(DistanzMatrix matrix) {
        this.matrix = matrix;
        renderMatrix(matrix);
    }

}
