import java.util.ArrayList;
import java.util.List;

/** INHERITANCE: extends Question. POLYMORPHISM: overrides check/display. */
public class MultipleChoiceQuestion extends Question {
    private static final long serialVersionUID = 1L;
    private final ArrayList<String> options;
    private final int correctIndex; // 0-based

    public MultipleChoiceQuestion(String text, int points, List<String> options, int correctIndex) {
        super(text, points);
        if (options.size() < 2) throw new IllegalArgumentException("Need at least 2 options.");
        if (correctIndex < 0 || correctIndex >= options.size())
            throw new IllegalArgumentException("Correct option number is out of range.");
        this.options = new ArrayList<>(options);
        this.correctIndex = correctIndex;
    }

    @Override public boolean check(String answer) {
        return answer != null && answer.trim().equals(String.valueOf(correctIndex + 1));
    }

    @Override public String display() {
        StringBuilder sb = new StringBuilder(getText());
        for (int i = 0; i < options.size(); i++) sb.append("\n  ").append(i + 1).append(") ").append(options.get(i));
        return sb.toString();
    }

    @Override public String getCorrectAnswer() { return (correctIndex + 1) + ") " + options.get(correctIndex); }

    @Override public String toFileLine() {
        return "MC|" + getPoints() + "|" + getText() + "|" + String.join(";", options) + "|" + (correctIndex + 1);
    }
}
