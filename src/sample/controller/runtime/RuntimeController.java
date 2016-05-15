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
            sample.model.algorithm.data.Parameters parameters = new sample.model.algorithm.data.Parameters();

            PheromonMatrix phMatr=PheromonMatrix.buildDMatrixFromFile("PhMatrix.m");
            DistanzMatrix dMatr=DistanzMatrix.buildDMatrixFromFile("DistanzMatrix.m");
            Problem problem=new Problem(phMatr,dMatr,parameters);

            // GridPane gridPane=new GridPane();
           // ColumnConstraints column = new ColumnConstraints(100,100,Double.MAX_VALUE);
          //  column.setHgrow(Priority.NEVER);
            //gridPane.getColumnConstraints().addAll(column); // first column gets any extra width
            //gridPane.add(infoData, 1, 0);
            //gridPane.add(info, 0, 0);
          //  Label info=new Label("Количество муравьев: "+problem.getParams().ANTS_NUMBER);
            runtimeParametersData.add("Количество муравьев: "+problem.getParams().ANTS_NUMBER);
            runtimeParametersData.add("al: "+problem.getParams().al);
            runtimeParametersData.add("b: "+problem.getParams().b);
            runtimeParametersData.add("Q: "+problem.getParams().q);
            runtimeParametersData.add("Испарение феромона: "+problem.getParams().p);
            runtimeParametersData.add("Жизненный цикл: "+problem.getParams().lifeСycle);
            //


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
