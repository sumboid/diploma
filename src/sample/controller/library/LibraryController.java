package sample.controller.library;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Pair;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by roman on 16.05.16.
 */
public class LibraryController implements Initializable {
    @FXML WebView libWebView;
    @FXML
    ListView<String> libraryItems;

    List<Pair<String, String>> libraryHTMLMapping = new ArrayList<>();

    private void generateMapping() {
        libraryHTMLMapping.add(new Pair<>("Роевые алгоритмы оптимизации", "../../assets/1.html"));
        libraryHTMLMapping.add(new Pair<>("Метод роя частиц", "../../assets/2.html"));
        libraryHTMLMapping.add(new Pair<>("Муравьиные алгоритмы", "../../assets/3.html"));
        libraryHTMLMapping.add(new Pair<>("Пчелиный алгоритм", "../../assets/4.html"));
        libraryHTMLMapping.add(new Pair<>("История муравьиных алгоритмов", "../../assets/5.html"));
        libraryHTMLMapping.add(new Pair<>("Задача коммивояжера", "../../assets/6.html"));
        libraryHTMLMapping.add(new Pair<>("Основные шаги муравьиных алгоритмов", "../../assets/7.html"));
        libraryHTMLMapping.add(new Pair<>("Применение муравьиных алгоритмов к задачекоммивояжера", "../../assets/8.html"));
        libraryHTMLMapping.add(new Pair<>("Сценарии обучения", "../../assets/9.html"));
        libraryHTMLMapping.add(new Pair<>("Индуктивное обучение", "../../assets/10.html"));
        libraryHTMLMapping.add(new Pair<>("Обучающие тренажеры", "../../assets/11.html"));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        WebEngine webEngine= libWebView.getEngine();
        webEngine.load(getClass().getResource("../../assets/0.html").toExternalForm());

        generateMapping();
        List<String> values = libraryHTMLMapping.stream().map(Pair::getKey).collect(Collectors.toList());

        libraryItems.setItems(FXCollections.observableList(values));
        libraryItems.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) { //handle doubleclick
                String currentItemSelected = libraryItems.getSelectionModel()
                        .getSelectedItem();
                String html = libraryHTMLMapping.stream().filter(x -> Objects.equals(x.getKey(), currentItemSelected)).findFirst().get().getValue();
                webEngine.load(getClass().getResource(html).toExternalForm());
            }
        });
    }
}
