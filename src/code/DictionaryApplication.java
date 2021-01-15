package code;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import sql.SQLConnector;

import java.io.File;
import java.sql.Connection;

public class DictionaryApplication extends Application {
    public static final DictionaryManagement dictionaryManagement = new DictionaryManagement("database.txt");
    public Connection sql = SQLConnector.getConnection();
    //public DictionaryManagement dictionaryManagement1 = new DictionaryManagement(sql);
    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            FXMLLoader loader = new FXMLLoader(new File("").toURI().toURL()); //personal
            //Parent root = FXMLLoader.load(getClass().getResource("Hello.fxml);

            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Dictionary App");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
