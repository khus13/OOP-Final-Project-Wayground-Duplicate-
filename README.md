# Notes for everyone
## We need to add more features:
1. *AI support* to generate flashcards automatically based on lessons
2. More quiz types -- T/F, flashcards, identification
3. Assign activities (homework)

# WayGround Duplicate (MAKE NAME)
<img width="950" height="855" alt="image" src="https://github.com/user-attachments/assets/8a54b629-5366-4bed-8b43-2256b97ae123" />

<img width="1053" height="936" alt="image" src="https://github.com/user-attachments/assets/98584e00-f0fe-4f4d-bc15-665e94529546" />


<img width="973" height="992" alt="image" src="https://github.com/user-attachments/assets/15f75be6-b55a-4414-afb5-f4c5fe9b3b69" />



<img width="1018" height="763" alt="image" src="https://github.com/user-attachments/assets/1794d56b-a00a-421b-8fd3-22ed908f9ed0" />


<img width="899" height="1093" alt="image" src="https://github.com/user-attachments/assets/b15f407c-f0ea-40fd-94d5-b0c307028045" />

```
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;

public class QuestionGenerator {

    public static void main(String[] args) {
        // 1. Initialize the AI Model
        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(System.getenv("OPENAI_API_KEY"))
                .modelName("gpt-4o-mini") // Cost-effective and fast
                .temperature(0.3) // Low temperature ensures factual questions
                .build();

        // 2. Sample text extracted from an imported file
        String importedText = "The solar system consists of the Sun and the objects that orbit it. "
                + "Jupiter is the largest planet, while Mercury is the closest to the Sun.";

        // 3. Craft a strict prompt requesting JSON
        String prompt = "You are an educational quiz generator. Based on the following text, "
                + "generate 2 multiple-choice questions. "
                + "You MUST reply ONLY with a raw JSON array matching this structure: "
                + "[{\"question\": \"...\", \"options\": [\"A\", \"B\", \"C\", \"D\"], \"correctAnswer\": \"...\"}]. "
                + "Do not include markdown formatting like ```json. \n\n"
                + "Text: " + importedText;

        // 4. Send to AI and get the raw JSON string
        String jsonResponse = model.generate(prompt);
        System.out.println(jsonResponse);
        
        // 5. Next step: Use Gson/Jackson to map jsonResponse to a List<QuestionObject>
    }
}
```
<img width="827" height="773" alt="image" src="https://github.com/user-attachments/assets/05ff057a-bd31-494d-8e4f-c496dfaa0c3f" />


Wayground Duplicate is an AI-supported, gamified supplemental learning and teaching platform.

- Create & Customize: Build interactive quizzes based on modules or lessons uploaded by the user.
- Wayground AI: Use an AI assistant to generate curriculum-aligned resources, differentiate materials, translate languages, or adjust reading levels in minutes.
- Run Sessions: Launch live gamified classroom sessions (with leaderboards, memes, and music) or assign activities as self-paced homework.
- Track Data: Access detailed class- and student-level performance reports tied to academic standards.

# Features

Users can perform the following functions:
- Join Easily: Participate in sessions or assignments using an access code or link on any device (PC, tablet, or smartphone) without needing a mandatory account.
- Engage & Learn: Work through interactive games, flashcards, and quizzes with built-in accessibility accommodations (such as read-aloud or modified timers).

# How To Run

# Credits
This project was developed by the following:
- Andrea Keisha To Chip
- Enoch Matugas
- Khugo Sebastian Auditor
