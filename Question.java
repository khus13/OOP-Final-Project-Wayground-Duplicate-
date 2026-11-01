import java.io.Serializable;

/** ABSTRACTION: defines what every question must do, not how. */
public abstract class Question implements Serializable {
    private static final long serialVersionUID = 1L;

    // ENCAPSULATION: private fields, validated setters
    private String text;
    private int points;

    protected Question(String text, int points) {
        setText(text);
        setPoints(points);
    }

    public String getText() { return text; }
    public int getPoints() { return points; }

    public void setText(String text) {
        if (text == null || text.isBlank()) throw new IllegalArgumentException("Question text is required.");
        this.text = text.trim();
    }

    public void setPoints(int points) {
        if (points < 1) throw new IllegalArgumentException("Points must be at least 1.");
        this.points = points;
    }

    public abstract boolean check(String answer);   // each subclass grades differently
    public abstract String display();               // each subclass shows itself differently
    public abstract String getCorrectAnswer();
    public abstract String toFileLine();            // text format used for import/export
}

