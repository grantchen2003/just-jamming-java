public class DealerPlayer extends Player{
    DealerPlayer() {
        super("Dealer");
    }

    @Override
    Move getMove() {
        return getHandTotal() < 17 ? Move.HIT : Move.STAND;
    }
}
