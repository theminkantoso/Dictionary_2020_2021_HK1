package code;

import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    private List<Word> words = new ArrayList<Word>();

    public Dictionary() {

    }

    /**
     * This method is used to initialize a dictionary via other dictionary.
     * @param dictionary This is the input
     */

    public Dictionary(Dictionary dictionary) {
        this.words = dictionary.getWords();
    }

    /**
     * This method is used to get the dictionary word count.
     * @return  wordcount
     */

    public int getWordCount() {
        return words.size();
    }

    /**
     * This method is used to get the word list of the dictionary.
     * @return the word list.
     */

    public List<Word> getWords() {
        return words;
    }

    /**
     * This method is used to get the explaination of a word given its index.
     * @param index This is the index.
     * @return the explaination of the respecitve word
     */

    public String getIndividualWords_explain(int index) {
        return words.get(index).getWord_explain();
    }

    /**
     * This method is used to get the target of a word given its index.
     * @param index This is the index.
     * @return the target of the respecitve word
     */

    public String getIndividualWords_target(int index) {
        return words.get(index).getWord_target();
    }

    /**
     * This method is used to set the explaination of a word given its index.
     * @param index This is the index.
     * @param explain explaination to set
     */

    public void setIndividualWords_explain(int index, String explain) {
        words.get(index).setWord_explain(explain);
    }

    /**
     * This method is used to set the target of a word given its index.
     * @param index This is the index.
     * @param target to set
     */

    public void setIndividualWords_target(int index, String target) {
        words.get(index).setWord_target(target);
    }

    /**
     * This method is used to add a new word.
     * @param word This is the new word.
     */

    public void addWord(Word word) {
        words.add(word);
    }

    /**
     * This method is used to delete word.
     * @param index This is the index of the word.
     */

    public void deleteWord(int index) {
        words.remove(index);
    }
}