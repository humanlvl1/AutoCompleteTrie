package dsacoursework2;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class AutoCompletionTrie {
    TrieNode root;

    public AutoCompletionTrie(){
        this.root = new TrieNode();
    }

    public TrieNode getRoot() {
        return root;
    }

    public void setRoot(TrieNode node){
        this.root = node;
    }

    /**
     *
     * @param key : string to be entered into the trie.
     * @return
     */
    public boolean add(String key, int frequency){
        //add the string to the trie. Add any nodes necessary.
        TrieNode curr = root;
        for (char c : key.toCharArray()) {
            if (curr.getChildren() == null){
                curr.initChildren();
            }
            if (curr.getChild(c) == null){
                curr.setNewChild(c);
            }
            curr = curr.getChild(c);
        }

        if(!curr.isWord()){
            curr.setIsWord(true);
            curr.setFrequency(frequency);
        } else {
            return false;
        }
        return true; //return true if successful (false if already in trie)
    }

    public boolean contains(String key){
        //returns true if the WHOLE word i2s in the trie (not just as a prefix)
        TrieNode curr = root;
        for (char c : key.toCharArray()) {
            if (curr.getChildren()==null || curr.getChild(c) == null){
                return false;
            }
            curr = curr.getChild(c);
        }
        return curr.isWord();
    }

    public String outputBreadthFirstSearch(){ //t
        /*return a string representing breadth-first traversal*/
        Queue<TrieNode>  queue = new LinkedList<>();
        queue.add(root);
        StringBuilder s = new StringBuilder();
        TrieNode trieNode;
        while(!queue.isEmpty()){
            trieNode = queue.poll();
            if(trieNode.getChildren()!=null){
                for (int i = 0; i < trieNode.getChildren().length; i++) {
                    if (trieNode.getChildren()[i] != null) {
                        s.append((char)(i+'a'));
                        queue.add(trieNode.getChildren()[i]);
                    }
                }
            }
        }
        return s.toString();
    }

    public String outputDepthFirstSearch(){
        return root.getDepthFirstString();
    }

    public AutoCompletionTrie getSubTrie(String prefix){
        TrieNode curr = root;
        for (char c : prefix.toCharArray()) {//find the node where prefix ends
            if (curr.getChildren()==null || curr.getChild(c)==null){
                return null;
            }
            curr = curr.getChild(c);
        }
        AutoCompletionTrie subTrie = new AutoCompletionTrie();
        subTrie.setRoot(curr);
        return subTrie;
    }

    public LinkedList<String> getAllWords(){ //linked list because add is O(1). Add is the only thing we need to do.
        //returns all words. Should be as efficient as possible.
        LinkedList<String> dictionary = new LinkedList<>();
        root.findWords(dictionary, "");
        return dictionary;
    }

    public HashMap<String, Integer> getAllWordsWithCount(){
        HashMap<String, Integer> hashMap = new HashMap<>();
        root.findWordsWithCount(hashMap, "");
        return hashMap;
    }

    public TreeMap<Integer, LinkedList<String>> getFrequencyMap(){
        TreeMap<Integer,LinkedList<String>> treeMap = new TreeMap<>();
        root.populateFrequencyMap(treeMap, "");
        return treeMap;
    }

    public void writeAutoComplete(String prefix, PrintWriter writer){
        AutoCompletionTrie subTrie = this.getSubTrie(prefix);
        TreeMap<Integer,LinkedList<String>> allWords = subTrie.getFrequencyMap();

        int totalWordFreq = 0;
        for (Map.Entry<Integer,LinkedList<String>> pair : allWords.entrySet())
            totalWordFreq += pair.getKey();

        TrieNode subTrieRoot = subTrie.getRoot();
        LinkedList<String> entryValue;
        if(subTrieRoot.isWord()){
            entryValue = allWords.get(subTrieRoot.getFrequency());
            if(entryValue == null) {
                entryValue = new LinkedList<>();
            }
            entryValue.push("");
            allWords.put(subTrieRoot.getFrequency(), entryValue);
        }
        writer.append(prefix+",");

        Map.Entry<Integer,LinkedList<String>> entry;
        float probability;
        int writeCount=0;
        while(allWords.size()>0){
            entry = allWords.pollLastEntry();

            entryValue = entry.getValue();
            if (entryValue.size() > 1) {
                Collections.sort(entryValue);
            }

            for (String str : entryValue) {
                probability = (float)entry.getKey()/totalWordFreq;
                System.out.println(prefix+str + " (probability "+probability+")");
                writer.append(prefix+str+","+probability+",");
                writeCount++;
                if(writeCount==3) {
                    System.out.println();
                    writer.append("\n");
                    return;
                }
            }
        }
        System.out.println();
        writer.append("\n");
    }

    public static void main(String[] args) throws Exception{
        String pwd = System.getProperty("user.dir");
        ArrayList<String> in = DictionaryMaker.readWordsFromCSV(pwd + "/lotr.csv");
        TreeMap<String, Integer> dictionaryTree = DictionaryMaker.formDictionary(in);

        AutoCompletionTrie trie = new AutoCompletionTrie();
        for (Map.Entry<String, Integer> entry : dictionaryTree.entrySet()) {
            trie.add(entry.getKey(), entry.getValue());
        }
        in = DictionaryMaker.readLinesFromCSV(pwd + "/lotrQueries.csv");

        File csv = new File(pwd+"/lotrMatches.csv");
        PrintWriter writer = new PrintWriter(csv);

        for (String s : in) {
           trie.writeAutoComplete(s, writer);
        }

        writer.flush();
        writer.close();
    }
}

