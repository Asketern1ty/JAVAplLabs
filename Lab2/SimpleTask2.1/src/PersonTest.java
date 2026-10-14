import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

public class PersonTest {

    @Test
    public void testEqualsAndHashCode() {
        // Включаем экспериментальную поддержку для работы с Java 25
        System.setProperty("net.bytebuddy.experimental", "true");
        
        // Дальше ваш обычный код
        EqualsVerifier.simple().forClass(Person.class).verify();
        System.out.println("Test passed successfully!");
    }
}