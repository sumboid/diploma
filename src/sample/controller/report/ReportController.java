package sample.controller.report;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Pair;
import sample.controller.report.internal.ReportView;
import sample.model.report.Report;
import sample.model.utils.FileWorker;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ReportController {
    @FXML private ListView reportsList;
    @FXML private LineChart reportChart;
    @FXML private AnchorPane matrixPane;

    private ArrayList reportListCheckBoxChoosed=new ArrayList<>();
    private Map<String, Pair<String, Report>> reportMap = new HashMap();
    private Map<String, XYChart.Series> seriesMap = new HashMap();

    public static final ObservableList reportsListData = FXCollections.observableArrayList();

    @FXML public void reportDelPress(){
        for(Object pane: reportListCheckBoxChoosed) {
            reportChart.getData().removeAll(seriesMap.get(pane));
            seriesMap.remove(pane);
            reportMap.remove(pane);
        }
        this.updateWebContent();
        reportsListData.removeAll(reportListCheckBoxChoosed);
    }

    @FXML public void handleChooseReport(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        final Window window = ((Node)event.getTarget()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(window);
        if (file != null) {
            String path = file.getAbsolutePath();
            Report report = (Report) FileWorker.readObjectFromFile(path);
            if (report != null) {
                String expParm=report.getProblem().getProblemName();
                reportsListData.add(expParm+": "+file.getAbsolutePath());
                reportMap.put(expParm+": "+file.getAbsolutePath(), new Pair(file.getName(), report));
                XYChart.Series series = this.makeReportSeries(report);
                series.setName(file.getName());
                seriesMap.put(expParm+": "+file.getAbsolutePath(), series);

                reportChart.getData().add(series);

                this.updateWebContent();
            }
        }
    }

    @FXML public void initialize() {
        reportsList.setItems(reportsListData);
        reportsList.setCellFactory(lv ->{
                    ListCell<String> cell = new ListCell<>();
                    ContextMenu  contextMenu = new ContextMenu();
                    MenuItem editItem = new MenuItem();
                    MenuItem deleteItem = new MenuItem();
                    deleteItem.textProperty().bind(Bindings.format("Delete \"%s\"", cell.itemProperty()));
                    deleteItem.setOnAction(event -> {
                        String item = cell.getItem();
                        reportsList.getItems().remove(item);
                        reportChart.getData().removeAll(seriesMap.get(item));
                        seriesMap.remove(item);
                        reportMap.remove(item);
                        reportsListData.removeAll(item);
                        this.updateWebContent();
                    });
                    contextMenu.getItems().addAll(deleteItem);

                    cell.textProperty().bind(cell.itemProperty());

                    cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                        if (isNowEmpty) {
                            cell.setContextMenu(null);
                        } else {
                            //System.out.println("removeALALAL");
                            cell.setContextMenu(contextMenu);
                        }
                    });
                    return cell ;
                }
        );
    }

    @FXML private XYChart.Series makeReportSeries(Report report) {
        XYChart.Series series = new XYChart.Series();
        List<Double> points = report.getGrafik();
        for(int i = 0; i < points.size(); ++i) {
            series.getData().add(new XYChart.Data(i, points.get(i)));
        }
        return series;
    }

    private void renderMatrix() {
        matrixPane.getChildren().clear();
        TableView table = new TableView();

        TableColumn<ReportView, String> nameCol = new TableColumn<>("Название");
        TableColumn<ReportView, Integer> sizeCol = new TableColumn<>("Количество городов");
        TableColumn<ReportView, Integer> cityCol = new TableColumn<>("Стартовый город");
        TableColumn<ReportView, Double> alCol = new TableColumn<>("Вес следа феромона");
        TableColumn<ReportView, Double> bCol = new TableColumn<>("Вес расстояния");
        TableColumn<ReportView, Double> qCol = new TableColumn<>("Коэффициент Q");
        TableColumn<ReportView, Double> pCol = new TableColumn<>("Коэффициент испарения");
        TableColumn<ReportView, Integer> iterCol = new TableColumn<>("Время жизни колонии");
        TableColumn<ReportView, Integer> lengthCol = new TableColumn<>("Кратчайший путь");

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("startNode"));
        alCol.setCellValueFactory(new PropertyValueFactory<>("al"));
        bCol.setCellValueFactory(new PropertyValueFactory<>("b"));
        pCol.setCellValueFactory(new PropertyValueFactory<>("p"));
        qCol.setCellValueFactory(new PropertyValueFactory<>("q"));
        iterCol.setCellValueFactory(new PropertyValueFactory<>("iterationNumber"));
        lengthCol.setCellValueFactory(new PropertyValueFactory<>("length"));

        table.getColumns().addAll(nameCol, sizeCol, cityCol, alCol, bCol, pCol, qCol, iterCol, lengthCol);

        AnchorPane.setTopAnchor(table, 0.0);
        AnchorPane.setBottomAnchor(table, 0.0);
        AnchorPane.setLeftAnchor(table, 0.0);
        AnchorPane.setRightAnchor(table, 0.0);

        matrixPane.getChildren().add(table);
    }

    private void updateWebContent() {
        Map<String, Report> reportRealMap = new HashMap<String, Report>();
        Collection<Pair<String, Report>> reportPairs = reportMap.values();

        for (Pair<String, Report> pair : reportPairs) {
            reportRealMap.put(pair.getKey(), pair.getValue());
        }
        renderMatrix();
    }

    @FXML public void handleSave(ActionEvent event) throws IOException {
        final FileChooser fileChooser = new FileChooser();
        final Window window = ((Node)event.getTarget()).getScene().getWindow();
        File file = fileChooser.showSaveDialog(window);

        if (file != null) {
            String path = file.getAbsolutePath();
            WritableImage snapShot = reportChart.snapshot(new SnapshotParameters(), null);
            ImageIO.write(SwingFXUtils.fromFXImage(snapShot, null), "png", new File(path + "chart.png"));
            Map<String, Report> reportRealMap = new HashMap<String, Report>();
            Collection<Pair<String, Report>> reportPairs = reportMap.values();

            for (Pair<String, Report> pair : reportPairs) {
                reportRealMap.put(pair.getKey(), pair.getValue());
            }
            HTMLbuilding.save(HTMLbuilding.htmlBuild(reportRealMap, file.getName() + "chart.png"), path);
        }
    }
}
