import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

/** FILE HANDLING + SERIALIZATION/DESERIALIZATION. */
public class FileManager {
    private final Path dir;

    public FileManager(Path dir) { this.dir = dir; }

    private Path resolve(String name) {
        return dir.resolve(name.endsWith(".quiz") ? name : name + ".quiz");
    }

    /** Serialization: Quiz object -> binary .quiz file. */
    public Path save(Quiz quiz) throws IOException {
        Files.createDirectories(dir);
        Path p = resolve(quiz.getTitle().replaceAll("[^a-zA-Z0-9_-]", "_"));
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(p))) {
            out.writeObject(quiz);
        }
        return p;
    }

    /** Deserialization: .quiz file -> Quiz object. */
    public Quiz load(String name) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(resolve(name)))) {
            return (Quiz) in.readObject();
        }
    }

    public List<String> list() throws IOException {
        if (!Files.isDirectory(dir)) return List.of();
        try (Stream<Path> s = Files.list(dir)) {
            return s.map(p -> p.getFileName().toString()).filter(n -> n.endsWith(".quiz")).sorted().toList();
        }
    }

    /** "Upload": read a text file and build a Quiz from it. */
    public Quiz importText(Path file) throws IOException {
        List<String> lines = Files.readAllLines(file);
        String fallback = file.getFileName().toString().replaceFirst("\\.[^.]+$", "");
        Quiz quiz = new Quiz(fallback);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty() || line.startsWith("#")) continue;
            try {
                if (line.toUpperCase().startsWith("TITLE|")) {
                    List<Question> keep = new ArrayList<>(quiz.getQuestions());
                    quiz = new Quiz(line.substring(6));
                    keep.forEach(quiz::addQuestion);
                } else {
                    quiz.addQuestion(parse(line));
                }
            } catch (RuntimeException e) {
                throw new IllegalArgumentException("Line " + (i + 1) + ": " + e.getMessage());
            }
        }
        if (quiz.size() == 0) throw new IllegalArgumentException("No questions found in file.");
        return quiz;
    }

    /** Format: MC|pts|question|a;b;c|correctNumber  TF|pts|question|true  ID|pts|question|answer */
    private Question parse(String line) {
        String[] p = line.split("\\|");
        int pts = Integer.parseInt(p[1].trim());
        return switch (p[0].trim().toUpperCase()) {
            case "MC" -> new MultipleChoiceQuestion(p[2], pts,
                    Arrays.stream(p[3].split(";")).map(String::trim).toList(), Integer.parseInt(p[4].trim()) - 1);
            case "TF" -> new TrueFalseQuestion(p[2], pts, Boolean.parseBoolean(p[3].trim()));
            case "ID" -> new IdentificationQuestion(p[2], pts, p[3]);
            default -> throw new IllegalArgumentException("Unknown type '" + p[0] + "'");
        };
    }

    public void exportText(Quiz quiz, Path file) throws IOException {
        List<String> out = new ArrayList<>();
        out.add("TITLE|" + quiz.getTitle());
        for (Question q : quiz.getQuestions()) out.add(q.toFileLine());
        Files.write(file, out);
    }
}
