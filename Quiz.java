import java.io.Serializable;
import java.util.*;

/** Holds questions; treats every Question the same (polymorphism). Serializable as a whole. */
public class Quiz implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private final List<Question> questions = new ArrayList<>();

    public Quiz(String title) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Title is required.");
        this.title = title.trim();
    }

    public String getTitle() { return title; }
    public List<Question> getQuestions() { return Collections.unmodifiableList(questions); }
    public void addQuestion(Question q) { questions.add(Objects.requireNonNull(q)); }
    public int size() { return questions.size(); }

    public int getTotalPoints() {
        int t = 0;
        for (Question q : questions) t += q.getPoints();
        return t;
    }

    /** Runs the quiz in the console and returns the score. */
    public int play(Scanner in) {
        List<Question> order = new ArrayList<>(questions);
        Collections.shuffle(order);
        int score = 0;
        for (int i = 0; i < order.size(); i++) {
            Question q = order.get(i);                       // could be any subtype
            System.out.println("\nQ" + (i + 1) + "/" + order.size() + " [" + q.getPoints() + " pt]");
            System.out.println(q.display());                 // polymorphic call
            System.out.print("> ");
            if (q.check(in.nextLine())) {                    // polymorphic call
                System.out.println("Correct!");
                score += q.getPoints();
            } else {
                System.out.println("Wrong. Answer: " + q.getCorrectAnswer());
            }
        }
        return score;
    }

    @Override public String toString() { return title + " (" + size() + " questions)"; }
}
