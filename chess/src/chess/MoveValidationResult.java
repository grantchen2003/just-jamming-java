package chess;

public class MoveValidationResult {
    private final boolean legal;
    private final String reason;

    private MoveValidationResult(boolean legal, String reason) {
        this.legal = legal;
        this.reason = reason;
    }

    public static MoveValidationResult legal() {
        return new MoveValidationResult(true, null);
    }

    public static MoveValidationResult illegal(String reason) {
        return new MoveValidationResult(false, reason);
    }

    public boolean isLegal() {
        return legal;
    }

    public String getReason() {
        return reason;
    }
}
