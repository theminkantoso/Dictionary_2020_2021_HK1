package FXMLS;
import APIs.TextToSpeech;
import APIs.Trying_Different_Languages;
import code.DictionaryApplication;
import code.DictionaryManagement;
import code.Main;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Pair;
import sql.SQLConnector;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
/**
 * all scene path from this java files are compare to my local laptop
 */
public class Text extends Main {
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
    private Button add;

    @FXML
    private Button delete;

    @FXML
    private Button favorite;

    @FXML
    private Button modify;

    @FXML
    private Button back;

    //private static final DictionaryManagement dictionaryManagement = new DictionaryManagement("database.txt");
    Connection sql = SQLConnector.getConnection();

    /**
     * handle the input search and obtain result from dictionary.
     * @param event enter input
     */

    public void handleSearch(ActionEvent event) {
        result.clear();
        view.getItems().clear();
        input.textProperty().addListener((observable, oldValue, newValue) -> {
                result.clear();
                view.getItems().clear();
                //String in = input.getText().toLowerCase();
                List<String> res = dictionaryManagement.dictionarySearcher(newValue);
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

    /**
     * handle the append the result to the listview.
     * @return the user choice for text to speech handling
     */

    public String handleOutput() {
        String choose = (String) view.getSelectionModel().getSelectedItem();
        word.setText(choose);
        result.setText(dictionaryManagement.dictionaryLookup(choose));
        return choose;
    }

    /**
     * handle the speaking part.
     * @param event user click
     */

    public void speak(ActionEvent event) {
        String text = handleOutput();
        //new Trying_Different_Languages(text);
        TextToSpeech speech = new TextToSpeech(text);
    }

    /**
     * handle the user adding.
     * @param event userclick
     */

    public void add(ActionEvent event) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Thêm từ mới");
        dialog.setHeaderText("Thêm một từ mới");
        ButtonType loginButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField word = new TextField();
        TextField meaning = new TextField();


        grid.add(new Label("Từ mới:"), 0, 0);
        grid.add(word, 1, 0);
        grid.add(new Label("Nghĩa của từ:"), 0, 1);
        grid.add(meaning, 1, 1);
        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(word.getText(), meaning.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
        boolean done = false;
        if(word.getText().toString() != null && word.getText().toString().length() != 0 &&
                meaning.getText().toString() != null && meaning.getText().toString().length() != 0) {
            dictionaryManagement.insertFromApp(word.getText().toString().toLowerCase(),
                    meaning.getText().toString().toLowerCase());
            dictionaryManagement.dictionaryExportToFile("testdictionary.txt");
            done = true;
        }
        if(done) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Done");
            alert.setHeaderText("Success");
            alert.setContentText("Bạn đã thêm thành công");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Oops");
            alert.setHeaderText("Oh no");
            alert.setContentText("Có vẻ có lỗi gì rồi, bạn thử xem lại xem");
            alert.show();
        }
    }

    /**
     * handling the user deleting word.
     * @param event user click
     */

    public void delete(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("word");
        dialog.setTitle("Delete a word");
        dialog.setHeaderText("Xoá từ khỏi từ điển");
        dialog.setContentText("Hãy nhập từ cần xóa:");

        Optional<String> result = dialog.showAndWait();
        boolean done = false;
        if (result.isPresent()){
            dictionaryManagement.deleteFromApp(result.get());
            dictionaryManagement.dictionaryExportToFile("testdictionary.txt");
            done = true;
        }
        if(done) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Done");
            alert.setHeaderText("Success");
            alert.setContentText("Bạn đã xóa thành công");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Oops");
            alert.setHeaderText("Oh no");
            alert.setContentText("Có vẻ có lỗi gì rồi, bạn thử xem lại xem");
            alert.show();
        }
    }

    /**
     * handle the user edit.
     * @param event user click
     */

    public void inputEdit(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("word");
        dialog.setTitle("Edit a word");
        dialog.setHeaderText("Sửa từ điển");
        dialog.setContentText("Hãy nhập từ cần sửa:");

        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()) {
            Dialog<Pair<String, String>> diag = new Dialog<>();
            diag.setTitle("Sửa từ");
            diag.setHeaderText("Sửa từ hiện có");
            ButtonType loginButtonType = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
            diag.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField word = new TextField();
            TextField meaning = new TextField();


            grid.add(new Label("Nhập từ mới:"), 0, 0);
            grid.add(word, 1, 0);
            grid.add(new Label("Nhập nghĩa mới:"), 0, 1);
            grid.add(meaning, 1, 1);
            diag.getDialogPane().setContent(grid);
            diag.setResultConverter(dialogButton -> {
                if (dialogButton == loginButtonType) {
                    return new Pair<>(word.getText(), meaning.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> kq = diag.showAndWait();
            boolean done = false;
            if(word.getText().toString() != null && word.getText().toString().length() != 0 &&
                    meaning.getText().toString() != null && meaning.getText().toString().length() != 0) {
                dictionaryManagement.editFromApp(result.get().toLowerCase(), word.getText().toString().toLowerCase(),
                        meaning.getText().toString().toLowerCase());
                dictionaryManagement.dictionaryExportToFile("testdictionary.txt");
                done = true;
            }
            if(done) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Done");
                alert.setHeaderText("Success");
                alert.setContentText("Bạn đã sửa thành công");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Oops");
                alert.setHeaderText("Oh no");
                alert.setContentText("Có vẻ có lỗi gì rồi, bạn thử xem lại xem");
                alert.show();
            }
        }
    }

    /**
     * handle the add to favorite request from user to the sql database.
     * @param event user click
     * @throws SQLException error connection database
     */

    public void addFavorite(ActionEvent event) throws SQLException {
        if(!(word.getText().isBlank() || word.getText().isEmpty())) {
            String favWord = word.getText();
            Statement stmt = sql.createStatement();
            stmt.executeUpdate("INSERT IGNORE INTO favorite (word) VALUES ('" + favWord + "');");
        }
    }

    /**
     * handle the change scene.
     * @param event click change scene
     * @throws IOException error
     */

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
