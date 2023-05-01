package assignment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;


public class main {

    private JPanel mainPanel;
    private JTextField messageTextField;
    private JTextField seedTextField;
    private JTextField numberOfShufflesTextField;
    private JTextField numberOfSuitsTextField;
    private JTextField numberOfCardsTextField;
    private JComboBox comboBox1;
    private JButton goButton;
    private JButton importFromTextButton;

    final int MIN_CARD_INDEX = 1;
    final int MAX_CARD_INDEX = 59;

    public main() {

        // Set the size of the window
        this.mainPanel.setSize(500, 500);

        // Sets the initial colours of the components
        initializeMainColours();

        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                go();
            }
        });
        importFromTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initializeTextWindow();
            }
        });
        messageTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                messageTextField.setText("");
            }
        });

        seedTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                seedTextField.setText("");
            }
        });
        numberOfShufflesTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                numberOfShufflesTextField.setText("");
            }
        });
        numberOfCardsTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                numberOfCardsTextField.setText("");
            }
        });
        numberOfSuitsTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                numberOfSuitsTextField.setText("");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Solitaire Cipher");
        frame.setContentPane(new main().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.requestFocus();
    }

    private void initializeMainColours() {

        // Get all components of the form
        Component[] comps = this.mainPanel.getComponents();

        // Set all of their backgrounds to white, as well as their sizes
        for (int i = 0; i < comps.length; i++) {
            comps[i].setBackground(Color.white);

        }

        this.goButton.setForeground(Color.black);
        this.importFromTextButton.setForeground(Color.black);

    }

    private void initializeTextWindow() {

        // Make an instance of the textInput class, to get its fields
        textInput textWindow = new textInput();

        // Set the message for the instructions of the window
        String instructions =
                "The purpose of this window is to reduce the time it takes " +
                "to run encoding/decoding operations. Instead of filling out " +
                "six individual text boxes, you can write out the six parameters " +
                "in one paragraph, one line at a time. Make sure you respect the format below! " +
                "Save your paragraphs in a text file to copy & paste them easily \n" +
                "\n" +
                 "Format: \n" +
                "stringMessage \n" +
                "intSeed \n" +
                "intNumberOfShuffles \n" +
                "intNumberOfCardsPerSuit \n" +
                "intNumberOfSuits \n" +
                "\n" + "Write your parameters in the text box below";


        JTextArea instructionTextArea = textWindow.getInstructionsTextArea();

        instructionTextArea.setText(instructions);
        instructionTextArea.setLineWrap(true);
        instructionTextArea.setWrapStyleWord(true);

        // Finally, make the new frame and display it
        JFrame textFrame = new JFrame("Input by Paragraph");
        textFrame.setContentPane(textWindow.getTextPanel());
        textFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        textFrame.pack();
        textFrame.setSize(400, 600);
        textFrame.setVisible(true);
    }

    private void go() {

        // Get the message String
        String message = messageTextField.getText();

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
            seed = Integer.parseInt(seedTextField.getText());
            shuffles = Integer.parseInt(numberOfShufflesTextField.getText());
            numOfCards = Integer.parseInt(numberOfCardsTextField.getText());
            numOfSuits = Integer.parseInt(numberOfSuitsTextField.getText());
        } catch (NumberFormatException e) {
            // Show an error message
            JOptionPane.showMessageDialog(mainPanel, "Looks like one of the numbers you entered isn't valid! \n" +
                    "Try again");
            reset();
        }

        // Call the correct method based on the choice of the user (encode/decode)
        switch (comboBox1.getSelectedItem().toString()) {
            case "Encode":
                encode(message, seed, shuffles, numOfCards, numOfSuits);
                break;
            case "Decode":
                decode(message, seed, shuffles, numOfCards, numOfSuits);
        }

    }

    // Resets the text box components
    private void reset() {
        // Reset the components
        messageTextField.setText("message");
        seedTextField.setText("seed");
        numberOfShufflesTextField.setText("shuffles");
        numberOfCardsTextField.setText("number of cards per suit");
        numberOfSuitsTextField.setText("number of suits");
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

    // Decodes the message
    private void decode(String message, int seed, int numShuffle, int numCards, int numSuits) {
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

    // Creates a pop-up window to display the result
    private void displayResult(String result) {

        int card_index = (int) (Math.random() * (MAX_CARD_INDEX - MIN_CARD_INDEX + 1) + MIN_CARD_INDEX);
        String card_path = "Card" + Integer.toString(card_index) + ".png";

        ImageIcon ace = new ImageIcon("src/assignment2/images/" + card_path);
        JOptionPane.showMessageDialog(mainPanel, result, "Result", JOptionPane.INFORMATION_MESSAGE, ace);
    }
}
