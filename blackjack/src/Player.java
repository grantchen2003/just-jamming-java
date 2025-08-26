import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public abstract class Player {
    protected final Set<Card> hand = new HashSet<>();
    private final String name;

    Player(String name) {
        this.name = name;
    }

    public String getHandStatus() {
        return String.format("%s's current hand: %s\n%s's current hand total: %d", name, hand, name, getHandTotal());
    }

    public int getHandTotal() {
        return getPossibleHandTotals().stream()
                .min(Comparator.comparingInt(handTotal -> Math.abs(handTotal - BlackJack.BUST_LIMIT)))
                .orElseThrow(() -> new IllegalStateException("No hand totals available"));
    }

    public String getName() {
        return name;
    }

    abstract Move getMove();

    public void addToHand(Set<Card> cards) {
        hand.addAll(cards);
    }

    private Set<Integer> getPossibleHandTotals() {
        Set<Integer> possibleHandTotals = new HashSet<>(Set.of(0));

        for (Card card : hand) {
            Set<Integer> newPossibleHandTotals = new HashSet<>();
            for (int total : possibleHandTotals) {
                for (int value : card.rank().values) {
                    newPossibleHandTotals.add(total + value);
                }
            }
            possibleHandTotals = newPossibleHandTotals; // update totals
        }

        return possibleHandTotals;
    }
}
