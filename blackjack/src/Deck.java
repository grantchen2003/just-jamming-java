import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Deck {
    private final Set<Card> cards = new HashSet<>();

    Deck() {
        for (final Card.Suit suit : Card.Suit.values()) {
            for (final Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    public Set<Card> draw(int numCards) {
        if (numCards > cards.size()) {
            throw new IllegalArgumentException("Cannot draw more cards than are available");
        }

        final List<Card> cardList = new ArrayList<>(cards);
        Collections.shuffle(cardList);

        final Set<Card> drawnCards = new HashSet<>();
        for (int i = 0; i < numCards; i++) {
            Card card = cardList.get(i);
            drawnCards.add(card);
            cards.remove(card);
        }

        return drawnCards;
    }
}
