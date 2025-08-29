package chess;

public record MoveValidationResult(boolean isLegal, String reason) {
    public static MoveValidationResult createIllegal(String reason) {
        return new MoveValidationResult(false, reason);
    }

    public static MoveValidationResult createLegal() {
        return new MoveValidationResult(true, null);
    }
}
