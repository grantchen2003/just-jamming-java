import java.util.Set;

public record Card(Card.Suit suit, Card.Rank rank) {
    public enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

    public enum Rank {
        TWO(Set.of(2)),
        THREE(Set.of(3)),
        FOUR(Set.of(4)),
        FIVE(Set.of(5)),
        SIX(Set.of(6)),
        SEVEN(Set.of(7)),
        EIGHT(Set.of(8)),
        NINE(Set.of(9)),
        TEN(Set.of(10)),
        JACK(Set.of(10)),
        QUEEN(Set.of(10)),
        KING(Set.of(10)),
        ACE(Set.of(1, 11));

        public final Set<Integer> values;

        Rank(Set<Integer> values) {
            this.values = values;
        }
    }

    @Override
    public String toString() {
        return String.format("%s of %s", rank.toString().toLowerCase(), suit.toString().toLowerCase());
    }
}
