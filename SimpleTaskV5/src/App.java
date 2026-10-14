public class App {

    public static void main(String[] args) {
        // Вхідний рядок із словами
        String inputText = "hello world java cat dog unique programming task";
        System.out.println("Input String: " + inputText);

        // Розбиваємо рядок на масив слів (за пробілами)
        String[] words = inputText.split("\\s+");

        // Отримуємо результат
        String[] result = findWordsWithUniqueChars(words);

        // Виводимо результат
        System.out.println("\nWords consisting only of distinct characters: ");
        for (String word : result) {
            System.out.println(word);
        }
    }

    public static String[] findWordsWithUniqueChars(String[] words) {
        int count = 0;
        for (String word : words) {
            if (hasOnlyUniqueChars(word)) {
                count++;
            }
        }

        String[] resultArray = new String[count];
        int index = 0;

        for (String word : words) {
            if (hasOnlyUniqueChars(word)) {
                resultArray[index] = word;
                index++;
            }
        }

        return resultArray;
    }

    public static boolean hasOnlyUniqueChars(String word) {
        if (word == null || word.isEmpty()) {
            return false;
        }

        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            for (int j = i + 1; j < word.length(); j++) {
                if (currentChar == word.charAt(j)) {
                    return false;
                }
            }
        }
        return true;
    }
}