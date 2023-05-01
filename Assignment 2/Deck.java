package assignment2;

import java.util.Random;

public class Deck {
    public static String[] suitsInOrder = {"clubs", "diamonds", "hearts", "spades"};
    public static Random gen = new Random();

    public int numOfCards; // contains the total number of cards in the deck
    public Card head; // contains a pointer to the card on the top of the deck

    /*
     * TODO: Initializes a Deck object using the inputs provided
     */
    public Deck(int numOfCardsPerSuit, int numOfSuits) {
        /**** ADD CODE HERE ****/
        if (numOfCardsPerSuit < 1 || numOfCardsPerSuit > 13) {
            throw new IllegalArgumentException("first input is not a number between 1 and 13");
        }
        if (numOfSuits < 1 || numOfSuits > 4) {
            throw new IllegalArgumentException("second input is not a number between 1 and the size of suitsInOrder");
        }
        for (int i=0; i<numOfSuits; i++) {
            for (int j=1; j<=numOfCardsPerSuit; j++) {
                this.addCard(new PlayingCard(suitsInOrder[i],j));
            }
        }
       this.addCard(new Joker("red"));
       this.addCard(new Joker("black"));
       //printDeck();
    }

    /*
     * TODO: Implements a copy constructor for Deck using Card.getCopy().
     * This method runs in O(n), where n is the number of cards in d.
     */
    public Deck(Deck d) {
        /**** ADD CODE HERE ****/

        Card tmpCard = d.head;
            for (int i = 0; i < d.numOfCards; i++) {
                this.addCard(tmpCard.getCopy());
                tmpCard = tmpCard.next;
            }
        this.numOfCards = d.numOfCards;
        //printDeck();
    }

    /*
     * For testing purposes we need a default constructor.
     */
    public Deck() {}

    /*
     * TODO: Adds the specified card at the bottom of the deck. This
     * method runs in $O(1)$.
     */
    public void addCard(Card c) {
        /**** ADD CODE HERE ****/
        if (this.numOfCards != 0 && this.numOfCards != 1) {
            this.head.prev.next = c;    // last cardNode -> c
            c.prev = this.head.prev;    // last cardNode <- c
            c.next = this.head;     // c -> head
            this.head.prev = c;     // head -> c
        }
        if (this.numOfCards == 0) {
            this.head = c;
            this.head.next = this.head;
            this.head.prev = this.head;
        }
        if (this.numOfCards == 1) {
            c.next = this.head;
            c.prev = this.head;
            this.head.next = c;
            this.head.prev = c;
        }
        this.numOfCards++;
    }

    /*
     * TODO: Shuffles the deck using the algorithm described in the pdf.
     * This method runs in O(n) and uses O(n) space, where n is the total
     * number of cards in the deck.
     */
    public void shuffle() {
        /**** ADD CODE HERE ****/
        if (this.numOfCards==0) {
            return;
        }

        Card tmpCard = this.head;
        Card[] cardArr = new Card[numOfCards];

        for (int i=0; i<cardArr.length; i++) {
            cardArr[i] = tmpCard; 
            tmpCard = tmpCard.next;
        }
        //System.out.println(Arrays.toString(cardArr));
        for (int i=numOfCards-1; i>0; i--) {
            int j = gen.nextInt(i+1);
            Card tmp = cardArr[i];
            cardArr[i] = cardArr[j];    // Swap Algorithm in an array
            cardArr[j] = tmp;
        }
        //System.out.println(Arrays.toString(cardArr));
        Card c = cardArr[0];
        this.head = c;
        for (int i = 0;i<this.numOfCards;i++) {
                c.next = cardArr[i];     // card -> next card
                c.next.prev = c;      // card <- next card
                c = c.next;
                this.head.prev = c;     // head -> tail (c)
                c.next = this.head;     // head <- tail (c)
        }
        //printDeck();
    }

    /*
     * TODO: Returns a reference to the joker with the specified color in
     * the deck. This method runs in O(n), where n is the total number of
     * cards in the deck.
     */
    public Joker locateJoker(String color) {
        /**** ADD CODE HERE ****/
        Card c = this.head;
        Joker joker = new Joker(color); // create a joker object, either red or black
        for (int i=0; i<numOfCards; i++) {
            if (c.toString().equals(joker.toString())) {
                return (Joker) c;
            }
            c = c.next;
        }
        return null;
    }

    /*
     * TODO: Moved the specified Card, p positions down the deck. You can
     * assume that the input Card does belong to the deck (hence the deck is
     * not empty). This method runs in O(p).
     */
    public void moveCard(Card c, int p) {
        /**** ADD CODE HERE ****/
        c.prev.next = c.next;
        c.next.prev = c.prev;
        Card tmp = c;   // Make a copy of card c
        for (int i=0; i<p; i++) {
            tmp = tmp.next;     // locate the card where c need to insert after
        }

        tmp.next.prev = c;
        c.next = tmp.next;
        c.prev = tmp;
        tmp.next = c;
        //printDeck();
    }

