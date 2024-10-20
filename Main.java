import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            // Create and start the ScrabbleGame, pointing to the correct file
            ScrabbleGame game = new ScrabbleGame("CollinsScrabbleWords_2019.txt");
            game.play();  // Start the game loop
        } catch (IOException e) {
            // Print the stack trace to see the exact error
            e.printStackTrace();
        }
    }
}
