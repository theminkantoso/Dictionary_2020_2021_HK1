package code;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Dictionary.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;

public class DictionaryManagement extends DictionaryCommandline{
    private Dictionary dictionary = new Dictionary();

    public DictionaryManagement() {

    }

    /**
     * Constructor load the data from txt file.
     * @param path file path
     */

    public DictionaryManagement(String path) {
        insertFromFile(path);
    }

    /**
     * Constructor loading data from sql database
     * @param con sql connector
     */

    public DictionaryManagement(Connection con) {
        try {
            insertFromSQL(con);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * This method is used to sychronize the current dictionary with the input dictionary.
     * @param dictionary This is the input dictionary.
     */

    public DictionaryManagement(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * This method is also used to sychronize the current dictionary with the input dictionary.
     * @param dictionary This is the input dictionary.
     */

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * This method is used to check whether if the input word existed in the dictionary.
     * @param input is the input word.
     * @return true if it is not in the dictionary, false if it is already in.
     */

    private int checkHave(String input) {
        int check = -1;
        int i;
        for(i = 0; i < dictionary.getWordCount(); i ++) {
            if((dictionary.getIndividualWords_target(i)).equals(input)) {
                check = i;
                break;
            }
        }
        return check;
    }

    /**
     * This method is used to check whether if the input word already existed in the dictionary.
     * @param input is the input word.
     * @return true if it is not in the dictionary, false if it is already in.
     */

    private boolean checkAlreadyHave(Word input) {
        boolean check = true;
        int i;
        for(i = 0; i < dictionary.getWordCount(); i ++) {
            if(((dictionary.getIndividualWords_target(i)).equals(input.getWord_target()))
            && ((dictionary.getIndividualWords_explain(i)).equals(input.getWord_explain()))) {
                check = false;
                break;
            }
        }
        return check;
    }

    /**
     * This method is used to insert new word.
     */

    public void insertFromCommandline() {
        Scanner sc = new Scanner(System.in);
        int count;
        System.out.println("Nhap so tu can them:");
        count = sc.nextInt();
        sc.nextLine();
        Word temp;
        int i;
        for(i = 0; i < count; i++) {
            System.out.println("Nhap lan luot 1. Tu Tieng Anh 2. Nghia Tieng Viet:");
            String str1 = sc.nextLine();
            String str2 = sc.nextLine();
            temp = new Word(str1, str2);
            if(checkAlreadyHave(temp)) {
                dictionary.addWord(temp);
            }
        }
    }

    /**
     * This method is used to insert new word from app.
     * @param word input word
     * @param meaning input meaning
     */

    public void insertFromApp(String word, String meaning) {

        Word temp;
        temp = new Word(word, meaning);
        if(checkAlreadyHave(temp)) {
            dictionary.addWord(temp);
        }
    }

    /**
     * This method is used to delete word from app.
     * @param word input word
     */

    public void deleteFromApp(String word) {
        if(checkHave(word) != -1) {
            dictionary.deleteWord(checkHave(word));
        }
    }

    /**
     * Edit already existed word.
     * @param word input word
     * @param newWord input newWord
     * @param meaning input new meaning
     */

    public void editFromApp(String word, String newWord, String meaning) {
        if(checkHave(word) != -1) {
            int index = checkHave(word);
            dictionary.setIndividualWords_target(index, newWord);
            dictionary.setIndividualWords_explain(index, meaning);
        }
    }

    /**
     * This method is used to print out all words.
     */

    @Override

    public void showAllWords() {
        int i;
        System.out.format("%-4s|%-20s|%-20s\n", "No", "English", "Vietnamese");
        for (i = 0; i < dictionary.getWordCount(); i ++) {
            System.out.format("%-4d|%-20s|%-20s\n", i + 1, dictionary.getIndividualWords_target(i),
                    dictionary.getIndividualWords_explain(i));
        }
    }

    /**
     * This method is used to integrate both inserting and printing.
     */

    @Override

    public void dictionaryBasic() {
        insertFromCommandline();
        showAllWords();
    }

    /**
     * This method contains three methods.
     */

    @Override
    public void dictionaryAdvanced() {
        insertFromFile();
        showAllWords();
        dictionaryLookup();
    }

    /**
     * Searching method.
     */

    @Override
    public void dictionarySearcher() {
        String search = "";
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhap can tra");
        search = sc.nextLine();
        String s1;
        String s2;
        int i;
        List<Integer> result = new ArrayList<Integer>();
        for(i = 0; i < dictionary.getWordCount(); i ++) {
            if((dictionary.getIndividualWords_target(i)).length() >= (search.length())) {
                s1 = dictionary.getIndividualWords_target(i);
                s2 = s1.substring(0, search.length());
                if(s2.equals(search)) {
                    result.add(Integer.valueOf(i));
                }
            }
        }
        if(result.size() > 0) {
            for(i = 0; i < result.size(); i ++) {
                System.out.format("%-20s|%-20s\n", dictionary.getIndividualWords_target(result.get(i).intValue()),
                        dictionary.getIndividualWords_explain(result.get(i).intValue()));
            }
        }
    }

    /**
     * Handle the input search.
     * @param input input search
     * @return results contain the input string under list string form
     */

    @Override
    public List<String> dictionarySearcher(String input) {
        List<String> search = new ArrayList<String>();
        //Scanner sc = new Scanner(System.in);
        //System.out.println("Nhap can tra");
        // search = sc.nextLine();
        String s1;
        String s2;
        int i;
        input = input.toLowerCase();
        //List<Integer> result = new ArrayList<Integer>();
        for(i = 0; i < dictionary.getWordCount(); i ++) {
            if((dictionary.getIndividualWords_target(i)).length() >= (input.length())) {
                s1 = dictionary.getIndividualWords_target(i);
                s2 = s1.substring(0, input.length());
                if(s2.equals(input)) {
                    search.add(dictionary.getIndividualWords_target(i));
                }
            }
        }
        return search;
    }

    /**
     * This method is used to synchronize the current dictionary with the to the output dictionary.
     * @return the output to synchronize for further modification.
     */

    public Dictionary outputDictionary() {
        return dictionary;
    }

    /**
     * This method is used to add words to the dictionary via text file.
     */

    public void insertFromFile() {
        try {
            File inFile = new File("dictionarytest.txt");
            Scanner input = new Scanner(inFile);
            Word temp;
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] data = line.split("\t");
                temp = new Word(data[0], data[1]);
                if(checkAlreadyHave(temp)) {
                    dictionary.addWord(temp);
                }
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error open file");
            e.printStackTrace();
        }
    }

    /**
     * insert data from txt file
     * @param path the given path of the txt file
     */

    public void insertFromFile(String path) {
        try {
            File inFile = new File(path);
            Scanner input = new Scanner(inFile);
            Word temp;
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] data = line.split("\t");
                temp = new Word(data[0], data[1]);
                if(checkAlreadyHave(temp)) {
                    dictionary.addWord(temp);
                }
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error open file");
            e.printStackTrace();
        }
    }

    /**
     * This method is used to search for words.
     */

    public void dictionaryLookup() {
        String search = "";
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhap tu can tra:");
        search = sc.nextLine();
        boolean check = false;
        int i;
        for (i = 0; i < dictionary.getWordCount(); i++) {
            if((dictionary.getIndividualWords_target(i)).equals(search)) {
                check = true;
                System.out.format("Found: %-20s|%-20s\n", dictionary.getIndividualWords_target(i),
                        dictionary.getIndividualWords_explain(i));
                break;
            }
        }
        if (!check) {
            System.out.println("Word not found");
        }
    }

    public String dictionaryLookup(String input) {
        String out = "";

        boolean check = false;
        int i;
        for (i = 0; i < dictionary.getWordCount(); i++) {
            if((dictionary.getIndividualWords_target(i)).equals(input)) {
                check = true;
                out = out + dictionary.getIndividualWords_explain(i) + "\n";
                break;
            }
        }
        if (!check) {
            out = "Word not found\n";
        }
        return out;
    }

    /**
     * Dictionary editor.
     */

    public void dictionaryEdit() {
        String command = "";
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhap THEM, SUA hoac XOA:");
        command = sc.nextLine();
        String s1;
        String s2;
        String s3;
        switch(command) {
            case "THEM":
                System.out.println("Nhap tu moi:");
                s1 = sc.nextLine();
                System.out.println("Nhap nghia tu moi:");
                s2 = sc.nextLine();
                Word temp = new Word(s1, s2);
                if(checkAlreadyHave(temp)) {
                    dictionary.addWord(temp);
                }
                break;
            case "SUA":
                System.out.println("Nhap tu can sua:");
                s1 = sc.nextLine();
                if(checkHave(s1) != -1) {
                    System.out.println("Nhap tu moi:");
                    s2 = sc.nextLine();
                    System.out.println("Nhap nghia moi:");
                    s3 = sc.nextLine();
                    int index = checkHave(s1);
                    dictionary.setIndividualWords_target(index, s2);
                    dictionary.setIndividualWords_explain(index, s3);
                } else {
                    System.out.println("Khong ton tai");
                }
                break;
            case "XOA":
                System.out.println("Nhap tu can xoa:");
                s1 = sc.nextLine();
                if(checkHave(s1) != -1) {
                    dictionary.deleteWord(checkHave(s1));
                } else {
                    System.out.println("Khong ton tai");
                }
                break;
            default:
                System.out.println("Invalid operator");
        }
    }

    /**
     * Dictionary export to file.
     */

    public void dictionaryExportToFile() {
        try {
            FileWriter fw = new FileWriter("testdictionary.txt");
            int i;
            for (i = 0; i < dictionary.getWordCount(); i ++) {
                fw.write(dictionary.getIndividualWords_target(i) + "\t" +
                        dictionary.getIndividualWords_explain(i) + "\n");
            }
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Export to a file given path.
     * @param path file path
     */

    public void dictionaryExportToFile(String path) {
        try {
            FileWriter fw = new FileWriter(path);
            int i;
            for (i = 0; i < dictionary.getWordCount(); i ++) {
                fw.write(dictionary.getIndividualWords_target(i) + "\t" +
                        dictionary.getIndividualWords_explain(i) + "\n");
            }
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * insert data from SQL to dictionary.
     * @param con sql connector
     * @throws SQLException error connection
     */

    public void insertFromSQL(Connection con) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT word, detail FROM dict");
        Word temp;
        while (rs.next()) {
            String word = rs.getString(1);
            String[] meaning = rs.getString(2).split("<br />");
            String meaningout = "";
            for(String part: meaning) {
                meaningout = meaningout + part + "\n";
            }
            temp = new Word(word, meaningout);
            if(checkAlreadyHave(temp)) {
                dictionary.addWord(temp);
            }
        }
    }
}
