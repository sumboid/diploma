package sample.controller.report;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.ArrayList;

public class ReportController {
    @FXML private ListView reportsList;

    private ArrayList reportListCheckBoxChoosed=new ArrayList<>();
    public static final ObservableList reportsListData = FXCollections.observableArrayList();

    @FXML public void reportDelPress(){
        reportsListData.removeAll(reportListCheckBoxChoosed);
    }
    @FXML public void handleChooseReport(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        final Window window = ((Node)event.getTarget()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(window);
        if (file != null) {
            GridPane gridPane = new GridPane();
            ColumnConstraints column1 = new ColumnConstraints(100,100,Double.MAX_VALUE);
            column1.setHgrow(Priority.NEVER);
            gridPane.getColumnConstraints().addAll(column1); // first column gets any extra width

            CheckBox checkbox =new CheckBox();
            checkbox.setSelected(true);
            Label reportName = new Label(file.getAbsolutePath());


            gridPane.add(checkbox, 1, 0);
            gridPane.add(reportName, 0, 0);
            reportsListData.add(gridPane);

            checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov,
                                    Boolean old_val, Boolean new_val) {
                    System.out.println(file.getAbsolutePath());
                    System.out.println(new_val);
                    if (new_val==false) {
                        reportListCheckBoxChoosed.add(gridPane);
                    }
                    if (new_val==true){
                        reportListCheckBoxChoosed.remove(gridPane);
                    }
                    System.out.println(reportListCheckBoxChoosed.get(0));
                    // reportName.setText("2312");
                }
            });
        }
    }

    @FXML public void initialize() {
        reportsList.setItems(reportsListData);
    }
}
