package assignment2;

public class SolitaireCipher {
    public Deck key;

    public SolitaireCipher (Deck key) {
        this.key = new Deck(key); // deep copy of the deck
    }

    /*
     * TODO: Generates a keystream of the given size
     */
    public int[] getKeystream(int size) {
        /**** ADD CODE HERE ****/
        int[] keyStreamArr = new int[size];
        for (int i=0; i<size; i++) {
            keyStreamArr[i] = key.generateNextKeystreamValue();
        }
        return keyStreamArr;
    }

    /*
     * TODO: Encodes the input message using the algorithm described in the pdf.
     */
    public String encode(String msg) {
        /**** ADD CODE HERE ****/
        String s = "";
        String cipherText = "";
        for (int i=0; i<msg.length();i++) {
            if (('A' <= msg.charAt(i) && msg.charAt(i) <= 'Z') || ('a' <= msg.charAt(i) && msg.charAt(i) <= 'z')) {
                s = s.concat(Character.toString(msg.charAt(i))); // appends (concatenate) a string to the end
            }
        }
        s = s.toUpperCase();
        //System.out.println(s);
        int[] keyValues = getKeystream(s.length());
        //System.out.println(Arrays.toString(keyValues));
        int shift = 0;

        for (int i=0; i<keyValues.length; i++) {
            shift = keyValues[i] % 26;
            char c = (char) ((s.charAt(i) - 'A' + shift) % 26 + 'A');
            cipherText = cipherText.concat(Character.toString(c));
        }
        return cipherText;
    }

    /*
     * TODO: Decodes the input message using the algorithm described in the pdf.
     */
    public String decode(String msg) {
        /**** ADD CODE HERE ****/
        int[] keyValues = getKeystream(msg.length());
        int shift = 0;
        String decodedMessage = "";
        char c;
        for (int i=0; i<keyValues.length; i++) {
            shift = keyValues[i] % 26;
            if (msg.charAt(i) - 'A' - shift <0) {
                c = (char) ((msg.charAt(i) - 'A' - shift + 26) % 26 + 'A');
            } else {
                c = (char) ((msg.charAt(i) - 'A' - shift) % 26 + 'A');
            }
            decodedMessage = decodedMessage.concat(Character.toString(c));
        }
        return decodedMessage;
    }
}
