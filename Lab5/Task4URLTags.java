import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task4URLTags {
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter URL (e.g., https://example.com): ");
        String urlString = scanner.nextLine();

        // TreeMap автоматично сортує ключі за лексикографічним порядком
        Map<String, Integer> tagCounts = new TreeMap<>();

        try {
            URL url = new URI(urlString).toURL();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                String line;
                // Регулярний вираз для пошуку імені тегу (наприклад, <div> -> div)
                Pattern pattern = Pattern.compile("<([a-zA-Z0-9]+)[^>]*>");

                while ((line = reader.readLine()) != null) {
                    Matcher matcher = pattern.matcher(line);
                    while (matcher.find()) {
                        String tag = matcher.group(1).toLowerCase();
                        tagCounts.put(tag, tagCounts.getOrDefault(tag, 0) + 1);
                    }
                }
            }

            System.out.println("\n=== Results: in lexicographical order ===");
            tagCounts.forEach((tag, count) -> System.out.println("<" + tag + "> : " + count));

            System.out.println("\n=== Results: in ascending order of frequency ===");
            
            // Сортування та вивід безпосередньо через Stream API (без створення змінної типу List)
            tagCounts.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach(entry -> System.out.println("<" + entry.getKey() + "> : " + entry.getValue()));

        } catch (Exception e) {
            System.out.println("An error occurred (check the correctness of the URL): " + e.getMessage());
        }
    }
}