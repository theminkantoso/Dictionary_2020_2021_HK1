package code;

import java.util.List;

public abstract class DictionaryCommandline {

    /**
     * This method is used to print out all words.
     */

    public abstract void showAllWords();

    /**
     * This method is used to integrate both inserting and printing.
     */

    public abstract void dictionaryBasic();

    /**
     * This method contains three methods: insert, show all and search.
     */

    public abstract void dictionaryAdvanced();

    /**
     * Searching method.
     */

    public abstract void dictionarySearcher();

    /**
     * Output the list of words contains the input search.
     * @param input input search
     * @return results in the form of list
     */

    public abstract List<String> dictionarySearcher(String input);



}

