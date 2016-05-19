package sample.controller.report;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.Pair;
import sample.controller.report.internal.ReportView;
import sample.model.report.Report;
import sample.model.utils.FileWorker;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class ReportController {
    @FXML private ListView reportsList;
    @FXML private LineChart reportChart;
    @FXML private AnchorPane matrixPane;

    private ArrayList reportListCheckBoxChoosed=new ArrayList<>();
    private Map<String, Report> reportMap = new HashMap();
    private Map<String, XYChart.Series> seriesMap = new HashMap();
    private ObservableList<ReportView> reportTableData = FXCollections.observableArrayList();
    private Map<String, ReportView> reportTableMap = new HashMap<>();
    private TableView table;
    public static final ObservableList reportsListData = FXCollections.observableArrayList();

    @FXML public void handleChooseReport(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        final Window window = ((Node)event.getTarget()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(window);
        if (file != null) {
            String path = file.getAbsolutePath();
            Report report = (Report) FileWorker.readObjectFromFile(path);
            if (report != null) {

                String expParm=report.getProblem().getProblemName();

                if(!reportsListData.contains(expParm+": "+file.getAbsolutePath())) {
                    reportsListData.add(expParm + ": " + file.getAbsolutePath());
                    reportMap.put(expParm + ": " + file.getAbsolutePath(), report);
                    XYChart.Series series = this.makeReportSeries(report);
                    series.setName(report.getProblem().getProblemName());
                    seriesMap.put(expParm + ": " + file.getAbsolutePath(), series);

                    reportChart.getData().add(series);

                    ReportView reportView = new ReportView(report);
                    reportTableData.add(reportView);

                    table.setVisible(false);
                    table.setVisible(true);
                }
            }
        }
    }

    @FXML public void initialize() {
        renderMatrix();
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

                        reportTableData.remove(reportTableMap.get(item));
                        reportTableMap.remove(item);
                    });
                    contextMenu.getItems().addAll(deleteItem);

                    cell.textProperty().bind(cell.itemProperty());

                    cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                        if (isNowEmpty) {
                            cell.setContextMenu(null);
                        } else {
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
        table = new TableView();

        TableColumn<ReportView, String> nameCol = new TableColumn<>("Название");
        nameCol.setMinWidth(100);
        TableColumn<ReportView, Integer> sizeCol = new TableColumn<>("Количество городов");
        sizeCol.setMinWidth(200);
        TableColumn<ReportView, Integer> antSizeCol = new TableColumn<>("Количество муравьев");
        sizeCol.setMinWidth(200);
        TableColumn<ReportView, Integer> cityCol = new TableColumn<>("Стартовый город");
        cityCol.setMinWidth(150);
        TableColumn<ReportView, Double> alCol = new TableColumn<>("Вес следа феромона");
        alCol.setMinWidth(100);
        TableColumn<ReportView, Double> bCol = new TableColumn<>("Вес расстояния");
        bCol.setMinWidth(150);
        TableColumn<ReportView, Double> qCol = new TableColumn<>("Коэффициент Q");
        qCol.setMinWidth(150);
        TableColumn<ReportView, Double> pCol = new TableColumn<>("Коэффициент испарения");
        pCol.setMinWidth(150);
        TableColumn<ReportView, Integer> iterCol = new TableColumn<>("Время жизни колонии");
        iterCol.setMinWidth(100);
        TableColumn<ReportView, Integer> eliteAntsCol = new TableColumn<>("Число элитных муравьев");
        iterCol.setMinWidth(100);
        TableColumn<ReportView, Integer> lengthCol = new TableColumn<>("Вес кратчайшего пути");
        lengthCol.setMinWidth(150);
        TableColumn<ReportView, Integer> timeCol = new TableColumn<>("Время исполнения");
        timeCol.setMinWidth(150);
        TableColumn<ReportView, String> pathCol = new TableColumn<>("Кратчайший путь");
        pathCol.setMinWidth(400);

        nameCol.setCellFactory(param -> {
            TableCell<ReportView, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(cell.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });
        pathCol.setCellFactory(param -> {
            TableCell<ReportView, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(cell.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        antSizeCol.setCellValueFactory(new PropertyValueFactory<>("antsNumber"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("startNode"));
        alCol.setCellValueFactory(new PropertyValueFactory<>("al"));
        bCol.setCellValueFactory(new PropertyValueFactory<>("b"));
        pCol.setCellValueFactory(new PropertyValueFactory<>("p"));
        qCol.setCellValueFactory(new PropertyValueFactory<>("q"));
        iterCol.setCellValueFactory(new PropertyValueFactory<>("iterationsNumber"));
        lengthCol.setCellValueFactory(new PropertyValueFactory<>("length"));
        eliteAntsCol.setCellValueFactory(new PropertyValueFactory<>("eliteAntsNumber"));
        pathCol.setCellValueFactory(new PropertyValueFactory<>("path"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        table.getColumns().addAll(nameCol, timeCol, sizeCol, antSizeCol, eliteAntsCol, cityCol, alCol, bCol, pCol, qCol, iterCol, lengthCol, pathCol);

        AnchorPane.setTopAnchor(table, 0.0);
        AnchorPane.setBottomAnchor(table, 0.0);
        AnchorPane.setLeftAnchor(table, 0.0);
        AnchorPane.setRightAnchor(table, 0.0);

        table.setItems(reportTableData);

        matrixPane.getChildren().add(table);
    }

    @FXML public void handleSave(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("view/htmlEditor.fxml"));
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

            Parent root = fxmlLoader.load(getClass().getResource("../../view/htmlEditor.fxml").openStream());
            ReportEditorController controller = fxmlLoader.getController();
            Stage stage = new Stage();
            controller.setWindow(((Node)event.getTarget()).getScene().getWindow());
            controller.setParentController(this);
            controller.setStage(stage);

            stage.setTitle("Report editor");
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        final FileChooser fileChooser = new FileChooser();
//        final Window window = ((Node)event.getTarget()).getScene().getWindow();
//        File file = fileChooser.showSaveDialog(window);
//
//        if (file != null) {
//            String path = file.getAbsolutePath();
//            WritableImage snapShot = reportChart.snapshot(new SnapshotParameters(), null);
//            ImageIO.write(SwingFXUtils.fromFXImage(snapShot, null), "png", new File(path + "chart.png"));
//            Map<String, Report> reportRealMap = new HashMap<String, Report>();
//            Collection<Report> reportPairs = reportMap.values();
//
//           // HTMLbuilding.save(HTMLbuilding.htmlBuild(reportRealMap, file.getName() + "chart.png"), path);
//        }
    }

    public void saveReport(Window window, String html) throws IOException {
        System.out.println(html);
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(window);

        if (file != null) {
            String path = file.getAbsolutePath();
            WritableImage snapshot = reportChart.snapshot(new SnapshotParameters(), null);
            BufferedImage image = SwingFXUtils.fromFXImage(snapshot, null);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            OutputStream b64 = Base64.getEncoder().wrap(os);
            ImageIO.write(image, "png", b64);
            String resultImage = os.toString("UTF-8");

            HTMLbuilding.save(HTMLbuilding.htmlBuild(reportTableData, resultImage, html), path);
        }
    }
}
