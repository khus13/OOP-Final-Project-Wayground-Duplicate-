public class TrueFalseQuestion extends Question {
    private static final long serialVersionUID = 1L;
    private final boolean correct;

    public TrueFalseQuestion(String text, int points, boolean correct) {
        super(text, points);
        this.correct = correct;
    }

    @Override public boolean check(String answer) {
        if (answer == null) return false;
        String a = answer.trim().toLowerCase();
        return (correct && (a.equals("t") || a.equals("true"))) || (!correct && (a.equals("f") || a.equals("false")));
    }

    @Override public String display() { return getText() + "\n  (true / false)"; }
    @Override public String getCorrectAnswer() { return String.valueOf(correct); }
    @Override public String toFileLine() { return "TF|" + getPoints() + "|" + getText() + "|" + correct; }
}