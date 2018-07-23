package coolness.balderdashserver.Model;

/**
 * Ready codes
 * 0 = Start the game
 * 1 = Check if all questions are in
 * 2 = Check if all answers are in
 * 3 = Check if all guesses are in
 * 4 = Go to the next question
 */

public class ReadyRequest {
    private int code;
    public int getCode() { return code; }
}
