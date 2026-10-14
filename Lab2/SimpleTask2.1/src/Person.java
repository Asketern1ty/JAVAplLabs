import java.util.Objects;

public class Person {
    private String lastName;
    private String firstName;
    private int age;

    public Person(String lastName, String firstName, int age) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
    }

    // Крок 1: Реалізація методу equals
    @Override
    public boolean equals(Object o) {
        // Перевірка на посилання на один і той самий об'єкт
        if (this == o) return true;
        
        // Перевірка на null і збіг класів
        if (o == null || getClass() != o.getClass()) return false;
        
        // Приведення типів і порівняння полів
        Person person = (Person) o;
        return age == person.age &&
               Objects.equals(lastName, person.lastName) &&
               Objects.equals(firstName, person.firstName);
    }

    // hashCode обов'язковий при перевизначенні equals (особливо для EqualsVerifier)
    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, age);
    }

    @Override
    public String toString() {
        return "Person{lastName='" + lastName + "', firstName='" + firstName + "', age=" + age + "}";
    }
}