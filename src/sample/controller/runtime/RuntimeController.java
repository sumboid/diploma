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
import javafx.scene.control.*;
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
import sample.model.report.Report;
import sample.model.utils.FileWorker;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RuntimeController {
    @FXML private LineChart realtimeChart;
    @FXML private GridPane exportedPane;
    @FXML private Pane problemMatrixPane;
    @FXML private Button startButton;
    @FXML private Button reportSaver;
    @FXML private Button problemSelection;
    @FXML private Label problemName;

    @FXML private TextField TEXTANTSNUMBER;
    @FXML private TextField TEXT_P;
    @FXML private TextField TEXT_Q;
    @FXML private TextField TEXT_AL;
    @FXML private TextField TEXT_B;
    @FXML private TextField TEXT_ENDWHILE;
    @FXML private TextField TEXT_lifeCycle;
    @FXML private TextField ELITE_SQUAD;

    @FXML private Label time;
    @FXML private Label path;
    @FXML private Label pathLength;

    @FXML private ListView runtimeParameters;

    private Model model;
    private Problem problem;
    private Report report;

    private XYChart.Series series;

    private Parameters createParams() {
        Parameters params = new Parameters();
        params.ANTS_NUMBER=Integer.parseInt(TEXTANTSNUMBER.getText());
        params.al=Double.parseDouble(TEXT_AL.getText());
        params.b=Double.parseDouble(TEXT_B.getText());
        params.p=Double.parseDouble(TEXT_P.getText());
        params.q=Double.parseDouble(TEXT_Q.getText());
        params.endWhile=Integer.parseInt(TEXT_ENDWHILE.getText());
        params.lifeСycle=Integer.parseInt(TEXT_lifeCycle.getText());
        params.eliteNumberAnt = Integer.parseInt(ELITE_SQUAD.getText());
        return params;
    }

    @FXML private void setParams(Parameters params) {
        TEXTANTSNUMBER.setText(Integer.toString(params.ANTS_NUMBER));
        TEXT_AL.setText(Double.toString(params.al));
        TEXT_B.setText(Double.toString(params.b));
        TEXT_P.setText(Double.toString(params.p));
        TEXT_Q.setText(Double.toString(params.q));
        TEXT_ENDWHILE.setText(Integer.toString(params.endWhile));
        TEXT_lifeCycle.setText(Integer.toString(params.lifeСycle));
        ELITE_SQUAD.setText(Integer.toString(params.eliteNumberAnt));
    }

    @FXML public void handleChooseProblem(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        final Window window = ((Node)event.getTarget()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(window);
        if (file != null) {
            String path = file.getAbsolutePath();
            String fileName=file.getName();
            this.problem = (Problem) FileWorker.readObjectFromFile(path);
            problemName.setText(problem.getProblemName());
            startButton.setDisable(false);

            this.time.setText("0");
            this.path.setText("Неизвестно");
            this.pathLength.setText("Неизвестно");
        }
    }

    public void endExecution(Report report) {
        System.out.println("END OF EXECUTION");
        startButton.setDisable(false);
        reportSaver.setDisable(false);
        problemSelection.setDisable(false);
        this.report = report;
    }

    @FXML public void handleStart() {
        clearChart();
        startButton.setDisable(true);
        reportSaver.setDisable(true);
        problemSelection.setDisable(true);
        model.start(problem, createParams());
    }

    @FXML public void handleSaveReport(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        final Window window = ((Node)event.getTarget()).getScene().getWindow();
        File file = fileChooser.showSaveDialog(window);
        if (file != null) {
            String path = file.getAbsolutePath();
            FileWorker.writeObjectToFile(report, path);
        }
    }


    public void clearChart() {
        series.getData().clear();
    }

    public void setPoint(int x, Double y) {
        series.getData().add(new XYChart.Data(x, y));
    }

    @FXML
    public void initialize() {
        series = new XYChart.Series();
        series.setName("Минимальная длина пути");
        realtimeChart.getData().add(series);
        setParams(new Parameters());
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setPath(ArrayList<Integer> rpath) {
        Integer pathSize = rpath.size();
        Integer lastElement = rpath.get(pathSize - 1);
        if (lastElement != 0) {
            rpath.set(pathSize - 1, rpath.get(pathSize - 2));
            rpath.set(pathSize - 2, lastElement);
        }

        String sp = rpath.stream().map(x -> Integer.toString(x)).collect(Collectors.joining(" -> "));
        if (!Objects.equals(sp, this.path.getText())) {
            this.path.setText(sp);
        }
    }

    public void setPathLength(Double pathLength) {
        this.pathLength.setText(Double.toString(pathLength));
    }

    public void setTime(Double time) {
        this.time.setText(Double.toString(time));
    }
}
