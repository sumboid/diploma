package sample.controller.runtime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import sample.model.Model;
import sample.model.algorithm.data.DistanzMatrix;
import sample.model.algorithm.data.Parameters;
import javafx.scene.layout.GridPane;
import sample.model.algorithm.data.PheromonMatrix;
import sample.model.problem.Problem;
import sample.model.utils.FileWorker;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RuntimeController {
    @FXML private LineChart realtimeChart;
    @FXML private GridPane exportedPane;
    @FXML private Pane problemMatrixPane;

    @FXML private ListView runtimeParameters;

    private Model model;
    private Problem problem;

    private XYChart.Series series;

    public static final ObservableList runtimeParametersData = FXCollections.observableArrayList();

    @FXML public void handleChooseProblem(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        final Window window = ((Node)event.getTarget()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(window);
        if (file != null) {
            String path = file.getAbsolutePath();
            this.problem = (Problem) FileWorker.readObjectFromFile(path);
//            sample.model.algorithm.data.Parameters parameters = new sample.model.algorithm.data.Parameters();
//
//            PheromonMatrix phMatr=PheromonMatrix.buildDMatrixFromFile("PhMatrix.m");
//            DistanzMatrix dMatr=DistanzMatrix.buildDMatrixFromFile("DistanzMatrix.m");
//            Problem problem=new Problem(phMatr,dMatr,parameters);
//
//            GridPane gridPane=new GridPane();
//            ColumnConstraints column = new ColumnConstraints(100,100,Double.MAX_VALUE);
//            column.setHgrow(Priority.NEVER);
//            gridPane.getColumnConstraints().addAll(column); // first column gets any extra width
//            Label info=new Label("Количество муравьев");
//            Label infoData=new Label(new Integer(problem.getParams().ANTS_NUMBER).toString());
//            gridPane.add(infoData, 1, 0);
//            gridPane.add(info, 0, 0);
//            runtimeParametersData.add(gridPane);
//
//            GridPane gridPane1=new GridPane();
//            ColumnConstraints column1 = new ColumnConstraints(100,100,Double.MAX_VALUE);
//            column1.setHgrow(Priority.NEVER);
//            gridPane1.getColumnConstraints().addAll(column1); // first column gets any extra width
//            Label info1=new Label("al");
//            Label infoData1=new Label(new Double(problem.getParams().al).toString());
//            gridPane1.add(infoData1, 1, 0);
//            gridPane1.add(info1, 0, 0);
//            runtimeParametersData.add(gridPane1);
//
//            GridPane gridPane2=new GridPane();
//            ColumnConstraints column2 = new ColumnConstraints(100,100,Double.MAX_VALUE);
//            column2.setHgrow(Priority.NEVER);
//            gridPane2.getColumnConstraints().addAll(column2); // first column gets any extra width
//            Label info2=new Label("q");
//            Label infoData2=new Label(new Double(problem.getParams().q).toString());
//            gridPane2.add(infoData2, 1, 0);
//            gridPane2.add(info2, 0, 0);
//            runtimeParametersData.add(gridPane2);
//
//            GridPane gridPane3=new GridPane();
//            ColumnConstraints column3 = new ColumnConstraints(100,100,Double.MAX_VALUE);
//            column3.setHgrow(Priority.NEVER);
//            gridPane3.getColumnConstraints().addAll(column3); // first column gets any extra width
//            Label info3=new Label("Жизненный цикл");
//            Label infoData3=new Label(new Integer(problem.getParams().lifeСycle).toString());
//            gridPane3.add(infoData3, 1, 0);
//            gridPane3.add(info3, 0, 0);
//            runtimeParametersData.add(gridPane3);
        }
    }

    @FXML public void handleSnapshot(ActionEvent event) throws IOException {
//        WritableImage snapShot = exportedPane.snapshot(new SnapshotParameters(), null);
//        ImageIO.write(SwingFXUtils.fromFXImage(snapShot, null), "png", new File("test.png"));
        model.start(problem);
    }


    public void clearChart() {
        series.getData().clear();

    }

    public void setPoint(Double x, Double y) {
        series.getData().add(new XYChart.Data(x, y));
    }

    @FXML
    public void initialize() {
        series = new XYChart.Series();
        series.setName("Скилл Доброго");
        realtimeChart.getData().add(series);
        runtimeParameters.setItems(runtimeParametersData);
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
