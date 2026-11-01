public class IdentificationQuestion extends Question {
    private static final long serialVersionUID = 1L;
    private final String answer;

    public IdentificationQuestion(String text, int points, String answer) {
        super(text, points);
        if (answer == null || answer.isBlank()) throw new IllegalArgumentException("Answer is required.");
        this.answer = answer.trim();
    }

    @Override public boolean check(String given) { return given != null && given.trim().equalsIgnoreCase(answer); }
    @Override public String display() { return getText() + "\n  (type your answer)"; }
    @Override public String getCorrectAnswer() { return answer; }
    @Override public String toFileLine() { return "ID|" + getPoints() + "|" + getText() + "|" + answer; }
}
 