public class DisplayDeck {
    public static void main(String[] args) {
        Deck deck = new Deck();
        for (int suit = Card.DIAMONDS; suit <= Card.SPADES; suit++) {
            for (int number = Card.ACE; number <= Card.KING; number++) {
                Card card = deck.getCard(suit, number);
                System.out.println(card.numberToString(card.getNumber())
                                   + " of " 
                                   + card.suitToString(card.getSuit()));
            }
        }
    }
}
