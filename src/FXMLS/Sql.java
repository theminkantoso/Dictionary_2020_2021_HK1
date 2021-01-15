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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
/**
 * all scene path from this java files are compare to my local laptop
 */
public class Sql extends Main {
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

    //Connection sql = SQLConnector.getConnection();

    /**
     * handle the input search, convert to sql query.
     * @param event user enter input
     * @throws SQLException error database connection.
     */

    public void handleSearch(ActionEvent event) throws SQLException {
        result.clear();
        view.getItems().clear();
        String in = input.getText().toLowerCase();
        Statement stmt = sql.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM dict WHERE word LIKE '" + in + "%'");
        while (rs.next()) {
            view.getItems().add(rs.getString(2));
        }
        view.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        handleOutput();
    }

    /**
     * append the query result to the list view.
     * @return user word choice for text to speech function.
     * @throws SQLException error database connection
     */

    public String handleOutput() throws SQLException {
        String choose = (String) view.getSelectionModel().getSelectedItem();
        word.setText(choose);
        Statement stmt = sql.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM dict WHERE word LIKE '" + choose + "'");
        while (rs.next()) {
            String[] handle = rs.getString(3).split("<br />");
            String out = "";
            for(String part: handle) {
                out = out + part + "\n";
            }
            result.setText(out);
        }
        return choose;
    }

    /**
     * text to speech.
     * @param event user click
     * @throws SQLException error database connection
     */

    public void speak(ActionEvent event) throws SQLException {
        String text = handleOutput();
        //new Trying_Different_Languages(text);
        TextToSpeech speech = new TextToSpeech(text);
    }

    /**
     * add to favorite request from user.
     * @param event user click.
     * @throws SQLException error sql connection
     */

    public void addFavorite(ActionEvent event) throws SQLException {
        boolean done = false;
        if(!(word.getText().isBlank() || word.getText().isEmpty())) {
            String favWord = word.getText();
            Statement stmt = sql.createStatement();
            stmt.executeUpdate("INSERT IGNORE INTO favorite (word) VALUES ('" + favWord + "');");
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
