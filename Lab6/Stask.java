import java.util.HashMap;
import java.util.Scanner;

class Translator {
    private HashMap<String, String> dictionary;

    public Translator() {
        dictionary = new HashMap<>();
    }

    public void addWord(String englishWord, String ukrainianWord) {
        dictionary.put(englishWord.toLowerCase(), ukrainianWord.toLowerCase());
    }

    public String translatePhrase(String phrase) {
        String[] words = phrase.split("\\s+");
        StringBuilder translatedPhrase = new StringBuilder();

        for (String word : words) {
            String cleanWord = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
            String translatedWord = dictionary.getOrDefault(cleanWord, word);
            translatedPhrase.append(translatedWord).append(" ");
        }

        return translatedPhrase.toString().trim();
    }
}

public class Stask {
    public static void main(String[] args) {
        Translator translator = new Translator();
        Scanner scanner = new Scanner(System.in);

        translator.addWord("hello", "привіт");
        translator.addWord("world", "світ");
        translator.addWord("this", "це");
        translator.addWord("is", "є");
        translator.addWord("a", "якийсь");
        translator.addWord("simple", "простий");
        translator.addWord("task", "завдання");
        translator.addWord("java", "джава");

        System.out.println("--- Translator Program ---");
        System.out.println("Base dictionary loaded.");

        while (true) {
            System.out.print("Do you want to add a new word to the dictionary? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
            
            if (!response.equals("yes")) {
                break;
            }

            System.out.print("Enter an English word: ");
            String engWord = scanner.nextLine().trim();
            System.out.print("Enter translation: ");
            String ukrWord = scanner.nextLine().trim();

            translator.addWord(engWord, ukrWord);
            System.out.println("Word added successfully!\n");
        }

        System.out.println("\nEnter an English phrase to translate:");
        String phraseToTranslate = scanner.nextLine();

        String result = translator.translatePhrase(phraseToTranslate);
        System.out.println("\nTranslation:");
        System.out.println(result);

        scanner.close();
    }
}