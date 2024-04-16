package LAB7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class TreeNode {
    String english;
    String spanish;
    TreeNode left, right;

    public TreeNode(String english, String spanish) {
        this.english = english;
        this.spanish = spanish;
        left = right = null;
    }
}

class BinaryTree {
    TreeNode root;

    public BinaryTree() {
        root = null;
    }

    public void insert(String english, String spanish, long frequency) {
        root = insertRec(root, english, spanish, frequency);
    }

    private TreeNode insertRec(TreeNode root, String english, String spanish, long frequency) {
        if (root == null) {
            root = new TreeNode(english, spanish);
            return root;
        }

        int comparison = english.compareTo(root.english);
        if (comparison < 0)
            root.left = insertRec(root.left, english, spanish, frequency);
        else if (comparison > 0)
            root.right = insertRec(root.right, english, spanish, frequency);

        return root;
    }

    public String translateSentence(String sentence) {
        String[] words = sentence.split(" ");
        StringBuilder translatedSentence = new StringBuilder();
        
        for (String word : words) {
            String translatedWord = translate(word);
            if (translatedWord != null) {
                translatedSentence.append(translatedWord).append(" ");
            } else {
                translatedSentence.append("[Not Found]").append(" ");
            }
        }
        
        return translatedSentence.toString().trim();
    }

    private String translate(String english) {
        return translateRec(root, english);
    }

    private String translateRec(TreeNode root, String english) {
        if (root == null)
            return null;

        int comparison = english.compareTo(root.english);
        if (comparison == 0)
            return root.spanish;
        else if (comparison < 0)
            return translateRec(root.left, english);
        else
            return translateRec(root.right, english);
    }

    public int getHeight() {
        return calculateHeight(root);
    }

    private int calculateHeight(TreeNode node) {
        if (node == null)
            return 0;
        else {
            int leftHeight = calculateHeight(node.left);
            int rightHeight = calculateHeight(node.right);

            return Math.max(leftHeight, rightHeight) + 1;
        }
    }
}

public class file {
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        loadTranslations(tree, "/Users/eimaanali/Desktop/CS211 Coding/LAB7/EnglishSpanish.csv", "/Users/eimaanali/Desktop/CS211 Coding/LAB7/EnglishFrequencies.csv");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter an English sentence to translate: ");
        String englishSentence = scanner.nextLine().trim();

        // Test translation
        String translatedSentence = tree.translateSentence(englishSentence);
        System.out.println("Spanish Translation: " + translatedSentence);

        // Evaluate average steps for the given sentence
        List<String> words = Arrays.asList(englishSentence.split(" "));
        double averageSteps = averageStepsForWords(tree, words);
        System.out.println("Average steps to find words: " + averageSteps);

        // Calculate tree height
        int height = tree.getHeight();
        System.out.println("Tree Height: " + height);

        scanner.close();
    }

    private static double averageStepsForWords(BinaryTree tree, List<String> words) {
        int totalSteps = 0;
        int foundWords = 0;
        for (String word : words) {
            int steps = stepsToFindWord(tree.root, word);
            if (steps != -1) {
                totalSteps += steps;
                foundWords++;
            }
        }
        return foundWords > 0 ? (int) Math.round((double) totalSteps / foundWords) : 0;
    }

    private static int stepsToFindWord(TreeNode root, String english) {
        int steps = 0;
        TreeNode current = root;
        while (current != null) {
            int comparison = english.compareTo(current.english);
            if (comparison == 0)
                return steps;
            else if (comparison < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
            steps++;
        }
        return -1; // Word not found
    }

    private static void loadTranslations(BinaryTree tree, String translationsFilename, String frequenciesFilename) {
        Map<String, Long> frequencies = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(frequenciesFilename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    frequencies.put(parts[0].trim(), Long.parseLong(parts[1].trim()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String[]> translations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(translationsFilename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    translations.add(parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sort translations based on frequencies
        translations.sort((arr1, arr2) -> {
            long freq1 = frequencies.getOrDefault(arr1[0].trim(), 0L);
            long freq2 = frequencies.getOrDefault(arr2[0].trim(), 0L);
            return Long.compare(freq2, freq1);
        });

        // Insert sorted translations into the tree in a balanced manner
        insertBalanced(tree, translations, frequencies, 0, translations.size() - 1);
    }

    private static void insertBalanced(BinaryTree tree, List<String[]> translations, Map<String, Long> frequencies, int start, int end) {
        if (start > end) {
            return;
        }
        int mid = start + (end - start) / 2;
        String[] translation = translations.get(mid);
        tree.insert(translation[0].trim(), translation[1].trim(), frequencies.getOrDefault(translation[0].trim(), 0L));
        insertBalanced(tree, translations, frequencies, start, mid - 1);
        insertBalanced(tree, translations, frequencies, mid + 1, end);
    }
}
