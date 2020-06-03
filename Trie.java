import java.util.ArrayList;

public class Trie {
    private final int MAXIMUM_CHARSET_COUNT = 26;
    private class Node {
        Node[] children = new Node[MAXIMUM_CHARSET_COUNT];
        private boolean isWord;
        private int searchFrequency;

        Node() {
            for (int i=0; i<MAXIMUM_CHARSET_COUNT; i++)
                children[i] = null;
            isWord = false;
            searchFrequency = 0;
        }
    }

    private Node root;

    public Trie(){
        root = new Node();
    }

    public void insert(String word){
        supportInsert(root,word,0);
    }

    private void supportInsert(Node current, String word, int j){
        if (j == word.length()){
            current.isWord = true;
            return;
        }
        int i = word.charAt(j) - 'a';
        if (current.children[i] == null)
            current.children[i] = new Node();
        supportInsert(current.children[i], word, j+1);
    }

    public boolean searchWord(String word){
        Node result = supportSearch(root, word, 0);
        if (result == null)
            return false;
        result.searchFrequency++;
        return result.isWord;
    }

    public boolean searchPrefix(String word){
        Node result = supportSearch(root, word, 0);
        if (result == null)
            return false;
        return true;
    }

    public int searchFrequency(String word){
        Node result = supportSearch(root, word, 0);
        if (result == null)
            return -1;
        return result.searchFrequency;
    }

    private Node supportSearch(Node current, String word, int j){
        if (j == word.length())
            return current;
        int i = word.charAt(j) - 'a';
        if (current.children[i] == null){
            return null;
        }
        return supportSearch(current.children[i], word, j+1);
    }

    public ArrayList<String> getWordsWithPrefix(String word){
        ArrayList<String> words = new ArrayList<>();
        Node prefix = supportSearch(root, word, 0);
        if (prefix == null)
            return words;
        supportWordPrefix(prefix, word, words);
        return words;
    }

    public ArrayList<String> getWords(){
        ArrayList<String> words = new ArrayList<>();
        supportWordPrefix(root, "", words);
        return words;
    }

    private void supportWordPrefix(Node current, String word, ArrayList<String> words){
        if (current == null)
            return;
        if (current.isWord)
            words.add(word);
        for (int i=0; i<MAXIMUM_CHARSET_COUNT; i++){
            supportWordPrefix(current.children[i], word + Character.toString((char) ('a'+i)), words);
        }
    }

    public static void main(String[] args){
        Trie t = new Trie();
        t.insert("by");
        t.insert("sea");
        t.insert("sells");
        t.insert("she");
        t.insert("shells");
        t.insert("shore");
        t.insert("the");

        System.out.println("Search result for word 'sell': " + t.searchWord("sell"));
        System.out.println("Search result for prefix 'sell': " + t.searchPrefix("sell"));

        System.out.println("Search result for word 'shore': " + t.searchWord("shore"));
        System.out.println("Search result for word 'shore': " + t.searchWord("shore"));
        System.out.println("Search result for word 'shore': " + t.searchWord("shore"));
        System.out.println("The word 'shore' has been searched " + t.searchFrequency("shore") + " times");

        System.out.println("The words starting with prefix 'sh': " + t.getWordsWithPrefix("sh"));
        System.out.println("All the words in the trie: " + t.getWords());
    }
}