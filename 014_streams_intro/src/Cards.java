import java.util.ArrayList;
import java.util.List;

public class Cards {
    public static List<Card> fullDeck() {
        List<Card> deck = new ArrayList<>();

        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                deck.add(new Card(suit, rank));
            }
        }

        return deck;
    }
}

class Card implements Comparable<Card> {
    private Suit suit;
    private Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public String toString() {
        return "" + suit + rank;
    }

    @Override
    public int compareTo(Card o) {
        int suitComparison = suit.compareTo(o.suit);
        if (suitComparison != 0) {
            return suitComparison;
        }
        return rank.compareTo(o.rank);
    }
}

enum Rank {
    TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"), TEN("10"),
    JACK("J"), QUEEN("Q"), KING("K"), ACE("A");

    private String symbol;

    Rank(String symbol) {
        this.symbol = symbol;
    }

    public String toString() {
        return symbol;
    }
}

enum Suit {
    HEARTS("♥"), DIAMONDS("♦"), CLUBS("♣"), SPADES("♠");

    private String symbol;

    Suit(String symbol) {
        this.symbol = symbol;
    }

    public String toString() {
        return symbol;
    }
}
