package dsacoursework2;

import java.util.ArrayList;
import java.util.TreeMap;

public class AutoComplete {

    public static void main(String[] args) throws Exception {
        /*1. form a dictionary file of words and counts from the file lotr.csv (if you cannot complete
            part 1, you may use the file gollum.csv instead without penalty).*/
        String pwd = System.getProperty("user.dir");
        ArrayList<String> in = DictionaryMaker.readWordsFromCSV(pwd + "/lotr.csv");
        TreeMap<String, Integer> dictionaryTree = DictionaryMaker.formDictionary(in);
        System.out.println(dictionaryTree);
        /*2. construct a trie from the dictionary using your solution from part 2 (if you cannot com-
            plete part 2, you may attempt a solution based on built in Java data structures, although
            this will attract a penalty).*/
    }
}
