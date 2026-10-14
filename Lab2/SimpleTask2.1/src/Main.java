import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {
        // a. Створіть екземпляр Person
        Person originalPerson = new Person("Шевченко", "Тарас", 47);
        System.out.println("Оригінальний об'єкт: " + originalPerson);

        // Ініціалізація бібліотеки Gson
        Gson gson = new Gson();

        // b. Конвертуйте його в JSON
        String jsonText = gson.toJson(originalPerson);
        System.out.println("\nJSON формат: " + jsonText);

        // c. Конвертуйте назад в об'єкт
        Person deserializedPerson = gson.fromJson(jsonText, Person.class);
        System.out.println("\nДесеріалізований об'єкт: " + deserializedPerson);

        // d. Перевірте equals-ом початковий і одержаний об'єкти
        boolean isEqual = originalPerson.equals(deserializedPerson);
        System.out.println("\nОб'єкти однакові (результат equals)? " + isEqual);
    }
}