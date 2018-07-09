public class InvalidTripException extends TransitException {
    public String getMessage() {
        return "Invalid Trip Found. Max Fee Charged.";
    }
}
