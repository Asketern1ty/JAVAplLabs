import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Task1MaxWords {
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the path to the text file: ");
        String filePath = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String maxWordsLine = "";
            int maxWordsCount = 0;

            while ((line = reader.readLine()) != null) {
                // Розділяємо рядок на слова за пробільними символами
                String[] words = line.trim().split("\\s+");
                if (words.length > maxWordsCount && !line.trim().isEmpty()) {
                    maxWordsCount = words.length;
                    maxWordsLine = line;
                }
            }

            if (maxWordsCount > 0) {
                System.out.println("Line with the maximum number of words (" + maxWordsCount + " words):");
                System.out.println(maxWordsLine);
            } else {
                System.out.println("The file is empty or contains no words.");
            }

        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }
}