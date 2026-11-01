import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class WayGround {
    private static final Scanner in = new Scanner(System.in);
    private static final FileManager files = new FileManager(Paths.get("quizzes"));
    private static Quiz current;

    public static void main(String[] args) {
        System.out.println("=== WayGround: OOP Quiz Game ===");
        boolean running = true;
        while (running) {
            System.out.println("\nCurrent quiz: " + (current == null ? "none" : current));
            System.out.println("1 Create quiz   2 Add question   3 Take quiz");
            System.out.println("4 Save (serialize)   5 Load (deserialize)");
            System.out.println("6 Import text file   7 Export text file   0 Exit");
            String c = ask("> ");
            try {
                switch (c) {
                    case "1" -> current = new Quiz(ask("Quiz title: "));
                    case "2" -> { if (hasQuiz()) addQuestion(); }
                    case "3" -> { if (hasQuiz()) takeQuiz(); }
                    case "4" -> { if (hasQuiz()) System.out.println("Saved to " + files.save(current)); }
                    case "5" -> load();
                    case "6" -> { current = files.importText(Paths.get(ask("Path to text file: "))); System.out.println("Imported " + current); }
                    case "7" -> { if (hasQuiz()) { files.exportText(current, Paths.get(ask("Export to (e.g. my_quiz.txt): "))); System.out.println("Exported."); } }
                    case "0" -> running = false;
                    default -> System.out.println("Invalid choice.");
                }
            } catch (IOException | ClassNotFoundException | RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println("Bye!");
    }

    private static String ask(String prompt) { System.out.print(prompt); return in.nextLine().trim(); }

    private static int askInt(String prompt) {
        while (true) {
            try { return Integer.parseInt(ask(prompt)); }
            catch (NumberFormatException e) { System.out.println("Enter a whole number."); }
        }
    }

    private static boolean hasQuiz() {
        if (current == null) System.out.println("Create, load, or import a quiz first.");
        return current != null;
    }

    private static void addQuestion() {
        String type = ask("Type [1] Multiple choice  [2] True/False  [3] Identification: ");
        String text = ask("Question: ");
        int pts = askInt("Points: ");
        Question q = switch (type) {
            case "1" -> {
                List<String> opts = Arrays.stream(ask("Options (separate with ;): ").split(";"))
                        .map(String::trim).filter(s -> !s.isEmpty()).toList();
                yield new MultipleChoiceQuestion(text, pts, opts, askInt("Correct option number: ") - 1);
            }
            case "2" -> new TrueFalseQuestion(text, pts, Boolean.parseBoolean(ask("Correct answer (true/false): ")));
            case "3" -> new IdentificationQuestion(text, pts, ask("Answer: "));
            default -> throw new IllegalArgumentException("Invalid type.");
        };
        current.addQuestion(q);
        System.out.println("Question added (" + current.size() + " total).");
    }

    private static void takeQuiz() {
        if (current.size() == 0) { System.out.println("This quiz has no questions."); return; }
        int score = current.play(in);
        int total = current.getTotalPoints();
        System.out.printf("%nScore: %d/%d (%.0f%%)%n", score, total, 100.0 * score / total);
    }

    private static void load() throws IOException, ClassNotFoundException {
        List<String> saved = files.list();
        if (saved.isEmpty()) { System.out.println("No saved quizzes yet."); return; }
        saved.forEach(s -> System.out.println("  - " + s));
        current = files.load(ask("File name: "));
        System.out.println("Loaded " + current);
    }
}