    /*
     * TODO: Performs a triple cut on the deck using the two input cards. You
     * can assume that the input cards belong to the deck and the first one is
     * nearest to the top of the deck. This method runs in O(1)
     */
    public void tripleCut(Card firstCard, Card secondCard) {
        /**** ADD CODE HERE ****/
        Card tail = this.head.prev;
        Card cardBeforeFirst = firstCard.prev;
        Card cardAfterSecond = secondCard.next;

        if (this.head == firstCard && this.head.prev != secondCard) {
            this.head = cardAfterSecond;
            return;
        } else if (this.head != firstCard && this.head.prev == secondCard) {
            this.head = firstCard;
            return;
        }

        secondCard.next = this.head;
        this.head.prev = secondCard;

        cardBeforeFirst.next = cardAfterSecond;
        cardAfterSecond.prev = cardBeforeFirst;

        tail.next = firstCard;
        firstCard.prev = tail;

        this.head = cardAfterSecond;
        this.head.prev = cardBeforeFirst;
        this.head.prev.next = this.head;
    }

    /*
     * TODO: Performs a count cut on the deck. Note that if the value of the
     * bottom card is equal to a multiple of the number of cards in the deck,
     * then the method should not do anything. This method runs in O(n).
     */
    public void countCut() {
        /**** ADD CODE HERE ****/
        if (this.head == null) {
            return;
        }
        Card c = this.head;
        Card oldHead = this.head;
        Card tail = this.head.prev;
        Card beforeTail = tail.prev;

        int countCutIndex = tail.getValue()%this.numOfCards;

        if (countCutIndex == 0 || countCutIndex == this.numOfCards-1) {
            return;
        }
        while (countCutIndex > 1) {
            c = c.next;
            countCutIndex--;
        }

        tail.next = c.next;     // reset circular linked list tail reference
        c.next.prev = tail;     // reset circular linked list head reference

        this.head = c.next;

        c.next = tail;
        tail.prev = c;
        oldHead.prev = beforeTail;
        beforeTail.next = oldHead;
    }

    /*
     * TODO: Returns the card that can be found by looking at the value of the
     * card on the top of the deck, and counting down that many cards. If the
     * card found is a Joker, then the method returns null, otherwise it returns
     * the Card found. This method runs in O(n).
     */
    public Card lookUpCard() {
        /**** ADD CODE HERE ****/
        Card c = this.head;

        int lookUpValue = c.getValue();
        while(lookUpValue != 0){
            c = c.next;
            lookUpValue--;
        }
        if (c instanceof Joker) {
            return null;
        }
        return c;
    }

    /*
     * TODO: Uses the Solitaire algorithm to generate one value for the keystream
     * using this deck. This method runs in O(n).
     */
    public int generateNextKeystreamValue() {
        /**** ADD CODE HERE ****/
        Joker RJ = locateJoker("red");
        moveCard(RJ,1);

        Joker BJ = locateJoker("black");
        moveCard(BJ,2);

        Card c = this.head;
        Card firstJoker = c;
        for (int i=0; i<numOfCards; i++) {
            if (c instanceof Joker) {
                firstJoker = c;
            } else {
                c = c.next;
            }
        }
        if (firstJoker.toString().equals(RJ.toString())) {
            tripleCut(firstJoker, BJ);
        }
        if (firstJoker.toString().equals(BJ.toString())) {
            tripleCut(firstJoker, RJ);
        }

        countCut();
        if (lookUpCard() == null) {
            return generateNextKeystreamValue();
        }
        return lookUpCard().getValue();
    }

    public abstract class Card {
        public Card next;
        public Card prev;

        public abstract Card getCopy();
        public abstract int getValue();

    }

    public class PlayingCard extends Card {
        public String suit;
        public int rank;

        public PlayingCard(String s, int r) {
            this.suit = s.toLowerCase();
            this.rank = r;
        }

        public String toString() {
            String info = "";
            if (this.rank == 1) {
                //info += "Ace";
                info += "A";
            } else if (this.rank > 10) {
                String[] cards = {"Jack", "Queen", "King"};
                //info += cards[this.rank - 11];
                info += cards[this.rank - 11].charAt(0);
            } else {
                info += this.rank;
            }
            //info += " of " + this.suit;
            info = (info + this.suit.charAt(0)).toUpperCase();
            return info;
        }

        public PlayingCard getCopy() {
            return new PlayingCard(this.suit, this.rank);
        }

        public int getValue() {
            int i;
            for (i = 0; i < suitsInOrder.length; i++) {
                if (this.suit.equals(suitsInOrder[i]))
                    break;
            }

            return this.rank + 13*i;
        }

    }

    public class Joker extends Card{
        public String redOrBlack;

        public Joker(String c) {
            if (!c.equalsIgnoreCase("red") && !c.equalsIgnoreCase("black"))
                throw new IllegalArgumentException("Jokers can only be red or black");

            this.redOrBlack = c.toLowerCase();
        }

        public String toString() {
            //return this.redOrBlack + " Joker";
            return (this.redOrBlack.charAt(0) + "J").toUpperCase();
        }

        public Joker getCopy() {
            return new Joker(this.redOrBlack);
        }

        public int getValue() {
            return numOfCards - 1;
        }

        public String getColor() {
            return this.redOrBlack;
        }
    }

}
