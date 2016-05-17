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
import javafx.scene.image.WritableImage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Pair;
import sample.model.report.Report;
import sample.model.utils.FileWorker;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ReportController {
    @FXML private ListView reportsList;
    @FXML private LineChart reportChart;
    @FXML private WebView reportWebView;

    private WebEngine engine;

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
                //GridPane gridPane = new GridPane();
                //ColumnConstraints column1 = new ColumnConstraints(100, 100, Double.MAX_VALUE);
               // column1.setHgrow(Priority.NEVER);
               // gridPane.getColumnConstraints().addAll(column1); // first column gets any extra width

                //CheckBox checkbox = new CheckBox();
              //  checkbox.setSelected(true);
               // Label reportName = new Label(file.getAbsolutePath());

                //gridPane.add(checkbox, 1, 0);
               // gridPane.add(reportName, 0, 0);
                String expParm=report.getProblem().getProblemName();
                reportsListData.add(expParm+": "+file.getAbsolutePath());
                reportMap.put(expParm+": "+file.getAbsolutePath(), new Pair(file.getName(), report));
                XYChart.Series series = this.makeReportSeries(report);
                series.setName(file.getName());
                seriesMap.put(expParm+": "+file.getAbsolutePath(), series);

                reportChart.getData().add(series);

                this.updateWebContent();

             /*   checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov,
                                        Boolean old_val, Boolean new_val) {
                        System.out.println(file.getAbsolutePath());
                        System.out.println(new_val);
                        if (new_val == false) {
                            reportListCheckBoxChoosed.add(gridPane);
                        }
                        if (new_val == true) {
                            reportListCheckBoxChoosed.remove(gridPane);
                        }
                        System.out.println(reportListCheckBoxChoosed.get(0));
                        // reportName.setText("2312");
                    }
                });*/
            }
        }
    }

    @FXML public void initialize() {
        engine = reportWebView.getEngine();
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

    private void updateWebContent() {
        Map<String, Report> reportRealMap = new HashMap();
        Collection<Pair<String, Report>> reportPairs = reportMap.values();

        for (Pair<String, Report> pair : reportPairs) {
            reportRealMap.put(pair.getKey(), pair.getValue());
        }

        engine.loadContent(HTMLbuilding.htmlBuild(reportRealMap, ""));
    }

    @FXML public void handleSave(ActionEvent event) throws IOException {
        final FileChooser fileChooser = new FileChooser();
        final Window window = ((Node)event.getTarget()).getScene().getWindow();
        File file = fileChooser.showSaveDialog(window);

        if (file != null) {
            String path = file.getAbsolutePath();
            WritableImage snapShot = reportChart.snapshot(new SnapshotParameters(), null);
            ImageIO.write(SwingFXUtils.fromFXImage(snapShot, null), "png", new File(path + "chart.png"));
            Map<String, Report> reportRealMap = new HashMap();
            Collection<Pair<String, Report>> reportPairs = reportMap.values();

            for (Pair<String, Report> pair : reportPairs) {
                reportRealMap.put(pair.getKey(), pair.getValue());
            }
            HTMLbuilding.save(HTMLbuilding.htmlBuild(reportRealMap, file.getName() + "chart.png"), path);
        }
    }
}
