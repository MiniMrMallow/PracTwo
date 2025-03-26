import java.io.*;

// class representing an entry in the knowledge base
class KnowledgeBaseEntry implements Comparable<KnowledgeBaseEntry> {
    String term;
    String sentence;
    double confidence;

    public KnowledgeBaseEntry(String term, String sentence, double confidence) {
        this.term = term.trim().toLowerCase(); //  allows for consistent searching
        this.sentence = sentence;
        this.confidence = confidence;
    }

    @Override
    public int compareTo(KnowledgeBaseEntry other) {
        return this.term.compareTo(other.term); // compare based on the term for AVL Tree ordering
    }

    @Override
    public String toString() {
        return term + ": " + sentence + " (" + confidence + ")"; // string representation for the entry
    }

}

public class GenericsKbAVLApp {
    public static void main(String[] args) { 

        AVLTree<KnowledgeBaseEntry> avlTree = new AVLTree<>();  // create the avl tree for knowledge base entries

        // Load kowledge base from file into the AVL Tree
        try (BufferedReader br = new BufferedReader(new FileReader("GenericsKB.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 3) { // ensures the correct format
                    String term = parts[0];
                    String sentence = parts[1];
                    double confidence = Double.parseDouble(parts[2]);
                    avlTree.insert(new KnowledgeBaseEntry(term, sentence, confidence)); // insert into avl tree
                }

            }
        } catch (IOException e) {
            System.err.println("Error reading GenericsKB.txt: " + e.getMessage()); // handle file reading error 
            return;

        }

        // Process queries from GenericsKB-queries.txt
        try (BufferedReader br = new BufferedReader(new FileReader("GenericsKB-queries.txt"))) {
            String query;
            while ((query = br.readLine()) != null) {
                query = query.trim().toLowerCase(); // allows for a case sensitive search
                KnowledgeBaseEntry searchEntry = new KnowledgeBaseEntry(query, "", 0); // create a search entry
                BinaryTreeNode<KnowledgeBaseEntry> resultNode = avlTree.find(searchEntry); // search the AVL Tree
                if (resultNode != null) {
                    System.out.println(resultNode.data); // print the matching entry
                } else {
                    System.out.println("Term not found: " + query); // if term is not found say that 
                }

            }

        } catch (IOException e) {
            System.err.println("Error reading GenericsKB-queries.txt: " + e.getMessage()); // another file reading error but for queries file  
        }

        // print comparison counts
        System.out.println("Total insertion comparison: " + avlTree.getInsertComparisons());
        System.out.println("Total search comparisons: " + avlTree.getSearchComparisons());


    }
}

