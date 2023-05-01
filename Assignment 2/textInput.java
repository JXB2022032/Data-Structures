package assignment2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class textInput {
    private JPanel textPanel;
    private JTextArea inputTextArea;

    private JButton encodeButton;
    private JButton decodeButton;
    private JTextArea instructionsTextArea;

    final int MIN_CARD_INDEX = 1;
    final int MAX_CARD_INDEX = 59;

    public textInput() {

        encodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parseInfo("encode");
            }
        });
        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parseInfo("decode");
            }
        });
    }

    private void parseInfo(String encode) {

        String textContent = inputTextArea.getText();

        String[] data = textContent.split("\n");

        // Initialize variables for encoding/decoding
        int seed = 0;
        int shuffles = 0;
        int numOfCards = 0;
        int numOfSuits = 0;

        /*
         * I would've liked to have left the input validation to the assignment's classes,
         * but the form will crash anyway if try to pass a invalid input to the constructors.
         * This is more of a final product anyway, not really a tester
         */
        try {
            seed = Integer.parseInt(data[1]);
            shuffles = Integer.parseInt(data[2]);
            numOfCards = Integer.parseInt(data[3]);
            numOfSuits = Integer.parseInt(data[4]);
        } catch (Exception e) {
            // Show an error message
            JOptionPane.showMessageDialog(textPanel, "Looks like some of the numbers you entered aren't valid! \n" +
                    "Try again");
            reset();
        }


        if (encode.equals("encode")) {
            encode(data[0], seed, shuffles, numOfCards, numOfSuits);
        }
        else {
            decode(data[0], seed, shuffles, numOfCards, numOfSuits);
        }
    }

    // Encodes the message
    private void encode(String message, int seed, int numShuffle, int numCards, int numSuits) {
        // Create the deck
        Deck keyDeck = new Deck(numCards, numSuits);

        // Set the seed
        keyDeck.gen.setSeed(seed);

        // Shuffle the deck
        for (int i = 0; i < numShuffle; i++) {
            keyDeck.shuffle();
        }

        // New cipher object
        SolitaireCipher cipher = new SolitaireCipher(keyDeck);

        // Decode result
        String encoded = cipher.encode(message);

        // Displays the result to the user
        displayResult(encoded);
        reset();
    }

    // Creates a pop-up window to display the result
    private void displayResult(String result) {

        int card_index = (int) (Math.random() * (MAX_CARD_INDEX - MIN_CARD_INDEX + 1) + MIN_CARD_INDEX);
        String card_path = "Card" + Integer.toString(card_index) + ".png";

        ImageIcon ace = new ImageIcon("src/assignment2/images/" + card_path);
        JOptionPane.showMessageDialog(textPanel, result, "Result", JOptionPane.INFORMATION_MESSAGE, ace);
    }

    // Decodes the message
    public void decode(String message, int seed, int numShuffle, int numCards, int numSuits) {
        // Create the deck
        Deck keyDeck = new Deck(numCards, numSuits);

        // Set the seed
        keyDeck.gen.setSeed(seed);

        // Shuffle the deck
        for (int i = 0; i < numShuffle; i++) {
            keyDeck.shuffle();
        }

        // New cipher object
        SolitaireCipher cipher = new SolitaireCipher(keyDeck);

        // Decode result
        String decoded = cipher.decode(message);

        // Displays the result to the user
        displayResult(decoded);
        reset();
    }

    // Resets the text box components
    private void reset() {
        // Reset the input text box
        inputTextArea.setText("");
    }


    public JPanel getTextPanel() {
        return this.textPanel;
    }

    public JTextArea getInstructionsTextArea() {
        return this.instructionsTextArea;
    }

    public JTextArea getInputTextArea() {
        return this.inputTextArea;
    }

}
