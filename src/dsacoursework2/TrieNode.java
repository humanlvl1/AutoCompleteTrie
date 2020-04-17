package dsacoursework2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

public class TrieNode {
    private TrieNode[] children;
    private boolean isWord;
    private int frequency;

    /**
     * Default constructor. Sets children to null to save space that a Trie occupies.
     */
    public TrieNode(){
        children = null;
        isWord = false;
        frequency = 0;
    }

    public TrieNode[] getChildren() {
        return children;
    }
    public TrieNode getChild(char c){
        return children[c - 'a'];
    }
    public boolean isWord(){
        return isWord;
    }
    public int getFrequency(){
        return frequency;
    }
    public void setIsWord(boolean bool){
        this.isWord = bool;
    }
    public void setFrequency(int frequency){
        this.frequency = frequency;
    }

    public void initChildren(){
        children = new TrieNode[26];
    }

/*    public void setChild(int index, TrieNode node){//todo check if you need it
        children[index] = node;
    }*/

    /**
     * Creates a new child node at specified index.
     * @param index : index in the 'children' array where the new node will be.
     */
    public void setNewChild(int index){
        index -= 'a';
        children[index] = new TrieNode();
    }

  /*  public void findWords(LinkedList<String> strList, String str){
        if(children!=null) {
            for (int i = 0; i < children.length; i++) {
                if (children[i] != null) {
                    if (children[i].isWord) {
                        strList.add(new StringBuilder().append(str).append((char)(i+'a')).toString());
                    }
                    children[i].findWords(strList, str.concat(String.valueOf((char) (i + 97))));
                }
            }
        }
    }*/
  public void findWords(LinkedList<String> strList, String str){
      if(children!=null) {
          for (int i = 0; i < children.length; i++) {
              if (children[i] != null) {
                  String temp = str.concat(String.valueOf((char) (i + 97)));
                  if (children[i].isWord) {
                      strList.add(temp);
                  }
                  children[i].findWords(strList, temp);
              }
          }
      }
  }

    public void findWordsWithCount(HashMap<String, Integer> hashMap, String s){
        if(children!=null) {
            for (int i = 0; i < children.length; i++) {
                if (children[i] != null) {
                    if (children[i].isWord) {
                        hashMap.put(new StringBuilder().append(s).append((char)(i+'a')).toString(),
                                children[i].getFrequency());
                    }
                    children[i].findWordsWithCount(hashMap, s.concat(String.valueOf((char) (i + 97))));
                }
            }
        }
    }

    public void populateIntegerStringsMap(TreeMap<Integer,LinkedList<String>> treeMap, String s){
        if(children!=null) {
            for (int i = 0; i < children.length; i++) {
                if (children[i] != null) {
                    if (children[i].isWord) {
                        LinkedList<String> list = treeMap.get(children[i].getFrequency());
                        if(list==null){
                            list = new LinkedList<>();
                        }
                        list.add(new StringBuilder().append(s).append((char)(i+'a')).toString());
                        treeMap.put(children[i].getFrequency(), list);
                    }
                    children[i].populateIntegerStringsMap(treeMap, s.concat(String.valueOf((char) (i + 97))));
                }
            }
        }
    }

    public String getDepthFirstString(){
        if(children==null){
            return "";
        }
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < children.length; i++) {
            if(children[i]!=null){
                s.append((char)(i+'a'));
                s.append(children[i].getDepthFirstString());
            }
        }
        return s.toString();
    }
}
