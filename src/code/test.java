package code;
import APIs.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        DictionaryManagement dictionaryManagement = new DictionaryManagement();
        dictionaryManagement.insertFromFile();
        dictionaryManagement.dictionaryEdit();
        dictionaryManagement.deleteFromApp("");
        dictionaryManagement.showAllWords();
    }
}

