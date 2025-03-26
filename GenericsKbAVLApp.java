import java.io.*;

class KnowledgeBaseEntry implements Comparable<KnowledgeBaseEntry> {
    String term;
    String sentence;
    double confidence;

    public KnowledgeBaseEntry(String term, String sentence, double confidence) {
        this.term = term.trim().toLowerCase(); // Normalize term for consistency
        this.sentence = sentence;
        this.confidence = confidence;
    }

    @Override
    public int compareTo(KnowledgeBaseEntry other) {
        return this.term.compareTo(other.term);
    }

    @Override
    public String toString() {
        return term + ": " + sentence + " (" + confidence + ")";
    }
}

public class GenericsKbAVLApp {
    public static void main(String[] args) {
        AVLTree<KnowledgeBaseEntry> avlTree = new AVLTree<>();

        // Load dataset into AVL Tree
        try (BufferedReader br = new BufferedReader(new FileReader("GenericsKB.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 3) {
                    String term = parts[0];
                    String sentence = parts[1];
                    double confidence = Double.parseDouble(parts[2]);
                    avlTree.insert(new KnowledgeBaseEntry(term, sentence, confidence));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading GenericsKB.txt: " + e.getMessage());
            return;
        }

        // Process queries from GenericsKB-queries.txt
        try (BufferedReader br = new BufferedReader(new FileReader("GenericsKB-queries.txt"))) {
            String query;
            while ((query = br.readLine()) != null) {
                query = query.trim().toLowerCase(); // Normalize search term
                KnowledgeBaseEntry searchEntry = new KnowledgeBaseEntry(query, "", 0);
                BinaryTreeNode<KnowledgeBaseEntry> resultNode = avlTree.find(searchEntry);
                if (resultNode != null) {
                    System.out.println(resultNode.data);
                } else {
                    System.out.println("Term not found: " + query);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading GenericsKB-queries.txt: " + e.getMessage());
        }
    }
}

