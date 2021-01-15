package FXMLS;

import APIs.TextToSpeech;
import code.DictionaryApplication;
import code.DictionaryManagement;
import code.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import sql.SQLConnector;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class Both extends Main {
    @FXML
    private TextField input;

    @FXML
    private ListView view;

    @FXML
    private TextArea result;

    @FXML
    private Button speaker;

    @FXML
    private TextField word;

    @FXML
    private Button favorite;

    @FXML
    private Button back;


    DictionaryManagement dictionaryManagement1 = new DictionaryManagement(sql);
    public void handleSearch(ActionEvent event) {
        result.clear();
        view.getItems().clear();
        input.textProperty().addListener((observable, oldValue, newValue) -> {
            result.clear();
            view.getItems().clear();
            //String in = input.getText().toLowerCase();
            List<String> res = dictionaryManagement1.dictionarySearcher(newValue);
            String[] re = res.toArray(new String[0]);
            for(int i = 0; i < res.size(); i ++) {
                view.getItems().add(re[i]);
            }

            view.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            handleOutput();
        });
        /*
        String in = input.getText().toLowerCase();
        List<String> res = dictionaryManagement.dictionarySearcher(in);
        String[] re = res.toArray(new String[0]);
        for(int i = 0; i < res.size(); i ++) {
            view.getItems().add(re[i]);
        }

        view.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        handleOutput();
         */
    }

    public String handleOutput() {
        String choose = (String) view.getSelectionModel().getSelectedItem();
        word.setText(choose);
        result.setText(dictionaryManagement1.dictionaryLookup(choose));
        return choose;
    }


    public void speak(ActionEvent event) {
        String text = handleOutput();
        //new Trying_Different_Languages(text);
        TextToSpeech speech = new TextToSpeech(text);
    }


    public void addFavorite(ActionEvent event) throws SQLException {
        if(!(word.getText().isBlank() || word.getText().isEmpty())) {
            String favWord = word.getText();
            Statement stmt = sql.createStatement();
            stmt.executeUpdate("INSERT IGNORE INTO favorite (word) VALUES ('" + favWord + "');");
        }
    }

    public void changeScene(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(new File("").toURI().toURL()); //personal
        /*
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(""));
         */
        Parent loadview = loader.load();
        Scene scene = new Scene(loadview);
        stage.setScene(scene);
    }


}

