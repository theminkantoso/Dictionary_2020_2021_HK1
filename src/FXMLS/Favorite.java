package FXMLS;

import code.DictionaryApplication;

import code.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sql.SQLConnector;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * all scene path from this java files are compare to my local laptop
 */

public class Favorite extends Main implements Initializable {
    @FXML
    private Button delete;

    @FXML
    private ListView favorite;

    @FXML
    private TextField search;

    @FXML
    private Button back;

    //Connection sql = SQLConnector.getConnection();

    /**
     * handle the search favorite list.
     * @param event enter user input
     * @throws SQLException error sql connection
     */

    public void searchFavorite(ActionEvent event) throws SQLException {
        favorite.getItems().clear();
        String in = search.getText();
        Statement stmt = sql.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM favorite WHERE word LIKE '" + in + "%';");
        while(rs.next()) {
            String res = rs.getString(1);
            favorite.getItems().add(res);
        }
        favorite.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /**
     * show all favourite words.
     * @throws SQLException error sql connection
     */

    public void showAll() throws SQLException {
        if(search.getText().isEmpty() || search.getText().isBlank()) {
            favorite.getItems().clear();
            Statement stmt = sql.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM favorite");
            while(rs.next()) {
                String res = rs.getString(1);
                favorite.getItems().add(res);
            }
        }
    }

    /**
     * delete from favorite word.
     * @param event user click
     * @throws SQLException error sql connection
     */

    public void delete(ActionEvent event) throws SQLException {
        String choose = (String) favorite.getSelectionModel().getSelectedItem();
        Statement stmt = sql.createStatement();
        stmt.executeUpdate("DELETE FROM favorite where word = '" + choose + "';");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Done");
        alert.setHeaderText("Hoàn tất");
        alert.setContentText("Chúng tôi đã thao tác xong");
        alert.show();
        showAll();
    }

    /**
     * init scene.
     * @param url input
     * @param resourceBundle input
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
