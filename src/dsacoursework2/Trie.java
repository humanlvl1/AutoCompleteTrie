package dsacoursework2;

import java.util.LinkedList;
import java.util.Queue;

public class Trie {
    TrieNode root;

    public Trie(){
        this.root = new TrieNode();
    }

    public void setRoot(TrieNode node){
        this.root = node;
    }

    /**
     *
     * @param key : string to be entered into the trie.
     * @return
     */
    public boolean add(String key){
        //add the string to the trie. Add any nodes necessary.
        TrieNode curr = root;
        for (char c : key.toCharArray()) {
            if (curr.getChildren() == null){
                curr.initChildren();
            }
            if (curr.getChild(c) == null){
                curr.setNewChild(c);
            }
            curr = curr.getChild(c); //fundamental op
        }
        if(!curr.isWord()){
            curr.setIsWord(true);
        } else {
            return false;
        }
        return true; //return true if successful (false if already in trie)
    }

    public boolean contains(String key){
        //returns true if the WHOLE word is in the trie (not just as a prefix)
        TrieNode curr = root;
        for (char c : key.toCharArray()) {
            if (curr.getChildren()==null || curr.getChild(c) == null){
                return false;
            }
            curr = curr.getChild(c);
        }
        return curr.isWord();
    }

    public String outputBreadthFirstSearch(){
        /*return a string representing breadth-first traversal*/
        Queue<TrieNode> queue = new LinkedList<>();
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

    public Trie getSubTrie(String prefix){
        TrieNode curr = root;
        for (char c : prefix.toCharArray()) {//find the node where prefix ends
            if (curr.getChildren()==null || curr.getChild(c) == null){
                return null;
            }
            curr = curr.getChild(c);
        }
        Trie subTrie = new Trie();
        subTrie.setRoot(curr);
        return subTrie;
    }

    public LinkedList<String> getAllWords(){ //linked list because add is O(1). Add is the only thing we need to do.
        //returns all words. Should be as efficient as possible.
        LinkedList<String> wordList = new LinkedList<>();
        root.findWords(wordList, "");
        return wordList;
    }

    private static Trie createExampleTrie(){
        Trie trie = new Trie();
        trie.add("bat");
        trie.add("cat");
        trie.add("chat");
        trie.add("cheers");
        trie.add("cheese");
        return trie;
    }

    public static void main(String[] args) {
        Trie trie = new Trie();

        System.out.println("Adding 'abcd' for the first time: "+trie.add("abcd"));
        System.out.println("Adding 'abcd' for the second time: "+trie.add("abcd"));
        System.out.println("Trie contains 'abcd' = "+trie.contains("abcd"));
        System.out.println("Trie contains 'abc' = "+trie.contains("abc"));
        System.out.println("Trie contains 'abcde' = "+trie.contains("abcde"));

        Trie exampleTrie = createExampleTrie();
        System.out.println("Breadth first: " + exampleTrie.outputBreadthFirstSearch());
        System.out.println("Depth first: " + exampleTrie.outputDepthFirstSearch());

        Trie subTrie = exampleTrie.getSubTrie("ch");
        System.out.println("Depth first of a subtree with root at 'ch': " + subTrie.outputDepthFirstSearch());

        System.out.println("Adding 'baton' as an example of a container word.");
        exampleTrie.add("baton");

        System.out.println("All words in exampleTrie: "+exampleTrie.getAllWords());
    }
}
