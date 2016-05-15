package sample.controller.library;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * Created by roman on 16.05.16.
 */
public class LibraryController {
    @FXML WebView libWebView;

    @FXML public void initialize() {
        WebEngine webEngine= libWebView.getEngine();
        webEngine.load("http://youtube.com");
    }
}
