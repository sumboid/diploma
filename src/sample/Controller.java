package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Window;
import sample.model.algorithm.data.Parametry;

import java.io.File;

public class Controller {
    @FXML private Label actiontarget;
    @FXML private Label test;
    @FXML private CheckBox ggwcg;
    @FXML private TextField TEXTANTSNUMBER;
    @FXML private TextField TEXT_P;
    @FXML private TextField TEXT_Q;
    @FXML private TextField TEXT_AL;
    @FXML private TextField TEXT_B;
    @FXML private TextField TEXT_ENDWHILE;
    @FXML private TextField TEXT_lifeCycle;
    @FXML private LineChart realtimeChart;

    @FXML private Button problemSelection;
    final FileChooser fileChooser = new FileChooser();

    private XYChart.Series series;


    @FXML public void derg() {
        System.out.println(actiontarget.getText());
        actiontarget.setText("ТЫ ОХУЕЛ, СТЕПАН?");
        derg2();
    }

    @FXML public void handleChooseProblem(ActionEvent event) {
        final Window window = ((Node)event.getTarget()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(window);
        if (file != null) {
            System.out.println(file.getAbsolutePath());
        }
    }

    @FXML public void derg2(){
        ggwcg.setSelected(true);
    }
    @FXML public void checkRepExpAntsNumber(){
        if ((Integer.parseInt(TEXTANTSNUMBER.getText())<=0)&&(Integer.parseInt(TEXTANTSNUMBER.getText())>2147483647)&&
                (Integer.parseInt(TEXTANTSNUMBER.getText())%1!=0)){
            TEXTANTSNUMBER.setPromptText("Неверное значение");
            Popup popup = new Popup();
            popup.setAutoHide(true);
            popup.setX(1);
            popup.setY(1);
           // popup.show();

        }
    }
    @FXML public void textAСhange(){
        TEXT_B.setText(Double.toString(1 - Double.parseDouble(TEXT_AL.getText())));
    }
  /*  @FXML public void expModulDiv2ButtonOkOnAction(){
        Parametry.ANTS_NUMBER=Integer.parseInt(TEXTANTSNUMBER.getText());
        TEXTANTSNUMBER.setTooltip(new Tooltip(">0"));
        Parametry.al=Double.parseDouble(TEXT_AL.getText());
        Parametry.b=Double.parseDouble(TEXT_B.getText());
        Parametry.p=Double.parseDouble(TEXT_P.getText());
        Parametry.q=Double.parseDouble(TEXT_Q.getText());
        Parametry.endWhile=Integer.parseInt(TEXT_ENDWHILE.getText());
        Parametry.lifeСycle=Integer.parseInt(TEXT_lifeCycle.getText());
    }*/

    @FXML
    public void initialize() {
        series = new XYChart.Series();
        series.setName("Скилл Доброго");
        realtimeChart.getData().add(series);
    }
    public void clearChart() {
        series.getData().clear();

    }
    @FXML public void expModulDiv2ButtonOkOnAction(){
        /*Parametry.ANTS_NUMBER=Integer.parseInt(TEXTANTSNUMBER.getText());
        TEXTANTSNUMBER.setTooltip(new Tooltip(">0"));
        Parametry.al=Double.parseDouble(TEXT_AL.getText());
        Parametry.b=Double.parseDouble(TEXT_B.getText());
        Parametry.p=Double.parseDouble(TEXT_P.getText());
        Parametry.q=Double.parseDouble(TEXT_Q.getText());
        Parametry.endWhile=Integer.parseInt(TEXT_ENDWHILE.getText());
        Parametry.lifeСycle=Integer.parseInt(TEXT_lifeCycle.getText());
        Controller2.main();*/
    }

    public void setPoint(Double x, Double y) {
        series.getData().add(new XYChart.Data(x, y));
    }


}

