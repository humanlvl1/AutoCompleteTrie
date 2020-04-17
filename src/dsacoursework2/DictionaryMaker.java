
package dsacoursework2;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.TreeMap;

/**
 1. read text document into a list of strings;
 2. form a set of words that exist in the document and count the number of times each word
 occurs in a method called FormDictionary;
 3. sort the words alphabetically; and
 4. write the words and associated frequency to file.
 */
public class DictionaryMaker {
/**
 * Reads all the words in a comma separated text document into an Array
 * @param
 */   
    public static ArrayList<String> readWordsFromCSV(String file) throws FileNotFoundException {
        Scanner sc=new Scanner(new File(file));
        sc.useDelimiter(" |,");
        ArrayList<String> words=new ArrayList<>();
        String str;
        while(sc.hasNext()){
            str=sc.next();
            str=str.trim();
            str=str.toLowerCase();
            words.add(str);
        }
        return words;
    }

    public static ArrayList<String> readLinesFromCSV(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<String> lines = new ArrayList<>();
        String line = null;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }

     public static void saveCollectionToFile(Collection<?> c,String file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);
         for(Object w: c){
            printWriter.println(w.toString());
         }
        printWriter.close();
     }

     public static TreeMap<String, Integer> formDictionary(ArrayList<String> document){
        TreeMap<String, Integer> dictionaryTree = new TreeMap<>();
        String temp;
        for (int i = 0; i < document.size(); i++) {
            temp = document.get(i);
            if(dictionaryTree.containsKey(temp)){
                dictionaryTree.put(temp, dictionaryTree.get(temp)+1);
             } else {
                dictionaryTree.put(temp, 1);
            }
         }
        return dictionaryTree;
     }

     public static void saveToFile(String path, Object o) throws FileNotFoundException {
         PrintStream ps = new PrintStream(new FileOutputStream(path, true));
         ps.print(o);
     }
         
    public static void main(String[] args) throws Exception {
        String pwd = System.getProperty("user.dir");
        ArrayList<String> in = readWordsFromCSV(pwd + "/testDocument.csv");
        TreeMap<String, Integer> dictionaryTree = formDictionary(in);
        System.out.println(dictionaryTree);

        saveToFile(pwd + "/testOutput.csv", dictionaryTree);
    }
}