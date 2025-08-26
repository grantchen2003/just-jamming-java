import java.util.List;

public class BlackJack {
    public static final int BUST_LIMIT = 21;

    public static void main(String[] args) {
        final Deck deck = new Deck();

        final Player user = new UserPlayer();
        final Player dealer = new DealerPlayer();

        final List<Player> players = List.of(user, dealer);

        for (final Player player : players) {
            player.addToHand(deck.draw(2));

            System.out.println(player.getHandStatus());

            while (player.getMove() == Move.HIT) {
                player.addToHand(deck.draw(1));

                System.out.println(player.getHandStatus());

                if (player.getHandTotal() > BUST_LIMIT) {
                    System.out.println(player.getName() + " busted!");
                    return;
                }
            }
        }

        if (user.getHandTotal() == dealer.getHandTotal()) {
            System.out.println("Tie! Both have the same hand total");
        } else if (user.getHandTotal() > dealer.getHandTotal()) {
            System.out.printf("%s wins! %s has the bigger hand total\n", user.getName(), user.getName());
        } else {
            System.out.printf("%s wins! %s has the bigger hand total\n", dealer.getName(), dealer.getName());
        }

        for (final Player player : players) {
            System.out.println(player.getHandStatus());
        }
    }
}
