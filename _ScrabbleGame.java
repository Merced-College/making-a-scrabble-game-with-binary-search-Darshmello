import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
//DARSH GANGAKHEDKAR
//10/19/2024
//I HAD NO GROUP CAUSE THEY DID NOT RESPOND TO ME!!!!
//Word class to represent each word in the dictionary
class Word implements Comparable<Word> {
    private String word;

    // Constructor for Word object
    public Word(String word) {
        this.word = word.toLowerCase();  // Store all words in lowercase for uniformity
    }

    public String getWord() {
        return word;
    }

    @Override
    public int compareTo(Word other) {
        return this.word.compareTo(other.word);  // Compare words lexicographically
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Word other = (Word) obj;
        return this.word.equals(other.word);
    }

    @Override
    public String toString() {
        return word;
    }
}

// ScrabbleGame class to manage the gameplay and logic
class ScrabbleGame {
    private List<Word> words;  // List to store Word objects
    private List<Character> letters;  // List to store random letters
    private String userWord;  // Stores the word the user enters

    // Constructor that loads the word list from a file
    public ScrabbleGame(String wordFile) throws IOException {
        this.words = loadWords(wordFile);
        this.letters = new ArrayList<>();
    }

    // Method to load the words from a file and store them as Word objects
    private List<Word> loadWords(String wordFile) throws IOException {
        List<Word> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(wordFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    words.add(new Word(line.trim()));  // Add words to the list
                }
            }
        }
        Collections.sort(words);  // Sort the words for binary search
        return words;
    }

    // Method to generate 4 random letters for the player to use
    private void generateRandomLetters() {
        Random rand = new Random();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        letters.clear();  // Clear previous letters
        for (int i = 0; i < 4; i++) {
            char letter = alphabet.charAt(rand.nextInt(alphabet.length()));
            letters.add(letter);
        }
        System.out.println("Your letters are: " + letters);
    }

    // Binary search to check if the user's word exists in the sorted list
    private boolean isWordValid(String word) {
        Word searchWord = new Word(word);
        int index = Collections.binarySearch(words, searchWord);  // Use binary search
        return index >= 0;  // Return true if word is found
    }

    // Method to check if the word can be formed from the given letters
    private boolean wordUsesLetters(String word) {
        List<Character> tempLetters = new ArrayList<>(letters);  // Copy the letters list
        for (char c : word.toCharArray()) {
            if (tempLetters.contains(c)) {
                tempLetters.remove((Character) c);  // Remove used letter from list
            } else {
                return false;  // If any letter is not available, return false
            }
        }
        return true;
    }

    // Improvement 1: Calculate points based on word length
    private void calculatePoints() {
        int points = userWord.length();  // Points based on the length of the word
        System.out.println("Your word '" + userWord + "' is valid and earns you " + points + " points!");
    }

    // Improvement 2: Allow the user to exchange one letter
    private void exchangeLetter() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Would you like to exchange one letter? (yes/no): ");
        String exchange = scanner.nextLine().toLowerCase();
        if (exchange.equals("yes")) {
            System.out.println("Which letter would you like to exchange? " + letters);
            char letterToReplace = scanner.nextLine().charAt(0);
            if (letters.contains(letterToReplace)) {
                Random rand = new Random();
                char newLetter = (char) ('a' + rand.nextInt(26));  // Generate new random letter
                letters.set(letters.indexOf(letterToReplace), newLetter);  // Replace letter
                System.out.println("Your new letters are: " + letters);
            } else {
                System.out.println("That letter is not in your set!");
            }
        }
    }

    // Method to start and run the game
    public void play() {
        Scanner scanner = new Scanner(System.in);
        generateRandomLetters();  // Generate 4 random letters for the user
        exchangeLetter();  // Improvement: Allow the user to exchange one letter

        System.out.print("Enter a word using the given letters: ");
        userWord = scanner.nextLine().toLowerCase();

        // Check if the word uses the correct letters
        if (wordUsesLetters(userWord)) {
            // Check if the word is valid
            if (isWordValid(userWord)) {
                calculatePoints();  // Improvement: Calculate points based on word length
            } else {
                System.out.println("The word you entered is not valid.");
            }
        } else {
            System.out.println("Your word does not use the given letters correctly.");
        }
    }
}
