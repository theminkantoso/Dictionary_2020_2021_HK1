package FXMLS;

import APIs.GoogleTranslatorAPI;
import APIs.Trying_Different_Languages;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.JSONException;
import javafx.event.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.UnknownHostException;
/**
 * all scene path from this java files are compare to my local laptop
 */

public class Ggtrans {
    @FXML
    private Button speak;

    @FXML
    private Button back;

    @FXML
    private TextField language;

    @FXML
    private TextField input;

    @FXML
    private TextArea output;

    private static String lang = "vi";

    /**
     * handle the input language code.
     * @param event enter in the code
     */

    public void language(ActionEvent event) {
        lang = language.getText().toLowerCase();

    }

    /**
     * handle the translate, obtain from google translate.
     * @param event
     */

    public void translate(ActionEvent event) {
        try{
            output.clear();
            String in = input.getText();
            String out = GoogleTranslatorAPI.translate("en", lang, in);
            output.setText(out);
        } catch(IOException | JSONException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Oops");
            alert.setHeaderText("No internet connection or invalid language code");
            alert.setContentText("Please check your language code or your internet connection");
            alert.show();
        }
    }

    /**
     * handle the speaking.
     * @param event click the button speak.
     */

    public void speech(ActionEvent event) {
        String text = input.getText();
        new Trying_Different_Languages(text);
    }

    /**
     * handle the searching click of browsing the web for language code.
     * @param event click
     * @throws IOException error connection
     */

    public void loadWeb(MouseEvent event) throws IOException {
        Desktop d = Desktop.getDesktop();
        d.browse(URI.create("https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes"));
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
