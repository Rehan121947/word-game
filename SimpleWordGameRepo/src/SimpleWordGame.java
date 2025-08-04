import java.util.*;
import java.io.*;

public class SimpleWordGame {
    private static final int ROUNDS = 5;
    private static final int START_TIME = 120;
    private static final int TIME_DECREMENT = 20;
    private static final String DICTIONARY_FILE = "dictionary.txt";
    private static Set<String> dictionary = new HashSet<>();
    private static Set<String> usedWords = new HashSet<>();
    private static char lastChar = 0;
    private static int[] scores = new int[2];
    private static List<Character> specialLetters = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        loadDictionary();
        Random rand = new Random();
        while (specialLetters.size() < 3) {
            char ch = (char) ('a' + rand.nextInt(26));
            if (!specialLetters.contains(ch)) specialLetters.add(ch);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Special letters (3 points each): " + specialLetters);
        for (int round = 0; round < ROUNDS; round++) {
            System.out.println("\nRound " + (round + 1) + " - Time: " + (START_TIME - round * TIME_DECREMENT) + " sec");
            for (int player = 0; player < 2; player++) {
                String word;
                while (true) {
                    System.out.print("Player " + (player + 1) + ", enter a word: ");
                    word = scanner.nextLine().toLowerCase().trim();
                    if (isValid(word)) break;
                    else System.out.println("Invalid or repeated word. Try again.");
                }
                usedWords.add(word);
                lastChar = word.charAt(word.length() - 1);
                scores[player] += calculatePoints(word);
            }
        }
        System.out.println("\nFinal Scores:");
        System.out.println("Player 1: " + scores[0]);
        System.out.println("Player 2: " + scores[1]);
        if (scores[0] > scores[1]) System.out.println("Player 1 wins!");
        else if (scores[1] > scores[0]) System.out.println("Player 2 wins!");
        else System.out.println("It's a tie!");
    }

    private static boolean isValid(String word) {
        return dictionary.contains(word) && !usedWords.contains(word)
                && (lastChar == 0 || word.charAt(0) == lastChar);
    }

    private static int calculatePoints(String word) {
        int points = 0;
        for (char c : word.toCharArray()) {
            points += specialLetters.contains(c) ? 3 : 1;
        }
        return points;
    }

    private static void loadDictionary() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(DICTIONARY_FILE));
        String line;
        while ((line = reader.readLine()) != null) {
            dictionary.add(line.trim().toLowerCase());
        }
        reader.close();
    }
}